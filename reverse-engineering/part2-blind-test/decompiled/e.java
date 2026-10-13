/*
 * Decompiled with CFR 0.152.
 */
public final class e
extends Enum {
    public static final /* enum */ e a = new e("REGULAR", 0, 0.0, "Regular");
    public static final /* enum */ e b = new e("SILVER", 1, 5.0, "Silver");
    public static final /* enum */ e c = new e("GOLD", 2, 10.0, "Gold");
    public static final /* enum */ e d = new e("PLATINUM", 3, 15.0, "Platinum");
    private final double e;
    private final String f;
    private static final /* synthetic */ e[] g;

    public static e[] values() {
        return (e[])g.clone();
    }

    public static e valueOf(String string) {
        return Enum.valueOf(e.class, string);
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private e() {
        void var5_3;
        void var2_-1;
        void var1_-1;
        this.e = (double)string;
        this.f = var5_3;
    }

    public double a() {
        return this.e;
    }

    public String b() {
        return this.f;
    }

    public static e a(String string) {
        return switch (string.toUpperCase().trim()) {
            case "REGULAR" -> a;
            case "SILVER" -> b;
            case "GOLD" -> c;
            case "PLATINUM" -> d;
            default -> throw new IllegalArgumentException("Bilinmeyen m\u00fc\u015fteri katman\u0131: " + string);
        };
    }

    private static /* synthetic */ e[] c() {
        return new e[]{a, b, c, d};
    }

    static {
        g = e.c();
    }
}

