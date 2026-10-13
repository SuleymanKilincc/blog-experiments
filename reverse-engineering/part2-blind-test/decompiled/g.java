/*
 * Decompiled with CFR 0.152.
 */
import java.util.Scanner;

public class g {
    private static final String a = "\u2554\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2557\n\u2551     MA\u011eAZA Y\u00d6NET\u0130M S\u0130STEM\u0130 v1.0         \u2551\n\u2551     Deterministik Sipari\u015f & Stok         \u2551\n\u255a\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u255d";

    public static void main(String[] stringArray) {
        System.out.println(a);
        System.out.println();
        Scanner scanner = new Scanner(System.in);
        m m2 = new m();
        b b2 = new b(m2);
        while (scanner.hasNextLine()) {
            String string = scanner.nextLine();
            String string2 = string.trim();
            if (string2.isEmpty() || string2.startsWith("#")) continue;
            b2.a(string2);
        }
        scanner.close();
        System.out.println();
        System.out.println("Sistem kapat\u0131ld\u0131.");
    }
}

