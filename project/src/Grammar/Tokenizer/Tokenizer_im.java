package Grammar.Tokenizer;

public class Tokenizer_im implements Tokenizer {
    private final String src;
    private int pos;
    private String next;

    public Tokenizer_im(String src) {
        this.src = src;
        this.pos = 0;
        computeNext();
    }
    @Override
    public boolean hasNextToken() {
        return next != null;
    }
    @Override
    public String peek() throws TokenizerError {
        if(!hasNextToken()) throw new TokenizerError.NextNull();
        return next;
    }
    @Override
    public boolean peek(String s) {
        if (!hasNextToken()) return false;
        return peek().equals(s);
    }
    @Override
    public String consume() throws TokenizerError {
        if(!hasNextToken()) throw new TokenizerError.NextNull();
        else{
            String result = next;
            computeNext();
            return result;
        }
    }
    @Override
    public boolean consume(String s) throws TokenizerError {
        if(!hasNextToken()) throw new TokenizerError.NextNull();
        else{
            if(next.equals(s)) {
                computeNext();
                return true;
            }else return false;
        }
    }
    private boolean DoNotCareChar(char c) throws TokenizerError {
        return Character.isWhitespace(c) || c == '#' ;
    }
    private void readText() {
        while(pos <src.length() && src.charAt(pos) != '\n') pos++;
    }
    private void computeNext() throws TokenizerError{
        String check = "+-*/(){}^=%";
        StringBuilder s = new StringBuilder();
        if(src == null) return;
        while (pos < src.length() && DoNotCareChar(src.charAt(pos))) {
            if(src.charAt(pos) == '#') readText();
            else pos++;
        }
        if(pos == src.length()) {
            next = null;
            return;
        }
        char c = src.charAt(pos);
        if(Character.isDigit(c)) {
            while (pos < src.length() && Character.isDigit(src.charAt(pos))) {
                s.append(src.charAt(pos));
                pos++;
            }
        } else if (Character.isAlphabetic(c) || c == '_') {
            while (pos < src.length() && Character.isAlphabetic(src.charAt(pos))) {
                s.append(src.charAt(pos));
                pos++;
            }
        } else if (check.contains(Character.toString(c))) {
            s.append(src.charAt(pos));
            pos++;
        }else{
            throw new TokenizerError.UnknownWord(c);
        }
        next = s.toString();
    }

}
