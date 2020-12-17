import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

class Id{
    @JsonProperty("$oid")
    public String oid;
}

public class JsonStudent {
    public Id _id;

    @JsonProperty("Name")
    public String name;
    @JsonProperty("Age")
    public int age;
    @JsonProperty("CourseList")
    public List<String> courseList;

    public Id get_id() {
        return _id;
    }

    public void set_id(Id _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<String> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<String> courseList) {
        this.courseList = courseList;
    }


}
