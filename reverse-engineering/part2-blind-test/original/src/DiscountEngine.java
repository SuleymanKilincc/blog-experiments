/**
 * The central engine that runs every discount rule applied to an order.
 *
 * ═══════════════════════════════════════════════════════════
 *  ORDER IN WHICH DISCOUNTS ARE APPLIED
 * ═══════════════════════════════════════════════════════════
 *  1. ITEM LEVEL (separately for each OrderItem)
 *     a) Category discount  : fixed % defined on the product's category
 *     b) Bulk discount      : an extra BULK_QUANTITY_DISCOUNT % when
 *                             ≥ BULK_QUANTITY_THRESHOLD units of the same product are bought
 *     Item unit price = basePrice × (1 - catPct/100) × (1 - bulkPct/100)
 *
 *  2. TIER DISCOUNT (on the order subtotal)
 *     A discount equal to the customer tier's getDiscountPercent().
 *
 *  3. LOYALTY BONUS (on the amount after the tier discount)
 *     If the customer has LOYALTY_ORDER_THRESHOLD or more completed orders,
 *     an extra LOYALTY_DISCOUNT_PCT is applied.
 *
 *  4. COUPON DISCOUNT (on the amount after the loyalty bonus)
 *     If the coupon assigned to the order is valid and its minimum amount is met,
 *     a discount equal to the coupon's discountPercent is applied.
 *
 *  5. The GRAND TOTAL is computed; it cannot go below zero.
 * ═══════════════════════════════════════════════════════════
 */
public class DiscountEngine {

    // ---- Constants ----------------------------------------------------------

    /** Buying this many or more of the same product triggers the bulk discount. */
    public static final int    BULK_QUANTITY_THRESHOLD  = 5;

    /** Bulk discount percentage (applied to the item's unit price). */
    public static final double BULK_QUANTITY_DISCOUNT   = 5.0;

    /** Minimum number of completed orders needed for the loyalty bonus. */
    public static final int    LOYALTY_ORDER_THRESHOLD  = 5;

    /** Loyalty bonus percentage (applied to the amount after the tier discount). */
    public static final double LOYALTY_DISCOUNT_PCT     = 2.0;

    // ---- Dependency ---------------------------------------------------------

    private final Store store;

    public DiscountEngine(Store store) {
        this.store = store;
    }

    // -------------------------------------------------------------------------
    // Main method
    // -------------------------------------------------------------------------

    /**
     * Applies every discount layer to the given order and records the
     * financial summary with {@link Order#setFinancials}.
     *
     * If the coupon is not valid or its minimum amount is not met,
     * the order is not changed and {@code false} is returned.
     *
     * @param order The OPEN order to price
     * @return {@code true} if pricing succeeded, {@code false} on a coupon error
     */
    public boolean applyDiscountsAndFinalize(Order order) {
        Customer customer = store.getCustomer(order.getCustomerId());
        if (customer == null) return false;

        // ── Step 1: item-level discounts ─────────────────────────────────────
        double subtotalBeforeDiscounts = 0.0;
        double itemDiscountSavings     = 0.0;

        for (OrderItem item : order.getItems()) {
            Product  product  = store.getProduct(item.getProductId());
            Category category = (product != null)
                    ? store.getCategory(product.getCategoryId())
                    : null;

            double catPct  = (category != null) ? category.getDiscountPercent() : 0.0;
            double bulkPct = (item.getQuantity() >= BULK_QUANTITY_THRESHOLD)
                    ? BULK_QUANTITY_DISCOUNT : 0.0;

            item.applyItemDiscounts(catPct, bulkPct);

            subtotalBeforeDiscounts += item.getUnitBasePrice() * item.getQuantity();
            itemDiscountSavings     += item.getSavings();
        }

        // Amount left after the item discounts
        double afterItemDiscounts = subtotalBeforeDiscounts - itemDiscountSavings;

        // ── Step 2: tier discount ─────────────────────────────────────────────
        double tierPct    = customer.getTier().getDiscountPercent();
        double tierAmount = roundToCents(afterItemDiscounts * tierPct / 100.0);
        double afterTier  = afterItemDiscounts - tierAmount;

        // ── Step 3: loyalty bonus ─────────────────────────────────────────────
        double loyaltyPct    = customer.isLoyaltyEligible() ? LOYALTY_DISCOUNT_PCT : 0.0;
        double loyaltyAmount = roundToCents(afterTier * loyaltyPct / 100.0);
        double afterLoyalty  = afterTier - loyaltyAmount;

        // ── Step 4: coupon discount ───────────────────────────────────────────
        double couponPct    = 0.0;
        double couponAmount = 0.0;

        String couponCode = order.getAppliedCouponCode();
        if (couponCode != null) {
            Coupon coupon = store.getCoupon(couponCode);
            if (coupon == null || !coupon.isValid(afterLoyalty)) {
                // Coupon not found or its conditions not met → fail
                return false;
            }
            couponPct    = coupon.getDiscountPercent();
            couponAmount = roundToCents(afterLoyalty * couponPct / 100.0);
            coupon.recordUsage();
        }

        // ── Step 5: grand total ───────────────────────────────────────────────
        double grandTotal = afterLoyalty - couponAmount;
        if (grandTotal < 0) grandTotal = 0.0;

        // Write to the order object
        order.setFinancials(
                subtotalBeforeDiscounts, itemDiscountSavings,
                tierPct,    tierAmount,
                loyaltyPct, loyaltyAmount,
                couponPct,  couponAmount,
                grandTotal
        );
        return true;
    }

    // -------------------------------------------------------------------------
    // Helpers
    // -------------------------------------------------------------------------

    /** Rounds an amount to cents (2 decimal places). */
    private static double roundToCents(double amount) {
        return Math.round(amount * 100.0) / 100.0;
    }
}
