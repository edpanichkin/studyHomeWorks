import java.io.*;
import java.util.*;
import java.util.concurrent.ForkJoinPool;

public class Main {
    private final static String PATH_SOURCE = "https://skillbox.ru";

    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        LinksCollector linksCollector = new LinksCollector(PATH_SOURCE, PATH_SOURCE);

        SortedSet<String> siteMap = new TreeSet<>(new ForkJoinPool().invoke(linksCollector));
        System.out.println("Время сканирования "
                + ((System.currentTimeMillis() - start) / 1000) + " сек.");
        System.out.println(siteMap.size());
        printSet(siteMap);
//        printSet(mapSubordinate(mapReadFromFile()));
    }

//    private static void writeFile(SortedSet<String> map) {
//        String filePath = "siteMap.txt";
//
//        File file = new File(filePath);
//        try (PrintWriter writer = new PrintWriter(file)) {
//            for (String s: map) {
//                writer.write(s + "\n");
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//    }
//    private static SortedSet<String> mapReadFromFile () throws IOException {
//        String filePath = "siteMap.txt";
//        SortedSet<String> readMap = new TreeSet<>();
//        File file = new File(filePath);
//        FileReader fr = new FileReader(file);
//        BufferedReader reader = new BufferedReader(fr);
//        String line = reader.readLine();
//        while (line !=null) {
//            readMap.add(line);
//            line = reader.readLine();
//        }
//        return readMap;
//    }
    private static void printSet (Set<String> set)  {
        int i = 0;
        for (String s : set) {
            i++;
            System.out.println(s);
        }
    }
//    private static Set<String> mapSubordinate (SortedSet<String> map){
//        List<String> mapSub = new ArrayList<>(map);
//        String preHref = PATH_SOURCE;
//        String space = " - ";
//        for (int i = 1; i < mapSub.size()-1; i++) {
//            if (mapSub.get(i).contains(preHref)) {
//                if(mapSub.get(i+1).contains(mapSub.get(i))){
//                    preHref = mapSub.get(i);
//                    space = space + " - ";
//                }
//                mapSub.set(i, space + mapSub.get(i));
//                //System.out.println(mapSub.get(i));
//               // System.out.printf("%s, %s, %s \n", preHref,space,mapSub.get(i));
//            }else {
//                preHref = PATH_SOURCE;
//                space = "   ";
//            }
//        }
//        return new TreeSet<>(mapSub);
//    }
}