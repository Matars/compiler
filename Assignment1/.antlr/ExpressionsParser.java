// Generated from /Users/matar/School/compiler/Assignment1/Expressions.g4 by ANTLR 4.13.1
    // Define name of package for generated Java files. 
    package generated;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class ExpressionsParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, T__29=30, INT=31, FLOAT=32, 
		BOOL=33, ID=34, STRING=35, CHAR=36, WS=37, TYPE=38, COMMENT=39;
	public static final int
		RULE_start = 0, RULE_maindef = 1, RULE_stmt = 2, RULE_expr = 3, RULE_stmtBlock = 4, 
		RULE_exprBlock = 5, RULE_argBlock = 6, RULE_exprList = 7, RULE_newArray = 8, 
		RULE_declare = 9, RULE_assign = 10, RULE_ifstmt = 11, RULE_elsePart = 12, 
		RULE_whilestmt = 13, RULE_returnstmt = 14, RULE_methodCall = 15, RULE_methodDef = 16, 
		RULE_print = 17, RULE_println = 18;
	private static String[] makeRuleNames() {
		return new String[] {
			"start", "maindef", "stmt", "expr", "stmtBlock", "exprBlock", "argBlock", 
			"exprList", "newArray", "declare", "assign", "ifstmt", "elsePart", "whilestmt", 
			"returnstmt", "methodCall", "methodDef", "print", "println"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'void main'", "'('", "','", "')'", "'{'", "'}'", "';'", "'*'", 
			"'/'", "'+'", "'-'", "'>'", "'<'", "'>='", "'<='", "'=='", "'!='", "'['", 
			"']'", "'.'", "'length'", "'new'", "'='", "'if'", "'else'", "'while'", 
			"'return'", "'print'", "'\"'", "'println'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, "INT", "FLOAT", "BOOL", "ID", 
			"STRING", "CHAR", "WS", "TYPE", "COMMENT"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "Expressions.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public ExpressionsParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StartContext extends ParserRuleContext {
		public MaindefContext maindef() {
			return getRuleContext(MaindefContext.class,0);
		}
		public List<MethodDefContext> methodDef() {
			return getRuleContexts(MethodDefContext.class);
		}
		public MethodDefContext methodDef(int i) {
			return getRuleContext(MethodDefContext.class,i);
		}
		public StartContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_start; }
	}

	public final StartContext start() throws RecognitionException {
		StartContext _localctx = new StartContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_start);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(41);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==TYPE) {
				{
				{
				setState(38);
				methodDef();
				}
				}
				setState(43);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(44);
			maindef();
			setState(48);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==TYPE) {
				{
				{
				setState(45);
				methodDef();
				}
				}
				setState(50);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class MaindefContext extends ParserRuleContext {
		public List<TerminalNode> TYPE() { return getTokens(ExpressionsParser.TYPE); }
		public TerminalNode TYPE(int i) {
			return getToken(ExpressionsParser.TYPE, i);
		}
		public List<TerminalNode> ID() { return getTokens(ExpressionsParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(ExpressionsParser.ID, i);
		}
		public List<StmtContext> stmt() {
			return getRuleContexts(StmtContext.class);
		}
		public StmtContext stmt(int i) {
			return getRuleContext(StmtContext.class,i);
		}
		public MaindefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_maindef; }
	}

	public final MaindefContext maindef() throws RecognitionException {
		MaindefContext _localctx = new MaindefContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_maindef);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(51);
			match(T__0);
			setState(52);
			match(T__1);
			setState(63);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==TYPE) {
				{
				setState(53);
				match(TYPE);
				setState(54);
				match(ID);
				setState(60);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__2) {
					{
					{
					setState(55);
					match(T__2);
					setState(56);
					match(TYPE);
					setState(57);
					match(ID);
					}
					}
					setState(62);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(65);
			match(T__3);
			setState(66);
			match(T__4);
			setState(70);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 411729657860L) != 0)) {
				{
				{
				setState(67);
				stmt();
				}
				}
				setState(72);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(73);
			match(T__5);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StmtContext extends ParserRuleContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public AssignContext assign() {
			return getRuleContext(AssignContext.class,0);
		}
		public DeclareContext declare() {
			return getRuleContext(DeclareContext.class,0);
		}
		public IfstmtContext ifstmt() {
			return getRuleContext(IfstmtContext.class,0);
		}
		public WhilestmtContext whilestmt() {
			return getRuleContext(WhilestmtContext.class,0);
		}
		public PrintContext print() {
			return getRuleContext(PrintContext.class,0);
		}
		public PrintlnContext println() {
			return getRuleContext(PrintlnContext.class,0);
		}
		public MethodDefContext methodDef() {
			return getRuleContext(MethodDefContext.class,0);
		}
		public MethodCallContext methodCall() {
			return getRuleContext(MethodCallContext.class,0);
		}
		public ReturnstmtContext returnstmt() {
			return getRuleContext(ReturnstmtContext.class,0);
		}
		public StmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stmt; }
	}

	public final StmtContext stmt() throws RecognitionException {
		StmtContext _localctx = new StmtContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_stmt);
		try {
			setState(87);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(75);
				expr(0);
				setState(76);
				match(T__6);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(78);
				assign();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(79);
				declare();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(80);
				ifstmt();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(81);
				whilestmt();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(82);
				print();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(83);
				println();
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(84);
				methodDef();
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(85);
				methodCall();
				}
				break;
			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(86);
				returnstmt();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ExprContext extends ParserRuleContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode INT() { return getToken(ExpressionsParser.INT, 0); }
		public TerminalNode FLOAT() { return getToken(ExpressionsParser.FLOAT, 0); }
		public TerminalNode BOOL() { return getToken(ExpressionsParser.BOOL, 0); }
		public TerminalNode ID() { return getToken(ExpressionsParser.ID, 0); }
		public TerminalNode STRING() { return getToken(ExpressionsParser.STRING, 0); }
		public TerminalNode CHAR() { return getToken(ExpressionsParser.CHAR, 0); }
		public MethodCallContext methodCall() {
			return getRuleContext(MethodCallContext.class,0);
		}
		public ExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr; }
	}

	public final ExprContext expr() throws RecognitionException {
		return expr(0);
	}

	private ExprContext expr(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExprContext _localctx = new ExprContext(_ctx, _parentState);
		ExprContext _prevctx = _localctx;
		int _startState = 6;
		enterRecursionRule(_localctx, 6, RULE_expr, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(104);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,6,_ctx) ) {
			case 1:
				{
				setState(90);
				match(T__1);
				setState(91);
				expr(0);
				setState(92);
				match(T__3);
				}
				break;
			case 2:
				{
				setState(94);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 135291469824L) != 0)) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
				break;
			case 3:
				{
				setState(95);
				match(ID);
				setState(96);
				match(T__17);
				setState(97);
				expr(0);
				setState(98);
				match(T__18);
				}
				break;
			case 4:
				{
				setState(100);
				match(ID);
				setState(101);
				match(T__19);
				setState(102);
				match(T__20);
				}
				break;
			case 5:
				{
				setState(103);
				methodCall();
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(117);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,8,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(115);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
					case 1:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(106);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(107);
						_la = _input.LA(1);
						if ( !(_la==T__7 || _la==T__8) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(108);
						expr(8);
						}
						break;
					case 2:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(109);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(110);
						_la = _input.LA(1);
						if ( !(_la==T__9 || _la==T__10) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(111);
						expr(7);
						}
						break;
					case 3:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(112);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(113);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 258048L) != 0)) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(114);
						expr(6);
						}
						break;
					}
					} 
				}
				setState(119);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,8,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StmtBlockContext extends ParserRuleContext {
		public List<StmtContext> stmt() {
			return getRuleContexts(StmtContext.class);
		}
		public StmtContext stmt(int i) {
			return getRuleContext(StmtContext.class,i);
		}
		public StmtBlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stmtBlock; }
	}

	public final StmtBlockContext stmtBlock() throws RecognitionException {
		StmtBlockContext _localctx = new StmtBlockContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_stmtBlock);
		try {
			int _alt;
			setState(133);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,12,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(121);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
				case 1:
					{
					setState(120);
					match(T__4);
					}
					break;
				}
				setState(126);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,10,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(123);
						stmt();
						}
						} 
					}
					setState(128);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,10,_ctx);
				}
				setState(130);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
				case 1:
					{
					setState(129);
					match(T__5);
					}
					break;
				}
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(132);
				stmt();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ExprBlockContext extends ParserRuleContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public ExprBlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exprBlock; }
	}

	public final ExprBlockContext exprBlock() throws RecognitionException {
		ExprBlockContext _localctx = new ExprBlockContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_exprBlock);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(136);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__4) {
				{
				setState(135);
				match(T__4);
				}
			}

			setState(141);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 410169376772L) != 0)) {
				{
				{
				setState(138);
				expr(0);
				}
				}
				setState(143);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(145);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__5) {
				{
				setState(144);
				match(T__5);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ArgBlockContext extends ParserRuleContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public ArgBlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_argBlock; }
	}

	public final ArgBlockContext argBlock() throws RecognitionException {
		ArgBlockContext _localctx = new ArgBlockContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_argBlock);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(147);
			match(T__1);
			setState(148);
			expr(0);
			setState(149);
			match(T__3);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ExprListContext extends ParserRuleContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public ExprListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exprList; }
	}

	public final ExprListContext exprList() throws RecognitionException {
		ExprListContext _localctx = new ExprListContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_exprList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(151);
			expr(0);
			setState(156);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__2) {
				{
				{
				setState(152);
				match(T__2);
				setState(153);
				expr(0);
				}
				}
				setState(158);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class NewArrayContext extends ParserRuleContext {
		public TerminalNode TYPE() { return getToken(ExpressionsParser.TYPE, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public NewArrayContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_newArray; }
	}

	public final NewArrayContext newArray() throws RecognitionException {
		NewArrayContext _localctx = new NewArrayContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_newArray);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(159);
			match(T__21);
			setState(160);
			match(TYPE);
			setState(161);
			match(T__17);
			setState(162);
			expr(0);
			setState(163);
			match(T__18);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class DeclareContext extends ParserRuleContext {
		public TerminalNode TYPE() { return getToken(ExpressionsParser.TYPE, 0); }
		public TerminalNode ID() { return getToken(ExpressionsParser.ID, 0); }
		public DeclareContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declare; }
	}

	public final DeclareContext declare() throws RecognitionException {
		DeclareContext _localctx = new DeclareContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_declare);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(165);
			match(TYPE);
			setState(166);
			match(ID);
			setState(169);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__17) {
				{
				setState(167);
				match(T__17);
				setState(168);
				match(T__18);
				}
			}

			setState(171);
			match(T__6);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AssignContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(ExpressionsParser.ID, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public NewArrayContext newArray() {
			return getRuleContext(NewArrayContext.class,0);
		}
		public ExprListContext exprList() {
			return getRuleContext(ExprListContext.class,0);
		}
		public TerminalNode TYPE() { return getToken(ExpressionsParser.TYPE, 0); }
		public AssignContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assign; }
	}

	public final AssignContext assign() throws RecognitionException {
		AssignContext _localctx = new AssignContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_assign);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(174);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==TYPE) {
				{
				setState(173);
				match(TYPE);
				}
			}

			setState(176);
			match(ID);
			setState(181);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__17) {
				{
				setState(177);
				match(T__17);
				setState(178);
				expr(0);
				setState(179);
				match(T__18);
				}
			}

			setState(183);
			match(T__22);
			setState(190);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__1:
			case INT:
			case FLOAT:
			case BOOL:
			case ID:
			case STRING:
			case CHAR:
			case TYPE:
				{
				setState(184);
				expr(0);
				}
				break;
			case T__21:
				{
				setState(185);
				newArray();
				}
				break;
			case T__4:
				{
				setState(186);
				match(T__4);
				setState(187);
				exprList();
				setState(188);
				match(T__5);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(192);
			match(T__6);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class IfstmtContext extends ParserRuleContext {
		public ArgBlockContext argBlock() {
			return getRuleContext(ArgBlockContext.class,0);
		}
		public StmtBlockContext stmtBlock() {
			return getRuleContext(StmtBlockContext.class,0);
		}
		public ElsePartContext elsePart() {
			return getRuleContext(ElsePartContext.class,0);
		}
		public IfstmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ifstmt; }
	}

	public final IfstmtContext ifstmt() throws RecognitionException {
		IfstmtContext _localctx = new IfstmtContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_ifstmt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(194);
			match(T__23);
			setState(195);
			argBlock();
			setState(196);
			stmtBlock();
			setState(198);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,21,_ctx) ) {
			case 1:
				{
				setState(197);
				elsePart();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ElsePartContext extends ParserRuleContext {
		public StmtBlockContext stmtBlock() {
			return getRuleContext(StmtBlockContext.class,0);
		}
		public IfstmtContext ifstmt() {
			return getRuleContext(IfstmtContext.class,0);
		}
		public ElsePartContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_elsePart; }
	}

	public final ElsePartContext elsePart() throws RecognitionException {
		ElsePartContext _localctx = new ElsePartContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_elsePart);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(200);
			match(T__24);
			setState(202);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,22,_ctx) ) {
			case 1:
				{
				setState(201);
				ifstmt();
				}
				break;
			}
			setState(204);
			stmtBlock();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class WhilestmtContext extends ParserRuleContext {
		public ArgBlockContext argBlock() {
			return getRuleContext(ArgBlockContext.class,0);
		}
		public StmtBlockContext stmtBlock() {
			return getRuleContext(StmtBlockContext.class,0);
		}
		public WhilestmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_whilestmt; }
	}

	public final WhilestmtContext whilestmt() throws RecognitionException {
		WhilestmtContext _localctx = new WhilestmtContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_whilestmt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(206);
			match(T__25);
			setState(207);
			argBlock();
			setState(208);
			stmtBlock();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ReturnstmtContext extends ParserRuleContext {
		public List<StmtContext> stmt() {
			return getRuleContexts(StmtContext.class);
		}
		public StmtContext stmt(int i) {
			return getRuleContext(StmtContext.class,i);
		}
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public ReturnstmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_returnstmt; }
	}

	public final ReturnstmtContext returnstmt() throws RecognitionException {
		ReturnstmtContext _localctx = new ReturnstmtContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_returnstmt);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(210);
			match(T__26);
			setState(215);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,24,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					setState(213);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,23,_ctx) ) {
					case 1:
						{
						setState(211);
						stmt();
						}
						break;
					case 2:
						{
						setState(212);
						expr(0);
						}
						break;
					}
					} 
				}
				setState(217);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,24,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class MethodCallContext extends ParserRuleContext {
		public List<TerminalNode> ID() { return getTokens(ExpressionsParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(ExpressionsParser.ID, i);
		}
		public ExprListContext exprList() {
			return getRuleContext(ExprListContext.class,0);
		}
		public TerminalNode TYPE() { return getToken(ExpressionsParser.TYPE, 0); }
		public MethodCallContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_methodCall; }
	}

	public final MethodCallContext methodCall() throws RecognitionException {
		MethodCallContext _localctx = new MethodCallContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_methodCall);
		int _la;
		try {
			setState(241);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,30,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(218);
				match(ID);
				setState(219);
				match(T__1);
				setState(221);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 410169376772L) != 0)) {
					{
					setState(220);
					exprList();
					}
				}

				setState(223);
				match(T__3);
				setState(225);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,26,_ctx) ) {
				case 1:
					{
					setState(224);
					match(T__6);
					}
					break;
				}
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(228);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==TYPE) {
					{
					setState(227);
					match(TYPE);
					}
				}

				setState(230);
				match(ID);
				setState(231);
				match(T__22);
				setState(232);
				match(ID);
				setState(233);
				match(T__1);
				setState(235);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 410169376772L) != 0)) {
					{
					setState(234);
					exprList();
					}
				}

				setState(237);
				match(T__3);
				setState(239);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,29,_ctx) ) {
				case 1:
					{
					setState(238);
					match(T__6);
					}
					break;
				}
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class MethodDefContext extends ParserRuleContext {
		public List<TerminalNode> TYPE() { return getTokens(ExpressionsParser.TYPE); }
		public TerminalNode TYPE(int i) {
			return getToken(ExpressionsParser.TYPE, i);
		}
		public List<TerminalNode> ID() { return getTokens(ExpressionsParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(ExpressionsParser.ID, i);
		}
		public List<StmtContext> stmt() {
			return getRuleContexts(StmtContext.class);
		}
		public StmtContext stmt(int i) {
			return getRuleContext(StmtContext.class,i);
		}
		public MethodDefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_methodDef; }
	}

	public final MethodDefContext methodDef() throws RecognitionException {
		MethodDefContext _localctx = new MethodDefContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_methodDef);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(243);
			match(TYPE);
			setState(244);
			match(ID);
			setState(245);
			match(T__1);
			setState(256);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==TYPE) {
				{
				setState(246);
				match(TYPE);
				setState(247);
				match(ID);
				setState(253);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__2) {
					{
					{
					setState(248);
					match(T__2);
					setState(249);
					match(TYPE);
					setState(250);
					match(ID);
					}
					}
					setState(255);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(258);
			match(T__3);
			setState(259);
			match(T__4);
			setState(263);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 411729657860L) != 0)) {
				{
				{
				setState(260);
				stmt();
				}
				}
				setState(265);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(266);
			match(T__5);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PrintContext extends ParserRuleContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public PrintContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_print; }
	}

	public final PrintContext print() throws RecognitionException {
		PrintContext _localctx = new PrintContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_print);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(268);
			match(T__27);
			setState(269);
			match(T__1);
			setState(271);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,34,_ctx) ) {
			case 1:
				{
				setState(270);
				match(T__28);
				}
				break;
			}
			setState(274);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 410169376772L) != 0)) {
				{
				setState(273);
				expr(0);
				}
			}

			setState(277);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__28) {
				{
				setState(276);
				match(T__28);
				}
			}

			setState(279);
			match(T__3);
			setState(280);
			match(T__6);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PrintlnContext extends ParserRuleContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public PrintlnContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_println; }
	}

	public final PrintlnContext println() throws RecognitionException {
		PrintlnContext _localctx = new PrintlnContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_println);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(282);
			match(T__29);
			setState(283);
			match(T__1);
			setState(285);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,37,_ctx) ) {
			case 1:
				{
				setState(284);
				match(T__28);
				}
				break;
			}
			setState(288);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 410169376772L) != 0)) {
				{
				setState(287);
				expr(0);
				}
			}

			setState(291);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__28) {
				{
				setState(290);
				match(T__28);
				}
			}

			setState(293);
			match(T__3);
			setState(294);
			match(T__6);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 3:
			return expr_sempred((ExprContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expr_sempred(ExprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 7);
		case 1:
			return precpred(_ctx, 6);
		case 2:
			return precpred(_ctx, 5);
		}
		return true;
	}

	public static final String _serializedATN =
		"\u0004\u0001\'\u0129\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007\u000f"+
		"\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002\u0012\u0007\u0012"+
		"\u0001\u0000\u0005\u0000(\b\u0000\n\u0000\f\u0000+\t\u0000\u0001\u0000"+
		"\u0001\u0000\u0005\u0000/\b\u0000\n\u0000\f\u00002\t\u0000\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0005\u0001;\b\u0001\n\u0001\f\u0001>\t\u0001\u0003\u0001@\b\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0005\u0001E\b\u0001\n\u0001\f\u0001H\t"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0001\u0002\u0001"+
		"\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001"+
		"\u0002\u0001\u0002\u0001\u0002\u0003\u0002X\b\u0002\u0001\u0003\u0001"+
		"\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0001\u0003\u0003\u0003i\b\u0003\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0005\u0003t\b\u0003\n\u0003\f\u0003w\t\u0003\u0001\u0004\u0003"+
		"\u0004z\b\u0004\u0001\u0004\u0005\u0004}\b\u0004\n\u0004\f\u0004\u0080"+
		"\t\u0004\u0001\u0004\u0003\u0004\u0083\b\u0004\u0001\u0004\u0003\u0004"+
		"\u0086\b\u0004\u0001\u0005\u0003\u0005\u0089\b\u0005\u0001\u0005\u0005"+
		"\u0005\u008c\b\u0005\n\u0005\f\u0005\u008f\t\u0005\u0001\u0005\u0003\u0005"+
		"\u0092\b\u0005\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0005\u0007\u009b\b\u0007\n\u0007\f\u0007\u009e"+
		"\t\u0007\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\t\u0001"+
		"\t\u0001\t\u0001\t\u0003\t\u00aa\b\t\u0001\t\u0001\t\u0001\n\u0003\n\u00af"+
		"\b\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0003\n\u00b6\b\n\u0001\n"+
		"\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0003\n\u00bf\b\n\u0001"+
		"\n\u0001\n\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0003\u000b"+
		"\u00c7\b\u000b\u0001\f\u0001\f\u0003\f\u00cb\b\f\u0001\f\u0001\f\u0001"+
		"\r\u0001\r\u0001\r\u0001\r\u0001\u000e\u0001\u000e\u0001\u000e\u0005\u000e"+
		"\u00d6\b\u000e\n\u000e\f\u000e\u00d9\t\u000e\u0001\u000f\u0001\u000f\u0001"+
		"\u000f\u0003\u000f\u00de\b\u000f\u0001\u000f\u0001\u000f\u0003\u000f\u00e2"+
		"\b\u000f\u0001\u000f\u0003\u000f\u00e5\b\u000f\u0001\u000f\u0001\u000f"+
		"\u0001\u000f\u0001\u000f\u0001\u000f\u0003\u000f\u00ec\b\u000f\u0001\u000f"+
		"\u0001\u000f\u0003\u000f\u00f0\b\u000f\u0003\u000f\u00f2\b\u000f\u0001"+
		"\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001"+
		"\u0010\u0001\u0010\u0005\u0010\u00fc\b\u0010\n\u0010\f\u0010\u00ff\t\u0010"+
		"\u0003\u0010\u0101\b\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0005\u0010"+
		"\u0106\b\u0010\n\u0010\f\u0010\u0109\t\u0010\u0001\u0010\u0001\u0010\u0001"+
		"\u0011\u0001\u0011\u0001\u0011\u0003\u0011\u0110\b\u0011\u0001\u0011\u0003"+
		"\u0011\u0113\b\u0011\u0001\u0011\u0003\u0011\u0116\b\u0011\u0001\u0011"+
		"\u0001\u0011\u0001\u0011\u0001\u0012\u0001\u0012\u0001\u0012\u0003\u0012"+
		"\u011e\b\u0012\u0001\u0012\u0003\u0012\u0121\b\u0012\u0001\u0012\u0003"+
		"\u0012\u0124\b\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0000"+
		"\u0001\u0006\u0013\u0000\u0002\u0004\u0006\b\n\f\u000e\u0010\u0012\u0014"+
		"\u0016\u0018\u001a\u001c\u001e \"$\u0000\u0004\u0001\u0000\u001f$\u0001"+
		"\u0000\b\t\u0001\u0000\n\u000b\u0001\u0000\f\u0011\u014a\u0000)\u0001"+
		"\u0000\u0000\u0000\u00023\u0001\u0000\u0000\u0000\u0004W\u0001\u0000\u0000"+
		"\u0000\u0006h\u0001\u0000\u0000\u0000\b\u0085\u0001\u0000\u0000\u0000"+
		"\n\u0088\u0001\u0000\u0000\u0000\f\u0093\u0001\u0000\u0000\u0000\u000e"+
		"\u0097\u0001\u0000\u0000\u0000\u0010\u009f\u0001\u0000\u0000\u0000\u0012"+
		"\u00a5\u0001\u0000\u0000\u0000\u0014\u00ae\u0001\u0000\u0000\u0000\u0016"+
		"\u00c2\u0001\u0000\u0000\u0000\u0018\u00c8\u0001\u0000\u0000\u0000\u001a"+
		"\u00ce\u0001\u0000\u0000\u0000\u001c\u00d2\u0001\u0000\u0000\u0000\u001e"+
		"\u00f1\u0001\u0000\u0000\u0000 \u00f3\u0001\u0000\u0000\u0000\"\u010c"+
		"\u0001\u0000\u0000\u0000$\u011a\u0001\u0000\u0000\u0000&(\u0003 \u0010"+
		"\u0000\'&\u0001\u0000\u0000\u0000(+\u0001\u0000\u0000\u0000)\'\u0001\u0000"+
		"\u0000\u0000)*\u0001\u0000\u0000\u0000*,\u0001\u0000\u0000\u0000+)\u0001"+
		"\u0000\u0000\u0000,0\u0003\u0002\u0001\u0000-/\u0003 \u0010\u0000.-\u0001"+
		"\u0000\u0000\u0000/2\u0001\u0000\u0000\u00000.\u0001\u0000\u0000\u0000"+
		"01\u0001\u0000\u0000\u00001\u0001\u0001\u0000\u0000\u000020\u0001\u0000"+
		"\u0000\u000034\u0005\u0001\u0000\u00004?\u0005\u0002\u0000\u000056\u0005"+
		"&\u0000\u00006<\u0005\"\u0000\u000078\u0005\u0003\u0000\u000089\u0005"+
		"&\u0000\u00009;\u0005\"\u0000\u0000:7\u0001\u0000\u0000\u0000;>\u0001"+
		"\u0000\u0000\u0000<:\u0001\u0000\u0000\u0000<=\u0001\u0000\u0000\u0000"+
		"=@\u0001\u0000\u0000\u0000><\u0001\u0000\u0000\u0000?5\u0001\u0000\u0000"+
		"\u0000?@\u0001\u0000\u0000\u0000@A\u0001\u0000\u0000\u0000AB\u0005\u0004"+
		"\u0000\u0000BF\u0005\u0005\u0000\u0000CE\u0003\u0004\u0002\u0000DC\u0001"+
		"\u0000\u0000\u0000EH\u0001\u0000\u0000\u0000FD\u0001\u0000\u0000\u0000"+
		"FG\u0001\u0000\u0000\u0000GI\u0001\u0000\u0000\u0000HF\u0001\u0000\u0000"+
		"\u0000IJ\u0005\u0006\u0000\u0000J\u0003\u0001\u0000\u0000\u0000KL\u0003"+
		"\u0006\u0003\u0000LM\u0005\u0007\u0000\u0000MX\u0001\u0000\u0000\u0000"+
		"NX\u0003\u0014\n\u0000OX\u0003\u0012\t\u0000PX\u0003\u0016\u000b\u0000"+
		"QX\u0003\u001a\r\u0000RX\u0003\"\u0011\u0000SX\u0003$\u0012\u0000TX\u0003"+
		" \u0010\u0000UX\u0003\u001e\u000f\u0000VX\u0003\u001c\u000e\u0000WK\u0001"+
		"\u0000\u0000\u0000WN\u0001\u0000\u0000\u0000WO\u0001\u0000\u0000\u0000"+
		"WP\u0001\u0000\u0000\u0000WQ\u0001\u0000\u0000\u0000WR\u0001\u0000\u0000"+
		"\u0000WS\u0001\u0000\u0000\u0000WT\u0001\u0000\u0000\u0000WU\u0001\u0000"+
		"\u0000\u0000WV\u0001\u0000\u0000\u0000X\u0005\u0001\u0000\u0000\u0000"+
		"YZ\u0006\u0003\uffff\uffff\u0000Z[\u0005\u0002\u0000\u0000[\\\u0003\u0006"+
		"\u0003\u0000\\]\u0005\u0004\u0000\u0000]i\u0001\u0000\u0000\u0000^i\u0007"+
		"\u0000\u0000\u0000_`\u0005\"\u0000\u0000`a\u0005\u0012\u0000\u0000ab\u0003"+
		"\u0006\u0003\u0000bc\u0005\u0013\u0000\u0000ci\u0001\u0000\u0000\u0000"+
		"de\u0005\"\u0000\u0000ef\u0005\u0014\u0000\u0000fi\u0005\u0015\u0000\u0000"+
		"gi\u0003\u001e\u000f\u0000hY\u0001\u0000\u0000\u0000h^\u0001\u0000\u0000"+
		"\u0000h_\u0001\u0000\u0000\u0000hd\u0001\u0000\u0000\u0000hg\u0001\u0000"+
		"\u0000\u0000iu\u0001\u0000\u0000\u0000jk\n\u0007\u0000\u0000kl\u0007\u0001"+
		"\u0000\u0000lt\u0003\u0006\u0003\bmn\n\u0006\u0000\u0000no\u0007\u0002"+
		"\u0000\u0000ot\u0003\u0006\u0003\u0007pq\n\u0005\u0000\u0000qr\u0007\u0003"+
		"\u0000\u0000rt\u0003\u0006\u0003\u0006sj\u0001\u0000\u0000\u0000sm\u0001"+
		"\u0000\u0000\u0000sp\u0001\u0000\u0000\u0000tw\u0001\u0000\u0000\u0000"+
		"us\u0001\u0000\u0000\u0000uv\u0001\u0000\u0000\u0000v\u0007\u0001\u0000"+
		"\u0000\u0000wu\u0001\u0000\u0000\u0000xz\u0005\u0005\u0000\u0000yx\u0001"+
		"\u0000\u0000\u0000yz\u0001\u0000\u0000\u0000z~\u0001\u0000\u0000\u0000"+
		"{}\u0003\u0004\u0002\u0000|{\u0001\u0000\u0000\u0000}\u0080\u0001\u0000"+
		"\u0000\u0000~|\u0001\u0000\u0000\u0000~\u007f\u0001\u0000\u0000\u0000"+
		"\u007f\u0082\u0001\u0000\u0000\u0000\u0080~\u0001\u0000\u0000\u0000\u0081"+
		"\u0083\u0005\u0006\u0000\u0000\u0082\u0081\u0001\u0000\u0000\u0000\u0082"+
		"\u0083\u0001\u0000\u0000\u0000\u0083\u0086\u0001\u0000\u0000\u0000\u0084"+
		"\u0086\u0003\u0004\u0002\u0000\u0085y\u0001\u0000\u0000\u0000\u0085\u0084"+
		"\u0001\u0000\u0000\u0000\u0086\t\u0001\u0000\u0000\u0000\u0087\u0089\u0005"+
		"\u0005\u0000\u0000\u0088\u0087\u0001\u0000\u0000\u0000\u0088\u0089\u0001"+
		"\u0000\u0000\u0000\u0089\u008d\u0001\u0000\u0000\u0000\u008a\u008c\u0003"+
		"\u0006\u0003\u0000\u008b\u008a\u0001\u0000\u0000\u0000\u008c\u008f\u0001"+
		"\u0000\u0000\u0000\u008d\u008b\u0001\u0000\u0000\u0000\u008d\u008e\u0001"+
		"\u0000\u0000\u0000\u008e\u0091\u0001\u0000\u0000\u0000\u008f\u008d\u0001"+
		"\u0000\u0000\u0000\u0090\u0092\u0005\u0006\u0000\u0000\u0091\u0090\u0001"+
		"\u0000\u0000\u0000\u0091\u0092\u0001\u0000\u0000\u0000\u0092\u000b\u0001"+
		"\u0000\u0000\u0000\u0093\u0094\u0005\u0002\u0000\u0000\u0094\u0095\u0003"+
		"\u0006\u0003\u0000\u0095\u0096\u0005\u0004\u0000\u0000\u0096\r\u0001\u0000"+
		"\u0000\u0000\u0097\u009c\u0003\u0006\u0003\u0000\u0098\u0099\u0005\u0003"+
		"\u0000\u0000\u0099\u009b\u0003\u0006\u0003\u0000\u009a\u0098\u0001\u0000"+
		"\u0000\u0000\u009b\u009e\u0001\u0000\u0000\u0000\u009c\u009a\u0001\u0000"+
		"\u0000\u0000\u009c\u009d\u0001\u0000\u0000\u0000\u009d\u000f\u0001\u0000"+
		"\u0000\u0000\u009e\u009c\u0001\u0000\u0000\u0000\u009f\u00a0\u0005\u0016"+
		"\u0000\u0000\u00a0\u00a1\u0005&\u0000\u0000\u00a1\u00a2\u0005\u0012\u0000"+
		"\u0000\u00a2\u00a3\u0003\u0006\u0003\u0000\u00a3\u00a4\u0005\u0013\u0000"+
		"\u0000\u00a4\u0011\u0001\u0000\u0000\u0000\u00a5\u00a6\u0005&\u0000\u0000"+
		"\u00a6\u00a9\u0005\"\u0000\u0000\u00a7\u00a8\u0005\u0012\u0000\u0000\u00a8"+
		"\u00aa\u0005\u0013\u0000\u0000\u00a9\u00a7\u0001\u0000\u0000\u0000\u00a9"+
		"\u00aa\u0001\u0000\u0000\u0000\u00aa\u00ab\u0001\u0000\u0000\u0000\u00ab"+
		"\u00ac\u0005\u0007\u0000\u0000\u00ac\u0013\u0001\u0000\u0000\u0000\u00ad"+
		"\u00af\u0005&\u0000\u0000\u00ae\u00ad\u0001\u0000\u0000\u0000\u00ae\u00af"+
		"\u0001\u0000\u0000\u0000\u00af\u00b0\u0001\u0000\u0000\u0000\u00b0\u00b5"+
		"\u0005\"\u0000\u0000\u00b1\u00b2\u0005\u0012\u0000\u0000\u00b2\u00b3\u0003"+
		"\u0006\u0003\u0000\u00b3\u00b4\u0005\u0013\u0000\u0000\u00b4\u00b6\u0001"+
		"\u0000\u0000\u0000\u00b5\u00b1\u0001\u0000\u0000\u0000\u00b5\u00b6\u0001"+
		"\u0000\u0000\u0000\u00b6\u00b7\u0001\u0000\u0000\u0000\u00b7\u00be\u0005"+
		"\u0017\u0000\u0000\u00b8\u00bf\u0003\u0006\u0003\u0000\u00b9\u00bf\u0003"+
		"\u0010\b\u0000\u00ba\u00bb\u0005\u0005\u0000\u0000\u00bb\u00bc\u0003\u000e"+
		"\u0007\u0000\u00bc\u00bd\u0005\u0006\u0000\u0000\u00bd\u00bf\u0001\u0000"+
		"\u0000\u0000\u00be\u00b8\u0001\u0000\u0000\u0000\u00be\u00b9\u0001\u0000"+
		"\u0000\u0000\u00be\u00ba\u0001\u0000\u0000\u0000\u00bf\u00c0\u0001\u0000"+
		"\u0000\u0000\u00c0\u00c1\u0005\u0007\u0000\u0000\u00c1\u0015\u0001\u0000"+
		"\u0000\u0000\u00c2\u00c3\u0005\u0018\u0000\u0000\u00c3\u00c4\u0003\f\u0006"+
		"\u0000\u00c4\u00c6\u0003\b\u0004\u0000\u00c5\u00c7\u0003\u0018\f\u0000"+
		"\u00c6\u00c5\u0001\u0000\u0000\u0000\u00c6\u00c7\u0001\u0000\u0000\u0000"+
		"\u00c7\u0017\u0001\u0000\u0000\u0000\u00c8\u00ca\u0005\u0019\u0000\u0000"+
		"\u00c9\u00cb\u0003\u0016\u000b\u0000\u00ca\u00c9\u0001\u0000\u0000\u0000"+
		"\u00ca\u00cb\u0001\u0000\u0000\u0000\u00cb\u00cc\u0001\u0000\u0000\u0000"+
		"\u00cc\u00cd\u0003\b\u0004\u0000\u00cd\u0019\u0001\u0000\u0000\u0000\u00ce"+
		"\u00cf\u0005\u001a\u0000\u0000\u00cf\u00d0\u0003\f\u0006\u0000\u00d0\u00d1"+
		"\u0003\b\u0004\u0000\u00d1\u001b\u0001\u0000\u0000\u0000\u00d2\u00d7\u0005"+
		"\u001b\u0000\u0000\u00d3\u00d6\u0003\u0004\u0002\u0000\u00d4\u00d6\u0003"+
		"\u0006\u0003\u0000\u00d5\u00d3\u0001\u0000\u0000\u0000\u00d5\u00d4\u0001"+
		"\u0000\u0000\u0000\u00d6\u00d9\u0001\u0000\u0000\u0000\u00d7\u00d5\u0001"+
		"\u0000\u0000\u0000\u00d7\u00d8\u0001\u0000\u0000\u0000\u00d8\u001d\u0001"+
		"\u0000\u0000\u0000\u00d9\u00d7\u0001\u0000\u0000\u0000\u00da\u00db\u0005"+
		"\"\u0000\u0000\u00db\u00dd\u0005\u0002\u0000\u0000\u00dc\u00de\u0003\u000e"+
		"\u0007\u0000\u00dd\u00dc\u0001\u0000\u0000\u0000\u00dd\u00de\u0001\u0000"+
		"\u0000\u0000\u00de\u00df\u0001\u0000\u0000\u0000\u00df\u00e1\u0005\u0004"+
		"\u0000\u0000\u00e0\u00e2\u0005\u0007\u0000\u0000\u00e1\u00e0\u0001\u0000"+
		"\u0000\u0000\u00e1\u00e2\u0001\u0000\u0000\u0000\u00e2\u00f2\u0001\u0000"+
		"\u0000\u0000\u00e3\u00e5\u0005&\u0000\u0000\u00e4\u00e3\u0001\u0000\u0000"+
		"\u0000\u00e4\u00e5\u0001\u0000\u0000\u0000\u00e5\u00e6\u0001\u0000\u0000"+
		"\u0000\u00e6\u00e7\u0005\"\u0000\u0000\u00e7\u00e8\u0005\u0017\u0000\u0000"+
		"\u00e8\u00e9\u0005\"\u0000\u0000\u00e9\u00eb\u0005\u0002\u0000\u0000\u00ea"+
		"\u00ec\u0003\u000e\u0007\u0000\u00eb\u00ea\u0001\u0000\u0000\u0000\u00eb"+
		"\u00ec\u0001\u0000\u0000\u0000\u00ec\u00ed\u0001\u0000\u0000\u0000\u00ed"+
		"\u00ef\u0005\u0004\u0000\u0000\u00ee\u00f0\u0005\u0007\u0000\u0000\u00ef"+
		"\u00ee\u0001\u0000\u0000\u0000\u00ef\u00f0\u0001\u0000\u0000\u0000\u00f0"+
		"\u00f2\u0001\u0000\u0000\u0000\u00f1\u00da\u0001\u0000\u0000\u0000\u00f1"+
		"\u00e4\u0001\u0000\u0000\u0000\u00f2\u001f\u0001\u0000\u0000\u0000\u00f3"+
		"\u00f4\u0005&\u0000\u0000\u00f4\u00f5\u0005\"\u0000\u0000\u00f5\u0100"+
		"\u0005\u0002\u0000\u0000\u00f6\u00f7\u0005&\u0000\u0000\u00f7\u00fd\u0005"+
		"\"\u0000\u0000\u00f8\u00f9\u0005\u0003\u0000\u0000\u00f9\u00fa\u0005&"+
		"\u0000\u0000\u00fa\u00fc\u0005\"\u0000\u0000\u00fb\u00f8\u0001\u0000\u0000"+
		"\u0000\u00fc\u00ff\u0001\u0000\u0000\u0000\u00fd\u00fb\u0001\u0000\u0000"+
		"\u0000\u00fd\u00fe\u0001\u0000\u0000\u0000\u00fe\u0101\u0001\u0000\u0000"+
		"\u0000\u00ff\u00fd\u0001\u0000\u0000\u0000\u0100\u00f6\u0001\u0000\u0000"+
		"\u0000\u0100\u0101\u0001\u0000\u0000\u0000\u0101\u0102\u0001\u0000\u0000"+
		"\u0000\u0102\u0103\u0005\u0004\u0000\u0000\u0103\u0107\u0005\u0005\u0000"+
		"\u0000\u0104\u0106\u0003\u0004\u0002\u0000\u0105\u0104\u0001\u0000\u0000"+
		"\u0000\u0106\u0109\u0001\u0000\u0000\u0000\u0107\u0105\u0001\u0000\u0000"+
		"\u0000\u0107\u0108\u0001\u0000\u0000\u0000\u0108\u010a\u0001\u0000\u0000"+
		"\u0000\u0109\u0107\u0001\u0000\u0000\u0000\u010a\u010b\u0005\u0006\u0000"+
		"\u0000\u010b!\u0001\u0000\u0000\u0000\u010c\u010d\u0005\u001c\u0000\u0000"+
		"\u010d\u010f\u0005\u0002\u0000\u0000\u010e\u0110\u0005\u001d\u0000\u0000"+
		"\u010f\u010e\u0001\u0000\u0000\u0000\u010f\u0110\u0001\u0000\u0000\u0000"+
		"\u0110\u0112\u0001\u0000\u0000\u0000\u0111\u0113\u0003\u0006\u0003\u0000"+
		"\u0112\u0111\u0001\u0000\u0000\u0000\u0112\u0113\u0001\u0000\u0000\u0000"+
		"\u0113\u0115\u0001\u0000\u0000\u0000\u0114\u0116\u0005\u001d\u0000\u0000"+
		"\u0115\u0114\u0001\u0000\u0000\u0000\u0115\u0116\u0001\u0000\u0000\u0000"+
		"\u0116\u0117\u0001\u0000\u0000\u0000\u0117\u0118\u0005\u0004\u0000\u0000"+
		"\u0118\u0119\u0005\u0007\u0000\u0000\u0119#\u0001\u0000\u0000\u0000\u011a"+
		"\u011b\u0005\u001e\u0000\u0000\u011b\u011d\u0005\u0002\u0000\u0000\u011c"+
		"\u011e\u0005\u001d\u0000\u0000\u011d\u011c\u0001\u0000\u0000\u0000\u011d"+
		"\u011e\u0001\u0000\u0000\u0000\u011e\u0120\u0001\u0000\u0000\u0000\u011f"+
		"\u0121\u0003\u0006\u0003\u0000\u0120\u011f\u0001\u0000\u0000\u0000\u0120"+
		"\u0121\u0001\u0000\u0000\u0000\u0121\u0123\u0001\u0000\u0000\u0000\u0122"+
		"\u0124\u0005\u001d\u0000\u0000\u0123\u0122\u0001\u0000\u0000\u0000\u0123"+
		"\u0124\u0001\u0000\u0000\u0000\u0124\u0125\u0001\u0000\u0000\u0000\u0125"+
		"\u0126\u0005\u0004\u0000\u0000\u0126\u0127\u0005\u0007\u0000\u0000\u0127"+
		"%\u0001\u0000\u0000\u0000()0<?FWhsuy~\u0082\u0085\u0088\u008d\u0091\u009c"+
		"\u00a9\u00ae\u00b5\u00be\u00c6\u00ca\u00d5\u00d7\u00dd\u00e1\u00e4\u00eb"+
		"\u00ef\u00f1\u00fd\u0100\u0107\u010f\u0112\u0115\u011d\u0120\u0123";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}