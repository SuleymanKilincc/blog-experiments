import java.util.Scanner;

/**
 * Store Management System — entry point
 *
 * Usage:
 *   java Main < input.txt
 *   java Main          (interactive mode)
 *
 * Behaviour rules:
 *   • All input is read from stdin, output is written to stdout.
 *   • Lines starting with '#' are treated as comments and ignored.
 *   • Empty lines are ignored.
 *   • The program is deterministic: it uses no randomness and no system clock.
 *   • When EOF is reached (stdin is closed), the program ends normally.
 *
 * For the supported commands see {@link CommandInterpreter}.
 */
public class Main {

    private static final String BANNER =
            "╔══════════════════════════════════════════╗\n" +
            "║     MAĞAZA YÖNETİM SİSTEMİ v1.0         ║\n" +
            "║     Deterministik Sipariş & Stok         ║\n" +
            "╚══════════════════════════════════════════╝";

    public static void main(String[] args) {
        System.out.println(BANNER);
        System.out.println();

        Scanner           scanner     = new Scanner(System.in);
        Store             store       = new Store();
        CommandInterpreter interpreter = new CommandInterpreter(store);

        while (scanner.hasNextLine()) {
            String line    = scanner.nextLine();
            String trimmed = line.trim();

            // Skip comment lines and empty lines
            if (trimmed.isEmpty() || trimmed.startsWith("#")) continue;

            interpreter.execute(trimmed);
        }

        scanner.close();
        System.out.println();
        System.out.println("Sistem kapatıldı.");
    }
}
