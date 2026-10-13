import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a store customer.
 *
 * Loyalty bonus logic:
 *   Once a customer has completed 5 or more orders, every order after that
 *   gets an extra 2% loyalty discount.
 *   {@link DiscountEngine} queries this.
 *
 * For customer tiers see {@link CustomerTier}.
 */
public class Customer {

    private final String       id;
    private String             name;
    private CustomerTier       tier;
    private final List<String> completedOrderIds; // IDs of completed orders
    private double             totalSpent;        // all-time total spend

    // -------------------------------------------------------------------------
    // Constructor
    // -------------------------------------------------------------------------

    /**
     * @param id   Unique customer identifier
     * @param name Customer's full name (must not contain spaces)
     * @param tier Starting loyalty tier
     */
    public Customer(String id, String name, CustomerTier tier) {
        if (id == null || id.isBlank())
            throw new IllegalArgumentException("Müşteri ID'si boş olamaz");
        if (tier == null)
            throw new IllegalArgumentException("Müşteri katmanı null olamaz");
        this.id                = id;
        this.name              = name;
        this.tier              = tier;
        this.completedOrderIds = new ArrayList<>();
        this.totalSpent        = 0.0;
    }

    // -------------------------------------------------------------------------
    // Getters
    // -------------------------------------------------------------------------

    public String       getId()                  { return id; }
    public String       getName()                { return name; }
    public CustomerTier getTier()                { return tier; }
    public int          getCompletedOrderCount() { return completedOrderIds.size(); }
    public double       getTotalSpent()          { return totalSpent; }

    /** Unmodifiable list of completed order IDs. */
    public List<String> getCompletedOrderIds() {
        return Collections.unmodifiableList(completedOrderIds);
    }

    // -------------------------------------------------------------------------
    // Setters
    // -------------------------------------------------------------------------

    public void setName(String name) { this.name = name; }
    public void setTier(CustomerTier tier) {
        if (tier == null) throw new IllegalArgumentException("Katman null olamaz");
        this.tier = tier;
    }

    // -------------------------------------------------------------------------
    // Business logic
    // -------------------------------------------------------------------------

    /**
     * Called when an order is completed.
     * Adds the order ID to the history and updates total spend.
     *
     * @param orderId     ID of the completed order
     * @param amountPaid  Amount paid (grand total)
     */
    public void recordCompletedOrder(String orderId, double amountPaid) {
        if (orderId == null || orderId.isBlank()) return;
        completedOrderIds.add(orderId);
        if (amountPaid > 0) totalSpent += amountPaid;
    }

    /**
     * Returns whether this customer qualifies for the loyalty bonus.
     * Condition: at least 5 completed orders before the current order is finalized
     * (previous orders count, the current one does not).
     *
     * @return if {@code true}, {@link DiscountEngine#LOYALTY_DISCOUNT_PCT} is applied
     */
    public boolean isLoyaltyEligible() {
        return completedOrderIds.size() >= DiscountEngine.LOYALTY_ORDER_THRESHOLD;
    }

    // -------------------------------------------------------------------------
    // Formatting
    // -------------------------------------------------------------------------

    /** Returns the customer formatted as one table row. */
    public String toTableRow() {
        return String.format("%-10s | %-22s | %-10s | %12d | %14.2f",
                id, name, tier.getDisplayName(), completedOrderIds.size(), totalSpent);
    }

    @Override
    public String toString() {
        return String.format("Customer{id='%s', name='%s', tier=%s, orders=%d, spent=%.2f}",
                id, name, tier.getDisplayName(), completedOrderIds.size(), totalSpent);
    }
}
