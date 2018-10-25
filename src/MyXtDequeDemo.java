import java.util.ArrayDeque;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class MyXtDequeDemo {

    public static void main(String[] args) {
        Random randomNum = new Random(2018);
        ArrayDeque<Integer> arrayDeque = new ArrayDeque<>();
        Lock lock = new ReentrantLock();


            Runnable produce = () -> {
                for (int i = 0; i < 30; i++) {
                    lock.lock();
                    try {
                        int randomInt = randomNum.nextInt(213);
                        arrayDeque.add(randomInt);
                        System.out.println("push " + randomInt);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        lock.unlock();
                    }
                }
            };


        Runnable consume = () -> {
            for (int i = 0; i < 30; i++) {
                lock.lock();
                try {
                    System.out.println("pop " + arrayDeque.pop());
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        };

        Thread thread1 = new Thread(produce);
        Thread thread2 = new Thread(consume);

        thread1.start();
        thread2.start();
    }

}

