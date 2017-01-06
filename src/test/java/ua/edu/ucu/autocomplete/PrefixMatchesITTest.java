
package ua.edu.ucu.autocomplete;

import static org.hamcrest.Matchers.containsInAnyOrder;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import ua.edu.ucu.tries.RWayTrie;

/**
 *
 * @author Andrii_Rodionov
 */
public class PrefixMatchesITTest {

    private PrefixMatches pm;

    @Before
    public void init() {
        pm = new PrefixMatches(new RWayTrie());
        pm.load("abc", "abce", "abcd", "abcde", "abcdef");
    }


    @Test
    public void testTrieSizeChangeToZero() {
        pm.delete("abc");
        pm.delete("abcd");
        pm.delete("abce");
        pm.delete("abcde");
        pm.delete("abcdef");

        int result = pm.size();

        int expResult = 0;

        assertEquals(expResult, result);
    }

    @Test
    public void testTrieSize() {

        int result = pm.size();

        int expResult = 5;

        assertEquals(expResult, result);
    }

    @Test
    public void testTrieSizeChange() {
        pm.delete("abc");

        int result = pm.size();

        int expResult = 4;

        assertEquals(expResult, result);
    }


    @Test
    public void testTrieFalseDelete() {

        boolean result = pm.delete("abcqe1");

        boolean expResult = false;

        assertEquals(expResult, result);
    }

    @Test
    public void testTrieTrueDelete() {

        boolean result = pm.delete("abc");

        boolean expResult = true;

        assertEquals(expResult, result);
    }

    @Test
    public void testTrieFalseContain() {

        boolean result = pm.contains("ASS123");

        boolean expResult = false;

        assertEquals(expResult, result);
    }

    @Test
    public void testTrieTrueContain() {

        boolean result = pm.contains("Abc");

        boolean expResult = true;

        assertEquals(expResult, result);
    }

    @Test
    public void testWordsWithNullPrefix_String() {
        String pref = "";

        Iterable<String> result = pm.wordsWithPrefix(pref);

        String[] expResult = {};

        assertThat(result, containsInAnyOrder(expResult));
    }

    @Test
    public void testWordsWithShortPrefix_String() {
        String pref = "a";

        Iterable<String> result = pm.wordsWithPrefix(pref);

        String[] expResult = {};

        assertThat(result, containsInAnyOrder(expResult));
    }

    @Test
    public void testWordsWithPrefix_String() {
        String pref = "ab";

        Iterable<String> result = pm.wordsWithPrefix(pref);

        String[] expResult = {"abc", "abce", "abcd", "abcde", "abcdef"};

        assertThat(result, containsInAnyOrder(expResult));
    }

    @Test
    public void testWordsWithPrefix_String_and_K() {
        String pref = "abc";
        int k = 3;

        Iterable<String> result = pm.wordsWithPrefix(pref, k);

        String[] expResult = {"abc", "abce", "abcd", "abcde"};

        assertThat(result, containsInAnyOrder(expResult));
    }

}
