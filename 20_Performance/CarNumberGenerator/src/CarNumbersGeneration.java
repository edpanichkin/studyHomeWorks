import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class CarNumbersGeneration implements Runnable {
  private final int num;

  public CarNumbersGeneration(int num) {
    this.num = num;
  }

  public void generateNumbers() throws FileNotFoundException {

    PrintWriter writer = new PrintWriter("res/numbers" + num + ".txt");
    char[] letters = {'У', 'К', 'Е', 'Н', 'Х', 'В', 'А', 'Р', 'О', 'С', 'М', 'Т'};

    for (int regionCode = 1; regionCode < 100; regionCode++) {
      StringBuilder stringBuilder = new StringBuilder();
      String strReg = padNumber(regionCode, 2);
      for (int number = 1; number < 1000; number++) {
        String strNum = padNumber(number, 3);
        for (char firstLetter : letters) {
          for (char secondLetter : letters) {
            for (char thirdLetter : letters) {
              stringBuilder
                      .append(firstLetter)
                      .append(strNum)
                      .append(secondLetter)
                      .append(thirdLetter)
                      .append(strReg)
                      .append('\n');
            }
          }
        }
      }
      writer.write(stringBuilder.toString());
    }
    writer.flush();
    writer.close();
  }

  private static String padNumber(int number, int numberLength) {
    StringBuilder numberStr = new StringBuilder();
    int padSize = numberLength - String.valueOf(number).length();
    return numberStr
            .append("0".repeat(Math.max(0, padSize)))
            .append(number).toString();
  }

  @Override
  public void run() {
    try {
      generateNumbers();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }
}
