package Word;

public class WordToken {
    public   int line;    //出现的位置
    public   String type;    //类型(词法信息)
    public  String wordMean;  //语义信息

    public WordToken(){ }
    public WordToken(int line, String type, String wordMean){
        this.line = line;
        this.wordMean = wordMean;
        this.type = type;
    }
}
