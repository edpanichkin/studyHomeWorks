package skillboxsql;

import javax.persistence.*;
import java.io.Serializable;

import java.util.Objects;

    @Entity
    @Table(name = "LinkedPurchaseList")
    public class LinkedPurchaseList {
        @EmbeddedId
        private KeyId keyId;

        public LinkedPurchaseList(int studentId, int courseId) {
            this.keyId = new KeyId(studentId, courseId);
        }

        @Embeddable
        public static class KeyId implements Serializable {
            @Column(name = "student_id")
            int studentId;

            @Column(name = "course_id")
            int courseId;

            public KeyId(int studentId, int courseId) {
                this.studentId = studentId;
                this.courseId = courseId;
            }


            @Override
            public boolean equals(Object o) {
                if (this == o) {
                    return true;
                }
                if (o == null || getClass() != o.getClass()) {
                    return false;
                }
                KeyId keyId = (KeyId) o;
                return studentId == keyId.studentId &&
                        courseId == keyId.courseId;
            }

            @Override
            public int hashCode() {
                return Objects.hash(studentId, courseId);
            }
        }
    }


