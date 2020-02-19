
public class Main {
    public static void main(String[] args) {
        CreditBankAccount creditCard = new CreditBankAccount(0, "Кредитный");
        DebetBankAccount debetCard = new DebetBankAccount(0, "Расчетный");
        debetCard.putCashCount(100);
        creditCard.putCashCount(100);
        creditCard.transferTo(debetCard, 99);
        debetCard.printCashCount();
        debetCard.transferTo(creditCard, 22);
        creditCard.printCashCount();
    }
}