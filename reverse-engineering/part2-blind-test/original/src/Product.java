/**
 * Represents a product sold in the store.
 *
 * Stock tracking happens in stages:
 *   1. {@link #reserveStock(int)}  – Stock is deducted when an item is added to an order.
 *   2. {@link #confirmSale(int)}   – totalSold is incremented when the order is finalized.
 *   3. {@link #releaseStock(int)}  – Stock is returned when the order is cancelled.
 *
 * This way OPEN orders "hold" stock; on cancellation the hold is released
 * automatically.
 */
public class Product {

    private final String id;
    private String name;
    private String categoryId;
    private double basePrice;
    private int    stock;
    private int    totalSold;

    // -------------------------------------------------------------------------
    // Constructor
    // -------------------------------------------------------------------------

    /**
     * @param id         Unique product identifier
     * @param name       Product name (must not contain spaces)
     * @param categoryId The category this product belongs to
     * @param basePrice  Unit price before discounts (≥ 0)
     * @param stock      Starting stock quantity (≥ 0)
     */
    public Product(String id, String name, String categoryId, double basePrice, int stock) {
        if (id == null || id.isBlank())
            throw new IllegalArgumentException("Ürün ID'si boş olamaz");
        if (basePrice < 0)
            throw new IllegalArgumentException("Fiyat negatif olamaz: " + basePrice);
        if (stock < 0)
            throw new IllegalArgumentException("Başlangıç stoku negatif olamaz: " + stock);
        this.id         = id;
        this.name       = name;
        this.categoryId = categoryId;
        this.basePrice  = basePrice;
        this.stock      = stock;
        this.totalSold  = 0;
    }

    // -------------------------------------------------------------------------
    // Getters
    // -------------------------------------------------------------------------

    public String getId()         { return id; }
    public String getName()       { return name; }
    public String getCategoryId() { return categoryId; }
    public double getBasePrice()  { return basePrice; }
    public int    getStock()      { return stock; }
    public int    getTotalSold()  { return totalSold; }

    // -------------------------------------------------------------------------
    // Setters
    // -------------------------------------------------------------------------

    public void setName(String name)        { this.name = name; }
    public void setCategoryId(String catId) { this.categoryId = catId; }

    public void setBasePrice(double price) {
        if (price < 0)
            throw new IllegalArgumentException("Fiyat negatif olamaz: " + price);
        this.basePrice = price;
    }

    // -------------------------------------------------------------------------
    // Stock operations
    // -------------------------------------------------------------------------

    /**
     * Reserves stock for an order (stock is reduced).
     *
     * @param quantity Requested quantity
     * @return {@code true} if there is enough stock, {@code false} otherwise
     * @throws IllegalArgumentException if the quantity is ≤ 0
     */
    public boolean reserveStock(int quantity) {
        if (quantity <= 0)
            throw new IllegalArgumentException("Rezervasyon miktarı pozitif olmalı");
        if (stock < quantity) return false;
        stock -= quantity;
        return true;
    }

    /**
     * Returns stock that was reserved for an order that has been cancelled.
     *
     * @param quantity Quantity to return (nothing happens if ≤ 0)
     */
    public void releaseStock(int quantity) {
        if (quantity > 0) stock += quantity;
    }

    /**
     * Confirms the sale of this product when an order is finalized.
     * Only updates the {@code totalSold} counter; the stock was already
     * deducted in {@link #reserveStock}.
     *
     * @param quantity Quantity sold
     */
    public void confirmSale(int quantity) {
        if (quantity > 0) totalSold += quantity;
    }

    /**
     * Warehouse restock: adds the given quantity to the stock.
     *
     * @param quantity Quantity to add (must be > 0)
     */
    public void addStock(int quantity) {
        if (quantity <= 0)
            throw new IllegalArgumentException("Yenileme miktarı pozitif olmalı");
        stock += quantity;
    }

    // -------------------------------------------------------------------------
    // Formatting
    // -------------------------------------------------------------------------

    /** Returns the product formatted as one table row. */
    public String toTableRow() {
        return String.format("%-10s | %-25s | %-12s | %10.2f | %6d | %9d",
                id, name, categoryId, basePrice, stock, totalSold);
    }

    @Override
    public String toString() {
        return String.format("Product{id='%s', name='%s', cat='%s', price=%.2f, stock=%d}",
                id, name, categoryId, basePrice, stock);
    }
}
