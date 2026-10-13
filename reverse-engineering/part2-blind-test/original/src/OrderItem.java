/**
 * Represents a single product line in an order.
 *
 * Item-level discounts:
 *  - Category discount (the base discount of the product's category)
 *  - Bulk discount (an extra 5% when 5 or more units of this product are in the same order)
 *
 * These discounts are set by {@link DiscountEngine} through {@link #applyItemDiscounts}.
 * Order-level discounts (tier, loyalty, coupon) don't affect this class;
 * they are applied directly to the order total.
 */
public class OrderItem {

    private final String productId;
    private final String productName;
    private final int    quantity;
    private final double unitBasePrice;        // unit price before discounts

    // Filled in by DiscountEngine
    private double unitDiscountedPrice;        // unit price after item discounts
    private double categoryDiscountPct;        // category discount percentage applied
    private double bulkDiscountPct;            // bulk discount percentage applied

    // -------------------------------------------------------------------------
    // Constructor
    // -------------------------------------------------------------------------

    /**
     * @param productId    Product identifier
     * @param productName  Product name (copied into the order at this moment)
     * @param quantity     Quantity (> 0)
     * @param unitBasePrice Unit base price (≥ 0)
     */
    public OrderItem(String productId, String productName, int quantity, double unitBasePrice) {
        if (quantity <= 0)
            throw new IllegalArgumentException("Miktar pozitif olmalı: " + quantity);
        if (unitBasePrice < 0)
            throw new IllegalArgumentException("Birim fiyat negatif olamaz: " + unitBasePrice);
        this.productId         = productId;
        this.productName       = productName;
        this.quantity          = quantity;
        this.unitBasePrice     = unitBasePrice;
        this.unitDiscountedPrice = unitBasePrice; // equal until discounts are applied
        this.categoryDiscountPct = 0.0;
        this.bulkDiscountPct     = 0.0;
    }

    // -------------------------------------------------------------------------
    // Getters
    // -------------------------------------------------------------------------

    public String getProductId()           { return productId; }
    public String getProductName()         { return productName; }
    public int    getQuantity()            { return quantity; }
    public double getUnitBasePrice()       { return unitBasePrice; }
    public double getUnitDiscountedPrice() { return unitDiscountedPrice; }
    public double getCategoryDiscountPct() { return categoryDiscountPct; }
    public double getBulkDiscountPct()     { return bulkDiscountPct; }

    /** Line total = discounted unit price × quantity. */
    public double getSubtotal() { return unitDiscountedPrice * quantity; }

    /** Savings on this line = (base - discounted) × quantity. */
    public double getSavings()  { return (unitBasePrice - unitDiscountedPrice) * quantity; }

    // -------------------------------------------------------------------------
    // Mutation (called only by DiscountEngine)
    // -------------------------------------------------------------------------

    /**
     * Applies the item-level discounts and computes the discounted unit price.
     *
     * Order of application: category first, then bulk (multiplicative).
     *
     * @param catPct  Category discount percentage  (0-100)
     * @param bulkPct Bulk discount percentage      (0-100)
     */
    public void applyItemDiscounts(double catPct, double bulkPct) {
        this.categoryDiscountPct  = catPct;
        this.bulkDiscountPct      = bulkPct;
        double multiplier = (1.0 - catPct / 100.0) * (1.0 - bulkPct / 100.0);
        this.unitDiscountedPrice  = unitBasePrice * multiplier;
    }

    // -------------------------------------------------------------------------
    // Formatting
    // -------------------------------------------------------------------------

    /** This line formatted as one row of the order's product table. */
    public String toTableRow() {
        return String.format("  %-10s | %-25s | %4d | %10.2f | %6.1f%% | %6.1f%% | %11.2f",
                productId, productName, quantity, unitBasePrice,
                categoryDiscountPct, bulkDiscountPct, getSubtotal());
    }

    @Override
    public String toString() {
        return String.format("OrderItem{product='%s', qty=%d, unitDisc=%.2f, subtotal=%.2f}",
                productId, quantity, unitDiscountedPrice, getSubtotal());
    }
}
