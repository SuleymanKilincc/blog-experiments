/*
 * Decompiled with CFR 0.152.
 */
public class b {
    private final m a;
    private final l b;

    public b(m m2) {
        this.a = m2;
        this.b = new l(m2);
    }

    public void a(String string) {
        String[] stringArray = string.trim().split("\\s+");
        if (stringArray.length == 0) {
            return;
        }
        String string2 = stringArray[0].toUpperCase();
        try {
            String string3 = this.a(string2, stringArray);
            System.out.println(string3);
        }
        catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            System.out.println("ERROR: Eksik arg\u00fcman \u2014 komut: " + string2);
        }
        catch (NumberFormatException numberFormatException) {
            System.out.println("ERROR: Ge\u00e7ersiz say\u0131 format\u0131: " + string);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            System.out.println("ERROR: " + illegalArgumentException.getMessage());
        }
    }

    private String a(String string, String[] stringArray) {
        return switch (string) {
            case "ADD_CATEGORY" -> this.a.a(stringArray[1], stringArray[2], Double.parseDouble(stringArray[3]));
            case "SET_BULK_DISCOUNT" -> this.a.a(stringArray[1], Double.parseDouble(stringArray[2]), Double.parseDouble(stringArray[3]));
            case "ADD_PRODUCT" -> this.a.a(stringArray[1], stringArray[2], stringArray[3], Double.parseDouble(stringArray[4]), Integer.parseInt(stringArray[5]));
            case "RESTOCK" -> this.a.a(stringArray[1], Integer.parseInt(stringArray[2]));
            case "UPDATE_PRICE" -> this.a.a(stringArray[1], Double.parseDouble(stringArray[2]));
            case "ADD_CUSTOMER" -> this.a.a(stringArray[1], stringArray[2], e.a(stringArray[3]));
            case "UPGRADE_TIER" -> this.a.a(stringArray[1], e.a(stringArray[2]));
            case "ADD_COUPON" -> this.a.b(stringArray[1], Double.parseDouble(stringArray[2]), Double.parseDouble(stringArray[3]));
            case "ADD_COUPON_LIMITED" -> this.a.a(stringArray[1], Double.parseDouble(stringArray[2]), Double.parseDouble(stringArray[3]), Integer.parseInt(stringArray[4]));
            case "CREATE_ORDER" -> this.a.a(stringArray[1], stringArray[2]);
            case "ADD_ITEM" -> this.a.a(stringArray[1], stringArray[2], Integer.parseInt(stringArray[3]));
            case "APPLY_COUPON" -> this.a.b(stringArray[1], stringArray[2]);
            case "FINALIZE_ORDER" -> this.a.a(stringArray[1]);
            case "CANCEL_ORDER" -> this.a.b(stringArray[1]);
            case "LIST_CATEGORIES" -> this.b.a();
            case "LIST_PRODUCTS" -> this.b.b();
            case "LIST_CUSTOMERS" -> this.b.c();
            case "LIST_ORDERS" -> this.b.d();
            case "LIST_COUPONS" -> this.b.e();
            case "ORDER_DETAIL" -> this.b.a(stringArray[1]);
            case "CUSTOMER_REPORT" -> this.b.b(stringArray[1]);
            case "STOCK_REPORT" -> this.b.f();
            default -> "ERROR: Bilinmeyen komut: " + string;
        };
    }
}

