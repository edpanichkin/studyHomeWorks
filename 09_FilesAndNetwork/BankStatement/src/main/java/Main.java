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
    private static final int ACCOUNT_TYPE = 0;
    private static final int ACCOUNT_NUM = 1;
    private static final int OPERATION_CURRENCY = 2;
    private static final int OPERATION_DATE = 3;
    private static final int OPERATION_REF = 4;
    private static final int OPERATION_INFO = 5;
    private static final int INCOME_VALUE = 6;
    private static final int EXPENSE_VALUE = 7;


    public static void main(String[] args) throws IOException {
        List<BankTransaction> operations = new ArrayList<>();
        csvReader(operations);
        printTask(operations);
    }

    private static List<BankTransaction> csvReader (List<BankTransaction> operations) throws IOException {
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
                            fragments[ACCOUNT_TYPE],
                            fragments[ACCOUNT_NUM],
                            fragments[OPERATION_CURRENCY],
                            fragments[OPERATION_DATE],
                            fragments[OPERATION_REF],
                            fragments[OPERATION_INFO],
                            Double.parseDouble(fragments[INCOME_VALUE]),
                            Double.parseDouble(fragments[EXPENSE_VALUE]),
                            setMccCodes(fragments[OPERATION_INFO]));
                    operations.add(bankTransaction);
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return operations;
    }

    private static String setMccCodes(String operationInfo) {
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
}

