import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.*;

class create_reg extends Frame implements ActionListener
{
    Label sgnup,fn,sn,mb,em,ps;
    TextField fn1,sn1,mb1,em1,ps1;
    Button sgn;
    create_reg()
    {
        setTitle("Registration Form");
        setSize(400,400);
        setVisible(true);
        setLayout(null);
        setBackground(Color.LIGHT_GRAY);

        sgnup=new Label("Sign Up");
        sgnup.setFont(new Font("Serif", Font.BOLD, 30));
        fn=new Label("FirstName");
        fn.setFont(new Font("Serif", Font.PLAIN, 20));
        sn=new Label("SurName");
        sn.setFont(new Font("Serif", Font.PLAIN, 20));
        mb=new Label("MobileNo");
        mb.setFont(new Font("Serif", Font.PLAIN, 20));
        em=new Label("Email");
        em.setFont(new Font("Serif", Font.PLAIN, 20));
        ps=new Label("Password");
        ps.setFont(new Font("Serif", Font.PLAIN, 20));

        // fn1=new TextField(20);
        // sn1=new TextField(20);
        // mb1=new TextField(20);
        // em1=new TextField(20);
        // ps1=new TextField(20);
        
        // Set custom default text for each TextField
        fn1 = createTextField("Enter First Name", 20);
        sn1 = createTextField("Enter SurName", 20);
        mb1 = createTextField("Enter Mobile Number", 20);
        em1 = createTextField("Enter Email", 20);
        ps1 = createTextField("Enter Password", 20);

        sgn=new Button("Sign Up");
        sgn.setFont(new Font("Serif", Font.BOLD, 19));
        sgn.setBackground(Color.GREEN);
        sgn.setForeground(Color.white);

        fn1.setFont(new Font("Serif", Font.PLAIN, 18));
        sn1.setFont(new Font("Serif", Font.PLAIN, 18));
        mb1.setFont(new Font("Serif", Font.PLAIN, 18));
        em1.setFont(new Font("Serif", Font.PLAIN, 18));
        ps1.setFont(new Font("Serif", Font.PLAIN, 18));

        sgnup.setBounds(150,20,110,50);
        fn.setBounds(50,80,90,27);
        fn1.setBounds(150,80,190,27);
        sn.setBounds(50,130,90,27);
        sn1.setBounds(150,130,190,27);
        mb.setBounds(50,180,90,27);
        mb1.setBounds(150,180,190,27);
        em.setBounds(50,230,90,27);
        em1.setBounds(150,230,190,27);
        ps.setBounds(50,280,90,27);
        ps1.setBounds(150,280,190,27);
        sgn.setBounds(140,330,110,40);

        add(sgnup);
        add(fn);
        add(fn1);
        add(sn);
        add(sn1);
        add(mb);
        add(mb1);
        add(em);
        add(em1);
        add(ps);
        add(ps1);
        add(sgn);

        sgn.addActionListener(this);

        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e)
            {
                dispose();
                Front_login frontLogin = new Front_login();
            }
        });
    }

    private TextField createTextField(String defaultText, int columns)  //mehtod use to show temporary text info inside textfield
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

    public void actionPerformed(ActionEvent e2)
    {
        if (e2.getSource() == sgn) 
        {
            try 
            {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/facethebook", "root", "nihcas");
        
                // Create a PreparedStatement with RETURN_GENERATED_KEYS option
                String insertDetailsQuery = "INSERT INTO user_details(first_name, surname, mobileno, email) VALUES (?, ?, ?, ?)";
                try(PreparedStatement stm = con.prepareStatement(insertDetailsQuery, Statement.RETURN_GENERATED_KEYS)) 
                {
                    stm.setString(1, fn1.getText());
                    stm.setString(2, sn1.getText());
                    stm.setLong(3, Long.parseLong(mb1.getText()));
                    stm.setString(4, em1.getText());
                    stm.executeUpdate();
        
                    // Retrieve the generated user_id
                    try(ResultSet generatedKeys = stm.getGeneratedKeys())
                    {
                        if(generatedKeys.next())
                        {
                            int userId = generatedKeys.getInt(1);
        
                            // Insert user password
                            String insertPasswordQuery = "INSERT INTO authentication(usr_id, password) VALUES (?, ?)";
                            try(PreparedStatement passwordStatement = con.prepareStatement(insertPasswordQuery))
                            {
                                passwordStatement.setInt(1, userId);
                                passwordStatement.setString(2, ps1.getText());
                                passwordStatement.executeUpdate();
                            }
                        } else {
                            throw new SQLException("User ID not generated.");
                        }
                    }
                }
                dispose();
                Front_login frontLogin = new Front_login();
                
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        
    }
}
class Create_Form
{
    public static void main(String[] args) 
    {
        create_reg r=new create_reg();
    }
}