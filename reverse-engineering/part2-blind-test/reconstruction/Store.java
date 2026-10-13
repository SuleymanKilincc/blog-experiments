import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

// Reconstructed from obfuscated class "m": holds every category, product, customer, order and
// coupon in insertion order, and implements each state-changing command. Every command returns
// the line to print, starting with "OK:" or "ERROR:".
public class Store {
    private final Map<String, Category> categories = new LinkedHashMap<>();
    private final Map<String, Product> products = new LinkedHashMap<>();
    private final Map<String, Customer> customers = new LinkedHashMap<>();
    private final Map<String, Order> orders = new LinkedHashMap<>();
    private final Map<String, Coupon> coupons = new LinkedHashMap<>();
    private final PricingEngine pricing = new PricingEngine(this);

    public String addCategory(String id, String name, double discountPercent) {
        if (this.categories.containsKey(id)) {
            return "ERROR: Kategori zaten mevcut: " + id;
        }
        try {
            this.categories.put(id, new Category(id, name, discountPercent));
        } catch (IllegalArgumentException e) {
            return "ERROR: " + e.getMessage();
        }
        return "OK: Kategori eklendi: " + id + " [" + name + ", %" + discountPercent + " indirim]";
    }

    public String setBulkDiscount(String categoryId, double threshold, double extraPercent) {
        Category category = this.categories.get(categoryId);
        if (category == null) {
            return "ERROR: Kategori bulunamadı: " + categoryId;
        }
        try {
            category.setBulkDiscount(threshold, extraPercent);
        } catch (IllegalArgumentException e) {
            return "ERROR: " + e.getMessage();
        }
        return String.format("OK: %s kategorisine toplu indirim ayarlandı: %.2f+ → +%%%.1f", categoryId, threshold, extraPercent);
    }

    public String addProduct(String id, String name, String categoryId, double price, int stock) {
        if (this.products.containsKey(id)) {
            return "ERROR: Ürün zaten mevcut: " + id;
        }
        if (!this.categories.containsKey(categoryId)) {
            return "ERROR: Kategori bulunamadı: " + categoryId;
        }
        try {
            this.products.put(id, new Product(id, name, categoryId, price, stock));
        } catch (IllegalArgumentException e) {
            return "ERROR: " + e.getMessage();
        }
        return String.format("OK: Ürün eklendi: %s [%s, %.2f TL, %d adet]", id, name, price, stock);
    }

    public String restock(String productId, int quantity) {
        Product product = this.products.get(productId);
        if (product == null) {
            return "ERROR: Ürün bulunamadı: " + productId;
        }
        try {
            product.restock(quantity);
        } catch (IllegalArgumentException e) {
            return "ERROR: " + e.getMessage();
        }
        return String.format("OK: %s yenilendi, eklenen: %d, yeni stok: %d", productId, quantity, product.stock());
    }

    public String updatePrice(String productId, double newPrice) {
        Product product = this.products.get(productId);
        if (product == null) {
            return "ERROR: Ürün bulunamadı: " + productId;
        }
        double oldPrice = product.price();
        try {
            product.setPrice(newPrice);
        } catch (IllegalArgumentException e) {
            return "ERROR: " + e.getMessage();
        }
        return String.format("OK: %s fiyatı güncellendi: %.2f → %.2f", productId, oldPrice, newPrice);
    }

    public String addCustomer(String id, String name, CustomerTier tier) {
        if (this.customers.containsKey(id)) {
            return "ERROR: Müşteri zaten mevcut: " + id;
        }
        try {
            this.customers.put(id, new Customer(id, name, tier));
        } catch (IllegalArgumentException e) {
            return "ERROR: " + e.getMessage();
        }
        return "OK: Müşteri eklendi: " + id + " [" + name + ", " + tier.label() + "]";
    }

    public String changeTier(String customerId, CustomerTier newTier) {
        Customer customer = this.customers.get(customerId);
        if (customer == null) {
            return "ERROR: Müşteri bulunamadı: " + customerId;
        }
        CustomerTier oldTier = customer.tier();
        customer.setTier(newTier);
        return String.format("OK: %s katmanı değiştirildi: %s → %s", customerId, oldTier.label(), newTier.label());
    }

    public String addCoupon(String code, double discountPercent, double minOrderAmount) {
        if (this.coupons.containsKey(code)) {
            return "ERROR: Kupon zaten mevcut: " + code;
        }
        try {
            this.coupons.put(code, new Coupon(code, discountPercent, minOrderAmount));
        } catch (IllegalArgumentException e) {
            return "ERROR: " + e.getMessage();
        }
        return String.format("OK: Kupon eklendi: %s [%%%.1f indirim, min %.2f TL]", code, discountPercent, minOrderAmount);
    }

    public String addLimitedCoupon(String code, double discountPercent, double minOrderAmount, int maxUses) {
        if (this.coupons.containsKey(code)) {
            return "ERROR: Kupon zaten mevcut: " + code;
        }
        try {
            this.coupons.put(code, new Coupon(code, discountPercent, minOrderAmount, maxUses));
        } catch (IllegalArgumentException e) {
            return "ERROR: " + e.getMessage();
        }
        return String.format("OK: Kupon eklendi: %s [%%%.1f indirim, min %.2f TL, max %d kullanım]", code, discountPercent, minOrderAmount, maxUses);
    }

    public String createOrder(String orderId, String customerId) {
        if (this.orders.containsKey(orderId)) {
            return "ERROR: Sipariş zaten mevcut: " + orderId;
        }
        if (!this.customers.containsKey(customerId)) {
            return "ERROR: Müşteri bulunamadı: " + customerId;
        }
        this.orders.put(orderId, new Order(orderId, customerId));
        return "OK: Sipariş oluşturuldu: " + orderId + " (müşteri: " + customerId + ")";
    }

    // Reserves stock immediately; the stock comes back only if the order is cancelled.
    public String addItem(String orderId, String productId, int quantity) {
        Order order = this.orders.get(orderId);
        if (order == null) {
            return "ERROR: Sipariş bulunamadı: " + orderId;
        }
        if (!order.isOpen()) {
            return "ERROR: Sipariş " + orderId + " açık değil (durum: " + order.status().label() + ")";
        }
        if (order.containsProduct(productId)) {
            return "ERROR: Ürün " + productId + " zaten siparişte mevcut: " + orderId;
        }
        Product product = this.products.get(productId);
        if (product == null) {
            return "ERROR: Ürün bulunamadı: " + productId;
        }
        if (quantity <= 0) {
            return "ERROR: Miktar pozitif olmalı: " + quantity;
        }
        if (!product.reserve(quantity)) {
            return String.format("ERROR: %s için yetersiz stok (istenen: %d, mevcut: %d)", productId, quantity, product.stock());
        }
        order.addItem(new OrderItem(productId, product.name(), quantity, product.price()));
        return String.format("OK: %d x %s siparişe eklendi (%s)", quantity, productId, orderId);
    }

    public String applyCoupon(String orderId, String couponCode) {
        Order order = this.orders.get(orderId);
        if (order == null) {
            return "ERROR: Sipariş bulunamadı: " + orderId;
        }
        if (!order.isOpen()) {
            return "ERROR: Sipariş " + orderId + " açık değil";
        }
        if (!this.coupons.containsKey(couponCode)) {
            return "ERROR: Kupon bulunamadı: " + couponCode;
        }
        order.setCouponCode(couponCode);
        return "OK: Kupon " + couponCode + " siparişe uygulandı: " + orderId;
    }

    public String finalizeOrder(String orderId) {
        Order order = this.orders.get(orderId);
        if (order == null) {
            return "ERROR: Sipariş bulunamadı: " + orderId;
        }
        if (!order.isOpen()) {
            return "ERROR: Sipariş " + orderId + " açık değil";
        }
        if (order.items().isEmpty()) {
            return "ERROR: Sipariş " + orderId + " boş, ürün ekleyin";
        }
        boolean priced = this.pricing.price(order);
        if (!priced) {
            // Pricing only fails on the coupon here; the message always blames the minimum amount,
            // even when the real reason is that the coupon's usage limit is used up.
            String couponCode = order.couponCode();
            Coupon coupon = couponCode != null ? this.coupons.get(couponCode) : null;
            if (coupon == null) {
                return "ERROR: Bilinmeyen kupon: " + couponCode;
            }
            return String.format("ERROR: Kupon %s geçersiz (min tutar: %.2f, mevcut tutar karşılanmıyor)", couponCode, coupon.minOrderAmount());
        }
        for (OrderItem item : order.items()) {
            Product product = this.products.get(item.productId());
            if (product == null) continue;
            product.recordSale(item.quantity());
        }
        Customer customer = this.customers.get(order.customerId());
        if (customer != null) {
            customer.recordCompletedOrder(orderId, order.total());
        }
        order.markFinalized();
        return String.format("OK: Sipariş %s tamamlandı | Ara toplam: %.2f | İndirim: %.2f | Ödenecek: %.2f", orderId, order.subtotal(), order.totalSavings(), order.total());
    }

    public String cancelOrder(String orderId) {
        Order order = this.orders.get(orderId);
        if (order == null) {
            return "ERROR: Sipariş bulunamadı: " + orderId;
        }
        if (!order.isOpen()) {
            return "ERROR: Sipariş " + orderId + " açık değil (durum: " + order.status().label() + ")";
        }
        for (OrderItem item : order.items()) {
            Product product = this.products.get(item.productId());
            if (product == null) continue;
            product.release(item.quantity());
        }
        order.markCancelled();
        return "OK: Sipariş " + orderId + " iptal edildi, stoklar iade edildi";
    }

    public Product product(String id) {
        return this.products.get(id);
    }

    public Category category(String id) {
        return this.categories.get(id);
    }

    public Customer customer(String id) {
        return this.customers.get(id);
    }

    public Order order(String id) {
        return this.orders.get(id);
    }

    public Coupon coupon(String code) {
        return this.coupons.get(code);
    }

    public Collection<Category> categories() {
        return this.categories.values();
    }

    public Collection<Product> products() {
        return this.products.values();
    }

    public Collection<Customer> customers() {
        return this.customers.values();
    }

    public Collection<Order> orders() {
        return this.orders.values();
    }

    public Collection<Coupon> coupons() {
        return this.coupons.values();
    }
}
