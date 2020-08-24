import java.util.Objects;

public class Connection implements  Comparable<Connection>{
    private String line;
    private String station;

    public Connection(String line, String station){
        this.line = line;
        this.station = station;
    }

    public String getLine() {
        return line;
    }

    @Override
    public int compareTo(Connection o) {
        return line.compareTo(o.getLine());
    }
    @Override
    public boolean equals(Object o) {
        if(this == o) return  true;
        if(o == null || getClass() != o.getClass()) return false;
        Connection connection = (Connection) o;
        return Objects.equals(line, connection.line) && Objects.equals(station,connection.station);
    }

    @Override
    public int hashCode() {
        return Objects.hash(line, station);
    }
}
