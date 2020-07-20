import companyClasses.*;

public class Main{
    public static void main(String[] args) {

        Company companyIntel = new Company();

        companyIntel.hire(1, Employee.Type.TOPMANAGER);
        companyIntel.printCompany();

        companyIntel.hire(400, Employee.Type.OPERATOR);
        companyIntel.hire(400, Employee.Type.MANAGER);
        companyIntel.printCompany();


        Company companyAMD = new Company();
        companyAMD.hire(1800, Employee.Type.OPERATOR);
        companyAMD.hire(800, Employee.Type.MANAGER);
        companyAMD.hire(100, Employee.Type.TOPMANAGER);

        companyIntel.printCompany();


//        companyIntel.getTopSalaryStaff(15);
//        companyIntel.getLowestSalaryStaff(30);
        //Увольнение 10%
//        companyIntel.firePercentageRandom(10);

//        companyIntel.getTopSalaryStaff(15);
//        companyIntel.getLowestSalaryStaff(30);
   }
}
