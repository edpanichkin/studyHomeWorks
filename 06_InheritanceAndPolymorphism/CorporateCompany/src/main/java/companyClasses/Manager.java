package companyClasses;

public class Manager extends Employee {
    final int OKLAD_FIX = 60000;
    final int OKLAD_RND = 10000;
    final int SALES = 120000;
    final int PLAN = 170000;
    final double BONUS = 0.05;

    public Manager(){
        super();
        this.setEmployeeType(Employee.Type.MANAGER);
        this.setSalary((int) (OKLAD_FIX + OKLAD_RND * Math.random()));
        this.setSales((int) (SALES + SALES * Math.random()));

        if (this.getSales() > PLAN) {
            this.setMonthSalary(this.getSalary() + (int) (BONUS * this.getSales()));
        } else {
            this.setMonthSalary(this.getSalary());
        }
    }
}
