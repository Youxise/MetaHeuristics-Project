import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class App extends JPanel {
    public static App app = new App();
    public static int size;
    public static int algo;
    private JButton launchButton;
    private JPanel mainPanel;
    private JTextField sizeField;
    private JComboBox algoSelectionField;

    private JLabel timeText;
    private JPanel boardContainer;

    public App() {
        launchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                size = Integer.parseInt(sizeField.getText());
                // algo values => 0:DFS 1:BFS 2:A*(h1) 3:A*(h2)
                algo = algoSelectionField.getSelectedIndex();
                drawBoard();
            }
        });
    }

    public static void main(String[] args) {

        JFrame frame = new JFrame("App");

        frame.add(app.mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();

        frame.setVisible(true);
    }

    public void drawBoard() {
        Node result;
        Board b = new Board();
        Board.size = size;

        long startTime = System.currentTimeMillis();
        switch (algo) {
            case 0:
                GA.setN(10000);
                GA.setM(101);
                GA.setMaxGen(15);
                GA.setRemplacement(true);
                GA.setCroisement(false);
                GA.setSelection(true);
                GA.setCroisementProba(0.9);
                GA.setMutationProba(0.9);
                result = GA.run(size);
                break;
            case 1:
                PSO.setC1(0.8);
                PSO.setC2(0.8);
                PSO.setW(0.5);
                PSO.setN(500);
                PSO.setMaxIterations(500);
                result = PSO.run(size);
                break;
            default:
                result = GA.run(size);
                break;
        }
        long endTime = System.currentTimeMillis();
        Date totalTime = new Date(endTime - startTime);
        DateFormat formatter = new SimpleDateFormat("HH:mm:ss.SSSS");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        String dateFormatted = formatter.format(totalTime);


        Board.queens = result.getQueens();
        timeText.setText(dateFormatted);
        b.setSize(600, 600);
        app.boardContainer.removeAll();
        app.boardContainer.add(b);
    }
}
