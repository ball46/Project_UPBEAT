package Grammar.Tokenizer;

import org.junit.jupiter.api.Test;

import java.io.IOException;

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
        assertThrows(TokenizerError.NextNull.class, () -> tokenizer.peek());
        tokenizer = new Tokenizer_im("");
        assertThrows(TokenizerError.NextNull.class, () -> tokenizer.peek());
        tokenizer = new Tokenizer_im("abc");
        assertEquals("abc", tokenizer.peek());
        tokenizer = new Tokenizer_im("a b");
        assertEquals("a", tokenizer.peek());
    }

    @Test
    public void testpeekhasString() {
        tokenizer = new Tokenizer_im(null);
        assertThrows(TokenizerError.NextNull.class, () -> tokenizer.peek(""));
        assertThrows(TokenizerError.NextNull.class, () -> tokenizer.peek("abc"));
        assertThrows(TokenizerError.NextNull.class, () -> tokenizer.peek("a b"));
        tokenizer = new Tokenizer_im("");
        assertThrows(TokenizerError.NextNull.class, () -> tokenizer.peek(""));
        assertThrows(TokenizerError.NextNull.class, () -> tokenizer.peek("abc"));
        assertThrows(TokenizerError.NextNull.class, () -> tokenizer.peek("a b"));
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
        assertThrows(TokenizerError.NextNull.class, () -> tokenizer.consume());
        assertThrows(TokenizerError.NextNull.class, () -> tokenizer.consume(""));
        assertThrows(TokenizerError.NextNull.class, () -> tokenizer.consume("abc"));
        assertThrows(TokenizerError.NextNull.class, () -> tokenizer.consume("a b"));
        tokenizer = new Tokenizer_im("");
        assertThrows(TokenizerError.NextNull.class, () -> tokenizer.consume(""));
        assertThrows(TokenizerError.NextNull.class, () -> tokenizer.consume("abc"));
        assertThrows(TokenizerError.NextNull.class, () -> tokenizer.consume("a b"));
        tokenizer = new Tokenizer_im("abc");
        assertEquals("abc", tokenizer.consume());
        tokenizer = new Tokenizer_im("a b");
        assertEquals("a", tokenizer.consume());
    }

    @Test
    public void testconsumehasString() {
        tokenizer = new Tokenizer_im(null);
        assertThrows(TokenizerError.NextNull.class, () -> tokenizer.consume(""));
        assertThrows(TokenizerError.NextNull.class, () -> tokenizer.consume("abc"));
        assertThrows(TokenizerError.NextNull.class, () -> tokenizer.consume("a b"));
        tokenizer = new Tokenizer_im("");
        assertThrows(TokenizerError.NextNull.class, () -> tokenizer.consume(""));
        assertThrows(TokenizerError.NextNull.class, () -> tokenizer.consume("abc"));
        assertThrows(TokenizerError.NextNull.class, () -> tokenizer.consume("a b"));
        tokenizer = new Tokenizer_im("abc");
        assertFalse(tokenizer.consume(""));
        assertTrue(tokenizer.consume("abc"));
        assertThrows(TokenizerError.NextNull.class, () -> tokenizer.consume("a b"));
        tokenizer = new Tokenizer_im("a b");
        assertFalse(tokenizer.consume(""));
        assertFalse(tokenizer.consume("abc"));
        assertTrue(tokenizer.consume("a"));
    }

    @Test
    public void testComment() throws IOException {
        tokenizer = new Tokenizer_im("# i am ball\n 555 \n # no no i am boss");
        assertEquals("555", tokenizer.consume());
    }
}