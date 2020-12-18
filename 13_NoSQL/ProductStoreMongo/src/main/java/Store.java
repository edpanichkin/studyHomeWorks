import java.util.List;
import java.util.OptionalDouble;

public class Store {
    public Store(String name, long dateOpen) {
        this.name = name;
        this.dateOpen = dateOpen;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getDateOpen() {
        return dateOpen;
    }

    public void setDateOpen(long dateOpen) {
        this.dateOpen = dateOpen;
    }

    private String name;
    private long dateOpen;
    private List<Product> productList;

    public List<Product> getProductList() {
        return productList;
    }

    public void addProduct(Product product) {
        productList.add(product);
    }

    public int countProduct() {
        return productList.size();
    }

    public OptionalDouble avgPriceProduct() {
        return productList.stream().mapToLong(Product::getPrice).average();
    }

//    public Product minPriceProduct() {
//        return productList.stream().mapToLong(Product::getPrice).min();
//    }

}
