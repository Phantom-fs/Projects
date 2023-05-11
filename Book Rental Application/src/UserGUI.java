//if library not available, remove it and all the annotations
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserGUI extends JFrame implements ActionListener
{
    private Connection conn;
    private JComboBox<String> bookList;
    private JLabel author;
    private JLabel description;
    private JLabel available;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JRadioButton rentButton, returnButton;
    private JButton submitButton;

    JFrame frame = new JFrame("Book Rental System");

    public UserGUI()
    {
        super("Book Rental System");

        frame.setSize(480, 400);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //**********************************************************/
        // Set up database connection
        try
        {
            //enter the password of your server
            conn = DriverManager.getConnection("jdbc:mysql://localhost/book_rental", "root", "");
        }
        catch (SQLException ex)
        {
            JOptionPane.showMessageDialog(frame, "Failed to connect to database: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Set up UI components
        JLabel titleLabel = new JLabel("Book Rental System");

        JLabel bookLabel = new JLabel("Select a book:");
        String[] books = booksFiller();
        bookList = new JComboBox<>(books);

        description = new JLabel();
        author = new JLabel();
        available = new JLabel();

        JLabel userLabel = new JLabel("Username:");
        usernameField = new JTextField(20);

        JLabel passLabel = new JLabel("Password:");
        passwordField = new JPasswordField(20);

        rentButton = new JRadioButton("Rent");
        returnButton = new JRadioButton("Return");

        ButtonGroup radioGroup = new ButtonGroup();
        radioGroup.add(rentButton);
        radioGroup.add(returnButton);

        submitButton = new JButton("Submit");

        // Set up UI component, font & positions
        //title in RED
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.RED);
        titleLabel.setBounds(140, 6, 200, 30);
        frame.add(titleLabel);

        //book label
        bookLabel.setFont(new Font("Arial", Font.BOLD, 15));
        bookLabel.setForeground(Color.BLACK);
        bookLabel.setBounds(10, 50, 180, 20);
        frame.add(bookLabel);

        //book list
        bookList.setFont(new Font("Arial", Font.PLAIN, 15));
        bookList.setForeground(Color.BLACK);
        bookList.setBackground(Color.WHITE);
        bookList.setBounds(130, 46, 250, 30);
        frame.add(bookList);

        author.setFont(new Font("Arial", Font.BOLD, 14));
        author.setForeground(Color.BLACK);
        author.setBounds(10, 80, 200, 30);
        frame.add(author);

        description.setFont(new Font("Arial", Font.BOLD, 14));
        description.setForeground(Color.BLACK);
        description.setBounds(10, 100, 450, 30);
        frame.add(description);

        available.setFont(new Font("Arial", Font.BOLD, 14));
        available.setForeground(Color.BLACK);
        available.setBounds(10, 120, 200, 30);
        frame.add(available);

        userLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        userLabel.setForeground(Color.BLACK);
        userLabel.setBounds(10, 160, 180, 20);
        frame.add(userLabel);

        usernameField.setFont(new Font("Arial", Font.PLAIN, 15));
        usernameField.setForeground(Color.BLACK);
        usernameField.setBounds(10, 180, 250, 30);
        frame.add(usernameField);

        passLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        passLabel.setForeground(Color.BLACK);
        passLabel.setBounds(10, 210, 180, 20);
        frame.add(passLabel);

        passwordField.setFont(new Font("Arial", Font.PLAIN, 15));
        passwordField.setForeground(Color.BLACK);
        passwordField.setBounds(10, 230, 250, 30);
        frame.add(passwordField);

        rentButton.setFont(new Font("Arial", Font.BOLD, 18));
        rentButton.setForeground(Color.BLACK);
        rentButton.setBackground(Color.WHITE);
        rentButton.setBounds(100, 280, 100, 25);
        frame.add(rentButton);

        returnButton.setFont(new Font("Arial", Font.BOLD, 18));
        returnButton.setForeground(Color.BLACK);
        returnButton.setBackground(Color.WHITE);
        returnButton.setBounds(280, 280, 100, 25);
        frame.add(returnButton);

        submitButton.setFont(new Font("Arial", Font.PLAIN, 20));
        submitButton.setForeground(Color.BLACK);
        submitButton.setBounds(175, 320, 100, 30);
        frame.add(submitButton);

        frame.getContentPane().setBackground(Color.WHITE);

        // Set up action listeners
        bookList.addActionListener(this);
        submitButton.addActionListener(this);
    }

    public String[] booksFiller()
    {
        List<String> books = new ArrayList<>();
        try
        {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT title FROM books");

            while (rs.next())
            {
                books.add(rs.getString("title"));
            }
        }
        catch (SQLException ex)
        {
            JOptionPane.showMessageDialog(frame, "Failed to retrieve book data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return books.toArray(new String[0]);
    }

    public void actionPerformed(@NotNull ActionEvent e)
    {
        if (e.getSource() == bookList)
        {
            // get the selected book name and fill description, author etc.
            try
            {
                String selectedBook = (String) bookList.getSelectedItem();
                PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM books WHERE title = ?");
                pstmt.setString(1, selectedBook);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next())
                {
                    author.setText("Author: " + rs.getString("author"));
                    description.setText(rs.getString("description"));
                    available.setText("Available: " + (rs.getString("available").equals("1") ? "Yes" : "No"));
                }
            }
            catch (SQLException ex)
            {
                JOptionPane.showMessageDialog(frame, "Failed to retrieve book data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        else if (e.getSource() == submitButton)
        {
            int userId = -1;
            String username = usernameField.getText();
            String password = String.valueOf(passwordField.getPassword());

            try
            {
                // Verify user credentials
                PreparedStatement pstmt = conn.prepareStatement("SELECT user_id FROM users WHERE username = ? AND password = ?");
                pstmt.setString(1, username);
                pstmt.setString(2, password);
                ResultSet rsUS = pstmt.executeQuery();

                if(rsUS.next())
                {
                    userId = rsUS.getInt("user_id");
                }
                else
                {
                    JOptionPane.showMessageDialog(this, "Invalid username or password");
                    return;
                }
            }
            catch (SQLException ex)
            {
                JOptionPane.showMessageDialog(frame, "Failed to retrieve user data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }

            boolean rent = rentButton.isSelected();
            boolean returnB = returnButton.isSelected();

            if(rent)
            {
                try
                {
                    // Check if the selected book is available
                    Statement stmt = conn.createStatement();
                    String selectedBook = (String) bookList.getSelectedItem();
                    ResultSet rs = stmt.executeQuery("SELECT * FROM books WHERE title = '" + selectedBook + "'");

                    if (rs.next() && rs.getBoolean("available"))
                    {
                        // Book is available, rent it
                        int bookId = rs.getInt("book_id");
                        LocalDate rentalDate = LocalDate.now();

                        PreparedStatement ps = conn.prepareStatement("INSERT INTO rentals (user_id, book_id, rental_date) VALUES (?, ?, ?)");
                        ps.setInt(1, userId);
                        ps.setInt(2, bookId);
                        ps.setDate(3, java.sql.Date.valueOf(rentalDate));
                        ps.executeUpdate();

                        // Update the availability of the book
                        PreparedStatement ps2 = conn.prepareStatement("UPDATE books SET available = false WHERE book_id = ?");
                        ps2.setInt(1, bookId);
                        ps2.executeUpdate();

                        JOptionPane.showMessageDialog(frame, "Book rented successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(frame, "Selected book is not available for rent.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                catch (SQLException ex)
                {
                    JOptionPane.showMessageDialog(frame, "Failed to rent book: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            else if(returnB)
            {
                try
                {
                    // Get the rental ID for the selected book and user
                    Statement stmt = conn.createStatement();
                    String selectedBook = (String) bookList.getSelectedItem();
                    ResultSet rs = stmt.executeQuery("SELECT * FROM rentals WHERE user_id = " + userId + " AND book_id = (SELECT book_id FROM books WHERE title = '" + selectedBook + "') AND returned = false");

                    if (rs.next())
                    {
                        int rentalId = rs.getInt("rental_id");
                        LocalDate returnDate = LocalDate.now();

                        // Update the rental record
                        PreparedStatement ps = conn.prepareStatement("UPDATE rentals SET returned = true, return_date = ? WHERE rental_id = ?");
                        ps.setDate(1, java.sql.Date.valueOf(returnDate));
                        ps.setInt(2, rentalId);
                        ps.executeUpdate();

                        // Update the availability of the book
                        int bookId = rs.getInt("book_id");
                        PreparedStatement ps2 = conn.prepareStatement("UPDATE books SET available = true WHERE book_id = ?");
                        ps2.setInt(1, bookId);
                        ps2.executeUpdate();

                        JOptionPane.showMessageDialog(frame, "Book returned successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(frame, "Selected book is not currently rented by you.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }

                catch (SQLException ex)
                {
                    JOptionPane.showMessageDialog(frame, "Failed to return book: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    public static void main(String[] args)
    {
        new UserGUI();
    }
}