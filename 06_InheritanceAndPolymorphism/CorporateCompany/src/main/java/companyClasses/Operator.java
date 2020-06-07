package companyClasses;

public class Operator extends Employee {
    final int OKLAD_FIX = 25000;
    final int OKLAD_RND = 5000;

    public Operator(){
        super();
        this.setSalary((int) (OKLAD_FIX + OKLAD_RND * Math.random()));
        this.setEmployeeType(Employee.Type.OPERATOR);
        this.setMonthSalary(this.getSalary());
    }
}
