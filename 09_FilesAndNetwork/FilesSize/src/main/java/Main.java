import java.io.*;
import java.nio.file.*;

public class Main {

    private static File directory;
    public static void main(String[] args) throws IOException{
        //System.out.println("Введите путь к папке:");
        //BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        //directory = new File(reader.readLine());
        if (args.length > 0) {
            directory = new File(args[0]);
            System.out.println("IO");
            sizeCounter(directory);
            try {
                long sizeDirNIO = Files.walk(Paths.get(directory.getAbsolutePath()))
                        .map(Path::toFile)
                        .filter(File::isFile)
                        .mapToLong(File::length)
                        .sum();
                System.out.println("NIO");
                sizeConverter(sizeDirNIO);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        else{
            System.out.println("NO INPUT ARGS!");
        }

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
        System.out.printf("Directory %s size: %.2f %s \n", directory,
                sizeInBytes / Math.pow(1024, i), lettersForSize[i]);
    }

    public static void sizeCounter (File folder){
        if (folder != null && folder.exists() && folder.isDirectory()) {
            sizeConverter(sizeInDirCounter(folder));
        }
        else {
            System.out.println("INPUT ERROR! " + directory.toString());
        }
    }
}