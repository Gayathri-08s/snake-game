
import javax.swing.JFrame;

public class App {
    public static void main(String[] args) throws Exception {
        int boardWidth = 600;
        int boardHeight = boardWidth;

        // WINDOW DISPLAY
        JFrame frame = new JFrame("Snake");
        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        snakegamee snakegame = new snakegamee(boardWidth, boardHeight);
        frame.add(snakegame);
        frame.pack();// place jpanel within the 4 dimensions

        snakegame.requestFocus();

    }
}
