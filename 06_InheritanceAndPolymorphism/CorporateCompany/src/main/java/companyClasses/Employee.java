package companyClasses;

public class Employee{

    protected Integer salary;
    protected Integer monthSalary;
    protected Employee.Type employeeType;
    protected Integer income = Company.getCompanyIncome();

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
    public Employee.Type getEmployeeType() {
        return employeeType;
    }

    public void setMonthSalary(Integer monthSalary) {
        this.monthSalary = monthSalary;
    }
    public void setSalary(Integer salary) {
        this.salary = salary;
    }
    public void setEmployeeType(Employee.Type employeeType) {
        this.employeeType = employeeType;
    }
}
