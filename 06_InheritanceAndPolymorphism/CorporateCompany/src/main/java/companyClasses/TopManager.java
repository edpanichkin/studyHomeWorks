package companyClasses;

public class TopManager extends CompanyEmployee implements Employee {
    public TopManager() {
    super();
    this.setSalary((int) (10000.0 + 5000.0 * Math.random()));
    this.setEmployeeType(EmployeeType.TOPMANAGER);
}
    @Override
    public int getMonthSalary() {
        int monthSalary = this.getSalary();
        if (super.getIncome() >= 10000000){
            this.setSalary(this.getSalary()+ (int)(1.5 * this.getSalary()));
            monthSalary = this.getSalary();
        }
        return monthSalary;
    }
}
