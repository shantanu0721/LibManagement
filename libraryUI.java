import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LibraryUI extends JFrame {
    private Library library;

    public LibraryUI() {
        library = new Library();
        initUI();
    }

    private void initUI() {
        setTitle("Library Management System");
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(5, 1));

        // Fields for Book Details
        JTextField titleField = new JTextField();
        JTextField authorField = new JTextField();
        JTextField genreField = new JTextField();
        JButton addBookButton = new JButton("Add Book");

        // Fields for User Details
        JTextField userNameField = new JTextField();
        JButton addUserButton = new JButton("Add User");

        // Fields for Search and Issue
        JTextField searchTitleField = new JTextField();
        JTextField issueToUserField = new JTextField();
        JButton searchButton = new JButton("Search Book");
        JButton issueBookButton = new JButton("Issue Book");

        // Adding components to panel
        panel.add(new JLabel("Book Title:"));
        panel.add(titleField);
        panel.add(new JLabel("Author:"));
        panel.add(authorField);
        panel.add(new JLabel("Genre:"));
        panel.add(genreField);
        panel.add(addBookButton);

        panel.add(new JLabel("User Name:"));
        panel.add(userNameField);
        panel.add(addUserButton);

        panel.add(new JLabel("Search Title:"));
        panel.add(searchTitleField);
        panel.add(searchButton);

        panel.add(new JLabel("Issue To (User Name):"));
        panel.add(issueToUserField);
        panel.add(issueBookButton);

        add(panel);

        // Action Listener for Adding Book
        addBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = titleField.getText().trim();
                String author = authorField.getText().trim();
                String genre = genreField.getText().trim();

                if (title.isEmpty() || author.isEmpty() || genre.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "All book fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                library.addBook(new Book(title, author, genre));
                JOptionPane.showMessageDialog(null, "Book added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                // Clear the fields
                titleField.setText("");
                authorField.setText("");
                genreField.setText("");
            }
        });

        // Action Listener for Adding User
        addUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userName = userNameField.getText().trim();

                if (userName.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "User name cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                library.addUser(new User(userName));
                JOptionPane.showMessageDialog(null, "User added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                // Clear the field
                userNameField.setText("");
            }
        });

        // Action Listener for Searching Book
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = searchTitleField.getText().trim();

                if (title.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Book title cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Book book = library.searchBook(title);
                if (book == null) {
                    JOptionPane.showMessageDialog(null, "Book not found!", "Info", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    String availability = book.isAvailable() ? "Available" : "Not Available";
                    JOptionPane.showMessageDialog(null, "Book Found:\nTitle: " + book.getTitle() + "\nAuthor: " + book.getAuthor() + "\nGenre: " + book.getGenre() + "\nStatus: " + availability, "Book Details", JOptionPane.INFORMATION_MESSAGE);
                }

                // Clear the field
                searchTitleField.setText("");
            }
        });

        // Action Listener for Issuing Book
        issueBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = searchTitleField.getText().trim();
                String userName = issueToUserField.getText().trim();

                if (title.isEmpty() || userName.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Both title and user name are required!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Book book = library.searchBook(title);
                User user = library.getUsers().stream().filter(u -> u.getName().equalsIgnoreCase(userName)).findFirst().orElse(null);

                if (book == null) {
                    JOptionPane.showMessageDialog(null, "Book not found!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (user == null) {
                    JOptionPane.showMessageDialog(null, "User not found!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!book.isAvailable()) {
                    JOptionPane.showMessageDialog(null, "Book is already issued to someone else!", "Info", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                library.issueBook(title, user);
                JOptionPane.showMessageDialog(null, "Book issued successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                // Clear the fields
                searchTitleField.setText("");
                issueToUserField.setText("");
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LibraryUI app = new LibraryUI();
            app.setVisible(true);
        });
    }
}
