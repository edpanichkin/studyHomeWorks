import java.util.ArrayList;
import java.util.Random;

public class Main {
    private static final int USERS = 20;
    public static void main(String[] args) throws InterruptedException {

        RedisStorage redis = new RedisStorage();
        redis.init("redis://127.0.0.1:6379");
        int i = 1;
        ArrayList<String> usersUpdated = new ArrayList<>(fillArray());
        for (;;) {
            if (Math.random() <= 0.1) {
                String userPaid = String.valueOf(new Random().nextInt(USERS - 1) + 1);
                usersUpdated.remove(userPaid);
                redis.userPaidUp(userPaid);
            }
            if (usersUpdated.contains(String.valueOf(i))) {
                redis.userOnline(String.valueOf(i));
            }
            i++;

            if (i == USERS + 1) {
                i = 1;
                usersUpdated = new ArrayList<>(fillArray());
                System.out.println("_________________________");
            }
        }
    }

    public static ArrayList<String> fillArray () {
        ArrayList<String> array = new ArrayList<>();
        for (int i = 1; i<=USERS; i++){
            array.add(String.valueOf(i));
        }
        return array;
    }
}



