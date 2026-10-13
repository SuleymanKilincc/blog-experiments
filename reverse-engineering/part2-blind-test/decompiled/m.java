/*
 * Decompiled with CFR 0.152.
 */
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class m {
    private final Map a = new LinkedHashMap();
    private final Map b = new LinkedHashMap();
    private final Map c = new LinkedHashMap();
    private final Map d = new LinkedHashMap();
    private final Map e = new LinkedHashMap();
    private final f f = new f(this);

    public String a(String string, String string2, double d2) {
        if (this.a.containsKey(string)) {
            return "ERROR: Kategori zaten mevcut: " + string;
        }
        try {
            this.a.put(string, new a(string, string2, d2));
        }
        catch (IllegalArgumentException illegalArgumentException) {
            return "ERROR: " + illegalArgumentException.getMessage();
        }
        return "OK: Kategori eklendi: " + string + " [" + string2 + ", %" + d2 + " indirim]";
    }

    public String a(String string, double d2, double d3) {
        a a2 = (a)this.a.get(string);
        if (a2 == null) {
            return "ERROR: Kategori bulunamad\u0131: " + string;
        }
        try {
            a2.a(d2, d3);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            return "ERROR: " + illegalArgumentException.getMessage();
        }
        return String.format("OK: %s kategorisine toplu indirim ayarland\u0131: %.2f+ \u2192 +%%%.1f", string, d2, d3);
    }

    public String a(String string, String string2, String string3, double d2, int n) {
        if (this.b.containsKey(string)) {
            return "ERROR: \u00dcr\u00fcn zaten mevcut: " + string;
        }
        if (!this.a.containsKey(string3)) {
            return "ERROR: Kategori bulunamad\u0131: " + string3;
        }
        try {
            this.b.put(string, new k(string, string2, string3, d2, n));
        }
        catch (IllegalArgumentException illegalArgumentException) {
            return "ERROR: " + illegalArgumentException.getMessage();
        }
        return String.format("OK: \u00dcr\u00fcn eklendi: %s [%s, %.2f TL, %d adet]", string, string2, d2, n);
    }

    public String a(String string, int n) {
        k k2 = (k)this.b.get(string);
        if (k2 == null) {
            return "ERROR: \u00dcr\u00fcn bulunamad\u0131: " + string;
        }
        try {
            k2.d(n);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            return "ERROR: " + illegalArgumentException.getMessage();
        }
        return String.format("OK: %s yenilendi, eklenen: %d, yeni stok: %d", string, n, k2.e());
    }

    public String a(String string, double d2) {
        k k2 = (k)this.b.get(string);
        if (k2 == null) {
            return "ERROR: \u00dcr\u00fcn bulunamad\u0131: " + string;
        }
        double d3 = k2.d();
        try {
            k2.a(d2);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            return "ERROR: " + illegalArgumentException.getMessage();
        }
        return String.format("OK: %s fiyat\u0131 g\u00fcncellendi: %.2f \u2192 %.2f", string, d3, d2);
    }

    public String a(String string, String string2, e e2) {
        if (this.c.containsKey(string)) {
            return "ERROR: M\u00fc\u015fteri zaten mevcut: " + string;
        }
        try {
            this.c.put(string, new d(string, string2, e2));
        }
        catch (IllegalArgumentException illegalArgumentException) {
            return "ERROR: " + illegalArgumentException.getMessage();
        }
        return "OK: M\u00fc\u015fteri eklendi: " + string + " [" + string2 + ", " + e2.b() + "]";
    }

    public String a(String string, e e2) {
        d d2 = (d)this.c.get(string);
        if (d2 == null) {
            return "ERROR: M\u00fc\u015fteri bulunamad\u0131: " + string;
        }
        e e3 = d2.c();
        d2.a(e2);
        return String.format("OK: %s katman\u0131 de\u011fi\u015ftirildi: %s \u2192 %s", string, e3.b(), e2.b());
    }

    public String b(String string, double d2, double d3) {
        if (this.e.containsKey(string)) {
            return "ERROR: Kupon zaten mevcut: " + string;
        }
        try {
            this.e.put(string, new c(string, d2, d3));
        }
        catch (IllegalArgumentException illegalArgumentException) {
            return "ERROR: " + illegalArgumentException.getMessage();
        }
        return String.format("OK: Kupon eklendi: %s [%%%.1f indirim, min %.2f TL]", string, d2, d3);
    }

    public String a(String string, double d2, double d3, int n) {
        if (this.e.containsKey(string)) {
            return "ERROR: Kupon zaten mevcut: " + string;
        }
        try {
            this.e.put(string, new c(string, d2, d3, n));
        }
        catch (IllegalArgumentException illegalArgumentException) {
            return "ERROR: " + illegalArgumentException.getMessage();
        }
        return String.format("OK: Kupon eklendi: %s [%%%.1f indirim, min %.2f TL, max %d kullan\u0131m]", string, d2, d3, n);
    }

    public String a(String string, String string2) {
        if (this.d.containsKey(string)) {
            return "ERROR: Sipari\u015f zaten mevcut: " + string;
        }
        if (!this.c.containsKey(string2)) {
            return "ERROR: M\u00fc\u015fteri bulunamad\u0131: " + string2;
        }
        this.d.put(string, new h(string, string2));
        return "OK: Sipari\u015f olu\u015fturuldu: " + string + " (m\u00fc\u015fteri: " + string2 + ")";
    }

    public String a(String string, String string2, int n) {
        h h2 = (h)this.d.get(string);
        if (h2 == null) {
            return "ERROR: Sipari\u015f bulunamad\u0131: " + string;
        }
        if (!h2.p()) {
            return "ERROR: Sipari\u015f " + string + " a\u00e7\u0131k de\u011fil (durum: " + h2.c().a() + ")";
        }
        if (h2.b(string2)) {
            return "ERROR: \u00dcr\u00fcn " + string2 + " zaten sipari\u015fte mevcut: " + string;
        }
        k k2 = (k)this.b.get(string2);
        if (k2 == null) {
            return "ERROR: \u00dcr\u00fcn bulunamad\u0131: " + string2;
        }
        if (n <= 0) {
            return "ERROR: Miktar pozitif olmal\u0131: " + n;
        }
        if (!k2.a(n)) {
            return String.format("ERROR: %s i\u00e7in yetersiz stok (istenen: %d, mevcut: %d)", string2, n, k2.e());
        }
        h2.a(new i(string2, k2.b(), n, k2.d()));
        return String.format("OK: %d x %s sipari\u015fe eklendi (%s)", n, string2, string);
    }

    public String b(String string, String string2) {
        h h2 = (h)this.d.get(string);
        if (h2 == null) {
            return "ERROR: Sipari\u015f bulunamad\u0131: " + string;
        }
        if (!h2.p()) {
            return "ERROR: Sipari\u015f " + string + " a\u00e7\u0131k de\u011fil";
        }
        if (!this.e.containsKey(string2)) {
            return "ERROR: Kupon bulunamad\u0131: " + string2;
        }
        h2.a(string2);
        return "OK: Kupon " + string2 + " sipari\u015fe uyguland\u0131: " + string;
    }

    public String a(String string) {
        h h2 = (h)this.d.get(string);
        if (h2 == null) {
            return "ERROR: Sipari\u015f bulunamad\u0131: " + string;
        }
        if (!h2.p()) {
            return "ERROR: Sipari\u015f " + string + " a\u00e7\u0131k de\u011fil";
        }
        if (h2.e().isEmpty()) {
            return "ERROR: Sipari\u015f " + string + " bo\u015f, \u00fcr\u00fcn ekleyin";
        }
        boolean bl = this.f.a(h2);
        if (!bl) {
            c c2;
            String string2 = h2.d();
            c c3 = c2 = string2 != null ? (c)this.e.get(string2) : null;
            if (c2 == null) {
                return "ERROR: Bilinmeyen kupon: " + string2;
            }
            return String.format("ERROR: Kupon %s ge\u00e7ersiz (min tutar: %.2f, mevcut tutar kar\u015f\u0131lanm\u0131yor)", string2, c2.c());
        }
        for (i i2 : h2.e()) {
            k k2 = (k)this.b.get(i2.a());
            if (k2 == null) continue;
            k2.c(i2.c());
        }
        d d2 = (d)this.c.get(h2.b());
        if (d2 != null) {
            d2.a(string, h2.n());
        }
        h2.s();
        return String.format("OK: Sipari\u015f %s tamamland\u0131 | Ara toplam: %.2f | \u0130ndirim: %.2f | \u00d6denecek: %.2f", string, h2.f(), h2.o(), h2.n());
    }

    public String b(String string) {
        h h2 = (h)this.d.get(string);
        if (h2 == null) {
            return "ERROR: Sipari\u015f bulunamad\u0131: " + string;
        }
        if (!h2.p()) {
            return "ERROR: Sipari\u015f " + string + " a\u00e7\u0131k de\u011fil (durum: " + h2.c().a() + ")";
        }
        for (i i2 : h2.e()) {
            k k2 = (k)this.b.get(i2.a());
            if (k2 == null) continue;
            k2.b(i2.c());
        }
        h2.t();
        return "OK: Sipari\u015f " + string + " iptal edildi, stoklar iade edildi";
    }

    public k c(String string) {
        return (k)this.b.get(string);
    }

    public a d(String string) {
        return (a)this.a.get(string);
    }

    public d e(String string) {
        return (d)this.c.get(string);
    }

    public h f(String string) {
        return (h)this.d.get(string);
    }

    public c g(String string) {
        return (c)this.e.get(string);
    }

    public Collection a() {
        return this.a.values();
    }

    public Collection b() {
        return this.b.values();
    }

    public Collection c() {
        return this.c.values();
    }

    public Collection d() {
        return this.d.values();
    }

    public Collection e() {
        return this.e.values();
    }
}

