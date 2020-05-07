package Parsing;

public class Enum {
    public enum lexType{
        /* 簿记单词符号 */
        ENDFILE,	ERROR,
        /* 保留字 */
        PROGRAM,	 PROCEDURE,	    TYPE,	        VAR,		 IF,
        THEN,		 ELSE,		    FI,		        WHILE,		 DO,
        ENDWH,		 BEGIN,		    END,	        READ,		 WRITE,
        ARRAY,		 OF,		    RECORD,	        RETURN,

        INTEGER,	 CHAR,
        /* 多字符单词符号 */
        ID,			 INTC,		    CHARC,
        /*特殊符号 */
        ASSIGN,		 EQ,		    LT,		        PLUS,		 MINUS,
        TIMES,		 OVER,		    LPAREN,	        RPAREN,		 DOT,
        COLON,		 SEMI,		    COMMA,	        LMIDPAREN,	 RMIDPAREN,
        UNDERANGE ;
    }
    /*非终极符*/
    public enum nonTerminals{
        Program,     ProgramHead,   ProgramName,    DeclarePart, TypeDecpart,
        TypeDec,     TypeDecList,   TypeDecMore,    TypeId,      TypeDef,
        BaseType,    StructureType, ArrayType,      Low,         Top,
        RecType,     FieldDecList,  FieldDecMore,   IdList,      IdMore,
        VarDecpart,  VarDec,        VarDecList,     VarDecMore,  VarIdList,   VarIdMore,
        ProcDecpart, ProcDec,       ProcDecMore,    ProcName,    ParamList,   ProcDeclaration ,
        ParamDecList,ParamMore,     Param,          FormList,    FidMore,
        ProcDecPart, ProcBody,      ProgramBody,    StmList,     StmMore,     Stm,
        AssCall,     AssignmentRest,ConditionalStm, LoopStm,     InputStm,
        Invar,		 OutputStm,     ReturnStm,      CallStmRest, ActParamList,
        ActParamMore,RelExp,        OtherRelE,      Exp,         OtherTerm,
        Term,        OtherFactor,   Factor,         Variable,    VariMore,    FieldVar,
        FieldVarMore,CmpOp,         AddOp,          MultOp ;
    }
    public enum nodeKind {
        ProK ,  PheadK , TypeK , VarK , ProcDecK , StmLK , DecK , StmtK , ExpL ;
    }
    public enum decKind {
        ArrayK , CharK , IntegerK , RecordK , IdK ;
    }
    public enum stmtKind {
        IfK , WhileK , AssignK , ReadK , WriteK , CallK , ReturnK ;
    }
    public enum expKind {
        OpK , ConstK , VariK ;
    }
    public enum varKind {
        IdV , ArrayMembV , FieldMembV ;
    }
    public enum expType {
        Void , Integer , Boolean ;
    }
    public enum paramType {
        ValParamType , VarparamType ;
    }
}
