package Grammar.Tokenizer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Tokenizer_imTest {
    private Tokenizer tokenizer;
    @Test
    public void testhasNexttoken() {
        tokenizer = new Tokenizer_im(null);
        assertFalse(tokenizer.hasNextToken());
        tokenizer = new Tokenizer_im("");
        assertFalse(tokenizer.hasNextToken());
        tokenizer = new Tokenizer_im("abc");
        assertTrue(tokenizer.hasNextToken());
    }

    @Test
    public void testpeek() {
        tokenizer = new Tokenizer_im(null);
        assertThrows(TokenizerError.Nextnull.class, () -> tokenizer.peek());
        tokenizer = new Tokenizer_im("");
        assertThrows(TokenizerError.Nextnull.class, () -> tokenizer.peek());
        tokenizer = new Tokenizer_im("abc");
        assertEquals("abc", tokenizer.peek());
        tokenizer = new Tokenizer_im("a b");
        assertEquals("a", tokenizer.peek());
    }

    @Test
    public void testpeekhasString() {
        tokenizer = new Tokenizer_im(null);
        assertThrows(TokenizerError.Nextnull.class, () -> tokenizer.peek(""));
        assertThrows(TokenizerError.Nextnull.class, () -> tokenizer.peek("abc"));
        assertThrows(TokenizerError.Nextnull.class, () -> tokenizer.peek("a b"));
        tokenizer = new Tokenizer_im("");
        assertThrows(TokenizerError.Nextnull.class, () -> tokenizer.peek(""));
        assertThrows(TokenizerError.Nextnull.class, () -> tokenizer.peek("abc"));
        assertThrows(TokenizerError.Nextnull.class, () -> tokenizer.peek("a b"));
        tokenizer = new Tokenizer_im("abc");
        assertFalse(tokenizer.peek(""));
        assertTrue(tokenizer.peek("abc"));
        assertFalse(tokenizer.peek("a b"));
        tokenizer = new Tokenizer_im("a b");
        assertFalse(tokenizer.peek(""));
        assertFalse(tokenizer.peek("abc"));
        assertTrue(tokenizer.peek("a"));
    }

    @Test
    public void testconsume() {
        tokenizer = new Tokenizer_im(null);
        assertThrows(TokenizerError.Nextnull.class, () -> tokenizer.consume());
        assertThrows(TokenizerError.Nextnull.class, () -> tokenizer.consume(""));
        assertThrows(TokenizerError.Nextnull.class, () -> tokenizer.consume("abc"));
        assertThrows(TokenizerError.Nextnull.class, () -> tokenizer.consume("a b"));
        tokenizer = new Tokenizer_im("");
        assertThrows(TokenizerError.Nextnull.class, () -> tokenizer.consume(""));
        assertThrows(TokenizerError.Nextnull.class, () -> tokenizer.consume("abc"));
        assertThrows(TokenizerError.Nextnull.class, () -> tokenizer.consume("a b"));
        tokenizer = new Tokenizer_im("abc");
        assertEquals("abc", tokenizer.consume());
        tokenizer = new Tokenizer_im("a b");
        assertEquals("a", tokenizer.consume());
    }

    @Test
    public void testconsumehasString() {
        tokenizer = new Tokenizer_im(null);
        assertThrows(TokenizerError.Nextnull.class, () -> tokenizer.consume(""));
        assertThrows(TokenizerError.Nextnull.class, () -> tokenizer.consume("abc"));
        assertThrows(TokenizerError.Nextnull.class, () -> tokenizer.consume("a b"));
        tokenizer = new Tokenizer_im("");
        assertThrows(TokenizerError.Nextnull.class, () -> tokenizer.consume(""));
        assertThrows(TokenizerError.Nextnull.class, () -> tokenizer.consume("abc"));
        assertThrows(TokenizerError.Nextnull.class, () -> tokenizer.consume("a b"));
        tokenizer = new Tokenizer_im("abc");
        assertFalse(tokenizer.consume(""));
        assertTrue(tokenizer.consume("abc"));
        assertThrows(TokenizerError.Nextnull.class, () -> tokenizer.consume("a b"));
        tokenizer = new Tokenizer_im("a b");
        assertFalse(tokenizer.consume(""));
        assertFalse(tokenizer.consume("abc"));
        assertTrue(tokenizer.consume("a"));
    }
}