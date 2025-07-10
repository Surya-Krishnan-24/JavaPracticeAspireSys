public class TreeMapProducts {

    int productID;
    String productName;

    public TreeMapProducts(int productID, String productName) {
        this.productID = productID;
        this.productName = productName;
    }

    @Override
    public String toString() {
        return "TreeMapProducts{" +
                "productID=" + productID +
                ", productName='" + productName + '\'' +
                '}';
    }
}
