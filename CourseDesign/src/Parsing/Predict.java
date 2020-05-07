package Parsing;

//保存一条规则的所有predict
public class Predict {
    int         predictNum ;
    Enum.lexType[] predict = new Enum.lexType[20] ;

    public Predict() {
        predictNum = 0 ;
    }

    public void setPredict( Enum.lexType pre ) {
        predict[predictNum] = pre ;
        predictNum ++ ;
    }

    public int getPredictNum() {
        return predictNum ;
    }

    public Enum.lexType getPredict( int number ) {
        return predict[number] ;
    }
}
