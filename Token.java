public class Token
{
  private Symbol sym; 
  private String id;
  private int num;

  public Token(Symbol s, String i, int n)
  {
    sym = s;
    id = i;
    num = n;
  }

  public Symbol getSym()
  {
    return sym;
  }

  public String getId()
  {
    return id;
  }

  public int getNum()
  {
    return num;
  }

  public void setSym(Symbol s)
  {
    sym = s;
  }

  public void setId(String i)
  {
    id = i;
  }

  public void setNum(int n)
  {
    num = n;
  }

  public String toString()
  {
    return ("Symbol: " + sym + ", id: " + id + ", num: " + num); 
  }
}