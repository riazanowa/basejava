package com.urise.webapp;

public class MainConcurrency {
    private static Object ACCOUNT_1 = new Object();
    private static Object ACCOUNT_2 = new Object();

    public static void  deposit(Object account, int amount) {
        System.out.println(Thread.currentThread().getName() + " deposit()");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("End: deposit()");
    }

    public static void  withdraw(Object account, int amount) {
        System.out.println(Thread.currentThread().getName() + " withdraw()");
        System.out.println("End: withdraw()");
    }
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            synchronized (ACCOUNT_1) {
                deposit(ACCOUNT_1, 50);
                synchronized (ACCOUNT_2) {
                    withdraw(ACCOUNT_2, 50);
                }
            }
        });

        Thread t2 = new Thread(() -> {
            synchronized (ACCOUNT_2) {
                withdraw(ACCOUNT_2, 20);
                synchronized (ACCOUNT_1) {
                    deposit(ACCOUNT_1, 20);
                }
            }
        });
        t1.start();
        t2.start();
    }
}
