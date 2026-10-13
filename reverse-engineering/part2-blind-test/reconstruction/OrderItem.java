// Reconstructed from obfuscated class "i": one product line in an order. The two discount
// percentages (category and quantity) are applied multiplicatively to the unit price.
public class OrderItem {
    private final String productId;
    private final String productName;
    private final int quantity;
    private final double unitPrice;
    private double discountedUnitPrice;
    private double categoryDiscountPercent;
    private double quantityDiscountPercent;

    public OrderItem(String productId, String productName, int quantity, double unitPrice) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Miktar pozitif olmalı: " + quantity);
        }
        if (unitPrice < 0.0) {
            throw new IllegalArgumentException("Birim fiyat negatif olamaz: " + unitPrice);
        }
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.discountedUnitPrice = unitPrice;
        this.categoryDiscountPercent = 0.0;
        this.quantityDiscountPercent = 0.0;
    }

    public String productId() {
        return this.productId;
    }

    public String productName() {
        return this.productName;
    }

    public int quantity() {
        return this.quantity;
    }

    public double unitPrice() {
        return this.unitPrice;
    }

    public double discountedUnitPrice() {
        return this.discountedUnitPrice;
    }

    public double categoryDiscountPercent() {
        return this.categoryDiscountPercent;
    }

    public double quantityDiscountPercent() {
        return this.quantityDiscountPercent;
    }

    public double lineTotal() {
        return this.discountedUnitPrice * (double) this.quantity;
    }

    public double lineSavings() {
        return (this.unitPrice - this.discountedUnitPrice) * (double) this.quantity;
    }

    public void applyDiscounts(double categoryPercent, double quantityPercent) {
        this.categoryDiscountPercent = categoryPercent;
        this.quantityDiscountPercent = quantityPercent;
        double factor = (1.0 - categoryPercent / 100.0) * (1.0 - quantityPercent / 100.0);
        this.discountedUnitPrice = this.unitPrice * factor;
    }

    public String toRow() {
        return String.format("  %-10s | %-25s | %4d | %10.2f | %6.1f%% | %6.1f%% | %11.2f", this.productId, this.productName, this.quantity, this.unitPrice, this.categoryDiscountPercent, this.quantityDiscountPercent, this.lineTotal());
    }

    @Override
    public String toString() {
        return String.format("OrderItem{product='%s', qty=%d, unitDisc=%.2f, subtotal=%.2f}", this.productId, this.quantity, this.discountedUnitPrice, this.lineTotal());
    }
}
