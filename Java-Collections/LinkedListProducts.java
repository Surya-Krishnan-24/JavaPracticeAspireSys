import java.util.Objects;

public class LinkedListProducts {
    int ProductID;
    String ProductName;
    int price;
    String Category;


    public LinkedListProducts(int productID, String productName, int price, String category) {
        ProductID = productID;
        ProductName = productName;
        this.price = price;
        Category = category;
    }

    @Override
    public String toString() {
        return "LinkedListProducts{" +
                "ProductID=" + ProductID +
                ", ProductName='" + ProductName + '\'' +
                ", price=" + price +
                ", Category='" + Category + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        LinkedListProducts that = (LinkedListProducts) o;
        return ProductID == that.ProductID && price == that.price && Objects.equals(ProductName, that.ProductName) && Objects.equals(Category, that.Category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ProductID, ProductName, price, Category);
    }
}
