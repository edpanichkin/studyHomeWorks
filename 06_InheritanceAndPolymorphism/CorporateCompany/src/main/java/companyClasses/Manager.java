package companyClasses;

public class Manager extends Employee {
    final int OKLAD_FIX = 60000;
    final int OKLAD_RND = 10000;

    public Manager(){
        super();
        this.setSalary((int) (OKLAD_FIX + OKLAD_RND * Math.random()));
        this.setEmployeeType(Employee.Type.MANAGER);
        this.setMonthSalary(this.getSalary() + (int)(0.05 * this.income));
    }
}
