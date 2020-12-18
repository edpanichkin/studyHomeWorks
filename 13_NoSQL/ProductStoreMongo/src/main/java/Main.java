import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        RetailGroup retailGroup = new RetailGroup();
        for(;;) {
            System.out.println("Listen for commands:\n" +
                    "NEWSTORE storeName // " +
                    "ADDPRODUCT name price // " +
                    "ONSHELVES productName storeName // " +
                    "STATS");
            String[] inputData = reader.nextLine().t;

            if (inputData.equals("NEWSTORE")) {
                String store = reader.nextLine();
                retailGroup.createNewStore(store);
            }
            else if (inputData.equals("STATS")) {
                retailGroup.printStores();
            }
        }
    }
}
