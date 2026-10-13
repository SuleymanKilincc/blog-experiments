/*
 * Decompiled with CFR 0.152.
 */
public class c {
    private final String a;
    private final double b;
    private final double c;
    private final int d;
    private int e;

    public c(String string, double d2, double d3, int n) {
        if (string == null || string.isBlank()) {
            throw new IllegalArgumentException("Kupon kodu bo\u015f olamaz");
        }
        if (d2 <= 0.0 || d2 > 100.0) {
            throw new IllegalArgumentException("Kupon indirimi 0-100 aras\u0131nda olmal\u0131 (0 hari\u00e7)");
        }
        if (d3 < 0.0) {
            throw new IllegalArgumentException("Minimum sipari\u015f tutar\u0131 negatif olamaz");
        }
        this.a = string;
        this.b = d2;
        this.c = d3;
        this.d = n;
        this.e = 0;
    }

    public c(String string, double d2, double d3) {
        this(string, d2, d3, -1);
    }

    public String a() {
        return this.a;
    }

    public double b() {
        return this.b;
    }

    public double c() {
        return this.c;
    }

    public int d() {
        return this.d;
    }

    public int e() {
        return this.e;
    }

    public boolean a(double d2) {
        if (this.d >= 0 && this.e >= this.d) {
            return false;
        }
        return d2 >= this.c;
    }

    public void f() {
        ++this.e;
    }

    public int g() {
        if (this.d < 0) {
            return -1;
        }
        return this.d - this.e;
    }

    public String h() {
        String string = this.d < 0 ? "sinirsiz" : String.valueOf(this.d);
        String string2 = this.d < 0 ? "\u221e" : String.valueOf(this.g());
        return String.format("%-14s | %7.1f%% | %12.2f | %10s | %5d / %s", this.a, this.b, this.c, string, this.e, string2);
    }

    public String toString() {
        return String.format("Coupon{code='%s', discount=%.1f%%, minOrder=%.2f, used=%d/%s}", this.a, this.b, this.c, this.e, this.d < 0 ? "\u221e" : String.valueOf(this.d));
    }
}

