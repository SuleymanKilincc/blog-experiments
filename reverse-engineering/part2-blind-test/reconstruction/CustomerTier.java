// Reconstructed from obfuscated enum "e": customer tiers and the discount each one gets.
public enum CustomerTier {
    REGULAR(0.0, "Regular"),
    SILVER(5.0, "Silver"),
    GOLD(10.0, "Gold"),
    PLATINUM(15.0, "Platinum");

    private final double discountPercent;
    private final String label;

    CustomerTier(double discountPercent, String label) {
        this.discountPercent = discountPercent;
        this.label = label;
    }

    public double discountPercent() {
        return this.discountPercent;
    }

    public String label() {
        return this.label;
    }

    public static CustomerTier parse(String text) {
        return switch (text.toUpperCase().trim()) {
            case "REGULAR" -> REGULAR;
            case "SILVER" -> SILVER;
            case "GOLD" -> GOLD;
            case "PLATINUM" -> PLATINUM;
            default -> throw new IllegalArgumentException("Bilinmeyen müşteri katmanı: " + text);
        };
    }
}
