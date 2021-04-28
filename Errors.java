public class Errors{
	
	public void error(int code, int position){
		switch(code) {
			case 101:
				PrintWhiteSpace(position);
				System.out.print("\"PROCSYM\" needed at start of code");
				System.exit(0);
				
			case 102:
				PrintWhiteSpace(position);
				System.out.print("\"IDENT\" missing");
				System.exit(0);
			
			case 103:
				PrintWhiteSpace(position);
				System.out.print("\"ISSYM\" required");
				System.exit(0);
				
			case 104:
				PrintWhiteSpace(position);
				System.out.print("\"BEGINSYM\" required" );
				System.exit(0);
				
			case 105:
				PrintWhiteSpace(position);
				System.out.print("\"ENDSYM\" required");
				System.exit(0);
				
			case 106:
				PrintWhiteSpace(position);
				System.out.print("\"SEMICOLON\" required");
				System.exit(0);
				
			case 107:
				PrintWhiteSpace(position);
				System.out.print("Did not reach EOI");
				System.exit(0);
				
			case 108:
				PrintWhiteSpace(position);
				System.out.print("Expected \"BOOLSYM\" or \"INTSYM\"");
				System.exit(0);
				
			case 109:
				PrintWhiteSpace(position);
				System.out.print("Missing \"SEQ OF STMT(s)\"");
				System.exit(0);
				
			case 110:
				PrintWhiteSpace(position);
				System.out.print("Expected \"LPAREN\"");
				System.exit(0);
				
			case 111:
				PrintWhiteSpace(position);
				System.out.print("Missing \"RPAREN\"");
				System.exit(0);
				
			case 112:
				PrintWhiteSpace(position);
				System.out.print("Expected \"COLON\"");
				System.exit(0);
				
			case 113:
				PrintWhiteSpace(position);
				System.out.print("\"BECOMES\" needed for STATEMENT");
				System.exit(0);
			
			case 114:
				PrintWhiteSpace(position);
				System.out.print("\"THEN\" required after IFSYM");
				System.exit(0);
				
			case 115:
				PrintWhiteSpace(position);
				System.out.print("Missing \"IF\"");
				System.exit(0);
				
			case 116:
				PrintWhiteSpace(position);
				System.out.print("Requires \"LOOP\" & \"END LOOP\" inside WHEN");
				System.exit(0);
				
			case 117:
				PrintWhiteSpace(position);
				System.out.print("Expected \"PUT\" or \"GET\"");
				System.exit(0);
				
			case 118:
				PrintWhiteSpace(position);
				System.out.print("Expected \"RPARAN\" OR \"IDENT, NUMLIT, TRUESYM, FALSESYM\" for STATEMENT");
				System.exit(0);
		}
	}

	private void PrintWhiteSpace(int position){
		for(int i=0; i <= position; i++){
			System.out.print(" ");
		}
		System.out.print("^ ");
	}
}