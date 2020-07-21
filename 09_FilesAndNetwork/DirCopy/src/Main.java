import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class Main extends SimpleFileVisitor {

    private static File directory;
    static Path pathSource = Paths.get("copy");
    static Path pathTarget = Paths.get("copy2");

    public static void main(String[] args) throws IOException {
        //System.out.println(pathSource.toAbsolutePath() + "\n");
        Files.walkFileTree(pathSource, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Path pathTo = pathTarget.resolve(file.getFileName());
                System.out.println(file.toString() + " " + file.getName(file.getNameCount()-2));
                System.out.println("pathTo: " + pathTo);

                Files.copy(file.toAbsolutePath(), pathTo, StandardCopyOption.REPLACE_EXISTING);

                return FileVisitResult.CONTINUE;
            }
        });
        System.out.println(fileCopy(pathSource.toFile()));
    }

    public static long fileCopy (File folder){
        File[] files = folder.listFiles();
        long bytes = 0;

        for (File file : files) {
            try {
                if (!file.isDirectory()) {
                    System.out.printf("Directory ");
                    bytes += file.length();
                } else {
                    bytes += fileCopy(new File(file.getAbsolutePath()));
                }
            } catch (Exception ex) {
                System.out.println(ex.getMessage() + " " + file.getAbsolutePath());
            }
        }
        return bytes;
    }
}