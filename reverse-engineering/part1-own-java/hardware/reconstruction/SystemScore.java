// Reconstructed from obfuscated class "e": combined score and CPU/GPU bottleneck verdict.
public class SystemScore {
    public double averageScore(GraphicsCard gpu, Processor cpu) {
        return (gpu.performanceScore + cpu.performanceScore) / 2.0;
    }

    // A positive gap means the GPU outscores the CPU, so the CPU holds the system back.
    public void printBottleneck(GraphicsCard gpu, Processor cpu) {
        int gap = gpu.performanceScore - cpu.performanceScore;
        String upgradeCpu = "Daha iyi performans için işlemcinizi yükseltin";
        String upgradeGpu = "Daha iyi performans için ekran kartınızı yükseltin";
        if (gap == 0) {
            System.out.println("Darboğaz yok");
            System.out.println("Sistem uyumlu çalışıyor");
        } else if (gap >= 40) {
            System.out.println("İşlemci %80-%99 arası darboğaz yapıyor");
            System.out.println(upgradeCpu);
        } else if (gap >= 30) {
            System.out.println("İşlemci %60-%80 arasında darboğaz yapıyor");
            System.out.println(upgradeCpu);
        } else if (gap >= 20) {
            System.out.println("İşlemci %40-%60 arasında darboğaz yapıyor");
            System.out.println(upgradeCpu);
        } else if (gap >= 10) {
            System.out.println("İşlemci %20-40% arasında darboğaz yapıyor");
            System.out.println(upgradeCpu);
        } else if (gap > 0) {
            System.out.println("İşlemci %1-20% arasında darboğaz yapıyor");
            System.out.println(upgradeCpu);
        } else if (gap <= -40) {
            System.out.println("Ekran kartınız %80-%99 arasında darboğaz yapıyor");
            System.out.println(upgradeGpu);
        } else if (gap <= -30) {
            System.out.println("Ekran kartınız %60-%80 arasında darboğaz yapıyor");
            System.out.println(upgradeGpu);
        } else if (gap <= -20) {
            System.out.println("Ekran kartınız %40-%60 arasında darboğaz yapıyor");
            System.out.println(upgradeGpu);
        } else if (gap <= -10) {
            System.out.println("Ekran kartınız %20-%40 arasında darboğaz yapıyor");
            System.out.println(upgradeGpu);
        } else {
            System.out.println("Ekran kartınız %1-%20 arasında darboğaz yapıyor");
            System.out.println(upgradeGpu);
        }
    }
}
