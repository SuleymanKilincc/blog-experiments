/**
 * Customer loyalty tier.
 * Each tier has a fixed discount percentage applied to the order total.
 *
 * Tiers:
 *   REGULAR  → 0%  (default)
 *   SILVER   → 5%
 *   GOLD     → 10%
 *   PLATINUM → 15%
 */
public enum CustomerTier {

    REGULAR (0.0,  "Regular"),
    SILVER  (5.0,  "Silver"),
    GOLD    (10.0, "Gold"),
    PLATINUM(15.0, "Platinum");

    private final double discountPercent;
    private final String displayName;

    CustomerTier(double discountPercent, String displayName) {
        this.discountPercent = discountPercent;
        this.displayName     = displayName;
    }

    /** Discount percentage this tier applies to the order total. */
    public double getDiscountPercent() { return discountPercent; }

    /** Tier name shown to the user. */
    public String getDisplayName() { return displayName; }

    /**
     * Creates a tier from a case-insensitive string.
     * @throws IllegalArgumentException for an unknown tier name
     */
    public static CustomerTier fromString(String s) {
        return switch (s.toUpperCase().trim()) {
            case "REGULAR"  -> REGULAR;
            case "SILVER"   -> SILVER;
            case "GOLD"     -> GOLD;
            case "PLATINUM" -> PLATINUM;
            default -> throw new IllegalArgumentException("Bilinmeyen müşteri katmanı: " + s);
        };
    }
}
