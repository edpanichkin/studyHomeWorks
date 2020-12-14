import java.util.Collections;
import java.util.List;

public class Student {
    private String name;
    private String age;
    private List<String> courseList;

    public Student(String name, String age, List<String> courseList) {
        this.name = name;
        this.age = age;
        this.courseList = courseList;
    }

    public Student() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public List<String> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<String>courseList) {
        this.courseList = courseList;
    }
}
