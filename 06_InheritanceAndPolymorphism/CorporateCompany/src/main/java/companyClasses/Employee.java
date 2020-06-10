package companyClasses;

public class Employee extends Company{

    protected Integer salary;
    protected Integer monthSalary;
    protected Integer sales = 0;
    protected Employee.Type employeeType;
    protected Integer income = super.getCompanyIncome();

    public Employee() {
    }

    protected enum Type {
        TOPMANAGER,
        OPERATOR,
        MANAGER;
    }

    public Integer getMonthSalary() {
        return this.monthSalary;
    }
    public Integer getSalary() {
        return salary;
    }
    public Integer getSales() {
        return sales;
    }
    public Employee.Type getEmployeeType() {
        return employeeType;
    }

    public void setMonthSalary(Integer monthSalary) {
        this.monthSalary = monthSalary;
    }
    public void setSalary(Integer salary) {
        this.salary = salary;
    }
    public void setSales(Integer sales) {
        this.sales = sales;
    }
    public void setEmployeeType(Employee.Type employeeType) {
        this.employeeType = employeeType;
    }
}
