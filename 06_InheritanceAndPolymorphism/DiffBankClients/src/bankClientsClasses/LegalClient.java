package bankClientsClasses;

public class LegalClient extends Client {
    private static final int PERCENTAGE = 1;
    public LegalClient(String name, float startCash) {
        super(name, startCash);
    }

    @Override
    public void getCashOut(float cash) {
        float commission = (cash * PERCENTAGE) / 100;
        super.getCashOut(cash + commission);
    }
}
