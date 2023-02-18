import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main implements ActionListener {
    static JFrame frame = new JFrame("Калькулятор");
    static JPanel mainPanel = new JPanel();
    static JPanel buttonPanel = new JPanel();
    static JTextField textField = new JTextField();
    static JButton[] buttons = new JButton[16];
    static int width = 300, height = 420;
    static String x = "", y = "", operation = "", output = "";
    static String[] operations = {"+", "-", "*", "/", "=", "C"};
    static Font f = new Font("Calibri", Font.BOLD, 45);

    
    public static void initialButtons(Main listen) {
        for (int i = 0; i < 10; i++) {
            buttons[i] = new JButton("" + i);
            buttons[i].setPreferredSize(new Dimension(60, 60));
            buttons[i].setMargin(new Insets(16, 0, 0, 0));
            buttons[i].setFont(f);
            buttons[i].addActionListener(listen);
        }

        for (int i = 10; i < 16; i++) {
            buttons[i] = new JButton(operations[i - 10]);
            buttons[i].setPreferredSize(new Dimension(60, 60));
            buttons[i].setMargin(new Insets(16, 0, 0, 0));
            buttons[i].setFont(f);
            buttons[i].addActionListener(listen);
        }
    }

    public static void prepareButtonPanel() {
        buttonPanel.setLayout(new GridLayout(4, 4, 10, 10));
        buttonPanel.setSize(270, 270);
        for (int i = 0; i < 3; i++) {
            for (int j = 1; j < 4; j++) {
                buttonPanel.add(buttons[3 * i + j]);
            }
            buttonPanel.add(buttons[10 + i]);
        }
        buttonPanel.add(buttons[15]);
        buttonPanel.add(buttons[0]);
        buttonPanel.add(buttons[14]);
        buttonPanel.add(buttons[13]);
    }

    public static void main(String[] args) {

        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setBounds(d.width / 2 - width / 2, d.height / 2 - height / 2, width, height);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        textField.setMaximumSize(new Dimension(270, 25));
        textField.setFont(f);
        textField.setMargin(new Insets(16, 0, 0, 0));
        textField.setFocusable(false);

        Main listen = new Main();
        initialButtons(listen);
        prepareButtonPanel();

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(textField);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(buttonPanel);

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String a = actionEvent.getActionCommand();
        if (a.charAt(0) >= '0' && a.charAt(0) <= '9') {
            if (operation.equals("")) {
                x += a;
            } else {
                y += a;
            }
            textField.setText(x + " " + operation + " " + y);
        } else if (a.charAt(0)=='C') {
            x = y = operation = "";
            textField.setText(x + " " + operation + " " + y);
        } else if (a.charAt(0)=='=' /*&& !y.equals("")*/) {
            switch (operation) {
                case "+" : output = "" + (Integer.parseInt(x) + Integer.parseInt(y)); break;
                case "-" : output = "" + (Integer.parseInt(x) - Integer.parseInt(y)); break;
                case "*" : output = "" + (Integer.parseInt(x) * Integer.parseInt(y)); break;
                case "/" : {
                    if (Integer.parseInt(y)!=0)
                        output = "" + (Integer.parseInt(x) / Integer.parseInt(y));
                    else
                        output = "Ошибка!";
                } break;
                default : output = "";
            }
            textField.setText(output);
            if (output.equals("Ошибка!")) {
                x = y = operation = "";
            } else {
                x = output;
                y = operation = "";
            }
        } else {
            if (operation.equals(""))
                operation = a;
            textField.setText(x + " " + operation + " " + y);
        }
    }
}
