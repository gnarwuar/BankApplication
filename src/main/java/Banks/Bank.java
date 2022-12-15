package Banks;

public class Bank {
    public Bank(int idBank, String bank_name) {
        this.idBank = idBank;
        this.bank_name = bank_name;
    }

    private int idBank;
    private String bank_name;


    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public int getIdBank() {
        return idBank;
    }

    public void setIdBank(int idBank) {
        this.idBank = idBank;
    }

    @Override
    public String toString() {
        return  bank_name;
    }
}
