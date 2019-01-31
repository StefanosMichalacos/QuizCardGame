package e.flashcards;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;


public class QuizCardBuilder {
    private JFrame cardBuilder;
    private JPanel componentPanel;
    private JPanel backPanel;
    private JButton nextCardButton;
    private JLabel questionLabel;
    private JLabel answerLabel;
    private JTextArea questionInput;
    private JTextArea answerInput;
    private QuizCard qc;
    private ArrayList<QuizCard> cardsList = new ArrayList<>();
    private static int counter;
    private FileOutputStream fileStream;
    private ObjectOutputStream cardStream;
    private QuizCardPlayer cardPlayer;
    private static String name;

    QuizCardBuilder() {
        cardBuilder = new JFrame("Quiz Card Builder");
        addMenuBar();
        componentBuilder();
        cardBuilder.getContentPane().add(BorderLayout.CENTER, backPanel);
        setWindowPreferences();
    }

    public void addMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenuItem save = new JMenuItem("Save and Exit");
        JMenu switchTo = new JMenu("Switch");
        JMenuItem switchButton = new JMenuItem("Card Maker");
        switchButton.addActionListener(new SwitchButtonListener());
        save.addActionListener(new SaveListener());
        switchTo.add(switchButton);
        file.add(save);
        menuBar.add(file);
        menuBar.add(switchTo);
        cardBuilder.setJMenuBar(menuBar);

    }

    public void setWindowPreferences() {
        cardBuilder.setSize(500, 700);
        cardBuilder.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        cardBuilder.setVisible(true);
        cardBuilder.pack();
        cardBuilder.setLocationRelativeTo(null);

    }


    public void componentBuilder() {
        componentPanel = new JPanel();
        componentPanel.setLayout(new BoxLayout(componentPanel, BoxLayout.Y_AXIS));
        questionLabel = new JLabel("Question :");
        answerLabel = new JLabel("Answer :");
        questionInput = new JTextArea(13, 45);
        answerInput = new JTextArea(13, 45);
        nextCardButton = new JButton("Next Card");
        addLabelToPanel(questionLabel);
        addScrollPane(questionInput);
        addLabelToPanel(answerLabel);
        addScrollPane(answerInput);
        componentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        nextCardButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        nextCardButton.addActionListener(new ButtonListener());
        componentPanel.add(nextCardButton);
        componentPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        addToBackPanel(componentPanel);
    }

    public void addScrollPane(JTextArea text) {
        JScrollPane scrollBar = new JScrollPane(text);
        text.setLineWrap(true);
        scrollBar.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollBar.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        componentPanel.add(scrollBar);
    }

    public void addToBackPanel(JPanel panel) {
        backPanel = new JPanel();
        backPanel.add(panel);
    }

    public void addLabelToPanel(JLabel label) {
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        componentPanel.add(label);
    }

    public void createQuizCard() {
        qc = new QuizCard();
        qc.setQuestion(questionInput.getText());
        qc.setAnswer(answerInput.getText());
        cardsList.add(counter, qc);
        counter++;
    }

    public String setFileName() {
        name = JOptionPane.showInputDialog("Please type the name of the file");
        if (name.equals("")) {
            name = "card.save";
            return name;
        }
        return name;
    }

    public class SaveListener implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            try {
                fileStream = new FileOutputStream(setFileName() + ".ser", false);
                cardStream = new ObjectOutputStream(fileStream);
                cardStream.writeObject(cardsList);
                cardStream.close();
                cardBuilder.setVisible(false);
                cardPlayer = new QuizCardPlayer();
                cardPlayer.getPlayerFrame().setVisible(true);


            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(null, "Oops something went wrong!\n Please make sure that the file is in \n the right directory");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Oops something went wrong!\n Please restart the program and try again.");
            }

        }
    }

    public class ButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            createQuizCard();
            questionInput.setText("");
            answerInput.setText("");
            questionInput.requestFocus();
        }

    }

    class SwitchButtonListener implements ActionListener{

        public void actionPerformed(ActionEvent event){
            cardBuilder.setVisible(false);
            cardPlayer = new QuizCardPlayer();
            cardPlayer.getPlayerFrame().setVisible(true);
        }
    }
}
