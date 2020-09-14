package skillboxsql;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "Purchaselist")
public class PurchaseList {
    @EmbeddedId
    private PurchaseListId plId;
    private int price;
    @Column(name = "subscription_date")
    private Date subscriptionDate;

    @Embeddable
    private static class PurchaseListId implements Serializable {
        @ManyToOne
        @JoinColumn(name = "student_name", referencedColumnName="name")
        private Student student;
        @ManyToOne
        @JoinColumn(name = "course_name", referencedColumnName="name")
        private Course course;

        public PurchaseListId() {
        }

        public PurchaseListId(Student student, Course course) {
            this.student = student;
            this.course = course;
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



