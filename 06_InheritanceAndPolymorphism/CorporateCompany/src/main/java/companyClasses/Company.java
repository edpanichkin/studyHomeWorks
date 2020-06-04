package companyClasses;

import java.util.*;

public class Company {

    private static Integer companyIncome;
    public List<Employee> employees = new ArrayList<Employee>();

    public Company(Integer companyIncome) {
        Company.companyIncome = companyIncome;
    }
    public static Integer getCompanyIncome() {
        return companyIncome;
    }

    //Блок Приема/Увольнения
    public List<Employee> hire(Employee.Type employeeType) {
        switch (employeeType) {
            case MANAGER:
                employees.add(new Manager());
                break;
            case OPERATOR:
                employees.add(new Operator());
                break;
            case TOPMANAGER:
                employees.add(new TopManager());
                break;
        }
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
        employees.stream()
                .forEach(f -> System.out.println(f.getMonthSalary() + " руб / Должность: " + f.getEmployeeType()));
        System.out.print("ФОТ: ");
        employees.stream()
                .map(f -> f.getSalary()).reduce((a, b) -> a + b).ifPresent(System.out::println);
        System.out.println("_______________________________________\n");
    }
}

