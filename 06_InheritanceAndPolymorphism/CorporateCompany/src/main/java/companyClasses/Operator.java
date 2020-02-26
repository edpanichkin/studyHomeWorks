package companyClasses;

import java.lang.reflect.Type;

public class Operator extends CompanyEmployee implements Employee {
    public Operator(){
        super();
        this.setSalary((int) (1000.0 + 1000.0 * Math.random()));
        this.setEmployeeType(EmployeeType.OPERATOR);
    }
    @Override
    public int getMonthSalary() {
        return this.getSalary();
    }
}
