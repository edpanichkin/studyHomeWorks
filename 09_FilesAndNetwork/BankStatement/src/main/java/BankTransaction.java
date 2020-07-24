
public class BankTransaction {
    private String type;
    private String accountNum;
    private String currency;
    private String dateOperation;
    private String reference;
    private String operationInfo;
    private Double income;
    private Double expense;
    private String mccCode;

    public String getMccCode() {
        return mccCode;
    }
    public String getType() {
        return type;
    }
    public String getAccountNum() {
        return accountNum;
    }
    public String getCurrency() {
        return currency;
    }
    public String getDateOperation() {
        return dateOperation;
    }
    public String getReference() {
        return reference;
    }
    public String getOperationInfo() {
        return operationInfo;
    }
    public Double getIncome() {
        return income;
    }
    public Double getExpense() {
        return expense;
    }

    public BankTransaction(String type, String accountNum, String currency,
                           String dateOperation, String reference, String operationInfo,
                           Double income, Double expense, String mccCode){
        this.type = type;
        this.accountNum = accountNum;
        this.currency = currency;
        this.dateOperation = dateOperation;
        this.reference = reference;
        this.operationInfo = operationInfo;
        this.income = income;
        this.expense = expense;
        this.mccCode = mccCode;
    }

    public void setMccCode(String mccCode) {
        this.mccCode = mccCode;
    }
}
