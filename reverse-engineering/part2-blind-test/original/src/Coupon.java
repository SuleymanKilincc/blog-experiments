/**
 * Discount coupon.
 *
 * A coupon is valid when both of these hold:
 *  - It has uses left (or is unlimited)
 *  - The order amount before the coupon meets minimumOrderAmount
 *
 * The coupon is applied to the order total before the order is finalized.
 * A maximum usage count of -1 means unlimited use.
 */
public class Coupon {

    private final String code;
    private final double discountPercent;
    private final double minimumOrderAmount; // minimum amount for the coupon to be valid
    private final int    maxUsages;          // -1 = unlimited
    private int          usageCount;

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Creates a coupon with limited uses.
     *
     * @param code               Unique coupon code
     * @param discountPercent    Discount percentage (0 < value ≤ 100)
     * @param minimumOrderAmount Minimum order amount for it to be valid (≥ 0)
     * @param maxUsages          Maximum number of uses (-1 = unlimited)
     */
    public Coupon(String code, double discountPercent, double minimumOrderAmount, int maxUsages) {
        if (code == null || code.isBlank())
            throw new IllegalArgumentException("Kupon kodu boş olamaz");
        if (discountPercent <= 0 || discountPercent > 100)
            throw new IllegalArgumentException("Kupon indirimi 0-100 arasında olmalı (0 hariç)");
        if (minimumOrderAmount < 0)
            throw new IllegalArgumentException("Minimum sipariş tutarı negatif olamaz");
        this.code                = code;
        this.discountPercent     = discountPercent;
        this.minimumOrderAmount  = minimumOrderAmount;
        this.maxUsages           = maxUsages;
        this.usageCount          = 0;
    }

    /** Creates a coupon with unlimited uses (maxUsages = -1). */
    public Coupon(String code, double discountPercent, double minimumOrderAmount) {
        this(code, discountPercent, minimumOrderAmount, -1);
    }

    // -------------------------------------------------------------------------
    // Getters
    // -------------------------------------------------------------------------

    public String getCode()               { return code; }
    public double getDiscountPercent()    { return discountPercent; }
    public double getMinimumOrderAmount() { return minimumOrderAmount; }
    public int    getMaxUsages()          { return maxUsages; }
    public int    getUsageCount()         { return usageCount; }

    // -------------------------------------------------------------------------
    // Business logic
    // -------------------------------------------------------------------------

    /**
     * Checks whether the coupon can be used for the given amount.
     *
     * @param orderAmount The order amount the coupon would be applied to
     * @return {@code true} if the coupon is valid
     */
    public boolean isValid(double orderAmount) {
        if (maxUsages >= 0 && usageCount >= maxUsages) return false;
        return orderAmount >= minimumOrderAmount;
    }

    /**
     * Records a use of the coupon (increments usageCount).
     * Called by {@link DiscountEngine} when an order is finalized successfully.
     */
    public void recordUsage() {
        usageCount++;
    }

    /** Remaining uses. Returns {@code -1} if unlimited. */
    public int getRemainingUsages() {
        if (maxUsages < 0) return -1;
        return maxUsages - usageCount;
    }

    // -------------------------------------------------------------------------
    // Formatting
    // -------------------------------------------------------------------------

    public String toTableRow() {
        String limit = (maxUsages < 0) ? "sinirsiz" : String.valueOf(maxUsages);
        String remaining = (maxUsages < 0) ? "∞" : String.valueOf(getRemainingUsages());
        return String.format("%-14s | %7.1f%% | %12.2f | %10s | %5d / %s",
                code, discountPercent, minimumOrderAmount, limit, usageCount, remaining);
    }

    @Override
    public String toString() {
        return String.format("Coupon{code='%s', discount=%.1f%%, minOrder=%.2f, used=%d/%s}",
                code, discountPercent, minimumOrderAmount, usageCount,
                maxUsages < 0 ? "∞" : String.valueOf(maxUsages));
    }
}
