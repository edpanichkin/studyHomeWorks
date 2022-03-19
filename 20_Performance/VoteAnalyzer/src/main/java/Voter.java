import java.util.Date;
import java.util.Objects;

public class Voter
{
    private String name;
    private Date birthDay;

    public Voter(String name, Date birthDay)
    {
        this.name = name;
        this.birthDay = birthDay;
    }

    public Voter() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Voter voter = (Voter) o;
        return name.equals(voter.name) && birthDay.equals(voter.birthDay);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, birthDay);
    }

    public String toString()
    {
        return name + " (" + birthDay + ")";
    }

    public String getName()
    {
        return name;
    }

    public Date getBirthDay()
    {
        return birthDay;
    }
}
