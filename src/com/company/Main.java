package com.company;

public class Main {

    private static final int SIZE = 10000000;
    private static final int HALF = SIZE / 2;
    private static int result = 0;

    public static void main(String[] args) {
        m1();
        m2();
    }

    private static void m1(){
        float[] arr = new float[SIZE];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = 1;
        }

        long a = System.currentTimeMillis();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < SIZE; i++) {
                    arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
                }
                System.out.println(System.currentTimeMillis() - a + " первый");
            }
        });
    }

    private static void m2(){
        float[] a1 = new float[HALF];
        float[] a2 = new float[HALF];
        float[] arr = new float[SIZE];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = 1;
        }
        System.arraycopy(arr, 0, a1, 0, HALF);
        System.arraycopy(arr, HALF, a2, 0, HALF);

        long b = System.currentTimeMillis();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < HALF; i++) {
                    a1[i] = (float)(a1[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
                }
                result++;
                resultArr(result, a1, a2, arr,b);
            }
        });

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < HALF; i++) {
                    a2[i] = (float)(a2[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
                }
                result++;
                resultArr(result, a1, a2, arr,b);
            }
        });

        thread.start();
        thread1.start();
    }

    private static void resultArr(int result, float[] a1, float[] a2, float[] arr, long b){
        if (result == 2) {
            System.arraycopy(a1, 0, arr, 0, HALF);
            System.arraycopy(a2, 0, arr, HALF, HALF);
            System.out.println(System.currentTimeMillis() - b + " второй");
        }
    }
}
