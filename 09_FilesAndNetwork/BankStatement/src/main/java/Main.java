import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final String PATH_TO_CSV = "src/main/resources/movementList.csv";
    private static final String COMMA_DELIMITER = ",";

    public static void main(String[] args) {
        //System.out.println(PATH_TO_CSV);
        BufferedReader fileReader =null;

        try {
            // List<String> lines = Files.readAllLines(Paths.get(PATH_TO_CSV));
            List<BankTransaction> operations = new ArrayList<>();
            String line = "";
            fileReader = new BufferedReader(new FileReader(PATH_TO_CSV));
            fileReader.readLine();
            while ((line = fileReader.readLine()) != null) {
                String[] fragments = line.split(COMMA_DELIMITER);
                if (fragments.length > 0) {

                    BankTransaction bankTransaction = new BankTransaction(
                            fragments[0], fragments[1], fragments[2], fragments[3],
                            fragments[4], fragments[5],
                            Double.parseDouble(fragments[6]), Double.parseDouble(fragments[7]));
                    operations.add(bankTransaction);
                }
            }
            for (int i = 0; i < operations.size(); i++) {
                System.out.println(operations.get(i).operationInfo);
            }
            System.out.println(operations.stream().map(e -> e.income).count());

        }
        catch (Exception ex) {
            ex.printStackTrace();
        }


        }
    }

