import java.util.ArrayList;
import java.util.List;

public class Station {

    private String line;
    private String name;
    private List<Connection> connects = new ArrayList<>();

    public List<Connection> getConnects() {
        return connects;
    }

    public void addConnects(String stationTo, String lineTo) {
        connects.add(new Connection(stationTo, lineTo));
    }

    public Station(String name, String line)
    {
        this.name = name;
        this.line = line;
    }

    public String getLine() {
        return line;
    }

    public String getName() {
        return name;
    }

    public void print(){
        System.out.printf("%s: %s \n", line, name);
    }
    public void printConnections(){
        System.out.printf("IN %s: %s\n", line, name);
        connects.forEach(System.out::println);
    }
}
