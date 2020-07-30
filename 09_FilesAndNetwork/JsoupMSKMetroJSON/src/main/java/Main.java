import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.sound.midi.Soundbank;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.*;

public class Main {
    private final static String PATH_TARGET = "src\\data\\images\\";
    private final static String PATH_SOURCE = "https://www.moscowmap.ru/metro.html#lines";

    private final static String LINES = "span.js-metro-line";

    public static void main(String[] args) throws IOException {
        Document doc = Jsoup.connect(PATH_SOURCE).maxBodySize(0).get();
        List<Line> moscowLines = parseLines(doc.select(LINES));
        //moscowLines.forEach(m -> m.print());
        List<Station> moscowStations = parseStations(doc.select("span"));
        moscowStations.forEach(m -> m.print());

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
        for (int i = 0; i < elements.size()-3; i++) {//ИСПРАВИТЬ КОСТЫЛЬ!
            //System.out.println(elements.get(i) + " " + elements.get(i).toString().indexOf("js-metro-line"));
            if (elements.get(i).toString().indexOf("js-metro-line") > 0) { //вхождение в линию
                String lineName = elements.get(i).text();
                String lineNum = elements.get(i).attr("data-line");

                while (!elements.get(i + 1).toString().contains("js-metro-line")&&i<950) {
                    i++;
                    if(!elements.get(i).select("span.num").text().isEmpty()){
                        stationName = elements.get(i+1).select("span.name").text();
                        stations.add(new Station(stationName,lineName));
                    }
                    if(elements.get(i).toString().contains("t-icon-metroln")){
                        System.out.println(elements.get(i).attr("title"));
                    }
                    System.out.printf("!!!!!! i %s, %s || %s %s\n",
                            i, stationName, lineNum, lineName);

                }


            }


        }
        return stations;
    }

}
