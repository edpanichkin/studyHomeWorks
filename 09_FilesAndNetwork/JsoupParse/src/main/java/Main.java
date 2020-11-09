import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        Document doc = Jsoup.connect("https://lenta.ru").get();
        Elements elements = doc.select("img");
        System.out.println("SIZE -1   " + elements.get(elements.size()-3));
        System.out.println("SIZE -1   " + elements.get(elements.size()-2));
        System.out.println("SIZE LAST   " + elements.get(elements.size()-1));
        List<Path> pathFilesToCopy = new ArrayList<>();
        for (int i = 0; i<elements.size()-3; i++) {
            String str = elements.get(i).attr("src");
            URL url = new URL(str);
            Path target = Paths.get("src\\data\\" + str.substring(str.lastIndexOf("/")+1));
            Files.copy(url.openStream(),target, StandardCopyOption.REPLACE_EXISTING);
        }

    }
}
