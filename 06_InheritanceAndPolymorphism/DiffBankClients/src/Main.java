import bankClientsClasses.*;

public class Main {
    public static void main(String[] args) {
        LegalClient legalClient = new LegalClient("ООО РОМАШКА", 1000);
        IndividEntrepClient individEntrepClient = new IndividEntrepClient("ИП РОЗА", 0);
        PhysicalClient physicalClient = new PhysicalClient("Петя", 20);
        //physicalClient.getCashOut(100);
        //legalClient.printCashCount();
        //legalClient.putCashCount(100);
        //legalClient.printCashCount();
        //legalClient.getCashOut(100);
        //individEntrepClient.putCashCount(1000);
        //individEntrepClient.putCashCount(2000);
    }
}
