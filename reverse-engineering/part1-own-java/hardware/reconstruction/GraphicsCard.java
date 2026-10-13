// Reconstructed from obfuscated class "a": one graphics card and its spec sheet.
public class GraphicsCard {
    String name;
    String memoryType;
    int performanceScore;
    int powerWatts;
    int vramGb;
    int coreCount;
    int processNm;
    int memoryBusBits;
    int targetResolutionK;
    int coreClockMhz;

    public GraphicsCard(String name, String memoryType, int performanceScore, int powerWatts, int vramGb,
                        int coreCount, int processNm, int memoryBusBits, int targetResolutionK, int coreClockMhz) {
        this.name = name;
        this.memoryType = memoryType;
        this.performanceScore = performanceScore;
        this.powerWatts = powerWatts;
        this.vramGb = vramGb;
        this.coreCount = coreCount;
        this.processNm = processNm;
        this.memoryBusBits = memoryBusBits;
        this.targetResolutionK = targetResolutionK;
        this.coreClockMhz = coreClockMhz;
    }

    public void printSpecs() {
        System.out.println("--------------------");
        System.out.println("Ekran kartınız " + this.name);
        System.out.println("Bellek türü " + this.memoryType);
        System.out.println("Wat değeri " + this.powerWatts + " W");
        System.out.println("Vram " + this.vramGb + " Gb");
        System.out.println("ÇekirdekSayisi " + this.coreCount);
        System.out.println("Çekirdek frekansı " + this.coreClockMhz + " Mhz");
        System.out.println("Transistör mesafesi " + this.processNm + " nm");
        System.out.println("Bellek arayüzü " + this.memoryBusBits + " bit");
        System.out.println("Oyun çözünürlüğü " + this.targetResolutionK + "K");
        System.out.println("Toplam performans " + this.performanceScore + "/100");
        System.out.println("----------------------");
    }
}
