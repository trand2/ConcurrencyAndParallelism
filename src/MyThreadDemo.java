import java.util.ArrayDeque;
import java.util.Random;


public class MyThreadDemo {


    public static void main(String[] args) {
        Random randomNum = new Random(2018);
        ArrayDeque<Integer> arrayDeque = new ArrayDeque<>();

        Runnable produce = () -> {
            for (int i = 0; i < 30; i++) {
                int randomInt = randomNum.nextInt(213);
                arrayDeque.add(randomInt);
                System.out.println("push " + randomInt);
            }
        };

        Runnable consume = () -> {
            for (int i = 0; i < 30; i++) {
                System.out.println("pop " + arrayDeque.pop());
            }
        };

        Thread thread1 = new Thread(produce);
        Thread thread2 = new Thread(consume);

        thread1.start();
        thread2.start();
    }

}

