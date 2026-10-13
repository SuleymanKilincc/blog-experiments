/*
 * Decompiled with CFR 0.152.
 */
public class l {
    private static final int a = 5;
    private final m b;

    public l(m m2) {
        this.b = m2;
    }

    public String a() {
        StringBuilder stringBuilder = new StringBuilder();
        l.a(stringBuilder, "\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550 KATEGOR\u0130LER \u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550");
        l.a(stringBuilder, String.format("%-12s | %-20s | %8s | %s", "ID", "Ad", "\u0130ndirim", "Toplu \u0130ndirim"));
        l.a(stringBuilder, "\u2500".repeat(65));
        if (this.b.a().isEmpty()) {
            l.a(stringBuilder, "  (hen\u00fcz kategori yok)");
        } else {
            for (a a2 : this.b.a()) {
                l.a(stringBuilder, a2.f());
            }
        }
        l.a(stringBuilder, "\u2550".repeat(65));
        return stringBuilder.toString().stripTrailing();
    }

    public String b() {
        StringBuilder stringBuilder = new StringBuilder();
        l.a(stringBuilder, "\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550 \u00dcR\u00dcNLER \u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550");
        l.a(stringBuilder, String.format("%-10s | %-25s | %-12s | %10s | %6s | %9s", "ID", "Ad", "Kategori", "Fiyat", "Stok", "Sat\u0131lan"));
        l.a(stringBuilder, "\u2500".repeat(85));
        if (this.b.b().isEmpty()) {
            l.a(stringBuilder, "  (hen\u00fcz \u00fcr\u00fcn yok)");
        } else {
            for (k k2 : this.b.b()) {
                l.a(stringBuilder, k2.g());
            }
        }
        l.a(stringBuilder, "\u2550".repeat(85));
        return stringBuilder.toString().stripTrailing();
    }

    public String c() {
        StringBuilder stringBuilder = new StringBuilder();
        l.a(stringBuilder, "\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550 M\u00dc\u015eTER\u0130LER \u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550");
        l.a(stringBuilder, String.format("%-10s | %-22s | %-10s | %12s | %14s", "ID", "Ad", "Katman", "Sipari\u015f", "Harcama"));
        l.a(stringBuilder, "\u2500".repeat(80));
        if (this.b.c().isEmpty()) {
            l.a(stringBuilder, "  (hen\u00fcz m\u00fc\u015fteri yok)");
        } else {
            for (d d2 : this.b.c()) {
                l.a(stringBuilder, d2.h());
            }
        }
        l.a(stringBuilder, "\u2550".repeat(80));
        return stringBuilder.toString().stripTrailing();
    }

    public String d() {
        StringBuilder stringBuilder = new StringBuilder();
        l.a(stringBuilder, "\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550 S\u0130PAR\u0130\u015eLER \u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550");
        l.a(stringBuilder, String.format("%-12s | %-10s | %-11s | %6s | %12s", "Sipari\u015f ID", "M\u00fc\u015fteri", "Durum", "Adet", "Toplam"));
        l.a(stringBuilder, "\u2500".repeat(60));
        if (this.b.d().isEmpty()) {
            l.a(stringBuilder, "  (hen\u00fcz sipari\u015f yok)");
        } else {
            for (h h2 : this.b.d()) {
                l.a(stringBuilder, String.format("%-12s | %-10s | %-11s | %6d | %12.2f", h2.a(), h2.b(), h2.c().a(), h2.u(), h2.n()));
            }
        }
        l.a(stringBuilder, "\u2550".repeat(60));
        return stringBuilder.toString().stripTrailing();
    }

    public String e() {
        StringBuilder stringBuilder = new StringBuilder();
        l.a(stringBuilder, "\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550 KUPONLAR \u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550");
        l.a(stringBuilder, String.format("%-14s | %8s | %12s | %10s | %s", "Kod", "\u0130ndirim", "Min Tutar", "Limit", "Kullan\u0131m"));
        l.a(stringBuilder, "\u2500".repeat(63));
        if (this.b.e().isEmpty()) {
            l.a(stringBuilder, "  (hen\u00fcz kupon yok)");
        } else {
            for (c c2 : this.b.e()) {
                l.a(stringBuilder, c2.h());
            }
        }
        l.a(stringBuilder, "\u2550".repeat(63));
        return stringBuilder.toString().stripTrailing();
    }

    public String a(String string) {
        h h2 = this.b.f(string);
        if (h2 == null) {
            return "ERROR: Sipari\u015f bulunamad\u0131: " + string;
        }
        StringBuilder stringBuilder = new StringBuilder();
        l.a(stringBuilder, "\u2550\u2550\u2550\u2550\u2550\u2550 S\u0130PAR\u0130\u015e DETAYI: " + string + " \u2550\u2550\u2550\u2550\u2550\u2550");
        l.a(stringBuilder, "M\u00fc\u015fteri  : " + h2.b());
        l.a(stringBuilder, "Durum    : " + h2.c().a());
        if (h2.d() != null) {
            l.a(stringBuilder, "Kupon    : " + h2.d());
        }
        l.a(stringBuilder, "");
        l.a(stringBuilder, String.format("  %-10s | %-25s | %4s | %10s | %7s | %7s | %11s", "\u00dcr\u00fcn ID", "Ad", "Adet", "BirimFiyat", "KatIndir", "TplIndir", "Subtotal"));
        l.a(stringBuilder, "  " + "\u2500".repeat(83));
        if (h2.e().isEmpty()) {
            l.a(stringBuilder, "  (sipari\u015f bo\u015f)");
        } else {
            for (i i2 : h2.e()) {
                l.a(stringBuilder, i2.j());
            }
        }
        if (h2.q() || !h2.e().isEmpty() && h2.p()) {
            l.a(stringBuilder, "");
            double d2 = h2.f() - h2.g();
            l.a(stringBuilder, String.format("  %-38s: %12.2f", "\u0130ndirim \u00f6ncesi ara toplam", h2.f()));
            l.a(stringBuilder, String.format("  %-38s: %12.2f", "Kalem indirimi tasarrufu", h2.g()));
            l.a(stringBuilder, String.format("  %-38s: %12.2f", "Kalem indirimleri sonras\u0131", d2));
            if (h2.h() > 0.0) {
                l.a(stringBuilder, String.format("  %-38s: %12.2f", String.format("M\u00fc\u015fteri katman\u0131 (%%%.1f)", h2.h()), -h2.i()));
            }
            if (h2.j() > 0.0) {
                l.a(stringBuilder, String.format("  %-38s: %12.2f", String.format("Sadakat bonusu (%%%.1f)", h2.j()), -h2.k()));
            }
            if (h2.l() > 0.0) {
                l.a(stringBuilder, String.format("  %-38s: %12.2f", String.format("Kupon indirimi (%%%.1f)", h2.l()), -h2.m()));
            }
            l.a(stringBuilder, "  " + "\u2550".repeat(53));
            l.a(stringBuilder, String.format("  %-38s: %12.2f", "\u00d6DENECEK TOPLAM", h2.n()));
            l.a(stringBuilder, String.format("  %-38s: %12.2f", "Toplam tasarruf", h2.o()));
        }
        l.a(stringBuilder, "\u2550".repeat(55));
        return stringBuilder.toString().stripTrailing();
    }

    public String b(String string) {
        d d2 = this.b.e(string);
        if (d2 == null) {
            return "ERROR: M\u00fc\u015fteri bulunamad\u0131: " + string;
        }
        StringBuilder stringBuilder = new StringBuilder();
        l.a(stringBuilder, "\u2550\u2550\u2550\u2550\u2550\u2550 M\u00dc\u015eTER\u0130 RAPORU: " + string + " \u2550\u2550\u2550\u2550\u2550\u2550");
        l.a(stringBuilder, "Ad               : " + d2.b());
        l.a(stringBuilder, "Katman           : " + d2.c().b() + String.format(" (%%%.0f indirim)", d2.c().a()));
        l.a(stringBuilder, "Tamamlanan sipari\u015f: " + d2.d());
        l.a(stringBuilder, String.format("Toplam harcama   : %.2f", d2.e()));
        l.a(stringBuilder, "Sadakat bonusu   : " + (d2.g() ? "AKTIF (%2 ek indirim)" : "pasif (gerekli: 5 sipari\u015f)"));
        l.a(stringBuilder, "");
        l.a(stringBuilder, "Sipari\u015f Ge\u00e7mi\u015fi:");
        l.a(stringBuilder, "\u2500".repeat(55));
        if (d2.f().isEmpty()) {
            l.a(stringBuilder, "  (hen\u00fcz tamamlanm\u0131\u015f sipari\u015f yok)");
        } else {
            l.a(stringBuilder, String.format("  %-12s | %6s | %10s | %12s", "Sipari\u015f", "\u00dcr\u00fcn", "Ham Tutar", "\u00d6denen"));
            l.a(stringBuilder, "  " + "\u2500".repeat(50));
            for (String string2 : d2.f()) {
                h h2 = this.b.f(string2);
                if (h2 == null) continue;
                l.a(stringBuilder, String.format("  %-12s | %6d | %10.2f | %12.2f", string2, h2.u(), h2.f(), h2.n()));
            }
        }
        l.a(stringBuilder, "\u2550".repeat(55));
        return stringBuilder.toString().stripTrailing();
    }

    public String f() {
        StringBuilder stringBuilder = new StringBuilder();
        l.a(stringBuilder, "\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550 STOK RAPORU \u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550");
        l.a(stringBuilder, String.format("%-10s | %-25s | %6s | %9s | %13s", "\u00dcr\u00fcn ID", "Ad", "Stok", "Sat\u0131lan", "Durum"));
        l.a(stringBuilder, "\u2500".repeat(72));
        if (this.b.b().isEmpty()) {
            l.a(stringBuilder, "  (hen\u00fcz \u00fcr\u00fcn yok)");
        } else {
            int n = 0;
            int n2 = 0;
            int n3 = 0;
            for (k k2 : this.b.b()) {
                String string;
                if (k2.e() == 0) {
                    string = "OUT-OF-STOCK";
                    ++n;
                } else if (k2.e() < 5) {
                    string = "LOW-STOCK";
                    ++n2;
                } else {
                    string = "IN-STOCK";
                    ++n3;
                }
                l.a(stringBuilder, String.format("%-10s | %-25s | %6d | %9d | %s", k2.a(), k2.b(), k2.e(), k2.f(), string));
            }
            l.a(stringBuilder, "\u2500".repeat(72));
            l.a(stringBuilder, String.format("\u00d6zet: IN-STOCK=%d  LOW-STOCK=%d  OUT-OF-STOCK=%d", n3, n2, n));
        }
        l.a(stringBuilder, "\u2550".repeat(72));
        return stringBuilder.toString().stripTrailing();
    }

    private static void a(StringBuilder stringBuilder, String string) {
        stringBuilder.append(string).append("\n");
    }
}

