package companyClasses;


import java.util.*;
import java.util.stream.Collectors;


public class Company {
    public List<Employee> employees = new ArrayList<>();

    public Company() {

    }
    public void setBonus() {
        for (Employee staff: employees){
            staff.setBonus(getCompanyIncome());
        }
    }
    public Integer getCompanyIncome() {
        return employees.stream().mapToInt(Employee::getSales).sum();
    }

    public int getWageFund() {
        return employees.stream().mapToInt(Employee::getMonthSalary).sum();
    }
    public void getProfit() {
        setBonus();
        System.out.printf("Доход с продаж: %d, ФОТ: %d \n Прибыль: %d\n\n",
                getCompanyIncome(), getWageFund(), getCompanyIncome() - getWageFund());
    }
    //Блок Приема/Увольнения

    public List<Employee> hire (Employee.Type employeeType) {
        employees.add(Employee.hireType(employeeType));
        return employees;
    }
    public List<Employee> hire(int count, Employee.Type employeeType) {
        if(count > 0) {
            for (int i = 0; i < count; i++) {
                employees.add(Employee.hireType(employeeType));
            }
        }
        else{
            System.out.println("input count error!");
        }
        return employees;
    }
    public List<Employee> fire(int index) {
        employees.remove(index);
        return employees;
    }
    public List<Employee> firePercentageRandom(int percentage) {
        int toFire = (employees.size() * percentage) / 100;
        Collections.shuffle(employees);
        for (int i = 0; i < toFire; i++) {
            employees.remove(0);
        }
        return employees;
    }

    //Блок печати
    public void getTopSalaryStaff(int count) {
        setBonus();
        System.out.printf("\ngetTopSalaryStaff -> Count %s\n", count);
        employees.stream()
                .map(c -> c.getSalary())
                .sorted(Comparator.reverseOrder())
                .limit(count)
                .forEach(System.out::println);
    }
    public void getLowestSalaryStaff(int count) {
        setBonus();
        System.out.printf("\ngetLowestSalaryStaff -> Count %s\n", count);
        employees.stream()
                .map(c -> c.getSalary())
                .sorted()
                .limit(count)
                .forEach(System.out::println);
    }
    public void printCompany() {
        setBonus();
        Map<Employee.Type, Long> count = employees.stream()
                .collect(Collectors.groupingBy(Employee::getEmployeeType, Collectors.counting()));
        Map<Employee.Type, Long> sumSalary = employees.stream()
                .collect(Collectors.groupingBy(Employee::getEmployeeType, Collectors.summingLong(Employee::getMonthSalary)));

        count.forEach((k,v) -> System.out.println(k + " count: " + v + " ФОТ: " + sumSalary.get(k) + "руб."));

        getProfit();
    }
}

