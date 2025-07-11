class Counter extends Thread{
    static int count = 0;

    public synchronized static void add(){
        count++;
    }

}

public class SyncronizePractice {
    public static void main(String[] args) throws InterruptedException {
        Runnable runnable = (() -> {
            for (int i=0;i<10000;i++) {
                Counter.add();
            }
        });

        Thread t1 = new Thread(runnable);
        Thread t2 = new Thread(runnable);

        t1.start();
        t2.start();
        t1.join();
        t2.join();


        System.out.println(Counter.count);
    }
}
