import companyClasses.*;

public class Main{
    public static void main(String[] args) {

        Company companyIntel = new Company();
        companyIntel.hireAll(180,80,10);

        companyIntel.printCompany();
//        companyIntel.getTopSalaryStaff(15);
//        companyIntel.getLowestSalaryStaff(30);
        //Увольнение 50%
        companyIntel.firePercentageRandom(10);

//        companyIntel.getTopSalaryStaff(15);
//        companyIntel.getLowestSalaryStaff(30);
        companyIntel.printCompany();
   }
}
