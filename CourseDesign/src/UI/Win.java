package UI;
import Parsing.treeNode;
import Word.WordAnalysis;
import Word.WordToken;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.io.PushbackReader;
import java.util.*;

import java.lang.Exception;



public class Win

{
    public static int analysisStartFlag;

    static int  X ;
    static int  Space = 30 ;
    static int  Width , High ;
    static TreePanel Panel ;
    static TreePanel treepanel;

    public static JFrame jf = new JFrame();

    public static void CleanCreating()
    {
        analysisStartFlag=1;

        // 一 创建一个顶层容器（窗口）
        //JFrame jf = new JFrame();          // 创建窗口
        ImageIcon icon=new ImageIcon("分析.png");  //设置窗口图标
        jf.setIconImage(icon.getImage());           //设置窗口图标
        jf.setTitle("小组 2117-30-03 出品");        // 设置窗口名称
        jf.setSize(1500, 805);      // 设置窗口大小
        jf.setLocationRelativeTo(null);            // 把窗口位置设置到屏幕中心
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // 当点击窗口的关闭按钮时退出程序（没有这一句，程序不会退出）
        jf.setResizable(false);                    //将窗口设置为不随意拖拽变换大小,防止老师随便乱动导致的组件变乱
        // 创建一批字体, 以便统一使用
        Font textAreaFont = new Font("微软雅黑",0,20);  //输入框内字体
        Font titleFont = new Font("微软雅黑",1,40); //词法分析界面上方标题的字体
        Font buttonFont = new Font("微软雅黑",0,15);    //按钮上的字体
        Color grey = new Color(125,125,125);

        // 二 创建中间容器（面板容器）
        JPanel userInputPanel = new JPanel(null);       //用户输入界面 // 创建面板容器，使用绝对布局, 也就是说这个panel里面所有的组件都需要自己确定好自己的位置和大小
        JPanel wordAnalysisPanel = new JPanel(null);    //词法分析界面
        JPanel parsingPanel = new JPanel(null);         //语法分析界面
        JPanel resultShowingPanel = new JPanel(null);   //结果展示界面

        //--------① 创建菜单栏----------------------------------------------------------------------------------------------------
        JMenuBar menuBar = new JMenuBar();

        //1.1 创建一级菜单
        JMenuItem userInputMenuItem = new JMenuItem("用户输入");
        JMenuItem wordAnalysisMenuItem = new JMenuItem("词法分析");
        JMenuItem parsingMenuItem = new JMenuItem("语法分析");
        JMenuItem resultShowingMenuItem = new JMenuItem("结果展示");

        //1.2 一级菜单添加到菜单栏
        menuBar.add(userInputMenuItem);
        menuBar.add(wordAnalysisMenuItem);
        menuBar.add(parsingMenuItem);
        menuBar.add(resultShowingMenuItem);

        //1.3 添加菜单栏的点击响应函数
        //1.3.1 添加"用户输入"菜单栏被点击的函数
        userInputMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("用户输入  被点击");
                jf.setContentPane(userInputPanel);
                jf.setVisible(true);
            }
        });
        //1.3.2 添加"词法分析"菜单栏被点击的函数
        wordAnalysisMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("词法分析  被点击");
                jf.setContentPane(wordAnalysisPanel);
                jf.setVisible(true);
            }
        });
        //1.3.3 添加"语法分析"菜单栏被点击的函数
        parsingMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("语法分析  被点击");
                jf.setContentPane(parsingPanel);
                jf.setVisible(true);
            }
        });
        //1.3.4 添加"结果展示"菜单栏被点击的函数
        resultShowingMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("结果展示  被点击");
                jf.setContentPane(resultShowingPanel);
                jf.setVisible(true);
            }
        });

        //1.4 将我们创的这个menuBar设置成窗口的菜单栏
        jf.setJMenuBar(menuBar);

        //--------② 搭建 用户输入 界面----------------------------------------------------------------------------------------------------
        //2.1 搭建左侧的文件输入部分
        //2.1.1 创建文本区域, 用于显示相关信息
        final JTextArea fileIputTextArea = new JTextArea("请点击下方的<打开文件>按钮添加您的代码文件...\n",10, 30);
        fileIputTextArea.setLineWrap(false);    // 设置是否自动换行，默认为 false
        fileIputTextArea.setEditable(false);   // 设置文本框是否可编辑
        fileIputTextArea.setFont(textAreaFont);// 设置文本框内文字的字体(这里的字体包括了"字体","大小","样式")

        //2.1.2 创建滚动面板, 指定滚动显示的视图组件(fileIputTextArea), 垂直滚动条一直显示, 水平滚动条从不显示
        JScrollPane fileInputTextAreaScrollPanel = new JScrollPane(
                fileIputTextArea,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        fileInputTextAreaScrollPanel.setLocation(20, 20);
        fileInputTextAreaScrollPanel.setSize(720, 660);
        userInputPanel.add(fileInputTextAreaScrollPanel);

        //2.1.3 创建"打开文件"按钮
        JButton fileInputOpenFileBtn = new JButton("打开文件");
        fileInputOpenFileBtn.setLocation(20, 690);
        fileInputOpenFileBtn.setSize(355, 50);
        fileInputOpenFileBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                showFileOpenDialog(jf, fileIputTextArea);
            }
        });
        userInputPanel.add(fileInputOpenFileBtn);
        //2.1.4 创建"从文件中开始词法分析"按钮
        JButton fileInputWordAnalysisBtn = new JButton("词法分析");
        fileInputWordAnalysisBtn.setLocation(385, 690);
        fileInputWordAnalysisBtn.setSize(355, 50);
        fileInputWordAnalysisBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("用户输入界面 从文件中开始词法分析 按钮 被点击");
                System.out.println("现在的flag为: "+analysisStartFlag) ;
                analysisStartFlag=2;
                System.out.println("现在的flag为: "+analysisStartFlag) ;
                jf.setContentPane(wordAnalysisPanel);
                jf.setVisible(true);
            }
        });
        userInputPanel.add(fileInputWordAnalysisBtn);

        //2.2 搭建右侧的文字输入部分
        //2.2.1 创建文本区域, 用于显示相关信息
        final JTextArea txtInputTextArea = new JTextArea("请于此处添加您的SNL代码.\n添加完成后, 请点击下方的<词法分析>按钮, 进入下一步.\n (添加前请删除此文本)",10, 30);
        txtInputTextArea.setLineWrap(false);    // 设置是否自动换行，默认为 false
        txtInputTextArea.setEditable(true);   // 设置文本框是否可编辑
        txtInputTextArea.setFont(textAreaFont);// 设置文本框内文字的字体(这里的字体包括了"字体","大小","样式")
        //2.2.2 创建滚动面板, 指定滚动显示的视图组件(txtIputTextArea), 垂直滚动条一直显示, 水平滚动条从不显示
        JScrollPane txtInputTextAreaScrollPanel = new JScrollPane(
                txtInputTextArea,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        txtInputTextAreaScrollPanel.setLocation(760, 20);
        txtInputTextAreaScrollPanel.setSize(720, 660);
        userInputPanel.add(txtInputTextAreaScrollPanel);

        //2.2.3 创建"从输入文字中开始词法分析"按钮
        JButton txtInputWordAnalysisBtn = new JButton("词法分析");
        txtInputWordAnalysisBtn.setLocation(1125, 690);
        txtInputWordAnalysisBtn.setSize(355, 50);
        txtInputWordAnalysisBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("现在的flag为: "+analysisStartFlag) ;
                analysisStartFlag=2;
                System.out.println("现在的flag为: "+analysisStartFlag) ;
                writeTextAreatoFile(txtInputTextArea,"./code.txt");
                jf.setContentPane(wordAnalysisPanel);
                jf.setVisible(true);
            }
        });
        userInputPanel.add(txtInputWordAnalysisBtn);

        //2.2.4 创建"保存文件"按钮
        JButton txtInputSaveFileBtn = new JButton("保存文件");
        txtInputSaveFileBtn.setLocation(760,690);
        txtInputSaveFileBtn.setSize(355,50);
        txtInputSaveFileBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showFileSaveDialog(jf,txtInputTextArea);
            }
        });
        userInputPanel.add(txtInputSaveFileBtn);



        //--------③ 搭建 词法分析 界面----------------------------------------------------------------------------------------------------

        //3.1 搭建左侧的 代码确认 区域
        //3.1.1 创建"代码确认"小标题
        JLabel codeConfirmLabel = new JLabel();
        codeConfirmLabel.setText("代码确认");
        codeConfirmLabel.setFont(titleFont);
        codeConfirmLabel.setLocation(20,20);
        codeConfirmLabel.setSize(400,50);
        wordAnalysisPanel.add(codeConfirmLabel);

        //3.1.2 创建文本区域
        final JTextArea codeConfirmTextArea = new JTextArea(10, 30);
        codeConfirmTextArea.setLineWrap(false);      // 设置是否自动换行，默认为 false
        codeConfirmTextArea.setEditable(false);      // 设置文本框是否可编辑
        codeConfirmTextArea.setFont(textAreaFont);  // 设置文本框内文字的字体(这里的字体包括了"字体","大小","样式")
        // 创建滚动面板, 指定滚动显示的视图组件(codeConfirmTextAreaScrollPanel), 垂直滚动条和水平滚动条都一直显示
        JScrollPane codeConfirmTextAreaScrollPanel = new JScrollPane(
                codeConfirmTextArea,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        codeConfirmTextAreaScrollPanel.setLocation(20, 90);
        codeConfirmTextAreaScrollPanel.setSize(373, 640);
        //writeFiletoTextFied("./code.txt",codeConfirmTextArea);
        wordAnalysisPanel.add(codeConfirmTextAreaScrollPanel);

        //3.1.3 创建"开始分析"按钮
        /*JButton analysisStartBtn = new JButton();
        analysisStartBtn.setText("开始分析");
        analysisStartBtn.setLocation(270,20);
        analysisStartBtn.setSize(118,50);
        analysisStartBtn.setFont(buttonFont);
        wordAnalysisPanel.add(analysisStartBtn);*/

        //3.2 搭建中间的 状态图示意 区域
        //3.2.1 "状态图示意"小标题
        JLabel stateDiagramLabel = new JLabel();
        stateDiagramLabel.setText("状态图示意");
        stateDiagramLabel.setLocation(413,20);
        stateDiagramLabel.setSize(400,50);
        stateDiagramLabel.setFont(titleFont);
        wordAnalysisPanel.add(stateDiagramLabel);

        //3.2.2 状态图底图
        ImageIcon statePic=new ImageIcon("src\\UI\\状态图.png");
        JLabel stateDiagramPicLabel = new JLabel();
        stateDiagramPicLabel.setIcon(statePic);
        stateDiagramPicLabel.setLocation(413,90);
        stateDiagramPicLabel.setSize(478,640);
        wordAnalysisPanel.add(stateDiagramPicLabel);


        ///3.3 搭建右侧的 过程展示 区域
        //3.3.1 过程展示 小标题
        JLabel processShowingLable = new JLabel();
        processShowingLable.setText("过程展示");
        processShowingLable.setLocation(911,20);
        processShowingLable.setSize(500,50);
        processShowingLable.setFont(titleFont);
        wordAnalysisPanel.add(processShowingLable);

        //3.3.2 过程展示 文本框
        /*JTextArea processShowingTextArea = new JTextArea();
        processShowingTextArea.setLocation(911,90);
        processShowingTextArea.setSize(555,120);
        processShowingTextArea.setFont(textAreaFont);
        processShowingTextArea.setEditable(false);
        processShowingTextArea.append(" 当前行 : \n 当前字符 : \n 当前词 : \n Token 序列(实时更新) : ");
        processShowingTextArea.setBorder(BorderFactory.createLineBorder(grey));
        wordAnalysisPanel.add(processShowingTextArea);*/

        //3.3.3 Token序列 表格
        /*JTextArea tokenFormHeadlineTextArea = new JTextArea();
        tokenFormHeadlineTextArea.setLocation(911,230);
        tokenFormHeadlineTextArea.setSize(555,30);
        tokenFormHeadlineTextArea.setFont(textAreaFont);
        tokenFormHeadlineTextArea.setEditable(false);
        tokenFormHeadlineTextArea.append(" 行 | 词法信息        | 语义信息");
        tokenFormHeadlineTextArea.setBorder(BorderFactory.createLineBorder(grey));
        wordAnalysisPanel.add(tokenFormHeadlineTextArea);

        JTextArea tokenFormContentTextArea = new JTextArea();
        tokenFormContentTextArea.setFont(textAreaFont);
        wordAnalysisResultShowing(tokenFormContentTextArea);

        JScrollPane tokenFormContentTextAreaScrollPanel = new JScrollPane(
                tokenFormContentTextArea,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        tokenFormContentTextAreaScrollPanel.setLocation(911,259);
        tokenFormContentTextAreaScrollPanel.setSize(555,471);*/

        //3.3.3 Token序列 表格
        JTextArea tokenFormHeadlineTextArea = new JTextArea();
        tokenFormHeadlineTextArea.setLocation(911,90);
        tokenFormHeadlineTextArea.setSize(555,30);
        tokenFormHeadlineTextArea.setFont(textAreaFont);
        tokenFormHeadlineTextArea.setEditable(false);
        tokenFormHeadlineTextArea.append(" 行 | 词法信息        | 语义信息");
        tokenFormHeadlineTextArea.setBorder(BorderFactory.createLineBorder(grey));
        wordAnalysisPanel.add(tokenFormHeadlineTextArea);

        JTextArea tokenFormContentTextArea = new JTextArea();
        tokenFormContentTextArea.setFont(textAreaFont);
        tokenFormContentTextArea.setLineWrap(false);
        //wordAnalysisResultShowing(tokenFormContentTextArea);

        JScrollPane tokenFormContentTextAreaScrollPanel = new JScrollPane(
                tokenFormContentTextArea,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        tokenFormContentTextAreaScrollPanel.setLocation(911,119);
        tokenFormContentTextAreaScrollPanel.setSize(555,611);

        wordAnalysisPanel.add(tokenFormContentTextAreaScrollPanel);

        /*analysisStartBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("词法分析界面的 开始分析 按钮被点击, 开始词法分析");
                if(wordAnalysisProcessShowing("code.txt",tokenFormContentTextArea))
                {
                   tokenFormContentTextArea.append("\n===== 词法分析结束 =====");
                }
            }

        });*/

        //--------④ 搭建 语法分析 界面----------------------------------------------------------------------------------------------------
        //4.1 左侧的 Token序列 区域
        //4.1.1 tokenLabel
        JLabel tokenLabel = new JLabel();
        tokenLabel.setText("Token序列");
        tokenLabel.setFont(titleFont);
        tokenLabel.setLocation(20,20);
        tokenLabel.setSize(300,50);
        parsingPanel.add(tokenLabel);

        //4.1.2 tokenHeadlineTextArea
        JTextArea tokenHeadlineTextArea = new JTextArea();
        tokenHeadlineTextArea.setEditable(false);
        tokenHeadlineTextArea.setLocation(20,90);
        tokenHeadlineTextArea.setSize(400,30);
        tokenHeadlineTextArea.setBorder(BorderFactory.createLineBorder(grey));
        tokenHeadlineTextArea.setFont(textAreaFont);
        tokenHeadlineTextArea.append(" 行 | 词法信息        | 语义信息");
        parsingPanel.add(tokenHeadlineTextArea);

        //4.1.3 tokenContentTextAreaScrollPanel
        //tokenContentTextArea
        JTextArea tokenContentTextArea = new JTextArea();
        tokenContentTextArea.setEditable(false);
        tokenContentTextArea.setFont(textAreaFont);
        tokenContentTextArea.setLineWrap(false);
        //wordAnalysisResultShowing(tokenContentTextArea);

        JScrollPane tokenContentTextAreaScrollPanel = new JScrollPane(
                tokenContentTextArea,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        tokenContentTextAreaScrollPanel.setSize(400,611);
        tokenContentTextAreaScrollPanel.setLocation(20,119);
        parsingPanel.add(tokenContentTextAreaScrollPanel);

        //4.1.4 parsingStartingBtn
        /*
        JButton parsingStartingBtn = new JButton();
        parsingStartingBtn.setText("开始分析");
        parsingStartingBtn.setLocation(300,20);
        parsingStartingBtn.setSize(120,50);
        parsingStartingBtn.setFont(buttonFont);
        parsingStartingBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("按钮 开始语法分析 被点击");
            }
        });
        parsingPanel.add(parsingStartingBtn);*/


        //4.2 中部 分析过程 区域
        //4.2.1 analysisProcessLabel
        JLabel analysisProcessLabel = new JLabel();
        analysisProcessLabel.setText("分析过程");
        analysisProcessLabel.setLocation(440,20);
        analysisProcessLabel.setSize(506,50);
        analysisProcessLabel.setFont(titleFont);
        parsingPanel.add(analysisProcessLabel);
        //4.2.2 analysisProcessTextAreaScrollPanel
        //analysisProcessTextArea
        JTextArea analysisProcessTextArea = new JTextArea();
        analysisProcessTextArea.setEditable(false);
        analysisProcessTextArea.setFont(textAreaFont);
        analysisProcessTextArea.setLineWrap(false);
        //writeFiletoTextFied("src\\UI\\ParsingResult.txt",analysisProcessTextArea);

        JScrollPane analysisProcessTextAreaScrollPanel = new JScrollPane(
                analysisProcessTextArea,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        analysisProcessTextAreaScrollPanel.setLocation(440,90);
        analysisProcessTextAreaScrollPanel.setSize(506,640);
        parsingPanel.add(analysisProcessTextAreaScrollPanel);


        //4.3 右侧 语法树 区域
        //4.3.1 parsingTreeLabel
        JLabel parsingTreeLabel = new JLabel();
        parsingTreeLabel.setText("语法树");
        parsingTreeLabel.setFont(titleFont);
        parsingTreeLabel.setLocation(966,20);
        parsingTreeLabel.setSize(506,50);
        parsingPanel.add(parsingTreeLabel);
        //4.3.2 parsingTreePanel
        //从别的模块里面调出来的
        //treepanel = new TreePanel() ;
        //treepanel.setPreferredSize( new Dimension( Width , High ) ) ;
        JScrollPane pane = new JScrollPane(
                treepanel,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        pane.setLocation(966,90);
        pane.setSize(506,640);
        parsingPanel.add( pane ) ;

        //--------⑤ 搭建 结果展示 界面----------------------------------------------------------------------------------------------------
        //5.1 左侧 词法分析结果 区域
        //5.1.1 wordAnalysisResultLabel
        JLabel wordAnalysisResultLabel = new JLabel();
        wordAnalysisResultLabel.setText("词法分析结果");
        wordAnalysisResultLabel.setFont(titleFont);
        wordAnalysisResultLabel.setLocation(20,20);
        wordAnalysisResultLabel.setSize(400,50);
        resultShowingPanel.add(wordAnalysisResultLabel);

        //5.1.2 wordAnalysisResultHeadlineTextArea
        JTextArea wordAnalysisResultHeadlineTextArea = new JTextArea();
        wordAnalysisResultHeadlineTextArea.setEditable(false);
        wordAnalysisResultHeadlineTextArea.setSize(400,30);
        wordAnalysisResultHeadlineTextArea.setLocation(20,90);
        wordAnalysisResultHeadlineTextArea.setFont(textAreaFont);
        wordAnalysisResultHeadlineTextArea.setBorder(BorderFactory.createLineBorder(grey));
        wordAnalysisResultHeadlineTextArea.append(" 行 | 词法信息        | 语义信息");
        resultShowingPanel.add(wordAnalysisResultHeadlineTextArea);

        //5.1.3 wordAnalysisResultContentTextAreaScrollPanel
        //wordAnalysisResultContentTextArea
        JTextArea wordAnalysisResultContentTextArea = new JTextArea();
        wordAnalysisResultContentTextArea.setFont(textAreaFont);
        wordAnalysisResultContentTextArea.setEditable(false);
        wordAnalysisResultContentTextArea.setLineWrap(false);
        //wordAnalysisResultShowing(wordAnalysisResultContentTextArea);

        JScrollPane wordAnalysisResultContentTextAreaScrollPanel = new JScrollPane(
                wordAnalysisResultContentTextArea,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        wordAnalysisResultContentTextAreaScrollPanel.setLocation(20,119);
        wordAnalysisResultContentTextAreaScrollPanel.setSize(400,611);
        resultShowingPanel.add(wordAnalysisResultContentTextAreaScrollPanel);

        //5.2 中部 语法分析结果 区域
        //5.2.1 parsingResultLabel
        JLabel parsingResultLabel = new JLabel();
        parsingResultLabel.setText("语法分析结果");
        parsingResultLabel.setLocation(440,20);
        parsingResultLabel.setSize(506,50);
        parsingResultLabel.setFont(titleFont);
        resultShowingPanel.add(parsingResultLabel);
        //5.2.2 parsingResultTextAreaScrollPanel
        //parsingResultTextArea
        JTextArea parsingResultTextArea = new JTextArea();
        parsingResultTextArea.setFont(textAreaFont);
        parsingResultTextArea.setEditable(false);
        parsingResultTextArea.setLineWrap(false);
        //writeFiletoTextFied("src\\UI\\ParsingResult.txt",parsingResultTextArea);

        JScrollPane parsingResultTextAreaScrollPanel = new JScrollPane(
                parsingResultTextArea,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        parsingResultTextAreaScrollPanel.setSize(506,640);
        parsingResultTextAreaScrollPanel.setLocation(440,90);
        resultShowingPanel.add(parsingResultTextAreaScrollPanel);


        //5.3 右侧 语法树 区域
        //5.3.1 parsingTreeResultLabel
        JLabel parsingTreeResultLabel = new JLabel();
        parsingTreeResultLabel.setText("语法树");
        parsingTreeResultLabel.setFont(titleFont);
        parsingTreeResultLabel.setSize(506,50);
        parsingTreeResultLabel.setLocation(966,20);
        resultShowingPanel.add(parsingTreeResultLabel);
        //4.3.2 parsingTreePanel
        //从别的模块里面调出来的
        //Panel = new TreePanel() ;
        //Panel.setPreferredSize( new Dimension( Width , High ) ) ;
        JScrollPane pane2 = new JScrollPane(
                Panel,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        pane2.setLocation(966,90);
        pane2.setSize(506,640);
        resultShowingPanel.add( pane2 ) ;


        // 三 把 面板容器 作为窗口的内容面板 设置到 窗口
        jf.setContentPane(userInputPanel);
        //jf.setContentPane(wordAnalysisPanel);
        //jf.setContentPane(parsingPanel);
        //jf.setContentPane(resultShowingPanel);

        // 四 显示窗口，前面创建的信息都在内存中，通过 jf.setVisible(true) 把内存中的窗口显示在屏幕上。
        jf.setVisible(true);



    }

    public static void Creating()
    {
        analysisStartFlag=1;

        // 一 创建一个顶层容器（窗口）
        //JFrame jf = new JFrame();          // 创建窗口
        ImageIcon icon=new ImageIcon("分析.png");  //设置窗口图标
        jf.setIconImage(icon.getImage());           //设置窗口图标
        jf.setTitle("小组 2117-30-03 出品");        // 设置窗口名称
        jf.setSize(1500, 805);      // 设置窗口大小
        jf.setLocationRelativeTo(null);            // 把窗口位置设置到屏幕中心
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // 当点击窗口的关闭按钮时退出程序（没有这一句，程序不会退出）
        jf.setResizable(false);                    //将窗口设置为不随意拖拽变换大小,防止老师随便乱动导致的组件变乱
        // 创建一批字体, 以便统一使用
        Font textAreaFont = new Font("微软雅黑",0,20);  //输入框内字体
        Font titleFont = new Font("微软雅黑",1,40); //词法分析界面上方标题的字体
        Font buttonFont = new Font("微软雅黑",0,15);    //按钮上的字体
        Color grey = new Color(125,125,125);

        // 二 创建中间容器（面板容器）
        JPanel userInputPanel = new JPanel(null);       //用户输入界面 // 创建面板容器，使用绝对布局, 也就是说这个panel里面所有的组件都需要自己确定好自己的位置和大小
        JPanel wordAnalysisPanel = new JPanel(null);    //词法分析界面
        JPanel parsingPanel = new JPanel(null);         //语法分析界面
        JPanel resultShowingPanel = new JPanel(null);   //结果展示界面

        //--------① 创建菜单栏----------------------------------------------------------------------------------------------------
        JMenuBar menuBar = new JMenuBar();

        //1.1 创建一级菜单
        JMenuItem userInputMenuItem = new JMenuItem("用户输入");
        JMenuItem wordAnalysisMenuItem = new JMenuItem("词法分析");
        JMenuItem parsingMenuItem = new JMenuItem("语法分析");
        JMenuItem resultShowingMenuItem = new JMenuItem("结果展示");

        //1.2 一级菜单添加到菜单栏
        menuBar.add(userInputMenuItem);
        menuBar.add(wordAnalysisMenuItem);
        menuBar.add(parsingMenuItem);
        menuBar.add(resultShowingMenuItem);

        //1.3 添加菜单栏的点击响应函数
        //1.3.1 添加"用户输入"菜单栏被点击的函数
        userInputMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("用户输入  被点击");
                jf.setContentPane(userInputPanel);
                jf.setVisible(true);
            }
        });
        //1.3.2 添加"词法分析"菜单栏被点击的函数
        wordAnalysisMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("词法分析  被点击");
                jf.setContentPane(wordAnalysisPanel);
                jf.setVisible(true);
            }
        });
        //1.3.3 添加"语法分析"菜单栏被点击的函数
        parsingMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("语法分析  被点击");
                jf.setContentPane(parsingPanel);
                jf.setVisible(true);
            }
        });
        //1.3.4 添加"结果展示"菜单栏被点击的函数
        resultShowingMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("结果展示  被点击");
                jf.setContentPane(resultShowingPanel);
                jf.setVisible(true);
            }
        });

        //1.4 将我们创的这个menuBar设置成窗口的菜单栏
        jf.setJMenuBar(menuBar);

        //--------② 搭建 用户输入 界面----------------------------------------------------------------------------------------------------
        //2.1 搭建左侧的文件输入部分
        //2.1.1 创建文本区域, 用于显示相关信息
        final JTextArea fileIputTextArea = new JTextArea("请点击下方的<打开文件>按钮添加您的代码文件...\n",10, 30);
        fileIputTextArea.setLineWrap(false);    // 设置是否自动换行，默认为 false
        fileIputTextArea.setEditable(false);   // 设置文本框是否可编辑
        fileIputTextArea.setFont(textAreaFont);// 设置文本框内文字的字体(这里的字体包括了"字体","大小","样式")

        //2.1.2 创建滚动面板, 指定滚动显示的视图组件(fileIputTextArea), 垂直滚动条一直显示, 水平滚动条从不显示
        JScrollPane fileInputTextAreaScrollPanel = new JScrollPane(
                fileIputTextArea,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        fileInputTextAreaScrollPanel.setLocation(20, 20);
        fileInputTextAreaScrollPanel.setSize(720, 660);
        userInputPanel.add(fileInputTextAreaScrollPanel);

        //2.1.3 创建"打开文件"按钮
        JButton fileInputOpenFileBtn = new JButton("打开文件");
        fileInputOpenFileBtn.setLocation(20, 690);
        fileInputOpenFileBtn.setSize(355, 50);
        fileInputOpenFileBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                showFileOpenDialog(jf, fileIputTextArea);
            }
        });
        userInputPanel.add(fileInputOpenFileBtn);
        //2.1.4 创建"从文件中开始词法分析"按钮
        JButton fileInputWordAnalysisBtn = new JButton("词法分析");
        fileInputWordAnalysisBtn.setLocation(385, 690);
        fileInputWordAnalysisBtn.setSize(355, 50);
        fileInputWordAnalysisBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("用户输入界面 从文件中开始词法分析 按钮 被点击");
                System.out.println("现在的flag为: "+analysisStartFlag) ;
                analysisStartFlag=2;
                System.out.println("现在的flag为: "+analysisStartFlag) ;

                jf.setContentPane(wordAnalysisPanel);
                jf.setVisible(true);
            }
        });
        userInputPanel.add(fileInputWordAnalysisBtn);

        //2.2 搭建右侧的文字输入部分
        //2.2.1 创建文本区域, 用于显示相关信息
        final JTextArea txtInputTextArea = new JTextArea("请于此处添加您的SNL代码.\n添加完成后, 请点击下方的<词法分析>按钮, 进入下一步.\n (添加前请删除此文本)",10, 30);
        txtInputTextArea.setLineWrap(false);    // 设置是否自动换行，默认为 false
        txtInputTextArea.setEditable(true);   // 设置文本框是否可编辑
        txtInputTextArea.setFont(textAreaFont);// 设置文本框内文字的字体(这里的字体包括了"字体","大小","样式")
        //2.2.2 创建滚动面板, 指定滚动显示的视图组件(txtIputTextArea), 垂直滚动条一直显示, 水平滚动条从不显示
        JScrollPane txtInputTextAreaScrollPanel = new JScrollPane(
                txtInputTextArea,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        txtInputTextAreaScrollPanel.setLocation(760, 20);
        txtInputTextAreaScrollPanel.setSize(720, 660);
        userInputPanel.add(txtInputTextAreaScrollPanel);

        //2.2.3 创建"从输入文字中开始词法分析"按钮
        JButton txtInputWordAnalysisBtn = new JButton("词法分析");
        txtInputWordAnalysisBtn.setLocation(1125, 690);
        txtInputWordAnalysisBtn.setSize(355, 50);
        txtInputWordAnalysisBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("现在的flag为: "+analysisStartFlag) ;
                analysisStartFlag=2;
                System.out.println("现在的flag为: "+analysisStartFlag) ;

                writeTextAreatoFile(txtInputTextArea,"./code.txt");
                jf.setContentPane(wordAnalysisPanel);
                jf.setVisible(true);
            }
        });
        userInputPanel.add(txtInputWordAnalysisBtn);

        //2.2.4 创建"保存文件"按钮
        JButton txtInputSaveFileBtn = new JButton("保存文件");
        txtInputSaveFileBtn.setLocation(760,690);
        txtInputSaveFileBtn.setSize(355,50);
        txtInputSaveFileBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showFileSaveDialog(jf,txtInputTextArea);
            }
        });
        userInputPanel.add(txtInputSaveFileBtn);



        //--------③ 搭建 词法分析 界面----------------------------------------------------------------------------------------------------

        //3.1 搭建左侧的 代码确认 区域
        //3.1.1 创建"代码确认"小标题
        JLabel codeConfirmLabel = new JLabel();
        codeConfirmLabel.setText("代码确认");
        codeConfirmLabel.setFont(titleFont);
        codeConfirmLabel.setLocation(20,20);
        codeConfirmLabel.setSize(400,50);
        wordAnalysisPanel.add(codeConfirmLabel);

        //3.1.2 创建文本区域
        final JTextArea codeConfirmTextArea = new JTextArea(10, 30);
        codeConfirmTextArea.setLineWrap(false);      // 设置是否自动换行，默认为 false
        codeConfirmTextArea.setEditable(false);      // 设置文本框是否可编辑
        codeConfirmTextArea.setFont(textAreaFont);  // 设置文本框内文字的字体(这里的字体包括了"字体","大小","样式")
        // 创建滚动面板, 指定滚动显示的视图组件(codeConfirmTextAreaScrollPanel), 垂直滚动条和水平滚动条都一直显示
        JScrollPane codeConfirmTextAreaScrollPanel = new JScrollPane(
                codeConfirmTextArea,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        codeConfirmTextAreaScrollPanel.setLocation(20, 90);
        codeConfirmTextAreaScrollPanel.setSize(373, 640);
        writeFiletoTextFied("./code.txt",codeConfirmTextArea);
        wordAnalysisPanel.add(codeConfirmTextAreaScrollPanel);

        //3.1.3 创建"开始分析"按钮
        /*JButton analysisStartBtn = new JButton();
        analysisStartBtn.setText("开始分析");
        analysisStartBtn.setLocation(270,20);
        analysisStartBtn.setSize(118,50);
        analysisStartBtn.setFont(buttonFont);
        wordAnalysisPanel.add(analysisStartBtn);*/

        //3.2 搭建中间的 状态图示意 区域
        //3.2.1 "状态图示意"小标题
        JLabel stateDiagramLabel = new JLabel();
        stateDiagramLabel.setText("状态图示意");
        stateDiagramLabel.setLocation(413,20);
        stateDiagramLabel.setSize(400,50);
        stateDiagramLabel.setFont(titleFont);
        wordAnalysisPanel.add(stateDiagramLabel);

        //3.2.2 状态图底图
        ImageIcon statePic=new ImageIcon("src\\UI\\状态图.png");
        JLabel stateDiagramPicLabel = new JLabel();
        stateDiagramPicLabel.setIcon(statePic);
        stateDiagramPicLabel.setLocation(413,90);
        stateDiagramPicLabel.setSize(478,640);
        wordAnalysisPanel.add(stateDiagramPicLabel);


        ///3.3 搭建右侧的 过程展示 区域
        //3.3.1 过程展示 小标题
        JLabel processShowingLable = new JLabel();
        processShowingLable.setText("过程展示");
        processShowingLable.setLocation(911,20);
        processShowingLable.setSize(500,50);
        processShowingLable.setFont(titleFont);
        wordAnalysisPanel.add(processShowingLable);

        //3.3.2 过程展示 文本框
        /*JTextArea processShowingTextArea = new JTextArea();
        processShowingTextArea.setLocation(911,90);
        processShowingTextArea.setSize(555,120);
        processShowingTextArea.setFont(textAreaFont);
        processShowingTextArea.setEditable(false);
        processShowingTextArea.append(" 当前行 : \n 当前字符 : \n 当前词 : \n Token 序列(实时更新) : ");
        processShowingTextArea.setBorder(BorderFactory.createLineBorder(grey));
        wordAnalysisPanel.add(processShowingTextArea);*/

        //3.3.3 Token序列 表格
        /*JTextArea tokenFormHeadlineTextArea = new JTextArea();
        tokenFormHeadlineTextArea.setLocation(911,230);
        tokenFormHeadlineTextArea.setSize(555,30);
        tokenFormHeadlineTextArea.setFont(textAreaFont);
        tokenFormHeadlineTextArea.setEditable(false);
        tokenFormHeadlineTextArea.append(" 行 | 词法信息        | 语义信息");
        tokenFormHeadlineTextArea.setBorder(BorderFactory.createLineBorder(grey));
        wordAnalysisPanel.add(tokenFormHeadlineTextArea);

        JTextArea tokenFormContentTextArea = new JTextArea();
        tokenFormContentTextArea.setFont(textAreaFont);
        wordAnalysisResultShowing(tokenFormContentTextArea);

        JScrollPane tokenFormContentTextAreaScrollPanel = new JScrollPane(
                tokenFormContentTextArea,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        tokenFormContentTextAreaScrollPanel.setLocation(911,259);
        tokenFormContentTextAreaScrollPanel.setSize(555,471);*/

        //3.3.3 Token序列 表格
        JTextArea tokenFormHeadlineTextArea = new JTextArea();
        tokenFormHeadlineTextArea.setLocation(911,90);
        tokenFormHeadlineTextArea.setSize(555,30);
        tokenFormHeadlineTextArea.setFont(textAreaFont);
        tokenFormHeadlineTextArea.setEditable(false);
        tokenFormHeadlineTextArea.append(" 行 | 词法信息        | 语义信息");
        tokenFormHeadlineTextArea.setLineWrap(false);
        tokenFormHeadlineTextArea.setBorder(BorderFactory.createLineBorder(grey));
        wordAnalysisPanel.add(tokenFormHeadlineTextArea);

        JTextArea tokenFormContentTextArea = new JTextArea();
        tokenFormContentTextArea.setFont(textAreaFont);
        wordAnalysisResultShowing(tokenFormContentTextArea);

        JScrollPane tokenFormContentTextAreaScrollPanel = new JScrollPane(
                tokenFormContentTextArea,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        tokenFormContentTextAreaScrollPanel.setLocation(911,119);
        tokenFormContentTextAreaScrollPanel.setSize(555,611);

        wordAnalysisPanel.add(tokenFormContentTextAreaScrollPanel);

        /*analysisStartBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("词法分析界面的 开始分析 按钮被点击, 开始词法分析");
                if(wordAnalysisProcessShowing("code.txt",tokenFormContentTextArea))
                {
                   tokenFormContentTextArea.append("\n===== 词法分析结束 =====");
                }
            }

        });*/

        //--------④ 搭建 语法分析 界面----------------------------------------------------------------------------------------------------
        //4.1 左侧的 Token序列 区域
        //4.1.1 tokenLabel
        JLabel tokenLabel = new JLabel();
        tokenLabel.setText("Token序列");
        tokenLabel.setFont(titleFont);
        tokenLabel.setLocation(20,20);
        tokenLabel.setSize(300,50);
        parsingPanel.add(tokenLabel);

        //4.1.2 tokenHeadlineTextArea
        JTextArea tokenHeadlineTextArea = new JTextArea();
        tokenHeadlineTextArea.setEditable(false);
        tokenHeadlineTextArea.setLocation(20,90);
        tokenHeadlineTextArea.setSize(400,30);
        tokenHeadlineTextArea.setBorder(BorderFactory.createLineBorder(grey));
        tokenHeadlineTextArea.setLineWrap(false);
        tokenHeadlineTextArea.setFont(textAreaFont);
        tokenHeadlineTextArea.append(" 行 | 词法信息        | 语义信息");
        parsingPanel.add(tokenHeadlineTextArea);

        //4.1.3 tokenContentTextAreaScrollPanel
        //tokenContentTextArea
        JTextArea tokenContentTextArea = new JTextArea();
        tokenContentTextArea.setEditable(false);
        tokenContentTextArea.setFont(textAreaFont);
        wordAnalysisResultShowing(tokenContentTextArea);

        JScrollPane tokenContentTextAreaScrollPanel = new JScrollPane(
                tokenContentTextArea,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        tokenContentTextAreaScrollPanel.setSize(400,611);
        tokenContentTextAreaScrollPanel.setLocation(20,119);
        parsingPanel.add(tokenContentTextAreaScrollPanel);

        //4.1.4 parsingStartingBtn
        /*
        JButton parsingStartingBtn = new JButton();
        parsingStartingBtn.setText("开始分析");
        parsingStartingBtn.setLocation(300,20);
        parsingStartingBtn.setSize(120,50);
        parsingStartingBtn.setFont(buttonFont);
        parsingStartingBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("按钮 开始语法分析 被点击");
            }
        });
        parsingPanel.add(parsingStartingBtn);*/


        //4.2 中部 分析过程 区域
        //4.2.1 analysisProcessLabel
        JLabel analysisProcessLabel = new JLabel();
        analysisProcessLabel.setText("分析过程");
        analysisProcessLabel.setLocation(440,20);
        analysisProcessLabel.setSize(506,50);
        analysisProcessLabel.setFont(titleFont);
        parsingPanel.add(analysisProcessLabel);
        //4.2.2 analysisProcessTextAreaScrollPanel
        //analysisProcessTextArea
        JTextArea analysisProcessTextArea = new JTextArea();
        analysisProcessTextArea.setEditable(false);
        analysisProcessTextArea.setFont(textAreaFont);
        analysisProcessTextArea.setLineWrap(false);
        writeFiletoTextFied("src\\UI\\ParsingResult.txt",analysisProcessTextArea);

        JScrollPane analysisProcessTextAreaScrollPanel = new JScrollPane(
                analysisProcessTextArea,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        analysisProcessTextAreaScrollPanel.setLocation(440,90);
        analysisProcessTextAreaScrollPanel.setSize(506,640);
        parsingPanel.add(analysisProcessTextAreaScrollPanel);


        //4.3 右侧 语法树 区域
        //4.3.1 parsingTreeLabel
        JLabel parsingTreeLabel = new JLabel();
        parsingTreeLabel.setText("语法树");
        parsingTreeLabel.setFont(titleFont);
        parsingTreeLabel.setLocation(966,20);
        parsingTreeLabel.setSize(506,50);
        parsingPanel.add(parsingTreeLabel);
        //4.3.2 parsingTreePanel
        //从别的模块里面调出来的
        treepanel = new TreePanel() ;
        treepanel.setPreferredSize( new Dimension( Width , High ) ) ;
        JScrollPane pane = new JScrollPane(
                treepanel,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        pane.setLocation(966,90);
        pane.setSize(506,640);
        parsingPanel.add( pane ) ;

        //--------⑤ 搭建 结果展示 界面----------------------------------------------------------------------------------------------------
        //5.1 左侧 词法分析结果 区域
        //5.1.1 wordAnalysisResultLabel
        JLabel wordAnalysisResultLabel = new JLabel();
        wordAnalysisResultLabel.setText("词法分析结果");
        wordAnalysisResultLabel.setFont(titleFont);
        wordAnalysisResultLabel.setLocation(20,20);
        wordAnalysisResultLabel.setSize(400,50);
        resultShowingPanel.add(wordAnalysisResultLabel);

        //5.1.2 wordAnalysisResultHeadlineTextArea
        JTextArea wordAnalysisResultHeadlineTextArea = new JTextArea();
        wordAnalysisResultHeadlineTextArea.setEditable(false);
        wordAnalysisResultHeadlineTextArea.setSize(400,30);
        wordAnalysisResultHeadlineTextArea.setLocation(20,90);
        wordAnalysisResultHeadlineTextArea.setFont(textAreaFont);
        wordAnalysisResultHeadlineTextArea.setBorder(BorderFactory.createLineBorder(grey));
        wordAnalysisResultHeadlineTextArea.append(" 行 | 词法信息        | 语义信息");
        resultShowingPanel.add(wordAnalysisResultHeadlineTextArea);

        //5.1.3 wordAnalysisResultContentTextAreaScrollPanel
        //wordAnalysisResultContentTextArea
        JTextArea wordAnalysisResultContentTextArea = new JTextArea();
        wordAnalysisResultContentTextArea.setFont(textAreaFont);
        wordAnalysisResultContentTextArea.setEditable(false);
        wordAnalysisResultContentTextArea.setLineWrap(false);
        wordAnalysisResultShowing(wordAnalysisResultContentTextArea);

        JScrollPane wordAnalysisResultContentTextAreaScrollPanel = new JScrollPane(
                wordAnalysisResultContentTextArea,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        wordAnalysisResultContentTextAreaScrollPanel.setLocation(20,119);
        wordAnalysisResultContentTextAreaScrollPanel.setSize(400,611);
        resultShowingPanel.add(wordAnalysisResultContentTextAreaScrollPanel);

        //5.2 中部 语法分析结果 区域
        //5.2.1 parsingResultLabel
        JLabel parsingResultLabel = new JLabel();
        parsingResultLabel.setText("语法分析结果");
        parsingResultLabel.setLocation(440,20);
        parsingResultLabel.setSize(506,50);
        parsingResultLabel.setFont(titleFont);
        resultShowingPanel.add(parsingResultLabel);
        //5.2.2 parsingResultTextAreaScrollPanel
        //parsingResultTextArea
        JTextArea parsingResultTextArea = new JTextArea();
        parsingResultTextArea.setFont(textAreaFont);
        parsingResultTextArea.setEditable(false);
        parsingResultTextArea.setLineWrap(false);
        writeFiletoTextFied("src\\UI\\ParsingResult.txt",parsingResultTextArea);

        JScrollPane parsingResultTextAreaScrollPanel = new JScrollPane(
                parsingResultTextArea,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        parsingResultTextAreaScrollPanel.setSize(506,640);
        parsingResultTextAreaScrollPanel.setLocation(440,90);
        resultShowingPanel.add(parsingResultTextAreaScrollPanel);


        //5.3 右侧 语法树 区域
        //5.3.1 parsingTreeResultLabel
        JLabel parsingTreeResultLabel = new JLabel();
        parsingTreeResultLabel.setText("语法树");
        parsingTreeResultLabel.setFont(titleFont);
        parsingTreeResultLabel.setSize(506,50);
        parsingTreeResultLabel.setLocation(966,20);
        resultShowingPanel.add(parsingTreeResultLabel);
        //4.3.2 parsingTreePanel
        //从别的模块里面调出来的
        Panel = new TreePanel() ;
        Panel.setPreferredSize( new Dimension( Width , High ) ) ;
        JScrollPane pane2 = new JScrollPane(
                Panel,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        pane2.setLocation(966,90);
        pane2.setSize(506,640);
        resultShowingPanel.add( pane2 ) ;


        // 三 把 面板容器 作为窗口的内容面板 设置到 窗口
        //jf.setContentPane(userInputPanel);
        jf.setContentPane(wordAnalysisPanel);
        //jf.setContentPane(parsingPanel);
        //jf.setContentPane(resultShowingPanel);

        // 四 显示窗口，前面创建的信息都在内存中，通过 jf.setVisible(true) 把内存中的窗口显示在屏幕上。
        jf.setVisible(true);



    }

    public static void ParsingWrongCreating()
    {
        analysisStartFlag = 1;

        // 一 创建一个顶层容器（窗口）
        //JFrame jf = new JFrame();          // 创建窗口
        ImageIcon icon=new ImageIcon("分析.png");  //设置窗口图标
        jf.setIconImage(icon.getImage());           //设置窗口图标
        jf.setTitle("小组 2117-30-03 出品");        // 设置窗口名称
        jf.setSize(1500, 805);      // 设置窗口大小
        jf.setLocationRelativeTo(null);            // 把窗口位置设置到屏幕中心
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // 当点击窗口的关闭按钮时退出程序（没有这一句，程序不会退出）
        jf.setResizable(false);                    //将窗口设置为不随意拖拽变换大小,防止老师随便乱动导致的组件变乱
        // 创建一批字体, 以便统一使用
        Font textAreaFont = new Font("微软雅黑",0,20);  //输入框内字体
        Font titleFont = new Font("微软雅黑",1,40); //词法分析界面上方标题的字体
        Font buttonFont = new Font("微软雅黑",0,15);    //按钮上的字体
        Color grey = new Color(125,125,125);

        // 二 创建中间容器（面板容器）
        JPanel userInputPanel = new JPanel(null);       //用户输入界面 // 创建面板容器，使用绝对布局, 也就是说这个panel里面所有的组件都需要自己确定好自己的位置和大小
        JPanel wordAnalysisPanel = new JPanel(null);    //词法分析界面
        JPanel parsingPanel = new JPanel(null);         //语法分析界面
        JPanel resultShowingPanel = new JPanel(null);   //结果展示界面

        //--------① 创建菜单栏----------------------------------------------------------------------------------------------------
        JMenuBar menuBar = new JMenuBar();

        //1.1 创建一级菜单
        JMenuItem userInputMenuItem = new JMenuItem("用户输入");
        JMenuItem wordAnalysisMenuItem = new JMenuItem("词法分析");
        JMenuItem parsingMenuItem = new JMenuItem("语法分析");
        JMenuItem resultShowingMenuItem = new JMenuItem("结果展示");

        //1.2 一级菜单添加到菜单栏
        menuBar.add(userInputMenuItem);
        menuBar.add(wordAnalysisMenuItem);
        menuBar.add(parsingMenuItem);
        menuBar.add(resultShowingMenuItem);

        //1.3 添加菜单栏的点击响应函数
        //1.3.1 添加"用户输入"菜单栏被点击的函数
        userInputMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("用户输入  被点击");
                jf.setContentPane(userInputPanel);
                jf.setVisible(true);
            }
        });
        //1.3.2 添加"词法分析"菜单栏被点击的函数
        wordAnalysisMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("词法分析  被点击");
                jf.setContentPane(wordAnalysisPanel);
                jf.setVisible(true);
            }
        });
        //1.3.3 添加"语法分析"菜单栏被点击的函数
        parsingMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("语法分析  被点击");
                jf.setContentPane(parsingPanel);
                jf.setVisible(true);
            }
        });
        //1.3.4 添加"结果展示"菜单栏被点击的函数
        resultShowingMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("结果展示  被点击");
                jf.setContentPane(resultShowingPanel);
                jf.setVisible(true);
            }
        });

        //1.4 将我们创的这个menuBar设置成窗口的菜单栏
        jf.setJMenuBar(menuBar);

        //--------② 搭建 用户输入 界面----------------------------------------------------------------------------------------------------
        //2.1 搭建左侧的文件输入部分
        //2.1.1 创建文本区域, 用于显示相关信息
        final JTextArea fileIputTextArea = new JTextArea("请点击下方的<打开文件>按钮添加您的代码文件...\n",10, 30);
        fileIputTextArea.setLineWrap(false);    // 设置是否自动换行，默认为 false
        fileIputTextArea.setEditable(false);   // 设置文本框是否可编辑
        fileIputTextArea.setFont(textAreaFont);// 设置文本框内文字的字体(这里的字体包括了"字体","大小","样式")

        //2.1.2 创建滚动面板, 指定滚动显示的视图组件(fileIputTextArea), 垂直滚动条一直显示, 水平滚动条从不显示
        JScrollPane fileInputTextAreaScrollPanel = new JScrollPane(
                fileIputTextArea,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        fileInputTextAreaScrollPanel.setLocation(20, 20);
        fileInputTextAreaScrollPanel.setSize(720, 660);
        userInputPanel.add(fileInputTextAreaScrollPanel);

        //2.1.3 创建"打开文件"按钮
        JButton fileInputOpenFileBtn = new JButton("打开文件");
        fileInputOpenFileBtn.setLocation(20, 690);
        fileInputOpenFileBtn.setSize(355, 50);
        fileInputOpenFileBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                showFileOpenDialog(jf, fileIputTextArea);
            }
        });
        userInputPanel.add(fileInputOpenFileBtn);
        //2.1.4 创建"从文件中开始词法分析"按钮
        JButton fileInputWordAnalysisBtn = new JButton("词法分析");
        fileInputWordAnalysisBtn.setLocation(385, 690);
        fileInputWordAnalysisBtn.setSize(355, 50);
        fileInputWordAnalysisBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("用户输入界面 从文件中开始词法分析 按钮 被点击");
                System.out.println("目前的analysisStartFlag为: "+analysisStartFlag);
                analysisStartFlag = 2;
                System.out.println("目前的analysisStartFlag为: "+analysisStartFlag);
                jf.setContentPane(wordAnalysisPanel);
                jf.setVisible(true);
            }
        });
        userInputPanel.add(fileInputWordAnalysisBtn);

        //2.2 搭建右侧的文字输入部分
        //2.2.1 创建文本区域, 用于显示相关信息
        final JTextArea txtInputTextArea = new JTextArea("请于此处添加您的SNL代码.\n添加完成后, 请点击下方的<词法分析>按钮, 进入下一步.\n (添加前请删除此文本)",10, 30);
        txtInputTextArea.setLineWrap(false);    // 设置是否自动换行，默认为 false
        txtInputTextArea.setEditable(true);   // 设置文本框是否可编辑
        txtInputTextArea.setFont(textAreaFont);// 设置文本框内文字的字体(这里的字体包括了"字体","大小","样式")
        //2.2.2 创建滚动面板, 指定滚动显示的视图组件(txtIputTextArea), 垂直滚动条一直显示, 水平滚动条从不显示
        JScrollPane txtInputTextAreaScrollPanel = new JScrollPane(
                txtInputTextArea,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        txtInputTextAreaScrollPanel.setLocation(760, 20);
        txtInputTextAreaScrollPanel.setSize(720, 660);
        userInputPanel.add(txtInputTextAreaScrollPanel);

        //2.2.3 创建"从输入文字中开始词法分析"按钮
        JButton txtInputWordAnalysisBtn = new JButton("词法分析");
        txtInputWordAnalysisBtn.setLocation(1125, 690);
        txtInputWordAnalysisBtn.setSize(355, 50);
        txtInputWordAnalysisBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("目前的analysisStartFlag为: "+analysisStartFlag);
                analysisStartFlag = 2;
                System.out.println("目前的analysisStartFlag为: "+analysisStartFlag);

                writeTextAreatoFile(txtInputTextArea,"./code.txt");
                jf.setContentPane(wordAnalysisPanel);
                jf.setVisible(true);
            }
        });
        userInputPanel.add(txtInputWordAnalysisBtn);

        //2.2.4 创建"保存文件"按钮
        JButton txtInputSaveFileBtn = new JButton("保存文件");
        txtInputSaveFileBtn.setLocation(760,690);
        txtInputSaveFileBtn.setSize(355,50);
        txtInputSaveFileBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showFileSaveDialog(jf,txtInputTextArea);
            }
        });
        userInputPanel.add(txtInputSaveFileBtn);



        //--------③ 搭建 词法分析 界面----------------------------------------------------------------------------------------------------

        //3.1 搭建左侧的 代码确认 区域
        //3.1.1 创建"代码确认"小标题
        JLabel codeConfirmLabel = new JLabel();
        codeConfirmLabel.setText("代码确认");
        codeConfirmLabel.setFont(titleFont);
        codeConfirmLabel.setLocation(20,20);
        codeConfirmLabel.setSize(400,50);
        wordAnalysisPanel.add(codeConfirmLabel);

        //3.1.2 创建文本区域
        final JTextArea codeConfirmTextArea = new JTextArea(10, 30);
        codeConfirmTextArea.setLineWrap(false);      // 设置是否自动换行，默认为 false
        codeConfirmTextArea.setEditable(false);      // 设置文本框是否可编辑
        codeConfirmTextArea.setFont(textAreaFont);  // 设置文本框内文字的字体(这里的字体包括了"字体","大小","样式")
        // 创建滚动面板, 指定滚动显示的视图组件(codeConfirmTextAreaScrollPanel), 垂直滚动条和水平滚动条都一直显示
        JScrollPane codeConfirmTextAreaScrollPanel = new JScrollPane(
                codeConfirmTextArea,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        codeConfirmTextAreaScrollPanel.setLocation(20, 90);
        codeConfirmTextAreaScrollPanel.setSize(373, 640);
        writeFiletoTextFied("./code.txt",codeConfirmTextArea);
        wordAnalysisPanel.add(codeConfirmTextAreaScrollPanel);

        //3.1.3 创建"开始分析"按钮
        /*JButton analysisStartBtn = new JButton();
        analysisStartBtn.setText("开始分析");
        analysisStartBtn.setLocation(270,20);
        analysisStartBtn.setSize(118,50);
        analysisStartBtn.setFont(buttonFont);
        wordAnalysisPanel.add(analysisStartBtn);*/

        //3.2 搭建中间的 状态图示意 区域
        //3.2.1 "状态图示意"小标题
        JLabel stateDiagramLabel = new JLabel();
        stateDiagramLabel.setText("状态图示意");
        stateDiagramLabel.setLocation(413,20);
        stateDiagramLabel.setSize(400,50);
        stateDiagramLabel.setFont(titleFont);
        wordAnalysisPanel.add(stateDiagramLabel);

        //3.2.2 状态图底图
        ImageIcon statePic=new ImageIcon("src\\UI\\状态图.png");
        JLabel stateDiagramPicLabel = new JLabel();
        stateDiagramPicLabel.setIcon(statePic);
        stateDiagramPicLabel.setLocation(413,90);
        stateDiagramPicLabel.setSize(478,640);
        wordAnalysisPanel.add(stateDiagramPicLabel);


        ///3.3 搭建右侧的 过程展示 区域
        //3.3.1 过程展示 小标题
        JLabel processShowingLable = new JLabel();
        processShowingLable.setText("过程展示");
        processShowingLable.setLocation(911,20);
        processShowingLable.setSize(500,50);
        processShowingLable.setFont(titleFont);
        wordAnalysisPanel.add(processShowingLable);

        //3.3.2 过程展示 文本框
        /*JTextArea processShowingTextArea = new JTextArea();
        processShowingTextArea.setLocation(911,90);
        processShowingTextArea.setSize(555,120);
        processShowingTextArea.setFont(textAreaFont);
        processShowingTextArea.setEditable(false);
        processShowingTextArea.append(" 当前行 : \n 当前字符 : \n 当前词 : \n Token 序列(实时更新) : ");
        processShowingTextArea.setBorder(BorderFactory.createLineBorder(grey));
        wordAnalysisPanel.add(processShowingTextArea);*/

        //3.3.3 Token序列 表格
        /*JTextArea tokenFormHeadlineTextArea = new JTextArea();
        tokenFormHeadlineTextArea.setLocation(911,230);
        tokenFormHeadlineTextArea.setSize(555,30);
        tokenFormHeadlineTextArea.setFont(textAreaFont);
        tokenFormHeadlineTextArea.setEditable(false);
        tokenFormHeadlineTextArea.append(" 行 | 词法信息        | 语义信息");
        tokenFormHeadlineTextArea.setBorder(BorderFactory.createLineBorder(grey));
        wordAnalysisPanel.add(tokenFormHeadlineTextArea);

        JTextArea tokenFormContentTextArea = new JTextArea();
        tokenFormContentTextArea.setFont(textAreaFont);
        wordAnalysisResultShowing(tokenFormContentTextArea);

        JScrollPane tokenFormContentTextAreaScrollPanel = new JScrollPane(
                tokenFormContentTextArea,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        tokenFormContentTextAreaScrollPanel.setLocation(911,259);
        tokenFormContentTextAreaScrollPanel.setSize(555,471);*/

        //3.3.3 Token序列 表格
        JTextArea tokenFormHeadlineTextArea = new JTextArea();
        tokenFormHeadlineTextArea.setLocation(911,90);
        tokenFormHeadlineTextArea.setSize(555,30);
        tokenFormHeadlineTextArea.setFont(textAreaFont);
        tokenFormHeadlineTextArea.setEditable(false);
        tokenFormHeadlineTextArea.append(" 行 | 词法信息        | 语义信息");
        tokenFormHeadlineTextArea.setBorder(BorderFactory.createLineBorder(grey));
        wordAnalysisPanel.add(tokenFormHeadlineTextArea);

        JTextArea tokenFormContentTextArea = new JTextArea();
        tokenFormContentTextArea.setFont(textAreaFont);
        tokenFormContentTextArea.setLineWrap(false);
        wordAnalysisResultShowing(tokenFormContentTextArea);

        JScrollPane tokenFormContentTextAreaScrollPanel = new JScrollPane(
                tokenFormContentTextArea,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        tokenFormContentTextAreaScrollPanel.setLocation(911,119);
        tokenFormContentTextAreaScrollPanel.setSize(555,611);

        wordAnalysisPanel.add(tokenFormContentTextAreaScrollPanel);

        /*analysisStartBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("词法分析界面的 开始分析 按钮被点击, 开始词法分析");
                if(wordAnalysisProcessShowing("code.txt",tokenFormContentTextArea))
                {
                   tokenFormContentTextArea.append("\n===== 词法分析结束 =====");
                }
            }

        });*/

        //--------④ 搭建 语法分析 界面----------------------------------------------------------------------------------------------------
        //4.1 左侧的 Token序列 区域
        //4.1.1 tokenLabel
        JLabel tokenLabel = new JLabel();
        tokenLabel.setText("Token序列");
        tokenLabel.setFont(titleFont);
        tokenLabel.setLocation(20,20);
        tokenLabel.setSize(300,50);
        parsingPanel.add(tokenLabel);

        //4.1.2 tokenHeadlineTextArea
        JTextArea tokenHeadlineTextArea = new JTextArea();
        tokenHeadlineTextArea.setEditable(false);
        tokenHeadlineTextArea.setLocation(20,90);
        tokenHeadlineTextArea.setSize(400,30);
        tokenHeadlineTextArea.setBorder(BorderFactory.createLineBorder(grey));
        tokenHeadlineTextArea.setFont(textAreaFont);
        tokenHeadlineTextArea.append(" 行 | 词法信息        | 语义信息");
        parsingPanel.add(tokenHeadlineTextArea);

        //4.1.3 tokenContentTextAreaScrollPanel
        //tokenContentTextArea
        JTextArea tokenContentTextArea = new JTextArea();
        tokenContentTextArea.setEditable(false);
        tokenContentTextArea.setFont(textAreaFont);
        tokenContentTextArea.setLineWrap(false);
        wordAnalysisResultShowing(tokenContentTextArea);

        JScrollPane tokenContentTextAreaScrollPanel = new JScrollPane(
                tokenContentTextArea,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        tokenContentTextAreaScrollPanel.setSize(400,611);
        tokenContentTextAreaScrollPanel.setLocation(20,119);
        parsingPanel.add(tokenContentTextAreaScrollPanel);

        //4.1.4 parsingStartingBtn
        /*
        JButton parsingStartingBtn = new JButton();
        parsingStartingBtn.setText("开始分析");
        parsingStartingBtn.setLocation(300,20);
        parsingStartingBtn.setSize(120,50);
        parsingStartingBtn.setFont(buttonFont);
        parsingStartingBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("按钮 开始语法分析 被点击");
            }
        });
        parsingPanel.add(parsingStartingBtn);*/


        //4.2 中部 分析过程 区域
        //4.2.1 analysisProcessLabel
        JLabel analysisProcessLabel = new JLabel();
        analysisProcessLabel.setText("分析过程");
        analysisProcessLabel.setLocation(440,20);
        analysisProcessLabel.setSize(506,50);
        analysisProcessLabel.setFont(titleFont);
        parsingPanel.add(analysisProcessLabel);
        //4.2.2 analysisProcessTextAreaScrollPanel
        //analysisProcessTextArea
        JTextArea analysisProcessTextArea = new JTextArea();
        analysisProcessTextArea.setEditable(false);
        analysisProcessTextArea.setFont(textAreaFont);
        analysisProcessTextArea.setLineWrap(false);
        writeFiletoTextFied("src\\UI\\ParsingResult.txt",analysisProcessTextArea);

        JScrollPane analysisProcessTextAreaScrollPanel = new JScrollPane(
                analysisProcessTextArea,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        analysisProcessTextAreaScrollPanel.setLocation(440,90);
        analysisProcessTextAreaScrollPanel.setSize(506,640);
        parsingPanel.add(analysisProcessTextAreaScrollPanel);


        //4.3 右侧 语法树 区域
        //4.3.1 parsingTreeLabel
        JLabel parsingTreeLabel = new JLabel();
        parsingTreeLabel.setText("语法树");
        parsingTreeLabel.setFont(titleFont);
        parsingTreeLabel.setLocation(966,20);
        parsingTreeLabel.setSize(506,50);
        parsingPanel.add(parsingTreeLabel);
        //4.3.2 parsingTreePanel
        //从别的模块里面调出来的
        JTextArea parsingErrorTreeTextArea = new JTextArea();
        parsingErrorTreeTextArea.setText("语法分析出现错误, 请认真检查您的代码;\n并在更改保存后再次尝试.");
        parsingErrorTreeTextArea.setFont(textAreaFont);
        //treepanel = new TreePanel() ;
        //treepanel.setPreferredSize( new Dimension( Width , High ) ) ;
        JScrollPane pane = new JScrollPane(
                parsingErrorTreeTextArea,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        pane.setLocation(966,90);
        pane.setSize(506,640);
        parsingPanel.add( pane ) ;

        //--------⑤ 搭建 结果展示 界面----------------------------------------------------------------------------------------------------
        //5.1 左侧 词法分析结果 区域
        //5.1.1 wordAnalysisResultLabel
        JLabel wordAnalysisResultLabel = new JLabel();
        wordAnalysisResultLabel.setText("词法分析结果");
        wordAnalysisResultLabel.setFont(titleFont);
        wordAnalysisResultLabel.setLocation(20,20);
        wordAnalysisResultLabel.setSize(400,50);
        resultShowingPanel.add(wordAnalysisResultLabel);

        //5.1.2 wordAnalysisResultHeadlineTextArea
        JTextArea wordAnalysisResultHeadlineTextArea = new JTextArea();
        wordAnalysisResultHeadlineTextArea.setEditable(false);
        wordAnalysisResultHeadlineTextArea.setSize(400,30);
        wordAnalysisResultHeadlineTextArea.setLocation(20,90);
        wordAnalysisResultHeadlineTextArea.setFont(textAreaFont);
        wordAnalysisResultHeadlineTextArea.setBorder(BorderFactory.createLineBorder(grey));
        wordAnalysisResultHeadlineTextArea.append(" 行 | 词法信息        | 语义信息");
        resultShowingPanel.add(wordAnalysisResultHeadlineTextArea);

        //5.1.3 wordAnalysisResultContentTextAreaScrollPanel
        //wordAnalysisResultContentTextArea
        JTextArea wordAnalysisResultContentTextArea = new JTextArea();
        wordAnalysisResultContentTextArea.setFont(textAreaFont);
        wordAnalysisResultContentTextArea.setEditable(false);
        wordAnalysisResultContentTextArea.setLineWrap(false);
        wordAnalysisResultShowing(wordAnalysisResultContentTextArea);

        JScrollPane wordAnalysisResultContentTextAreaScrollPanel = new JScrollPane(
                wordAnalysisResultContentTextArea,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        wordAnalysisResultContentTextAreaScrollPanel.setLocation(20,119);
        wordAnalysisResultContentTextAreaScrollPanel.setSize(400,611);
        resultShowingPanel.add(wordAnalysisResultContentTextAreaScrollPanel);

        //5.2 中部 语法分析结果 区域
        //5.2.1 parsingResultLabel
        JLabel parsingResultLabel = new JLabel();
        parsingResultLabel.setText("语法分析结果");
        parsingResultLabel.setLocation(440,20);
        parsingResultLabel.setSize(506,50);
        parsingResultLabel.setFont(titleFont);
        resultShowingPanel.add(parsingResultLabel);
        //5.2.2 parsingResultTextAreaScrollPanel
        //parsingResultTextArea
        JTextArea parsingResultTextArea = new JTextArea();
        parsingResultTextArea.setFont(textAreaFont);
        parsingResultTextArea.setEditable(false);
        parsingResultTextArea.setLineWrap(false);
        writeFiletoTextFied("src\\UI\\ParsingResult.txt",parsingResultTextArea);

        JScrollPane parsingResultTextAreaScrollPanel = new JScrollPane(
                parsingResultTextArea,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        parsingResultTextAreaScrollPanel.setSize(506,640);
        parsingResultTextAreaScrollPanel.setLocation(440,90);
        resultShowingPanel.add(parsingResultTextAreaScrollPanel);


        //5.3 右侧 语法树 区域
        //5.3.1 parsingTreeResultLabel
        JLabel parsingTreeResultLabel = new JLabel();
        parsingTreeResultLabel.setText("语法树");
        parsingTreeResultLabel.setFont(titleFont);
        parsingTreeResultLabel.setSize(506,50);
        parsingTreeResultLabel.setLocation(966,20);
        resultShowingPanel.add(parsingTreeResultLabel);
        //4.3.2 parsingTreePanel
        //从别的模块里面调出来的
        JTextArea parsingErrorTreeTextArea2 = new JTextArea();
        parsingErrorTreeTextArea2.setText("语法分析出现错误, 请认真检查您的代码;\n并在更改保存后再次尝试.");
        parsingErrorTreeTextArea2.setFont(textAreaFont);
        //Panel = new TreePanel() ;
        //Panel.setPreferredSize( new Dimension( Width , High ) ) ;
        JScrollPane pane2 = new JScrollPane(
                parsingErrorTreeTextArea2,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        pane2.setLocation(966,90);
        pane2.setSize(506,640);
        resultShowingPanel.add( pane2 ) ;


        // 三 把 面板容器 作为窗口的内容面板 设置到 窗口
        //jf.setContentPane(userInputPanel);
        jf.setContentPane(wordAnalysisPanel);
        //jf.setContentPane(parsingPanel);
        //jf.setContentPane(resultShowingPanel);

        // 四 显示窗口，前面创建的信息都在内存中，通过 jf.setVisible(true) 把内存中的窗口显示在屏幕上。
        jf.setVisible(true);



    }

    public static void WordAnalysisWrongCreating()
    {
        analysisStartFlag=1;

        // 一 创建一个顶层容器（窗口）
        //JFrame jf = new JFrame();          // 创建窗口
        ImageIcon icon=new ImageIcon("分析.png");  //设置窗口图标
        jf.setIconImage(icon.getImage());           //设置窗口图标
        jf.setTitle("小组 2117-30-03 出品");        // 设置窗口名称
        jf.setSize(1500, 805);      // 设置窗口大小
        jf.setLocationRelativeTo(null);            // 把窗口位置设置到屏幕中心
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // 当点击窗口的关闭按钮时退出程序（没有这一句，程序不会退出）
        jf.setResizable(false);                    //将窗口设置为不随意拖拽变换大小,防止老师随便乱动导致的组件变乱
        // 创建一批字体, 以便统一使用
        Font textAreaFont = new Font("微软雅黑",0,20);  //输入框内字体
        Font titleFont = new Font("微软雅黑",1,40); //词法分析界面上方标题的字体
        Font buttonFont = new Font("微软雅黑",0,15);    //按钮上的字体
        Color grey = new Color(125,125,125);

        // 二 创建中间容器（面板容器）
        JPanel userInputPanel = new JPanel(null);       //用户输入界面 // 创建面板容器，使用绝对布局, 也就是说这个panel里面所有的组件都需要自己确定好自己的位置和大小
        JPanel wordAnalysisPanel = new JPanel(null);    //词法分析界面
        JPanel parsingPanel = new JPanel(null);         //语法分析界面
        JPanel resultShowingPanel = new JPanel(null);   //结果展示界面

        //--------① 创建菜单栏----------------------------------------------------------------------------------------------------
        JMenuBar menuBar = new JMenuBar();

        //1.1 创建一级菜单
        JMenuItem userInputMenuItem = new JMenuItem("用户输入");
        JMenuItem wordAnalysisMenuItem = new JMenuItem("词法分析");
        JMenuItem parsingMenuItem = new JMenuItem("语法分析");
        JMenuItem resultShowingMenuItem = new JMenuItem("结果展示");

        //1.2 一级菜单添加到菜单栏
        menuBar.add(userInputMenuItem);
        menuBar.add(wordAnalysisMenuItem);
        menuBar.add(parsingMenuItem);
        menuBar.add(resultShowingMenuItem);

        //1.3 添加菜单栏的点击响应函数
        //1.3.1 添加"用户输入"菜单栏被点击的函数
        userInputMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("用户输入  被点击");
                jf.setContentPane(userInputPanel);
                jf.setVisible(true);
            }
        });
        //1.3.2 添加"词法分析"菜单栏被点击的函数
        wordAnalysisMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("词法分析  被点击");
                jf.setContentPane(wordAnalysisPanel);
                jf.setVisible(true);
            }
        });
        //1.3.3 添加"语法分析"菜单栏被点击的函数
        parsingMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("语法分析  被点击");
                jf.setContentPane(parsingPanel);
                jf.setVisible(true);
            }
        });
        //1.3.4 添加"结果展示"菜单栏被点击的函数
        resultShowingMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("结果展示  被点击");
                jf.setContentPane(resultShowingPanel);
                jf.setVisible(true);
            }
        });

        //1.4 将我们创的这个menuBar设置成窗口的菜单栏
        jf.setJMenuBar(menuBar);

        //--------② 搭建 用户输入 界面----------------------------------------------------------------------------------------------------
        //2.1 搭建左侧的文件输入部分
        //2.1.1 创建文本区域, 用于显示相关信息
        final JTextArea fileIputTextArea = new JTextArea("请点击下方的<打开文件>按钮添加您的代码文件...\n",10, 30);
        fileIputTextArea.setLineWrap(false);    // 设置是否自动换行，默认为 false
        fileIputTextArea.setEditable(false);   // 设置文本框是否可编辑
        fileIputTextArea.setFont(textAreaFont);// 设置文本框内文字的字体(这里的字体包括了"字体","大小","样式")

        //2.1.2 创建滚动面板, 指定滚动显示的视图组件(fileIputTextArea), 垂直滚动条一直显示, 水平滚动条从不显示
        JScrollPane fileInputTextAreaScrollPanel = new JScrollPane(
                fileIputTextArea,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        fileInputTextAreaScrollPanel.setLocation(20, 20);
        fileInputTextAreaScrollPanel.setSize(720, 660);
        userInputPanel.add(fileInputTextAreaScrollPanel);

        //2.1.3 创建"打开文件"按钮
        JButton fileInputOpenFileBtn = new JButton("打开文件");
        fileInputOpenFileBtn.setLocation(20, 690);
        fileInputOpenFileBtn.setSize(355, 50);
        fileInputOpenFileBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                showFileOpenDialog(jf, fileIputTextArea);
            }
        });
        userInputPanel.add(fileInputOpenFileBtn);
        //2.1.4 创建"从文件中开始词法分析"按钮
        JButton fileInputWordAnalysisBtn = new JButton("词法分析");
        fileInputWordAnalysisBtn.setLocation(385, 690);
        fileInputWordAnalysisBtn.setSize(355, 50);
        fileInputWordAnalysisBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("用户输入界面 从文件中开始词法分析 按钮 被点击");
                System.out.println("现在的flag为: "+analysisStartFlag) ;
                analysisStartFlag=2;
                System.out.println("现在的flag为: "+analysisStartFlag) ;

                jf.setContentPane(wordAnalysisPanel);
                jf.setVisible(true);
            }
        });
        userInputPanel.add(fileInputWordAnalysisBtn);

        //2.2 搭建右侧的文字输入部分
        //2.2.1 创建文本区域, 用于显示相关信息
        final JTextArea txtInputTextArea = new JTextArea("请于此处添加您的SNL代码.\n添加完成后, 请点击下方的<词法分析>按钮, 进入下一步.\n (添加前请删除此文本)",10, 30);
        txtInputTextArea.setLineWrap(true);    // 设置是否自动换行，默认为 false
        txtInputTextArea.setEditable(false);   // 设置文本框是否可编辑
        txtInputTextArea.setFont(textAreaFont);// 设置文本框内文字的字体(这里的字体包括了"字体","大小","样式")
        //2.2.2 创建滚动面板, 指定滚动显示的视图组件(txtIputTextArea), 垂直滚动条一直显示, 水平滚动条从不显示
        JScrollPane txtInputTextAreaScrollPanel = new JScrollPane(
                txtInputTextArea,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        txtInputTextAreaScrollPanel.setLocation(760, 20);
        txtInputTextAreaScrollPanel.setSize(720, 660);
        userInputPanel.add(txtInputTextAreaScrollPanel);

        //2.2.3 创建"从输入文字中开始词法分析"按钮
        JButton txtInputWordAnalysisBtn = new JButton("词法分析");
        txtInputWordAnalysisBtn.setLocation(1125, 690);
        txtInputWordAnalysisBtn.setSize(355, 50);
        txtInputWordAnalysisBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("现在的flag为: "+analysisStartFlag) ;
                analysisStartFlag=2;
                System.out.println("现在的flag为: "+analysisStartFlag) ;

                writeTextAreatoFile(txtInputTextArea,"./code.txt");
                jf.setContentPane(wordAnalysisPanel);
                jf.setVisible(true);
            }
        });
        userInputPanel.add(txtInputWordAnalysisBtn);

        //2.2.4 创建"保存文件"按钮
        JButton txtInputSaveFileBtn = new JButton("保存文件");
        txtInputSaveFileBtn.setLocation(760,690);
        txtInputSaveFileBtn.setSize(355,50);
        txtInputSaveFileBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showFileSaveDialog(jf,txtInputTextArea);
            }
        });
        userInputPanel.add(txtInputSaveFileBtn);



        //--------③ 搭建 词法分析 界面----------------------------------------------------------------------------------------------------

        //3.1 搭建左侧的 代码确认 区域
        //3.1.1 创建"代码确认"小标题
        JLabel codeConfirmLabel = new JLabel();
        codeConfirmLabel.setText("代码确认");
        codeConfirmLabel.setFont(titleFont);
        codeConfirmLabel.setLocation(20,20);
        codeConfirmLabel.setSize(400,50);
        wordAnalysisPanel.add(codeConfirmLabel);

        //3.1.2 创建文本区域
        final JTextArea codeConfirmTextArea = new JTextArea(10, 30);
        codeConfirmTextArea.setLineWrap(false);      // 设置是否自动换行，默认为 false
        codeConfirmTextArea.setEditable(false);      // 设置文本框是否可编辑
        codeConfirmTextArea.setFont(textAreaFont);  // 设置文本框内文字的字体(这里的字体包括了"字体","大小","样式")
        // 创建滚动面板, 指定滚动显示的视图组件(codeConfirmTextAreaScrollPanel), 垂直滚动条和水平滚动条都一直显示
        JScrollPane codeConfirmTextAreaScrollPanel = new JScrollPane(
                codeConfirmTextArea,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        codeConfirmTextAreaScrollPanel.setLocation(20, 90);
        codeConfirmTextAreaScrollPanel.setSize(373, 640);
        writeFiletoTextFied("./code.txt",codeConfirmTextArea);
        wordAnalysisPanel.add(codeConfirmTextAreaScrollPanel);

        //3.1.3 创建"开始分析"按钮
        /*JButton analysisStartBtn = new JButton();
        analysisStartBtn.setText("开始分析");
        analysisStartBtn.setLocation(270,20);
        analysisStartBtn.setSize(118,50);
        analysisStartBtn.setFont(buttonFont);
        wordAnalysisPanel.add(analysisStartBtn);*/

        //3.2 搭建中间的 状态图示意 区域
        //3.2.1 "状态图示意"小标题
        JLabel stateDiagramLabel = new JLabel();
        stateDiagramLabel.setText("状态图示意");
        stateDiagramLabel.setLocation(413,20);
        stateDiagramLabel.setSize(400,50);
        stateDiagramLabel.setFont(titleFont);
        wordAnalysisPanel.add(stateDiagramLabel);

        //3.2.2 状态图底图
        ImageIcon statePic=new ImageIcon("src\\UI\\状态图.png");
        JLabel stateDiagramPicLabel = new JLabel();
        stateDiagramPicLabel.setIcon(statePic);
        stateDiagramPicLabel.setLocation(413,90);
        stateDiagramPicLabel.setSize(478,640);
        wordAnalysisPanel.add(stateDiagramPicLabel);


        ///3.3 搭建右侧的 过程展示 区域
        //3.3.1 过程展示 小标题
        JLabel processShowingLable = new JLabel();
        processShowingLable.setText("过程展示");
        processShowingLable.setLocation(911,20);
        processShowingLable.setSize(500,50);
        processShowingLable.setFont(titleFont);
        wordAnalysisPanel.add(processShowingLable);

        //3.3.2 过程展示 文本框
        /*JTextArea processShowingTextArea = new JTextArea();
        processShowingTextArea.setLocation(911,90);
        processShowingTextArea.setSize(555,120);
        processShowingTextArea.setFont(textAreaFont);
        processShowingTextArea.setEditable(false);
        processShowingTextArea.append(" 当前行 : \n 当前字符 : \n 当前词 : \n Token 序列(实时更新) : ");
        processShowingTextArea.setBorder(BorderFactory.createLineBorder(grey));
        wordAnalysisPanel.add(processShowingTextArea);*/

        //3.3.3 Token序列 表格
        /*JTextArea tokenFormHeadlineTextArea = new JTextArea();
        tokenFormHeadlineTextArea.setLocation(911,230);
        tokenFormHeadlineTextArea.setSize(555,30);
        tokenFormHeadlineTextArea.setFont(textAreaFont);
        tokenFormHeadlineTextArea.setEditable(false);
        tokenFormHeadlineTextArea.append(" 行 | 词法信息        | 语义信息");
        tokenFormHeadlineTextArea.setBorder(BorderFactory.createLineBorder(grey));
        wordAnalysisPanel.add(tokenFormHeadlineTextArea);

        JTextArea tokenFormContentTextArea = new JTextArea();
        tokenFormContentTextArea.setFont(textAreaFont);
        wordAnalysisResultShowing(tokenFormContentTextArea);

        JScrollPane tokenFormContentTextAreaScrollPanel = new JScrollPane(
                tokenFormContentTextArea,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        tokenFormContentTextAreaScrollPanel.setLocation(911,259);
        tokenFormContentTextAreaScrollPanel.setSize(555,471);*/

        //3.3.3 Token序列 表格
        JTextArea tokenFormHeadlineTextArea = new JTextArea();
        tokenFormHeadlineTextArea.setLocation(911,90);
        tokenFormHeadlineTextArea.setSize(555,30);
        tokenFormHeadlineTextArea.setFont(textAreaFont);
        tokenFormHeadlineTextArea.setEditable(false);
        tokenFormHeadlineTextArea.append(" 行 | 词法信息        | 语义信息");
        tokenFormHeadlineTextArea.setBorder(BorderFactory.createLineBorder(grey));
        wordAnalysisPanel.add(tokenFormHeadlineTextArea);

        JTextArea tokenFormContentTextArea = new JTextArea();
        tokenFormContentTextArea.setFont(textAreaFont);
        tokenFormContentTextArea.setLineWrap(false);
        wordAnalysisResultShowing(tokenFormContentTextArea);

        JScrollPane tokenFormContentTextAreaScrollPanel = new JScrollPane(
                tokenFormContentTextArea,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        tokenFormContentTextAreaScrollPanel.setLocation(911,119);
        tokenFormContentTextAreaScrollPanel.setSize(555,611);

        wordAnalysisPanel.add(tokenFormContentTextAreaScrollPanel);

        /*analysisStartBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("词法分析界面的 开始分析 按钮被点击, 开始词法分析");
                if(wordAnalysisProcessShowing("code.txt",tokenFormContentTextArea))
                {
                   tokenFormContentTextArea.append("\n===== 词法分析结束 =====");
                }
            }

        });*/

        //--------④ 搭建 语法分析 界面----------------------------------------------------------------------------------------------------
        //4.1 左侧的 Token序列 区域
        //4.1.1 tokenLabel
        JLabel tokenLabel = new JLabel();
        tokenLabel.setText("Token序列");
        tokenLabel.setFont(titleFont);
        tokenLabel.setLocation(20,20);
        tokenLabel.setSize(300,50);
        parsingPanel.add(tokenLabel);

        //4.1.2 tokenHeadlineTextArea
        JTextArea tokenHeadlineTextArea = new JTextArea();
        tokenHeadlineTextArea.setEditable(false);
        tokenHeadlineTextArea.setLocation(20,90);
        tokenHeadlineTextArea.setSize(400,30);
        tokenHeadlineTextArea.setBorder(BorderFactory.createLineBorder(grey));
        tokenHeadlineTextArea.setFont(textAreaFont);
        tokenHeadlineTextArea.append(" 行 | 词法信息        | 语义信息");
        parsingPanel.add(tokenHeadlineTextArea);

        //4.1.3 tokenContentTextAreaScrollPanel
        //tokenContentTextArea
        JTextArea tokenContentTextArea = new JTextArea();
        tokenContentTextArea.setEditable(false);
        tokenContentTextArea.setFont(textAreaFont);
        tokenContentTextArea.setLineWrap(false);
        wordAnalysisResultShowing(tokenContentTextArea);

        JScrollPane tokenContentTextAreaScrollPanel = new JScrollPane(
                tokenContentTextArea,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        tokenContentTextAreaScrollPanel.setSize(400,611);
        tokenContentTextAreaScrollPanel.setLocation(20,119);
        parsingPanel.add(tokenContentTextAreaScrollPanel);

        //4.1.4 parsingStartingBtn
        /*
        JButton parsingStartingBtn = new JButton();
        parsingStartingBtn.setText("开始分析");
        parsingStartingBtn.setLocation(300,20);
        parsingStartingBtn.setSize(120,50);
        parsingStartingBtn.setFont(buttonFont);
        parsingStartingBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("按钮 开始语法分析 被点击");
            }
        });
        parsingPanel.add(parsingStartingBtn);*/


        //4.2 中部 分析过程 区域
        //4.2.1 analysisProcessLabel
        JLabel analysisProcessLabel = new JLabel();
        analysisProcessLabel.setText("分析过程");
        analysisProcessLabel.setLocation(440,20);
        analysisProcessLabel.setSize(506,50);
        analysisProcessLabel.setFont(titleFont);
        parsingPanel.add(analysisProcessLabel);
        //4.2.2 analysisProcessTextAreaScrollPanel
        //analysisProcessTextArea
        JTextArea analysisProcessTextArea = new JTextArea();
        analysisProcessTextArea.setEditable(false);
        analysisProcessTextArea.setFont(textAreaFont);
        analysisProcessTextArea.setLineWrap(false);
        //writeFiletoTextFied("src\\UI\\ParsingResult.txt",analysisProcessTextArea);
        analysisProcessTextArea.append("词法分析发现错误\n无法进行语法分析");

        JScrollPane analysisProcessTextAreaScrollPanel = new JScrollPane(
                analysisProcessTextArea,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        analysisProcessTextAreaScrollPanel.setLocation(440,90);
        analysisProcessTextAreaScrollPanel.setSize(506,640);
        parsingPanel.add(analysisProcessTextAreaScrollPanel);


        //4.3 右侧 语法树 区域
        //4.3.1 parsingTreeLabel
        JLabel parsingTreeLabel = new JLabel();
        parsingTreeLabel.setText("语法树");
        parsingTreeLabel.setFont(titleFont);
        parsingTreeLabel.setLocation(966,20);
        parsingTreeLabel.setSize(506,50);
        parsingPanel.add(parsingTreeLabel);
        //4.3.2 parsingTreePanel
        //从别的模块里面调出来的
        //treepanel = new TreePanel() ;
        //treepanel.setPreferredSize( new Dimension( Width , High ) ) ;
        JTextArea analysisProcessTextArea2 = new JTextArea();
        analysisProcessTextArea2.setEditable(false);
        analysisProcessTextArea2.setFont(textAreaFont);
        analysisProcessTextArea2.append("词法分析发现错误\n无法进行语法分析");
        JScrollPane pane = new JScrollPane(
                analysisProcessTextArea2,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        pane.setLocation(966,90);
        pane.setSize(506,640);
        parsingPanel.add( pane ) ;

        //--------⑤ 搭建 结果展示 界面----------------------------------------------------------------------------------------------------
        //5.1 左侧 词法分析结果 区域
        //5.1.1 wordAnalysisResultLabel
        JLabel wordAnalysisResultLabel = new JLabel();
        wordAnalysisResultLabel.setText("词法分析结果");
        wordAnalysisResultLabel.setFont(titleFont);
        wordAnalysisResultLabel.setLocation(20,20);
        wordAnalysisResultLabel.setSize(400,50);
        resultShowingPanel.add(wordAnalysisResultLabel);

        //5.1.2 wordAnalysisResultHeadlineTextArea
        JTextArea wordAnalysisResultHeadlineTextArea = new JTextArea();
        wordAnalysisResultHeadlineTextArea.setEditable(false);
        wordAnalysisResultHeadlineTextArea.setSize(400,30);
        wordAnalysisResultHeadlineTextArea.setLocation(20,90);
        wordAnalysisResultHeadlineTextArea.setFont(textAreaFont);
        wordAnalysisResultHeadlineTextArea.setBorder(BorderFactory.createLineBorder(grey));
        wordAnalysisResultHeadlineTextArea.append(" 行 | 词法信息        | 语义信息");
        resultShowingPanel.add(wordAnalysisResultHeadlineTextArea);

        //5.1.3 wordAnalysisResultContentTextAreaScrollPanel
        //wordAnalysisResultContentTextArea
        JTextArea wordAnalysisResultContentTextArea = new JTextArea();
        wordAnalysisResultContentTextArea.setFont(textAreaFont);
        wordAnalysisResultContentTextArea.setEditable(false);
        wordAnalysisResultContentTextArea.setLineWrap(false);
        wordAnalysisResultShowing(wordAnalysisResultContentTextArea);

        JScrollPane wordAnalysisResultContentTextAreaScrollPanel = new JScrollPane(
                wordAnalysisResultContentTextArea,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        wordAnalysisResultContentTextAreaScrollPanel.setLocation(20,119);
        wordAnalysisResultContentTextAreaScrollPanel.setSize(400,611);
        resultShowingPanel.add(wordAnalysisResultContentTextAreaScrollPanel);

        //5.2 中部 语法分析结果 区域
        //5.2.1 parsingResultLabel
        JLabel parsingResultLabel = new JLabel();
        parsingResultLabel.setText("语法分析结果");
        parsingResultLabel.setLocation(440,20);
        parsingResultLabel.setSize(506,50);
        parsingResultLabel.setFont(titleFont);
        resultShowingPanel.add(parsingResultLabel);
        //5.2.2 parsingResultTextAreaScrollPanel
        //parsingResultTextArea
        JTextArea parsingResultTextArea = new JTextArea();
        parsingResultTextArea.setFont(textAreaFont);
        parsingResultTextArea.setEditable(false);
        parsingResultTextArea.setLineWrap(false);
        //writeFiletoTextFied("src\\UI\\ParsingResult.txt",parsingResultTextArea);
        parsingResultTextArea.append("词法分析发现错误\n无法进行语法分析");

        JScrollPane parsingResultTextAreaScrollPanel = new JScrollPane(
                parsingResultTextArea,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        parsingResultTextAreaScrollPanel.setSize(506,640);
        parsingResultTextAreaScrollPanel.setLocation(440,90);
        resultShowingPanel.add(parsingResultTextAreaScrollPanel);


        //5.3 右侧 语法树 区域
        //5.3.1 parsingTreeResultLabel
        JLabel parsingTreeResultLabel = new JLabel();
        parsingTreeResultLabel.setText("语法树");
        parsingTreeResultLabel.setFont(titleFont);
        parsingTreeResultLabel.setSize(506,50);
        parsingTreeResultLabel.setLocation(966,20);
        resultShowingPanel.add(parsingTreeResultLabel);
        //4.3.2 parsingTreePanel
        //从别的模块里面调出来的
        //Panel = new TreePanel() ;
        //Panel.setPreferredSize( new Dimension( Width , High ) ) ;
        JTextArea parsingResultTextArea2 = new JTextArea();
        parsingResultTextArea2.setFont(textAreaFont);
        parsingResultTextArea2.setEditable(false);
        parsingResultTextArea2.append("词法分析发现错误\n无法进行语法分析");
        JScrollPane pane2 = new JScrollPane(
                parsingResultTextArea2,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        pane2.setLocation(966,90);
        pane2.setSize(506,640);
        resultShowingPanel.add( pane2 ) ;


        // 三 把 面板容器 作为窗口的内容面板 设置到 窗口
        //jf.setContentPane(userInputPanel);
        jf.setContentPane(wordAnalysisPanel);
        //jf.setContentPane(parsingPanel);
        //jf.setContentPane(resultShowingPanel);

        // 四 显示窗口，前面创建的信息都在内存中，通过 jf.setVisible(true) 把内存中的窗口显示在屏幕上。
        jf.setVisible(true);



    }

    //------------------------- 以下为 用户输入 界面用到的函数 ------------------------------------------------------
    // 打开文件, 并将文件展示到文本框内
    private static void showFileOpenDialog(Component parent, JTextArea msgTextArea) {
        // 创建一个默认的文件选取器
        JFileChooser fileChooser = new JFileChooser();

        // 设置默认显示的文件夹为桌面
        fileChooser.setCurrentDirectory(new File("C:\\Users\\hp\\Desktop"));

        // 设置文件选择的模式（只选文件、只选文件夹、文件和文件均可选）
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        // 设置是否允许多选
        fileChooser.setMultiSelectionEnabled(true);

        // 添加可用的文件过滤器（FileNameExtensionFilter 的第一个参数是描述, 后面是需要过滤的文件扩展名 可变参数）
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("文本文件(*.txt, *.doc,*.docx,*.wps)", "txt", "doc","docx","wps"));
        // 设置默认使用的文件过滤器
        fileChooser.setFileFilter(new FileNameExtensionFilter("文本文件(*.txt, *.doc,*.docx,*.wps)", "txt", "doc","docx","wps"));

        // 打开文件选择框（线程将被阻塞, 直到选择框被关闭）
        int result = fileChooser.showOpenDialog(parent);

        if (result == JFileChooser.APPROVE_OPTION) {
            // 如果点击了"确定", 则获取选择的文件路径
            File file = fileChooser.getSelectedFile();

            // 如果允许选择多个文件, 则通过下面方法获取选择的所有文件
            // File[] files = fileChooser.getSelectedFiles();

            // 将选中的文件的内容添加到我们的内部文件./code.txt
            String sourceFile = file.getAbsolutePath(); //用户选中的文件地址
            String targetFile = "./code.txt";           //目标文件
            copyFile(sourceFile,targetFile);

            // 给用户一点提示信息
            msgTextArea.setText("");
            msgTextArea.append("成功打开文件: " + file.getAbsolutePath() + "\n" + "以下为代码预览, \n请在确认无误后, 点击下方的<词法分析>按钮, 进入下一步.\n注: 此处仅做预览使用, 若想修改请移步您的原文件.\n\n");
            //输出文件中的内容到这个文本框内
            writeFiletoTextFied(sourceFile,msgTextArea);
        }
    }

    // 用于将文件中为内容写入文板框内     (这个函数, 词法分析界面也用到了)
    public static  void writeFiletoTextFied(String sourceFile,JTextArea msgTextArea)
    {
        String wirte = "";
//	创建对象
        FileReader fr=null;
        try {
            fr=new FileReader(sourceFile);
//		循环读和循环写
            int len=0;
            int num=2;
            msgTextArea.append(" 1  | ");
            while((len=fr.read())!=-1)
            {
                wirte = "" + (char)len;
                if((char)len == '\n')
                {
                    if(num<=9)
                    {
                        msgTextArea.append(wirte+" "+num+"  | ");
                        num++;
                    }
                    else
                    {
                        msgTextArea.append(wirte+num+" | ");
                        num++;
                    }

                }
                else
                {
                    msgTextArea.append(wirte);
                }
            }

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally
        {
            if(fr!=null)
            {
                try {
                    fr.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

    }

    //用于两个文件之间复制粘贴内容的函数, 从sourceFile复制粘贴到targetFile
    public static void copyFile(String sourceFile,String targetFile)
    {
//	创建对象
        FileReader fr=null;
        FileWriter fw=null;
        try {
            fr=new FileReader(sourceFile);
            fw=new FileWriter(targetFile);
//		循环读和循环写
            int len=0;
            while((len=fr.read())!=-1)
            {
                fw.write((char)len);
            }

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally
        {
            if(fr!=null)
            {
                try {
                    fr.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if(fw!=null)
            {
                try {
                    fw.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    //选择文件保存路径
    private static void showFileSaveDialog(Component parent, JTextArea msgTextArea) {
        // 创建一个默认的文件选取器
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("C:\\Users\\hp\\Desktop"));
        fileChooser.setFileFilter(new FileNameExtensionFilter("文本文件(*.txt, *.doc,*.docx,*.wps)", "txt", "doc","docx","wps"));

        // 设置打开文件选择框后默认输入的文件名
        fileChooser.setSelectedFile(new File("UserCode.txt"));

        // 打开文件选择框（线程将被阻塞, 直到选择框被关闭）
        int result = fileChooser.showSaveDialog(parent);

        if (result == JFileChooser.APPROVE_OPTION) {
            // 如果点击了"保存", 则获取选择的保存路径
            File file = fileChooser.getSelectedFile();
            String targetFile = file.getAbsolutePath(); //用户选中的文件地址
            writeTextAreatoFile(msgTextArea,targetFile);//将文本框内的内容写入用户选择的文件路径
            // clearFile("code.txt");
            writeTextAreatoFile(msgTextArea,"code.txt");//将文本框内的内容写入用户选择的文件路径

            // 给用户一点提示信息
            msgTextArea.setText("");
            msgTextArea.append("成功保存文件: " + targetFile + "\n" + "以下为代码预览, \n请在确认无误后, 点击下方的<词法分析>按钮, 进入下一步.\n\n");
            //输出文件中的内容到这个文本框内
            writeFiletoTextFied(targetFile,msgTextArea);
        }
    }

    //将文本框中内的信息写入文件
    private static void writeTextAreatoFile(JTextArea textArea, String targetFile){
        File f=new File(targetFile);//新建一个文件对象，如果不存在则创建一个该文件
        FileWriter fw;
        try {
            fw=new FileWriter(f);
            String str=textArea.getText();
            fw.write(str);//将字符串写入到指定的路径下的文件中
            fw.close();
        } catch (IOException e) { e.printStackTrace(); }
    }




    //------------------------- 以下为 词法分析 界面用到的函数 ------------------------------------------------------


    //------------------------- 以下为 语法分析 界面用到的函数 ------------------------------------------------------
    public  static int getTreeInf(treeNode root , int Y ) {
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

    public  static void drawtree( treeNode root )
    {

        X = 20 ;
        High = getTreeInf( root , 20 );
        Width = X ;
        treepanel.Root = root;
        Panel.Root = root ;
        jf.setVisible(true);
    }




    //------------------------- 以下为 结果展示 界面用到的函数 ------------------------------------------------------
    //词法分析结果展示(快速打印Token序列)
    public static void wordAnalysisResultShowing(JTextArea textArea)
    {
        WordAnalysis wordAnalysis=new WordAnalysis();
        if(wordAnalysis.Scanner("./code.txt"))//词法分析没有出错, 直接输出Token序列
            for(WordToken wordToken:wordAnalysis.tokenList)
            {
                if(wordToken.line<=9)
                {
                    textArea.append(" "+wordToken.line+" | "+wordToken.type+" \t   | "+wordToken.wordMean+"\n");
                }
                else
                {
                    textArea.append(" "+wordToken.line+" | "+wordToken.type+" \t   | "+wordToken.wordMean+"\n");
                }
            }
        else//词法分析出错, 输出一条提示语句, 以及Token序列
        {
            textArea.append(("词法分析发现错误;\n请根据以下内容中的错误提示信息仔细检查;\n"));
            for(WordToken wordToken:wordAnalysis.tokenList)
            {
                if(wordToken.line<=9)
                {
                    textArea.append(" "+wordToken.line+" | "+wordToken.type+" \t   | "+wordToken.wordMean+"\n");
                }
                else
                {
                    textArea.append(" "+wordToken.line+" | "+wordToken.type+" \t   | "+wordToken.wordMean+"\n");
                }
            }
        }

    }

    //将语法分析结果一条条的写入文件 src\UI\ParsingResult.txt
    public static void parsingResultWritingFile(String str)
    {
        //	创建对象
        FileWriter fw=null;
        try {
            fw=new FileWriter("src\\UI\\ParsingResult.txt",true);
            fw.write(str+"\n");
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally
        {
            if(fw!=null)
            {
                try {
                    fw.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    //语法分析结果展示(快速打印语法分析结果)(从文件 src\UI\ParsingResult 里面读数据打印

    //清空文件 ParsingResult.txt
    public static void clearFile(String fileName)
    {
        File file =new File(fileName);
        try {
            if(!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter =new FileWriter(file);
            fileWriter.write("");
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





}
