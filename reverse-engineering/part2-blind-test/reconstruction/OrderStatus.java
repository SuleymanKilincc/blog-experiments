// Reconstructed from obfuscated enum "j": the life cycle of an order.
public enum OrderStatus {
    OPEN("Open"),
    FINALIZED("Finalized"),
    CANCELLED("Cancelled");

    private final String label;

    OrderStatus(String label) {
        this.label = label;
    }

    public String label() {
        return this.label;
    }
}
