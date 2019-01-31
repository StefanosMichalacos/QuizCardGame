package e.flashcards;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class QuizCardPlayer {

    private JFrame playerFrame, loadFrame;
    private JPanel componentPanel, backPanel, buttonPanel;
    private JButton cardFlipperButton, nextCardButton;
    private JTextArea cardArea;
    private FileInputStream fileStream;
    private ObjectInputStream cardStream;
    private QuizCardBuilder qcg1;
    private File saveFile;
    private ArrayList<QuizCard> cardList;
    private static int cardNumber;
    private boolean question;


    public static void main(String[] args) {
        QuizCardPlayer cardPlayer = new QuizCardPlayer();
    }

    QuizCardPlayer() {
        playerFrame = new JFrame("Quiz Card Builder");
        addMenuBar();
        componentBuilder();
        playerFrame.getContentPane().add(BorderLayout.CENTER, backPanel);
        setWindowPreferences();
    }

    public void addMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenu switchTo = new JMenu("Switch");
        JMenuItem switchButton = new JMenuItem("Card Maker");
        switchButton.addActionListener(new SwitchButtonListener());
        JMenuItem load = new JMenuItem("Load Card Save");
        load.addActionListener(new LoadListener());
        file.add(load);
        switchTo.add(switchButton);
        menuBar.add(file);
        menuBar.add(switchTo);
        playerFrame.setJMenuBar(menuBar);

    }

    public void setWindowPreferences() {
        playerFrame.setSize(500, 700);
        playerFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        playerFrame.setVisible(true);
        playerFrame.pack();
        playerFrame.setLocationRelativeTo(null);

    }

    public void componentBuilder() {
        componentPanel = new JPanel();
        componentPanel.setLayout(new BoxLayout(componentPanel, BoxLayout.Y_AXIS));
        cardArea = new JTextArea(13, 45);
        addScrollPane(cardArea);
        componentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buildButtonPanel();
        componentPanel.add(buttonPanel);
        componentPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        addToBackPanel(componentPanel);
    }

    public void buildButtonPanel() {
        buttonPanel = new JPanel();
        cardFlipperButton = new JButton("Flip Card");
        nextCardButton = new JButton("Next Card");
        nextCardButton.addActionListener(new NextButtonListener());
        cardFlipperButton.addActionListener(new FlipButtonListener());
        buttonPanel.add(cardFlipperButton);
        buttonPanel.add(nextCardButton);


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

    public class LoadListener implements ActionListener {

        public void actionPerformed (ActionEvent event) {
            qcg1 = new QuizCardBuilder();
            loadFrame = new JFrame();
            JFileChooser directory =  new JFileChooser();
            directory.setCurrentDirectory(new File(System.getProperty("user.home")));
            int result = directory.showOpenDialog(loadFrame);
            if (result == JFileChooser.APPROVE_OPTION){
                saveFile = directory.getSelectedFile();
            }
            try {
                fileStream = new FileInputStream(saveFile);             //qcg1.getFileName() + ".ser"
                cardStream = new ObjectInputStream(fileStream);
                Object array = cardStream.readObject();
                cardList = (ArrayList<QuizCard>) array;
                cardStream.close();
            } catch (ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(null, "Oops something went wrong!\n Please make sure that the file is in \n the right directory");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Oops something went wrong!\n Please restart the program and try again.");
            }
            showCards();
        }
    }

    public void showCards() {
        if (question) {
            cardArea.setText(cardList.get(cardNumber).getAnswer());
            question = false;
        }else{
            cardArea.setText(cardList.get(cardNumber).getQuestion());
            question = true;
        }
    }

    class NextButtonListener implements ActionListener {

        public void actionPerformed (ActionEvent event){
            cardNumber++;
            question= false;
            showCards();

        }
    }

    class FlipButtonListener implements ActionListener {

        public void actionPerformed (ActionEvent event){
            showCards();
        }
    }

    class SwitchButtonListener implements ActionListener{

        public void actionPerformed(ActionEvent event){
            playerFrame.setVisible(false);
            QuizCardBuilder cardBuilder = new QuizCardBuilder();
        }
    }

    public JFrame getPlayerFrame (){
        return playerFrame;
    }
}
