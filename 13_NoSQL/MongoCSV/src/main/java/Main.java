import au.com.bytecode.opencsv.CSVReader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        MongoClient mongoClient = new MongoClient( "127.0.0.1" , 27017 );
        MongoDatabase database = mongoClient.getDatabase("local");
        MongoCollection<Document> collection = database.getCollection("TestSkillDemo");
        collection.drop();

        String csvFile = "src\\main\\resources\\mongo.csv";
        List<Student> students = ParseProductCsv(csvFile);
        List<Document> studentDocuments = new ArrayList<>();
        students.forEach(s ->
            studentDocuments.add(new Document()
                    .append("Name", s.getName())
                    .append("Age", s.getAge())
                    .append("CourseList", s.getCourseList())));
        collection.insertMany(studentDocuments);

        ObjectMapper mapper = new ObjectMapper();
        BasicDBObject query = new BasicDBObject();
        query.put("Age", new BasicDBObject("$gt", "40"));
        int sizeAge40 = 0;
        for (Document doc: collection.find(query)) {
            sizeAge40 = doc.size();
        }

        for (Document doc: collection.find().sort(new BasicDBObject("Age",1)).limit(1))
        {
            JsonStudent student = mapper.readValue(doc.toJson(), JsonStudent.class);
            System.out.printf("1. Самый молодой студент: %s, его возраст: %s \n", student.getName(), student.getAge());
        }

        System.out.println("2. Кол-во документов в коллекции: " + collection.countDocuments());
        System.out.println("3. " +query + " count: " + sizeAge40);

        for (Document doc: collection.find().sort(new BasicDBObject("Age",-1)).limit(1))
        {
            JsonStudent student = mapper.readValue(doc.toJson(), JsonStudent.class);
            System.out.printf("4. Список курсов самого старшего студента: %s, его возраст: %s \n",
                    student.getCourseList(), student.getAge());
        }
    }

    private static List<Student> ParseProductCsv(String csvFile){
        List<Student> students = new ArrayList<>();
            try {
                CSVReader reader = new CSVReader(new FileReader(csvFile));
                String[] line;
                while ((line = reader.readNext()) != null) {
                    students.add(new Student(line[0], line[1], courseList(line[2])));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        return students;
    }

    private static List<String> courseList (String s) {
        return new ArrayList<>(Arrays.asList(s.split(",")));
    }

}
