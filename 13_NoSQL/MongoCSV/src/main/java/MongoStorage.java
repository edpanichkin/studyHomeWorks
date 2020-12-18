import au.com.bytecode.opencsv.CSVReader;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import static com.mongodb.client.model.Sorts.*;
import static com.mongodb.client.model.Filters.*;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MongoStorage {
    private MongoCollection<Document> collection;
    private final String csvFile = "src\\main\\resources\\mongo.csv";

    public void init(String host, int port) {
        MongoClient mongoClient = new MongoClient(host, port);
        MongoDatabase database = mongoClient.getDatabase("local");
        collection = database.getCollection("TestSkillDemo");
        collection.drop();
    }

    public void fillDBStudents() {
        fillDB(studentsToDoc(parseStudentsCSV(csvFile)));
    }

    void fillDB(List<Document> data) {
        for (Document doc : data) {
            collection.insertOne(doc);
        }
    }

    public int findAgeGtValueCount(int value) {
        int count = 0;
        for (Document doc: collection.find(gt("Age", value))) {
            count++;
        }
        System.out.println("Кол-во в коллекции старше " + value + ": " + count);
        return count;
    }

    public List<String> findCourseListByAgeDesc() throws JsonProcessingException {
        List<String> courseList = new ArrayList<>();
        for (Document doc: collection.find().sort(descending("Age")).limit(1))
        {
            JsonStudent student = new ObjectMapper().readValue(doc.toJson(), JsonStudent.class);
            courseList = student.getCourseList();
            System.out.printf("Список курсов самого старшего студента: %s, его возраст: %s \n",
                    courseList, student.getAge());
        }
        return courseList;
    }

    public String findNameByAgeAsc() throws JsonProcessingException {
        String name = null;
        for (Document doc: collection.find().sort(ascending("Age")).limit(1))
        {
            JsonStudent student = new ObjectMapper().readValue(doc.toJson(), JsonStudent.class);
            name = student.getName();
            System.out.printf("Самый молодой студент: %s, его возраст: %s \n", student.getName(), student.getAge());
        }
        return name;
    }

    public int collectionSize () {
        int size = (int) collection.countDocuments();
        System.out.println("Кол-во документов в коллекции: " + size);
        return size;
    }

    private List<Student> parseStudentsCSV(String csvFile){
        List<Student> students = new ArrayList<>();
        try {
            CSVReader reader = new CSVReader(new FileReader(csvFile));
            String[] line;
            while ((line = reader.readNext()) != null) {
                students.add(new Student(line[0], Integer.parseInt(line[1]), courseList(line[2])));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return students;
    }

    private List<String> courseList (String s) {
        return new ArrayList<>(Arrays.asList(s.split(",")));
    }
    public List<Document> studentsToDoc (List<Student> students) {
        List<Document> stDocs = new ArrayList<>();
        students.forEach(s ->
                stDocs.add(new Document()
                        .append("Name", s.getName())
                        .append("Age", s.getAge())
                        .append("CourseList", s.getCourseList())));
        return stDocs;
    }
}

