/*
 * Decompiled with CFR 0.152.
 */
public class a {
    private final String a;
    private String b;
    private double c;
    private double d;
    private double e;

    public a(String string, String string2, double d2) {
        if (string == null || string.isBlank()) {
            throw new IllegalArgumentException("Kategori ID'si bo\u015f olamaz");
        }
        if (d2 < 0.0 || d2 > 100.0) {
            throw new IllegalArgumentException("\u0130ndirim y\u00fczdesi 0-100 aras\u0131nda olmal\u0131: " + d2);
        }
        this.a = string;
        this.b = string2;
        this.c = d2;
        this.d = 0.0;
        this.e = 0.0;
    }

    public String a() {
        return this.a;
    }

    public String b() {
        return this.b;
    }

    public double c() {
        return this.c;
    }

    public double d() {
        return this.d;
    }

    public double e() {
        return this.e;
    }

    public void a(String string) {
        this.b = string;
    }

    public void a(double d2) {
        if (d2 < 0.0 || d2 > 100.0) {
            throw new IllegalArgumentException("\u0130ndirim y\u00fczdesi 0-100 aras\u0131nda olmal\u0131: " + d2);
        }
        this.c = d2;
    }

    public void a(double d2, double d3) {
        if (d2 < 0.0) {
            throw new IllegalArgumentException("E\u015fik tutar\u0131 negatif olamaz");
        }
        if (d3 < 0.0 || d3 > 100.0) {
            throw new IllegalArgumentException("Ek indirim y\u00fczdesi 0-100 aras\u0131nda olmal\u0131");
        }
        this.d = d2;
        this.e = d3;
    }

    public String f() {
        String string = this.d > 0.0 ? String.format("%.2f \u2192 +%.1f%%", this.d, this.e) : "yok";
        return String.format("%-12s | %-20s | %7.1f%% | %s", this.a, this.b, this.c, string);
    }

    public String toString() {
        return String.format("Category{id='%s', name='%s', discount=%.1f%%}", this.a, this.b, this.c);
    }
}

