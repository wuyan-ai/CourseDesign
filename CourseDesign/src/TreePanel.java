import Parsing.treeNode;

import javax.swing.*;
import java.awt.*;

public class TreePanel extends JLabel {
        public static treeNode Root ;

    public void draw( treeNode root , Graphics g ) {
        g.drawRect( root.getX() - 3 , root.getY() - 15 , root.getLength() + 1 , 20 ) ;
        if( root.getflag() == 0 ) {
            g.drawString( root.getData() , root.getX() , root.getY() ) ;
        }
        else {
            g.drawString( "" + root.getNonTerminal() , root.getX() , root.getY() ) ;
            if( root.getchildNum() != 0 ) {
                for( int i = 0 ; i < root.getchildNum() ; i ++ ) {
                    g.drawLine( root.getX() + root.getLength() / 2 , root.getY() + 5  , root.getChild(i).getX() + root.getChild(i).getLength() / 2 , root.getChild(i).getY() - 15 ) ;
                    draw( root.getChild( i ),g ) ;
                }
            }
        }
    }

    public void paint( Graphics g ) {
        super.paint( g ) ;
        g.setColor( Color.white ) ;
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        g.setColor( Color.black ) ;
        draw( Root , g ) ;
    }

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

}
