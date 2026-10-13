// Reconstructed from obfuscated class "c": a percentage coupon with a minimum order amount and
// an optional usage limit (-1 means unlimited).
public class Coupon {
    private final String code;
    private final double discountPercent;
    private final double minOrderAmount;
    private final int maxUses;
    private int timesUsed;

    public Coupon(String code, double discountPercent, double minOrderAmount, int maxUses) {
        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("Kupon kodu boş olamaz");
        }
        if (discountPercent <= 0.0 || discountPercent > 100.0) {
            throw new IllegalArgumentException("Kupon indirimi 0-100 arasında olmalı (0 hariç)");
        }
        if (minOrderAmount < 0.0) {
            throw new IllegalArgumentException("Minimum sipariş tutarı negatif olamaz");
        }
        this.code = code;
        this.discountPercent = discountPercent;
        this.minOrderAmount = minOrderAmount;
        this.maxUses = maxUses;
        this.timesUsed = 0;
    }

    public Coupon(String code, double discountPercent, double minOrderAmount) {
        this(code, discountPercent, minOrderAmount, -1);
    }

    public String code() {
        return this.code;
    }

    public double discountPercent() {
        return this.discountPercent;
    }

    public double minOrderAmount() {
        return this.minOrderAmount;
    }

    public int maxUses() {
        return this.maxUses;
    }

    public int timesUsed() {
        return this.timesUsed;
    }

    // False when the usage limit is reached or the amount is below the minimum.
    public boolean canApplyTo(double amount) {
        if (this.maxUses >= 0 && this.timesUsed >= this.maxUses) {
            return false;
        }
        return amount >= this.minOrderAmount;
    }

    public void recordUse() {
        ++this.timesUsed;
    }

    public int remainingUses() {
        if (this.maxUses < 0) {
            return -1;
        }
        return this.maxUses - this.timesUsed;
    }

    public String toRow() {
        String limit = this.maxUses < 0 ? "sinirsiz" : String.valueOf(this.maxUses);
        String remaining = this.maxUses < 0 ? "∞" : String.valueOf(this.remainingUses());
        return String.format("%-14s | %7.1f%% | %12.2f | %10s | %5d / %s", this.code, this.discountPercent, this.minOrderAmount, limit, this.timesUsed, remaining);
    }

    @Override
    public String toString() {
        return String.format("Coupon{code='%s', discount=%.1f%%, minOrder=%.2f, used=%d/%s}", this.code, this.discountPercent, this.minOrderAmount, this.timesUsed, this.maxUses < 0 ? "∞" : String.valueOf(this.maxUses));
    }
}
