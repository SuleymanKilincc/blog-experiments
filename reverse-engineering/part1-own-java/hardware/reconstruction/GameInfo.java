// Reconstructed from obfuscated class "d": a game's details and recommended system.
public class GameInfo {
    String title;
    String publisher;
    String genre;
    int metacriticScore;
    int releaseYear;
    int recommendedRamGb;
    String recommendedCpu;
    String recommendedGpu;
    int storageGb;
    String operatingSystem;
    String storageType;

    public GameInfo(String title, String publisher, String genre, int metacriticScore, int releaseYear,
                    int recommendedRamGb, String recommendedCpu, String recommendedGpu, int storageGb,
                    String operatingSystem, String storageType) {
        this.title = title;
        this.publisher = publisher;
        this.genre = genre;
        this.metacriticScore = metacriticScore;
        this.releaseYear = releaseYear;
        this.recommendedRamGb = recommendedRamGb;
        this.recommendedCpu = recommendedCpu;
        this.recommendedGpu = recommendedGpu;
        this.storageGb = storageGb;
        this.operatingSystem = operatingSystem;
        this.storageType = storageType;
    }

    public void printInfo() {
        System.out.println("OYUN BİLGİLERİ\n");
        System.out.println("Seçtiğiniz oyun : " + this.title);
        System.out.println("Oyunu yayımlayan firma : " + this.publisher);
        System.out.println("Oyunun piyasaya sürüldüğü tarih : " + this.releaseYear);
        System.out.println("Oyunun 'Meta Critic puanı' : " + this.metacriticScore);
        System.out.println();
        System.out.println("OYUNUN SİSTEM GEREKSİNİMLERİ\n");
        System.out.println("Önerilen sistem 'RAM' : " + this.recommendedRamGb + " GB");
        System.out.println("Önerilen sistem 'İŞLEMCİ' : " + this.recommendedCpu);
        System.out.println("Önerilen sistem 'EKRAN KARTI' : " + this.recommendedGpu);
        System.out.println("Önerilen sistem 'DEPOLAMA ALANI' : " + this.storageGb + " GB");
        System.out.println("Önerilen sistem 'DEPOLAMA TÜRÜ' : " + this.storageType);
        System.out.println("Önerilen sistem 'İŞLETİMİ' : " + this.operatingSystem);
    }
}
