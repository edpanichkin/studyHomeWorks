import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main
{
    private static String staffFile = "data/staff.txt";
    private static String dateFormat = "dd.MM.yyyy";

    public static void main(String[] args) {
        ArrayList<Employee> staff = loadStaffFromFile();

//Задание 7.2
        System.out.println("____________Задание 7.2________________");
        staff.stream().sorted(Main::compare).forEach(System.out::println);

//Задание 7.8
        System.out.println("____________Задание 7.8________________");
       staff.stream().filter(employee -> employee.getWorkStart().getYear() >= (2017-1990))
               .max(Comparator.comparing(Employee::getSalary)).ifPresent(System.out::println);
    }

    private static ArrayList<Employee> loadStaffFromFile()
    {
        ArrayList<Employee> staff = new ArrayList<>();
        try
        {
            List<String> lines = Files.readAllLines(Paths.get(staffFile));
            for(String line : lines)
            {
                String[] fragments = line.split("\t");
                if(fragments.length != 3) {
                    System.out.println("Wrong line: " + line);
                    continue;
                }
                staff.add(new Employee(
                    fragments[0],
                    Integer.parseInt(fragments[1]),
                    (new SimpleDateFormat(dateFormat)).parse(fragments[2])
                ));
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return staff;
    }
//для Лямбды, написанная сортировка по зп и алфавиту
    public static int compare(Employee o1, Employee o2) {
        int result;
        if (o1.getSalary().compareTo(o2.getSalary()) < 0) {
            result = -1;
        } else result = 1;
        if (o1.getSalary().compareTo(o2.getSalary()) == 0) {
            result = o1.getName().compareTo(o2.getName());
        }
        return result;
    }
}