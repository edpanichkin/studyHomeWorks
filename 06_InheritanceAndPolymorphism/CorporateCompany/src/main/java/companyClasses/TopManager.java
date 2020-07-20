package companyClasses;

public class TopManager extends Employee {
    final int OKLAD_FIX = 100000;
    final int OKLAD_RND = 30000;
    final int WHEN_BONUS = 10000000;
    final double BONUS_COEFF = 1.5;

    public TopManager() {
        super();
        this.setSalary((int) (OKLAD_FIX + OKLAD_RND * Math.random()));
        this.setMonthSalary(this.getSalary());
        this.setEmployeeType(Employee.Type.TOPMANAGER);

    }

    @Override
    public void setBonus(Integer income) {
        if (income >= WHEN_BONUS) {
            this.setMonthSalary(this.getSalary() + (int) (BONUS_COEFF * this.getSalary()));
        } else {
            this.setMonthSalary(this.getSalary());
        }
    }
}
