import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

// Reconstructed from the obfuscated Main: a four-player console Monopoly on Istanbul districts.
// Players roll for turn order, then take turns moving, buying, paying rent and going bankrupt
// until one is left.
public class Main {
    static String[] tokens = new String[]{"Araba", "Gemi", "Uçak", "Tren"};
    static int[] startRolls = new int[4];
    static List<Property> board = new ArrayList<>();
    static List<Player> players = new ArrayList<>();
    static Scanner input = new Scanner(System.in);
    static Random dice = new Random();

    public static void main(String[] args) {
        setUpBoard();
        System.out.println("========================================");
        System.out.println("      MONOPOLY OYUNUNA HOS GELDINIZ     ");
        System.out.println("========================================\n");
        System.out.println("Karakterler:");
        System.out.println("1. Araba \t 2. Gemi");
        System.out.println("3. Ucak  \t 4. Tren\n");
        System.out.println(">>> Baslangic sirasi icin [ENTER] tusuna basin...");
        rollForTurnOrder();
        sortTokensByStartRoll();
        createPlayers();

        System.out.println("\n----------- OYUN SIRALAMASI -----------");
        for (int i = 0; i < players.size(); ++i) {
            System.out.println(i + 1 + ". " + players.get(i).name);
        }
        System.out.println("---------------------------------------");
        System.out.println("\n>>> OYUN BASLIYOR! Lider: " + tokens[0].toUpperCase());

        int turn = 0;
        while (players.size() > 1) {
            Player player = players.get(turn % players.size());
            System.out.println("\n============================================================");
            System.out.printf("SIRA: %-10s | BAKIYE: %.2f M $ | KONUM: %d%n", player.name.toUpperCase(), player.balance, player.position);
            System.out.println("============================================================");
            System.out.println(">>> Zar atmak icin [ENTER] yapin...");
            input.nextLine();

            int[] roll = rollDice();
            int rollTotal = roll[0] + roll[1];
            System.out.println("[ZAR] Atilan: " + roll[0] + " - " + roll[1] + " (Toplam: " + rollTotal + ")");
            int steps;
            if (roll[0] == roll[1]) {
                System.out.println("!!! DUSES GELDI! Ekstra zar atiliyor...");
                int[] bonus = rollDice();
                int bonusTotal = bonus[0] + bonus[1];
                System.out.println("[ZAR] 2. Atis: " + bonus[0] + " - " + bonus[1] + " (Toplam: " + bonusTotal + ")");
                steps = rollTotal + bonusTotal;
                System.out.println(">>> Toplam Ilerleme: " + steps + " kare");
            } else {
                steps = rollTotal;
            }

            player.position = (player.position + steps) % board.size();
            Property square = board.get(player.position);
            String status = square.owner == null ? "SATILIK" : square.owner;
            System.out.println("\n[KONUM]: " + square.name.toUpperCase());
            System.out.println("(Kare: " + square.squareNumber + " | Durum: " + status + ")");

            if (square.owner == null) {
                offerPurchase(player, square);
                ++turn;
                continue;
            }
            if (square.owner.equals(player.name)) {
                System.out.println("\n[INFO] Burasi zaten sizin mulkunuz.");
                ++turn;
                continue;
            }

            System.out.println("\n$$$ KIRA VAKTI! $$$");
            System.out.printf("Mulk Sahibi: %s | Odenecek: %.2f M $%n", square.owner, square.rent);
            // Rent is taken in full and paid to the owner even when it leaves the payer below zero.
            player.balance -= square.rent;
            for (Player owner : players) {
                if (!owner.name.equals(square.owner)) continue;
                owner.balance += square.rent;
                System.out.printf(">>> TRANSFER: %s hesabina +%.2f M $ eklendi.%n", owner.name, square.rent);
                break;
            }
            if (player.balance >= 0.0) {
                System.out.printf("\n[OK] Odeme Basarili. Kalan: %.2f M $%n", player.balance);
                ++turn;
                continue;
            }

            System.out.println("\n!!! IFLAS !!!");
            System.out.println("OYUNCU ELENDI: " + player.name);
            for (Property property : board) {
                if (property.owner == null || !property.owner.equals(player.name)) continue;
                property.owner = null;
                System.out.println(">>> Iade: " + property.name + " artik sahipsiz.");
            }
            // turn is not advanced: after the removal, the same index already points at the next player
            players.remove(player);
        }

        System.out.println("\n############################################");
        System.out.println("#              OYUN SONA ERDI              #");
        System.out.println("############################################");
        System.out.println("KAZANAN: " + players.get(0).name.toUpperCase());
        System.out.printf("KASA: %.2f M $%n", players.get(0).balance);
        System.out.println("############################################");
    }

    static void offerPurchase(Player player, Property square) {
        System.out.println("---------------------------------------");
        System.out.printf("Tapu Fiyati: %.2f M $ \nKira Getirisi: %.2f M $%n", square.price, square.rent);
        System.out.println("---------------------------------------");
        System.out.println("[B] Satin Al  |  [P] Pas Gec");
        System.out.print("Secim: ");
        String choice = input.nextLine();
        if (choice.equalsIgnoreCase("b")) {
            if (player.balance >= square.price) {
                player.balance -= square.price;
                square.owner = player.name;
                System.out.println("\n+++ Satin alma basarili! " + square.name + " artik sizin.");
                System.out.printf("Kalan Bakiye: %.2f M $%n", player.balance);
            } else {
                System.out.println("\n[!] Yetersiz bakiye! Islem iptal.");
            }
        } else {
            System.out.println("\n>>> Pas gecildi.");
        }
    }

    public static void setUpBoard() {
        board.add(new Property("Esenler", 0.5, 0.1, 1));
        board.add(new Property("Bagcilar", 0.5, 0.1, 2));
        board.add(new Property("Sultanbeyli", 0.6, 0.15, 3));
        board.add(new Property("Gungoren", 0.6, 0.15, 4));
        board.add(new Property("Zeytinburnu", 0.7, 0.2, 5));
        board.add(new Property("Gaziosmanpasa", 0.7, 0.2, 6));
        board.add(new Property("Uskudar", 0.8, 0.25, 7));
        board.add(new Property("Umraniye", 0.8, 0.25, 8));
        board.add(new Property("Kartal", 0.9, 0.3, 9));
        board.add(new Property("Pendik", 0.9, 0.3, 10));
        board.add(new Property("Maltepe", 1.0, 0.35, 11));
        board.add(new Property("Kagithane", 1.1, 0.35, 12));
        board.add(new Property("Fatih", 1.3, 0.4, 13));
        board.add(new Property("Beyoglu", 1.4, 0.45, 14));
        board.add(new Property("Sisli", 1.5, 0.5, 15));
        board.add(new Property("Mecidiyekoy", 1.6, 0.55, 16));
        board.add(new Property("Besiktas", 1.8, 0.6, 17));
        board.add(new Property("Bakirkoy", 2.0, 0.65, 18));
        board.add(new Property("Atakoy", 2.2, 0.7, 19));
        board.add(new Property("Florya", 2.4, 0.8, 20));
        board.add(new Property("Atasehir", 2.5, 0.85, 21));
        board.add(new Property("Kadikoy", 2.7, 0.9, 22));
        board.add(new Property("Bostanci", 2.9, 1.0, 23));
        board.add(new Property("Caddebostan", 3.2, 1.1, 24));
        board.add(new Property("Suadiye", 3.5, 1.2, 25));
        board.add(new Property("Kalamis", 3.8, 1.3, 26));
        board.add(new Property("Levent", 4.0, 1.4, 27));
        board.add(new Property("Etiler", 4.2, 1.5, 28));
        board.add(new Property("Nisantasi", 4.5, 1.7, 29));
        board.add(new Property("Bebek", 5.0, 2.0, 30));
    }

    // Each token starts with 15 M $ on the first square, in the order sortTokensByStartRoll left them.
    public static void createPlayers() {
        for (String token : tokens) {
            players.add(new Player(token, 15.0, 0));
        }
    }

    public static int[] rollDice() {
        int first = dice.nextInt(6) + 1;
        int second = dice.nextInt(6) + 1;
        return new int[]{first, second};
    }

    // Waits for an empty line, then rolls two dice for each token.
    public static void rollForTurnOrder() {
        while (true) {
            String line = input.nextLine();
            if (line.isEmpty()) {
                for (int i = 0; i < 4; ++i) {
                    int[] roll = rollDice();
                    startRolls[i] = roll[0] + roll[1];
                    System.out.println(tokens[i] + " : " + startRolls[i] + " atti");
                }
                break;
            }
            System.out.println("Yanlis tus! Devam etmek icin [ENTER] tusuna basiniz.");
        }
    }

    // Bubble sort, highest roll first; tokens move with their rolls. Ties keep their order.
    public static void sortTokensByStartRoll() {
        for (int i = 0; i < startRolls.length - 1; ++i) {
            for (int j = 0; j < startRolls.length - i - 1; ++j) {
                if (startRolls[j] >= startRolls[j + 1]) continue;
                int roll = startRolls[j];
                startRolls[j] = startRolls[j + 1];
                startRolls[j + 1] = roll;
                String token = tokens[j];
                tokens[j] = tokens[j + 1];
                tokens[j + 1] = token;
            }
        }
    }
}
