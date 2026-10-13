/**
 * Command-line interpreter.
 *
 * Every line has this format:
 *   COMMAND_NAME [arg1] [arg2] ...
 *
 * A space separates arguments, so name fields must use underscores
 * (e.g. "Ahmet_Yilmaz" instead of "Ahmet Yilmaz").
 *
 * Supported commands:
 * ──────────────────────────────────────────────────────────────────
 *  Category:
 *    ADD_CATEGORY      <id> <name> <discountPct>
 *    SET_BULK_DISCOUNT <categoryId> <threshold> <extraPct>
 *
 *  Product:
 *    ADD_PRODUCT   <id> <name> <categoryId> <price> <stock>
 *    RESTOCK       <productId> <quantity>
 *    UPDATE_PRICE  <productId> <newPrice>
 *
 *  Customer:
 *    ADD_CUSTOMER  <id> <name> <tier>      (tier: REGULAR/SILVER/GOLD/PLATINUM)
 *    UPGRADE_TIER  <customerId> <newTier>
 *
 *  Coupon:
 *    ADD_COUPON          <code> <discountPct> <minAmount>
 *    ADD_COUPON_LIMITED  <code> <discountPct> <minAmount> <maxUsages>
 *
 *  Order:
 *    CREATE_ORDER   <orderId> <customerId>
 *    ADD_ITEM       <orderId> <productId> <quantity>
 *    APPLY_COUPON   <orderId> <couponCode>
 *    FINALIZE_ORDER <orderId>
 *    CANCEL_ORDER   <orderId>
 *
 *  Lists & reports:
 *    LIST_CATEGORIES
 *    LIST_PRODUCTS
 *    LIST_CUSTOMERS
 *    LIST_ORDERS
 *    LIST_COUPONS
 *    ORDER_DETAIL    <orderId>
 *    CUSTOMER_REPORT <customerId>
 *    STOCK_REPORT
 * ──────────────────────────────────────────────────────────────────
 */
public class CommandInterpreter {

    private final Store           store;
    private final ReportGenerator reporter;

    /**
     * @param store The store all operations are applied to
     */
    public CommandInterpreter(Store store) {
        this.store    = store;
        this.reporter = new ReportGenerator(store);
    }

    /**
     * Parses the given line and calls the matching store/report method.
     * The result is written to stdout.
     *
     * @param line A single command line (trimmed, not a comment and not empty)
     */
    public void execute(String line) {
        String[] parts = line.trim().split("\\s+");
        if (parts.length == 0) return;
        String cmd = parts[0].toUpperCase();

        try {
            String result = dispatch(cmd, parts);
            System.out.println(result);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("ERROR: Eksik argüman — komut: " + cmd);
        } catch (NumberFormatException e) {
            System.out.println("ERROR: Geçersiz sayı formatı: " + line);
        } catch (IllegalArgumentException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    // -------------------------------------------------------------------------
    // Command dispatch
    // -------------------------------------------------------------------------

    private String dispatch(String cmd, String[] p) {
        return switch (cmd) {

            // ── Category ─────────────────────────────────────────────────────
            case "ADD_CATEGORY" ->
                    store.addCategory(p[1], p[2], Double.parseDouble(p[3]));

            case "SET_BULK_DISCOUNT" ->
                    store.setBulkDiscount(p[1], Double.parseDouble(p[2]), Double.parseDouble(p[3]));

            // ── Product ──────────────────────────────────────────────────────
            case "ADD_PRODUCT" ->
                    store.addProduct(p[1], p[2], p[3],
                            Double.parseDouble(p[4]), Integer.parseInt(p[5]));

            case "RESTOCK" ->
                    store.restockProduct(p[1], Integer.parseInt(p[2]));

            case "UPDATE_PRICE" ->
                    store.updatePrice(p[1], Double.parseDouble(p[2]));

            // ── Customer ─────────────────────────────────────────────────────
            case "ADD_CUSTOMER" ->
                    store.addCustomer(p[1], p[2], CustomerTier.fromString(p[3]));

            case "UPGRADE_TIER" ->
                    store.upgradeTier(p[1], CustomerTier.fromString(p[2]));

            // ── Coupon ───────────────────────────────────────────────────────
            case "ADD_COUPON" ->
                    store.addCoupon(p[1], Double.parseDouble(p[2]), Double.parseDouble(p[3]));

            case "ADD_COUPON_LIMITED" ->
                    store.addCouponWithLimit(p[1], Double.parseDouble(p[2]),
                            Double.parseDouble(p[3]), Integer.parseInt(p[4]));

            // ── Order ────────────────────────────────────────────────────────
            case "CREATE_ORDER" ->
                    store.createOrder(p[1], p[2]);

            case "ADD_ITEM" ->
                    store.addItem(p[1], p[2], Integer.parseInt(p[3]));

            case "APPLY_COUPON" ->
                    store.applyCoupon(p[1], p[2]);

            case "FINALIZE_ORDER" ->
                    store.finalizeOrder(p[1]);

            case "CANCEL_ORDER" ->
                    store.cancelOrder(p[1]);

            // ── Lists ────────────────────────────────────────────────────────
            case "LIST_CATEGORIES" -> reporter.listCategories();
            case "LIST_PRODUCTS"   -> reporter.listProducts();
            case "LIST_CUSTOMERS"  -> reporter.listCustomers();
            case "LIST_ORDERS"     -> reporter.listOrders();
            case "LIST_COUPONS"    -> reporter.listCoupons();

            // ── Reports ──────────────────────────────────────────────────────
            case "ORDER_DETAIL"    -> reporter.orderDetail(p[1]);
            case "CUSTOMER_REPORT" -> reporter.customerReport(p[1]);
            case "STOCK_REPORT"    -> reporter.stockReport();

            default -> "ERROR: Bilinmeyen komut: " + cmd;
        };
    }
}
