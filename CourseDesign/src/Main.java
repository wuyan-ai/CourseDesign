import Word.WordAnalysis;
import Word.WordToken;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Main {
//    public static String readString2()
//
//    {
////        StringBuffer str=new StringBuffer("");
////
////        File file=new File("C:\\Users\\Administrator\\Desktop\\test.txt");
////
////        try {
////
////            FileReader fr=new FileReader(file);
////
////            int ch;
////
////            while((ch = fr.read())!=-1 )
////            {
////                str.append((char)ch);
////                //System.out.println(ch);
////                System.out.println((char)ch+" ");
////            }
////            fr.close();
////        } catch (IOException e) {
////            // TODO Auto-generated catch block
////            e.printStackTrace();
////            System.out.println("File reader出错");
////        }
//
//        return str.toString();
//
//    }
    public static void main(String[] args) {
          WordAnalysis wordAnalysis=new WordAnalysis();
          String filename= "C:\\Users\\Administrator\\Desktop\\test.txt";
           if(wordAnalysis.Scanner(filename)) {
               if(wordAnalysis.tokenList!=null)
                 for(WordToken wordToken:wordAnalysis.tokenList)
                     System.out.println(wordToken.line+"  "+wordToken.type+"  "+wordToken.wordMean);
           }
           else{
               System.out.println(wordAnalysis.line);
               System.out.println(wordAnalysis.msg);
           }
//        wordAnalysis.tokenList=null;
//        WordAnalysis.tokenList=null;


       // System.out.println(readString2().length());
        System.out.println("Hello World!");
    }
}
