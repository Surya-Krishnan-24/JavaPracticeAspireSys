class Bank{
    void applyCreditCard(){
        System.out.println("Credit card Applied");
    }
    void savingAccountIntrest(){
        System.out.println("Intrest amount credited");
    }
}

class Internet_Banking extends Bank{

    void checkBalance(){
        System.out.println("balance is 20,000");
    }

    void moneyTransfer(){
        System.out.println("Money transferred to Shop Rs. 1000");
    }



}

class YonoSBI extends Internet_Banking{

    void upiPayments(){
        System.out.println("Upi payment done");
    }
}



public class Inheritance {
    public static void main(String[] args) {
        YonoSBI yonoSBI= new YonoSBI();

        yonoSBI.upiPayments();
        yonoSBI.moneyTransfer();
        yonoSBI.applyCreditCard();
        yonoSBI.checkBalance();
        yonoSBI.savingAccountIntrest();
    }
}
