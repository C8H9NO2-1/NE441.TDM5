package fr.esisar.exercice1;

public class NoThread {

    public static void main(String[] args) {
        NoThread noThread = new NoThread();
        noThread.execute();
    }

    private void execute() {
        long start = System.currentTimeMillis();
        int N = 2_000_000_000;
        double result = 0;

        for (double i = 1; i <= N; i++) {
            result += 1 / Math.pow(i, 2);
        }

        System.out.println("Résultat = " + result);
        long stop = System.currentTimeMillis();
        System.out.println("Elapsed Time = "+(stop-start)+" ms");
    }
}
