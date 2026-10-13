/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class d {
    private final String a;
    private String b;
    private e c;
    private final List d;
    private double e;

    public d(String string, String string2, e e2) {
        if (string == null || string.isBlank()) {
            throw new IllegalArgumentException("M\u00fc\u015fteri ID'si bo\u015f olamaz");
        }
        if (e2 == null) {
            throw new IllegalArgumentException("M\u00fc\u015fteri katman\u0131 null olamaz");
        }
        this.a = string;
        this.b = string2;
        this.c = e2;
        this.d = new ArrayList();
        this.e = 0.0;
    }

    public String a() {
        return this.a;
    }

    public String b() {
        return this.b;
    }

    public e c() {
        return this.c;
    }

    public int d() {
        return this.d.size();
    }

    public double e() {
        return this.e;
    }

    public List f() {
        return Collections.unmodifiableList(this.d);
    }

    public void a(String string) {
        this.b = string;
    }

    public void a(e e2) {
        if (e2 == null) {
            throw new IllegalArgumentException("Katman null olamaz");
        }
        this.c = e2;
    }

    public void a(String string, double d2) {
        if (string == null || string.isBlank()) {
            return;
        }
        this.d.add(string);
        if (d2 > 0.0) {
            this.e += d2;
        }
    }

    public boolean g() {
        return this.d.size() >= 5;
    }

    public String h() {
        return String.format("%-10s | %-22s | %-10s | %12d | %14.2f", this.a, this.b, this.c.b(), this.d.size(), this.e);
    }

    public String toString() {
        return String.format("Customer{id='%s', name='%s', tier=%s, orders=%d, spent=%.2f}", this.a, this.b, this.c.b(), this.d.size(), this.e);
    }
}

