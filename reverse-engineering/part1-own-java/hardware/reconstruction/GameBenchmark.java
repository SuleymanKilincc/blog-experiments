// Reconstructed from obfuscated class "b": how demanding a game is, and the FPS bands it maps to.
public class GameBenchmark {
    String title;
    double cpuRequirement;
    double gpuRequirement;
    double cpuWeight;
    double gpuWeight;

    public GameBenchmark(String title, double cpuRequirement, double gpuRequirement, double cpuWeight, double gpuWeight) {
        this.title = title;
        this.cpuRequirement = cpuRequirement;
        this.gpuRequirement = gpuRequirement;
        this.cpuWeight = cpuWeight;
        this.gpuWeight = gpuWeight;
    }

    // Each part's score is divided by what the game needs, then the two ratios are blended
    // by how CPU- or GPU-bound the game is. 1.0 means the system just meets the requirement.
    public void printEstimate(GraphicsCard gpu, Processor cpu) {
        System.out.println("-------------------------------");
        System.out.println("Seçilen Oyun : " + this.title);
        double cpuRatio = cpu.performanceScore / this.cpuRequirement;
        double gpuRatio = gpu.performanceScore / this.gpuRequirement;
        double headroom = cpuRatio * this.cpuWeight + gpuRatio * this.gpuWeight;
        String header = "Çözünürlük ve FPS Aralığı";
        if (headroom >= 2.0) {
            printBands("\n------" + header + "------", "100+", "130+", "180+");
        } else if (headroom >= 1.75) {
            printBands("\n------" + header + "------", "80-100", "100-140", "144-180");
        } else if (headroom >= 1.5) {
            printBands(header, "60-80", "80-110", "100-144");
        } else if (headroom >= 1.25) {
            printBands(header, "40-60", "60-80", "80-100");
        } else if (headroom >= 1.0) {
            printBands(header, "15-35", "30-55", "60-80");
        } else if (headroom >= 0.8) {
            printBands(header, "5-15", "15-35", "35-55");
        } else if (headroom >= 0.5) {
            printBands(header, "1-5", "5-15", "15-35");
        } else if (headroom >= 0.2) {
            printBands(header, "0", "1-10", "10-25");
        } else {
            System.out.println("Maalesef oyun sisteminizde çalışmaz");
        }
    }

    private static void printBands(String header, String fps4k, String fps2k, String fps1k) {
        System.out.println(header);
        System.out.println("4k : " + fps4k);
        System.out.println("2K : " + fps2k);
        System.out.println("1K : " + fps1k);
    }
}
