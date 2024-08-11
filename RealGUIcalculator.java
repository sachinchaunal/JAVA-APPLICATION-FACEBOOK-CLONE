import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent; 

class RealGUI extends Frame implements ActionListener 
{
    TextField t1;
    Button one, two, three, four, five, six, seven, eight, nine, zero, addition, subtraction, multiplication, division, equal, clear;
    Label l1;
    RealGUI() 
    {
        setTitle("Calculator");
        setLayout(null);
        setSize(470, 500);
        setVisible(true);
        setBackground(Color.orange);

        t1 = new TextField(40);
        t1.setBackground(Color.lightGray);
        one = new Button("1");
        two = new Button("2");
        three = new Button("3");
        four = new Button("4");
        five = new Button("5");
        six = new Button("6");
        seven = new Button("7");
        eight = new Button("8");
        nine = new Button("9");
        zero = new Button("0");
        addition = new Button("+");
        subtraction = new Button("-");
        division = new Button("/");
        multiplication = new Button("*");
        equal = new Button("=");
        clear = new Button("<--");
        l1 = new Label("Made by Sachin");
        l1.setFont(new Font("Serif", Font.PLAIN, 45));
        l1.setForeground(Color.CYAN);

        t1.setBounds(20, 30, 432, 60);
        t1.setFont(new Font("Serif", Font.BOLD, 45));
        one.setBounds(20, 90, 100, 50);
        two.setBounds(130, 90, 100, 50);
        three.setBounds(239, 90, 100, 50);
        four.setBounds(20, 150, 100, 50);
        five.setBounds(130, 150, 100, 50);
        six.setBounds(239, 150, 100, 50);
        seven.setBounds(20, 212, 100, 50);
        eight.setBounds(130, 212, 100, 50);
        nine.setBounds(239, 212, 100, 50);
        zero.setBounds(130, 275, 100, 50);
        addition.setBounds(350, 90, 100, 50);
        subtraction.setBounds(350, 150, 100, 50);
        division.setBounds(350, 212, 100, 50);
        multiplication.setBounds(350, 275, 100, 50);
        equal.setBounds(239, 275, 100, 50);
        clear.setBounds(20, 275, 100, 50);
        l1.setBounds(60,370,350,50);

        add(t1);
        add(one);
        add(two);
        add(three);
        add(four);
        add(five);
        add(six);
        add(seven);
        add(eight);
        add(nine);
        add(zero);
        add(addition);
        add(subtraction);
        add(division);
        add(multiplication);
        add(equal);
        add(clear);
        add(l1);

        one.addActionListener(this);
        two.addActionListener(this);
        three.addActionListener(this);
        four.addActionListener(this);
        five.addActionListener(this);
        six.addActionListener(this);
        seven.addActionListener(this);
        eight.addActionListener(this);
        nine.addActionListener(this);
        zero.addActionListener(this);
        subtraction.addActionListener(this);
        multiplication.addActionListener(this);
        addition.addActionListener(this);
        division.addActionListener(this);
        equal.addActionListener(this);
        clear.addActionListener(this);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }

    public void actionPerformed(ActionEvent e) 
    {
        String currenttext = t1.getText(); // Get the current text from the TextField

        if (e.getSource() == one) 
        {
            t1.setText(currenttext + "1"); // Append "1" to the current text
        }
        if (e.getSource() == two) 
        {
            t1.setText(currenttext + "2");
        }
        if (e.getSource() == three) 
        {
            t1.setText(currenttext + "3");
        }
        if (e.getSource() == four) 
        {
            t1.setText(currenttext + "4");
        }
        if (e.getSource() == five) 
        {
            t1.setText(currenttext + "5");
        }
        if (e.getSource() == six) 
        {
            t1.setText(currenttext + "6");
        }
        if (e.getSource() == seven) 
        {
            t1.setText(currenttext + "7");
        }
        if (e.getSource() == eight) 
        {
            t1.setText(currenttext + "8");
        }
        if (e.getSource() == nine) 
        {
            t1.setText(currenttext + "9");
        }
        if (e.getSource() == zero) 
        {
            t1.setText(currenttext + "0");
        }
        if (e.getSource() == addition) 
        {
            t1.setText(currenttext + " + ");
        }
        if (e.getSource() == subtraction) 
        {
            t1.setText(currenttext + " - ");
        }
        if (e.getSource() == division) 
        {
            t1.setText(currenttext + " / ");
        }
        if (e.getSource() == multiplication) 
        {
            t1.setText(currenttext + " * ");
        }
        if (e.getSource() == equal)
        {
            try
            {
                // Using split to separate operands and operator
                String[] parts = currenttext.split(" ");
                double operand1 = Double.parseDouble(parts[0]);
                String operator = parts[1];
                double operand2 = Double.parseDouble(parts[2]);

                // Perform the calculation based on the operator
                double result = 0.0;
                switch (operator)
                {
                    case "+":
                    result = operand1 + operand2;
                    break;
                    case "-":
                    result = operand1 - operand2;
                    break;
                    case "*":
                    result = operand1 * operand2;
                    break;
                    case "/":
                    if (operand2 != 0)            // Check for division by zero
                    {
                        result = operand1 / operand2;
                    }
                    else 
                    {
                        t1.setText("Error1");
                        return;                 // Exit the method to avoid setting the result on error
                    }
                    break;
                    default:
                    t1.setText("Error2");
                    return;                      // Exit the method for an invalid operator
                }
                
                // Display the result in the TextField
                t1.setText(String.valueOf(result));

            } catch (NumberFormatException | ArrayIndexOutOfBoundsException ex) 
            {
                t1.setText("Error3"); // Display an error message for invalid input format
            }
        }
        if(e.getSource()==clear)
        {
            t1.setText("");
        }
    }
}

class RealGUIcalculator
{
    public static void main(String[] args) 
    {
        RealGUI r = new RealGUI();
    }
}