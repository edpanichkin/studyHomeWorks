import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    private final static String PATH_SOURCE = "https://www.moscowmap.ru/metro.html#lines";

    private final static String LINES = "span.js-metro-line";

    public static void main(String[] args) throws IOException {
        Document doc = Jsoup.connect(PATH_SOURCE).maxBodySize(0).get();
        List<Line> moscowLines = parseLines(doc.select(LINES));
        List<Station> moscowStations = parseStations(doc.select("span"));
        //moscowStations.forEach(Station::printConnections);
        toMapStations(moscowStations, moscowLines);
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

    private static List<Station> parseStations(Elements elements) {
        List<Station> stations = new ArrayList<>();
        String stationName = "";
        int stationIndex = 0;
        for (int i = 0; i < elements.size()-3; i++) {//ИСПРАВИТЬ КОСТЫЛЬ!
            if (elements.get(i).toString().indexOf("js-metro-line") > 0) { //вхождение в линию
                String lineName = elements.get(i).text();
                String lineNum = elements.get(i).attr("data-line");

                while (!elements.get(i + 1).toString().contains("js-metro-line")&&i<950) {
                    i++;
                    if(!elements.get(i).select("span.num").text().isEmpty()){
                        stationName = elements.get(i+1).select("span.name").text();
                        stations.add(new Station(stationName,lineName));
                        stationIndex++;
                    }
                    if(elements.get(i).toString().contains("t-icon-metroln")){
                        stations.get(stationIndex-1).addConnects(elements.get(i).attr("title"));
                    }
                }
            }
        }
        return stations;
    }
    private static void toMapStations (List<Station> stations, List<Line> lines) throws FileNotFoundException {
        Map<String, List<Station>> map = stations.stream()
                .collect(Collectors.groupingBy(Station::getLine));
        PrintWriter writer = new PrintWriter("src/main/resources/msk.json");
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();

        writer.write(.toJSONString());
        writer.flush();
        writer.close();



//        //STATIONS
//        writer.write("{\n");
//        writer.write("\t\"stations\" : {\n");
//        for(int i = 0; i < lines.size(); i++) {
//            if(i > 0) {
//                writer.write("\t\t],\n");
//            }
//            writer.write("\t\t\"" + lines.get(i).getNumber() + "\" : [\n");
//            map.get(lines.get(i).getName())
//                    .forEach(s -> {
//                    writer.write("\t\t\t\"" + s.getName() + "\",\n");
//                    });
//            if(i == (lines.size() - 1)) {
//                writer.write("\t\t],\n\t},\n");
//            }
//        }
//        //CONNECTIONS
//        //LINES
//        writer.write("\t\"lines\" : [\n\t\t{\n");
//        for (int i = 0; i < lines.size() ; i++) {
//            if(i > 0) {
//                writer.write("\t\t{\n");
//            }
//            writer.write("\t\t\t\"number\" : \"" + lines.get(i).getNumber() +
//                    "\",\n\t\t\t\"name\" : \"" + lines.get(i).getName() + "\"\n");
//            if(i < lines.size() - 1){
//                writer.write("\t\t},\n");
//            }
//            if(i == lines.size() - 1){
//                writer.write("\t\t}\n");
//            }
//
//        }
//        writer.flush();
//        writer.close();
    }

}
