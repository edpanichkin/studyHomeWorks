import com.fasterxml.jackson.core.JsonProcessingException;
import org.bson.Document;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.containers.GenericContainer;

import java.util.ArrayList;
import java.util.List;

public class MongoStorageTest {
    MongoStorage mongoTest = new MongoStorage();

    @Rule
    public GenericContainer mongoDBContainer = new GenericContainer(DockerImageName.parse("mongo:4.0.10"))
            .withExposedPorts(27017);

    @Before
    public void setUp() {
        mongoTest.init(mongoDBContainer.getHost(),mongoDBContainer.getMappedPort(27017));
    }

    @Test
    public void testStudent() throws JsonProcessingException {
        List<Document> data = new ArrayList<>();
        List<Student> studentList = new ArrayList<>();
        List<String> courseList = new ArrayList<>();

        studentList.add(new Student("1", 1, new ArrayList<>()));
        studentList.add(new Student("2", 2, new ArrayList<>()));
        courseList.add("Java");
        courseList.add("Python");
        studentList.add(new Student("3", 3, new ArrayList<>()));
        studentList.add(new Student("4", 4, courseList));

        mongoTest.fillDB(mongoTest.studentsToDoc(studentList));

        assertEquals(mongoTest.collectionSize(), 4);
        System.out.println("...next test...");
        assertEquals(mongoTest.findAgeGtValueCount(2),2);
        System.out.println("...next test...");
        assertEquals(mongoTest.findNameByAgeAsc(),"1");
        System.out.println("...next test...");
        assertEquals(mongoTest.findCourseListByAgeDesc(), courseList);
//
//        mongoTest.fillDB();
//        mongoTest.findNameByAgeAsc();

    }
}
