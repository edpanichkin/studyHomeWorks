import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Main {
    private static final Random random = new Random();
    private static final String ACC_NUM_FORMAT = "%03d";
    private static long start = System.currentTimeMillis();

    public static void main(String[] args) throws InterruptedException {
        Bank bank = new Bank();
        for (int i = 1; i < 999; i++) {
            bank.createAccount(50000 + random.nextInt(100000));
        }
        bank.bankBalance();
        bank.printBlockedCount();
        System.out.println("Program START!");

        ExecutorService es = Executors.newFixedThreadPool(4);
        Runnable task = () -> {
            try {
                for (int j = 0; j < 100000; j++) {
                    String from = String.format(ACC_NUM_FORMAT, 1 + random.nextInt(998));
                    String to = String.format(ACC_NUM_FORMAT, 1 + random.nextInt(998));
                    int rndAmount = random.nextInt(50000);
                    long amount = (Math.random() < 0.01 ? (50000 + rndAmount) : rndAmount);
                    bank.transfer(from, to, amount);
                }
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        for (int i = 0; i < 4; i++) {
            es.submit(task);
        }

//        List<Thread> threadList = new ArrayList<>();
//        for (int i = 0; i < Runtime.getRuntime().availableProcessors() * 2; i++) {
//            threadList.add(new Thread(() -> {
//                try {
//                    for (int j = 0; j < 1000; j++) {
//                        String from = String.format(ACC_NUM_FORMAT, 1 + random.nextInt(998));
//                        String to = String.format(ACC_NUM_FORMAT, 1 + random.nextInt(998));
//                        int rndAmount = random.nextInt(50000);
//                        long amount = (Math.random() < 0.01 ? (50000 + rndAmount) : rndAmount);
//                        bank.transfer(from, to, amount);
//                    }
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }));
//            threadList.add(new Thread(() -> {
//                for (int j = 0; j < 1000000; j++) {
//                    String get = String.format("%03d", 1 + random.nextInt(998));
//                    bank.getAccountBalance(get);
//                }
//            }));
//        }
//        threadList.forEach(Thread::start);
//        for(Thread t : threadList){
//            t.join();
//        }
        es.shutdown();
        es.awaitTermination(1, TimeUnit.HOURS);

        bank.debugPrint();
        bank.printBlockedCount();
        System.out.println("ALL DONE, " + (System.currentTimeMillis() - start) + " ms");

    }
}
