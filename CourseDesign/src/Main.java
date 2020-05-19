import Parsing.Parse;
import Parsing.treeNode;
import UI.TreePanel;
import Word.WordAnalysis;
import Word.WordToken;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {
    /*
        static int  X ;
        static int  Space = 30 ;
        static int  Width , High ;
        static TreePanel Panel ;

        //测试
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
            High = getTreeInf( root , 20 );
            Width = X ;
            Panel.Root = root ;

            Main frame = new Main() ;
            frame.setTitle( "语法分析树" ) ;
            frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE ) ;
            frame.setSize( 1000 , 800 ) ;
            frame.setVisible( true ) ;
        }*/

    public static void main(String[] args) {
        UI.Win win=new UI.Win();
        while(true)
        {
            win.clearFile("src\\UI\\ParsingResult.txt");
            win.CleanCreating();

            while(win.analysisStartFlag == 1)
            {
                if(win.analysisStartFlag == 2)
                    break;

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            WordAnalysis wordAnalysis=new WordAnalysis();
            String filename= "./code.txt";
            if(wordAnalysis.Scanner(filename)) {
                if(wordAnalysis.tokenList!=null)
                    for(WordToken wordToken:wordAnalysis.tokenList)
                        System.out.println(wordToken.line+"  "+wordToken.type+"  "+wordToken.wordMean);
                treeNode root = new treeNode() ;

                Parse parser = new Parse() ;

                try{
                    root = parser.getTree() ;
                    if(root!=null)
                    {
                        try{

                            win.drawtree(root);

                            //Main.drawtree( root ) ;

                            System.out.println("\n成功");
                            win.parsingResultWritingFile("\n成功");
                            win.Creating();
                        }catch (Exception e){
                            System.out.println("\n语法存在ERROR");
                        }
                    }
                    else {
                        System.out.println("\n语法存在ERROR");
                        win.ParsingWrongCreating();
                        for(WordToken wordToken:parser.parsingErr) {
                            System.out.println(wordToken.line + "  " + wordToken.type + "  " + wordToken.wordMean);
                            win.parsingErrorWirting(wordToken.line + "  " + wordToken.type + "  " + wordToken.wordMean);
                        }
                    }
                }catch (Exception e){
                    System.out.println("\n语法存在ERROR");
                    win.ParsingWrongCreating();
                    for(WordToken wordToken:parser.parsingErr) {
                        System.out.println(wordToken.line + "  " + wordToken.type + "  " + wordToken.wordMean);
                        win.parsingErrorWirting(wordToken.line + "  " + wordToken.type + "  " + wordToken.wordMean);
                    }
                }
            }
            else{
                System.out.println("\n词法分析过程中发现ERROR");
                if(wordAnalysis.tokenList!=null)
                    win.WordAnalysisWrongCreating();
                for(WordToken wordToken:wordAnalysis.tokenList)
                    System.out.println(wordToken.line+"  "+wordToken.type+"  "+wordToken.wordMean);
            }

            while(win.programResetFlag == 1)
            {
                if (win.programResetFlag == 2)
                    break;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}

