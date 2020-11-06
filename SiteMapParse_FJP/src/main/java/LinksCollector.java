import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.RecursiveTask;

public class LinksCollector extends RecursiveTask<CopyOnWriteArraySet<String>> {
    private final String href;
    private static String startUrl;
    private static final CopyOnWriteArraySet<String> allLinks = new CopyOnWriteArraySet<>();

    public LinksCollector(String href) {
        this.href = href;
    }

    public LinksCollector(String href, String startUrl) {
        this.href = href;
        LinksCollector.startUrl = startUrl;
    }

    @Override
    protected CopyOnWriteArraySet<String> compute() {
        Set<LinksCollector> subTask = new HashSet<>();

        getHref(subTask);
        for (LinksCollector task : subTask) {
            task.join();
        }
        return allLinks;
    }

    private void getHref(Set<LinksCollector> subTask) {
        Document doc;
        Elements elements;
        try {
            Thread.sleep(1);
            doc = Jsoup.connect(href).get();
            elements = doc.select("a");
            for (Element el : elements) {
                String attr = el.attr("abs:href");
                if (!attr.isEmpty() && attr.startsWith(startUrl) && !allLinks.contains(attr) && !attr
                        .contains("#") && attr.endsWith("/")) {
                    subTask.add((LinksCollector) (new LinksCollector(attr)).fork());
                    allLinks.add(attr);
                }
            }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}

