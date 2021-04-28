
public class Parser {
	
	private LexicalAnalyzer la;
	private Token token;
	private Errors errors;
	
	public Parser(LexicalAnalyzer la) {
		this.la = la;
		errors = new Errors();
		this.token = la.getToken();
	}
	
	public void START() {
		match(Symbol.PROCSYM, 101);
		match(Symbol.IDENT, 102);
		match(Symbol.ISSYM, 103);
			DECPART();
		match(Symbol.BEGINSYM, 104);
			if(this.token.getSym() == Symbol.ENDSYM) {
				errors.error(109, la.getCurrCharPosNum());
			}
			SEQOFSTMT();
		match(Symbol.ENDSYM, 105);
		match(Symbol.SEMICOLON, 106);
			if(this.token.getSym() == Symbol.EOI) {
				System.out.print("Program Syntatically Correct!!");
			}else {
				errors.error(107, 0);
			}
	}
	
	private void DECPART() {
		while(this.token.getSym() == Symbol.IDENT) {
			OBJECTDEC();
		}
	}
	
	private void OBJECTDEC() {
		this.token = la.getToken();
		while(this.token.getSym() == Symbol.COMMA) {
			this.token = la.getToken();
			if(this.token.getSym() == Symbol.IDENT) {
				this.token = la.getToken();
			}else {
				errors.error(102, la.getCurrCharPosNum());
			}
		}
		
		if(this.token.getSym() == Symbol.COLON) {
			this.token = la.getToken();
			if(this.token.getSym() == Symbol.BOOLSYM || this.token.getSym() == Symbol.INTSYM) {
				this.token = la.getToken();
				if(this.token.getSym() == Symbol.SEMICOLON) {
					this.token = la.getToken();
				}else {
					errors.error(106, la.getCurrCharPosNum());
				}
			}else {
				errors.error(108, la.getCurrCharPosNum());
			}
		}else {
			errors.error(112, la.getCurrCharPosNum());
		}
	}
	
	
	private void SEQOFSTMT() {
		while(this.token.getSym() == Symbol.NULLSYM ||
				this.token.getSym() == Symbol.IDENT ||
				this.token.getSym() == Symbol.IFSYM ||
				this.token.getSym() == Symbol.WHILESYM ||
				this.token.getSym() == Symbol.GETSYM ||
				this.token.getSym() == Symbol.PUTSYM ||
				this.token.getSym() == Symbol.NEWLINE) {
			STATEMENT();
		}
	}
	
	private void IDENTLIST() {
		if(this.token.getSym() == Symbol.IDENT) {
			this.token = la.getToken();
			while(this.token.getSym() == Symbol.COMMA) {
				this.token = la.getToken();
				if(this.token.getSym() == Symbol.IDENT) {
					this.token = la.getToken();
				}else {
					errors.error(102, la.getCurrCharPosNum());
				}
			}
		}
	}
	
	private void STATEMENT() {
		if(this.token.getSym() == Symbol.NULLSYM) {
			this.token = la.getToken();
			match(Symbol.SEMICOLON, 106);
		}else if(this.token.getSym() == Symbol.IDENT){
			this.token = la.getToken();
			match(Symbol.BECOMES , 113);
			EXPRESSION();
			match(Symbol.SEMICOLON, 106);
		}else if(this.token.getSym() == Symbol.IFSYM) {
			this.token = la.getToken();
			CONDITION();
			match(Symbol.THENSYM, 114);
			SEQOFSTMT();
			if(this.token.getSym() == Symbol.ELSESYM) {
				this.token = la.getToken();
				SEQOFSTMT();
			}
			match(Symbol.ENDSYM, 105);
			match(Symbol.IFSYM, 115);
			match(Symbol.SEMICOLON, 106);
		}else if(this.token.getSym() == Symbol.WHILESYM) {
			this.token = la.getToken();
			CONDITION();
			match(Symbol.LOOPSYM, 116);
			SEQOFSTMT();
			match(Symbol.ENDSYM, 105);
			match(Symbol.LOOPSYM, 116);
			match(Symbol.SEMICOLON, 106);
		}else if(this.token.getSym() == Symbol.GETSYM) {
				this.token = la.getToken();
				match(Symbol.LPAREN, 110);
				IDENTLIST();
				match(Symbol.RPAREN, 111);
				match(Symbol.SEMICOLON, 106);
		}else if(this.token.getSym() == Symbol.PUTSYM){
			this.token = la.getToken();
			match(Symbol.LPAREN, 110);
			IDENTLIST();
			match(Symbol.RPAREN, 111);
			match(Symbol.SEMICOLON, 106);
		}else if(this.token.getSym() == Symbol.NEWLINE) {
			this.token = la.getToken();
			match(Symbol.SEMICOLON, 106);
		}else {
			errors.error(109, la.getCurrCharPosNum());
		}
	}
	
	private void CONDITION() {
		EXPRESSION();
	}
	
	private void EXPRESSION() {
		SIMPEXPR();
		if(this.token.getSym() == Symbol.EQL || this.token.getSym() == Symbol.NEQ || this.token.getSym() == Symbol.LSS ||
				this.token.getSym() == Symbol.LEQ || this.token.getSym() == Symbol.GTR || this.token.getSym() == Symbol.GEQ) {
			this.token = la.getToken();
			SIMPEXPR();
		}
	}
	
	private void SIMPEXPR() {
		TERM();
		while(this.token.getSym() == Symbol.PLUS || this.token.getSym() == Symbol.MINUS) {
			this.token = la.getToken();
			TERM();
		}
	}
	
	private void TERM() {
		PRIMARY();
		while(this.token.getSym() == Symbol.TIMES || this.token.getSym() == Symbol.SLASH || this.token.getSym() == Symbol.REMSYM) {
			this.token = la.getToken();
			PRIMARY();
		}
	}
	
	private void PRIMARY() {
		if(this.token.getSym() == Symbol.LPAREN) {
			this.token = la.getToken();
			EXPRESSION();
			match(Symbol.RPAREN, 111);
		}else if(this.token.getSym() == Symbol.IDENT || this.token.getSym() == Symbol.NUMLIT || 
				this.token.getSym() == Symbol.TRUESYM || this.token.getSym() == Symbol.FALSESYM) {
			this.token = la.getToken();
		}else if(this.token.getSym() == Symbol.RPAREN){
			errors.error(110, la.getCurrCharPosNum());
		}else if(this.token.getSym() == Symbol.TIMES || this.token.getSym() == Symbol.SLASH ||
				this.token.getSym() == Symbol.REMSYM){
			errors.error(118, la.getCurrCharPosNum());
		}else if(this.token.getSym() == Symbol.PLUS || this.token.getSym() == Symbol.MINUS) {
			errors.error(118, la.getCurrCharPosNum());
		}else if(this.token.getSym() == Symbol.EQL || this.token.getSym() == Symbol.NEQ || this.token.getSym() == Symbol.LSS ||
				this.token.getSym() == Symbol.LEQ || this.token.getSym() == Symbol.GTR || this.token.getSym() == Symbol.GEQ) {
			errors.error(118, la.getCurrCharPosNum());
		}else{
			errors.error(118, la.getCurrCharPosNum());
		}
	}
	
	private void match(Symbol s, int code) {
		if(this.token.getSym() == s) {
			this.token = la.getToken();
		}else {
			errors.error(code, la.getCurrCharPosNum());
		}
	}
}
