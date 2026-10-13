/**
 * Reports store data as formatted text tables.
 *
 * Every method returns a string; the output is written to stdout through
 * {@link CommandInterpreter}.
 *
 * Report types:
 *   listCategories()   – All categories and their discounts
 *   listProducts()     – All products with price and stock
 *   listCustomers()    – All customers with an order/spend summary
 *   listOrders()       – Summary table of all orders
 *   listCoupons()      – All coupons and their usage
 *   orderDetail()      – Detailed discount breakdown for one order
 *   customerReport()   – Order history for one customer
 *   stockReport()      – Stock status (IN-STOCK / LOW-STOCK / OUT-OF-STOCK)
 */
public class ReportGenerator {

    /** Threshold for the low-stock warning. */
    private static final int LOW_STOCK_THRESHOLD = 5;

    private final Store store;

    public ReportGenerator(Store store) {
        this.store = store;
    }

    // =========================================================================
    // LIST REPORTS
    // =========================================================================

    /** Lists all categories as a table. */
    public String listCategories() {
        StringBuilder sb = new StringBuilder();
        appendLine(sb, "═══════════════════ KATEGORİLER ═══════════════════");
        appendLine(sb, String.format("%-12s | %-20s | %8s | %s",
                "ID", "Ad", "İndirim", "Toplu İndirim"));
        appendLine(sb, "─".repeat(65));
        if (store.getAllCategories().isEmpty()) {
            appendLine(sb, "  (henüz kategori yok)");
        } else {
            for (Category cat : store.getAllCategories()) {
                appendLine(sb, cat.toTableRow());
            }
        }
        appendLine(sb, "═".repeat(65));
        return sb.toString().stripTrailing();
    }

    /** Lists all products as a table. */
    public String listProducts() {
        StringBuilder sb = new StringBuilder();
        appendLine(sb, "════════════════════════════ ÜRÜNLER ════════════════════════════");
        appendLine(sb, String.format("%-10s | %-25s | %-12s | %10s | %6s | %9s",
                "ID", "Ad", "Kategori", "Fiyat", "Stok", "Satılan"));
        appendLine(sb, "─".repeat(85));
        if (store.getAllProducts().isEmpty()) {
            appendLine(sb, "  (henüz ürün yok)");
        } else {
            for (Product p : store.getAllProducts()) {
                appendLine(sb, p.toTableRow());
            }
        }
        appendLine(sb, "═".repeat(85));
        return sb.toString().stripTrailing();
    }

    /** Lists all customers as a table. */
    public String listCustomers() {
        StringBuilder sb = new StringBuilder();
        appendLine(sb, "══════════════════════════ MÜŞTERİLER ══════════════════════════");
        appendLine(sb, String.format("%-10s | %-22s | %-10s | %12s | %14s",
                "ID", "Ad", "Katman", "Sipariş", "Harcama"));
        appendLine(sb, "─".repeat(80));
        if (store.getAllCustomers().isEmpty()) {
            appendLine(sb, "  (henüz müşteri yok)");
        } else {
            for (Customer c : store.getAllCustomers()) {
                appendLine(sb, c.toTableRow());
            }
        }
        appendLine(sb, "═".repeat(80));
        return sb.toString().stripTrailing();
    }

    /** Lists all orders as a summary table. */
    public String listOrders() {
        StringBuilder sb = new StringBuilder();
        appendLine(sb, "══════════════════════ SİPARİŞLER ══════════════════════");
        appendLine(sb, String.format("%-12s | %-10s | %-11s | %6s | %12s",
                "Sipariş ID", "Müşteri", "Durum", "Adet", "Toplam"));
        appendLine(sb, "─".repeat(60));
        if (store.getAllOrders().isEmpty()) {
            appendLine(sb, "  (henüz sipariş yok)");
        } else {
            for (Order o : store.getAllOrders()) {
                appendLine(sb, String.format("%-12s | %-10s | %-11s | %6d | %12.2f",
                        o.getOrderId(), o.getCustomerId(),
                        o.getStatus().getDisplayName(),
                        o.getTotalItemCount(), o.getGrandTotal()));
            }
        }
        appendLine(sb, "═".repeat(60));
        return sb.toString().stripTrailing();
    }

    /** Lists all coupons and how often they have been used. */
    public String listCoupons() {
        StringBuilder sb = new StringBuilder();
        appendLine(sb, "════════════════════════ KUPONLAR ════════════════════════");
        appendLine(sb, String.format("%-14s | %8s | %12s | %10s | %s",
                "Kod", "İndirim", "Min Tutar", "Limit", "Kullanım"));
        appendLine(sb, "─".repeat(63));
        if (store.getAllCoupons().isEmpty()) {
            appendLine(sb, "  (henüz kupon yok)");
        } else {
            for (Coupon c : store.getAllCoupons()) {
                appendLine(sb, c.toTableRow());
            }
        }
        appendLine(sb, "═".repeat(63));
        return sb.toString().stripTrailing();
    }

    // =========================================================================
    // DETAIL REPORTS
    // =========================================================================

    /**
     * Shows the detailed discount breakdown of the given order.
     *
     * @param orderId ID of the order to report on
     */
    public String orderDetail(String orderId) {
        Order order = store.getOrder(orderId);
        if (order == null) return "ERROR: Sipariş bulunamadı: " + orderId;

        StringBuilder sb = new StringBuilder();
        appendLine(sb, "══════ SİPARİŞ DETAYI: " + orderId + " ══════");
        appendLine(sb, "Müşteri  : " + order.getCustomerId());
        appendLine(sb, "Durum    : " + order.getStatus().getDisplayName());
        if (order.getAppliedCouponCode() != null)
            appendLine(sb, "Kupon    : " + order.getAppliedCouponCode());
        appendLine(sb, "");

        // Item table
        appendLine(sb, String.format("  %-10s | %-25s | %4s | %10s | %7s | %7s | %11s",
                "Ürün ID", "Ad", "Adet", "BirimFiyat", "KatIndir", "TplIndir", "Subtotal"));
        appendLine(sb, "  " + "─".repeat(83));

        if (order.getItems().isEmpty()) {
            appendLine(sb, "  (sipariş boş)");
        } else {
            for (OrderItem item : order.getItems()) {
                appendLine(sb, item.toTableRow());
            }
        }

        // Financial summary (only meaningful for finalized or cancelled orders)
        if (order.isFinalized() || (!order.getItems().isEmpty() && order.isOpen())) {
            appendLine(sb, "");
            double afterItems = order.getSubtotalBeforeDiscounts() - order.getItemDiscountTotal();
            appendLine(sb, String.format("  %-38s: %12.2f",
                    "İndirim öncesi ara toplam", order.getSubtotalBeforeDiscounts()));
            appendLine(sb, String.format("  %-38s: %12.2f",
                    "Kalem indirimi tasarrufu", order.getItemDiscountTotal()));
            appendLine(sb, String.format("  %-38s: %12.2f",
                    "Kalem indirimleri sonrası", afterItems));

            if (order.getTierDiscountPct() > 0) {
                appendLine(sb, String.format("  %-38s: %12.2f",
                        String.format("Müşteri katmanı (%%%.1f)", order.getTierDiscountPct()),
                        -order.getTierDiscountAmount()));
            }
            if (order.getLoyaltyDiscountPct() > 0) {
                appendLine(sb, String.format("  %-38s: %12.2f",
                        String.format("Sadakat bonusu (%%%.1f)", order.getLoyaltyDiscountPct()),
                        -order.getLoyaltyDiscountAmount()));
            }
            if (order.getCouponDiscountPct() > 0) {
                appendLine(sb, String.format("  %-38s: %12.2f",
                        String.format("Kupon indirimi (%%%.1f)", order.getCouponDiscountPct()),
                        -order.getCouponDiscountAmount()));
            }
            appendLine(sb, "  " + "═".repeat(53));
            appendLine(sb, String.format("  %-38s: %12.2f",
                    "ÖDENECEK TOPLAM", order.getGrandTotal()));
            appendLine(sb, String.format("  %-38s: %12.2f",
                    "Toplam tasarruf", order.getTotalSavings()));
        }
        appendLine(sb, "═".repeat(55));
        return sb.toString().stripTrailing();
    }

    /**
     * Shows the profile summary and order history of the given customer.
     *
     * @param customerId ID of the customer to report on
     */
    public String customerReport(String customerId) {
        Customer customer = store.getCustomer(customerId);
        if (customer == null) return "ERROR: Müşteri bulunamadı: " + customerId;

        StringBuilder sb = new StringBuilder();
        appendLine(sb, "══════ MÜŞTERİ RAPORU: " + customerId + " ══════");
        appendLine(sb, "Ad               : " + customer.getName());
        appendLine(sb, "Katman           : " + customer.getTier().getDisplayName()
                + String.format(" (%%%.0f indirim)", customer.getTier().getDiscountPercent()));
        appendLine(sb, "Tamamlanan sipariş: " + customer.getCompletedOrderCount());
        appendLine(sb, String.format("Toplam harcama   : %.2f", customer.getTotalSpent()));
        appendLine(sb, "Sadakat bonusu   : " + (customer.isLoyaltyEligible()
                ? "AKTIF (%2 ek indirim)" : "pasif (gerekli: "
                + DiscountEngine.LOYALTY_ORDER_THRESHOLD + " sipariş)"));
        appendLine(sb, "");
        appendLine(sb, "Sipariş Geçmişi:");
        appendLine(sb, "─".repeat(55));
        if (customer.getCompletedOrderIds().isEmpty()) {
            appendLine(sb, "  (henüz tamamlanmış sipariş yok)");
        } else {
            appendLine(sb, String.format("  %-12s | %6s | %10s | %12s",
                    "Sipariş", "Ürün", "Ham Tutar", "Ödenen"));
            appendLine(sb, "  " + "─".repeat(50));
            for (String oid : customer.getCompletedOrderIds()) {
                Order o = store.getOrder(oid);
                if (o != null) {
                    appendLine(sb, String.format("  %-12s | %6d | %10.2f | %12.2f",
                            oid, o.getTotalItemCount(),
                            o.getSubtotalBeforeDiscounts(), o.getGrandTotal()));
                }
            }
        }
        appendLine(sb, "═".repeat(55));
        return sb.toString().stripTrailing();
    }

    /**
     * Produces a stock status report for all products.
     * A label is assigned by stock level:
     *   OUT-OF-STOCK : stock = 0
     *   LOW-STOCK    : stock < LOW_STOCK_THRESHOLD (default 5)
     *   IN-STOCK     : stock ≥ LOW_STOCK_THRESHOLD
     */
    public String stockReport() {
        StringBuilder sb = new StringBuilder();
        appendLine(sb, "══════════════════ STOK RAPORU ══════════════════");
        appendLine(sb, String.format("%-10s | %-25s | %6s | %9s | %13s",
                "Ürün ID", "Ad", "Stok", "Satılan", "Durum"));
        appendLine(sb, "─".repeat(72));

        if (store.getAllProducts().isEmpty()) {
            appendLine(sb, "  (henüz ürün yok)");
        } else {
            int outOfStock = 0, lowStock = 0, inStock = 0;
            for (Product p : store.getAllProducts()) {
                String status;
                if (p.getStock() == 0) {
                    status = "OUT-OF-STOCK";
                    outOfStock++;
                } else if (p.getStock() < LOW_STOCK_THRESHOLD) {
                    status = "LOW-STOCK";
                    lowStock++;
                } else {
                    status = "IN-STOCK";
                    inStock++;
                }
                appendLine(sb, String.format("%-10s | %-25s | %6d | %9d | %s",
                        p.getId(), p.getName(), p.getStock(), p.getTotalSold(), status));
            }
            appendLine(sb, "─".repeat(72));
            appendLine(sb, String.format("Özet: IN-STOCK=%d  LOW-STOCK=%d  OUT-OF-STOCK=%d",
                    inStock, lowStock, outOfStock));
        }
        appendLine(sb, "═".repeat(72));
        return sb.toString().stripTrailing();
    }

    // =========================================================================
    // HELPERS
    // =========================================================================

    private static void appendLine(StringBuilder sb, String line) {
        sb.append(line).append("\n");
    }
}
