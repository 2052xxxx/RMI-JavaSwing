/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package calclient;

/**
 *
 * @author acer
 */
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Rectangle;
import javax.swing.JMenuBar;
import javax.swing.JMenu;

import calserver.CalculatorInterface;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import javax.swing.JOptionPane;
import java.rmi.Naming;

public class CalClient  extends JFrame implements ActionListener{
    
    private JFrame frame;
    // Khai báo biến đối tượng riêng tư 'frame' kiểu JFrame để lưu trữ cửa sổ GUI.
    private JTextField txtResult;
    // Khai báo biến đối tượng riêng tư 'txtResult' kiểu JTextField để lưu trữ kết quả của máy tính.
    private CalculatorInterface calculator;

    String operand1="";
    // Khai báo biến chuỗi 'operand1' để lưu trữ toán hạng đầu tiên do người dùng nhập vào.

    String operator ="";
    // Khai báo biến chuỗi 'operator' để lưu trữ toán tử do người dùng nhập vào.

    boolean isEqualsPress = false;
    // Khai báo biến boolean 'isEqualsPress' để kiểm tra xem nút bằng đã được nhấn hay chưa.
    boolean operatorClicked;
    boolean decimalClicked;
    double currentResult;
    double currentNumber;

    
        
    ActionListener al= new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(isEqualsPress)
            {
                    isEqualsPress=false;
                    txtResult.setText("");
            }
            // Kiểm tra nếu nút bằng đã được nhấn, nếu đúng thì xóa trường văn bản và thiết lập cờ 'isEqualsPress' về false.

            String cmd= e.getActionCommand();
            txtResult.setText(txtResult.getText()+cmd);
            System.out.println("Input: "+ cmd);
        }// Nhận lệnh hành động của nút đã nhấn và ghi nó vào trường văn bản.

    };
    ActionListener al1= new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String cmd= e.getActionCommand();
            System.out.println("Input: "+ cmd);
            operator= cmd ;
            operand1=txtResult.getText();
            txtResult.setText("");
// Nhận lệnh hành động của nút đã nhấn, thiết lập biến toán tử tương ứng, lưu giá trị của trường văn bản vào biến toán hạng thứ nhất và xóa trường văn bản.
        }
    };

    //sqrt, %, 1/x
    ActionListener al2= new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(txtResult.getText().equals(""))
            {
                    return;
            }
            // Kiểm tra nếu trường văn bản rỗng, nếu đúng thì thoát.

            String cmd= e.getActionCommand();

            try {                                                      
                double value=Double.parseDouble(txtResult.getText());
                if(cmd.equals("sqrt"))
                        value=calculator.squareRoot(value);
                else if(cmd.equals("%"))
                        value=calculator.percent(value);
                else if(cmd.equals("1/x"))
                        value=calculator.inverse(value);
                txtResult.setText(""+value);
                isEqualsPress=true;
// Nhận lệnh hành động của nút đã nhấn, thực hiện phép tính tương ứng trên giá trị do người dùng nhập vào, thiết lập trường văn bản với kết quả nhận được và thiết lập cờ 'isEqualsPress' về
            } catch (Exception ex) {
                    ex.printStackTrace();
            }
        }
    };
    // Hàm main để khởi chạy chương trình, tạo một đối tượng của class Main và hiển thị cửa sổ GUI.
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                        CalClient window = new CalClient();
                        window.frame.setVisible(true);
                } catch (Exception e) {
                        e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public CalClient() {
        try {
            calculator = (CalculatorInterface) Naming.lookup("rmi://localhost:5000/MyServices");
            initialize();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Failed to connect to server: " + e.getMessage());
        }
    }
    // Hàm khởi tạo của class Main.
    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        
        frame = new JFrame();
        frame.setResizable(false);
        frame.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 15));
        frame.setBounds(100, 100, 611, 553);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        // Tạo một đối tượng JFrame với kích thước và vị trí cụ thể, thiết lập hành động khi người dùng đóng cửa sổ là thoát chương trình.

        JLabel lblNewLabel = new JLabel("Calculator");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 22));
        lblNewLabel.setBounds(455, 11, 132, 27);
        frame.getContentPane().add(lblNewLabel);
        // Tạo một thành phần JLabel có tên 'lblNewLabel' với text "Calculator" và thiết lập font và vị trí trên JFrame.

        JPanel panel = new JPanel();
        panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        panel.setBounds(10, 38, 577, 64);
        frame.getContentPane().add(panel);
        panel.setLayout(null);
        // Tạo một thành phần JPanel có tên 'panel', thiết lập đường viền, kích thước và vị trí trên JFrame.

        txtResult = new JTextField();
        txtResult.setEditable(false);
        txtResult.setFont(new Font("Tahoma", Font.BOLD, 15));
        txtResult.setHorizontalAlignment(SwingConstants.RIGHT);
        txtResult.setBounds(10, 11, 557, 45);
        panel.add(txtResult);
        txtResult.setColumns(10);
        // Tạo một thành phần JTextField có tên 'txtResult', thiết lập font, căn chỉnh, kích thước và thêm nó vào JPanel 'panel'.

        JPanel panel_1 = new JPanel();
        panel_1.setBackground(new Color(240, 240, 240));
        panel_1.setBounds(10, 106, 577, 373);
        frame.getContentPane().add(panel_1);
        GridBagLayout gbl_panel_1 = new GridBagLayout();
        gbl_panel_1.columnWidths = new int[] {111, 111, 111, 111, 111};
        gbl_panel_1.rowHeights = new int[] {61, 61, 61, 61, 61, 61};
        gbl_panel_1.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0};
        gbl_panel_1.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0};
        panel_1.setLayout(gbl_panel_1);

        JButton btnSquareRoot = new JButton("sqrt");
        btnSquareRoot.setBounds(new Rectangle(10, 10, 10, 10));
        btnSquareRoot.addActionListener(al2);

        JButton btnNewButton = new JButton("MC");
        btnNewButton.setBounds(new Rectangle(10, 10, 10, 10));
        btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 18));
        GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
        gbc_btnNewButton.fill = GridBagConstraints.BOTH;
        gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
        gbc_btnNewButton.gridx = 0;
        gbc_btnNewButton.gridy = 0;
        panel_1.add(btnNewButton, gbc_btnNewButton);

        JButton btnNewButton_1 = new JButton("MR");
        btnNewButton_1.setBounds(new Rectangle(10, 10, 10, 10));
        btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 18));
        GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
        gbc_btnNewButton_1.fill = GridBagConstraints.BOTH;
        gbc_btnNewButton_1.insets = new Insets(0, 0, 5, 5);
        gbc_btnNewButton_1.gridx = 1;
        gbc_btnNewButton_1.gridy = 0;
        panel_1.add(btnNewButton_1, gbc_btnNewButton_1);

        JButton btnNewButton_2 = new JButton("MS");
        btnNewButton_2.setBounds(new Rectangle(10, 10, 10, 10));
        btnNewButton_2.setFont(new Font("Tahoma", Font.BOLD, 18));
        GridBagConstraints gbc_btnNewButton_2 = new GridBagConstraints();
        gbc_btnNewButton_2.fill = GridBagConstraints.BOTH;
        gbc_btnNewButton_2.insets = new Insets(0, 0, 5, 5);
        gbc_btnNewButton_2.gridx = 2;
        gbc_btnNewButton_2.gridy = 0;
        panel_1.add(btnNewButton_2, gbc_btnNewButton_2);

        JButton btnNewButton_3 = new JButton("M+");
        btnNewButton_3.setBounds(new Rectangle(10, 10, 10, 10));
        btnNewButton_3.setFont(new Font("Tahoma", Font.BOLD, 18));
        GridBagConstraints gbc_btnNewButton_3 = new GridBagConstraints();
        gbc_btnNewButton_3.fill = GridBagConstraints.BOTH;
        gbc_btnNewButton_3.insets = new Insets(0, 0, 5, 5);
        gbc_btnNewButton_3.gridx = 3;
        gbc_btnNewButton_3.gridy = 0;
        panel_1.add(btnNewButton_3, gbc_btnNewButton_3);

        JButton btnNewButton_4 = new JButton("M-");
        btnNewButton_4.setBounds(new Rectangle(10, 10, 10, 10));
        btnNewButton_4.setFont(new Font("Tahoma", Font.BOLD, 18));
        GridBagConstraints gbc_btnNewButton_4 = new GridBagConstraints();
        gbc_btnNewButton_4.fill = GridBagConstraints.BOTH;
        gbc_btnNewButton_4.insets = new Insets(0, 0, 5, 0);
        gbc_btnNewButton_4.gridx = 4;
        gbc_btnNewButton_4.gridy = 0;
        panel_1.add(btnNewButton_4, gbc_btnNewButton_4);

        JButton btnPlus = new JButton("+/-");
        btnPlus.setBounds(new Rectangle(10, 10, 10, 10));
        btnPlus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    double value =Double.parseDouble(txtResult.getText());
                    value = -value;
                    txtResult.setText(""+value);
                } catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });

        JButton btnClear = new JButton("C");
        btnClear.setBounds(new Rectangle(10, 10, 10, 10));
        btnClear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                txtResult.setText("");
                operand1="";
                operator="";
                isEqualsPress=false;
            }
        });

        JButton btnClear_2 = new JButton("<-");
        btnClear_2.setBounds(new Rectangle(10, 10, 10, 10));
        btnClear_2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                     String text = txtResult.getText();
                if (text.length() == 1) {
                    txtResult.setText("");		           
                } else {
                    txtResult.setText(text.substring(0, text.length() - 1));
                }
            }
        });
        btnClear_2.setFont(new Font("Tahoma", Font.BOLD, 18));
        GridBagConstraints gbc_btnClear_2 = new GridBagConstraints();
        gbc_btnClear_2.fill = GridBagConstraints.BOTH;
        gbc_btnClear_2.insets = new Insets(0, 0, 5, 5);
        gbc_btnClear_2.gridx = 0;
        gbc_btnClear_2.gridy = 1;
        panel_1.add(btnClear_2, gbc_btnClear_2);

        JButton btnClear_1 = new JButton("CE");
        btnClear_1.setBounds(new Rectangle(10, 10, 10, 10));
        btnClear_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                txtResult.setText("");
                operand1="";
            }
        });
        btnClear_1.setFont(new Font("Tahoma", Font.BOLD, 18));
        GridBagConstraints gbc_btnClear_1 = new GridBagConstraints();
        gbc_btnClear_1.fill = GridBagConstraints.BOTH;
        gbc_btnClear_1.insets = new Insets(0, 0, 5, 5);
        gbc_btnClear_1.gridx = 1;
        gbc_btnClear_1.gridy = 1;
        panel_1.add(btnClear_1, gbc_btnClear_1);
        btnClear.setFont(new Font("Tahoma", Font.BOLD, 18));
        GridBagConstraints gbc_btnClear = new GridBagConstraints();
        gbc_btnClear.fill = GridBagConstraints.BOTH;
        gbc_btnClear.insets = new Insets(0, 0, 5, 5);
        gbc_btnClear.gridx = 2;
        gbc_btnClear.gridy = 1;
        panel_1.add(btnClear, gbc_btnClear);
        btnPlus.setFont(new Font("Tahoma", Font.BOLD, 18));
        GridBagConstraints gbc_btnPlus = new GridBagConstraints();
        gbc_btnPlus.fill = GridBagConstraints.BOTH;
        gbc_btnPlus.insets = new Insets(0, 0, 5, 5);
        gbc_btnPlus.gridx = 3;
        gbc_btnPlus.gridy = 1;
        panel_1.add(btnPlus, gbc_btnPlus);
        btnSquareRoot.setFont(new Font("Tahoma", Font.BOLD, 18));
        GridBagConstraints gbc_btnSquareRoot = new GridBagConstraints();
        gbc_btnSquareRoot.fill = GridBagConstraints.BOTH;
        gbc_btnSquareRoot.insets = new Insets(0, 0, 5, 0);
        gbc_btnSquareRoot.gridx = 4;
        gbc_btnSquareRoot.gridy = 1;
        panel_1.add(btnSquareRoot, gbc_btnSquareRoot);

        JButton btnPercentage = new JButton("%");
        btnPercentage.setBounds(new Rectangle(10, 10, 10, 10));
        btnPercentage.addActionListener(al2);

        JButton btnDivide = new JButton("/");
        btnDivide.setBounds(new Rectangle(10, 10, 10, 10));
        btnDivide.addActionListener(al1);
        // Tạo một thành phần JPanel khác có tên 'panel_1', thiết lập màu nền, kích thước, vị trí và bố cục là lưới gồm 4 hàng và 5 cột với khoảng cách 5 pixel giữa chúng.

        JButton btnNumber7 = new JButton("7");
        btnNumber7.setBounds(new Rectangle(10, 10, 10, 10));
        btnNumber7.addActionListener(al);
        btnNumber7.setFont(new Font("Tahoma", Font.BOLD, 18));
        GridBagConstraints gbc_btnNumber7 = new GridBagConstraints();
        gbc_btnNumber7.fill = GridBagConstraints.BOTH;
        gbc_btnNumber7.insets = new Insets(0, 0, 5, 5);
        gbc_btnNumber7.gridx = 0;
        gbc_btnNumber7.gridy = 2;
        panel_1.add(btnNumber7, gbc_btnNumber7);
        // Tạo một thành phần JButton có tên 'btnNumber7' với văn bản "7", thiết lập font và thêm ActionListener có tên 'al' để thực hiện hành động khi nút này được nhấn, sau đó thêm nó vào JPanel 'panel_1'.
        // tương tự
        JButton btnNumber8 = new JButton("8");
        btnNumber8.setBounds(new Rectangle(10, 10, 10, 10));
        btnNumber8.addActionListener(al);
        btnNumber8.setFont(new Font("Tahoma", Font.BOLD, 18));
        GridBagConstraints gbc_btnNumber8 = new GridBagConstraints();
        gbc_btnNumber8.fill = GridBagConstraints.BOTH;
        gbc_btnNumber8.insets = new Insets(0, 0, 5, 5);
        gbc_btnNumber8.gridx = 1;
        gbc_btnNumber8.gridy = 2;
        panel_1.add(btnNumber8, gbc_btnNumber8);

        JButton btnNumber9 = new JButton("9");
        btnNumber9.setBounds(new Rectangle(10, 10, 10, 10));
        btnNumber9.addActionListener(al);
        btnNumber9.setFont(new Font("Tahoma", Font.BOLD, 18));
        GridBagConstraints gbc_btnNumber9 = new GridBagConstraints();
        gbc_btnNumber9.fill = GridBagConstraints.BOTH;
        gbc_btnNumber9.insets = new Insets(0, 0, 5, 5);
        gbc_btnNumber9.gridx = 2;
        gbc_btnNumber9.gridy = 2;
        panel_1.add(btnNumber9, gbc_btnNumber9);
        btnDivide.setFont(new Font("Tahoma", Font.BOLD, 18));
        GridBagConstraints gbc_btnDivide = new GridBagConstraints();
        gbc_btnDivide.fill = GridBagConstraints.BOTH;
        gbc_btnDivide.insets = new Insets(0, 0, 5, 5);
        gbc_btnDivide.gridx = 3;
        gbc_btnDivide.gridy = 2;
        panel_1.add(btnDivide, gbc_btnDivide);
        btnPercentage.setFont(new Font("Tahoma", Font.BOLD, 18));
        GridBagConstraints gbc_btnPercentage = new GridBagConstraints();
        gbc_btnPercentage.fill = GridBagConstraints.BOTH;
        gbc_btnPercentage.insets = new Insets(0, 0, 5, 0);
        gbc_btnPercentage.gridx = 4;
        gbc_btnPercentage.gridy = 2;
        panel_1.add(btnPercentage, gbc_btnPercentage);

        JButton btnFraction = new JButton("1/x");
        btnFraction.setBounds(new Rectangle(10, 10, 10, 10));
        btnFraction.addActionListener(al2);

        JButton btnMultiply = new JButton("*");
        btnMultiply.setBounds(new Rectangle(10, 10, 10, 10));
        btnMultiply.addActionListener(al1);

        JButton btnNumber6 = new JButton("6");
        btnNumber6.setBounds(new Rectangle(10, 10, 10, 10));
        btnNumber6.addActionListener(al);

        JButton btnNumber5 = new JButton("5");
        btnNumber5.setBounds(new Rectangle(10, 10, 10, 10));
        btnNumber5.addActionListener(al);

        JButton btnNumber4 = new JButton("4");
        btnNumber4.setBounds(new Rectangle(10, 10, 10, 10));
        btnNumber4.addActionListener(al);
        btnNumber4.setFont(new Font("Tahoma", Font.BOLD, 18));
        GridBagConstraints gbc_btnNumber4 = new GridBagConstraints();
        gbc_btnNumber4.fill = GridBagConstraints.BOTH;
        gbc_btnNumber4.insets = new Insets(0, 0, 5, 5);
        gbc_btnNumber4.gridx = 0;
        gbc_btnNumber4.gridy = 3;
        panel_1.add(btnNumber4, gbc_btnNumber4);
        btnNumber5.setFont(new Font("Tahoma", Font.BOLD, 18));
        GridBagConstraints gbc_btnNumber5 = new GridBagConstraints();
        gbc_btnNumber5.fill = GridBagConstraints.BOTH;
        gbc_btnNumber5.insets = new Insets(0, 0, 5, 5);
        gbc_btnNumber5.gridx = 1;
        gbc_btnNumber5.gridy = 3;
        panel_1.add(btnNumber5, gbc_btnNumber5);
        btnNumber6.setFont(new Font("Tahoma", Font.BOLD, 18));
        GridBagConstraints gbc_btnNumber6 = new GridBagConstraints();
        gbc_btnNumber6.fill = GridBagConstraints.BOTH;
        gbc_btnNumber6.insets = new Insets(0, 0, 5, 5);
        gbc_btnNumber6.gridx = 2;
        gbc_btnNumber6.gridy = 3;
        panel_1.add(btnNumber6, gbc_btnNumber6);
        btnMultiply.setFont(new Font("Tahoma", Font.BOLD, 18));
        GridBagConstraints gbc_btnMultiply = new GridBagConstraints();
        gbc_btnMultiply.fill = GridBagConstraints.BOTH;
        gbc_btnMultiply.insets = new Insets(0, 0, 5, 5);
        gbc_btnMultiply.gridx = 3;
        gbc_btnMultiply.gridy = 3;
        panel_1.add(btnMultiply, gbc_btnMultiply);
        btnFraction.setFont(new Font("Tahoma", Font.BOLD, 18));
        GridBagConstraints gbc_btnFraction = new GridBagConstraints();
        gbc_btnFraction.fill = GridBagConstraints.BOTH;
        gbc_btnFraction.insets = new Insets(0, 0, 5, 0);
        gbc_btnFraction.gridx = 4;
        gbc_btnFraction.gridy = 3;
        panel_1.add(btnFraction, gbc_btnFraction);

        JButton btnEquals = new JButton("=");
        btnEquals.setBounds(new Rectangle(10, 10, 10, 10));
        btnEquals.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(txtResult.getText().equals("")||operand1.equals(""))
                {
                    return;
                }
                try {
                    double oprand1= Double.parseDouble(operand1);
                    double oprand2= Double.parseDouble(txtResult.getText());
                    double result = 0;
                    if(operator.equals("+"))
                            result = calculator.add(oprand1, oprand2);
                    else if (operator.equals("-"))
                            result= calculator.subtract(oprand1, oprand2);
                    else if (operator.equals("*"))
                            result= calculator.multiply(oprand1, oprand2);
                    else if (operator.equals("/"))
                            result= calculator.divide(oprand1, oprand2);
                    txtResult.setText(""+result);
                    System.out.println("Output: "+ result);
                    isEqualsPress=true;
                } catch (Exception ex)
                {
                        ex.printStackTrace();
                }
            }
        });

        JButton btnNumber2 = new JButton("2");
        btnNumber2.setBounds(new Rectangle(10, 10, 10, 10));
        btnNumber2.addActionListener(al);

        JButton btnNumber1 = new JButton("1");
        btnNumber1.setBounds(new Rectangle(10, 10, 10, 10));
        btnNumber1.addActionListener(al);
        btnNumber1.setFont(new Font("Tahoma", Font.BOLD, 18));
        GridBagConstraints gbc_btnNumber1 = new GridBagConstraints();
        gbc_btnNumber1.fill = GridBagConstraints.BOTH;
        gbc_btnNumber1.insets = new Insets(0, 0, 5, 5);
        gbc_btnNumber1.gridx = 0;
        gbc_btnNumber1.gridy = 4;
        panel_1.add(btnNumber1, gbc_btnNumber1);
        btnNumber2.setFont(new Font("Tahoma", Font.BOLD, 18));
        GridBagConstraints gbc_btnNumber2 = new GridBagConstraints();
        gbc_btnNumber2.fill = GridBagConstraints.BOTH;
        gbc_btnNumber2.insets = new Insets(0, 0, 5, 5);
        gbc_btnNumber2.gridx = 1;
        gbc_btnNumber2.gridy = 4;
        panel_1.add(btnNumber2, gbc_btnNumber2);

        JButton btnSubtract = new JButton("-");
        btnSubtract.setBounds(new Rectangle(10, 10, 10, 10));
        btnSubtract.addActionListener(al1);

        JButton btnNumber3 = new JButton("3");
        btnNumber3.setBounds(new Rectangle(10, 10, 10, 10));
        btnNumber3.addActionListener(al);
        btnNumber3.setFont(new Font("Tahoma", Font.BOLD, 18));
        GridBagConstraints gbc_btnNumber3 = new GridBagConstraints();
        gbc_btnNumber3.fill = GridBagConstraints.BOTH;
        gbc_btnNumber3.insets = new Insets(0, 0, 5, 5);
        gbc_btnNumber3.gridx = 2;
        gbc_btnNumber3.gridy = 4;
        panel_1.add(btnNumber3, gbc_btnNumber3);
        btnSubtract.setFont(new Font("Tahoma", Font.BOLD, 18));
        GridBagConstraints gbc_btnSubtract = new GridBagConstraints();
        gbc_btnSubtract.fill = GridBagConstraints.BOTH;
        gbc_btnSubtract.insets = new Insets(0, 0, 5, 5);
        gbc_btnSubtract.gridx = 3;
        gbc_btnSubtract.gridy = 4;
        panel_1.add(btnSubtract, gbc_btnSubtract);
        btnEquals.setFont(new Font("Tahoma", Font.BOLD, 18));
        GridBagConstraints gbc_btnEquals = new GridBagConstraints();
        gbc_btnEquals.gridheight = 2;
        gbc_btnEquals.fill = GridBagConstraints.BOTH;
        gbc_btnEquals.gridx = 4;
        gbc_btnEquals.gridy = 4;
        panel_1.add(btnEquals, gbc_btnEquals);

        JButton btnNumber0 = new JButton("0");
        btnNumber0.setBounds(new Rectangle(10, 10, 10, 10));
        btnNumber0.addActionListener(al);
        btnNumber0.setFont(new Font("Tahoma", Font.BOLD, 18));
        GridBagConstraints gbc_btnNumber0 = new GridBagConstraints();
        gbc_btnNumber0.gridwidth = 2;
        gbc_btnNumber0.fill = GridBagConstraints.BOTH;
        gbc_btnNumber0.insets = new Insets(0, 0, 0, 5);
        gbc_btnNumber0.gridx = 0;
        gbc_btnNumber0.gridy = 5;
        panel_1.add(btnNumber0, gbc_btnNumber0);

        JButton btnNewButton_5 = new JButton(".");
        btnNewButton_5.setBounds(new Rectangle(10, 10, 10, 10));
        btnNewButton_5.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                        if (!txtResult.getText().contains(".")) {
                                txtResult.setText(txtResult.getText() + ".");
                    }
                }
        });
        btnNewButton_5.setFont(new Font("Tahoma", Font.BOLD, 18));
        GridBagConstraints gbc_btnNewButton_5 = new GridBagConstraints();
        gbc_btnNewButton_5.fill = GridBagConstraints.BOTH;
        gbc_btnNewButton_5.insets = new Insets(0, 0, 0, 5);
        gbc_btnNewButton_5.gridx = 2;
        gbc_btnNewButton_5.gridy = 5;
        panel_1.add(btnNewButton_5, gbc_btnNewButton_5);

        JButton btnAdd = new JButton("+");
        btnAdd.setBounds(new Rectangle(10, 10, 10, 10));
        btnAdd.addActionListener(al1);
        btnAdd.setFont(new Font("Tahoma", Font.BOLD, 18));
        GridBagConstraints gbc_btnAdd = new GridBagConstraints();
        gbc_btnAdd.fill = GridBagConstraints.BOTH;
        gbc_btnAdd.insets = new Insets(0, 0, 0, 5);
        gbc_btnAdd.gridx = 3;
        gbc_btnAdd.gridy = 5;
        panel_1.add(btnAdd, gbc_btnAdd);

        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        JMenu mnNewMenu = new JMenu("View");
        mnNewMenu.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        menuBar.add(mnNewMenu);

        JMenu mnNewMenu_1 = new JMenu("Edit");
        mnNewMenu_1.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        menuBar.add(mnNewMenu_1);

        JMenu mnNewMenu_2 = new JMenu("Help");
        mnNewMenu_2.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        menuBar.add(mnNewMenu_2);		
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}

