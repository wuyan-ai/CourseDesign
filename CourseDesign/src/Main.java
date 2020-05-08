import Parsing.Enum;
import Parsing.Parse;
import Parsing.treeNode;
import Word.WordAnalysis;
import Word.WordToken;

import javax.swing.*;
import java.awt.*;

import static Parsing.Parse.match;

public class Main extends JFrame {
        static int  X ;
        static int  Space = 30 ;
        static int  Width , High ;
        static TreePanel Panel ;


        public  Main() {
            Panel = new TreePanel() ;
            Panel.setPreferredSize( new Dimension( Width , High ) ) ;
            JScrollPane pane = new JScrollPane( Panel );
            add( pane ) ;
        }

        public  static int getTreeInf( treeNode root , int Y ) {
            int  temp , Length , width = 0 ;
            String str = "" ;

            if( root.getflag() == 0 || ( root.getchildNum() == 0 ) ) {
                if( root.getflag() == 0 ) str += root.getData() ;
                else str += root.getNonTerminal() ;
                Length = str.length() * 8 ;
                width = Length + Space ;
                root.setLength( Length ) ;
                root.setWidth( width ) ;
                root.setX( X ) ;
                root.setY( Y ) ;
                X += width ;
            }
            else {
                str += root.getNonTerminal() ;
                Length = str.length() * 8 ;
                root.setLength( Length ) ;
                root.setY( Y ) ;
                temp = X ;
                for( int i = 0 ; i < root.getchildNum() ; i ++ ) {
                    width += getTreeInf( root.getChild( i ) , Y + 50 ) ;
                }
                root.setX( temp + width / 2 - Length / 2 ) ;
                if( width < Length ) {
                    width = Length / 2 + width / 2 ;
                    X += Length - width + Space ;
                }
                root.setWidth( width ) ;

            }
            return width ;
        }

        public  static void drawtree( treeNode root ) {
            X = 20 ;
            High = getTreeInf( root , 20 ) ;
            Width = X ;
            Panel.Root = root ;

            Main frame = new Main() ;
            frame.setTitle( "语法分析树" ) ;
            frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE ) ;
            frame.setSize( 1000 , 800 ) ;
            frame.setVisible( true ) ;
        }

    public static void main(String[] args) {
        WordAnalysis wordAnalysis=new WordAnalysis();
        String filename= "C:\\Users\\Administrator\\Desktop\\test.txt";
        if(wordAnalysis.Scanner(filename)) {
            if(wordAnalysis.tokenList!=null)
                for(WordToken wordToken:wordAnalysis.tokenList)
                    System.out.println(wordToken.line+"  "+wordToken.type+"  "+wordToken.wordMean);
            treeNode root = new treeNode() ;

            Parse parser = new Parse() ;

            try{
                root = parser.getTree() ;
            }catch (Exception e){
                System.out.println("语法存在ERROR");
            }

            if(root!=null)
            {
               try{
                   Main.drawtree( root ) ;
               }catch (Exception e){
                   System.out.println("语法存在ERROR");
               }
            }
            else {
                System.out.println("语法存在ERROR");
                for(WordToken wordToken:parser.parsingErr)
                    System.out.println(wordToken.line+"  "+wordToken.type+"  "+wordToken.wordMean);
            }

        }
        else{
            System.out.println("词法分析过程中发现ERROR");
            if(wordAnalysis.tokenList!=null)
                for(WordToken wordToken:wordAnalysis.tokenList)
                    System.out.println(wordToken.line+"  "+wordToken.type+"  "+wordToken.wordMean);
        }
    }
}
// 这是来自黑黑的本地修改 !!!!!!

