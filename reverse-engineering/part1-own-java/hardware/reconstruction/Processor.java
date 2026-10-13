// Reconstructed from obfuscated class "c": one CPU and its spec sheet.
public class Processor {
    String name;
    String memoryType;
    int performanceScore;
    int powerWatts;
    int processNm;
    int coreCount;
    int threadCount;
    double baseClockGhz;
    double boostClockGhz;
    int memorySpeedMhz;
    String l1Cache;
    String l2Cache;
    String l3Cache;

    public Processor(String name, String memoryType, int performanceScore, int powerWatts, int processNm,
                     int coreCount, int threadCount, double baseClockGhz, double boostClockGhz, int memorySpeedMhz,
                     String l1Cache, String l2Cache, String l3Cache) {
        this.name = name;
        this.memoryType = memoryType;
        this.performanceScore = performanceScore;
        this.powerWatts = powerWatts;
        this.processNm = processNm;
        this.coreCount = coreCount;
        this.threadCount = threadCount;
        this.baseClockGhz = baseClockGhz;
        this.boostClockGhz = boostClockGhz;
        this.memorySpeedMhz = memorySpeedMhz;
        this.l1Cache = l1Cache;
        this.l2Cache = l2Cache;
        this.l3Cache = l3Cache;
    }

    public void printSpecs() {
        System.out.println("--------------------");
        System.out.println("İşlemciniz " + this.name);
        System.out.println("Bellek türü " + this.memoryType);
        System.out.println("Wat değeri " + this.powerWatts + " W");
        System.out.println("Transistör mesafesi " + this.processNm + " nm");
        System.out.println("Çekirdek sayısı " + this.coreCount);
        System.out.println("İş parçacığı sayısı " + this.threadCount);
        System.out.println("Temel frekans " + this.baseClockGhz + " GHz");
        System.out.println("Artırılmış frekans " + this.boostClockGhz + " GHz");
        System.out.println("Bellek hızı " + this.memorySpeedMhz + " MHz");
        System.out.println("L1 önbellek boyutu " + this.l1Cache);
        System.out.println("L2 önbellek boyutu " + this.l2Cache);
        System.out.println("L3 önbellek boyutu " + this.l3Cache);
        System.out.println("Toplam performans puanı " + this.performanceScore + "/100");
        System.out.println("--------------------");
    }
}
