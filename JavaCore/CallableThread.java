import java.util.concurrent.*;

public class CallableThread {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Callable<Integer> task = (() -> {
            Thread.sleep(10000);
            return 5;
        }
        );

        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Future<Integer> future = executorService.submit(task);

        System.out.println("Other operations");

        int num = future.get();

        System.out.println(num);

        executorService.shutdown();
    }
}
