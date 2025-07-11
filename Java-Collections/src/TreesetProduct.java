

public class TreesetProduct implements Comparable<TreesetProduct> {
    int ID;
    String productName;
    int price;
    String Category;

    public TreesetProduct(int ID, String productName, int price, String category) {
        this.ID = ID;
        this.productName = productName;
        this.price = price;
        Category = category;
    }

    @Override
    public String toString() {
        return "TreesetProduct{" +
                "ID=" + ID +
                ", productName='" + productName + '\'' +
                ", price=" + price +
                ", Category='" + Category + '\'' +
                '}';
    }


    @Override
    public int compareTo(TreesetProduct treesetProduct) {
        return this.ID - treesetProduct.ID;
    }
}
