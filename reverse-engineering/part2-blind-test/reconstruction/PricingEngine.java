// Reconstructed from obfuscated class "f": prices an order in a fixed sequence.
//   1. per item: category discount, then 5% more for 5 or more units (multiplicative)
//   2. customer tier discount on what is left
//   3. 2% loyalty bonus once the customer has 5 completed orders
//   4. coupon, if its minimum is met and it has uses left
// Each discount amount is rounded to cents before it is subtracted.
public class PricingEngine {
    public static final int BULK_QUANTITY = 5;
    public static final double BULK_DISCOUNT_PERCENT = 5.0;
    public static final int LOYALTY_MIN_ORDERS = 5;
    public static final double LOYALTY_DISCOUNT_PERCENT = 2.0;
    private final Store store;

    public PricingEngine(Store store) {
        this.store = store;
    }

    // Returns false (and leaves the order unpriced) if the customer is missing or the coupon can't be used.
    public boolean price(Order order) {
        Customer customer = this.store.customer(order.customerId());
        if (customer == null) {
            return false;
        }
        double subtotal = 0.0;
        double itemSavings = 0.0;
        for (OrderItem item : order.items()) {
            Product product = this.store.product(item.productId());
            Category category = product != null ? this.store.category(product.categoryId()) : null;
            double categoryPercent = category != null ? category.discountPercent() : 0.0;
            double quantityPercent = item.quantity() >= BULK_QUANTITY ? BULK_DISCOUNT_PERCENT : 0.0;
            item.applyDiscounts(categoryPercent, quantityPercent);
            subtotal += item.unitPrice() * (double) item.quantity();
            itemSavings += item.lineSavings();
        }
        double afterItems = subtotal - itemSavings;

        double tierPercent = customer.tier().discountPercent();
        double tierDiscount = roundToCents(afterItems * tierPercent / 100.0);
        double afterTier = afterItems - tierDiscount;

        double loyaltyPercent = customer.isLoyal() ? LOYALTY_DISCOUNT_PERCENT : 0.0;
        double loyaltyDiscount = roundToCents(afterTier * loyaltyPercent / 100.0);
        double afterLoyalty = afterTier - loyaltyDiscount;

        double couponPercent = 0.0;
        double couponDiscount = 0.0;
        String couponCode = order.couponCode();
        if (couponCode != null) {
            Coupon coupon = this.store.coupon(couponCode);
            if (coupon == null || !coupon.canApplyTo(afterLoyalty)) {
                return false;
            }
            couponPercent = coupon.discountPercent();
            couponDiscount = roundToCents(afterLoyalty * couponPercent / 100.0);
            coupon.recordUse();
        }

        double total = afterLoyalty - couponDiscount;
        if (total < 0.0) {
            total = 0.0;
        }
        order.setPricing(subtotal, itemSavings, tierPercent, tierDiscount, loyaltyPercent, loyaltyDiscount,
                couponPercent, couponDiscount, total);
        return true;
    }

    private static double roundToCents(double amount) {
        return (double) Math.round(amount * 100.0) / 100.0;
    }
}
