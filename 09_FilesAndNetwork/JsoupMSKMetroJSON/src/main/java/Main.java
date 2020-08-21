import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private final static String PATH_SOURCE = "https://www.moscowmap.ru/metro.html#lines";

    private final static String LINES = "span.js-metro-line";

    public static void main(String[] args) throws IOException {
        Document doc = Jsoup.connect(PATH_SOURCE).maxBodySize(0).get();
        List<Line> moscowLines = parseLines(doc.select(LINES));
        List<Station> moscowStations = parseStations(doc, moscowLines);

        PrintWriter writer = new PrintWriter("src/main/resources/msk.json");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();


        moscowStations = duplicateConnDelete(moscowStations); //тестовое решение не доделанное
        List<List<Connection>> connections = new ArrayList<>();
        for (int i = 0; i < moscowStations.size(); i++) {
            List<Connection> list = new ArrayList<>();
            if(!moscowStations.get(i).getConnects().isEmpty()){
                list.add(new Connection(moscowStations.get(i).getLine(),moscowStations.get(i).getName()));
                list.addAll(moscowStations.get(i).getConnects());
                Collections.sort(list);
                connections.add(list);
            }

        }
        Collections.sort(connections,new Comparator<List<Connection>>() {
            public int compare(List<Connection> o1, List<Connection> o2) {//
                return o1.size()-o2.size();
            }
        });
        Metro mskMetro = new Metro();

        mskMetro.setLines(moscowLines);
        mskMetro.setStations(moscowStations.stream()
                .collect(Collectors.groupingBy(Station::getLine, Collectors.mapping(Station::getName, Collectors.toList()))));

        mskMetro.setConnections(connections);


        writer.write(gson.toJson(mskMetro));
        writer.flush();
        writer.close();
    }
    private static List<Station> duplicateConnDelete(List<Station> station) {
        List<Integer> indexConnect = new ArrayList<>();
        for (int i = 0; i < station.size(); i++) {
            if (station.get(i).getConnects().size() > 0) {
                indexConnect.add(i);
            }
        }
        try {
            List<Integer> arrayK = new ArrayList<>();
            for (Integer k : indexConnect) {
                    if (!arrayK.contains(k)) {
                        String searchStName = station.get(k).getName();
                        String searchStLine = station.get(k).getLine();
                        String connectedStationName = station.get(k).getConnects().get(0).getStation();
                        String connectedStationLine = station.get(k).getConnects().get(0).getLine();
                        System.out.printf("%s | %s, %s  = %s, %s\n", k, searchStLine, searchStName, connectedStationLine, connectedStationName);

                        for (int i = 0; i < station.size(); i++) {
                            if (station.get(i).getLine().equals(connectedStationLine) && station.get(i).getName().equals(connectedStationName)) {
                                Station tmpStation = station.get(i);
                                arrayK.add(i);

                                if (tmpStation.getConnects().get(0).getLine().equals(searchStLine)
                                        && tmpStation.getConnects().get(0).getStation().equals(searchStName)) {
                                    tmpStation.getConnects().remove(0);
                                    System.out.println("_________NEXT______");
                                }
                            }
                        }
                    }
                }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return station;
    }
    private static List<Line> parseLines(Elements elements) {
        List<Line> lines = new ArrayList<>();
        for (int i = 0; i < elements.size(); i++) {
            String lineNum = elements.get(i).attr("data-line");
            String lineName = elements.get(i).text();
            lines.add(new Line(lineNum, lineName));
        }
        return lines;
    }

    private static List<Station> parseStations(Document doc, List<Line> lines) {
        List<Station> stations = new ArrayList<>();
        for (int j = 0; j < lines.size(); j++) {
            Elements e = doc.select("div[data-line=" + lines.get(j).getNumber() + "]").select("p");
            for (int i = 0; i < e.size(); i++) {
                stations.add(new Station(e.get(i).select("span.name").text(),lines.get(j).getNumber()));
                if (!e.get(i).select("span").attr("title").isEmpty()) {
                    Elements eTmp = e.get(i).select("span");
                    for (int k = 2; k < eTmp.size(); k++) {
                        stations.get(stations.size()-1)
                                .addConnects(connParseLineNum(eTmp.get(k).toString()),connParseStationName(eTmp.get(k).toString()));
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
