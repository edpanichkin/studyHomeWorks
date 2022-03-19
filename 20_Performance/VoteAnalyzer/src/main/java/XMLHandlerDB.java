import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;

public class XMLHandlerDB extends DefaultHandler {
  private long start;
  private Voter voter;
  private static SimpleDateFormat birthDayFormat = new SimpleDateFormat("yyyy.MM.dd");
  //private static SimpleDateFormat birthDayFormatDB = new SimpleDateFormat("yyyy-MM-dd");
  private HashMap<Voter, Integer> voterCounts;

  public XMLHandlerDB() {
    voterCounts = new HashMap<>();
  }

  @Override
  public void startDocument() throws SAXException {
    System.out.println("Parsing process started");
    start = System.currentTimeMillis();
  }

  @Override
  public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
    if (qName.equals("voter")) {
      try {
        voter = new Voter(attributes.getValue("name"),
                birthDayFormat.parse(attributes.getValue("birthDay")));
      } catch (ParseException e) {
        e.printStackTrace();
      }
      int count = voterCounts.getOrDefault(voter, 0);
      voterCounts.put(voter, ++count);
    }
  }

  @Override
  public void endDocument() throws SAXException {
    System.out.println("END PARSING: " + (System.currentTimeMillis() - start)
            + "\nvoterCounts size is: " + voterCounts.size());
    putInDB();
  }

  private void putInDB() {
    try {
      for (Voter voter : voterCounts.keySet()) {
        int count = voterCounts.get(voter);
        DBConnection.countVoter(voter.getName(), birthDayFormat.format(voter.getBirthDay()), count);
      }
      DBConnection.executeMultiInsert();

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
