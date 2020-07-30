import java.util.ArrayList;
import java.util.List;

public class Line {
    private String number;
    private String name;
    private List<Station> stations;

    public Line(String number, String name)
    {
        this.number = number;
        this.name = name;
        stations = new ArrayList<>();
    }
    public void print(){
        System.out.printf("%s: %s \n", number, name);
    }
}
