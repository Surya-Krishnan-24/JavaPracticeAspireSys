import java.util.concurrent.*;

class Countere{
    int count = 0 ;

    synchronized void increment(){
        this.count++;
    }

}


public class callexx {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Countere countere = new Countere();

        Callable<Integer> task = (() -> {
            for (int i=0;i<100;i++){
                countere.increment();
                System.out.println("current thred : " + Thread.currentThread().getName());

                if(Thread.currentThread().getName().equals("pool-1-thread-2")){
                    Thread.sleep(4);
                }
            }
            return countere.count;
        });


        ExecutorService service = Executors.newFixedThreadPool(2);

        Future<Integer> run1 = service.submit(task);

        Future<Integer> run2 = service.submit(task);


        int data1 = run1.get();
        int data2 = run2.get();


        System.out.println(data1);
        System.out.println(data2);




    }
}
