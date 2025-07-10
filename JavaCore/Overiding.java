class BankAccount1{
    int balance = 2000;
    void balance(){
        System.out.println("Bank account balance is " + balance);
    }
}

class BankAccount2 extends BankAccount1{
    int balance = 3000;
    void balance(){
        System.out.println("Bank account balance is " + balance);
    }
}



public class Overiding {
    public static void main(String[] args) {
        BankAccount1 bankAccount1 = new BankAccount1();
        BankAccount2 bankAccount2 = new BankAccount2();

        bankAccount1.balance();
        bankAccount2.balance();


    }
}
