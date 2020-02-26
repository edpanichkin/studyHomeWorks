import companyClasses.*;

import java.util.ArrayList;

import static companyClasses.CompanyEmployee.*;

public class Main{
    public static void main(String[] args) {

        ArrayList<CompanyEmployee> companyIBM = new ArrayList<CompanyEmployee>();
        hireAll(companyIBM, 20, 10, 5);
        //printCompany(companyIBM);
        hire(companyIBM,EmployeeType.OPERATOR);
        fire(companyIBM, companyIBM.size()-1);

        getTopSalaryStaff(companyIBM, 15);
        getLowestSalaryStaff(companyIBM, 30);
        //Fire 50%
        firePercentageRandom(companyIBM, 50);

        getTopSalaryStaff(companyIBM, 15);
        getLowestSalaryStaff(companyIBM, 30);

        //printCompany(companyIBM);
        //getLowestSalaryStaff(companyIBM, 10);
        //getTopSalaryStaff(companyIBM, 10);
        companyIBM.stream().forEach(c -> c.getMonthSalary());
        getTopSalaryStaff(companyIBM, 15);
        getLowestSalaryStaff(companyIBM, 30);
        //printCompany(companyIBM);
        //System.out.println(getRevenue(companyIBM, 10000000));
    }



}
