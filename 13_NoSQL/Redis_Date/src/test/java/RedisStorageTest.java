import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;
import static org.junit.Assert.assertEquals;

public class RedisStorageTest {
    RedisStorage redisTest = new RedisStorage();

    // rule {
    @Rule
    public GenericContainer redis = new GenericContainer(DockerImageName.parse("redis:5.0.3-alpine"))
            .withExposedPorts(6379);
    // }

    @Before
    public void setUp() {
        Integer port = redis.getFirstMappedPort();
        String address = "redis://" + redis.getHost() + ":" + port;
        redisTest.init(address);
    }

    @Test
    public void testSimplePutAndGet() throws InterruptedException {
        for (int i = 1; i <= 5; i++) {
            redisTest.userOnline(String.valueOf(i));
        }
        assertEquals("5", redisTest.last());
        redisTest.userPaidUp("1");
        assertEquals("1", redisTest.last());
        redisTest.userPaidUp("3");
        assertEquals("3", redisTest.last());


    }
}
