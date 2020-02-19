package bankClientsClasses;

public class IndividEntrepClient extends Client{
    private static final int PERCENTAGE = 1;
    private float valueCashChangeCommission = 1000;
    private float valueDecreaseCommission = (float) 0.5;

    public IndividEntrepClient(String name, float startCash) {
        super(name, startCash);
    }

    @Override
    public void putCashCount(float cash) {
        float commission = (cash * PERCENTAGE) / 100;
        System.out.println(commission);
        if (cash < valueCashChangeCommission) {
            super.putCashCount(cash - commission);
        }
        else {
            super.putCashCount(cash - commission * valueDecreaseCommission);
        }
    }
}
