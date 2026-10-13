import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Central store object: holds every domain object (product, category,
 * customer, order, coupon) and implements the business operations.
 *
 * Every command response has the form "OK: ..." or "ERROR: ...";
 * the response is written straight to stdout.
 *
 * Design note:
 *   Every Map is a LinkedHashMap. That keeps insertion order
 *   so the output of the LIST_* commands is deterministic.
 */
public class Store {

    // ---- Data stores --------------------------------------------------------
    private final Map<String, Category> categories = new LinkedHashMap<>();
    private final Map<String, Product>  products   = new LinkedHashMap<>();
    private final Map<String, Customer> customers  = new LinkedHashMap<>();
    private final Map<String, Order>    orders     = new LinkedHashMap<>();
    private final Map<String, Coupon>   coupons    = new LinkedHashMap<>();

    // ---- Discount engine ----------------------------------------------------
    private final DiscountEngine discountEngine = new DiscountEngine(this);

    // =========================================================================
    // CATEGORY OPERATIONS
    // =========================================================================

    /**
     * Adds a new product category.
     *
     * @param id           Unique category ID
     * @param name         Category name
     * @param discountPct  Base category discount percentage (0-100)
     */
    public String addCategory(String id, String name, double discountPct) {
        if (categories.containsKey(id))
            return "ERROR: Kategori zaten mevcut: " + id;
        try {
            categories.put(id, new Category(id, name, discountPct));
        } catch (IllegalArgumentException e) {
            return "ERROR: " + e.getMessage();
        }
        return "OK: Kategori eklendi: " + id + " [" + name + ", %" + discountPct + " indirim]";
    }

    /**
     * Defines a bulk-quantity discount for a category.
     *
     * @param categoryId   Target category
     * @param threshold    Minimum order amount at which the extra discount kicks in
     * @param extraPct     Extra discount percentage
     */
    public String setBulkDiscount(String categoryId, double threshold, double extraPct) {
        Category cat = categories.get(categoryId);
        if (cat == null) return "ERROR: Kategori bulunamadı: " + categoryId;
        try {
            cat.setBulkDiscount(threshold, extraPct);
        } catch (IllegalArgumentException e) {
            return "ERROR: " + e.getMessage();
        }
        return String.format("OK: %s kategorisine toplu indirim ayarlandı: %.2f+ → +%%%.1f",
                categoryId, threshold, extraPct);
    }

    // =========================================================================
    // PRODUCT OPERATIONS
    // =========================================================================

    /**
     * Adds a new product to the store.
     *
     * @param id         Unique product ID
     * @param name       Product name
     * @param categoryId ID of an existing category
     * @param price      Base price (≥ 0)
     * @param stock      Starting stock quantity (≥ 0)
     */
    public String addProduct(String id, String name, String categoryId, double price, int stock) {
        if (products.containsKey(id))
            return "ERROR: Ürün zaten mevcut: " + id;
        if (!categories.containsKey(categoryId))
            return "ERROR: Kategori bulunamadı: " + categoryId;
        try {
            products.put(id, new Product(id, name, categoryId, price, stock));
        } catch (IllegalArgumentException e) {
            return "ERROR: " + e.getMessage();
        }
        return String.format("OK: Ürün eklendi: %s [%s, %.2f TL, %d adet]", id, name, price, stock);
    }

    /**
     * Increases a product's stock.
     *
     * @param productId Target product
     * @param quantity  Quantity to add (> 0)
     */
    public String restockProduct(String productId, int quantity) {
        Product p = products.get(productId);
        if (p == null) return "ERROR: Ürün bulunamadı: " + productId;
        try {
            p.addStock(quantity);
        } catch (IllegalArgumentException e) {
            return "ERROR: " + e.getMessage();
        }
        return String.format("OK: %s yenilendi, eklenen: %d, yeni stok: %d",
                productId, quantity, p.getStock());
    }

    /**
     * Updates a product's base price.
     * The update only affects order items created after this point.
     *
     * @param productId Target product
     * @param newPrice  New price (≥ 0)
     */
    public String updatePrice(String productId, double newPrice) {
        Product p = products.get(productId);
        if (p == null) return "ERROR: Ürün bulunamadı: " + productId;
        double oldPrice = p.getBasePrice();
        try {
            p.setBasePrice(newPrice);
        } catch (IllegalArgumentException e) {
            return "ERROR: " + e.getMessage();
        }
        return String.format("OK: %s fiyatı güncellendi: %.2f → %.2f", productId, oldPrice, newPrice);
    }

    // =========================================================================
    // CUSTOMER OPERATIONS
    // =========================================================================

    /**
     * Adds a new customer to the store.
     *
     * @param id   Unique customer ID
     * @param name Customer name
     * @param tier Starting loyalty tier
     */
    public String addCustomer(String id, String name, CustomerTier tier) {
        if (customers.containsKey(id))
            return "ERROR: Müşteri zaten mevcut: " + id;
        try {
            customers.put(id, new Customer(id, name, tier));
        } catch (IllegalArgumentException e) {
            return "ERROR: " + e.getMessage();
        }
        return "OK: Müşteri eklendi: " + id + " [" + name + ", " + tier.getDisplayName() + "]";
    }

    /**
     * Upgrades (or changes) a customer's loyalty tier.
     *
     * @param customerId Target customer
     * @param newTier    New tier
     */
    public String upgradeTier(String customerId, CustomerTier newTier) {
        Customer c = customers.get(customerId);
        if (c == null) return "ERROR: Müşteri bulunamadı: " + customerId;
        CustomerTier old = c.getTier();
        c.setTier(newTier);
        return String.format("OK: %s katmanı değiştirildi: %s → %s",
                customerId, old.getDisplayName(), newTier.getDisplayName());
    }

    // =========================================================================
    // COUPON OPERATIONS
    // =========================================================================

    /**
     * Adds a coupon with unlimited uses.
     *
     * @param code         Unique coupon code
     * @param discountPct  Discount percentage
     * @param minAmount    Minimum order amount for it to be valid
     */
    public String addCoupon(String code, double discountPct, double minAmount) {
        if (coupons.containsKey(code))
            return "ERROR: Kupon zaten mevcut: " + code;
        try {
            coupons.put(code, new Coupon(code, discountPct, minAmount));
        } catch (IllegalArgumentException e) {
            return "ERROR: " + e.getMessage();
        }
        return String.format("OK: Kupon eklendi: %s [%%%.1f indirim, min %.2f TL]",
                code, discountPct, minAmount);
    }

    /**
     * Adds a coupon with limited uses.
     *
     * @param code         Unique coupon code
     * @param discountPct  Discount percentage
     * @param minAmount    Minimum order amount
     * @param maxUsages    Maximum number of uses
     */
    public String addCouponWithLimit(String code, double discountPct, double minAmount, int maxUsages) {
        if (coupons.containsKey(code))
            return "ERROR: Kupon zaten mevcut: " + code;
        try {
            coupons.put(code, new Coupon(code, discountPct, minAmount, maxUsages));
        } catch (IllegalArgumentException e) {
            return "ERROR: " + e.getMessage();
        }
        return String.format("OK: Kupon eklendi: %s [%%%.1f indirim, min %.2f TL, max %d kullanım]",
                code, discountPct, minAmount, maxUsages);
    }

    // =========================================================================
    // ORDER OPERATIONS
    // =========================================================================

    /**
     * Creates a new, empty order (in the OPEN state).
     *
     * @param orderId    Unique order ID
     * @param customerId ID of an existing customer
     */
    public String createOrder(String orderId, String customerId) {
        if (orders.containsKey(orderId))
            return "ERROR: Sipariş zaten mevcut: " + orderId;
        if (!customers.containsKey(customerId))
            return "ERROR: Müşteri bulunamadı: " + customerId;
        orders.put(orderId, new Order(orderId, customerId));
        return "OK: Sipariş oluşturuldu: " + orderId + " (müşteri: " + customerId + ")";
    }

    /**
     * Adds a product line to the order.
     * Stock is reserved immediately (and stays held while the order is OPEN).
     *
     * @param orderId   Target order
     * @param productId Product to add
     * @param quantity  Quantity (> 0)
     */
    public String addItem(String orderId, String productId, int quantity) {
        Order order = orders.get(orderId);
        if (order == null)     return "ERROR: Sipariş bulunamadı: " + orderId;
        if (!order.isOpen())   return "ERROR: Sipariş " + orderId + " açık değil (durum: "
                                        + order.getStatus().getDisplayName() + ")";

        if (order.hasProduct(productId))
            return "ERROR: Ürün " + productId + " zaten siparişte mevcut: " + orderId;

        Product product = products.get(productId);
        if (product == null)   return "ERROR: Ürün bulunamadı: " + productId;
        if (quantity <= 0)     return "ERROR: Miktar pozitif olmalı: " + quantity;

        if (!product.reserveStock(quantity)) {
            return String.format("ERROR: %s için yetersiz stok (istenen: %d, mevcut: %d)",
                    productId, quantity, product.getStock());
        }

        order.addItem(new OrderItem(productId, product.getName(), quantity, product.getBasePrice()));
        return String.format("OK: %d x %s siparişe eklendi (%s)", quantity, productId, orderId);
    }

    /**
     * Applies a coupon to an open order.
     *
     * @param orderId    Target order
     * @param couponCode An existing coupon code
     */
    public String applyCoupon(String orderId, String couponCode) {
        Order order = orders.get(orderId);
        if (order == null)   return "ERROR: Sipariş bulunamadı: " + orderId;
        if (!order.isOpen()) return "ERROR: Sipariş " + orderId + " açık değil";
        if (!coupons.containsKey(couponCode))
            return "ERROR: Kupon bulunamadı: " + couponCode;
        order.setCoupon(couponCode);
        return "OK: Kupon " + couponCode + " siparişe uygulandı: " + orderId;
    }

    /**
     * Finalizes the order: discounts are calculated, stock sales are confirmed,
     * and the customer's record is updated.
     *
     * @param orderId The order to finalize
     */
    public String finalizeOrder(String orderId) {
        Order order = orders.get(orderId);
        if (order == null)          return "ERROR: Sipariş bulunamadı: " + orderId;
        if (!order.isOpen())        return "ERROR: Sipariş " + orderId + " açık değil";
        if (order.getItems().isEmpty())
            return "ERROR: Sipariş " + orderId + " boş, ürün ekleyin";

        // The discount engine does the calculation and checks the coupon
        boolean success = discountEngine.applyDiscountsAndFinalize(order);
        if (!success) {
            String cc = order.getAppliedCouponCode();
            Coupon coupon = (cc != null) ? coupons.get(cc) : null;
            if (coupon == null) return "ERROR: Bilinmeyen kupon: " + cc;
            return String.format(
                    "ERROR: Kupon %s geçersiz (min tutar: %.2f, mevcut tutar karşılanmıyor)",
                    cc, coupon.getMinimumOrderAmount());
        }

        // Confirm the sale: update the totalSold counters
        for (OrderItem item : order.getItems()) {
            Product p = products.get(item.getProductId());
            if (p != null) p.confirmSale(item.getQuantity());
        }

        // Add to the customer's history
        Customer customer = customers.get(order.getCustomerId());
        if (customer != null) {
            customer.recordCompletedOrder(orderId, order.getGrandTotal());
        }

        order.markFinalized();
        return String.format(
                "OK: Sipariş %s tamamlandı | Ara toplam: %.2f | İndirim: %.2f | Ödenecek: %.2f",
                orderId,
                order.getSubtotalBeforeDiscounts(),
                order.getTotalSavings(),
                order.getGrandTotal());
    }

    /**
     * Cancels an open order.
     * All reserved stock is returned.
     *
     * @param orderId The order to cancel
     */
    public String cancelOrder(String orderId) {
        Order order = orders.get(orderId);
        if (order == null)      return "ERROR: Sipariş bulunamadı: " + orderId;
        if (!order.isOpen())    return "ERROR: Sipariş " + orderId + " açık değil (durum: "
                                        + order.getStatus().getDisplayName() + ")";

        // Return the reserved stock
        for (OrderItem item : order.getItems()) {
            Product p = products.get(item.getProductId());
            if (p != null) p.releaseStock(item.getQuantity());
        }
        order.markCancelled();
        return "OK: Sipariş " + orderId + " iptal edildi, stoklar iade edildi";
    }

    // =========================================================================
    // ACCESSORS (for DiscountEngine and ReportGenerator)
    // =========================================================================

    public Product  getProduct(String id)  { return products.get(id); }
    public Category getCategory(String id) { return categories.get(id); }
    public Customer getCustomer(String id) { return customers.get(id); }
    public Order    getOrder(String id)    { return orders.get(id); }
    public Coupon   getCoupon(String id)   { return coupons.get(id); }

    public Collection<Category> getAllCategories() { return categories.values(); }
    public Collection<Product>  getAllProducts()   { return products.values(); }
    public Collection<Customer> getAllCustomers()  { return customers.values(); }
    public Collection<Order>    getAllOrders()     { return orders.values(); }
    public Collection<Coupon>   getAllCoupons()    { return coupons.values(); }
}
