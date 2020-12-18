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

    public long newStoreOpenDateOf() {
        return new Date().getTime() / 1000;
    }

    public void stats(Store store) {
        System.out.println(store.avgPriceProduct());
    }

    public void printStores() {
        for (Store s: storeList) {
            System.out.println(s.getName());
        }
    }
}
