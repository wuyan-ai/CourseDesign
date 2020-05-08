package UI;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class Win

{

    public static void Creating() {
        // 1. 创建一个顶层容器（窗口）
        JFrame jf = new JFrame();          // 创建窗口
        ImageIcon icon=new ImageIcon("src\\Parsing\\UI\\分析.png");  //设置窗口图标
        jf.setIconImage(icon.getImage());           //设置窗口图标
        jf.setTitle("小组 2117-30-03 出品");        // 设置窗口名称
        jf.setSize(1500, 805);      // 设置窗口大小
        jf.setLocationRelativeTo(null);            // 把窗口位置设置到屏幕中心
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // 当点击窗口的关闭按钮时退出程序（没有这一句，程序不会退出）
        jf.setResizable(false);                    //将窗口设置为不随意拖拽变换大小,防止老师随便乱动导致的组件变乱


        // 2. 创建中间容器（面板容器）
        JPanel panel = new JPanel(null);      // 创建面板容器，使用绝对布局


        //1 创建菜单栏
        JMenuBar menuBar = new JMenuBar();
        //1.1 创建一级菜单
        JMenu inputMenu = new JMenu("用户输入");
        JMenu lesMenu = new JMenu("词法分析");
        JMenu parsingMenu = new JMenu("语法分析");
        JMenu resultMenu = new JMenu("结果展示");
        //1.2 一级菜单添加到菜单栏
        menuBar.add(inputMenu);
        menuBar.add(lesMenu);
        menuBar.add(parsingMenu);
        menuBar.add(resultMenu);
        //1.3 添加菜单栏的点击响应函数
        //1.3.1 添加"用户输入"菜单栏被点击的函数
        inputMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("用户输入  被点击");
            }
        });
        //1.3.2 添加"词法分析"菜单栏被点击的函数
        lesMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("词法分析  被点击");
            }
        });
        //1.3.3 添加"语法分析"菜单栏被点击的函数
        parsingMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("语法分析  被点击");
            }
        });
        //1.3.4 添加"结果展示"菜单栏被点击的函数
        resultMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("结果展示  被点击");
            }
        });
        //1.4 将我们创的这个menuBar设置成窗口的菜单栏
        jf.setJMenuBar(menuBar);

        //2 搭建左侧的文件输入部分
        //2.1 创建文本区域, 用于显示相关信息
        final JTextArea msgTextArea = new JTextArea(10, 30);
        msgTextArea.setLocation(20, 20);
        msgTextArea.setSize(720, 660);
        msgTextArea.setLineWrap(true);
        panel.add(msgTextArea);
        //2.3 创建"保存"按钮
        JButton openBtn = new JButton("打开文件");
        openBtn.setLocation(20, 690);
        openBtn.setSize(355, 50);
        openBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                showFileOpenDialog(jf, msgTextArea);
            }
        });
        panel.add(openBtn);
        //2.4 创建"从文件中开始词法分析"按钮
        JButton lecFileBtn = new JButton("词法分析");
        lecFileBtn.setLocation(385, 690);
        lecFileBtn.setSize(355, 50);
        panel.add(lecFileBtn);

        //3 搭建右侧的文字输入部分
        //3.1 创建文本区域, 用于显示相关信息
        final JTextArea msgTextArea2 = new JTextArea(10, 30);
        msgTextArea2.setLocation(760, 20);
        msgTextArea2.setSize(720, 660);
        msgTextArea2.setLineWrap(true);
        panel.add(msgTextArea2);
        //3.2 创建"从输入文字中开始词法分析"按钮
        JButton lexTxtBtn = new JButton("词法分析");
        lexTxtBtn.setLocation(760, 690);
        lexTxtBtn.setSize(720, 50);
        panel.add(lexTxtBtn);


        // 4. 把 面板容器 作为窗口的内容面板 设置到 窗口
        jf.setContentPane(panel);

        // 5. 显示窗口，前面创建的信息都在内存中，通过 jf.setVisible(true) 把内存中的窗口显示在屏幕上。
        jf.setVisible(true);

    }


     //创建一个面板，面板中心显示一个标签，用于表示某个选项卡需要显示的内容
    private static JComponent createTextPanel(String text)
            {
            // 创建面板, 使用一个 1 行 1 列的网格布局（为了让标签的宽高自动撑满面板）
            JPanel panel = new JPanel(new GridLayout(1, 1));

            // 创建标签
            JLabel label = new JLabel(text);
            label.setFont(new Font(null, Font.PLAIN, 50));
            label.setHorizontalAlignment(SwingConstants.CENTER);

            // 添加标签到面板
            panel.add(label);

            return panel;
            }



    /*
     * 打开文件
     */
    private static void showFileOpenDialog(Component parent, JTextArea msgTextArea) {
        // 创建一个默认的文件选取器
        JFileChooser fileChooser = new JFileChooser();

        // 设置默认显示的文件夹为当前文件夹
        fileChooser.setCurrentDirectory(new File("."));

        // 设置文件选择的模式（只选文件、只选文件夹、文件和文件均可选）
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        // 设置是否允许多选
        fileChooser.setMultiSelectionEnabled(true);

        // 添加可用的文件过滤器（FileNameExtensionFilter 的第一个参数是描述, 后面是需要过滤的文件扩展名 可变参数）
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("zip(*.zip, *.rar)", "zip", "rar"));
        // 设置默认使用的文件过滤器
        fileChooser.setFileFilter(new FileNameExtensionFilter("image(*.jpg, *.png, *.gif)", "jpg", "png", "gif"));

        // 打开文件选择框（线程将被阻塞, 直到选择框被关闭）
        int result = fileChooser.showOpenDialog(parent);

        if (result == JFileChooser.APPROVE_OPTION) {
            // 如果点击了"确定", 则获取选择的文件路径
            File file = fileChooser.getSelectedFile();

            // 如果允许选择多个文件, 则通过下面方法获取选择的所有文件
            // File[] files = fileChooser.getSelectedFiles();

            msgTextArea.append("打开文件: " + file.getAbsolutePath() + "\n\n");
        }
    }

    /*
     * 选择文件保存路径
     */
    private static void showFileSaveDialog(Component parent, JTextArea msgTextArea) {
        // 创建一个默认的文件选取器
        JFileChooser fileChooser = new JFileChooser();

        // 设置打开文件选择框后默认输入的文件名
        fileChooser.setSelectedFile(new File("测试文件.zip"));

        // 打开文件选择框（线程将被阻塞, 直到选择框被关闭）
        int result = fileChooser.showSaveDialog(parent);

        if (result == JFileChooser.APPROVE_OPTION) {
            // 如果点击了"保存", 则获取选择的保存路径
            File file = fileChooser.getSelectedFile();
            msgTextArea.append("保存到文件: " + file.getAbsolutePath() + "\n\n");
        }
    }

}
