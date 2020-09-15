package skillboxsql;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name ="Subscriptions")
public class Subscription {
    @EmbeddedId
    private IdSubscription id;

    @Column(name = "subscription_date")
    private Date subscriptionDate;

    public Subscription() {
    }

    public Subscription(IdSubscription id, Date subscriptionDate) {
        this.id = id;
        this.subscriptionDate = subscriptionDate;
    }

    public Date getSubscriptionDate() {
        return subscriptionDate;
    }
    public IdSubscription getId() {
        return id;
    }

    @Embeddable
    public static class IdSubscription implements Serializable {
        @ManyToOne
        @JoinColumn(name = "student_id", insertable = false, updatable = false)
        private Student student;
        @ManyToOne
        @JoinColumn(name = "course_id", insertable = false, updatable = false)
        private Course course;

        public IdSubscription() {
        }

        public IdSubscription(Student student, Course course) {
            this.student = student;
            this.course = course;
        }

        public Student getStudent() {
            return student;
        }

        public Course getCourse() {
            return course;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            IdSubscription that = (IdSubscription) o;
            return student == that.student &&
                    course == that.course;
        }

        @Override
        public int hashCode() {
            return Objects.hash(student, course);
        }
    }
}
