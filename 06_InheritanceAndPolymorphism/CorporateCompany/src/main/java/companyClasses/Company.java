package companyClasses;

import java.util.*;
import static companyClasses.Employee.Type.*;

public class Company {
    public List<Employee> employees = new ArrayList<>();

    public Company() {
    }
    public Integer getCompanyIncome() {
        return employees.stream().mapToInt(e -> e.getSales()).sum();
    }
    public int getWageFund() {
        return employees.stream().mapToInt(e -> e.getMonthSalary()).sum();
    }
    public void getProfit() {
        System.out.printf("Доход с продаж: %d, ФОТ: %d \n Прибыль: %d\n\n",
                getCompanyIncome(), getWageFund(), getCompanyIncome() - getWageFund());
    }
    //Блок Приема/Увольнения


    public List<Employee> hire (Employee.Type employeeType) {
        employees.add(Employee.hireType(employeeType));
        return employees;
    }

    public List<Employee> hireAll(int operator, int manager, int topManager) {
        for (int i = 0; i < operator; i++) {
            employees.add(new Operator());
        }
        for (int i = 0; i < manager; i++) {
            employees.add(new Manager());
        }
        for (int i = 0; i < topManager; i++) {
            employees.add(new TopManager());
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
        System.out.printf("\ngetTopSalaryStaff -> Count %s\n", count);
        employees.stream()
                .map(c -> c.getSalary())
                .sorted(Comparator.reverseOrder())
                .limit(count)
                .forEach(System.out::println);
    }
    public void getLowestSalaryStaff(int count) {
        System.out.printf("\ngetLowestSalaryStaff -> Count %s\n", count);
        employees.stream()
                .map(c -> c.getSalary())
                .sorted()
                .limit(count)
                .forEach(System.out::println);
    }
    public void printCompany() {
        System.out.printf("TOPMANAGER Count: %d, ФОТ %d руб. \n",
                employees.stream()
                        .filter(e -> TOPMANAGER.equals(e.getEmployeeType())).count(),
                employees.stream()
                        .filter(e -> TOPMANAGER.equals(e.getEmployeeType())).mapToInt(e -> e.getMonthSalary()).sum());
        System.out.printf("MANAGER Count: %d, ФОТ %d руб. \n",
                employees.stream()
                        .filter(e -> MANAGER.equals(e.getEmployeeType())).count(),
                employees.stream()
                        .filter(e -> MANAGER.equals(e.getEmployeeType())).mapToInt(e -> e.getMonthSalary()).sum());
        System.out.printf("OPERATOR Count: %d, ФОТ %d руб. \n",
                employees.stream()
                        .filter(e -> OPERATOR.equals(e.getEmployeeType())).count(),
                employees.stream()
                        .filter(e -> OPERATOR.equals(e.getEmployeeType())).mapToInt(e -> e.getMonthSalary()).sum());
        getProfit();

    }
}

