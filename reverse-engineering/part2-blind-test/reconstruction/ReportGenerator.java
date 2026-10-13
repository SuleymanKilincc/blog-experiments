// Reconstructed from obfuscated class "l": the read-only LIST_*, ORDER_DETAIL, CUSTOMER_REPORT
// and STOCK_REPORT commands. Each returns a multi-line table.
public class ReportGenerator {
    private static final int LOW_STOCK_THRESHOLD = 5;
    private final Store store;

    public ReportGenerator(Store store) {
        this.store = store;
    }

    public String listCategories() {
        StringBuilder out = new StringBuilder();
        line(out, "═══════════════════ KATEGORİLER ═══════════════════");
        line(out, String.format("%-12s | %-20s | %8s | %s", "ID", "Ad", "İndirim", "Toplu İndirim"));
        line(out, "─".repeat(65));
        if (this.store.categories().isEmpty()) {
            line(out, "  (henüz kategori yok)");
        } else {
            for (Category category : this.store.categories()) {
                line(out, category.toRow());
            }
        }
        line(out, "═".repeat(65));
        return out.toString().stripTrailing();
    }

    public String listProducts() {
        StringBuilder out = new StringBuilder();
        line(out, "════════════════════════════ ÜRÜNLER ════════════════════════════");
        line(out, String.format("%-10s | %-25s | %-12s | %10s | %6s | %9s", "ID", "Ad", "Kategori", "Fiyat", "Stok", "Satılan"));
        line(out, "─".repeat(85));
        if (this.store.products().isEmpty()) {
            line(out, "  (henüz ürün yok)");
        } else {
            for (Product product : this.store.products()) {
                line(out, product.toRow());
            }
        }
        line(out, "═".repeat(85));
        return out.toString().stripTrailing();
    }

    public String listCustomers() {
        StringBuilder out = new StringBuilder();
        line(out, "══════════════════════════ MÜŞTERİLER ══════════════════════════");
        line(out, String.format("%-10s | %-22s | %-10s | %12s | %14s", "ID", "Ad", "Katman", "Sipariş", "Harcama"));
        line(out, "─".repeat(80));
        if (this.store.customers().isEmpty()) {
            line(out, "  (henüz müşteri yok)");
        } else {
            for (Customer customer : this.store.customers()) {
                line(out, customer.toRow());
            }
        }
        line(out, "═".repeat(80));
        return out.toString().stripTrailing();
    }

    public String listOrders() {
        StringBuilder out = new StringBuilder();
        line(out, "══════════════════════ SİPARİŞLER ══════════════════════");
        line(out, String.format("%-12s | %-10s | %-11s | %6s | %12s", "Sipariş ID", "Müşteri", "Durum", "Adet", "Toplam"));
        line(out, "─".repeat(60));
        if (this.store.orders().isEmpty()) {
            line(out, "  (henüz sipariş yok)");
        } else {
            for (Order order : this.store.orders()) {
                line(out, String.format("%-12s | %-10s | %-11s | %6d | %12.2f", order.id(), order.customerId(), order.status().label(), order.totalQuantity(), order.total()));
            }
        }
        line(out, "═".repeat(60));
        return out.toString().stripTrailing();
    }

    public String listCoupons() {
        StringBuilder out = new StringBuilder();
        line(out, "════════════════════════ KUPONLAR ════════════════════════");
        line(out, String.format("%-14s | %8s | %12s | %10s | %s", "Kod", "İndirim", "Min Tutar", "Limit", "Kullanım"));
        line(out, "─".repeat(63));
        if (this.store.coupons().isEmpty()) {
            line(out, "  (henüz kupon yok)");
        } else {
            for (Coupon coupon : this.store.coupons()) {
                line(out, coupon.toRow());
            }
        }
        line(out, "═".repeat(63));
        return out.toString().stripTrailing();
    }

    // The price breakdown is only filled in at finalization, so an open order with items shows zeros.
    public String orderDetail(String orderId) {
        Order order = this.store.order(orderId);
        if (order == null) {
            return "ERROR: Sipariş bulunamadı: " + orderId;
        }
        StringBuilder out = new StringBuilder();
        line(out, "══════ SİPARİŞ DETAYI: " + orderId + " ══════");
        line(out, "Müşteri  : " + order.customerId());
        line(out, "Durum    : " + order.status().label());
        if (order.couponCode() != null) {
            line(out, "Kupon    : " + order.couponCode());
        }
        line(out, "");
        line(out, String.format("  %-10s | %-25s | %4s | %10s | %7s | %7s | %11s", "Ürün ID", "Ad", "Adet", "BirimFiyat", "KatIndir", "TplIndir", "Subtotal"));
        line(out, "  " + "─".repeat(83));
        if (order.items().isEmpty()) {
            line(out, "  (sipariş boş)");
        } else {
            for (OrderItem item : order.items()) {
                line(out, item.toRow());
            }
        }
        if (order.isFinalized() || !order.items().isEmpty() && order.isOpen()) {
            line(out, "");
            double afterItems = order.subtotal() - order.itemSavings();
            line(out, String.format("  %-38s: %12.2f", "İndirim öncesi ara toplam", order.subtotal()));
            line(out, String.format("  %-38s: %12.2f", "Kalem indirimi tasarrufu", order.itemSavings()));
            line(out, String.format("  %-38s: %12.2f", "Kalem indirimleri sonrası", afterItems));
            if (order.tierPercent() > 0.0) {
                line(out, String.format("  %-38s: %12.2f", String.format("Müşteri katmanı (%%%.1f)", order.tierPercent()), -order.tierDiscount()));
            }
            if (order.loyaltyPercent() > 0.0) {
                line(out, String.format("  %-38s: %12.2f", String.format("Sadakat bonusu (%%%.1f)", order.loyaltyPercent()), -order.loyaltyDiscount()));
            }
            if (order.couponPercent() > 0.0) {
                line(out, String.format("  %-38s: %12.2f", String.format("Kupon indirimi (%%%.1f)", order.couponPercent()), -order.couponDiscount()));
            }
            line(out, "  " + "═".repeat(53));
            line(out, String.format("  %-38s: %12.2f", "ÖDENECEK TOPLAM", order.total()));
            line(out, String.format("  %-38s: %12.2f", "Toplam tasarruf", order.totalSavings()));
        }
        line(out, "═".repeat(55));
        return out.toString().stripTrailing();
    }

    public String customerReport(String customerId) {
        Customer customer = this.store.customer(customerId);
        if (customer == null) {
            return "ERROR: Müşteri bulunamadı: " + customerId;
        }
        StringBuilder out = new StringBuilder();
        line(out, "══════ MÜŞTERİ RAPORU: " + customerId + " ══════");
        line(out, "Ad               : " + customer.name());
        line(out, "Katman           : " + customer.tier().label() + String.format(" (%%%.0f indirim)", customer.tier().discountPercent()));
        line(out, "Tamamlanan sipariş: " + customer.completedOrderCount());
        line(out, String.format("Toplam harcama   : %.2f", customer.totalSpent()));
        line(out, "Sadakat bonusu   : " + (customer.isLoyal() ? "AKTIF (%2 ek indirim)" : "pasif (gerekli: 5 sipariş)"));
        line(out, "");
        line(out, "Sipariş Geçmişi:");
        line(out, "─".repeat(55));
        if (customer.completedOrderIds().isEmpty()) {
            line(out, "  (henüz tamamlanmış sipariş yok)");
        } else {
            line(out, String.format("  %-12s | %6s | %10s | %12s", "Sipariş", "Ürün", "Ham Tutar", "Ödenen"));
            line(out, "  " + "─".repeat(50));
            for (String orderId : customer.completedOrderIds()) {
                Order order = this.store.order(orderId);
                if (order == null) continue;
                line(out, String.format("  %-12s | %6d | %10.2f | %12.2f", orderId, order.totalQuantity(), order.subtotal(), order.total()));
            }
        }
        line(out, "═".repeat(55));
        return out.toString().stripTrailing();
    }

    public String stockReport() {
        StringBuilder out = new StringBuilder();
        line(out, "══════════════════ STOK RAPORU ══════════════════");
        line(out, String.format("%-10s | %-25s | %6s | %9s | %13s", "Ürün ID", "Ad", "Stok", "Satılan", "Durum"));
        line(out, "─".repeat(72));
        if (this.store.products().isEmpty()) {
            line(out, "  (henüz ürün yok)");
        } else {
            int outOfStock = 0;
            int lowStock = 0;
            int inStock = 0;
            for (Product product : this.store.products()) {
                String status;
                if (product.stock() == 0) {
                    status = "OUT-OF-STOCK";
                    ++outOfStock;
                } else if (product.stock() < LOW_STOCK_THRESHOLD) {
                    status = "LOW-STOCK";
                    ++lowStock;
                } else {
                    status = "IN-STOCK";
                    ++inStock;
                }
                line(out, String.format("%-10s | %-25s | %6d | %9d | %s", product.id(), product.name(), product.stock(), product.unitsSold(), status));
            }
            line(out, "─".repeat(72));
            line(out, String.format("Özet: IN-STOCK=%d  LOW-STOCK=%d  OUT-OF-STOCK=%d", inStock, lowStock, outOfStock));
        }
        line(out, "═".repeat(72));
        return out.toString().stripTrailing();
    }

    private static void line(StringBuilder out, String text) {
        out.append(text).append("\n");
    }
}
