import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

// import com.fasterxml.jackson.databind.ObjectMapper; // version 2.11.1
// import com.fasterxml.jackson.annotation.JsonProperty; // version 2.11.1
/* ObjectMapper om = new ObjectMapper();
Root root = om.readValue(myJsonString), Root.class); */
class Id{
    @JsonProperty("$oid")
    public String oid;
}

public class JsonStudent {
    public Id _id;

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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public List<String> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<String> courseList) {
        this.courseList = courseList;
    }

    @JsonProperty("Name")
    public String name;
    @JsonProperty("Age")
    public String age;
    @JsonProperty("CourseList")
    public List<String> courseList;
}
