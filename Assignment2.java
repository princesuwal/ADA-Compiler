
public class Assignment2 {
	public static void main(String args[]) {
		if(args.length == 0) {
			System.out.println("Requires .ada file");
			System.exit(0);
		}
		
		LexicalAnalyzer la = new LexicalAnalyzer(args[0]);
		Parser parser = new Parser(la);
		parser.START();
	}
}
