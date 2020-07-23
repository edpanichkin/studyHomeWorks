import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    private static final String PATH_TO_CSV = "src/main/resources/movementList.csv";
    private static final String COMMA_DELIMITER = ",";

    public static void main(String[] args) {

        try {
            List<BankTransaction> operations = new ArrayList<>();
            String line = "";
            BufferedReader fileReader = new BufferedReader(new FileReader(PATH_TO_CSV));
            fileReader.readLine();
            while ((line = fileReader.readLine()) != null) {
                String lineOptimize = null;
                boolean lineCheckDouble = false;
                if(line.indexOf("\"") > 0) {
                    lineOptimize = line.substring(line.indexOf("\""))
                            .replace("\"","")
                            .replace(",", ".");
                    lineCheckDouble = true;
                }
                String[] fragments = line.split(COMMA_DELIMITER);
                if (fragments.length > 0) {
                    if (lineCheckDouble) {
                        fragments[7] = lineOptimize;}

                    BankTransaction bankTransaction = new BankTransaction(
                            fragments[0], fragments[1], fragments[2], fragments[3],
                            fragments[4], fragments[5],
                            fragments[6], fragments[7], setMccCodes(fragments[5]));
                    operations.add(bankTransaction);
                }
            }
            for (int i = 0; i < operations.size(); i++) {
//                System.out.println(i + " opInf" + operations.get(i).getOperationInfo() + "  ref:");
//                System.out.println(setMccCodes(operations.get(i).getOperationInfo()));
            }
            System.out.println("INCOMES +" + operations.stream().mapToDouble(e -> Double.parseDouble(e.getIncome())).sum());
            System.out.println("EXPENSES -" + operations.stream().mapToDouble(e -> Double.parseDouble(e.getExpense())).sum());
        Map<String,Double> groupsExpense = operations.stream()
                .collect(Collectors.groupingBy(BankTransaction::getMccCode, Collectors.summingDouble(BankTransaction::expenseDouble)));
        groupsExpense.forEach((k,v) -> System.out.println(k + " count: " + v + "руб."));
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public static String setMccCodes(String operationInfo) {
        return operationInfo.substring(operationInfo.indexOf("MCC"));
    }
}

