/**
 * The states an order can be in during its life cycle.
 *
 * State machine:
 *   OPEN ──► FINALIZED   (via the FINALIZE_ORDER command)
 *   OPEN ──► CANCELLED   (via the CANCEL_ORDER command)
 *
 * FINALIZED and CANCELLED are terminal states; no further transitions are possible.
 */
public enum OrderStatus {

    OPEN      ("Open"),
    FINALIZED ("Finalized"),
    CANCELLED ("Cancelled");

    private final String displayName;

    OrderStatus(String displayName) {
        this.displayName = displayName;
    }

    /** Status name shown to the user. */
    public String getDisplayName() { return displayName; }
}
