package game;
import javax.swing.JFrame;

public class GameFrame extends JFrame{
    //Constructor class
    GameFrame(){
        // GamePanel panel = new GamePanel();
        // this.add(panel);

        this.add(new GamePanel());
        this.setName("Snake"); //title
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false); 
        this.pack(); //when we add components to our jframe this will fit it all well
        this.setVisible(true);
        this.setLocationRelativeTo(null); //appears in the middle of computer

    }

  
}

