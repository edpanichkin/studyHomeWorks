public class CreditBankAccount extends BankAccount {
    public static final int PERCENTAGE = 1;

    public CreditBankAccount(float startCash, String name) {
        super(startCash, name);
    }

    @Override
    public boolean getCashOut(float cash) {
        float commission = (cash * PERCENTAGE) / 100;
        System.out.printf("-- Комисия %.2f  / ", commission);
        return super.getCashOut(cash + commission);

    }
}