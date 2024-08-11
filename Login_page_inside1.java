import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.*;
import java.util.ArrayList;  // Import List and ArrayList
import java.util.List;
import java.sql.*;

class Login_page_inside extends Frame implements ActionListener
{
    Label ftb, wlc;
    Button hm, mg, st, snd,l1,l2;
    TextField textField;
    TextArea textArea;

    Socket clientSocket;
    BufferedReader reader;
    BufferedWriter writer;
    private RealGUI calculatorWindow;

    private String enteredUsername;

    Login_page_inside(String username) 
    {
        this.enteredUsername = username;

        // Start the server
        startServer();

        setTitle("Face The Book");
        setSize(1535, 827);
        setVisible(true);
        setLayout(null);
        setBackground(Color.WHITE);

        ftb = new Label("Face The Book");
        ftb.setForeground(Color.BLUE);
        ftb.setFont(new Font("Serif", Font.BOLD, 65));
        wlc = new Label("Welcome");
        wlc.setFont(new Font("serif", Font.BOLD, 35));
        hm = new Button("Home");
        hm.setBackground(Color.GREEN);
        hm.setFont(new Font("serif", Font.PLAIN, 35));
        mg = new Button("Message");
        mg.setBackground(Color.GREEN);
        mg.setFont(new Font("serif", Font.PLAIN, 35));
        st = new Button("Settings");
        st.setBackground(Color.GREEN);
        st.setFont(new Font("Serif", Font.PLAIN, 35));
        snd = new Button("Send");
        snd.setBackground(Color.LIGHT_GRAY);
        textArea = new TextArea();
        textArea.setFont(new Font("Serif", Font.PLAIN, 30));
        textArea.setBackground(Color.LIGHT_GRAY);
        textField = new TextField("Enter your message");
        textField.setFont(new Font("Serif", Font.PLAIN, 30));
        textField.setBackground(Color.LIGHT_GRAY);
        l1=new Button("Calculator");
        l2=new Button("unknown");

        ftb.setBounds(570, 25, 430, 80);
        wlc.setBounds(700, 100, 150, 50);
        hm.setBounds(450, 150, 200, 50);
        mg.setBounds(650, 150, 200, 50);
        st.setBounds(850, 150, 200, 50);
        snd.setBounds(950, 530, 100, 50);
        textArea.setBounds(450, 220, 600, 300);
        textField.setBounds(450, 530, 490, 50);
        l1.setBounds(450,220,150,40);
        l2.setBounds(450,260,150,40);

        add(ftb);
        add(wlc);
        add(hm);
        add(mg);
        add(st);
        add(textArea);
        add(textField);
        add(snd);
        add(l1);
        add(l2);

        textArea.setVisible(false);
        textField.setVisible(false);
        snd.setVisible(false);
        l1.setVisible(false);
        l2.setVisible(false);

        mg.addActionListener(this);
        snd.addActionListener(this);
        hm.addActionListener(this);
        st.addActionListener(this);
        l1.addActionListener(this);
        l2.addActionListener(this);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
                try 
                {
                    if (clientSocket != null) 
                    {
                        clientSocket.close();
                    }
                } catch (IOException ex) 
                {
                    ex.printStackTrace();
                }
                if (calculatorWindow != null) {
                    calculatorWindow.dispose();
                }
            }
        });
    }

    public void actionPerformed(ActionEvent ee) 
    {
        if (ee.getSource() == mg) 
        {
            l1.setVisible(false);
            l2.setVisible(false);
            textArea.setVisible(true);
            textField.setVisible(true);
            snd.setVisible(true);

            // Connect to the server
            connectToServer();
        } 
        else if (ee.getSource() == snd) 
        {
            String message = textField.getText();
            textArea.append(Login_Id()+":->" + message + "\n");
            textField.setText("");

            // Send the message to the server
            sendMessageToServer(message);
        } 
        else if (ee.getSource() == hm || ee.getSource() == st) 
        {
            textArea.setVisible(false);
            textField.setVisible(false);
            snd.setVisible(false);
            
            l1.setVisible(true);
            l2.setVisible(true);
        }
        else if(ee.getSource()==l1)
        {
            if (calculatorWindow == null) {
                calculatorWindow = new RealGUI();
            } else {
                calculatorWindow.setVisible(true);
            }
        }
        else if(ee.getSource()==l2)
        {

        }
    }

    public void startServer() 
    {
        // Start the server in a new thread
        new Thread(() -> {
            Server server = new Server();
            server.start();
        }).start();
    }

    public void connectToServer() 
    {
        try {
            clientSocket = new Socket("localhost", 9999);
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

            // Start a thread to continuously receive messages from the server
            new Thread(() -> {
                try {
                    String serverMessage;
                    while ((serverMessage = reader.readLine()) != null) {
                        textArea.append("Server: " + serverMessage + "\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessageToServer(String message) 
    {
        // Send the message to the server
        try {
            writer.write(message + "\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String Login_Id() 
    {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/facethebook", "root", "nihcas");
    
            // Checking login credentials using a PreparedStatement
            String query = "SELECT first_name FROM user_details WHERE (mobileno=? OR email=?)";
            try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
                preparedStatement.setString(1, enteredUsername);
                preparedStatement.setString(2, enteredUsername);
    
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    // Print the first_name
                    String firstName = resultSet.getString("first_name");
                    System.out.println("Welcome, " + firstName);
                    return firstName;
    
                } else {
                    System.out.println("User not found");
                    return "User not found"; // Provide a default value or handle this case accordingly
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error occurred"; // Provide a default value or handle this case accordingly
        }
    }
    
}

class Server 
{
    public static List<Socket> clients = new ArrayList<>();
    public static List<PrintWriter> writers = new ArrayList<>();

    public void start()
    {
        try {
            ServerSocket serverSocket = new ServerSocket(9999);
            System.out.println("Server is running...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());

                clients.add(clientSocket);

                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
                writers.add(writer);

                new Thread(() -> {
                    try {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                        String clientMessage;
                        while ((clientMessage = reader.readLine()) != null) {
                            System.out.println("Received from " + clientSocket.getInetAddress() + ": " + clientMessage);
                            broadcast(clientMessage, writer);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void broadcast(String message, PrintWriter sender) 
    {
        for (PrintWriter writer : writers) {
            if (writer != sender) {
                writer.println(message);
            }
        }
    }
}

class Login_page_inside1
{
    public static void main(String[] args) {
        Login_page_inside l = new Login_page_inside("8191059756");
    }
}
