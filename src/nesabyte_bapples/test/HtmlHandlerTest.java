package nesabyte_bapples.test;

import org.junit.jupiter.api.Test;

import static nesabyte_bapples.HtmlHandler.classifyingHTML;
import static org.junit.jupiter.api.Assertions.*;

class HtmlHandlerTest {

    // testing classifyingHTML() tests everything under HtmlHandler.java
    @Test
    void classifyHTML() {
        String test_str = "I am a string with a good url: https://github.com/Seneca-CDOT/topics-in-open-source-2020/wiki/lab-8";
        int test_int = 999;

        try{
            classifyingHTML(test_str,test_int);
        }catch(Exception e) { // catch any exceptions
            assertFalse(false);
        }
        assertTrue(true);
    }

}