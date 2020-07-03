import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        File folder;
        try {
            System.out.println("Введите путь к папке:");

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            //String inputPath = reader.readLine().replace("\\", File.separator);
            folder = new File("D:\\Videos_LG");
            File[] files = folder.listFiles();
            for (File file: files){
                System.out.println(file.getAbsolutePath() + " " + file.length() + " "+ file.isDirectory());

            }


        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
