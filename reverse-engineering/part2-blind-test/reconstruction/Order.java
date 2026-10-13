import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Reconstructed from obfuscated class "h": an order, its items, optional coupon and the price
// breakdown that PricingEngine fills in when the order is finalized.
public class Order {
    private final String id;
    private final String customerId;
    private OrderStatus status;
    private final List<OrderItem> items;
    private String couponCode;
    private double subtotal;
    private double itemSavings;
    private double tierPercent;
    private double tierDiscount;
    private double loyaltyPercent;
    private double loyaltyDiscount;
    private double couponPercent;
    private double couponDiscount;
    private double total;

    public Order(String id, String customerId) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Sipariş ID'si boş olamaz");
        }
        if (customerId == null || customerId.isBlank()) {
            throw new IllegalArgumentException("Müşteri ID'si boş olamaz");
        }
        this.id = id;
        this.customerId = customerId;
        this.status = OrderStatus.OPEN;
        this.items = new ArrayList<>();
        this.couponCode = null;
    }

    public String id() {
        return this.id;
    }

    public String customerId() {
        return this.customerId;
    }

    public OrderStatus status() {
        return this.status;
    }

    public String couponCode() {
        return this.couponCode;
    }

    public List<OrderItem> items() {
        return Collections.unmodifiableList(this.items);
    }

    public double subtotal() {
        return this.subtotal;
    }

    public double itemSavings() {
        return this.itemSavings;
    }

    public double tierPercent() {
        return this.tierPercent;
    }

    public double tierDiscount() {
        return this.tierDiscount;
    }

    public double loyaltyPercent() {
        return this.loyaltyPercent;
    }

    public double loyaltyDiscount() {
        return this.loyaltyDiscount;
    }

    public double couponPercent() {
        return this.couponPercent;
    }

    public double couponDiscount() {
        return this.couponDiscount;
    }

    public double total() {
        return this.total;
    }

    public double totalSavings() {
        return this.subtotal - this.total;
    }

    public boolean isOpen() {
        return this.status == OrderStatus.OPEN;
    }

    public boolean isFinalized() {
        return this.status == OrderStatus.FINALIZED;
    }

    public boolean isCancelled() {
        return this.status == OrderStatus.CANCELLED;
    }

    public void addItem(OrderItem item) {
        if (item == null) {
            throw new IllegalArgumentException("Sipariş kalemi null olamaz");
        }
        this.items.add(item);
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public void setPricing(double subtotal, double itemSavings, double tierPercent, double tierDiscount,
                           double loyaltyPercent, double loyaltyDiscount, double couponPercent,
                           double couponDiscount, double total) {
        this.subtotal = subtotal;
        this.itemSavings = itemSavings;
        this.tierPercent = tierPercent;
        this.tierDiscount = tierDiscount;
        this.loyaltyPercent = loyaltyPercent;
        this.loyaltyDiscount = loyaltyDiscount;
        this.couponPercent = couponPercent;
        this.couponDiscount = couponDiscount;
        this.total = total;
    }

    public void markFinalized() {
        this.status = OrderStatus.FINALIZED;
    }

    public void markCancelled() {
        this.status = OrderStatus.CANCELLED;
    }

    public boolean containsProduct(String productId) {
        return this.items.stream().anyMatch(item -> item.productId().equals(productId));
    }

    public int totalQuantity() {
        return this.items.stream().mapToInt(OrderItem::quantity).sum();
    }

    @Override
    public String toString() {
        return String.format("Order{id='%s', customer='%s', status=%s, items=%d, total=%.2f}", this.id, this.customerId, this.status.label(), this.items.size(), this.total);
    }
}
