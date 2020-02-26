package companyClasses;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;

public class CompanyEmployee implements Employee{

    private int income = 10000000;
    private Integer salary;
    private EmployeeType employeeType;
    private Float bonus;

    public CompanyEmployee() {
    }
    @Override
    public int getMonthSalary() {
        return this.getSalary();
    }
    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public EmployeeType getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(EmployeeType employeeType) {
        this.employeeType = employeeType;
    }

    public static ArrayList<CompanyEmployee> hire(ArrayList<CompanyEmployee> company, EmployeeType employeeType) {
        switch (employeeType){
            case MANAGER:
                company.add(new Manager());
                break;
            case OPERATOR:
                company.add(new Operator());
                break;
            case TOPMANAGER:
                company.add(new TopManager());
                break;
        }
        return company;
    }

    public static void getTopSalaryStaff (ArrayList<CompanyEmployee> company, int count)
    {
        System.out.printf("\ngetTopSalaryStaff -> Count %s\n", count);
        company.stream()
                .map(c -> c.getSalary())
                .sorted(Comparator.reverseOrder())
                .limit(count)
                .forEach(System.out::println);
    }
    public static void getLowestSalaryStaff (ArrayList<CompanyEmployee> company, int count)
    {
        System.out.printf("\ngetLowestSalaryStaff -> Count %s\n", count);
        company.stream()
                .map(c -> c.getSalary())
                .sorted()
                .limit(count)
                .forEach(System.out::println);
    }

    public static ArrayList<CompanyEmployee> fire(ArrayList<CompanyEmployee> company, int index){
        company.remove(index);
        return company;
    }
    public static void printCompany(ArrayList<CompanyEmployee> company){
        company.stream()
                .forEach(f -> System.out.println(f.getSalary() + " руб / Должность: " + f.getEmployeeType()));
        company.stream()
                .map(f -> f.getSalary()).reduce((a,b) -> a + b).ifPresent(System.out::println);
        System.out.println("_______________________________________\n");
    }
    public static ArrayList<CompanyEmployee> firePercentageRandom(ArrayList<CompanyEmployee> company, int percentage){
        int toFire = (company.size() * percentage) / 100;
        Collections.shuffle(company);
        for (int i = 0; i < toFire; i++) {
            company.remove(0);
        }
        return company;
    }

    public static ArrayList<CompanyEmployee> hireAll(ArrayList<CompanyEmployee> company,
                                                     int operator, int manager, int topManager)
    {
        for(int i = 0; i < operator; i++){
            company.add(new Operator());
        }
        for(int i = 0; i < manager; i++){
            company.add(new Manager());
        }
        for(int i =0; i < topManager; i++){
            company.add(new TopManager());
        }
        return company;
    }
    public static int getRevenue(ArrayList<CompanyEmployee> company, int income){
        Optional<Integer> revenue = company.stream().map(f -> f.getSalary())
                .reduce((a,b) -> a + b);
        return income - revenue.get();
    }
    public int getIncome() {
        return income;
    }
    public void setIncome(int income) {
        this.income = income;
    }
}
