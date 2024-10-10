import javax.swing.*;
public class App {
    public static void main(String[] args) throws Exception {
        int boarderwidth= 360;
        int boarderheight= 640;

        JFrame frame= new JFrame("Flappy Drone");
        frame.setVisible(true); 
        frame.setSize(boarderwidth,boarderheight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        FlappyDrone flappyDrone= new FlappyDrone();
        frame.add(flappyDrone);
        frame.pack();
        flappyBird.requestFocus(); 
        frame.setVisible(true);
        
    }
}
