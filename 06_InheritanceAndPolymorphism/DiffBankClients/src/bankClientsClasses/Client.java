package bankClientsClasses;

public abstract class Client {

    private float cashCount;
    private String nameAccount;

    public Client (String name, float startCash) {
        this.nameAccount = name;
        this.cashCount = startCash;
    }
    public void printCashCount() {
        System.out.printf("%s счет, хранится %.2f%n", nameAccount, cashCount);
    }

    public void putCashCount(float cash) {
        System.out.printf("%S счет, внесли средства %.2f, теперь остаток %.2f%n",
                nameAccount, cash, (this.cashCount += cash));
    }
    public void getCashOut(float cash) {
        if (isBalancePositive(cash)) {
            cashCount -= cash;
            System.out.printf("Выведено %.2f, Остаток %.2f %n", cash, cashCount);
        }
        else {
            System.out.printf("%s !! Недостаточно средств для вывода, попытка снять %.2f, на счету %.2f%n",
                    nameAccount, cash, cashCount);
        }
    }

    public boolean isBalancePositive(float cash) {
        return cashCount >= cash;
    }

}
