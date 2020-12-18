import java.util.*;

public class RetailGroup {
    private final List<Store> storeList = new ArrayList<>();
    private final List<Product> productList = new ArrayList<>();

    public void createNewStore(String storeName) {
        storeList.add(new Store(storeName, newStoreOpenDateOf()));
    }

    public void addNewProduct(String product, long price) {
        productList.add(new Product(product, price));
    }

    public void putProductOnShelf(Product product, Store store) {
        store.addProduct(product);
    }
    public Store findStore (String storeName) {
        for (Store s : storeList) {
            if (s.getName().equals(storeName)) {
                return s;
            }
        }
        return null;
    }

    public Product findProduct (String productName) {
        for (Product p : productList) {
            if (p.getName().equals(productName)) {
                return p;
            }
        }
        return null;
    }

    private long newStoreOpenDateOf() {
        return new Date().getTime() / 1000;
    }

    public void stats() {
        storeList.forEach(s -> System.out.printf("Store %s, avg: %s \n", s.getName(), s.avgPriceProduct()));
    }

    public void printStores() {
        storeList.forEach(s -> {
            System.out.println("-s " + s.getName());
            s.getProductList().forEach(p -> System.out.println("--p " + p.getName()));
        });
    }
}
