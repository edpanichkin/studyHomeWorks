import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {
    private final static String PATH_SOURCE = "https://skillbox.ru/code";
    private final static String ELEMENT_SEL = "a[href]";
    private static int SUM = 0;

    public static void main(String[] args) throws IOException {
        Document doc = Jsoup.connect(PATH_SOURCE).maxBodySize(0).get();
        Elements links = doc.select(ELEMENT_SEL);
        List<String> listHref= hrefList(doc.select(ELEMENT_SEL),PATH_SOURCE);
        printListDebug("RAWDATA", listHref);
        //printListDebug("CLEAN", pathListDmnClean(listHref, PATH_SOURCE));
        List<String> listHref2= hrefList(doc.select(ELEMENT_SEL),"https://www.skillbox.ru/code/design");
        printListDebug("code", listHref2);

      //  List<String> list = pathListDmnClean(listHref,PATH_SOURCE);
       // System.out.println("SIZE " + list.size());
      //  printListDebug("final", list);

    }

    //filter from garbage. only PATH & endBy /
    public static List<String> hrefList (Elements links, String path) throws IOException {

        List<String> listHref= new ArrayList<>();

        for (Element e: links){
            String s = e.attr("href");
            if (s.startsWith("/") && s.endsWith("/"))
            {
                s = PATH_SOURCE + s;
            }
            listHref.add(s);

        }
        sumHref(listHref.size());
        //printListDebug(path, pathListDmnClean(listHref,path));
        return pathListDmnClean(listHref,path);
        //return listHref;
    }

    public static List<String> pathListDmnClean (List<String> listToClean, String parent) throws IOException {
        Set<String> setClean = new HashSet<>();
        Document doc = null;
        for (String l: listToClean) {
            if(l.startsWith(parent) && l.endsWith("/")) {
                hrefList(Jsoup.connect(PATH_SOURCE).maxBodySize(0).get().select(ELEMENT_SEL),l);
                setClean.add(l);
            }
        }
        return new ArrayList<>(setClean);
    }

    public static void printListDebug (String info, List<String> list) {
        info = "DEBUG_PRINT " + info + " :";
        for (String l: list) {
            System.out.println(info + l + "  " + list.size());
        }
    }
//    public static void firstRekurs (List<String> list) throws IOException {
//        for (String l: list) {
//            hrefList(l);
//        }
//    }

    public static void sumHref(int s) {
        SUM += s;
    }
}