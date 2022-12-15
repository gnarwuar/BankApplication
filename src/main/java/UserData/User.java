package UserData;

public class User {

    static private String login;
    static private String password;
    static private int ID;
    static private int BankID;
    static private String nameBank;

    public static Number getID() {
        return ID;
    }

    public static void setID(int ID) {
        User.ID = ID;
    }

    static public String getLogin() {
        return login;
    }

    static public void setLogin(String login) {
        User.login = login;
    }

    static public String getPassword() {
        return password;
    }

    static public void setPassword(String password) {
        User.password = password;
    }

    public static int getBankID() {
        return BankID;
    }

    public static void setBankID(int bankID) {
        BankID = bankID;
    }

    public static String getNameBank() {
        return nameBank;
    }

    public static void setNameBank(String nameBank) {
        User.nameBank = nameBank;
    }
}
