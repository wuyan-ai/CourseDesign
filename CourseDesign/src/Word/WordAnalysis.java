package Word;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PushbackReader;
import java.util.*;

public class WordAnalysis {
     public String msg=null;  //保存错误信息
     static public int line=1;
     static public List<WordToken> tokenList=new ArrayList<WordToken>();  //保存token序列

    /*
     *词法分析扫描程序，每次调用返回一个Token指针
     */
    public boolean  Scanner(String filePath)
    {
        char ch ;
        int success=1;
        String tmpStr = "";
        WordToken wordToken=new WordToken();
        StringBuffer str=new StringBuffer("");
        File file=new File(filePath);
        try {
            PushbackReader fr = new PushbackReader(new FileReader(file), 1); //能够回退一个字符
            //FileReader fr=new FileReader(file);
            int chNum;
            int flag=0;
            while((chNum = fr.read())!=-1 )
            {
                str.append((char)chNum);
                ch=(char)chNum;
                if (chNum != -1)
                {
                    if(ch == '\n')
                            line++;
                    if ((ch >= 'A' &&ch <= 'Z') || (ch >= 'a' && ch <= 'z'))  flag=1;
                    else
                        if (ch >= '0' &&ch <= '9')  flag=2;
                        else if (ch == '+')  flag=3;
                            else if (ch == '-') flag=4;
                                else if (ch == '*')   flag=5;
                                    else if (ch == '/')   flag=6;
                                        else if (ch == '<')   flag=7;
                                           else if (ch == ';')  flag=8;
                                              else if (ch == ':')  flag=9;
                                                 else if (ch == ',')  flag=10;
                                                   else if (ch == '.')  flag=11;
                                                     else  if (ch == '=')  flag=12;
                                                       else   if (ch == '[')  flag=13;
                                                        else   if (ch == ']')  flag=14;
                                                           else  if (ch == '(')  flag=15;
                                                             else  if (ch == ')')  flag=16;
                                                                else  if (ch == ' ' || ch == '\n' || ch == '\r' || ch == '\t') flag=17;
                                                                   else if(ch=='\'')  flag=18;
                                                                    else if(ch=='{') flag=19;
                                                                     else  flag=19;
                }
                else
                {
                    msg="文件为空";
                    return false;
                }

                tmpStr="";
                switch (flag){
                    case 1:    //标示符和关键字
                        tmpStr += ch;
                        while((chNum = fr.read())!=-1 ){
                            ch=(char)chNum;
                            if ((ch >= 'A' &&ch <= 'Z') || (ch >= 'a' && ch <= 'z') || (ch >= '0' &&ch <= '9'))
                                tmpStr += ch;
                            if (IsOther(ch)) {
                                fr.unread((int)ch); //把读到的字符放回到文件流中
                                if (IsKeyWord(tmpStr))
                                {
                                    //关键字
                                    wordToken = new WordToken(line,tmpStr.toUpperCase(),"reserved word");
                                    tokenList.add(wordToken);
                                    tmpStr="";
                                }
                                else
                                {
                                    //不是关键字,是标示符
                                    wordToken = new WordToken(line,"ID",tmpStr);
                                    tokenList.add(wordToken);
                                    tmpStr="";
                                }
                                break;
                            }
                        }
                        if(tmpStr!=""){             //最后一个标识符|保留字的判断
                            if (IsKeyWord(tmpStr))
                            {
                                //关键字
                                wordToken = new WordToken(line,tmpStr.toUpperCase(),"reserved word");
                                tokenList.add(wordToken);
                            }
                            else
                            {
                                //不是关键字,是标示符
                                wordToken = new WordToken(line,"ID",tmpStr);
                                tokenList.add(wordToken);
                            }
                        }
                        break;
                    case 2:  //数字
                        tmpStr += ch;
                        while((chNum = fr.read())!=-1 ){
                            ch=(char)chNum;
                            if (ch >= '0' &&ch <= '9')
                                tmpStr += ch;
                            if (IsOther(ch))
                            {
                                fr.unread((int)ch);
                                //wordToken = new WordToken(line,"INTC",tmpStr);
                                //tokenList.add(wordToken);
                                break;
                            }
                        }
                        if(tmpStr!=""){             //最后一个数字的判断
                            wordToken = new WordToken(line,"INTC",tmpStr);
                            tokenList.add(wordToken);
                        }
                        break;
                    case 3: //'+'
                        wordToken = new WordToken(line,"+","分界符，无语义信息");
                        tokenList.add(wordToken);
                        break;
                    case 4://'-'
                        wordToken = new WordToken(line,"-","分界符，无语义信息");
                        tokenList.add(wordToken);
                        break;
                    case 5://'*'
                        wordToken = new WordToken(line,"*","分界符，无语义信息");
                        tokenList.add(wordToken);
                        break;
                    case 6://'/'
                        wordToken = new WordToken(line,"/","分界符，无语义信息");
                        tokenList.add(wordToken);
                        break;
                    case 7://'<'
                        wordToken = new WordToken(line,"<","分界符，无语义信息");
                        tokenList.add(wordToken);
                        break;
                    case 8://';'
                        wordToken = new WordToken(line,";","分界符，无语义信息");
                        tokenList.add(wordToken);
                        break;
                    case 9: //':'
                        if((chNum = fr.read())!=-1 ){
                            ch=(char)chNum;
                        }
                        if (ch == '=') {
                            wordToken = new WordToken(line,":=","分界符，无语义信息");
                            tokenList.add(wordToken);
                        }
                        else {
                            msg="错误:“:”不是合法的符号";
                            success=0;
                            wordToken = new WordToken(line,"ERROR","词法分析出错："+msg);
                            tokenList.add(wordToken);
                            break;
                        }
                        break;
                    case 10://','
                        wordToken = new WordToken(line,"COMMA","分界符，无语义信息");
                        tokenList.add(wordToken);
                        break;
                    case 11://'.'
                        if((chNum = fr.read())!=-1 && ((char)chNum=='.'))
                        {
                            wordToken = new WordToken(line,"..","分界符，无语义信息");
                            tokenList.add(wordToken);
                        }
                        else if((chNum = fr.read())==-1)
                        {
                            wordToken = new WordToken(line,".","结束符，无语义信息");
                            tokenList.add(wordToken);
                        }
                        else
                        {
                            wordToken = new WordToken(line,".","结束符，无语义信息");
                            tokenList.add(wordToken);
                            fr.unread(chNum);
                        }
                        break;
                    case 12: //'='
                        wordToken = new WordToken(line,"=","分界符，无语义信息");
                        tokenList.add(wordToken);
                        break;
                    case 13://'['
                        wordToken = new WordToken(line,"[","分界符，无语义信息");
                        tokenList.add(wordToken);
                        break;
                    case 14://']'
                        wordToken = new WordToken(line,"]","分界符，无语义信息");
                        tokenList.add(wordToken);
                        break;
                    case 15: //'('
                        wordToken = new WordToken(line,"(","分界符，无语义信息");
                        tokenList.add(wordToken);
                        break;
                    case 16: //')'
                        wordToken = new WordToken(line,")","分界符，无语义信息");
                        tokenList.add(wordToken);
                        break;
                    case 17: //空白符
                        break;
                    case 18://' ' '  字符处理
                        while(((chNum = fr.read())!=-1) && !IsOther((char)chNum) ){
                            ch=(char)chNum;
                            tmpStr+=ch;
                        }
                        ch=(char)chNum;
                        if(ch=='\'')
                        {
                            wordToken = new WordToken(line,"CHARC",tmpStr);
                            tokenList.add(wordToken);
                        }
                        else
                        {
                            success=0;
                            msg="字符‘不合法";
                            wordToken = new WordToken(line,"ERROR","词法分析出错："+msg+ch);
                            tokenList.add(wordToken);
                        }
                        break;
                    case 19: //空白符
                        while(((chNum = fr.read())!=-1) && (char)chNum!='}'){ }
                        if(chNum==-1)
                        {
                            success=0;
                            msg="无}与{匹配";
                            wordToken = new WordToken(line,"ERROR","词法分析出错："+msg+ch);
                            tokenList.add(wordToken);
                        }
                        break;
                        default:
                            msg="遇到错误字符";//打印错误信息
                            success=0;
                            wordToken = new WordToken(line,"ERROR",msg+ch);
                            tokenList.add(wordToken);
                            break;
                }
            }
            if(success==1){
                wordToken = new WordToken(line,"EOF","结束");
                tokenList.add(wordToken);
            }
            fr.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            msg="File reader出错";
            return false;
        }
        if(success == 0)
          return false;
        return true;
    }

    //保存所有错误信息
    public  List<WordToken> showErrLines(){
        List<WordToken> tempList=new ArrayList<>();
        for(WordToken wordToken:tokenList){
            if(wordToken.type.equals("ERROR"))
                tempList.add(wordToken);
        }
        if(tempList==null || tempList.size()==0)
            return null;
        return tempList;
    }

     /*
     *判断字符是不是其他字符
     */
    boolean IsOther(char ch)
    {
        if (ch >= 'A'&&ch <= 'Z')
            return false;
        if (ch >= 'a'&&ch <= 'z')
            return false;
        if (ch >= '0'&&ch <= '9')
            return false;
        return true;
    }

    /*
     *判断字符串是不是关键字
     */
    boolean IsKeyWord(String str)
    {
        if (str.equals("integer"))
            return true;
        if (str.equals("char"))
            return true;
        if (str.equals("program"))
            return true;
        if (str.equals("array"))
            return true;
        if (str.equals("of"))
            return true;
        if (str.equals("record"))
            return true;
        if (str.equals("end"))
            return true;
        if (str.equals("var"))
            return true;
        if (str.equals("procedure"))
            return true;
        if (str.equals("begin"))
            return true;
        if (str.equals("if"))
            return true;
        if (str.equals("then"))
            return true;
        if (str.equals("else"))
            return true;
        if (str.equals("fi"))
            return true;
        if (str.equals("while"))
            return true;
        if (str.equals("do"))
            return true;
        if (str.equals("endwh"))
            return true;
        if (str.equals("read"))
            return true;
        if (str.equals("write"))
            return true;
        if (str.equals("return"))
            return true;
        if (str.equals("type"))
            return true;
        return false;
    }
}
