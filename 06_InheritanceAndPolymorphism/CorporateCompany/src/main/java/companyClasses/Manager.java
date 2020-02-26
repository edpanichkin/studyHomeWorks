package companyClasses;

public class Manager extends CompanyEmployee implements Employee {
    public Manager(){
        super();
        this.setSalary((int) (5000.0 + 2000.0 * Math.random()));
        this.setEmployeeType(EmployeeType.MANAGER);
    }

    @Override
    public int getMonthSalary() {
        int monthSalary = this.getSalary();
            this.setSalary((int)(this.getSalary() + 0.05 * super.getIncome()));
        return monthSalary;
    }
}
