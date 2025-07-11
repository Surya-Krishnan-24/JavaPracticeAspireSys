import java.util.concurrent.*;

public class CallableApplication {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(4);

        Callable<String> loadUser = (() -> {
            Thread.sleep(2000);
            return "User Profile Loaded";
        });

        Callable<String> loadbalance = (() -> {
            Thread.sleep(2000);
            return "Balance fetched";
        });

        Callable<String> loadtransaction = (() -> {
            Thread.sleep(20000);
            return "Last 20 transactions Fetched";
        });
        Callable<String> loadcibilScore = (() -> {
            Thread.sleep(10000);
            return "Cibil score fetched";
        });


        Future<String> future1 = service.submit(loadUser);
        Future<String> future2 = service.submit(loadbalance);
        Future<String> future3 = service.submit(loadtransaction);
        Future<String> future4 = service.submit(loadcibilScore);

        String profile = future1.get();
        String balance = future2.get();
        String transaction = future3.get();
        String cibilScore = future4.get();


        System.out.println(profile);
        System.out.println(balance);
        System.out.println(transaction);
        System.out.println(cibilScore);

        service.shutdown();




    }
}
