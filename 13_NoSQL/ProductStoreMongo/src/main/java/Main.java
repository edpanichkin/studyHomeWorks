import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        RetailGroup retailGroup = new RetailGroup();
        retailGroup.createNewStore("testInMain_1");
        retailGroup.createNewStore("testInMain_2");
        retailGroup.addNewProduct("moloko", 10);
        retailGroup.addNewProduct("cheese", 2);
        retailGroup.addNewProduct("ham", 3);
        retailGroup.findProduct("ham");
        retailGroup.putProductOnShelf(retailGroup.findProduct("ham"),
                retailGroup.findStore("testInMain_1"));
        retailGroup.putProductOnShelf(retailGroup.findProduct("moloko"),
                retailGroup.findStore("testInMain_1"));
        retailGroup.putProductOnShelf(retailGroup.findProduct("cheese"),
                retailGroup.findStore("testInMain_2"));

        retailGroup.printStores();

        retailGroup.stats();
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
