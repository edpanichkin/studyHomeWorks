import java.util.*;

public class Metro {
    private List<Line> lines;
    private Map<String, List<String>> stations;
    private Set<List<Connection>> connections;

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

    public Set<List<Connection>> getConnections() {
        return connections;
    }

    public void setConnections(Set<List<Connection>> connections) {
        this.connections = connections;
    }


}
