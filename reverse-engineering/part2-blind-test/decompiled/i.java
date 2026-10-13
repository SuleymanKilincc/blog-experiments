/*
 * Decompiled with CFR 0.152.
 */
public class i {
    private final String a;
    private final String b;
    private final int c;
    private final double d;
    private double e;
    private double f;
    private double g;

    public i(String string, String string2, int n, double d2) {
        if (n <= 0) {
            throw new IllegalArgumentException("Miktar pozitif olmal\u0131: " + n);
        }
        if (d2 < 0.0) {
            throw new IllegalArgumentException("Birim fiyat negatif olamaz: " + d2);
        }
        this.a = string;
        this.b = string2;
        this.c = n;
        this.d = d2;
        this.e = d2;
        this.f = 0.0;
        this.g = 0.0;
    }

    public String a() {
        return this.a;
    }

    public String b() {
        return this.b;
    }

    public int c() {
        return this.c;
    }

    public double d() {
        return this.d;
    }

    public double e() {
        return this.e;
    }

    public double f() {
        return this.f;
    }

    public double g() {
        return this.g;
    }

    public double h() {
        return this.e * (double)this.c;
    }

    public double i() {
        return (this.d - this.e) * (double)this.c;
    }

    public void a(double d2, double d3) {
        this.f = d2;
        this.g = d3;
        double d4 = (1.0 - d2 / 100.0) * (1.0 - d3 / 100.0);
        this.e = this.d * d4;
    }

    public String j() {
        return String.format("  %-10s | %-25s | %4d | %10.2f | %6.1f%% | %6.1f%% | %11.2f", this.a, this.b, this.c, this.d, this.f, this.g, this.h());
    }

    public String toString() {
        return String.format("OrderItem{product='%s', qty=%d, unitDisc=%.2f, subtotal=%.2f}", this.a, this.c, this.e, this.h());
    }
}

