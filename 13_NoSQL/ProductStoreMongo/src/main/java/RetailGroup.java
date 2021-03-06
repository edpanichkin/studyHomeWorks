import com.mongodb.MongoClient;
import com.mongodb.client.model.*;
import org.bson.Document;

import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.*;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.conversions.Bson;
import org.bson.io.BsonOutput;

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
        if (getStore(store) != null) {
            System.out.println("Магазин " + store + " уже был открыт ранее");
        } else {
            stores.insertOne(new Document().append("name", store).append("products", new ArrayList<>()));
            System.out.println("Магазин " + store + " открыт!" + "\n");
        }
    }

    public void addNewProduct(String product, double price) {
        if (getProduct(product) != null) {
            System.out.println("Продукт " + product + " уже был добавлен ранее");
        } else {
            products.insertOne(new Document().append("name", product).append("price", price));
            //System.out.println("Продукт " + product + " добавлен по цене: " + price + "\n");
        }
    }

    public void putProductOnShelf(String product, String store) {
        if(stores.find(and(ne("products", getProduct(product)), eq("name", store))).first() != null) {
            stores.updateOne(eq("name", store),
                    Updates.addToSet("products", getProduct(product)));
        }
        else {
            System.out.println("Товар " + product + " уже выставлен на полки в магазине " + store);
        }
    }

    public Document getStore(String name) {
        return stores.find(eq("name", name)).first();
    }
    public Document getProduct(String name) {
        return products.find(eq("name", name)).first();
    }

    public int countCheapProducts(String store) {
        Document countLtPr = stores.aggregate(Arrays.asList(
                unwind("$products"),
                match(and(eq("name", store), lt("products.price", 100))), count())).first();
        return countLtPr != null ? (int) countLtPr.get("count") : 0;
    }

    public List<Document> getStats() {
        Bson cheapLt100Field = Aggregates.addFields(new Field("cheapLt100",
                new Document("$cond",
                        new Document("if", new Document("$gte", Arrays.asList("$products.price", 100L)))
                                .append("then", 0L)
                                .append("else", 1L))));

        List<Document> list = stores.aggregate(Arrays.asList(
                unwind("$products"), cheapLt100Field,
                group("$name", avg("avg", "$products.price"),
                        Accumulators.max("max", "$products.price"),
                        Accumulators.min("min", "$products.price"),
                        sum("count", 1),
                Accumulators.sum("cheapField", "$cheapLt100")),
                sort(Sorts.ascending("_id")))).into(new ArrayList<>());
        //list.forEach(d -> d.append("cheap", countCheapProducts((String) d.get("_id"))));
       //System.out.println("LIST" + list);
        return list;

    }
    public List<Document> getEmptyStoreList() {
        return stores.aggregate(Arrays.asList(match(size("products", 0)))).into(new ArrayList<>());
    }

    public void printStats() {
        getStats().forEach(d -> {
            System.out.println("Информация по магазину: " + d.get("_id") +
                    "\nКол-во товаров на полках: " + d.get("count") +
                    "\nМинимальная стоимость товара: " + d.get("min") +
                    "\nМаксимальная стоимость товара: " + d.get("max") +
                    "\nСредняя стоимость товара:  " + d.get("avg") +
                   // "\nКол-во товаров, стоимостью ниже 100р: " + d.get("cheap") +
                    "\nКол-во товаров, стоимостью ниже 100р (aggregation Field ): " + d.get("cheapField") +

                    "\n___________________________________________");
        });

        getEmptyStoreList().forEach(d -> {
            System.out.println("В магазине: " + d.get("name") + " еще нет товаров" +
                    "\n___________________________________________");
        });
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
