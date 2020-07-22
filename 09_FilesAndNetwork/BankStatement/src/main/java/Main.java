import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final String PATH_TO_CSV = "src/main/resources/movementList.csv";

    public static void main(String[] args) {
        //System.out.println(PATH_TO_CSV);
        ArrayList<BankTransaction> staff = new ArrayList<>();
        try
        {
            List<String> lines = Files.readAllLines(Paths.get(PATH_TO_CSV));
            for(String line : lines)
            {
                String[] fragments = line.split(",");
                if(fragments.length != 8) {
                    System.out.println("Wrong line: " + line);
                    continue;
                }
                staff.add(new BankTransaction(
                        fragments[0],fragments[1],fragments[2],fragments[3],
                        fragments[4],fragments[5],fragments[6],fragments[7]));
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
