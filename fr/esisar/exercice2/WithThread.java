package fr.esisar.exercice2;

import java.util.ArrayList;
import java.util.List;

public class WithThread extends Thread {

    private int begin;
    private int end;
    private float result;

    public WithThread(int begin, int end) {
        this.begin = begin;
        this.end = end;
        result = 0;
    }

    public void run() {
        for (int i = begin; i <= end; i++) {
            result += 1 / Math.pow(i, 2);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        int N = 2_000_000_000;
        int numberOfThread = 16;
        int step = N / numberOfThread;
        double result = 0;
        List<WithThread> threads = new ArrayList<>();
        for (int i = 0; i < numberOfThread; i++) {
            int begin = i * step + 1;
            int end = (i + 1) * step;
            threads.add(new WithThread(begin, end));
            threads.get(i).start();
        } 

        for (WithThread thread : threads) {
            thread.join(); 
        }

        for (WithThread thread : threads) {
            result += thread.result;
        }


        System.out.println("Résultat = " + result);
        long stop = System.currentTimeMillis();
        System.out.println("Elapsed Time = "+(stop-start)+" ms");
    }

}

