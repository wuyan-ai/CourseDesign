package UI;
import Parsing.treeNode;

import javax.swing.*;
import java.awt.*;

public class TreePanel extends JLabel
{
    public static treeNode Root ;

    public void draw( treeNode root , Graphics g )
    {
        // g.drawRect( root.getX() - 3 , root.getY() - 15 , root.getLength() + 1 , 20 ) ;
        if( root.getflag() == 0 )
        {
            g.setColor( Color.black ) ;
            g.drawString( root.getData() , root.getX() , root.getY() ) ;
        }
        else
        {
            g.setColor( Color.black ) ;
            g.drawString( "" + root.getNonTerminal() , root.getX() , root.getY() ) ;
            if( root.getchildNum() != 0 )
            {
                for( int i = 0 ; i < root.getchildNum() ; i ++ )
                {
                    g.setColor(Color.GRAY);
                    g.drawLine( root.getX() + root.getLength() / 2 , root.getY() + 10  , root.getChild(i).getX() + root.getChild(i).getLength() / 2 , root.getChild(i).getY() - 20 ) ;
                    draw( root.getChild( i ),g ) ;
                }
            }
        }
    }

    public void paint( Graphics g )
    {
        super.paint( g ) ;
        g.setColor( Color.black ) ;
        g.setFont(new Font("MicrosoftYaHei-Bold",Font.BOLD,15));
        draw( Root , g ) ;
    }
}
