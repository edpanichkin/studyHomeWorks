import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


public class Main {
    private final static String PATH_TARGET = "src\\data\\images\\";
    private final static String PATH_SOURCE = "https://lenta.ru";

    public static void main(String[] args) throws IOException {
        imgFileDownload();
        }

    private static void imgFileDownload () throws IOException {
        Document doc = Jsoup.connect(PATH_SOURCE).get();
        Elements elements = doc.select("img.g-picture");
        if(!Files.exists(Paths.get(PATH_TARGET))){
            Files.createDirectory(Paths.get(PATH_TARGET));
        }
        for (Element element : elements) {
            String absStr = element.attr("abs:src");
            String fileName = absStr.substring(absStr.lastIndexOf("/") + 1);
            URL url = new URL(absStr);
            Path target = Paths.get(PATH_TARGET + fileName);

            Files.copy(url.openStream(), target, StandardCopyOption.REPLACE_EXISTING);
            System.out.printf("FILE > %s >> DOWNLOADED\nFROM > %s\n\n", target.getFileName(), absStr);
        }
    }
}