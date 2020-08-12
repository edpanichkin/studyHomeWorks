package skillboxsql;

import javax.persistence.*;
import java.util.Date;
@Entity
@Table(name ="Subscriptions")
public class Subscription {
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;
    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;
    @Column(name = "subscription_date")
    private Date subscriptionDate;
}
