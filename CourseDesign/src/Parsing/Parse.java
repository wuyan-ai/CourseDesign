package Parsing;

import Word.WordToken;
import UI.Win;

import java.util.*;
import java.util.List;



import static Parsing.Enum.lexType.*;
import static Word.WordAnalysis.tokenList;

public class Parse {
    static SNLproduct Product = new SNLproduct() ;
    static SNLpredict Predict = new SNLpredict() ;
    static List<transToken> TokenList = new ArrayList< transToken >() ;
    static int cur ;  //当前token位置
    public Map<String, Enum.lexType> trans=new HashMap<String, Enum.lexType>();   //token类型转换
    static public List<WordToken> parsingErr=new ArrayList<>();

    public Parse() {
        cur = 0 ;
        trans.put("+",PLUS);
        trans.put("-",MINUS);
        trans.put("*",TIMES);
        trans.put("/",OVER);
        trans.put("(",LPAREN);
        trans.put(")",RPAREN);
        trans.put(".",DOT);
        trans.put("[",LMIDPAREN);
        trans.put("]",RMIDPAREN);
        trans.put(";",SEMI);
        trans.put(":",COLON);
        trans.put("COMMA",COMMA);
        trans.put("<",LT);
        trans.put("=",EQ);
        trans.put(":=",ASSIGN);
        trans.put("..",UNDERANGE);
        trans.put("EOF",ENDFILE);
        trans.put("ERROR",ERROR);
        trans.put("PROGRAM",PROGRAM);
        trans.put("PROCEDURE",PROCEDURE);
        trans.put("TYPE",TYPE);
        trans.put("VAR",VAR);
        trans.put("IF",IF);
        trans.put("THEN",THEN);
        trans.put("ELSE",ELSE);
        trans.put("FI",FI);
        trans.put("WHILE",WHILE);
        trans.put("DO",DO);
        trans.put("ENDWH",ENDWH);
        trans.put("BEGIN",BEGIN);
        trans.put("END",END);
        trans.put("READ",READ);
        trans.put("WRITE",WRITE);
        trans.put("ARRAY",ARRAY);
        trans.put("OF",OF);
        trans.put("RECORD",RECORD);
        trans.put("RETURN",RETURN);
        trans.put("INTEGER",INTEGER);
        trans.put("CHAR",CHAR);
        trans.put("ID",ID);
        trans.put("INTC",INTC);
        trans.put("CHARC",CHARC);

        for(WordToken wordToken:tokenList){
            transToken temp=new transToken();
            temp.line=wordToken.line;
            if(wordToken.type.equals("ID")||wordToken.type.equals("INTC")||wordToken.type.equals("CHARC"))
                temp.wordMean=wordToken.wordMean;
            else
                temp.wordMean=trans.get(wordToken.type).toString();
            temp.type=trans.get(wordToken.type);
            TokenList.add(temp);
        }
    }


    /*****************/
    /*递归地匹配一个非终极符*/
    /*****************/
    public static treeNode match( Enum.nonTerminals NonTerminal , treeNode father ) {

        int  i , j , choose = -1 ;
        treeNode root = new treeNode() ;
        Enum.nonTerminals temp ;
        Enum.lexType curLex = TokenList.get(cur).type;
        int nowline=0;

        root.setflag(1) ;      //非叶子节点
        root.setNonTerminal( NonTerminal ) ;
        root.setFather( father ) ;

        for( i = 1 ; i <= 104 ; i ++ ) {
            int flag = 0 ;
            temp = Product.product[i].getHead() ;               //104条规则的左部
            for( j = 0 ; j < Predict.predict[i].getPredictNum() ; j ++ ) {
                if( curLex == Predict.predict[i].getPredict( j ) ) {
                    flag = 1 ;
                    break ;
                }
            }
            if(  flag == 1 && temp == NonTerminal ) {
                choose = i ;     //选择第i条文法规则
                break ;
            }
        }

        if( choose == -1 ) {
            WordToken tempMsg=new WordToken(TokenList.get(cur).line,"ERROR：未找到匹配文法规则","语法错误");
            parsingErr.add(tempMsg);
            return null ;
        }
        else {
            for( i = 0 ; i < Product.product[choose].getproductNum() ; i ++ ) {
                if( Product.product[choose].getflag( i ) == 0 ) {  //终极符
                    treeNode leaf = new treeNode() ;
                    leaf.setFather( father ) ;
                    leaf.setflag( 0 ) ;
                    leaf.setTerminal( Product.product[choose].getProductTerminal( i ) ) ;
                    leaf.setData( TokenList.get( cur ).wordMean) ;

                   /*
                   打印 叶子节点的wordmean
                   */
                    System.out.println();
                    System.out.print("匹配 ：");
                    if(cur-1>=0)
                        nowline=TokenList.get( cur-1 ).line;
                    if(nowline==TokenList.get( cur ).line||cur==0)
                        System.out.print(TokenList.get( cur ).wordMean+"  ");
                    else{
                        //  System.out.println();
                        System.out.print(TokenList.get( cur ).wordMean+"  ");
                    }


                    root.setChild( leaf ) ;
                    cur ++ ;
                }
                else {
                    treeNode child ;
                    Enum.nonTerminals NonTerminals = Product.product[choose].getProductNonterminal(i) ;

                      /*
                   打印 非叶子节点的wordmean
                   */
                    System.out.println();
                    System.out.print("规约 ：");
//                    if(cur-1>=0)
//                        nowline=TokenList.get( cur-1 ).line;
//                    if(nowline==TokenList.get( cur ).line||cur==0)
//                        System.out.print(Product.product[choose].getHead()+" ->"+NonTerminals);
//                    else{
                    String t="";
                    for(j = 0 ; j < Product.product[choose].getproductNum() ; j ++ )
                    {
                        if(Product.product[choose].getflag(j)==0)
                            t=""+Product.product[choose].getProductTerminal(j).toString()+" ";
                        else
                            t=t+Product.product[choose].getProductNonterminal(j).toString()+" ";
                    }

                    System.out.print(Product.product[choose].getHead()+" ->"+t);
                    //  }

                    child = match( NonTerminals , root ) ;   //非终极符递归
                    root.setChild( child ) ;
                }
            }
        }
        return root ;
    }


    /*********************/
    /*得到一个语法分析树，返回树根*/
    /*********************/
    public static treeNode getTree(){
        if(TokenList==null||TokenList.size()==0) {
            return null ;
        }
        else {
            treeNode root = new treeNode() ;
            cur = 0 ;
            root = match( Enum.nonTerminals.Program , root ) ;
            if(root==null)
                return null;
            if((cur)>=TokenList.size()){
                WordToken tempMsg=new WordToken(TokenList.get(cur).line,"ERROR：输入流不为空，分析栈为空","语法错误");
                parsingErr.add(tempMsg);
                return null;
            }

            if( TokenList.get(cur).type != Enum.lexType.ENDFILE ){
                //System.out.println(TokenList.get(cur+1).type);
                WordToken tempMsg=new WordToken(TokenList.get(cur).line,"ERROR：无结束标识","语法错误");
                parsingErr.add(tempMsg);
            }
            if(cur>0 &&TokenList.get(cur-1).type != Enum.lexType.END){
                //System.out.println(TokenList.get(cur+1).type);
                WordToken tempMsg=new WordToken(TokenList.get(cur).line,"ERROR：缺少END","语法错误");
                parsingErr.add(tempMsg);
            }
            if( parsingErr!=null && parsingErr.size()!=0 ) {
                return null ;
            }
            return root ;
        }
    }
}
