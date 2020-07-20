package companyClasses;

public class Employee {

    protected Integer salary;
    protected Integer monthSalary;
    protected Integer sales = 0;
    protected Employee.Type employeeType;

    public Employee() {
    }

    public void setBonus(Integer companyIncome) {
    }

    public enum Type {
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

    public static Employee hireType(Employee.Type employeeType) {
        Employee e = new Employee();
        switch (employeeType) {
            case MANAGER:
                e = new Manager();
                break;
            case OPERATOR:
                e = new Operator();
                break;
            case TOPMANAGER:
                e = new TopManager();
                break;
        }
        return e;
    }
}
