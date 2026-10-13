import java.util.Scanner;

// Reconstructed from obfuscated class "g", the entry point: prints a banner, then runs every
// non-empty line of stdin that doesn't start with "#" as a command.
public class StoreApp {
    private static final String BANNER = "╔══════════════════════════════════════════╗\n║     MAĞAZA YÖNETİM SİSTEMİ v1.0         ║\n║     Deterministik Sipariş & Stok         ║\n╚══════════════════════════════════════════╝";

    public static void main(String[] args) {
        System.out.println(BANNER);
        System.out.println();
        Scanner scanner = new Scanner(System.in);
        Store store = new Store();
        CommandProcessor processor = new CommandProcessor(store);
        while (scanner.hasNextLine()) {
            String raw = scanner.nextLine();
            String line = raw.trim();
            if (line.isEmpty() || line.startsWith("#")) continue;
            processor.process(line);
        }
        scanner.close();
        System.out.println();
        System.out.println("Sistem kapatıldı.");
    }
}
