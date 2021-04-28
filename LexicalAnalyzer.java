import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.HashMap;

public class LexicalAnalyzer
{
  private boolean eoi;
  private char currChar;
  private int currCharIndex;
  private BufferedReader br;
  private FileInputStream fstream;
  private HashMap<String, Symbol> reservedWords;
  private String line;

  public LexicalAnalyzer(String filename)
  {
    eoi = false;
    currChar = ' ';
    currCharIndex = 0;
    setUpReservedWordMap();

    try
    {
      fstream = new FileInputStream(filename);
      br = new BufferedReader(new InputStreamReader(fstream));
    }
    catch (IOException e)
    {
      System.out.println(e);
    }

    line = "\n";
  }

  public int getCurrCharPosNum()
  {
    return currCharIndex;
  }

  public Token getToken()
  {
    Token t;

    while (!eoi && isWhiteSpace(currChar)) getChar();
    if (eoi)
    {
      t = new Token(Symbol.EOI, null, 0);
      try
      {
        br.close();
      }
      catch (IOException e)
      {
        System.out.println(e);
      }
    }
    else
      if (isLetter(currChar))
        t = readIdent();
      else
        if (isDigit(currChar))
          t = readNumLit();
        else
          t = readOthers();

    return t;
  }

  private void getChar()
  {
    if (currCharIndex == (line.length() - 1))
    {
      line = readNextLine();
      if (line != null) System.out.print(line);
      currCharIndex = -1;
    }
    if (line == null)
      eoi = true;
    else
    {
      currCharIndex++;
      currChar = line.charAt(currCharIndex);
    }
  }

  private boolean isDigit(char ch)
  {
    return ((ch >= '0' && ch <= '9'));
  }

  private boolean isLetter(char ch)
  {
    return ((ch >= 'a' && ch <= 'z') ||
            (ch >= 'A' && ch <= 'Z'));
  }

  private boolean isWhiteSpace(char ch)
  {
    return ((ch == ' ' || ch == '\t' || ch == '\n'));
  }

  private Token readIdent()
  {
    String id;
    StringBuilder s;
    Symbol sym;
    Token t;

    s = new StringBuilder();
    do
    {
      s.append(currChar);
      getChar();
    } while (isLetter(currChar) || isDigit(currChar));

    id = (s.length() <= 20 ? s.toString() : s.substring(0, 20));
    sym = reservedWords.get(id.toLowerCase());
    if (sym == null)
      sym = Symbol.IDENT;
    else
      id = null;
    t = new Token(sym, id, 0);
    return t;
  }

  private String readNextLine()
  {
    String input;

    input = "";
    try
    {
      input = br.readLine();
    }
    catch (IOException e)
    {
      System.out.println(e);
    }

    if (input != null) input = input + "\n";

    return (input);
  }

  private Token readNumLit()
  {
    String num;
    StringBuilder s;
    Token t;

    s = new StringBuilder();
    do
    {
      s.append(currChar);
      getChar();
    } while (isDigit(currChar));

    num = (s.length() <= 10 ? s.toString() : s.substring(0, 10));
    t = new Token(Symbol.NUMLIT, null, Integer.parseInt(num));
    return t;
  }

  private Token readOthers()
  {
    char ch;
    Symbol sym;
    Token t;

    ch = currChar;
    getChar();
    switch (ch)
    {
      case '+': sym = Symbol.PLUS;
                break;
      case '-': sym = Symbol.MINUS;
                break;
      case '*': sym = Symbol.TIMES;
                break;
      case '=': sym = Symbol.EQL;
                break;
      case '(': sym = Symbol.LPAREN;
                break;
      case ')': sym = Symbol.RPAREN;
                break;
      case ',': sym = Symbol.COMMA;
                break;
      case ';': sym = Symbol.SEMICOLON;
                break;
      case '/': if (currChar == '=')
                {
                  sym = Symbol.NEQ;
                  getChar();
                }
                else
                  sym = Symbol.SLASH;
                break;
      case ':': if (currChar == '=')
                {
                  sym = Symbol.BECOMES;
                  getChar();
                }
                else
                  sym = Symbol.COLON;
                break;
      case '<': if (currChar == '=')
                {
                  sym = Symbol.LEQ;
                  getChar();
                }
                else
                  sym = Symbol.LSS;
                break;
      case '>': if (currChar == '=')
                {
                  sym = Symbol.GEQ;
                  getChar();
                }
                else
                  sym = Symbol.GTR;
                break;
      default: sym = Symbol.NUL;
               break;
    }

    t = new Token(sym, null, 0);
    return t;
  }

  private void setUpReservedWordMap()
  {
    reservedWords = new HashMap<>();

    reservedWords.put("true", Symbol.TRUESYM);
    reservedWords.put("false", Symbol.FALSESYM);
    reservedWords.put("not", Symbol.NOTSYM);
    reservedWords.put("rem", Symbol.REMSYM);
    reservedWords.put("begin", Symbol.BEGINSYM);
    reservedWords.put("end", Symbol.ENDSYM);
    reservedWords.put("if", Symbol.IFSYM);
    reservedWords.put("then", Symbol.THENSYM);
    reservedWords.put("else", Symbol.ELSESYM);
    reservedWords.put("while", Symbol.WHILESYM);
    reservedWords.put("loop", Symbol.LOOPSYM);
    reservedWords.put("get", Symbol.GETSYM);
    reservedWords.put("put", Symbol.PUTSYM);
    reservedWords.put("newline", Symbol.NEWLINE);
    reservedWords.put("null", Symbol.NULLSYM);
    reservedWords.put("boolean", Symbol.BOOLSYM);
    reservedWords.put("integer", Symbol.INTSYM);
    reservedWords.put("is", Symbol.ISSYM);
    reservedWords.put("procedure", Symbol.PROCSYM);
  }
}