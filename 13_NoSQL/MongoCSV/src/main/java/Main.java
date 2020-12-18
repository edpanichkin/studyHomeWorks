
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        MongoStorage collection = new MongoStorage();
        collection.init("127.0.0.1", 27017);
        collection.fillDBStudents();

        collection.collectionSize();
        collection.findAgeGtValueCount(40);
        collection.findNameByAgeAsc();
        collection.findCourseListByAgeDesc();
    }
}
