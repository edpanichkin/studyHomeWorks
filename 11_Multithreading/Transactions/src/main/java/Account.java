public class Account
{
    public Account(String accNumber, long money) {
        this.money = money;
        this.accNumber = accNumber;
    }

    private long money;
    private String accNumber;
    private boolean isFraud = false;

    public boolean isFraud() {
        return isFraud;
    }

    public void setFraud(boolean fraud) {
        isFraud = fraud;
    }

    public synchronized long getMoney() {
        return money;
    }
    public synchronized void transferOff(long amount) {
        money -= amount;
    }
    public synchronized void transferIn(long amount) {
        money += amount;
    }

    public synchronized void setMoney(long money) {
        this.money = money;
    }

    public String getAccNumber() {
        return accNumber;
    }

    public void setAccNumber(String accNumber) {
        this.accNumber = accNumber;
    }


}
