import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        RetailGroup retailGroup = new RetailGroup();
        retailGroup.init("127.0.0.1", 27017);

        for(;;) {
            System.out.println("Listen for commands:\n" +
                    "NEWSTORE storeName // " +
                    "ADDPRODUCT productName price // " +
                    "ONSHELVES productName storeName // " +
                    "STATS");
            String[] inputData = reader.nextLine().split(" ");
            String command = inputData[0];

            switch (command) {
                case "NEWSTORE" -> retailGroup.createNewStore(inputData[1]);
                case "ADDPRODUCT" -> {
                    String productName = inputData[1];
                    double price = Double.parseDouble(inputData[2]);
                    retailGroup.addNewProduct(productName, price);
                }
                case "ONSHELVES" -> {
                    String product = inputData[1];
                    String store = inputData[2];
                    retailGroup.putProductOnShelf(product, store);
                }
                case "STATS" -> retailGroup.printStats();
            }
        }
    }
}
