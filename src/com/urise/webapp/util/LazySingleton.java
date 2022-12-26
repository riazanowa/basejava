package com.urise.webapp.util;

public class LazySingleton {
    private static LazySingleton INSTANCE;

    //Reordering Java Memory Model - 'volatile' prevents reordering
    double sin = Math.sin(13);

    private LazySingleton() {
    }
    private static class LazySingletonHolder {
        private static final LazySingleton INSTANCE = new LazySingleton();
    }
    public static LazySingleton getInstance() {
        return LazySingletonHolder.INSTANCE;
    }
//    public static synchronized LazySingleton getInstance() {
//        if (INSTANCE == null) {
//            // double check locking
//            synchronized ((LazySingleton.class)) {
//                if (INSTANCE == null) {
//                    INSTANCE = new LazySingleton();
//                }
//            }
//        }
//        return INSTANCE;
//    }
}
