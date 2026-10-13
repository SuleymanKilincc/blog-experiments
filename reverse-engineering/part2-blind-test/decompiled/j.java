/*
 * Decompiled with CFR 0.152.
 */
public final class j
extends Enum {
    public static final /* enum */ j a = new j("OPEN", 0, "Open");
    public static final /* enum */ j b = new j("FINALIZED", 1, "Finalized");
    public static final /* enum */ j c = new j("CANCELLED", 2, "Cancelled");
    private final String d;
    private static final /* synthetic */ j[] e;

    public static j[] values() {
        return (j[])e.clone();
    }

    public static j valueOf(String string) {
        return Enum.valueOf(j.class, string);
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private j() {
        void var3_2;
        void var2_-1;
        void var1_-1;
        this.d = var3_2;
    }

    public String a() {
        return this.d;
    }

    private static /* synthetic */ j[] b() {
        return new j[]{a, b, c};
    }

    static {
        e = j.b();
    }
}

