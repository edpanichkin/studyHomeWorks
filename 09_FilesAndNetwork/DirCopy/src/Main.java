import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class Main extends SimpleFileVisitor {

    static Path pathSource = Paths.get("D:\\test");
    static Path pathTarget = Paths.get("D:\\test2");

    public static void main(String[] args) throws IOException {
        filesAndDirsFullCopy(pathSource.toFile());
    }

    public static void filesAndDirsFullCopy (File folder) throws IOException {
        File[] files = folder.listFiles();
        if(!Files.exists(pathTarget)){
           Files.createDirectory(pathTarget);
        }
        for (File file : files) {
            try {
                if (!file.isDirectory()) {
                    Files.copy(file.toPath(), pathChange(file.toString()),
                            StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.COPY_ATTRIBUTES);
                } else {
                    String str = file.getAbsolutePath();
                    if(!Files.exists(pathChange(str))) {
                        Files.createDirectory(pathChange(str));
                    }
                    filesAndDirsFullCopy(new File(str));
                }
            } catch (Exception ex) {
                System.out.println(ex.getMessage() + " ERROR " + file.getAbsolutePath());
                ex.printStackTrace();
            }
        }
    }
    public static Path pathChange (String file){
        return Paths.get(file.replace(pathSource.toString(),pathTarget.toString()));
    }
}