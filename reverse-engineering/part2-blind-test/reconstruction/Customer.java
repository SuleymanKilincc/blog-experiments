import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Reconstructed from obfuscated class "d": a customer, their tier, completed orders and spend.
public class Customer {
    private final String id;
    private String name;
    private CustomerTier tier;
    private final List<String> completedOrderIds;
    private double totalSpent;

    public Customer(String id, String name, CustomerTier tier) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Müşteri ID'si boş olamaz");
        }
        if (tier == null) {
            throw new IllegalArgumentException("Müşteri katmanı null olamaz");
        }
        this.id = id;
        this.name = name;
        this.tier = tier;
        this.completedOrderIds = new ArrayList<>();
        this.totalSpent = 0.0;
    }

    public String id() {
        return this.id;
    }

    public String name() {
        return this.name;
    }

    public CustomerTier tier() {
        return this.tier;
    }

    public int completedOrderCount() {
        return this.completedOrderIds.size();
    }

    public double totalSpent() {
        return this.totalSpent;
    }

    public List<String> completedOrderIds() {
        return Collections.unmodifiableList(this.completedOrderIds);
    }

    public void rename(String name) {
        this.name = name;
    }

    public void setTier(CustomerTier tier) {
        if (tier == null) {
            throw new IllegalArgumentException("Katman null olamaz");
        }
        this.tier = tier;
    }

    public void recordCompletedOrder(String orderId, double amountPaid) {
        if (orderId == null || orderId.isBlank()) {
            return;
        }
        this.completedOrderIds.add(orderId);
        if (amountPaid > 0.0) {
            this.totalSpent += amountPaid;
        }
    }

    // Loyalty bonus kicks in once five orders have been completed.
    public boolean isLoyal() {
        return this.completedOrderIds.size() >= 5;
    }

    public String toRow() {
        return String.format("%-10s | %-22s | %-10s | %12d | %14.2f", this.id, this.name, this.tier.label(), this.completedOrderIds.size(), this.totalSpent);
    }

    @Override
    public String toString() {
        return String.format("Customer{id='%s', name='%s', tier=%s, orders=%d, spent=%.2f}", this.id, this.name, this.tier.label(), this.completedOrderIds.size(), this.totalSpent);
    }
}
