public class Category implements Comparable<Category> {
    String category;

    public Category(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return category;
    }

    @Override
    public int compareTo(Category category) {
        return this.category.compareTo(category.category);



    }
}
