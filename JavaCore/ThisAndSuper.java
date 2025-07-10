class Bank1{
    String AccountHolderName;
    Long AccountNumber;
    int i;

    public Bank1(String accountHolderName, Long accountNumber) {
        AccountHolderName = accountHolderName;
        AccountNumber = accountNumber;
    }

    public Bank1(String accountHolderName, Long accountNumber, int i) {
        AccountHolderName = accountHolderName;
        AccountNumber = accountNumber;
        this.i = i;
    }
}

class YonoSBI1 extends Bank1{

    public YonoSBI1(String Name, long Number, int i){
        super(Name,Number,i);

    }

    public YonoSBI1(String NAME, long NUMBER) {
        super(NAME, NUMBER);
        System.out.println(NAME+" "+NUMBER);



    }


}

public class ThisAndSuper {
    public static void main(String[] args) {


        YonoSBI1 yonoSBI1 = new YonoSBI1("surya prakash", 4266526262227L);

        System.out.println(yonoSBI1.AccountHolderName);
        System.out.println(yonoSBI1.AccountNumber);

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
            }
        }
    }
}


