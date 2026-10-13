// Reconstructed from obfuscated class "b": parses one input line ("COMMAND arg arg ...") and
// dispatches it to Store or ReportGenerator, turning bad input into an ERROR line.
public class CommandProcessor {
    private final Store store;
    private final ReportGenerator reports;

    public CommandProcessor(Store store) {
        this.store = store;
        this.reports = new ReportGenerator(store);
    }

    public void process(String line) {
        String[] args = line.trim().split("\\s+");
        if (args.length == 0) {
            return;
        }
        String command = args[0].toUpperCase();
        try {
            System.out.println(this.execute(command, args));
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("ERROR: Eksik argüman — komut: " + command);
        } catch (NumberFormatException e) {
            System.out.println("ERROR: Geçersiz sayı formatı: " + line);
        } catch (IllegalArgumentException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private String execute(String command, String[] args) {
        return switch (command) {
            case "ADD_CATEGORY" -> this.store.addCategory(args[1], args[2], Double.parseDouble(args[3]));
            case "SET_BULK_DISCOUNT" -> this.store.setBulkDiscount(args[1], Double.parseDouble(args[2]), Double.parseDouble(args[3]));
            case "ADD_PRODUCT" -> this.store.addProduct(args[1], args[2], args[3], Double.parseDouble(args[4]), Integer.parseInt(args[5]));
            case "RESTOCK" -> this.store.restock(args[1], Integer.parseInt(args[2]));
            case "UPDATE_PRICE" -> this.store.updatePrice(args[1], Double.parseDouble(args[2]));
            case "ADD_CUSTOMER" -> this.store.addCustomer(args[1], args[2], CustomerTier.parse(args[3]));
            case "UPGRADE_TIER" -> this.store.changeTier(args[1], CustomerTier.parse(args[2]));
            case "ADD_COUPON" -> this.store.addCoupon(args[1], Double.parseDouble(args[2]), Double.parseDouble(args[3]));
            case "ADD_COUPON_LIMITED" -> this.store.addLimitedCoupon(args[1], Double.parseDouble(args[2]), Double.parseDouble(args[3]), Integer.parseInt(args[4]));
            case "CREATE_ORDER" -> this.store.createOrder(args[1], args[2]);
            case "ADD_ITEM" -> this.store.addItem(args[1], args[2], Integer.parseInt(args[3]));
            case "APPLY_COUPON" -> this.store.applyCoupon(args[1], args[2]);
            case "FINALIZE_ORDER" -> this.store.finalizeOrder(args[1]);
            case "CANCEL_ORDER" -> this.store.cancelOrder(args[1]);
            case "LIST_CATEGORIES" -> this.reports.listCategories();
            case "LIST_PRODUCTS" -> this.reports.listProducts();
            case "LIST_CUSTOMERS" -> this.reports.listCustomers();
            case "LIST_ORDERS" -> this.reports.listOrders();
            case "LIST_COUPONS" -> this.reports.listCoupons();
            case "ORDER_DETAIL" -> this.reports.orderDetail(args[1]);
            case "CUSTOMER_REPORT" -> this.reports.customerReport(args[1]);
            case "STOCK_REPORT" -> this.reports.stockReport();
            default -> "ERROR: Bilinmeyen komut: " + command;
        };
    }
}
