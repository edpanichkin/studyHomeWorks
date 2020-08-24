import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private final static String PATH_SOURCE = "https://www.moscowmap.ru/metro.html#lines";
    private final static String JSON_SOURCE = "src/main/resources/msk.json";

    private final static String LINES = "span.js-metro-line";

    public static void main(String[] args) throws IOException {
        Document doc = Jsoup.connect(PATH_SOURCE).maxBodySize(0).get();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        List<Line> moscowLines = parseLines(doc.select(LINES));
        List<Station> moscowStations = parseStations(doc, moscowLines);

        Set<List<Connection>> connections = new HashSet<>();
        for (Station moscowStation : moscowStations) {
            List<Connection> list = new ArrayList<>();
            if (!moscowStation.getConnects().isEmpty()) {
                list.add(new Connection(moscowStation.getLine(), moscowStation.getName()));
                list.addAll(moscowStation.getConnects());
                list.sort((o1, o2) -> o1.getLine().compareTo(o2.getLine()));
                connections.add(list);
            }
        }

        Metro mskMetro = new Metro();
        mskMetro.setLines(moscowLines);
        mskMetro.setStations(moscowStations.stream()
                .collect(Collectors.groupingBy(Station::getLine,
                        Collectors.mapping(Station::getName, Collectors.toList()))));
        mskMetro.setConnections(connections);

        try(PrintWriter writer = new PrintWriter(JSON_SOURCE)) {
            writer.write(gson.toJson(mskMetro));
        }
    }

    private static List<Line> parseLines(Elements elements) {
        List<Line> lines = new ArrayList<>();
        for (Element e : elements) {
            String lineNum = e.attr("data-line");
            String lineName = e.text();
            lines.add(new Line(lineNum, lineName));
        }
        return lines;
    }

    private static List<Station> parseStations(Document doc, List<Line> lines) {
        List<Station> stations = new ArrayList<>();
        for (Line line : lines) {
            Elements elements = doc.select("div[data-line=" + line.getNumber() + "]").select("p");
            for (Element e : elements) {
                stations.add(new Station(e.select("span.name").text(), line.getNumber()));
                if (!e.select("span").attr("title").isEmpty()) {
                    Elements connects = e.select("span");
                    for (int k = 2; k < connects.size(); k++) {
                        stations.get(stations.size() - 1)
                                .addConnects(connParseLineNum(connects.get(k).toString()),
                                        connParseStationName(connects.get(k).toString()));
                    }
                }
            }
        }
        return stations;
    }
    private static String connParseLineNum (String str){
        return  str.substring(str.indexOf("ln-"),str.indexOf("\" title")).replace("ln-","");
    }
    private static String connParseStationName (String str){
        return str.substring(str.indexOf("«"),str.indexOf("»")).replace("«","");
    }
}
