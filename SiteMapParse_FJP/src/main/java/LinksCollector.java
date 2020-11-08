import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class LinksCollector extends RecursiveTask<Set<String>> {
    private final String url;
    private static String startUrl;
    private static final Set<String> allLinks = ConcurrentHashMap.newKeySet();

    public LinksCollector(String url) {
        this.url = url;
    }

    public LinksCollector(String url, String startUrl) {
        this.url = url;
        LinksCollector.startUrl = startUrl;
    }

    @Override
    protected Set<String> compute() {
        try {
            Thread.sleep(1);
            Elements elements = Jsoup.connect(url).get().select("a");
            List<LinksCollector> subTask = new ArrayList<>(createLinksCollectorTasks(elements));
            subTask.forEach(ForkJoinTask::fork);
            subTask.forEach(ForkJoinTask::join);
        }
        catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
        return allLinks;
    }

    private List<LinksCollector> createLinksCollectorTasks(Elements elements) {
        List<LinksCollector> tasks = new ArrayList<>();
        for (Element el : elements) {
            String attr = el.attr("abs:href");
            if (!attr.isEmpty() && attr.startsWith(startUrl) && !allLinks.contains(attr)
                    && !attr.contains("#") && attr.endsWith("/")) {
                    allLinks.add(attr);
                    tasks.add(new LinksCollector(attr));
            }
        }
        return tasks;
    }
}

