
import org.redisson.Redisson;
import org.redisson.api.RKeys;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RedissonClient;
import org.redisson.client.RedisConnectionException;
import org.redisson.config.Config;

import java.util.*;

import static java.lang.System.out;

public class RedisStorage {

    // Объект для работы с Redis
    private RedissonClient redisson;

    // Объект для работы с Sorted Set'ом
    private RScoredSortedSet<String> onlineUsers;

    private final static String KEY = "ONLINE_USERS";
    private static final int SLEEP = 1000;

    private double getTs() {
        return new Date().getTime() / 1000;
    }

    public void print () {
        out.println("— На главной странице показываем пользователя -- "
                + onlineUsers.last());
        }

    void init() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        try {
            redisson = Redisson.create(config);
        } catch (RedisConnectionException Exc) {
            out.println("Не удалось подключиться к Redis");
            out.println(Exc.getMessage());
        }
        onlineUsers = redisson.getScoredSortedSet(KEY);
    }

    void shutdown() {
        redisson.shutdown();
    }

    // Поднимает в выдаче после платежа
    void userPaidUp(String user_id) throws InterruptedException {
        onlineUsers.add(getTs(), user_id);
        out.println("> Пользователь " + user_id + " оплатил платную услугу ");
        Thread.sleep(SLEEP);
        print();
    }
    void userOnline (String user_id) throws InterruptedException {
        onlineUsers.add(getTs(), user_id);
        Thread.sleep(SLEEP);
        print();
    }
}
