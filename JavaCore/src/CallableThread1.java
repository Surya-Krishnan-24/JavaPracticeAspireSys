import java.util.concurrent.*;

public class CallableThread1 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Callable<String> task = (() ->{
            Thread.sleep(10000);
            return "DB Data Fetched";
        }
        );

        ExecutorService service = Executors.newSingleThreadExecutor();

        Future<String> future = service.submit(task);

        System.out.println("Other operations");

        String data = future.get();

        System.out.println(data);

        service.shutdown();
    }
}
