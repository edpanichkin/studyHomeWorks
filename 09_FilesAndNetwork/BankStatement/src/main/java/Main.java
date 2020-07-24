import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    private static final String PATH_TO_CSV = "src/main/resources/movementList.csv";
    private static final String COMMA_DELIMITER = ",";


    public static void main(String[] args) throws IOException {
        List<BankTransaction> operations = new ArrayList<>();
        csvReader(operations);
        printTask(operations);
    }

    public static String setMccCodes(String operationInfo) {
        return operationInfo.substring(operationInfo.indexOf("MCC"));
    }

    public static void printTask (List<BankTransaction> operations) {
        System.out.printf("Поступления: %.2f руб. \nРасходы: %.2f руб. \n\n",
                operations.stream().mapToDouble(e -> e.getIncome()).sum(),
                operations.stream().mapToDouble(e -> e.getExpense()).sum());
        Map<String, Double> groupsExpense = operations.stream().filter(b -> b.getExpense() > 0)
                .collect(Collectors.groupingBy(BankTransaction::getMccCode,
                        Collectors.summingDouble(BankTransaction::getExpense)));
        groupsExpense.forEach((k, v) -> System.out.printf("КОД %s потрачено: %.2f руб.\n", k, v));

    }

    public static List<BankTransaction> csvReader (List<BankTransaction> operations) throws IOException {
        try {
            String line = "";
            BufferedReader fileReader = new BufferedReader(new FileReader(PATH_TO_CSV));
            fileReader.readLine();
            while ((line = fileReader.readLine()) != null) {
                String lineOptimize = null;
                boolean lineCheckDouble = false;
                if (line.indexOf("\"") > 0) {
                    lineOptimize = line.substring(line.indexOf("\""))
                            .replace("\"", "")
                            .replace(",", ".");
                    lineCheckDouble = true;
                }
                String[] fragments = line.split(COMMA_DELIMITER);
                if (fragments.length > 0) {
                    if (lineCheckDouble) {
                        fragments[7] = lineOptimize;
                    }

                    BankTransaction bankTransaction = new BankTransaction(
                            fragments[0], fragments[1], fragments[2], fragments[3],
                            fragments[4], fragments[5],
                            Double.parseDouble(fragments[6]), Double.parseDouble(fragments[7]), setMccCodes(fragments[5]));
                    operations.add(bankTransaction);
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return operations;
    }

}

