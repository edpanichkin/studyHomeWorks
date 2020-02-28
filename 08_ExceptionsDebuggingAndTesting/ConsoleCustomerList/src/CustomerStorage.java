import java.util.HashMap;

public class CustomerStorage
{
    private HashMap<String, Customer> storage;

    public CustomerStorage()
    {
        storage = new HashMap<>();
    }

    public void addCustomer(String data) throws Exception
    {
        String[] components = data.split("\\s+");
        String regexEmail = "^([a-z0-9_-]+\\.)*[a-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}$";
        String regexPhone = "\\+\\d{11}";
        String name = components[0] + " " + components[1];
        if (!components[2].matches(regexEmail)) {
            throw new Exception("Regex error - e-mail. Введите vvv.v@vvv.ru");
        }
        if (!components[3].matches(regexPhone)) {
            throw new Exception("Regex error - phone. Введите +79215637722");
        }
        storage.put(name, new Customer(name, components[3], components[2]));
    }

    public void listCustomers()
    {
        storage.values().forEach(System.out::println);
    }

    public void removeCustomer(String name) throws Exception
    {
        if (!storage.containsKey(name)) {
            throw new Exception("Нет такого Покупателя, посмотрите коммандой list");
        }
        storage.remove(name);
    }

    public int getCount()
    {
        return storage.size();
    }
}
