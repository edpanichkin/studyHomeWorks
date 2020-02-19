import java.time.LocalDate;

public class DebetBankAccount extends BankAccount {
    LocalDate dateLastIncome;

    public DebetBankAccount(float startCash, String name) {
        super(startCash, name);
    }
    @Override
    public void putCashCount(float cash) {
        super.putCashCount(cash);
        //dateLastIncome = LocalDate.of(2020,01,18);
        dateLastIncome = LocalDate.now();
    }
    public boolean isMonthPassed () {
        return  LocalDate.now().isAfter(dateLastIncome.plusMonths(1));
    }
    @Override
    public boolean getCashOut(float cash) {
        if (isMonthPassed()) {
            return super.getCashOut(cash);
        }
        else {
            System.out.println("\nРано снимать, деньги были внесены в: "
                    + dateLastIncome);
            return false;
        }
    }
}