import java.util.concurrent.*;


class Counter1{
    int count = 0;
    synchronized void increment(){
        count++;
    }
}
public class CallableThread1 {


    public static void main(String[] args) throws ExecutionException, InterruptedException {

        Counter1 counter1 = new Counter1();

        Callable<Integer> task = (() -> {

            for(int i=0;i<10000;i++){
                counter1.increment();
            }
            return counter1.count;

        }
        );



        ExecutorService service = Executors.newFixedThreadPool(2);

        Future<Integer> future = service.submit(task);

        Future<Integer> future1 = service.submit(task);

        future1.get();

        System.out.println(counter1.count);

        System.out.println("Other operations");

        Integer data = future.get();

        System.out.println(counter1.count);

        service.shutdown();
    }
}
