import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

public class Metro {
    private List<Line> lines;
    private Map<String, List<String>> stations;
    private List<List<Connection>> connections;

    public List<Line> getLines() {
        return lines;
    }

    public void setLines(List<Line> lines) {
        this.lines = lines;
    }

    public Map<String, List<String>> getStations() {
        return stations;
    }

    public void setStations(Map<String, List<String>> stations) {
        this.stations = stations;
    }

    public List<List<Connection>> getConnections() {
        return connections;
    }

    public void setConnections(List<List<Connection>> connections) {
        this.connections = connections;
    }


}
