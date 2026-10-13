/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class h {
    private final String a;
    private final String b;
    private j c;
    private final List d;
    private String e;
    private double f;
    private double g;
    private double h;
    private double i;
    private double j;
    private double k;
    private double l;
    private double m;
    private double n;

    public h(String string, String string2) {
        if (string == null || string.isBlank()) {
            throw new IllegalArgumentException("Sipari\u015f ID'si bo\u015f olamaz");
        }
        if (string2 == null || string2.isBlank()) {
            throw new IllegalArgumentException("M\u00fc\u015fteri ID'si bo\u015f olamaz");
        }
        this.a = string;
        this.b = string2;
        this.c = j.a;
        this.d = new ArrayList();
        this.e = null;
    }

    public String a() {
        return this.a;
    }

    public String b() {
        return this.b;
    }

    public j c() {
        return this.c;
    }

    public String d() {
        return this.e;
    }

    public List e() {
        return Collections.unmodifiableList(this.d);
    }

    public double f() {
        return this.f;
    }

    public double g() {
        return this.g;
    }

    public double h() {
        return this.h;
    }

    public double i() {
        return this.i;
    }

    public double j() {
        return this.j;
    }

    public double k() {
        return this.k;
    }

    public double l() {
        return this.l;
    }

    public double m() {
        return this.m;
    }

    public double n() {
        return this.n;
    }

    public double o() {
        return this.f - this.n;
    }

    public boolean p() {
        return this.c == j.a;
    }

    public boolean q() {
        return this.c == j.b;
    }

    public boolean r() {
        return this.c == j.c;
    }

    public void a(i i2) {
        if (i2 == null) {
            throw new IllegalArgumentException("Sipari\u015f kalemi null olamaz");
        }
        this.d.add(i2);
    }

    public void a(String string) {
        this.e = string;
    }

    public void a(double d2, double d3, double d4, double d5, double d6, double d7, double d8, double d9, double d10) {
        this.f = d2;
        this.g = d3;
        this.h = d4;
        this.i = d5;
        this.j = d6;
        this.k = d7;
        this.l = d8;
        this.m = d9;
        this.n = d10;
    }

    public void s() {
        this.c = j.b;
    }

    public void t() {
        this.c = j.c;
    }

    public boolean b(String string) {
        return this.d.stream().anyMatch(i2 -> i2.a().equals(string));
    }

    public int u() {
        return this.d.stream().mapToInt(i::c).sum();
    }

    public String toString() {
        return String.format("Order{id='%s', customer='%s', status=%s, items=%d, total=%.2f}", this.a, this.b, this.c.a(), this.d.size(), this.n);
    }
}

