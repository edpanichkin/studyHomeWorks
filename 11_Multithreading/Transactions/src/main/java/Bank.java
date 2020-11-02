import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Bank
{
    private static final String ACC_NUM_FORMAT = "%03d";
    private static final int TIME_TO_FRAUD_CHECK = 10;
    private static final int SUM_TO_FRAUD_CHECK = 50000;
    private AtomicInteger transferCount = new AtomicInteger(0);
    private AtomicInteger transferOKCount = new AtomicInteger(0);
    private HashMap<String, Account> accounts;
    private final Random random = new Random();

    public Bank (){
        this.accounts = new HashMap<>();
    }

    public synchronized boolean isFraud(String fromAccountNum, String toAccountNum, long amount)
        throws InterruptedException
    {
        Thread.sleep(TIME_TO_FRAUD_CHECK);
        return random.nextBoolean();
    }

    public void bankBalance() {
        System.out.println(accounts.values().stream().mapToLong(Account::getMoney).sum());
    }

    public void createAccount (long money) {
        String accNumber = String.format(ACC_NUM_FORMAT, accounts.size() + 1);
        accounts.put(accNumber, new Account(accNumber, money));

    }

    /**
     * TODO: реализовать метод. Метод переводит деньги между счетами.
     * Если сумма транзакции > 50000, то после совершения транзакции,
     * она отправляется на проверку Службе Безопасности – вызывается
     * метод isFraud. Если возвращается true, то делается блокировка
     * счетов (как – на ваше усмотрение)
     */
    public boolean validCheck(String fromAccountNum, String toAccountNum, long amount) {
        boolean validCheck = !fromAccountNum.equals(toAccountNum)
                && !accountFraudCheck(fromAccountNum, toAccountNum)
                && accounts.get(fromAccountNum).getMoney() >= amount;
        return validCheck;
    }
    public void transfer(String fromAccountNum, String toAccountNum, long amount) throws InterruptedException {
        transferCount.incrementAndGet();
        if(validCheck(fromAccountNum,toAccountNum, amount))
        {
            if ((amount > SUM_TO_FRAUD_CHECK)) {
                if (isFraud(fromAccountNum, toAccountNum, amount)) {
                    blockAfterFraudCheck(fromAccountNum, toAccountNum);
                } else {
                    transferOperationApproved(fromAccountNum, toAccountNum, amount);
                }
            }
            transferOperationApproved(fromAccountNum, toAccountNum, amount);
        }
    }

    private boolean accountFraudCheck(String fromAccountNum, String toAccountNum) {
        return  accounts.get(fromAccountNum).isFraud() && accounts.get(toAccountNum).isFraud();
    }
    public void blockAfterFraudCheck(String fromAccountNum, String toAccountNum){
        accounts.get(fromAccountNum).setFraud(true);
        accounts.get(toAccountNum).setFraud(true);
    }

    /**
     * TODO: реализовать метод. Возвращает остаток на счёте.
     */
    public long getAccountBalance(String accountNum)
    {
        return accounts.get(accountNum).getMoney();
    }

    public void printBlockedCount() {
        System.out.println("Blocked accounts: " + accounts.values().stream().filter(Account::isFraud).count()
                + " | Active accounts: " + accounts.values().stream().filter(a -> !a.isFraud()).count());

    }
    private void transferOperationApproved (String fromAccountNum, String toAccountNum, long amount){
        if (fromAccountNum.compareTo(toAccountNum) > 0) {
            synchronized (accounts.get(fromAccountNum)) {
                synchronized (accounts.get(toAccountNum)) {
                    transferOKCount.incrementAndGet();
                    accounts.get(fromAccountNum).transferOff(amount);
                    accounts.get(toAccountNum).transferIn(amount);
                }
            }
        }
        else {
            synchronized (accounts.get(toAccountNum)) {
                synchronized (accounts.get(fromAccountNum)) {
                    transferOKCount.incrementAndGet();
                    accounts.get(fromAccountNum).transferOff(amount);
                    accounts.get(toAccountNum).transferIn(amount);
                }
            }
        }

    }
    public void debugPrint(){
        bankBalance();
        System.out.printf("Transfers %s, OK %s\n",
            transferCount.get(), transferOKCount.get());
}


}
