# Name recovery

Classes: 10/13 exact; 6 of them appear verbatim in a toString format string.
Fields and methods: 181 scored, 62 exact, 90 equal after dropping get/is/set and case, 12 appear verbatim somewhere in the program's strings.

## Classes

| Original class | Reconstruction | Exact | In a toString string |
|---|---|---|---|
| `Category` | `Category` | yes | yes |
| `CommandInterpreter` | `CommandProcessor` |  |  |
| `Coupon` | `Coupon` | yes | yes |
| `Customer` | `Customer` | yes | yes |
| `CustomerTier` | `CustomerTier` | yes |  |
| `DiscountEngine` | `PricingEngine` |  |  |
| `Main` | `StoreApp` |  |  |
| `Order` | `Order` | yes | yes |
| `OrderItem` | `OrderItem` | yes | yes |
| `OrderStatus` | `OrderStatus` | yes |  |
| `Product` | `Product` | yes | yes |
| `ReportGenerator` | `ReportGenerator` | yes |  |
| `Store` | `Store` | yes |  |

## Fields and methods

| Class (original) | Original member | Reconstruction | Exact | Same after get/is/set + case | Name visible in strings |
|---|---|---|---|---|---|
| Category | `id` | `id` | yes | yes | yes |
| Category | `name` | `name` | yes | yes | yes |
| Category | `discountPercent` | `discountPercent` | yes | yes |  |
| Category | `bulkThresholdAmount` | `bulkThreshold` |  |  |  |
| Category | `bulkExtraDiscountPercent` | `bulkExtraPercent` |  |  |  |
| Category | `getId` | `id` |  | yes |  |
| Category | `getName` | `name` |  | yes |  |
| Category | `getDiscountPercent` | `discountPercent` |  | yes |  |
| Category | `getBulkThresholdAmount` | `bulkThreshold` |  |  |  |
| Category | `getBulkExtraDiscountPercent` | `bulkExtraPercent` |  |  |  |
| Category | `setName` | `rename` |  |  |  |
| Category | `setDiscountPercent` | `setDiscountPercent` | yes | yes |  |
| Category | `setBulkDiscount` | `setBulkDiscount` | yes | yes |  |
| Category | `toTableRow` | `toRow` |  |  |  |
| CommandInterpreter | `store` | `store` | yes | yes |  |
| CommandInterpreter | `reporter` | `reports` |  |  |  |
| CommandInterpreter | `execute` | `process` |  |  |  |
| CommandInterpreter | `dispatch` | `execute` |  |  |  |
| Coupon | `code` | `code` | yes | yes | yes |
| Coupon | `discountPercent` | `discountPercent` | yes | yes |  |
| Coupon | `minimumOrderAmount` | `minOrderAmount` |  |  |  |
| Coupon | `maxUsages` | `maxUses` |  |  |  |
| Coupon | `usageCount` | `timesUsed` |  |  |  |
| Coupon | `getCode` | `code` |  | yes |  |
| Coupon | `getDiscountPercent` | `discountPercent` |  | yes |  |
| Coupon | `getMinimumOrderAmount` | `minOrderAmount` |  |  |  |
| Coupon | `getMaxUsages` | `maxUses` |  |  |  |
| Coupon | `getUsageCount` | `timesUsed` |  |  |  |
| Coupon | `isValid` | `canApplyTo` |  |  |  |
| Coupon | `recordUsage` | `recordUse` |  |  |  |
| Coupon | `getRemainingUsages` | `remainingUses` |  |  |  |
| Coupon | `toTableRow` | `toRow` |  |  |  |
| Customer | `id` | `id` | yes | yes | yes |
| Customer | `name` | `name` | yes | yes | yes |
| Customer | `tier` | `tier` | yes | yes | yes |
| Customer | `completedOrderIds` | `completedOrderIds` | yes | yes |  |
| Customer | `totalSpent` | `totalSpent` | yes | yes |  |
| Customer | `getId` | `id` |  | yes |  |
| Customer | `getName` | `name` |  | yes |  |
| Customer | `getTier` | `tier` |  | yes |  |
| Customer | `getCompletedOrderCount` | `completedOrderCount` |  | yes |  |
| Customer | `getTotalSpent` | `totalSpent` |  | yes |  |
| Customer | `getCompletedOrderIds` | `completedOrderIds` |  | yes |  |
| Customer | `setName` | `rename` |  |  |  |
| Customer | `setTier` | `setTier` | yes | yes |  |
| Customer | `recordCompletedOrder` | `recordCompletedOrder` | yes | yes |  |
| Customer | `isLoyaltyEligible` | `isLoyal` |  |  |  |
| Customer | `toTableRow` | `toRow` |  |  |  |
| CustomerTier | `discountPercent` | `discountPercent` | yes | yes |  |
| CustomerTier | `displayName` | `label` |  |  |  |
| CustomerTier | `getDiscountPercent` | `discountPercent` |  | yes |  |
| CustomerTier | `getDisplayName` | `label` |  |  |  |
| CustomerTier | `fromString` | `parse` |  |  |  |
| DiscountEngine | `BULK_QUANTITY_THRESHOLD` | `BULK_QUANTITY` |  |  |  |
| DiscountEngine | `BULK_QUANTITY_DISCOUNT` | `BULK_DISCOUNT_PERCENT` |  |  |  |
| DiscountEngine | `LOYALTY_ORDER_THRESHOLD` | `LOYALTY_MIN_ORDERS` |  |  |  |
| DiscountEngine | `LOYALTY_DISCOUNT_PCT` | `LOYALTY_DISCOUNT_PERCENT` |  |  |  |
| DiscountEngine | `store` | `store` | yes | yes |  |
| DiscountEngine | `applyDiscountsAndFinalize` | `price` |  |  |  |
| DiscountEngine | `roundToCents` | `roundToCents` | yes | yes |  |
| Main | `BANNER` | `BANNER` | yes | yes |  |
| Order | `orderId` | `id` |  |  |  |
| Order | `customerId` | `customerId` | yes | yes |  |
| Order | `status` | `status` | yes | yes | yes |
| Order | `items` | `items` | yes | yes | yes |
| Order | `appliedCouponCode` | `couponCode` |  |  |  |
| Order | `subtotalBeforeDiscounts` | `subtotal` |  |  |  |
| Order | `itemDiscountTotal` | `itemSavings` |  |  |  |
| Order | `tierDiscountPct` | `tierPercent` |  |  |  |
| Order | `tierDiscountAmount` | `tierDiscount` |  |  |  |
| Order | `loyaltyDiscountPct` | `loyaltyPercent` |  |  |  |
| Order | `loyaltyDiscountAmount` | `loyaltyDiscount` |  |  |  |
| Order | `couponDiscountPct` | `couponPercent` |  |  |  |
| Order | `couponDiscountAmount` | `couponDiscount` |  |  |  |
| Order | `grandTotal` | `total` |  |  |  |
| Order | `getOrderId` | `id` |  |  |  |
| Order | `getCustomerId` | `customerId` |  | yes |  |
| Order | `getStatus` | `status` |  | yes |  |
| Order | `getAppliedCouponCode` | `couponCode` |  |  |  |
| Order | `getItems` | `items` |  | yes |  |
| Order | `getSubtotalBeforeDiscounts` | `subtotal` |  |  |  |
| Order | `getItemDiscountTotal` | `itemSavings` |  |  |  |
| Order | `getTierDiscountPct` | `tierPercent` |  |  |  |
| Order | `getTierDiscountAmount` | `tierDiscount` |  |  |  |
| Order | `getLoyaltyDiscountPct` | `loyaltyPercent` |  |  |  |
| Order | `getLoyaltyDiscountAmount` | `loyaltyDiscount` |  |  |  |
| Order | `getCouponDiscountPct` | `couponPercent` |  |  |  |
| Order | `getCouponDiscountAmount` | `couponDiscount` |  |  |  |
| Order | `getGrandTotal` | `total` |  |  |  |
| Order | `getTotalSavings` | `totalSavings` |  | yes |  |
| Order | `isOpen` | `isOpen` | yes | yes |  |
| Order | `isFinalized` | `isFinalized` | yes | yes |  |
| Order | `isCancelled` | `isCancelled` | yes | yes |  |
| Order | `addItem` | `addItem` | yes | yes |  |
| Order | `setCoupon` | `setCouponCode` |  |  |  |
| Order | `setFinancials` | `setPricing` |  |  |  |
| Order | `markFinalized` | `markFinalized` | yes | yes |  |
| Order | `markCancelled` | `markCancelled` | yes | yes |  |
| Order | `hasProduct` | `containsProduct` |  |  |  |
| Order | `getTotalItemCount` | `totalQuantity` |  |  |  |
| OrderItem | `productId` | `productId` | yes | yes |  |
| OrderItem | `productName` | `productName` | yes | yes |  |
| OrderItem | `quantity` | `quantity` | yes | yes |  |
| OrderItem | `unitBasePrice` | `unitPrice` |  |  |  |
| OrderItem | `unitDiscountedPrice` | `discountedUnitPrice` |  |  |  |
| OrderItem | `categoryDiscountPct` | `categoryDiscountPercent` |  |  |  |
| OrderItem | `bulkDiscountPct` | `quantityDiscountPercent` |  |  |  |
| OrderItem | `getProductId` | `productId` |  | yes |  |
| OrderItem | `getProductName` | `productName` |  | yes |  |
| OrderItem | `getQuantity` | `quantity` |  | yes |  |
| OrderItem | `getUnitBasePrice` | `unitPrice` |  |  |  |
| OrderItem | `getUnitDiscountedPrice` | `discountedUnitPrice` |  |  |  |
| OrderItem | `getCategoryDiscountPct` | `categoryDiscountPercent` |  |  |  |
| OrderItem | `getBulkDiscountPct` | `quantityDiscountPercent` |  |  |  |
| OrderItem | `getSubtotal` | `lineTotal` |  |  |  |
| OrderItem | `getSavings` | `lineSavings` |  |  |  |
| OrderItem | `applyItemDiscounts` | `applyDiscounts` |  |  |  |
| OrderItem | `toTableRow` | `toRow` |  |  |  |
| OrderStatus | `displayName` | `label` |  |  |  |
| OrderStatus | `getDisplayName` | `label` |  |  |  |
| Product | `id` | `id` | yes | yes | yes |
| Product | `name` | `name` | yes | yes | yes |
| Product | `categoryId` | `categoryId` | yes | yes |  |
| Product | `basePrice` | `price` |  |  |  |
| Product | `stock` | `stock` | yes | yes | yes |
| Product | `totalSold` | `unitsSold` |  |  |  |
| Product | `getId` | `id` |  | yes |  |
| Product | `getName` | `name` |  | yes |  |
| Product | `getCategoryId` | `categoryId` |  | yes |  |
| Product | `getBasePrice` | `price` |  |  |  |
| Product | `getStock` | `stock` |  | yes |  |
| Product | `getTotalSold` | `unitsSold` |  |  |  |
| Product | `setName` | `rename` |  |  |  |
| Product | `setCategoryId` | `setCategoryId` | yes | yes |  |
| Product | `setBasePrice` | `setPrice` |  |  |  |
| Product | `reserveStock` | `reserve` |  |  |  |
| Product | `releaseStock` | `release` |  |  |  |
| Product | `confirmSale` | `recordSale` |  |  |  |
| Product | `addStock` | `restock` |  |  |  |
| Product | `toTableRow` | `toRow` |  |  |  |
| ReportGenerator | `LOW_STOCK_THRESHOLD` | `LOW_STOCK_THRESHOLD` | yes | yes |  |
| ReportGenerator | `store` | `store` | yes | yes |  |
| ReportGenerator | `listCategories` | `listCategories` | yes | yes |  |
| ReportGenerator | `listProducts` | `listProducts` | yes | yes |  |
| ReportGenerator | `listCustomers` | `listCustomers` | yes | yes |  |
| ReportGenerator | `listOrders` | `listOrders` | yes | yes |  |
| ReportGenerator | `listCoupons` | `listCoupons` | yes | yes |  |
| ReportGenerator | `orderDetail` | `orderDetail` | yes | yes |  |
| ReportGenerator | `customerReport` | `customerReport` | yes | yes |  |
| ReportGenerator | `stockReport` | `stockReport` | yes | yes |  |
| ReportGenerator | `appendLine` | `line` |  |  |  |
| Store | `categories` | `categories` | yes | yes |  |
| Store | `products` | `products` | yes | yes |  |
| Store | `customers` | `customers` | yes | yes |  |
| Store | `orders` | `orders` | yes | yes | yes |
| Store | `coupons` | `coupons` | yes | yes |  |
| Store | `discountEngine` | `pricing` |  |  |  |
| Store | `addCategory` | `addCategory` | yes | yes |  |
| Store | `setBulkDiscount` | `setBulkDiscount` | yes | yes |  |
| Store | `addProduct` | `addProduct` | yes | yes |  |
| Store | `restockProduct` | `restock` |  |  |  |
| Store | `updatePrice` | `updatePrice` | yes | yes |  |
| Store | `addCustomer` | `addCustomer` | yes | yes |  |
| Store | `upgradeTier` | `changeTier` |  |  |  |
| Store | `addCoupon` | `addCoupon` | yes | yes |  |
| Store | `addCouponWithLimit` | `addLimitedCoupon` |  |  |  |
| Store | `createOrder` | `createOrder` | yes | yes |  |
| Store | `addItem` | `addItem` | yes | yes |  |
| Store | `applyCoupon` | `applyCoupon` | yes | yes |  |
| Store | `finalizeOrder` | `finalizeOrder` | yes | yes |  |
| Store | `cancelOrder` | `cancelOrder` | yes | yes |  |
| Store | `getProduct` | `product` |  | yes |  |
| Store | `getCategory` | `category` |  | yes |  |
| Store | `getCustomer` | `customer` |  | yes |  |
| Store | `getOrder` | `order` |  | yes |  |
| Store | `getCoupon` | `coupon` |  | yes |  |
| Store | `getAllCategories` | `categories` |  |  |  |
| Store | `getAllProducts` | `products` |  |  |  |
| Store | `getAllCustomers` | `customers` |  |  |  |
| Store | `getAllOrders` | `orders` |  |  |  |
| Store | `getAllCoupons` | `coupons` |  |  |  |
