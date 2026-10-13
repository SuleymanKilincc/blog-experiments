// Reconstructed from obfuscated class "k": a product with price, available stock and units sold.
// Stock is reserved when an item is added to an order and returned if the order is cancelled.
public class Product {
    private final String id;
    private String name;
    private String categoryId;
    private double price;
    private int stock;
    private int unitsSold;

    public Product(String id, String name, String categoryId, double price, int initialStock) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Ürün ID'si boş olamaz");
        }
        if (price < 0.0) {
            throw new IllegalArgumentException("Fiyat negatif olamaz: " + price);
        }
        if (initialStock < 0) {
            throw new IllegalArgumentException("Başlangıç stoku negatif olamaz: " + initialStock);
        }
        this.id = id;
        this.name = name;
        this.categoryId = categoryId;
        this.price = price;
        this.stock = initialStock;
        this.unitsSold = 0;
    }

    public String id() {
        return this.id;
    }

    public String name() {
        return this.name;
    }

    public String categoryId() {
        return this.categoryId;
    }

    public double price() {
        return this.price;
    }

    public int stock() {
        return this.stock;
    }

    public int unitsSold() {
        return this.unitsSold;
    }

    public void rename(String name) {
        this.name = name;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public void setPrice(double price) {
        if (price < 0.0) {
            throw new IllegalArgumentException("Fiyat negatif olamaz: " + price);
        }
        this.price = price;
    }

    public boolean reserve(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Rezervasyon miktarı pozitif olmalı");
        }
        if (this.stock < quantity) {
            return false;
        }
        this.stock -= quantity;
        return true;
    }

    public void release(int quantity) {
        if (quantity > 0) {
            this.stock += quantity;
        }
    }

    public void recordSale(int quantity) {
        if (quantity > 0) {
            this.unitsSold += quantity;
        }
    }

    public void restock(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Yenileme miktarı pozitif olmalı");
        }
        this.stock += quantity;
    }

    public String toRow() {
        return String.format("%-10s | %-25s | %-12s | %10.2f | %6d | %9d", this.id, this.name, this.categoryId, this.price, this.stock, this.unitsSold);
    }

    @Override
    public String toString() {
        return String.format("Product{id='%s', name='%s', cat='%s', price=%.2f, stock=%d}", this.id, this.name, this.categoryId, this.price, this.stock);
    }
}
