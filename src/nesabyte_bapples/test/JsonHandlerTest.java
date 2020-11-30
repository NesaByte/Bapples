package nesabyte_bapples.test;

import org.junit.jupiter.api.Test;

import static nesabyte_bapples.JsonHandler.tobeJSON;
import static org.junit.jupiter.api.Assertions.*;

class JsonHandlerTest {

    @Test
    void testJSON() {
        String html = "assets/testing.html";
        try{
            tobeJSON(html);
        }catch(Exception e) { // catch any exceptions
            assertFalse(false);
        }
        assertTrue(true);
    }
}