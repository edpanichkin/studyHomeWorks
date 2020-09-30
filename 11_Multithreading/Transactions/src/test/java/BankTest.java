import org.junit.*;
import java.util.HashMap;

import static org.junit.Assert.*;

public class BankTest {
    Bank bank = new Bank();

    @Before
    public void setup() throws InterruptedException {

        for (int i = 1; i <= 5; i++) {
            bank.createAccount(50000);
        }
    }

    @Test
    public void accountFraudCheckTest() {

    }
    @Test
    public void testValidCheck() {
        assertTrue(bank.validCheck("004", "005", 1));
        assertTrue(bank.validCheck("001", "002", 10000));

        bank.blockAfterFraudCheck("004","005");
        assertFalse(bank.validCheck("004", "005", 1));
        assertFalse(bank.validCheck("001", "002", 1000000));
    }

    @Test
    public void testTransfer() throws InterruptedException {

        for (int i = 0; i < 50000; i++) {
            bank.transfer("001","002", 1);
            bank.transfer("002","003", 1);
            bank.transfer("004","005", 1);

        }

        assertEquals(bank.getAccountBalance("001"), 0);
        assertEquals(bank.getAccountBalance("002"), 50000);
        assertEquals(bank.getAccountBalance("003"), 100000);
        assertEquals(bank.getAccountBalance("004"), 1);


    }

}
