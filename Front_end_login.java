import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.*;

class Front_login extends Frame implements ActionListener
{
    Label usr,pas,fb;
    TextField t1,t2;
    Button lgn,create;
    Front_login()
    {
        setTitle("Login Page");
        setSize(400,400);
        setLayout(null);
        setVisible(true);
        setBackground(Color.LIGHT_GRAY);

        usr=new Label("Login");
        usr.setFont(new Font("Serif", Font.PLAIN, 25));
        pas=new Label("Password");
        pas.setFont(new Font("Serif", Font.PLAIN, 25));
        fb=new Label("FaceTheBook");
        fb.setFont(new Font("Serif", Font.BOLD, 35));
        fb.setForeground(Color.BLUE);
        t1=new TextField(20);
        t1=createTextField("Enter Mobile or Email", 20);
        t1.setFont(new Font("Serif", Font.PLAIN, 20));
        t2=new TextField(20);
        t2=createTextField("Enter Password",20);
        t2.setFont(new Font("Serif", Font.PLAIN, 19));
        lgn=new Button("Log in");
        lgn.setFont(new Font("Serif", Font.BOLD, 20));
        lgn.setBackground(Color.BLUE);
        lgn.setForeground(Color.WHITE);
        create=new Button("Create new account");
        create.setFont(new Font("Serif", Font.BOLD, 20));
        create.setBackground(Color.GREEN);
        create.setForeground(Color.WHITE);

        fb.setBounds(90,60,220,40);
        usr.setBounds(30, 125, 110, 40);
        pas.setBounds(30,195,110,40);
        t1.setBounds(160,130,200,35);
        t2.setBounds(160,200,200,35);
        lgn.setBounds(30,260,330,40);
        create.setBounds(115,320,180,50);

        add(fb);
        add(usr);
        add(t1);
        add(pas);
        add(t2);
        add(lgn);
        add(create);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e)
            {
                dispose();
            }
        });

        create.addActionListener(this);
        lgn.addActionListener(this);
    }

    private TextField createTextField(String defaultText, int columns) //mehtod use to show temporary text info inside textfield
    {
        TextField textField = new TextField(defaultText, columns);
        textField.setForeground(Color.GRAY);

        textField.addFocusListener(new FocusAdapter() 
        {
            @Override
            public void focusGained(FocusEvent e)
            {
                if (textField.getText().equals(defaultText)) 
                {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e)
            {
                if (textField.getText().isEmpty()) 
                {
                    textField.setText(defaultText);
                    textField.setForeground(Color.GRAY);
                }
            }
        });

        return textField;
    }
    private String enteredUsername;
    private String enteredPassword;

    public void actionPerformed(ActionEvent e1)
    {
        if(e1.getSource()==create)
        {
            create_reg r=new create_reg();
            dispose();
        }
        if(e1.getSource()==lgn)
        {
            enteredUsername = t1.getText();
            enteredPassword = t2.getText();

            if (checkLogin(enteredUsername, enteredPassword)) 
            {
                // Successful login
                Login_page_inside l = new Login_page_inside(enteredUsername);
        
                System.out.println("Login successful!");
                dispose();
            } else 
            {
                // Failed login
                System.out.println("Invalid username or password");
            }
        }
    }

    private boolean checkLogin(String username, String password) 
    {
        try 
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/facethebook", "root", "nihcas");

            // Checking login credentials using a PreparedStatement
            String query = "SELECT * FROM user_details,authentication WHERE (mobileno=? OR email=?) AND password=?";
            try (PreparedStatement preparedStatement = con.prepareStatement(query))
            {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, username);
                preparedStatement.setString(3, password);

                ResultSet resultSet = preparedStatement.executeQuery();
                return resultSet.next(); // If a record is found, the login is successful
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Login fails in case of an exception
        }
    }
}
class Front_end_login
{
    public static void main(String[] args)
    {
        Front_login f = new Front_login();    
    }
}