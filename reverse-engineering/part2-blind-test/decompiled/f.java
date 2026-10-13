/*
 * Decompiled with CFR 0.152.
 */
public class f {
    public static final int a = 5;
    public static final double b = 5.0;
    public static final int c = 5;
    public static final double d = 2.0;
    private final m e;

    public f(m m2) {
        this.e = m2;
    }

    public boolean a(h h2) {
        double d2;
        double d3;
        double d4;
        d d5 = this.e.e(h2.b());
        if (d5 == null) {
            return false;
        }
        double d6 = 0.0;
        double d7 = 0.0;
        for (i i2 : h2.e()) {
            k k2 = this.e.c(i2.a());
            a a2 = k2 != null ? this.e.d(k2.c()) : null;
            d4 = a2 != null ? a2.c() : 0.0;
            d3 = i2.c() >= 5 ? 5.0 : 0.0;
            i2.a(d4, d3);
            d6 += i2.d() * (double)i2.c();
            d7 += i2.i();
        }
        double d8 = d6 - d7;
        double d9 = d5.c().a();
        d4 = f.a(d8 * d9 / 100.0);
        d3 = d8 - d4;
        double d10 = d5.g() ? 2.0 : 0.0;
        double d11 = f.a(d3 * d10 / 100.0);
        double d12 = d3 - d11;
        double d13 = 0.0;
        double d14 = 0.0;
        String string = h2.d();
        if (string != null) {
            c c2 = this.e.g(string);
            if (c2 == null || !c2.a(d12)) {
                return false;
            }
            d13 = c2.b();
            d14 = f.a(d12 * d13 / 100.0);
            c2.f();
        }
        if ((d2 = d12 - d14) < 0.0) {
            d2 = 0.0;
        }
        h2.a(d6, d7, d9, d4, d10, d11, d13, d14, d2);
        return true;
    }

    private static double a(double d2) {
        return (double)Math.round(d2 * 100.0) / 100.0;
    }
}

