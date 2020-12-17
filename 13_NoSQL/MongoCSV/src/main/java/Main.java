
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        MongoStorage collection = new MongoStorage();
        collection.init();
        collection.fillDBStudents();

        collection.collectionSize();
        collection.findAgeGtValueCount(40);
        collection.findNameByAgeAsc();
        collection.findCourseListByAgeDesc();
    }
}
