import org.junit.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class BankTest {
    Bank bank = new Bank();

    @Before
    public void setup() {
        for (int i = 1; i <= 5; i++) {
            bank.createAccount(500000);
        }
    }

    @Test
    public void testValidCheck() {
        assertTrue(bank.validCheck("004", "005", 1));
        assertTrue(bank.validCheck("001", "002", 10000));

        bank.blockAfterFraudCheck("004","005");
        assertFalse(bank.validCheck("004", "005", 1));
        assertFalse(bank.validCheck("001", "001", 1));
        assertFalse(bank.validCheck("001", "002", 1000000));
    }

    @Test
    public void testTransfer() throws InterruptedException {
        ExecutorService es = Executors.newFixedThreadPool(50);

        Runnable task = () -> {
            for (int i = 0; i < 10000; i++) {
                try {
                    bank.transfer("001", "002", 1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        for (int i = 0; i < 50; i++) {
            es.submit(task);
        }
        es.shutdown();
        es.awaitTermination(1, TimeUnit.HOURS);

        for (int i = 0; i < 50000; i++) {
            //bank.transfer("001","002", 1);
            bank.transfer("002","003", 1);

            bank.transfer("002","003", 1);
            bank.transfer("003","002", 1);

            bank.transfer("002","003", 1);
            bank.transfer("003","002", 1);

            bank.transfer("004","005", 1);

            bank.blockAfterFraudCheck("004","005");
        }

        assertEquals(bank.getAccountBalance("001"), 0);
        assertEquals(bank.getAccountBalance("002"), 950000);
        assertEquals(bank.getAccountBalance("003"), 550000);
        assertEquals(bank.getAccountBalance("004"), 499999);
    }

}
