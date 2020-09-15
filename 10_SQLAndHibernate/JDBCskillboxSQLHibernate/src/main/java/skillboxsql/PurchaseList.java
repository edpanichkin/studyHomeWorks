package skillboxsql;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "purchaselist")
public class PurchaseList {

    @EmbeddedId
    private PurchaseListId plId;

    private int price;
    @Column(name = "subscription_date")
    private Date subscriptionDate;

    public PurchaseListId getPlId() {
        return plId;
    }

    @Embeddable
    public static class PurchaseListId implements Serializable {

        @ManyToOne
        @JoinColumn(name = "student_name", referencedColumnName="name")
        protected Student student;
        @ManyToOne
        @JoinColumn(name = "course_name", referencedColumnName="name")
        protected Course course;

        public PurchaseListId() {
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
            PurchaseListId plId = (PurchaseListId) o;
            return student.equals(plId.student) &&
                    course.equals(plId.course);
        }

        @Override
        public int hashCode() {
            return Objects.hash(student, course);
        }
    }
}



