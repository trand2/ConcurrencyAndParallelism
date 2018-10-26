import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class MyMonteCarlo {
    public static void main(String[] args) {
        sequentialMonteCarlo();

    }

    public static void sequentialMonteCarlo() {
        int nThrows = 1000;
        long startTime = System.currentTimeMillis();
        double x = 0, y = 0;
        int nSuccess = 0;
        for (int i = 1; i <= nThrows; i++) {
            x = Math.random();
            y = Math.random();
            if (x * x + y * y <= 1)
                nSuccess++;
        }
        double value = 4.0 * nSuccess / nThrows;
        long stopTime = System.currentTimeMillis();
        System.out.println("Approx value: " + value);
        System.out.println("Time Duration: " + (stopTime - startTime) + "ms\n");


        PiMonteCarlo PiVal = new PiMonteCarlo(nThrows);
        long startTime2 = System.currentTimeMillis();
        double value2 = PiVal.getPi();
        long stopTime2 = System.currentTimeMillis();
        System.out.println("Approx value: " + value);
        System.out.println("Time Duration: " + (stopTime2 - startTime2) + "ms");
    }
}

class PiMonteCarlo {
    AtomicInteger nAtomSuccess;
    int nThrows;
    double value;
    class MonteCarlo implements Runnable {
        @Override
        public void run() {
            double x = Math.random();
            double y = Math.random();
            if (x * x + y * y <= 1)
                nAtomSuccess.incrementAndGet();
        }
    }
    public PiMonteCarlo(int i) {
        this.nAtomSuccess = new AtomicInteger(0);
        this.nThrows = i;
        this.value = 0;
    }
    public double getPi() {
        int nProcessors = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newWorkStealingPool(nProcessors);
        for (int i = 1; i <= nThrows; i++) {
            Runnable worker = new MonteCarlo();
            executor.execute(worker);
        }
        executor.shutdown();
        while (!executor.isTerminated()) {
        }
        value = 4.0 * nAtomSuccess.get() / nThrows;
        return value;
    }
}

