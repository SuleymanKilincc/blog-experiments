/**
 * Represents a product category in the store.
 *
 * Each category:
 *  - May have a base discount percentage applied to all of its products.
 *  - Can optionally give an extra "bulk category" discount when a single order goes over
 *    a certain amount in this category (configured with the SET_BULK_DISCOUNT command).
 *
 * Example:
 *   ADD_CATEGORY ELEC Electronics 10
 *   SET_BULK_DISCOUNT ELEC 500 5
 *   → Electronics are 10% off, plus an extra 5% on orders of 500+ TL
 */
public class Category {

    private final String id;
    private String name;
    private double discountPercent;            // 0.0 – 100.0
    private double bulkThresholdAmount;        // 0 = disabled
    private double bulkExtraDiscountPercent;   // extra discount once the threshold is exceeded

    // -------------------------------------------------------------------------
    // Constructor
    // -------------------------------------------------------------------------

    /**
     * Creates a new category.
     *
     * @param id             Unique category identifier
     * @param name           Human-readable name
     * @param discountPercent Base discount percentage (0-100)
     */
    public Category(String id, String name, double discountPercent) {
        if (id == null || id.isBlank())
            throw new IllegalArgumentException("Kategori ID'si boş olamaz");
        if (discountPercent < 0 || discountPercent > 100)
            throw new IllegalArgumentException("İndirim yüzdesi 0-100 arasında olmalı: " + discountPercent);
        this.id = id;
        this.name = name;
        this.discountPercent = discountPercent;
        this.bulkThresholdAmount = 0.0;
        this.bulkExtraDiscountPercent = 0.0;
    }

    // -------------------------------------------------------------------------
    // Getters
    // -------------------------------------------------------------------------

    public String getId()                       { return id; }
    public String getName()                     { return name; }
    public double getDiscountPercent()          { return discountPercent; }
    public double getBulkThresholdAmount()      { return bulkThresholdAmount; }
    public double getBulkExtraDiscountPercent() { return bulkExtraDiscountPercent; }

    // -------------------------------------------------------------------------
    // Setters
    // -------------------------------------------------------------------------

    public void setName(String name) { this.name = name; }

    public void setDiscountPercent(double pct) {
        if (pct < 0 || pct > 100)
            throw new IllegalArgumentException("İndirim yüzdesi 0-100 arasında olmalı: " + pct);
        this.discountPercent = pct;
    }

    /**
     * Configures a bulk order discount for this category.
     *
     * @param threshold      Minimum order amount at which the extra discount kicks in
     * @param extraDiscountPct Extra discount percentage applied once the threshold is exceeded
     */
    public void setBulkDiscount(double threshold, double extraDiscountPct) {
        if (threshold < 0)
            throw new IllegalArgumentException("Eşik tutarı negatif olamaz");
        if (extraDiscountPct < 0 || extraDiscountPct > 100)
            throw new IllegalArgumentException("Ek indirim yüzdesi 0-100 arasında olmalı");
        this.bulkThresholdAmount = threshold;
        this.bulkExtraDiscountPercent = extraDiscountPct;
    }

    // -------------------------------------------------------------------------
    // Helper methods
    // -------------------------------------------------------------------------

    /** Returns the category formatted as a table row. */
    public String toTableRow() {
        String bulkInfo = (bulkThresholdAmount > 0)
                ? String.format("%.2f → +%.1f%%", bulkThresholdAmount, bulkExtraDiscountPercent)
                : "yok";
        return String.format("%-12s | %-20s | %7.1f%% | %s",
                id, name, discountPercent, bulkInfo);
    }

    @Override
    public String toString() {
        return String.format("Category{id='%s', name='%s', discount=%.1f%%}",
                id, name, discountPercent);
    }
}
