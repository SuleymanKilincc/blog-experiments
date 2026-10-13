import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

// Reconstructed from the obfuscated Main: asks for a GPU, a CPU and two games, then prints
// specs, a combined score, a bottleneck verdict, game requirements and an FPS estimate.
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Sisteminizi puanlamamız için , lütfen sırasısyla sistem parçalarınızı giriniz ");

        Map<String, GraphicsCard> gpus = new HashMap<>();
        gpus.put("RTX 2060", new GraphicsCard("Nvidia RTX 2060", "GDDR6", 44, 160, 6, 1920, 12, 192, 1, 1750));
        gpus.put("RTX 2070", new GraphicsCard("Nvidia RTX 2070", "GDDR6", 46, 185, 8, 2304, 12, 256, 1, 1750));
        gpus.put("RTX 2080", new GraphicsCard("Nvidia RTX 2080", "GDDR6", 48, 215, 8, 2944, 12, 256, 2, 1800));
        gpus.put("RTX 3050", new GraphicsCard("Nvidia RTX 3050", "GDDR6", 44, 130, 8, 2560, 8, 128, 1, 1822));
        gpus.put("RTX 3060", new GraphicsCard("Nvidia RTX 3060", "GDDR6", 45, 170, 8, 3584, 8, 128, 1, 1850));
        gpus.put("RTX 3070", new GraphicsCard("Nvidia RTX 3070", "GDDR6", 53, 220, 8, 5888, 8, 256, 2, 1815));
        gpus.put("RTX 3080", new GraphicsCard("Nvidia RTX 3080", "GDDR6X", 62, 370, 10, 8704, 8, 370, 4, 1905));
        gpus.put("RTX 3090", new GraphicsCard("Nvidia RTX 3090", "GDDR6X", 66, 350, 24, 10496, 8, 384, 4, 1740));
        gpus.put("RTX 4060", new GraphicsCard("Nvidia RTX 4060", "GDDR6", 52, 115, 8, 3072, 5, 128, 1, 2125));
        gpus.put("RTX 4070", new GraphicsCard("Nvidia RTX 4070", "GDDR6X", 60, 200, 12, 5888, 5, 192, 2, 2508));
        gpus.put("RTX 4080", new GraphicsCard("Nvidia RTX 4080", "GDDR6X", 69, 320, 16, 9728, 5, 256, 4, 2655));
        gpus.put("RTX 4090", new GraphicsCard("Nvidia RTX 4090", "GDDR6X", 80, 450, 24, 16384, 4, 384, 4, 2595));
        gpus.put("RTX 5050", new GraphicsCard("Nvidia RTX 5050", "GDDR6", 53, 130, 8, 2560, 5, 128, 1, 2587));
        gpus.put("RTX 5060", new GraphicsCard("Nvidia RTX 5060", "GDDR7", 58, 145, 8, 3840, 4, 128, 2, 2600));
        gpus.put("RTX 5070", new GraphicsCard("Nvidia RTX 5070", "GDDR7", 67, 250, 12, 6144, 4, 192, 4, 2715));
        gpus.put("RTX 5080", new GraphicsCard("Nvidia RTX 5080", "GDDR7", 77, 360, 16, 10752, 4, 256, 4, 2730));
        gpus.put("RTX 5090", new GraphicsCard("Nvidia RTX 5090", "GDDR7", 95, 575, 32, 21760, 4, 512, 8, 2610));
        gpus.put("RX 6600", new GraphicsCard("AMD RADEON RX 6600", "GDDR6", 44, 132, 8, 1792, 7, 128, 1, 2044));
        gpus.put("RX 6700 XT", new GraphicsCard("AMD RADEON RX 6700 XT", "GDDR6", 52, 230, 12, 2560, 7, 192, 2, 2424));
        gpus.put("RX 6800 XT", new GraphicsCard("AMD RADEON RX 6800 XT", "GDDR6", 61, 300, 16, 4608, 7, 256, 4, 2250));
        gpus.put("RX 6900 XT", new GraphicsCard("AMD RADEON RX 6900 XT", "GDDR6", 67, 300, 16, 5120, 7, 256, 4, 2250));
        gpus.put("RX 7600", new GraphicsCard("AMD RADEON RX 7600", "GDDR6", 50, 165, 8, 2048, 6, 128, 1, 2655));
        gpus.put("RX 7700 XT", new GraphicsCard("AMD RADEON RX 7700 XT", "GDDR6", 58, 245, 12, 3456, 5, 192, 2, 2544));
        gpus.put("RX 7800 XT", new GraphicsCard("AMD RADEON RX 7800 XT", "GDDR6", 64, 263, 16, 3840, 5, 256, 4, 2430));
        gpus.put("RX 7900 XT", new GraphicsCard("AMD RADEON RX 7900 XT", "GDDR6", 74, 315, 20, 5376, 5, 320, 4, 2400));
        gpus.put("RX 7900 XTX", new GraphicsCard("AMD RADEON RX 7900 XTX", "GDDR6", 79, 355, 24, 6144, 5, 384, 4, 2500));
        gpus.put("RX 9060 XT", new GraphicsCard("AMD RADEON RX 9060 XT", "GDDR6", 58, 160, 16, 2048, 4, 128, 2, 3340));
        gpus.put("RX 9070 XT", new GraphicsCard("AMD RADEON RX 9700 XT", "GDDR6", 65, 304, 16, 4096, 5, 256, 4, 3080));
        System.out.println("Sorgulamak istediğiniz ekran kartı modelini girin , (tüm harfler büyük) örnek : RTX 3060 , RTX 4060 , RX 6700 XT , RX 7900 XTX");
        String gpuKey;
        while (!gpus.containsKey(gpuKey = scanner.nextLine().strip().toUpperCase())) {
            System.out.println("Sistemde böyle bir ekran kartı yok , lütfen tekrar deneyiniz");
            System.out.println("Örnek : RTX 2080 , RTX 4090 , RX 6800 XT , RX 9700 XT");
            System.out.print("Tekrar deneyin > ");
        }
        GraphicsCard gpu = gpus.get(gpuKey);
        gpu.printSpecs();

        Map<String, Processor> cpus = new HashMap<>();
        cpus.put("INTEL CORE i3-12100", new Processor("INTEL CORE i3-12100", "DDR5", 47, 60, 10, 4, 8, 3.3, 4.3, 4800, "320 KB", "5 MB", "12 MB"));
        cpus.put("INTEL CORE i5-12600", new Processor("INTEL CORE i5-12600", "DDR5", 55, 65, 10, 6, 12, 3.3, 4.8, 4800, "448 KB", "7.5 MB", "18 MB"));
        cpus.put("INTEL CORE i5-12600K", new Processor("INTEL CORE i5-12600K", "DDR5", 61, 125, 10, 10, 16, 3.7, 4.9, 4800, "864 KB", "9.5 MB", "20 MB"));
        cpus.put("INTEL CORE i7-12700", new Processor("INTEL CORE i7-12700", "DDR5", 59, 65, 10, 12, 20, 2.1, 4.9, 4800, "900 KB", "15 MB", "25 MB"));
        cpus.put("INTEL CORE i7-12700K", new Processor("INTEL CORE i7-12700K", "DDR5", 64, 125, 10, 12, 20, 3.6, 5.0, 4800, "900 KB", "15 MB", "25 MB"));
        cpus.put("INTEL CORE i9-12900", new Processor("INTEL CORE i7-12900", "DDR5", 62, 65, 10, 16, 24, 2.4, 5.1, 4800, "1.375 MB", "14 MB", "30 MB"));
        cpus.put("INTEL CORE i9-12900K", new Processor("INTEL CORE i9-12900K", "DDR5", 68, 125, 10, 16, 24, 3.2, 5.2, 4800, "1.375 MB", "14 MB", "30 MB"));
        cpus.put("INTEL CORE i3-13100", new Processor("INTEL CORE i3-13100", "DDR5", 49, 60, 10, 4, 8, 3.4, 4.5, 4800, "320 KB", "5 MB", "12 MB"));
        cpus.put("INTEL CORE i5-13600", new Processor("INTEL CORE i5-13600", "DDR5", 64, 65, 10, 14, 20, 2.7, 5.0, 4800, "1.125 MB", "11.5 MB", "24 MB"));
        cpus.put("INTEL CORE i5-13600K", new Processor("INTEL CORE i5-13600K", "DDR5", 67, 125, 10, 14, 20, 3.5, 5.1, 5600, "1.125 MB", "20 MB", "24 MB"));
        cpus.put("INTEL CORE i7-13700", new Processor("INTEL CORE i7-13700", "DDR5", 65, 65, 10, 16, 24, 2.1, 5.2, 5600, "1.375 MB", "24 MB", "30 MB"));
        cpus.put("INTEL CORE i7-13700K", new Processor("INTEL CORE i7-13700K", "DDR5", 73, 125, 10, 16, 24, 3.4, 5.4, 5600, "1.375 MB", "24 MB", "30 MB"));
        cpus.put("INTEL CORE i9-13900", new Processor("INTEL CORE i9-13900", "DDR5", 75, 65, 10, 24, 32, 2.0, 5.6, 5600, "1.9 MB", "32 MB", "36 MB"));
        cpus.put("INTEL CORE i9-13900K", new Processor("INTEL CORE i9-13900K", "DDR5", 83, 125, 10, 24, 32, 3.9, 5.8, 5600, "1.9 MB", "32 MB", "36 MB"));
        cpus.put("INTEL CORE i3-14100", new Processor("INTEL CORE i3-14100", "DDR5", 50, 60, 10, 4, 8, 3.5, 4.7, 4800, "320 KB", "5 MB", "12 MB"));
        cpus.put("INTEL CORE i5-14600", new Processor("INTEL CORE i5-14600", "DDR5", 63, 65, 10, 14, 20, 2.7, 5.2, 5600, "1.1 MB", "20 MB", "24 MB"));
        cpus.put("INTEL CORE i5-14600K", new Processor("INTEL CORE i5-14600K", "DDR5", 69, 125, 10, 14, 20, 3.5, 5.3, 5600, "1.1 MB", "20 MB", "24 MB"));
        cpus.put("INTEL CORE i7-14700", new Processor("INTEL CORE i7-14700", "DDR5", 69, 65, 10, 20, 28, 2.1, 5.4, 5600, "1.6 MB", "28 MB", "33 MB"));
        cpus.put("INTEL CORE i7-14700K", new Processor("INTEL CORE i7-14700K", "DDR5", 78, 125, 10, 20, 28, 3.4, 5.6, 5600, "1.8 MB", "28 MB", "33 MB"));
        cpus.put("INTEL CORE i9-14900", new Processor("INTEL CORE i9-14900", "DDR5", 74, 65, 10, 24, 32, 2.0, 5.8, 5600, "1.9 MB", "32 MB", "36 MB"));
        cpus.put("INTEL CORE i9-14900K", new Processor("INTEL CORE i9-14900K", "DDR5", 82, 125, 10, 24, 32, 3.2, 6.0, 5600, "1.9 MB", "32 MB", "36 MB"));
        cpus.put("INTEL CORE i9-14900KS", new Processor("INTEL CORE i9-14900KS", "DDR5", 85, 150, 10, 24, 32, 3.2, 6.2, 5600, "1.9 MB", "32 MB", "36 MB"));
        cpus.put("RYZEN 5 5600", new Processor("AMD RYZEN 5 5600", "DDR4", 54, 65, 7, 6, 12, 3.5, 4.4, 3200, "384 KB", "3 MB", "32 MB"));
        cpus.put("RYZEN 7 5700X", new Processor("AMD RYZEN 7 5700X", "DDR4", 60, 65, 7, 8, 16, 3.4, 4.6, 3200, "512 KB", "4 MB", "32 MB"));
        cpus.put("RYZEN 7 5800X3D", new Processor("AMD RYZEN 7 5800X3D", "DDR4", 74, 105, 7, 8, 16, 3.4, 4.5, 3200, "512 KB", "4 MB", "96 MB"));
        cpus.put("RYZEN 5 7600", new Processor("AMD RYZEN 5 7600", "DDR5", 65, 65, 5, 6, 12, 3.8, 5.1, 5200, "384 KB", "6 MB", "32 MB"));
        cpus.put("RYZEN 7 7700X", new Processor("AMD RYZEN 7 7700X", "DDR5", 74, 105, 5, 8, 16, 4.5, 5.4, 5200, "512 KB", "8 MB", "32 MB"));
        cpus.put("RYZEN 7 7800X3D", new Processor("AMD RYZEN 7 7800X3D", "DDR5", 86, 120, 5, 8, 16, 4.2, 5.0, 5200, "512 KB", "8 MB", "96 MB"));
        cpus.put("RYZEN 9 7900X", new Processor("AMD RYZEN 9 7900X", "DDR5", 75, 170, 5, 12, 24, 4.7, 5.6, 5200, "768 KB", "12 MB", "64 MB"));
        cpus.put("RYZEN 9 7950X", new Processor("AMD RYZEN 9 7950X", "DDR5", 78, 170, 5, 16, 32, 4.5, 5.7, 5200, "1 MB", "16 MB", "64 MB"));
        cpus.put("RYZEN 5 9600X", new Processor("AMD RYZEN 5 9600X", "DDR5", 72, 65, 4, 6, 12, 3.9, 5.4, 5600, "480 KB", "6 MB", "32 MB"));
        cpus.put("RYZEN 7 9700X", new Processor("AMD RYZEN 7 9700X", "DDR5", 80, 65, 4, 8, 16, 3.8, 5.5, 5600, "640 KB", "8 MB", "32 MB"));
        cpus.put("RYZEN 9 9950X", new Processor("AMD RYZEN 9 9950X", "DDR5", 84, 170, 4, 16, 32, 4.3, 5.7, 5600, "1.2 MB", "16 MB", "64 MB"));
        cpus.put("RYZEN 7 9800X3D", new Processor("AMD RYZEN 7 9800X3D", "DDR5", 89, 120, 4, 8, 16, 4.7, 5.2, 5600, "640 KB", "8 MB", "96 MB"));
        cpus.put("RYZEN 9 9900X3D", new Processor("AMD RYZEN 9 9900X3D", "DDR5", 92, 120, 4, 12, 24, 4.4, 5.6, 5600, "960 KB", "12 MB", "128 MB"));
        cpus.put("RYZEN 9 9950X3D", new Processor("AMD RYZEN 9 9950X3D", "DDR5", 96, 120, 4, 16, 32, 4.3, 5.7, 5600, "1.25 MB", "16 MB", "128 MB"));
        System.out.println("Sorgulamak istediğniz işlemci modelini girin  , (tüm harfler büyük) örnek : INTEL CORE i3-12100 , INTEL CORE i7-13700K , RYZEN 7 7700X , RYZEN 7 7800X3D ");
        String cpuKey;
        while (!cpus.containsKey(cpuKey = scanner.nextLine().strip().toUpperCase())) {
            System.out.println("Sistemde böyle bir işlemci yok , lütfen tekrar deneyiniz");
            System.out.println("Örnek : INTEL CORE i3-14100 , INTEL CORE i9-12900 , INTEL CORE i9-14900K , RYZEN 9 7950X , RYZEN 9 9950X3D ");
            System.out.print("Tekrar deneyin > ");
        }
        Processor cpu = cpus.get(cpuKey);
        cpu.printSpecs();

        System.out.println("Sisteminizin toplam performans puanı");
        SystemScore score = new SystemScore();
        System.out.println(score.averageScore(gpu, cpu));
        System.out.println("--------------------");
        System.out.println("Sisteminizin darboğaz hesabı\n");
        score.printBottleneck(gpu, cpu);

        Map<String, GameInfo> games = new HashMap<>();
        System.out.println("\n--------------------------------------------------------");
        System.out.println("Sistem gereksinimlerini ve oyun bilgilerini öğrenmek istediğiniz oyunu giriniz , (tüm harfler büyük) örnek : THE LAST OF US PART 2");
        games.put("THE LAST OF US REMAKE", new GameInfo("The Last OF Us", "Naughty Dog", "Aksiyon - Hayatta Kalma", 97, 2024, 32, "INTEL CORE i7 11400k", "RTX 3060", 80, "Windows 11", "SSD"));
        games.put("RED DEAD REDEMPT\u0130ON 2", new GameInfo("Red Redemption 2", "Rockstar Games", "A\u00e7\u0131k D\u00fcnya - Aksiyon", 97, 2018, 16, "INTEL CORE i7 9920k", "RTX 2060", 130, "Windows 10", "SSD-HDD"));
        games.put("GOD OF WAR", new GameInfo("God Of War", "Sony Santa Monica", "Aksiyon - Macera", 94, 2018, 16, "INTEL CORE i7 10770k", "RTX 2060", 70, "Windows 10", "SSD-HDD"));
        games.put("CYBERPUNK 2077", new GameInfo("Cyberpunk 2077", "CD Project Red", "A\u00e7\u0131k D\u00fcnya - Aksiyon - Rol Yapma", 86, 2019, 32, "INTEL CORE i7 11770k", "RTX 4060", 90, "Windows 11", "SSD"));
        games.put("CITIES SKYLINES 2", new GameInfo("Cities Skylines 2", "Colossal Order", "Strateji - \u0130n\u015fa", 75, 2023, -32, "INTEL CORE i7 13900k", "RTX 3080", 60, "Windows 11", "SSD"));
        games.put("DYING LIGHT 2", new GameInfo("Dying Light 2: Stay Human", "Techland", "Aksiyon - Rol Yapma", 77, 2022, 16, "INTEL CORE i5 8600K", "RTX 2060", 60, "Windows 10", "SSD"));
        games.put("DYING LIGHT: THE BEAST", new GameInfo("Dying Light: The Beast", "Techland", "Aksiyon - Hayatta Kalma", 80, 2025, 32, "INTEL CORE i7 12700K", "RTX 4070", 90, "Windows 11", "SSD"));
        games.put("STATE OF DECAY 2", new GameInfo("State of Decay 2", "Undead Labs", "Hayatta Kalma", 69, 2018, 16, "INTEL CORE i5 4570", "GTX 1060", 30, "Windows 10", "SSD"));
        games.put("DAYS GONE", new GameInfo("Days Gone", "Bend Studio", "Aksiyon - Macera", 76, 2021, 16, "INTEL CORE i7 4770K", "GTX 1060", 70, "Windows 10", "SSD"));
        games.put("UNCHARTED LEGACY", new GameInfo("Uncharted: Legacy of Thieves", "Naughty Dog", "Aksiyon - Macera", 88, 2022, 16, "INTEL CORE i7 4770", "GTX 1060", 126, "Windows 10", "SSD"));
        games.put("GTA 5 ENHANCED", new GameInfo("GTA V: Enhanced", "Rockstar Games", "A\u00e7\u0131k D\u00fcnya", 97, 2022, 16, "INTEL CORE i7 8700K", "RTX 2070", 110, "Windows 10", "SSD"));
        games.put("FORZA HORIZON 4", new GameInfo("Forza Horizon 4", "Playground Games", "Yar\u0131\u015f", 88, 2018, 12, "INTEL CORE i7 3820", "GTX 1060", 80, "Windows 10", "SSD"));
        games.put("FORZA HORIZON 5", new GameInfo("Forza Horizon 5", "Playground Games", "Yar\u0131\u015f", 92, 2021, 16, "INTEL CORE i5 8400", "RTX 2060 ", 110, "Windows 10", "SSD"));
        games.put("FORZA MOTORSPORT", new GameInfo("Forza Motorsport 8", "Turn 10", "Sim\u00fclasyon Yar\u0131\u015f", 84, 2023, 16, "INTEL CORE i5 11600k", "RTX 2080 Ti", 130, "Windows 11", "SSD"));
        games.put("NFS HEAT", new GameInfo("NFS Heat", "Ghost Games", "Yar\u0131\u015f", 74, 2019, 16, "INTEL CORE i7 4790", "GTX 1060", 50, "Windows 10", "HDD"));
        games.put("NFS UNBOUND", new GameInfo("NFS Unbound", "Criterion Games", "Yar\u0131\u015f", 77, 2022, 16, "INTEL CORE i7 8700", "RTX 2070", 60, "Windows 11", "SSD"));
        games.put("BEAMNG DRIVE", new GameInfo("BeamNG.drive", "BeamNG", "Sim\u00fclasyon - Fizik", 90, 2024, 32, "RYZEN 7 5800X", "RTX 3060", 45, "Windows 10", "SSD"));
        games.put("F1 25", new GameInfo("F1 25", "Codemasters", "Sim\u00fclasyon Yar\u0131\u015f", 82, 2024, 16, "INTEL CORE i5 12600K", "RTX 3070", 100, "Windows 11", "SSD"));
        games.put("F1 26", new GameInfo("F1 26", "Codemasters", "Sim\u00fclasyon Yar\u0131\u015f", 85, 2025, 16, "INTEL CORE i7 12700K", "RTX 4070", 110, "Windows 11", "SSD"));
        games.put("FC 25", new GameInfo("EA Sports FC 25", "EA Sports", "Spor", 76, 2024, 16, "INTEL CORE i7 6700", "GTX 1660", 100, "Windows 10", "SSD"));
        games.put("FC 26", new GameInfo("EA Sports FC 26", "EA Sports", "Spor", 78, 2025, 16, "INTEL CORE i7 8700K", "RTX 2060", 105, "Windows 11", "SSD"));
        games.put("NBA 2K25", new GameInfo("NBA 2K25", "Visual Concepts", "Spor", 79, 2024, 16, "INTEL CORE i5 10600K", "RTX 2070", 150, "Windows 11", "SSD"));
        games.put("NBA 2K26", new GameInfo("NBA 2K26", "Visual Concepts", "Spor", 80, 2025, 16, "INTEL CORE i7 10700K", "RTX 2070", 160, "Windows 11", "SSD"));
        games.put("COD MW2 REBOOT", new GameInfo("Call of Duty: MW2", "Infinity Ward", "FPS", 79, 2022, 16, "INTEL CORE i7 4770K", "GTX 1060", 125, "Windows 10", "SSD"));
        games.put("COD MW3 REBOOT", new GameInfo("Call of Duty: MW3", "Sledgehammer", "FPS", 70, 2023, 16, "INTEL CORE i7 6700K", "GTX 1080", 149, "Windows 10", "SSD"));
        games.put("COD BLACK OPS 6", new GameInfo("Call of Duty: Black Ops 6", "Treyarch", "FPS", 85, 2024, 16, "INTEL CORE i7 8700K", "RTX 3060", 160, "Windows 11", "SSD"));
        games.put("COD BLACK OPS 7", new GameInfo("Call of Duty: Black Ops 7", "Treyarch", "FPS", 67, 2025, 16, "INTEL CORE i7 11600", "RTX 3060", 180, "Windows 11", "SSD"));
        games.put("COD WARZONE", new GameInfo("Call of Duty: Warzone", "Raven Software", "Battle Royale", 80, 2020, 16, "INTEL CORE i7 8700K", "RTX 3050", 125, "Windows 10", "SSD"));
        games.put("BATTLEFIELD 2042", new GameInfo("Battlefield 2042", "DICE", "FPS", 68, 2021, 16, "INTEL CORE i7 4790", "RTX 3060", 100, "Windows 10", "SSD"));
        games.put("BATTLEFIELD 6", new GameInfo("Battlefield 6", "DICE", "FPS", 85, 2025, 32, "INTEL CORE i7 11700", "RTX 4070", 150, "Windows 11", "SSD"));
        games.put("FORTNITE", new GameInfo("Fortnite (UE5)", "Epic Games", "Battle Royale", 85, 2024, 16, "INTEL CORE i7 9700K", "RTX 3070", 50, "Windows 11", "SSD"));
        String gameKey;
        while (!games.containsKey(gameKey = scanner.nextLine().strip())) {
            System.out.println("Sistemde böyle oyun yok lütfen tekrar giriniz");
            System.out.println("Örnek : The Last Of Us Remake , Red Redemption 2 , Cities Skylines 2");
            System.out.print("Tekrar deneyin > ");
        }
        games.get(gameKey).printInfo();

        Map<String, GameBenchmark> benchmarks = new HashMap<>();
        benchmarks.put("CITIES SKYL\u0130NES 2", new GameBenchmark("Cities Skylines 2", 70.0, 62.0, 0.8, 0.2));
        benchmarks.put("THE LAST OF US Part 1", new GameBenchmark("The Last Of Us Part 1", 47.0, 48.0, 0.4, 0.6));
        benchmarks.put("THE LAST OF US Part 2", new GameBenchmark("The Last Of Us Part 2", 45.0, 45.0, 0.35, 0.65));
        benchmarks.put("RED DEAD REDEMPT\u0130ON 2", new GameBenchmark("Red Dead Redemption 2", 43.0, 42.0, 0.35, 0.65));
        benchmarks.put("CYBERPUNK 2077", new GameBenchmark("Cyberpunk 2077", 57.0, 50.0, 0.3, 0.7));
        benchmarks.put("GOD OF WAR 2018", new GameBenchmark("God Of War 2018", 42.0, 36.0, 0.4, 0.6));
        benchmarks.put("GOD OF WAR RAGNAR\u00d6K", new GameBenchmark("God Of War Ragnar\u00f6k", 44.0, 38.0, 0.45, 0.55));
        benchmarks.put("SPIDER-MAN REMASTERED", new GameBenchmark("Spider-Man Remastered", 38.0, 37.0, 0.4, 0.6));
        benchmarks.put("SPIDER-MAN 2", new GameBenchmark("Spider-Man 2", 41.0, 48.0, 0.45, 0.55));
        benchmarks.put("DYING LIGHT 2", new GameBenchmark("Dying Light 2: Stay Human", 44.0, 45.0, 0.4, 0.6));
        benchmarks.put("DYING LIGHT: THE BEAST", new GameBenchmark("Dying Light: The Beast", 48.0, 55.0, 0.4, 0.6));
        benchmarks.put("STATE OF DECAY 2", new GameBenchmark("State of Decay 2", 35.0, 45.0, 0.5, 0.5));
        benchmarks.put("DAYS GONE", new GameBenchmark("Days Gone", 38.0, 44.0, 0.35, 0.65));
        benchmarks.put("UNCHARTED LEGACY", new GameBenchmark("Uncharted: Legacy of Thieves Collection", 40.0, 42.0, 0.3, 0.7));
        benchmarks.put("GTA 5 ENHANCED", new GameBenchmark("GTA V: Enhanced & Expanded", 43.0, 42.0, 0.5, 0.5));
        benchmarks.put("FORZA HORIZON 4", new GameBenchmark("Forza Horizon 4", 38.0, 35.0, 0.3, 0.7));
        benchmarks.put("FORZA HORIZON 5", new GameBenchmark("Forza Horizon 5", 44.0, 45.0, 0.35, 0.65));
        benchmarks.put("FORZA MOTORSPORT", new GameBenchmark("Forza Motorsport (2023)", 50.0, 56.0, 0.45, 0.55));
        benchmarks.put("NFS HEAT", new GameBenchmark("Need for Speed Heat", 33.0, 35.0, 0.4, 0.6));
        benchmarks.put("NFS UNBOUND", new GameBenchmark("Need for Speed Unbound", 35.0, 37.0, 0.4, 0.6));
        benchmarks.put("BEAMNG DRIVE", new GameBenchmark("BeamNG.drive", 50.0, 45.0, 0.85, 0.15));
        benchmarks.put("F1 24", new GameBenchmark("F1 24", 50.0, 39.0, 0.6, 0.4));
        benchmarks.put("F1 25", new GameBenchmark("F1 25", 55.0, 42.0, 0.6, 0.4));
        benchmarks.put("F1 26", new GameBenchmark("F1 26", 60.0, 45.0, 0.6, 0.4));
        benchmarks.put("FC 25", new GameBenchmark("EA Sports FC 25", 45.0, 39.0, 0.55, 0.45));
        benchmarks.put("FC 26", new GameBenchmark("EA Sports FC 26", 48.0, 40.0, 0.55, 0.45));
        benchmarks.put("NBA 2K25", new GameBenchmark("NBA 2K25", 58.0, 52.0, 0.4, 0.6));
        benchmarks.put("NBA 2K26", new GameBenchmark("NBA 2K26", 62.0, 55.0, 0.4, 0.6));
        benchmarks.put("COD MW2 REBOOT", new GameBenchmark("Call of Duty: MW2 (2022)", 48.0, 48.0, 0.5, 0.5));
        benchmarks.put("COD MW3 REBOOT", new GameBenchmark("Call of Duty: MW3 (2023)", 50.0, 50.0, 0.5, 0.5));
        benchmarks.put("COD BLACK OPS 6", new GameBenchmark("Call of Duty: Black Ops 6", 58.0, 55.0, 0.5, 0.5));
        benchmarks.put("COD BLACK OPS 7", new GameBenchmark("Call of Duty: Black Ops 7", 60.0, 57.0, 0.5, 0.5));
        benchmarks.put("COD WARZONE", new GameBenchmark("Call of Duty: Warzone 2.0", 55.0, 65.0, 0.6, 0.4));
        benchmarks.put("BATTLEFIELD 2042", new GameBenchmark("Battlefield 2042", 44.0, 42.0, 0.7, 0.3));
        benchmarks.put("BATTLEFIELD 6", new GameBenchmark("Battlefield 6", 47.0, 50.0, 0.7, 0.3));
        benchmarks.put("FORTNITE", new GameBenchmark("Fortnite (Unreal Engine 5)", 68.0, 55.0, 0.3, 0.7));
        String benchmarkKey;
        while (true) {
            System.out.println("\nLütfen sisteminiz için benchmark yapmak istediğiniz oyunu seçin");
            benchmarkKey = scanner.nextLine().strip();
            if (benchmarks.containsKey(benchmarkKey)) break;
            System.out.println("Sistemde böyle oyun yok lütfen tekrar giriniz");
            System.out.println("Örnek : The Last Of Us Remake , Red Redemption 2 , Cities Skylines 2");
            System.out.print("Tekrar deneyin > ");
        }
        benchmarks.get(benchmarkKey).printEstimate(gpu, cpu);
    }
}
