package Banks;

public class Loan {
    private String loan_name;
    private double sum;
    private int duration;
    private double percents;
    private double month_pay;


    public Loan() {
        this.loan_name = "Undefined";
        this.sum = 0;
        this.duration = 0;
        this.percents = 0;

    }

    public Loan(String loan_name, double sum, int duration, double percents) {
        this.loan_name = loan_name;
        this.sum = sum;
        this.duration = duration;
        this.percents = percents;
        this.month_pay = Pay();

    }

    @Override
    public String toString() {
        return
                "Назва кредиту: '" + loan_name + '\'' +
                "\nКредитний ліміт: " + sum +
                "\nТермін: " + duration +
                " місяців \nВідсотки: " + percents +
                "%  ";
    }

    public String getLoan_name() {
        return loan_name;
    }

    public void setLoan_name(String loan_name) {
        this.loan_name = loan_name;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;

    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public double getPercents() {
        return percents;
    }

    public void setPercents(double percents) {
        this.percents = percents;
    }

    public double getMonth_pay() {
        return Pay();
    }

    public void setMonth_pay(double month_pay) {
        this.month_pay = month_pay;
    }

    private double Pay(){
        return Math.round((sum)*(((percents/duration)/100)));

    }
}
