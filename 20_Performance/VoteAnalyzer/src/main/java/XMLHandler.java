import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class XMLHandler extends DefaultHandler {
  private long start;
  private Voter voter;
  private static SimpleDateFormat birthDayFormat = new SimpleDateFormat("yyyy.MM.dd");
  private HashMap<Voter, Integer> voterCounts;
  private ArrayList<String> voterList;

  public XMLHandler() {
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
    if (qName.equals("voter") && voter == null) {
      Date birthDay = null;
      try {
        birthDay = birthDayFormat.parse(attributes.getValue("birthDay"));
      } catch (ParseException e) {
        e.printStackTrace();
      }
      voter = new Voter(attributes.getValue("name"), birthDay);
    } else if (qName.equals("visit") && voter != null) {
      int count = voterCounts.getOrDefault(voter, 0);
      voterCounts.put(voter, count + 1);
    }
  }


  @Override
  public void endElement(String uri, String localName, String qName) throws SAXException {
    if (qName.equals("voter")) {
      voter = null;
    }
  }

  public void printDuplicatedVotersInMap() {


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
