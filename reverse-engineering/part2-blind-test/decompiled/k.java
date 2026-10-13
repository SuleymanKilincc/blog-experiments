/*
 * Decompiled with CFR 0.152.
 */
public class k {
    private final String a;
    private String b;
    private String c;
    private double d;
    private int e;
    private int f;

    public k(String string, String string2, String string3, double d2, int n) {
        if (string == null || string.isBlank()) {
            throw new IllegalArgumentException("\u00dcr\u00fcn ID'si bo\u015f olamaz");
        }
        if (d2 < 0.0) {
            throw new IllegalArgumentException("Fiyat negatif olamaz: " + d2);
        }
        if (n < 0) {
            throw new IllegalArgumentException("Ba\u015flang\u0131\u00e7 stoku negatif olamaz: " + n);
        }
        this.a = string;
        this.b = string2;
        this.c = string3;
        this.d = d2;
        this.e = n;
        this.f = 0;
    }

    public String a() {
        return this.a;
    }

    public String b() {
        return this.b;
    }

    public String c() {
        return this.c;
    }

    public double d() {
        return this.d;
    }

    public int e() {
        return this.e;
    }

    public int f() {
        return this.f;
    }

    public void a(String string) {
        this.b = string;
    }

    public void b(String string) {
        this.c = string;
    }

    public void a(double d2) {
        if (d2 < 0.0) {
            throw new IllegalArgumentException("Fiyat negatif olamaz: " + d2);
        }
        this.d = d2;
    }

    public boolean a(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Rezervasyon miktar\u0131 pozitif olmal\u0131");
        }
        if (this.e < n) {
            return false;
        }
        this.e -= n;
        return true;
    }

    public void b(int n) {
        if (n > 0) {
            this.e += n;
        }
    }

    public void c(int n) {
        if (n > 0) {
            this.f += n;
        }
    }

    public void d(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Yenileme miktar\u0131 pozitif olmal\u0131");
        }
        this.e += n;
    }

    public String g() {
        return String.format("%-10s | %-25s | %-12s | %10.2f | %6d | %9d", this.a, this.b, this.c, this.d, this.e, this.f);
    }

    public String toString() {
        return String.format("Product{id='%s', name='%s', cat='%s', price=%.2f, stock=%d}", this.a, this.b, this.c, this.d, this.e);
    }
}

