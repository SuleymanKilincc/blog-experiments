// Reconstructed from obfuscated class "a": a product category with its own discount, plus a
// "bulk" rule (spend threshold and extra percent) that is stored and listed but never priced.
public class Category {
    private final String id;
    private String name;
    private double discountPercent;
    private double bulkThreshold;
    private double bulkExtraPercent;

    public Category(String id, String name, double discountPercent) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Kategori ID'si boş olamaz");
        }
        if (discountPercent < 0.0 || discountPercent > 100.0) {
            throw new IllegalArgumentException("İndirim yüzdesi 0-100 arasında olmalı: " + discountPercent);
        }
        this.id = id;
        this.name = name;
        this.discountPercent = discountPercent;
        this.bulkThreshold = 0.0;
        this.bulkExtraPercent = 0.0;
    }

    public String id() {
        return this.id;
    }

    public String name() {
        return this.name;
    }

    public double discountPercent() {
        return this.discountPercent;
    }

    public double bulkThreshold() {
        return this.bulkThreshold;
    }

    public double bulkExtraPercent() {
        return this.bulkExtraPercent;
    }

    public void rename(String name) {
        this.name = name;
    }

    public void setDiscountPercent(double discountPercent) {
        if (discountPercent < 0.0 || discountPercent > 100.0) {
            throw new IllegalArgumentException("İndirim yüzdesi 0-100 arasında olmalı: " + discountPercent);
        }
        this.discountPercent = discountPercent;
    }

    public void setBulkDiscount(double threshold, double extraPercent) {
        if (threshold < 0.0) {
            throw new IllegalArgumentException("Eşik tutarı negatif olamaz");
        }
        if (extraPercent < 0.0 || extraPercent > 100.0) {
            throw new IllegalArgumentException("Ek indirim yüzdesi 0-100 arasında olmalı");
        }
        this.bulkThreshold = threshold;
        this.bulkExtraPercent = extraPercent;
    }

    public String toRow() {
        String bulk = this.bulkThreshold > 0.0 ? String.format("%.2f → +%.1f%%", this.bulkThreshold, this.bulkExtraPercent) : "yok";
        return String.format("%-12s | %-20s | %7.1f%% | %s", this.id, this.name, this.discountPercent, bulk);
    }

    @Override
    public String toString() {
        return String.format("Category{id='%s', name='%s', discount=%.1f%%}", this.id, this.name, this.discountPercent);
    }
}
