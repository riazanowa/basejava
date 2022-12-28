package com.urise.webapp;

public class MainConcurrency {
    private static Object ACCOUNT_1 = new Object();
    private static Object ACCOUNT_2 = new Object();

    public static void deposit(Object account, int amount) {
        System.out.println(Thread.currentThread().getName() + " deposit()");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("End: deposit()");
    }

    public static void withdraw(Object account, int amount) {
        System.out.println(Thread.currentThread().getName() + " withdraw()");
        System.out.println("End: withdraw()");
    }

    public static void main(String[] args) {

        int amount1 = 50;
        int amount2 = 20;
        Thread t1 = createTask(ACCOUNT_1, ACCOUNT_2, amount1, MainConcurrency::deposit, MainConcurrency::withdraw);
        Thread t2 = createTask(ACCOUNT_2, ACCOUNT_1, amount2, MainConcurrency::withdraw, MainConcurrency::deposit);
        t1.start();
        t2.start();
    }

    public static Thread createTask(Object acc1, Object acc2, int amount, CustomConsumer c1, CustomConsumer c2) {
        return new Thread(() -> {
            synchronized (acc1) {
                c1.accept(acc1, amount);
                synchronized (acc2) {
                    c2.accept(acc2, amount);
                }
            }
        });
    }
}
interface CustomConsumer {
    void accept(Object acc, int amount);
}
