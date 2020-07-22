public class BankTransaction {
    String type;
    String accountNum;
    String currency;
    String dateOperation;
    String reference;
    String operationInfo;
    String income;
    String expense;

    public BankTransaction(String type, String accountNum, String currency,
                           String dateOperation, String reference, String operationInfo,
                           String income, String expense){
        this.type = type;
        this.accountNum = accountNum;
        this.currency = currency;
        this.dateOperation = dateOperation;
        this.reference = reference;
        this.operationInfo = operationInfo;
        this.income = income;
        this.expense = expense;
    }
}
