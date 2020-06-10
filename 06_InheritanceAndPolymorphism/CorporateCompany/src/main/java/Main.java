import companyClasses.*;

public class Main{
    public static void main(String[] args) {

        Company companyIntel = new Company();
        companyIntel.hireAll(180,80,10);
        Company companyAMD = new Company();
        companyAMD.hireAll(0,0,1);

        companyIntel.printCompany();
        companyAMD.printCompany();
//        companyIntel.getTopSalaryStaff(15);
//        companyIntel.getLowestSalaryStaff(30);
        //Увольнение 10%
//        companyIntel.firePercentageRandom(10);

//        companyIntel.getTopSalaryStaff(15);
//        companyIntel.getLowestSalaryStaff(30);
   }
}
