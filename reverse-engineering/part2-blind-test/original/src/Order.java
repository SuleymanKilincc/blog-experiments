import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a customer order.
 *
 * State machine:
 *   OPEN ──► FINALIZED   (when {@link #markFinalized()} is called)
 *   OPEN ──► CANCELLED   (when {@link #markCancelled()} is called)
 *
 * The financial summary fields ({@code subtotalBeforeDiscounts}, {@code grandTotal} etc.)
 * are filled in only by {@link DiscountEngine} through {@link #setFinancials}.
 * For OPEN orders these fields are 0.0.
 *
 * The same product can be added to an order only once.
 */
public class Order {

    private final String        orderId;
    private final String        customerId;
    private OrderStatus         status;
    private final List<OrderItem> items;
    private String              appliedCouponCode; // null = no coupon

    // --- Financial summary (filled in by DiscountEngine) ---------------------
    private double subtotalBeforeDiscounts;   // raw total before discounts
    private double itemDiscountTotal;          // savings from item discounts
    private double tierDiscountPct;            // tier discount percentage applied
    private double tierDiscountAmount;         // tier discount amount
    private double loyaltyDiscountPct;         // loyalty discount percentage applied
    private double loyaltyDiscountAmount;      // loyalty discount amount
    private double couponDiscountPct;          // coupon discount percentage applied
    private double couponDiscountAmount;       // coupon discount amount
    private double grandTotal;                 // final amount the customer has to pay

    // -------------------------------------------------------------------------
    // Constructor
    // -------------------------------------------------------------------------

    /**
     * @param orderId    Unique order identifier
     * @param customerId Identifier of the customer placing this order
     */
    public Order(String orderId, String customerId) {
        if (orderId == null || orderId.isBlank())
            throw new IllegalArgumentException("Sipariş ID'si boş olamaz");
        if (customerId == null || customerId.isBlank())
            throw new IllegalArgumentException("Müşteri ID'si boş olamaz");
        this.orderId           = orderId;
        this.customerId        = customerId;
        this.status            = OrderStatus.OPEN;
        this.items             = new ArrayList<>();
        this.appliedCouponCode = null;
    }

    // -------------------------------------------------------------------------
    // Getters
    // -------------------------------------------------------------------------

    public String         getOrderId()    { return orderId; }
    public String         getCustomerId() { return customerId; }
    public OrderStatus    getStatus()     { return status; }
    public String         getAppliedCouponCode() { return appliedCouponCode; }
    public List<OrderItem> getItems()     { return Collections.unmodifiableList(items); }

    // Financial summary getters
    public double getSubtotalBeforeDiscounts() { return subtotalBeforeDiscounts; }
    public double getItemDiscountTotal()        { return itemDiscountTotal; }
    public double getTierDiscountPct()          { return tierDiscountPct; }
    public double getTierDiscountAmount()       { return tierDiscountAmount; }
    public double getLoyaltyDiscountPct()       { return loyaltyDiscountPct; }
    public double getLoyaltyDiscountAmount()    { return loyaltyDiscountAmount; }
    public double getCouponDiscountPct()        { return couponDiscountPct; }
    public double getCouponDiscountAmount()     { return couponDiscountAmount; }
    public double getGrandTotal()               { return grandTotal; }

    /** Total savings = raw total before discounts − amount paid. */
    public double getTotalSavings() { return subtotalBeforeDiscounts - grandTotal; }

    // Status query helpers
    public boolean isOpen()      { return status == OrderStatus.OPEN; }
    public boolean isFinalized() { return status == OrderStatus.FINALIZED; }
    public boolean isCancelled() { return status == OrderStatus.CANCELLED; }

    // -------------------------------------------------------------------------
    // Mutation methods
    // -------------------------------------------------------------------------

    /**
     * Adds a new item to the order.
     * May only be called while the order is OPEN.
     *
     * @param item The order item to add
     */
    public void addItem(OrderItem item) {
        if (item == null) throw new IllegalArgumentException("Sipariş kalemi null olamaz");
        items.add(item);
    }

    /**
     * Sets the coupon code to apply to this order.
     * May only be called while the order is OPEN.
     *
     * @param couponCode Coupon code (null removes the current coupon)
     */
    public void setCoupon(String couponCode) {
        this.appliedCouponCode = couponCode;
    }

    /**
     * Called by DiscountEngine: sets every value of the financial summary.
     */
    public void setFinancials(double subtotal,   double itemDisc,
                              double tierPct,    double tierAmt,
                              double loyaltyPct, double loyaltyAmt,
                              double couponPct,  double couponAmt,
                              double grand) {
        this.subtotalBeforeDiscounts = subtotal;
        this.itemDiscountTotal       = itemDisc;
        this.tierDiscountPct         = tierPct;
        this.tierDiscountAmount      = tierAmt;
        this.loyaltyDiscountPct      = loyaltyPct;
        this.loyaltyDiscountAmount   = loyaltyAmt;
        this.couponDiscountPct       = couponPct;
        this.couponDiscountAmount    = couponAmt;
        this.grandTotal              = grand;
    }

    /** Marks the order as FINALIZED. */
    public void markFinalized() { this.status = OrderStatus.FINALIZED; }

    /** Marks the order as CANCELLED. */
    public void markCancelled() { this.status = OrderStatus.CANCELLED; }

    // -------------------------------------------------------------------------
    // Helper queries
    // -------------------------------------------------------------------------

    /**
     * Checks whether a given product is already in this order.
     * The same product cannot be added to an order twice.
     */
    public boolean hasProduct(String productId) {
        return items.stream().anyMatch(i -> i.getProductId().equals(productId));
    }

    /** Returns the total number of units across all items of the order. */
    public int getTotalItemCount() {
        return items.stream().mapToInt(OrderItem::getQuantity).sum();
    }

    // -------------------------------------------------------------------------
    // Formatting
    // -------------------------------------------------------------------------

    @Override
    public String toString() {
        return String.format("Order{id='%s', customer='%s', status=%s, items=%d, total=%.2f}",
                orderId, customerId, status.getDisplayName(), items.size(), grandTotal);
    }
}
