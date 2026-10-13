"""Score the blind reconstruction's names against the original ones from ProGuard's mapping.

MINE maps each obfuscated member (class letter + obfuscated name + descriptor-free original
name, as listed in mapping.txt) to the name used in the reconstruction. Writes names.md.
Usage: name_score.py <mapping.txt> <decompiled dir> <out names.md>"""
import pathlib
import re
import sys

mapping_path, decomp_dir, out_path = sys.argv[1:4]

CLASSES = {'Category': 'Category', 'CommandInterpreter': 'CommandProcessor', 'Coupon': 'Coupon',
           'Customer': 'Customer', 'CustomerTier': 'CustomerTier', 'DiscountEngine': 'PricingEngine',
           'Main': 'StoreApp', 'Order': 'Order', 'OrderItem': 'OrderItem', 'OrderStatus': 'OrderStatus',
           'Product': 'Product', 'ReportGenerator': 'ReportGenerator', 'Store': 'Store'}

MINE = {
    'Category': {'id': 'id', 'name': 'name', 'discountPercent': 'discountPercent', 'bulkThresholdAmount': 'bulkThreshold',
                 'bulkExtraDiscountPercent': 'bulkExtraPercent', 'getId': 'id', 'getName': 'name',
                 'getDiscountPercent': 'discountPercent', 'getBulkThresholdAmount': 'bulkThreshold',
                 'getBulkExtraDiscountPercent': 'bulkExtraPercent', 'setName': 'rename',
                 'setDiscountPercent': 'setDiscountPercent', 'setBulkDiscount': 'setBulkDiscount', 'toTableRow': 'toRow'},
    'CommandInterpreter': {'store': 'store', 'reporter': 'reports', 'execute': 'process', 'dispatch': 'execute'},
    'Coupon': {'code': 'code', 'discountPercent': 'discountPercent', 'minimumOrderAmount': 'minOrderAmount',
               'maxUsages': 'maxUses', 'usageCount': 'timesUsed', 'getCode': 'code', 'getDiscountPercent': 'discountPercent',
               'getMinimumOrderAmount': 'minOrderAmount', 'getMaxUsages': 'maxUses', 'getUsageCount': 'timesUsed',
               'isValid': 'canApplyTo', 'recordUsage': 'recordUse', 'getRemainingUsages': 'remainingUses', 'toTableRow': 'toRow'},
    'Customer': {'id': 'id', 'name': 'name', 'tier': 'tier', 'completedOrderIds': 'completedOrderIds', 'totalSpent': 'totalSpent',
                 'getId': 'id', 'getName': 'name', 'getTier': 'tier', 'getCompletedOrderCount': 'completedOrderCount',
                 'getTotalSpent': 'totalSpent', 'getCompletedOrderIds': 'completedOrderIds', 'setName': 'rename',
                 'setTier': 'setTier', 'recordCompletedOrder': 'recordCompletedOrder', 'isLoyaltyEligible': 'isLoyal',
                 'toTableRow': 'toRow'},
    'CustomerTier': {'discountPercent': 'discountPercent', 'displayName': 'label', 'getDiscountPercent': 'discountPercent',
                     'getDisplayName': 'label', 'fromString': 'parse'},
    'DiscountEngine': {'BULK_QUANTITY_THRESHOLD': 'BULK_QUANTITY', 'BULK_QUANTITY_DISCOUNT': 'BULK_DISCOUNT_PERCENT',
                       'LOYALTY_ORDER_THRESHOLD': 'LOYALTY_MIN_ORDERS', 'LOYALTY_DISCOUNT_PCT': 'LOYALTY_DISCOUNT_PERCENT',
                       'store': 'store', 'applyDiscountsAndFinalize': 'price', 'roundToCents': 'roundToCents'},
    'Main': {'BANNER': 'BANNER'},
    'Order': {'orderId': 'id', 'customerId': 'customerId', 'status': 'status', 'items': 'items', 'appliedCouponCode': 'couponCode',
              'subtotalBeforeDiscounts': 'subtotal', 'itemDiscountTotal': 'itemSavings', 'tierDiscountPct': 'tierPercent',
              'tierDiscountAmount': 'tierDiscount', 'loyaltyDiscountPct': 'loyaltyPercent', 'loyaltyDiscountAmount': 'loyaltyDiscount',
              'couponDiscountPct': 'couponPercent', 'couponDiscountAmount': 'couponDiscount', 'grandTotal': 'total',
              'getOrderId': 'id', 'getCustomerId': 'customerId', 'getStatus': 'status', 'getAppliedCouponCode': 'couponCode',
              'getItems': 'items', 'getSubtotalBeforeDiscounts': 'subtotal', 'getItemDiscountTotal': 'itemSavings',
              'getTierDiscountPct': 'tierPercent', 'getTierDiscountAmount': 'tierDiscount', 'getLoyaltyDiscountPct': 'loyaltyPercent',
              'getLoyaltyDiscountAmount': 'loyaltyDiscount', 'getCouponDiscountPct': 'couponPercent',
              'getCouponDiscountAmount': 'couponDiscount', 'getGrandTotal': 'total', 'getTotalSavings': 'totalSavings',
              'isOpen': 'isOpen', 'isFinalized': 'isFinalized', 'isCancelled': 'isCancelled', 'addItem': 'addItem',
              'setCoupon': 'setCouponCode', 'setFinancials': 'setPricing', 'markFinalized': 'markFinalized',
              'markCancelled': 'markCancelled', 'hasProduct': 'containsProduct', 'getTotalItemCount': 'totalQuantity'},
    'OrderItem': {'productId': 'productId', 'productName': 'productName', 'quantity': 'quantity', 'unitBasePrice': 'unitPrice',
                  'unitDiscountedPrice': 'discountedUnitPrice', 'categoryDiscountPct': 'categoryDiscountPercent',
                  'bulkDiscountPct': 'quantityDiscountPercent', 'getProductId': 'productId', 'getProductName': 'productName',
                  'getQuantity': 'quantity', 'getUnitBasePrice': 'unitPrice', 'getUnitDiscountedPrice': 'discountedUnitPrice',
                  'getCategoryDiscountPct': 'categoryDiscountPercent', 'getBulkDiscountPct': 'quantityDiscountPercent',
                  'getSubtotal': 'lineTotal', 'getSavings': 'lineSavings', 'applyItemDiscounts': 'applyDiscounts',
                  'toTableRow': 'toRow'},
    'OrderStatus': {'displayName': 'label', 'getDisplayName': 'label'},
    'Product': {'id': 'id', 'name': 'name', 'categoryId': 'categoryId', 'basePrice': 'price', 'stock': 'stock',
                'totalSold': 'unitsSold', 'getId': 'id', 'getName': 'name', 'getCategoryId': 'categoryId',
                'getBasePrice': 'price', 'getStock': 'stock', 'getTotalSold': 'unitsSold', 'setName': 'rename',
                'setCategoryId': 'setCategoryId', 'setBasePrice': 'setPrice', 'reserveStock': 'reserve',
                'releaseStock': 'release', 'confirmSale': 'recordSale', 'addStock': 'restock', 'toTableRow': 'toRow'},
    'ReportGenerator': {'LOW_STOCK_THRESHOLD': 'LOW_STOCK_THRESHOLD', 'store': 'store', 'listCategories': 'listCategories',
                        'listProducts': 'listProducts', 'listCustomers': 'listCustomers', 'listOrders': 'listOrders',
                        'listCoupons': 'listCoupons', 'orderDetail': 'orderDetail', 'customerReport': 'customerReport',
                        'stockReport': 'stockReport', 'appendLine': 'line'},
    'Store': {'categories': 'categories', 'products': 'products', 'customers': 'customers', 'orders': 'orders',
              'coupons': 'coupons', 'discountEngine': 'pricing', 'addCategory': 'addCategory', 'setBulkDiscount': 'setBulkDiscount',
              'addProduct': 'addProduct', 'restockProduct': 'restock', 'updatePrice': 'updatePrice', 'addCustomer': 'addCustomer',
              'upgradeTier': 'changeTier', 'addCoupon': 'addCoupon', 'addCouponWithLimit': 'addLimitedCoupon',
              'createOrder': 'createOrder', 'addItem': 'addItem', 'applyCoupon': 'applyCoupon', 'finalizeOrder': 'finalizeOrder',
              'cancelOrder': 'cancelOrder', 'getProduct': 'product', 'getCategory': 'category', 'getCustomer': 'customer',
              'getOrder': 'order', 'getCoupon': 'coupon', 'getAllCategories': 'categories', 'getAllProducts': 'products',
              'getAllCustomers': 'customers', 'getAllOrders': 'orders', 'getAllCoupons': 'coupons'},
}

# Members that kept their names (toString, main, enum constants) or are synthetic aren't scored.
SKIP = re.compile(r'<init>|<clinit>|lambda\$|\$VALUES|\$values|^toString$|^main$|^values$|^valueOf$|^(REGULAR|SILVER|GOLD|PLATINUM|OPEN|FINALIZED|CANCELLED)$')

members, cls = [], None
for line in open(mapping_path, encoding='utf-8'):
    if line.startswith('#'):
        continue
    m = re.match(r'^(\S+) -> \S+:$', line.rstrip())
    if m:
        cls = m.group(1)
        continue
    m = re.match(r'^\s+(?:\d+:\d+:)?\S+ (\w+|\$\w+|<\w+>)(?:\(.*\))? -> \S+$', line.rstrip())
    if m and not SKIP.search(m.group(1)):
        if (cls, m.group(1)) not in [(c, n) for c, n in members]:
            members.append((cls, m.group(1)))

# Which original identifiers appear verbatim inside the decompiled strings (e.g. toString formats)?
decompiled = ' '.join(p.read_text(encoding='utf-8') for p in pathlib.Path(decomp_dir).glob('*.java'))
strings = ' '.join(re.findall(r'"((?:[^"\\]|\\.)*)"', decompiled))


def norm(name):
    return re.sub(r'^(get|is|set)(?=[A-Z])', '', name).lower().replace('_', '')


missing = [(c, n) for c, n in members if n not in MINE.get(c, {})]
assert not missing, f'unmapped members: {missing}'

rows, exact, normalized, leaked = [], 0, 0, 0
for c, n in members:
    mine = MINE[c][n]
    e = mine == n
    nm = norm(mine) == norm(n)
    lk = re.search(r'\b' + re.escape(n) + r'\b', strings) is not None
    exact += e
    normalized += nm
    leaked += lk
    rows.append(f'| {c} | `{n}` | `{mine}` | {"yes" if e else ""} | {"yes" if nm else ""} | {"yes" if lk else ""} |')

class_exact = sum(1 for o, mine in CLASSES.items() if o == mine)
class_leaked = sum(1 for o in CLASSES if re.search(r'\b' + o + r'\{', strings))
summary = (f'Classes: {class_exact}/{len(CLASSES)} exact; {class_leaked} of them appear verbatim in a toString format string.\n'
           f'Fields and methods: {len(members)} scored, {exact} exact, {normalized} equal after dropping get/is/set and case, '
           f'{leaked} appear verbatim somewhere in the program\'s strings.')
print(summary)

table = ['| Class (original) | Original member | Reconstruction | Exact | Same after get/is/set + case | Name visible in strings |',
         '|---|---|---|---|---|---|'] + rows
def in_tostring(cls_name):
    return re.search(r'\b' + cls_name + r'\{', strings) is not None


class_table = ['| Original class | Reconstruction | Exact | In a toString string |', '|---|---|---|---|'] + [
    '| `{}` | `{}` | {} | {} |'.format(o, m, 'yes' if o == m else '', 'yes' if in_tostring(o) else '')
    for o, m in CLASSES.items()]
pathlib.Path(out_path).write_text('# Name recovery\n\n' + summary + '\n\n## Classes\n\n' + '\n'.join(class_table) +
                                  '\n\n## Fields and methods\n\n' + '\n'.join(table) + '\n', encoding='utf-8')
