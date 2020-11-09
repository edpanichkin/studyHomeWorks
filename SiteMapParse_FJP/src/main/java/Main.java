import java.io.*;
import java.util.*;
import java.util.concurrent.ForkJoinPool;

public class Main {
    private final static String PATH_SOURCE = "https://skillbox.ru";

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        LinksCollector linksCollector = new LinksCollector(PATH_SOURCE, PATH_SOURCE);
        SortedSet<String> siteMap = new TreeSet<>(new ForkJoinPool().invoke(linksCollector));
        System.out.println("Время сканирования "
                + ((System.currentTimeMillis() - start) / 1000) + " сек.");
        System.out.println("Найдено ссылок:" + siteMap.size());
        writeFile(domainLevelToListChange(siteMap));        
    }

    private static void writeFile(List<String> map) {
        String filePath = "siteMap.txt";
        File file = new File(filePath);
        try (PrintWriter writer = new PrintWriter(file)) {
            for (String s: map) {
                writer.write(s + "\n");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static SortedSet<String> mapReadFromFile () throws IOException {
        String filePath = "siteMap.txt";
        SortedSet<String> readMap = new TreeSet<>();
        File file = new File(filePath);
        FileReader fr = new FileReader(file);
        BufferedReader reader = new BufferedReader(fr);
        String line = reader.readLine();
        while (line !=null) {
            readMap.add(line);
            line = reader.readLine();
        }
        return readMap;
    }
   
    private static List<String> domainLevelToListChange(SortedSet<String> map) {
        List<String> listMap = new ArrayList<>(map);
        for (int i=1; i<listMap.size(); i++){
            char[] c = listMap.get(i).toCharArray();
            int freq = -3;
            StringBuilder space = new StringBuilder("\t");
            for (char value : c) {
                if (value == '/') {
                    if (freq > 0) {
                        space.append(space);
                    }
                    freq++;
                }
            }
            listMap.set(i, space + listMap.get(i));
        }
    return listMap;
    }    
}