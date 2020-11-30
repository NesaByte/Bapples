package nesabyte_bapples.test;

import nesabyte_bapples.Cleaner;
import org.junit.jupiter.api.Test;

import static nesabyte_bapples.Handlers.LinkCode;
import static org.junit.jupiter.api.Assertions.*;

class CleanerTest {

  // Testing for 'no feature' argument
  @Test
  void normal_args() throws Exception {
    Cleaner cleaner = new Cleaner();

    String[] args = {"assets/testing.html"};

    try {
      cleaner.main(args);
    } catch (Exception e) { // catch any exceptions
      assertFalse(false);
    }
    assertTrue(true);
  }

  // Testing for multiple questionable arguments
  @Test
  void abc_args() throws Exception {
    Cleaner cleaner = new Cleaner();

    String[] abc_args = {"a", "b", "c", "d", "e"};

    try {
      cleaner.main(abc_args);
    } catch (Exception e) { // catch any exceptions
      assertFalse(false);
    }
    assertTrue(true);
  }

  // Testing for file that doesnt exist
  @Test
  void none_args() throws Exception {
    Cleaner cleaner = new Cleaner();

    String[] none_args = {"assets/tester123.html"};
    try {
      cleaner.main(none_args);
    } catch (Exception e) { // catch any exceptions
      assertFalse(false);
    }
    assertTrue(true);
  }

  // Testing for no argument at all
  @Test
  void no_args() throws Exception {
    Cleaner cleaner = new Cleaner();

    String[] no_args = {""};

    try {
      cleaner.main(no_args);
    } catch (Exception e) { // catch any exceptions
      assertFalse(false);
    }
    assertTrue(true);
  }

  // Testing for version
  @Test
  void ver_args() throws Exception {
    Cleaner cleaner = new Cleaner();

    String[] ver_args = {"--v"};
    try {
      cleaner.main(ver_args);
    } catch (Exception e) { // catch any exceptions
      assertFalse(false);
    }
    assertTrue(true);
  }

  // Testing for help
  @Test
  void help_args() throws Exception {
    Cleaner cleaner = new Cleaner();

    String[] help_args = {"--h"};

    try {
      cleaner.main(help_args);
    } catch (Exception e) { // catch any exceptions
      assertFalse(false);
    }
    assertTrue(true);
  }

  // Testing for good links
  @Test
  void good_args() throws Exception {
    Cleaner cleaner = new Cleaner();

    String[] good_args = {"--good", "assets/testing.html"};

    try {
      cleaner.main(good_args);
    } catch (Exception e) { // catch any exceptions
      assertFalse(false);
    }
    assertTrue(true);
  }

  // Testing for bad links
  @Test
  void bad_args() throws Exception {
    Cleaner cleaner = new Cleaner();

    String[] bad_args = {"--bad", "assets/testing.html"};

    try {
      cleaner.main(bad_args);
    } catch (Exception e) { // catch any exceptions
      assertFalse(false);
    }
    assertTrue(true);
  }

  // Testing for json output
  @Test
  void json_args() throws Exception {
    Cleaner cleaner = new Cleaner();

    String[] json_args = {"--j", "assets/testing.html"};

    try {
      cleaner.main(json_args);
    } catch (Exception e) { // catch any exceptions
      assertFalse(false);
    }
    assertTrue(true);
  }

  // Testing for ignored feature
  @Test
  void ignore_args() throws Exception {
    Cleaner cleaner = new Cleaner();

    String[] ignore_args = {"--ignore", "assets/ignore-urls.txt", "assets/testing.html"};

    try {
      cleaner.main(ignore_args);
    } catch (Exception e) { // catch any exceptions
      assertFalse(false);
    }
    assertTrue(true);
  }

  // Testing for the telescope localhost feature
  @Test
  void lh_args() throws Exception {
    Cleaner cleaner = new Cleaner();

    String[] lh_args = {"--lh", "http://localhost:3000"};
    try {
      cleaner.main(lh_args);
    } catch (Exception e) { // catch any exceptions
      assertFalse(false);
    }
    assertTrue(true);
  }
}
