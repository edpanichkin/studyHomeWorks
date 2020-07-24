
public class BankTransaction {
    String type;
    String accountNum;
    String currency;
    String dateOperation;
    String reference;
    String operationInfo;
    Double income;
    Double expense;

    public String getMccCode() {
        return mccCode;
    }

    public void setMccCode(String mccCode) {
        this.mccCode = mccCode;
    }

    String mccCode;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(String accountNum) {
        this.accountNum = accountNum;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDateOperation() {
        return dateOperation;
    }

    public void setDateOperation(String dateOperation) {
        this.dateOperation = dateOperation;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getOperationInfo() {
        return operationInfo;
    }

    public void setOperationInfo(String operationInfo) {
        this.operationInfo = operationInfo;
    }

    public Double getIncome() {
        return income;
    }

    public void setIncome(Double income) {
        this.income = income;
    }

    public Double getExpense() {
        return expense;
    }

    public void setExpense(Double expense) {
        this.expense = expense;
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
}
