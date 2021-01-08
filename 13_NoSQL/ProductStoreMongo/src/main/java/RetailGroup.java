import com.mongodb.MongoClient;
import com.mongodb.client.model.*;
import org.bson.Document;

import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.*;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import static com.mongodb.client.model.Accumulators.*;

import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

public class RetailGroup {
    private MongoCollection<Document> stores;
    private MongoCollection<Document> products;

    public void init(String host, int port) {
        MongoClient mongoClient = new MongoClient(host, port);
        MongoDatabase database = mongoClient.getDatabase("local");
        stores = database.getCollection("stores");
        stores.drop();
        products = database.getCollection("products");
        products.drop();
    }

    public void createNewStore(String store) {
        stores.insertOne(new Document().append("name", store).append("products", new ArrayList<>()));
    }

    public void addNewProduct(String product, double price) {
        products.insertOne(new Document().append("name", product).append("price", price));
    }

    public void putProductOnShelf(String product, String store) {
        stores.updateOne(eq("name", store),
                Updates.addToSet("products", getProduct(product)));
    }

    public Document getStore(String name) {
        return stores.find(eq("name", name)).first();
    }
    public Document getProduct(String name) {
        return products.find(eq("name", name)).first();
    }

    public String countCheapProducts(String store) {
        Document countLtPr = stores.aggregate(Arrays.asList(
                unwind("$products"),
                match(and(eq("name", store), lt("products.price", 100))), count())).first();
        return countLtPr != null ? countLtPr.get("count").toString() : "0";

    }

    public List<Document> getStats() {
        List<Document> list = stores.aggregate(Arrays.asList(
                unwind("$products"),
                group("$name", avg("avg", "$products.price"),
                Accumulators.max("max", "$products.price"),
                Accumulators.min("min", "$products.price"),
                sum("count", 1)),
                sort(Sorts.ascending("_id")))).into(new ArrayList<>());
        printStats(list);
        return list;
    }

    public void printStats(List<Document> list) {
        list.forEach(d -> {
            String store = (String) d.get("_id");

            System.out.println("Информация по магазину: " + store +
            "\nКол-во товаров на полках: " + d.get("count") +
            "\nМинимальная стоимость товара: " + d.get("min") +
            "\nМаксимальная стоимость товара: " + d.get("max") +
            "\nСредняя стоимость товара:  " + d.get("avg") +
            "\nКол-во товаров, стоимостью ниже 100р: " + countCheapProducts(store) +
            "\n___________________________________________");
        });

//        AggregateIterable <Document> documentAggregateIterable =
//                products.aggregate(Arrays.asList(
//                Aggregates.lookup("stores", "name", "products.name", "shops"),
//                Aggregates.unwind("$shops"),
//                Aggregates.group("$shops.name", Accumulators.avg("avg", "$price"))));
//        for(Document doc:documentAggregateIterable){
//            System.out.println(doc.get("_id") + " / " + doc.get("avg"));
    }

    public void printMongoStores() {
        for(Document doc : stores.find()) {
            System.out.println(doc);
        }
    }

    public void printMongoProducts() {
        for(Document doc : products.find()) {
            System.out.println(doc);
        }
    }
}
