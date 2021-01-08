import org.bson.Document;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class RetailGroupTest {
    RetailGroup retailTest = new RetailGroup();
    @Rule
    public GenericContainer mongoDBContainer = new GenericContainer(DockerImageName.parse("mongo:4.0.10"))
            .withExposedPorts(27017);

    @Before
    public void setUp() {
        retailTest.init(mongoDBContainer.getHost(), mongoDBContainer.getMappedPort(27017));
        retailTest.createNewStore("s1");
        retailTest.createNewStore("s2");
        retailTest.createNewStore("s3");

        retailTest.createNewStore("s4");
        retailTest.putProductOnShelf("pr1", "s4");

        for (int i = 0; i < 50; i++) {
            retailTest.addNewProduct("pr" + i, i);
            retailTest.putProductOnShelf("pr" + i, "s1");
        }
        for (int i = 98; i < 200; i++) {
            retailTest.addNewProduct("pr" + i, i);
            retailTest.putProductOnShelf("pr" + i, "s2");
        }
        for (int i = 200; i < 300; i++) {
            retailTest.addNewProduct("pr" + i, i);
            retailTest.putProductOnShelf("pr" + i, "s3");
        }
    }

    @Test
    public void testRetail() {

        assertEquals(retailTest.getProduct("pr99").get("name"), "pr99");
        assertEquals(retailTest.getProduct("pr99").get("price"), 99.0);
        assertEquals(retailTest.getStats().size(), 4);
        assertEquals(retailTest.getStats().get(0).get("max"), 99.0);
        assertEquals(retailTest.getStats().get(1).get("max"), 199.0);
        assertEquals(retailTest.getStats().get(2).get("max"), 199.0);

        assertEquals(retailTest.getStats().get(0).get("min"), 0.0);
        assertEquals(retailTest.getStats().get(1).get("min"), 100.0);
        assertEquals(retailTest.getStats().get(2).get("min"), 200.0);

    }
}
