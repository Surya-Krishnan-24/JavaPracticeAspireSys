class Mythread implements Runnable{
    public  void run() {
        System.out.println("Thread running: " + Thread.currentThread().getName());
        try {
            Thread.sleep(30);
        } catch (InterruptedException ignored) {

        }
        System.out.println("Loaded");

    }

}

public class ThreadPractice extends Thread {
    public static void main(String[] args) throws InterruptedException {

        Thread t =  new Thread(new Mythread());


        Thread printNumber = new Thread(() ->
        {
            for (int i = 1; i < 1000; i++) {
                System.out.println("Number " + i);

            }
        });

        Thread printAlphabet = new Thread(() ->
        {
            for (int i = 2000; i < 3000; i++) {
                System.out.println("Alphabet " + i);
            }
        }
        );

        printNumber.start();
        printAlphabet.start();
        t.start();
        t.join();
        printAlphabet.join();
        printNumber.join();

        System.out.println("Main is printing");



        }


    }

