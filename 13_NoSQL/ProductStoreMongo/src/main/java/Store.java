import java.util.*;

public class Store {

    private String name;
    private long dateOpen;
    private final List<Product> productList = new ArrayList<>();

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

    public List<Product> getProductList() {
        return productList;
    }

    public void addProduct(Product product) {
        productList.add(product);
    }

    public int countProduct() {
        return productList.size();
    }

    public Double avgPriceProduct() {
        OptionalDouble od = productList.stream().mapToLong(Product::getPrice).average();
        return od.isPresent() ? od.getAsDouble() : null;
    }

//    public Product minPriceProduct() {
//        Collections.sort(productList);
//        return productList.stream().mapToLong(Product::getPrice).min();
//    }

}
