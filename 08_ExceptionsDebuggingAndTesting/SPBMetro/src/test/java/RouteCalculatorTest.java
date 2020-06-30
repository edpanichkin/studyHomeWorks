import core.Line;
import core.Station;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RouteCalculatorTest extends TestCase {

    StationIndex stationIndex;
    RouteCalculator routeCalculator;
    List<Station> stations;

    @Override
    protected void setUp() {

        /* Схема тестовой линии

        [11]           [21]
        [12]-[переход]-[22]           [31]
        [13]           [23]-[переход]-[32]
        [14]           [24]           [33]
                                      [34]
        */
        Line lineA = new Line(1, "Первая");
        Line lineB = new Line(2, "Вторая");
        Line lineC = new Line(3, "Третья");

        stations = new ArrayList<>();
        stationIndex = new StationIndex();
        routeCalculator = new RouteCalculator(stationIndex);

        String[] names = {"11", "12", "13", "14",
                "21", "22", "23", "24",
                "31", "32", "33", "34"};

        for (int i = 0; i < names.length; i++) {
            if (i < 4) {
                stations.add(new Station(names[i], lineA));
            } else if (i < 8) {
                stations.add(new Station(names[i], lineB));
            } else {
                stations.add(new Station(names[i], lineC));
            }
        }

        for (Station station : stations) {
            station.getLine().addStation(station);
        }

        stationIndex.addLine(lineA);
        stationIndex.addLine(lineB);
        stationIndex.addLine(lineC);

        for (Station station : stations) {
            stationIndex.addStation(station);
        }

        stationIndex.addConnection(new ArrayList<>(Arrays.asList(stationIndex.getStation("12"),
                stationIndex.getStation("22"))));
        stationIndex.addConnection(new ArrayList<>(Arrays.asList(stationIndex.getStation("23"),
                stationIndex.getStation("32"))));
    }

    public void testCalculationRouteOnTheLine() throws NullPointerException {

        List<Station> testRoute = routeCalculator.getShortestRoute(
                stationIndex.getStation("11"),
                stationIndex.getStation("14"));

        double actual = RouteCalculator.calculateDuration(testRoute);
        double expected = 3 * 2.5; // 3 прогона

        assertEquals(expected, actual);
    }

    public void testCalculationRouteWithOneConnection() throws NullPointerException {

        List<Station> testRoute = routeCalculator.getShortestRoute(
                stationIndex.getStation("13"),
                stationIndex.getStation("24"));

        double actual = RouteCalculator.calculateDuration(testRoute);
        double expected = 3 * 2.5 + 3.5; // 3 прогона, 1 переход

        assertEquals(expected, actual);
    }

    public void testCalculationRouteWithTwoConnections() throws NullPointerException {

        List<Station> testRoute = routeCalculator.getShortestRoute(
                stationIndex.getStation("11"),
                stationIndex.getStation("34"));

        double actual = RouteCalculator.calculateDuration(testRoute);
        double expected = 4 * 2.5 + 2 * 3.5; // 4 прогона, 2 перехода

        assertEquals(expected, actual);
    }
}