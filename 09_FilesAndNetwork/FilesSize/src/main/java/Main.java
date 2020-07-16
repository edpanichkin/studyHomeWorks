import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    private static File directory;
    public static void main(String[] args) throws IOException{
        System.out.println("Введите путь к папке:");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        directory = new File(reader.readLine());
        sizeCounter(directory);
    }
    public static long sizeInDirCounter (File folder){
        File[] files = folder.listFiles();
        long bytes = 0;

            for (File file : files) {
                try {
                    if (!file.isDirectory()) {
                        bytes += file.length();
                    } else {
                        bytes += sizeInDirCounter(new File(file.getAbsolutePath()));
                    }
                } catch (Exception ex) {
                    System.out.println(ex.getMessage() + " " + file.getAbsolutePath());
                }
            }
        return bytes;
    }

    public static void sizeConverter (long sizeInBytes) {
        String[] lettersForSize = {"B", "kB", "MB", "GB", "TB"};
        int i = (int) Math.floor(Math.log10(sizeInBytes) / 3);
        System.out.printf("Директория %s размером: %.2f %s", directory,
                sizeInBytes / Math.pow(1024, i), lettersForSize[i]);
    }

    public static void sizeCounter (File folder){
        if (folder != null && folder.exists() && folder.isDirectory()) {
            sizeConverter(sizeInDirCounter(folder));
        }
        else {
            System.out.println("Ошибка ввода!");
        }
    }
}