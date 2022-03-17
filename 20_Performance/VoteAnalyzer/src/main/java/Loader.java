import org.apache.commons.io.FileUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class Loader {
  private static SimpleDateFormat birthDayFormat = new SimpleDateFormat("yyyy.MM.dd");
  private static SimpleDateFormat visitDateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");

  private static HashMap<Integer, WorkTime> voteStationWorkTimes = new HashMap<>();
  private static HashMap<Voter, Integer> voterCounts = new HashMap<>();

  public static void main(String[] args) throws Exception {
    String fileName = "res/data-18M.xml";
    SAXParserFactory factory = SAXParserFactory.newInstance();
    SAXParser parser = factory.newSAXParser();
    XMLHandler handler = new XMLHandler();
    XMLHandler2 handler2 = new XMLHandler2();

    long usage;
    long start;
    long end;
    String method;

    // DOMParse
    usage = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
    System.out.println(FileUtils.byteCountToDisplaySize(usage) + " / " + usage);
    start = System.currentTimeMillis();
    parseFile(fileName);
    printAfterDOMParse();
    method = "DOMParse";
    memoryUsage(method, fileName, usage, start);
    System.gc();
    Thread.sleep(1000);


    //    SAXParse
    usage = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
    System.out.println(FileUtils.byteCountToDisplaySize(usage) + " / " + usage);
    start = System.currentTimeMillis();
    parser.parse(new File(fileName), handler);
    //handler.printDuplicatedVoters();
    handler.printDuplicatedVotersInMap();
    method = "SAXParse";
    memoryUsage(method, fileName, usage, start);
    System.gc();
    Thread.sleep(1000);

    //    SAXParse2 (ArrayList)
    usage = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
    System.out.println(FileUtils.byteCountToDisplaySize(usage) + " / " + usage);
    start = System.currentTimeMillis();
    parser.parse(new File(fileName), handler2);
    //handler.printDuplicatedVoters();
    handler2.printDuplicatedVotersInMap();
    method = "SAXParse2";
    memoryUsage(method, fileName, usage, start);


//    // DOMParse + DB
//    usage = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
//    start = System.currentTimeMillis();
//    parseFileDB(fileName);
//    //DBConnection.printVoterCounts();
//    usage = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory() - usage;
//    end = System.currentTimeMillis() - start;
//    System.out.println("Memory usage after DOMParse -> DB : " + fileName + " = " + FileUtils.byteCountToDisplaySize(usage));
//    System.out.println("Time spent performing this operation: " + end);
//    System.out.println("_______________________________");
//
//  // DBConnection.printVoterCounts();
  }

  private static void memoryUsage(String method, String fileName, long usage, long start) {
    long end;
    usage = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory() - usage;
    end = System.currentTimeMillis() - start;
    System.out.println("Memory usage after " + method + ": " + fileName + " = " + FileUtils.byteCountToDisplaySize(usage) + " / " + usage);
    System.out.println("Time spent performing this operation: " + end);
    System.out.println("_______________________________");
  }


  private static void parseFile(String fileName) throws Exception {
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    DocumentBuilder db = dbf.newDocumentBuilder();
    Document doc = db.parse(new File(fileName));

    //findEqualVoters(doc);
    //fixWorkTimes(doc);

  }

//  private static void parseFileDB(String fileName) throws Exception {
//    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//    DocumentBuilder db = dbf.newDocumentBuilder();
//    Document doc = db.parse(new File(fileName));
//
//    //findEqualVotersDB(doc);
//
//  }

  private static void printAfterDOMParse() {
    //Printing results
    System.out.println("Voting station work times: ");
    for (Integer votingStation : voteStationWorkTimes.keySet()) {
      WorkTime workTime = voteStationWorkTimes.get(votingStation);
      System.out.println("\t" + votingStation + " - " + workTime);
    }

    System.out.println("Duplicated voters: ");
    for (Voter voter : voterCounts.keySet()) {
      Integer count = voterCounts.get(voter);
      if (count > 1) {
        System.out.println("\t" + voter + " - " + count);
      }
    }
  }

  private static void findEqualVoters(Document doc) throws Exception {
//    NodeList voters = doc.getElementsByTagName("voter");
//    int votersCount = voters.getLength();
//    for (int i = 0; i < votersCount; i++) {
//      Node node = voters.item(i);
//      NamedNodeMap attributes = node.getAttributes();
//
//      String name = attributes.getNamedItem("name").getNodeValue();
//      Date birthDay = birthDayFormat.parse(attributes.getNamedItem("birthDay").getNodeValue());
//
//      Voter voter = new Voter(name, birthDay);
//      Integer count = voterCounts.get(voter);
//      voterCounts.put(voter, count == null ? 1 : count + 1);
//    }
  }

  private static void findEqualVotersDB(Document doc) throws Exception {
    NodeList voters = doc.getElementsByTagName("voter");
    int votersCount = voters.getLength();
    for (int i = 0; i < votersCount; i++) {
      Node node = voters.item(i);
      NamedNodeMap attributes = node.getAttributes();

      String name = attributes.getNamedItem("name").getNodeValue();
      String birthDay = attributes.getNamedItem("birthDay").getNodeValue();
      DBConnection.countVoter(name, birthDay);
//      Voter voter = new Voter(name, birthDay);
//      Integer count = voterCounts.get(voter);
//      voterCounts.put(voter, count == null ? 1 : count + 1);
    }
    DBConnection.executeMultiInsert();
  }

  private static void fixWorkTimes(Document doc) throws Exception {
    NodeList visits = doc.getElementsByTagName("visit");
    int visitCount = visits.getLength();
    for (int i = 0; i < visitCount; i++) {
      Node node = visits.item(i);
      NamedNodeMap attributes = node.getAttributes();

      Integer station = Integer.parseInt(attributes.getNamedItem("station").getNodeValue());
      Date time = visitDateFormat.parse(attributes.getNamedItem("time").getNodeValue());
      WorkTime workTime = voteStationWorkTimes.get(station);
      if (workTime == null) {
        workTime = new WorkTime();
        voteStationWorkTimes.put(station, workTime);
      }
      workTime.addVisitTime(time.getTime());
    }
  }
}