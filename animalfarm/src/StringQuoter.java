import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class StringQuoter
{
    public static void main(String[] args)
    {
        try
        {
            System.out.println(quoteFromFile("cat.txt"));
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }

    public static String quoteFromFile(String filename) throws IOException
    {
        try (BufferedReader in = new BufferedReader(new FileReader(filename)))
        {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null)
            {
                content.append(line);
                content.append('\n');
            }
            return quote(content);
        }
    }

    public static String quote(CharSequence raw)
    {
        StringBuilder literal = new StringBuilder("\"");
        for (int i = 0; i < raw.length(); ++i)
        {
            char x = raw.charAt(i);
            literal.append(quote(x));
        }
        literal.append("\"");
        return literal.toString();
    }

    public static String quote(char raw)
    {
        switch (raw)
        {
        case 7:
            return "\\a";
        case 8:
            return "\\b";
        case 9:
            return "\\t";
        case 10:
            return "\\n";
        case 11:
            return "\\v";
        case 12:
            return "\\f";
        case 13:
            return "\\r";
        case ' ':
            return " ";
        case '!':
            return "!";
        case '\"':
            return "\\\"";
        case '#':
            return "#";
        case '$':
            return "$";
        case '%':
            return "%";
        case '&':
            return "&";
        case '\'':
            return "\\\'";
        case '(':
            return "(";
        case ')':
            return ")";
        case '*':
            return "*";
        case '+':
            return "+";
        case ',':
            return ",";
        case '-':
            return "-";
        case '.':
            return ".";
        case '/':
            return "/";
        case '0':
            return "0";
        case '1':
            return "1";
        case '2':
            return "2";
        case '3':
            return "3";
        case '4':
            return "4";
        case '5':
            return "5";
        case '6':
            return "6";
        case '7':
            return "7";
        case '8':
            return "8";
        case '9':
            return "9";
        case ':':
            return ":";
        case ';':
            return ";";
        case '<':
            return "<";
        case '=':
            return "=";
        case '>':
            return ">";
        case '?':
            return "?";
        case '@':
            return "@";
        case 'A':
            return "A";
        case 'B':
            return "B";
        case 'C':
            return "C";
        case 'D':
            return "D";
        case 'E':
            return "E";
        case 'F':
            return "F";
        case 'G':
            return "G";
        case 'H':
            return "H";
        case 'I':
            return "I";
        case 'J':
            return "J";
        case 'K':
            return "K";
        case 'L':
            return "L";
        case 'M':
            return "M";
        case 'N':
            return "N";
        case 'O':
            return "O";
        case 'P':
            return "P";
        case 'Q':
            return "Q";
        case 'R':
            return "R";
        case 'S':
            return "S";
        case 'T':
            return "T";
        case 'U':
            return "U";
        case 'V':
            return "V";
        case 'W':
            return "W";
        case 'X':
            return "X";
        case 'Y':
            return "Y";
        case 'Z':
            return "Z";
        case '[':
            return "[";
        case '\\':
            return "\\\\";
        case ']':
            return "]";
        case '^':
            return "^";
        case '_':
            return "_";
        case '`':
            return "`";
        case 'a':
            return "a";
        case 'b':
            return "b";
        case 'c':
            return "c";
        case 'd':
            return "d";
        case 'e':
            return "e";
        case 'f':
            return "f";
        case 'g':
            return "g";
        case 'h':
            return "h";
        case 'i':
            return "i";
        case 'j':
            return "j";
        case 'k':
            return "k";
        case 'l':
            return "l";
        case 'm':
            return "m";
        case 'n':
            return "n";
        case 'o':
            return "o";
        case 'p':
            return "p";
        case 'q':
            return "q";
        case 'r':
            return "r";
        case 's':
            return "s";
        case 't':
            return "t";
        case 'u':
            return "u";
        case 'v':
            return "v";
        case 'w':
            return "w";
        case 'x':
            return "x";
        case 'y':
            return "y";
        case 'z':
            return "z";
        case '{':
            return "{";
        case '|':
            return "|";
        case '}':
            return "}";
        case '~':
            return "~";
        default:
            return String.format("\\u%04x", +raw);
        }
    }
}
