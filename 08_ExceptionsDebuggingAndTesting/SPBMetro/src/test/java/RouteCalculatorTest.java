import core.Line;
import core.Station;
import org.junit.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class RouteCalculatorTest{

    StationIndex stationIndex;
    RouteCalculator routeCalculator;
    List<Station> stations;

    @Before
    public void setUp() {

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

    @Test
    public void testRoutePath(){

        List<Station> testRoute = routeCalculator.getShortestRoute(
                stationIndex.getStation("31"),
                stationIndex.getStation("14"));
        List<Station> expectedRoute = new ArrayList<>();

        expectedRoute.add(stationIndex.getStation("31"));
        expectedRoute.add(stationIndex.getStation("32"));
        expectedRoute.add(stationIndex.getStation("23"));
        expectedRoute.add(stationIndex.getStation("22"));
        expectedRoute.add(stationIndex.getStation("12"));
        expectedRoute.add(stationIndex.getStation("13"));
        expectedRoute.add(stationIndex.getStation("14"));

        assertEquals(expectedRoute, testRoute);
    }
    @Test
    public void testCalculationRouteOnTheLine(){

        List<Station> testRoute = routeCalculator.getShortestRoute(
                stationIndex.getStation("11"),
                stationIndex.getStation("14"));

        double actual = RouteCalculator.calculateDuration(testRoute);
        double expected = 3 * 2.5; // 3 прогона

        assertEquals(expected, actual,0.0);
    }

    @Test
    public void testCalculationRouteWithOneConnection(){

        List<Station> testRoute = routeCalculator.getShortestRoute(
                stationIndex.getStation("13"),
                stationIndex.getStation("24"));

        double actual = RouteCalculator.calculateDuration(testRoute);
        double expected = 3 * 2.5 + 3.5; // 3 прогона, 1 переход

        assertEquals(expected, actual, 0.0);
    }

    @Test
    public void testCalculationRouteWithTwoConnections(){

        List<Station> testRoute = routeCalculator.getShortestRoute(
                stationIndex.getStation("11"),
                stationIndex.getStation("34"));

        double actual = RouteCalculator.calculateDuration(testRoute);
        double expected = 4 * 2.5 + 2 * 3.5; // 4 прогона, 2 перехода

        assertEquals(expected, actual, 0.0);
    }
}