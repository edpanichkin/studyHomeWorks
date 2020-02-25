import com.skillbox.airport.*;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        Date dateNow = new Date();
        var timeConst = 2 * 60 * 60 * 1000; // 2 часа
        Airport.getInstance().getTerminals().stream()
                .flatMap(Terminal -> Terminal.getFlights().stream())
                .filter(f -> f.getType().equals(Flight.Type.DEPARTURE))
                .filter(f -> (f.getDate().getTime() - dateNow.getTime()) > 0)
                .filter(f -> (f.getDate().getTime() - dateNow.getTime()) <= timeConst)
                .forEach(f -> System.out.printf("Вылет в: %s / Рейс: %s / Самолет: %s\n",
                        new SimpleDateFormat("HH:mm").format(f.getDate()),
                        f.getCode(),
                        f.getAircraft()));

// Первое решение, без Стрима
//        List<Terminal> terminalList = Airport.getInstance().getTerminals();
//        for (int i = 0; i < terminalList.size(); i++) {
//
//            for (int j = 0; j < terminalList.get(i).getFlights().size(); j++) {
//                long deltaDate = terminalList.get(i).getFlights().get(j).getDate().getTime() - date.getTime();
//                if (terminalList.get(i).getFlights().get(j).getType().toString().equals("DEPARTURE")
//                        && deltaDate <= 2 * 60 * 60 * 1000 && deltaDate > 0) {
//
//                    System.out.println("Терминал: " + terminalList.get(i).getName()
//                            + " / Вылет через: " + deltaDate / 1000 / 60 + "мин "
//                            + " / Рейс: " + terminalList.get(i).getFlights().get(j).getCode()
//                            + " / Самолет: " + terminalList.get(i).getFlights().get(j).getAircraft());
//                }
//            }
//        }
    }
}
