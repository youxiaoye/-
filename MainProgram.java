package doorlock;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;

import java.net.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;




public class MainProgram {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            HomeScreen homeScreen = new HomeScreen();
            homeScreen.setVisible(true);
        });
    }
}
class HomeScreen extends JFrame{
    private JButton bigButton;
    private JButton smallButton;
    private static int failCount =0;
    public HomeScreen(){
        setTitle("欢迎光临");
        setSize(800,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initializeUI();
    }
    private void initializeUI() {
        // 设置背景图片
        JLabel background = new JLabel(new ImageIcon("C:\\Users\\sunjiaze\\Desktop\\m7Sg644psN96oA7.png"));
        background.setLayout(null); // 绝对定位
    add(background);
    // 添加标题
    JLabel titleLabel = new JLabel("发   际   线   门   禁   系   统", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 50));
        titleLabel.setBounds(0, 10, 800, 100); // 位置和大小
        titleLabel.setForeground(Color.BLACK);
        background.add(titleLabel);

    // 左上角的小字
    JLabel smallTextTop = new JLabel("欢迎光临");
        smallTextTop.setFont(new Font("Serif", Font.PLAIN, 12));
        smallTextTop.setBounds(10, 50, 150, 20); // 位置和大小
        background.add(smallTextTop);

    // 小按钮上方的一行字
    JLabel smallTextBottom = new JLabel("刷卡进入，如有问题联系管理员");
        smallTextBottom.setFont(new Font("Serif", Font.PLAIN, 20));
        smallTextBottom.setBounds(10, 500, 500, 50); // 位置和大小
        background.add(smallTextBottom);

    // 创建大按钮
    bigButton = new JButton("刷卡区");
        bigButton.setFont(new Font("Serif", Font.BOLD, 18));
        bigButton.setBounds(50, 100, 300, 300); // 位置和大小
        bigButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            openBigButtonPage();
        }
    });
        background.add(bigButton);

    // 创建小按钮
    smallButton = new JButton("更改");
        smallButton.setFont(new Font("Serif", Font.BOLD, 14));
        smallButton.setBounds(650, 500, 100, 40); // 位置和大小
        smallButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            openSmallButtonPage();
        }
    });
        background.add(smallButton);

    // 将背景标签设置为最底层
        background.setBounds(0, 0, 800, 600);
        background.setOpaque(true);
    getContentPane().add(background);
}

    private void openBigButtonPage() {
        // 打开大按钮对应的页面
        String input = JOptionPane.showInputDialog(this, "请刷卡: ");
        if (input != null && !input.trim().isEmpty()) {
            boolean ismatch = ConnectDatabase.checkIfExists(input);
            if (ismatch) {
                failCount = 0; // 重置失败计数器
                SwingUtilities.invokeLater(() -> {
                    SuccessfulPage successfulpage = new SuccessfulPage();
                    successfulpage.setVisible(true);
                    this.dispose();

                    Timer timer = new Timer(1200, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            successfulpage.dispose();
                            HomeScreen homeScreen = new HomeScreen();
                            homeScreen.setVisible(true);
                        }
                    });
                    timer.setRepeats(false);
                    timer.start();
                });
            } else {

                handleFailedAttempt();
            }
        } else {

            handleFailedAttempt();
        }
    }
private void openSmallButtonPage() {
    // 打开小按钮对应的页面
    String input = JOptionPane.showInputDialog(this,"请刷卡: ");
    if(input!=null&&!input.trim().isEmpty()){
        boolean ismatch =ConnectDatabase.checkroot(input);
        if(ismatch){
            SwingUtilities.invokeLater(() -> {
                ChangePage changePage = new ChangePage();
                changePage.setVisible(true);
                this.dispose();

            });
        }
    }else{
        System.out.println("wrong");
    }
   }
    private void handleFailedAttempt() {
        failCount++;
        System.out.println("Fail count: " + failCount);
        if (failCount >= 3) {
            // 跳转到警告页面
            SwingUtilities.invokeLater(() -> {
                WarningPage warningPage = new WarningPage();
                warningPage.setVisible(true);
                this.dispose();
            });
        } else {
            SwingUtilities.invokeLater(() -> {
                FailPage failPage = new FailPage();
                failPage.setVisible(true);
                this.dispose();

                Timer timer = new Timer(1200, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        failPage.dispose();
                        HomeScreen homeScreen = new HomeScreen();
                        homeScreen.setVisible(true);
                    }
                });
                timer.setRepeats(false);
                timer.start();
            });
        }
    }
}
class SuccessfulPage extends JFrame{
    public SuccessfulPage(){
        setTitle("欢迎光临");
        setSize(800,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initializeUI();

    }
    private void initializeUI() {
        // 设置背景图片
        JLabel background = new JLabel(new ImageIcon("C:\\Users\\sunjiaze\\Desktop\\m7Sg644psN96oA7.png"));
        background.setLayout(null); // 绝对定位
        add(background);
        // 添加标题
        JLabel titleLabel = new JLabel("发   际   线   门   禁   系   统", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 50));
        titleLabel.setBounds(0, 10, 800, 100); // 位置和大小
        titleLabel.setForeground(Color.BLACK);
        background.add(titleLabel);

        // 左上角的小字
        JLabel smallTextTop = new JLabel("欢迎光临");
        smallTextTop.setFont(new Font("Serif", Font.PLAIN, 12));
        smallTextTop.setBounds(10, 50, 150, 20); // 位置和大小
        background.add(smallTextTop);

        // 显示
        JLabel smallTextBottom = new JLabel("刷卡成功，欢迎光临！");
        smallTextBottom.setFont(new Font("Serif", Font.PLAIN, 60));
        smallTextBottom.setBounds(100, 300, 800, 50); // 位置和大小
        background.add(smallTextBottom);



        // 将背景标签设置为最底层
        background.setBounds(0, 0, 800, 600);
        background.setOpaque(true);
        getContentPane().add(background);
    }
}
class ChangePage extends JFrame {
    private JButton insertButton;
    private JButton deleteButton;
    private JButton returnButton;

    public ChangePage() {
        setTitle("欢迎光临");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initializeUI();

    }

    private void initializeUI() {
        JLabel background = new JLabel(new ImageIcon("C:\\Users\\sunjiaze\\Desktop\\m7Sg644psN96oA7.png"));
        background.setLayout(null); // 绝对定位
        add(background);
        // 添加标题
        JLabel titleLabel = new JLabel("发   际   线   门   禁   系   统", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 50));
        titleLabel.setBounds(0, 10, 800, 100); // 位置和大小
        titleLabel.setForeground(Color.BLACK);
        background.add(titleLabel);
        // 左上角的小字
        JLabel smallTextTop = new JLabel("欢迎光临");
        smallTextTop.setFont(new Font("Serif", Font.PLAIN, 12));
        smallTextTop.setBounds(10, 50, 150, 20); // 位置和大小
        background.add(smallTextTop);
        //设置返回按钮
        returnButton =new JButton("返回首页");
        returnButton.setFont(new Font("Serif", Font.BOLD, 18));
        returnButton.setBounds(320,450,150,100);
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                SwingUtilities.invokeLater(() -> {
                    HomeScreen homeScreen = new HomeScreen();
                    homeScreen.setVisible(true);
                });
            }
        });
        background.add(returnButton);
        //俩按钮
        insertButton = new JButton("增加人员");
        insertButton.setFont(new Font("Serif", Font.BOLD, 18));
        insertButton.setBounds(50, 350, 200,200); // 位置和大小
        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField cardIdField = new JTextField();
                JTextField nameField = new JTextField();
                JTextField sexField = new JTextField();

                Object[] message = {
                        "Card ID:", cardIdField,
                        "Name:", nameField,
                        "Sex:", sexField
                };

                int option = JOptionPane.showConfirmDialog(ChangePage.this, message, "增加的：", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    String cardId = cardIdField.getText().trim();
                    String name = nameField.getText().trim();
                    String sex = sexField.getText().trim();

                    if (!cardId.isEmpty() && !name.isEmpty() && !sex.isEmpty()) {
                        boolean isSuccess = ConnectDatabase.insertusertabData(cardId, name, sex);
                        if (isSuccess) {
                            JOptionPane.showMessageDialog(ChangePage.this, "success!");
                        } else {
                            JOptionPane.showMessageDialog(ChangePage.this, "failed.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(ChangePage.this, "所有字段不能为空!");
                    }
                }
                JTextField cardIdField2 =new JTextField();
                JTextField typeeField =new JTextField();
                Object []message2 ={
                        "Card ID:",cardIdField2,
                        "Type:",typeeField
                };
                int option2 =JOptionPane.showConfirmDialog(ChangePage.this,message2,"增加的: ",JOptionPane.OK_CANCEL_OPTION);
                if(option2==JOptionPane.OK_OPTION){
                    String cardId2 = cardIdField2.getText().trim();
                    String typee = typeeField.getText().trim();

                    if (!cardId2.isEmpty() && !typee.isEmpty()) {
                        boolean isSuccess2 = ConnectDatabase.insertcontrolData(cardId2, typee);
                        if (isSuccess2) {
                            JOptionPane.showMessageDialog(ChangePage.this, "成功插入 control 表！");
                        } else {
                            JOptionPane.showMessageDialog(ChangePage.this, "插入 control 表失败。");
                        }
                    } else {
                        JOptionPane.showMessageDialog(ChangePage.this, "所有字段不能为空!");
                    }

                }
            }
        });
        background.add(insertButton);

        deleteButton = new JButton("删除人员");
        deleteButton.setFont(new Font("Serif", Font.BOLD, 18));
        deleteButton.setBounds(550, 350, 200, 200); // 位置和大小
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cardId = JOptionPane.showInputDialog(ChangePage.this, "请输入要删除的卡号:");
                if (cardId != null && !cardId.trim().isEmpty()) {
                    boolean isSuccess = ConnectDatabase.deleteusertabData(cardId);
                    if (isSuccess) {
                        JOptionPane.showMessageDialog(ChangePage.this, "删除人员成功!");
                    } else {
                        JOptionPane.showMessageDialog(ChangePage.this, "删除人员失败!");
                    }
                } else {
                    JOptionPane.showMessageDialog(ChangePage.this, "输入不能为空!");
                }
            }
        });
        background.add(deleteButton);

        // 将背景标签设置为最底层
        background.setBounds(0, 0, 800, 600);
        background.setOpaque(true);
        getContentPane().add(background);
    }
}
class FailPage extends JFrame {
    public FailPage() {
        setTitle("刷卡失败");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initializeUI();
    }

    private void initializeUI() {
        JLabel background = new JLabel(new ImageIcon("C:\\Users\\sunjiaze\\Desktop\\m7Sg644psN96oA7.png"));
        background.setLayout(null); // 绝对定位
        add(background);

        JLabel titleLabel = new JLabel("发   际   线   门   禁   系   统", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 50));
        titleLabel.setBounds(0, 10, 800, 100); // 位置和大小
        titleLabel.setForeground(Color.BLACK);
        background.add(titleLabel);

        JLabel smallTextTop = new JLabel("欢迎光临");
        smallTextTop.setFont(new Font("Serif", Font.PLAIN, 12));
        smallTextTop.setBounds(10, 50, 150, 20); // 位置和大小
        background.add(smallTextTop);

        JLabel smallTextBottom = new JLabel("刷卡失败，请重试！");
        smallTextBottom.setFont(new Font("Serif", Font.PLAIN, 60));
        smallTextBottom.setBounds(100, 300, 800, 50); // 位置和大小
        background.add(smallTextBottom);

        background.setBounds(0, 0, 800, 600);
        background.setOpaque(true);
        getContentPane().add(background);
    }
}
class WarningPage extends JFrame {
    public WarningPage() {
        setTitle("警告");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initializeUI();
    }

    private void initializeUI() {
        JLabel background = new JLabel(new ImageIcon("C:\\Users\\sunjiaze\\Desktop\\m7Sg644psN96oA7.png"));
        background.setLayout(null); // 绝对定位
        add(background);

        JLabel titleLabel = new JLabel("发   际   线   门   禁   系   统", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 50));
        titleLabel.setBounds(0, 10, 800, 100); // 位置和大小
        titleLabel.setForeground(Color.BLACK);
        background.add(titleLabel);

        JLabel smallTextTop = new JLabel("欢迎光临");
        smallTextTop.setFont(new Font("Serif", Font.PLAIN, 12));
        smallTextTop.setBounds(10, 50, 150, 20); // 位置和大小
        background.add(smallTextTop);

        JLabel smallTextBottom = new JLabel("连续失败五次，系统锁定！");
        smallTextBottom.setFont(new Font("Serif", Font.PLAIN, 50));
        smallTextBottom.setBounds(100, 300, 800, 50); // 位置和大小
        background.add(smallTextBottom);

        JButton unlockButton = new JButton("解锁");
        unlockButton.setFont(new Font("Serif", Font.BOLD, 18));
        unlockButton.setBounds(320, 400, 150, 100);
        unlockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String adminInput = JOptionPane.showInputDialog(WarningPage.this, "请输入管理员卡号:");
                if (adminInput != null && !adminInput.trim().isEmpty()) {
                    boolean isAdmin = ConnectDatabase.checkroot(adminInput);
                    if (isAdmin) {
                        dispose();
                        SwingUtilities.invokeLater(() -> {
                            HomeScreen homeScreen = new HomeScreen();
                            homeScreen.setVisible(true);
                        });
                    } else {
                        JOptionPane.showMessageDialog(WarningPage.this, "管理员卡号错误，无法解锁！");
                    }
                }
            }
        });
        background.add(unlockButton);

        background.setBounds(0, 0, 800, 600);
        background.setOpaque(true);
        getContentPane().add(background);
    }
}












