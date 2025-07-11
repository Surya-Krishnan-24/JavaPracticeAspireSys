class Balance implements Runnable {
    private int balance;
    private boolean hasBalance = false;

    public synchronized void run() {
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        synchronized (this) {
            balance = 10000;
            hasBalance = true;
            notify();
        }
    }

    public synchronized int getBalance() {
        return balance;
    }

    public synchronized boolean hasBalance() {
        return hasBalance;
    }
}

class UserUI implements Runnable {
    private Balance balanceObj;

    UserUI(Balance balanceObj) {
        this.balanceObj = balanceObj;
    }

    public void run() {
        synchronized (balanceObj) {
            while (!balanceObj.hasBalance()) {
                try {
                    balanceObj.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Amount is " + balanceObj.getBalance());
        }
    }
}

public class WaitNotify {
    public static void main(String[] args) {
        Balance balance = new Balance();
        UserUI userUI = new UserUI(balance);

        Thread t1 = new Thread(balance);
        Thread t2 = new Thread(userUI);

        t2.start();
        t1.start();
    }
}
