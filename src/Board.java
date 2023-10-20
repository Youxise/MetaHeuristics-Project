import java.awt.*;

public class Board extends Canvas {
    public static int size;
    public static int[] queens;

    public void paint(Graphics g) {
        Toolkit t = Toolkit.getDefaultToolkit();
        //Image image = t.getImage("C:\\m1\\s2\\queen1.png");
        Image image = t.getImage("C:\\Users\\TRETEC\\Desktop\\TPs\\MetaH\\N Queens P2\\src\\queen1.png");

        int squareSize = (int) (600 / size);
        setBackground(Color.WHITE);
        setForeground(Color.BLACK);
        for (int i = 0; i < size; i++) {

            int c = 1;
            if (i % 2 == 0) {
                c = -1;
            }
            for (int j = 0; j < size; j++) {

                if (c == 1) {
                    g.fillRect(squareSize * j, squareSize * i, squareSize, squareSize);
                }
                c *= -1;
            }

            g.drawImage(image, squareSize * queens[i], squareSize * i,squareSize,squareSize, this);

        }
    }

}  