package ie.gmit.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EPLTest {

    private EPL epl;

    @BeforeEach
    private void setup() {
        epl = new EPL();
    }

    @Test
    public void test() {
        String code = "this is a cat";

        assertNotEquals(code, epl.getTicketCode());

        epl.setTicketCode(code);

        assertEquals(code, epl.getTicketCode());
    }

}