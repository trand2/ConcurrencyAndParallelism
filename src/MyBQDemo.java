import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;


public class MyBQDemo {

    public static void main(String[] args) {
        Random randomNum = new Random(2018);
        ArrayBlockingQueue<Integer> buffer = new ArrayBlockingQueue<>(2);


        Runnable produce = () -> {
            for (int i = 0; i < 30; i++) {
                    int randomInt = randomNum.nextInt(213);
                try {
                    buffer.put(randomInt);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("push " + randomInt);
            }
        };


        Runnable consume = () -> {
            for (int i = 0; i < 30; i++) {
                try {
                    System.out.println("pop " + buffer.take());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread thread1 = new Thread(produce);
        Thread thread2 = new Thread(consume);

        thread1.start();
        thread2.start();
    }

}