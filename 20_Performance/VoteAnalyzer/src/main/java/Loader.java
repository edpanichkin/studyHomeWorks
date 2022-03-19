import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;

public class Loader {

  public static void main(String[] args) throws Exception {
    String fileName = "res/data-18M.xml";
    SAXParserFactory factory = SAXParserFactory.newInstance();
    SAXParser parser = factory.newSAXParser();

    XMLHandlerDB handlerDB = new XMLHandlerDB();

    long usage;
    long start;
    long end;

    usage = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
    start = System.currentTimeMillis();
    parser.parse(new File(fileName), handlerDB);
    DBConnection.printVoterCounts();
    usage = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory() - usage;
    memoryUsage("SAXParse for DB", fileName, usage, start);
  }

  private static void memoryUsage(String method, String fileName, long usage, long start) {
    long end;
    usage = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory() - usage;
    end = System.currentTimeMillis() - start;
    //System.out.println("Memory usage after " + method + ": " + fileName + " = " + FileUtils.byteCountToDisplaySize(usage) + " / " + usage);
    System.out.println("Time spent performing this operation: " + end);
    System.out.println("_______________________________");
  }
}