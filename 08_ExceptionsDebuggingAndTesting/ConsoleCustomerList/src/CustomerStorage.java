import org.w3c.dom.ls.LSOutput;

import javax.sound.midi.Soundbank;
import java.util.HashMap;
import java.util.regex.Matcher;

public class CustomerStorage
{
    private HashMap<String, Customer> storage;

    public CustomerStorage()
    {
        storage = new HashMap<>();
    }

    public void addCustomer(String data)
    {
        String[] components = data.split("\\s+");
        String regexEmail = "^([a-z0-9_-]+\\.)*[a-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}$";
        String regexPhone = "\\+\\d+";
        String name = components[0] + " " + components[1];
        try {
            if (!components[2].matches(regexEmail)) {
                System.out.println("Regex error - e-mail. Введите vvv.v@vvv.ru");
                throw new Exception();
            }
            if (!components[3].matches(regexPhone)) {
                System.out.println("Regex error - phone. Введите +79215637722");
                throw new Exception();
            }
            storage.put(name, new Customer(name, components[3], components[2]));
        } catch (Exception e) {
                System.out.println("Введите верные данные");
        }
    }

    public void listCustomers()
    {
        storage.values().forEach(System.out::println);
    }

    public void removeCustomer(String name) throws Exception
    {
        try {
            if (!storage.containsKey(name)) {
                throw new Exception();
            }
            storage.remove(name);
        }
        catch (Exception e) {
            System.out.println("Нет такого Покупателя, посмотрите коммандой list");
        }

    }

    public int getCount()
    {
        return storage.size();
    }
}