public class Station {

    private String line;
    private String name;

    public Station(String name, String line)
    {
        this.name = name;
        this.line = line;
    }
    public void print(){
        System.out.printf("%s: %s \n", line, name);
    }
}
