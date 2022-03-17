import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class XMLHandler2 extends DefaultHandler {
  private long start;
  private Voter voter;
  private static SimpleDateFormat birthDayFormat = new SimpleDateFormat("yyyy.MM.dd");
  private HashMap<Voter, Integer> voterCounts;
  private ArrayList<String> voterList;

  public XMLHandler2() {
    voterCounts = new HashMap<>();
    voterList = new ArrayList<>();
  }

  @Override
  public void startDocument() throws SAXException {
    super.startDocument();
    start = System.currentTimeMillis();
  }

  @Override
  public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
    if (qName.equals("voter")) {
      voterList.add(attributes.getValue("name") + "," + attributes.getValue("birthDay"));
    }
  }

  public void printDuplicatedVotersInMap() {
    voterList.sort(new Comparator<String>() {
      @Override
      public int compare(String o1, String o2) {
        return o1.compareTo(o2);
      }
    });
    System.out.println(voterList.size());
    for (int i = 0; i < voterList.size() - 1; i++) {
      String strVoter = voterList.get(i);
      if (voterList.get(i + 1).equals(strVoter)) {
        String[] str = strVoter.split(",");
        int count = 0;

        try {
          voter = new Voter(str[0], birthDayFormat.parse(str[1]));
          count = voterCounts.getOrDefault(voter, 1);

          voterCounts.put(voter, count + 1);

        } catch (ParseException e) {
          e.printStackTrace();
        }
      }
    }

    for (Voter voter : voterCounts.keySet()) {
      int count = voterCounts.get(voter);
      if (count > 1) {
        System.out.println(voter.toString() + " - " + count);
      }
    }
  }

  @Override
  public void endDocument() throws SAXException {
    System.out.println("END PARSING: " + (System.currentTimeMillis() - start));
    super.endDocument();
  }
}
