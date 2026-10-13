// Reconstructed from obfuscated class "b": one square on the board; owner is null while it is for sale.
public class Property {
    String name;
    double price;
    double rent;
    int squareNumber;
    String owner;

    public Property(String name, double price, double rent, int squareNumber) {
        this.name = name;
        this.price = price;
        this.rent = rent;
        this.squareNumber = squareNumber;
        this.owner = null;
    }
}
