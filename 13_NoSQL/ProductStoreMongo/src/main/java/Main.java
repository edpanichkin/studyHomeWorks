import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        RetailGroup retailGroup = new RetailGroup();
        retailGroup.init("127.0.0.1", 27017);
        retailGroup.createNewStore("store1");
        retailGroup.createNewStore("store2");
        retailGroup.createNewStore("store3");
        retailGroup.addNewProduct("moloko", 100);
        retailGroup.addNewProduct("cheese", 200);
        retailGroup.addNewProduct("ham", 3);
        retailGroup.printMongoProducts();
        retailGroup.addNewProduct("ham2", 4);
        retailGroup.printMongoProducts();
        retailGroup.addNewProduct("ham2", 4);
        retailGroup.printMongoProducts();

        retailGroup.putProductOnShelf("ham","store1");
        retailGroup.putProductOnShelf("ham2","store3");
        retailGroup.putProductOnShelf("ham","store2");
        retailGroup.putProductOnShelf("moloko","store1");
        retailGroup.putProductOnShelf("cheese", "store2");


        retailGroup.printStats(retailGroup.getStats());
//        for(;;) {
//            System.out.println("Listen for commands:\n" +
//                    "NEWSTORE storeName // " +
//                    "ADDPRODUCT productName price // " +
//                    "ONSHELVES productName storeName // " +
//                    "STATS");
//            String[] inputData = reader.nextLine().split(" ");
//            String command = inputData[0];
//
//            if (command.equals("NEWSTORE")) {
//                retailGroup.createNewStore(inputData[1]);
//            }
//            else if (command.equals("ADDPRODUCT")) {
//                String productName = inputData[1];
//                long price = Long.parseLong(inputData[2]);
//                retailGroup.addNewProduct(productName, price);
//            }
//            else if (command.equals("ONSHELVES")) {
//                Product product = retailGroup.findProduct(inputData[1]);
//                Store store = retailGroup.findStore(inputData[2]);
//                retailGroup.putProductOnShelf(product, store);
//            }
//
//            else if (command.equals("STATS")) {
//                retailGroup.printStores();
//            }
//        }
    }
}
