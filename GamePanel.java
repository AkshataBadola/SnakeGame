package game;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH = 800;
    static final int SCREEN_HEIGHT = 800;
    static final int UNIT_SIZE = 25;     //matrix foR the size of objects
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE; //no of objects we can fit in the scren
    static final int DELAY = 75;

    //these arrays will hold all the body parts of the snake including the head of the snake 
    final int x[] = new int[GAME_UNITS]; //x codinates setting to game units because the snake cant we bigger than the game this holds head 
    final int y[] = new int[GAME_UNITS]; //y coodrinates this holds body
    int bodyParts = 6;
    int applesEaten;
    int appleX; //where apple is located on x coordinate
    int appleY;

    char direction = 'R'; //snake starts on right
    boolean running = false;
    Timer timer;
    Random random; //instance of the random class

    //Constructor
    GamePanel() {
        random = new Random(); //INSTANCE OF RANDOM CLASS
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.lightGray);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        StartGame();

    }

    public void StartGame() {
        newApple(); //to create a new apple on the screen for us
        running = true; //since its false to begin with
        timer = new Timer(DELAY, this); //instace of our timer
        timer.start();

    }

    @Override
    protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    draw(g);
}


    public void draw(Graphics g) {

        if (running) {
            //the for loop make a grid and is optional
            for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
            }
            //apple
            g.setColor(Color.green);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            //head 
            for (int i = 0; i < bodyParts; i++) {
                if (i == 0)//dealing with the head of our snake
                {
                    g.setColor(Color.red);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    g.setColor(Color.blue);
                    //g.setColor(new Color(random.next(255), random.next(255), random.next(255)))
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);

                }
            }

            //Score
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free", Font.BOLD, 25));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: "+applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());
    
        }
        else{
            GameOver(g);
        }
    }

    //populating the game with an apple to score
    //it generates the coordinates of the new apple whenever this method is called like begin the game or score the point
    public void newApple() {
        appleX = random.nextInt((int) (SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;  //X COORDINATE
        appleY = random.nextInt((int) (SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;  //Y COORDINATE

    }

    public void move() {
        //moving body of the snake for loop to iterate thru all the body parts of snake
        for (int i = bodyParts; i > 0; i--) {
            //shifting the body of snake around
            x[i] = x[i - 1]; //shifting coordinates by 1 spot
            y[i] = y[i - 1];

        }
        //creating a switch to change the direction of where our name is headed
        switch (direction) {
            case 'U':
                y[0] = y[0] - UNIT_SIZE; //going to go to next position
                break;

            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;

            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;

            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }

    }

    public void CheckApple() {
        if ((x[0] == appleX && x[0] == appleX)) {
            bodyParts++;
            applesEaten++;
            newApple();

        }

    }

    public void CheckCollisions() {
        //checks if head collides with body
        for (int i = bodyParts; i > 0; i--) {
            if ((x[0] == x[i] && (y[0] == y[i]))) {
                running = false;
            }
        }

        //checks if head touched left border
        if (x[0] < 0) {
            running = false;
        }

        //checks if head touched right border
        if (x[0] > SCREEN_WIDTH) {
            running = false;
        }

        //checks if head touched top border
        if (y[0] < 0) {
            running = false;
        }

        //checks if head touched bottom border
        if (y[0] > SCREEN_HEIGHT) {
            running = false;
        }

        if (!running) {
            timer.stop();
        }

    }

    public void GameOver(Graphics g) {
        //Game Over Text
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH - metrics.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);

        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 25));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score: "+applesEaten, (SCREEN_WIDTH - metrics1.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());

    }

    //override Function
    @Override
    public void actionPerformed(ActionEvent e) {

        if (running) {
            move();
            CheckApple();
            CheckCollisions();
        }
        repaint();
    }

    //inner class
    public class MyKeyAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {

                case KeyEvent.VK_LEFT:
                    if (direction != 'R') {
                        direction = 'L';
                    }
                    break;

                case KeyEvent.VK_RIGHT:
                    if (direction != 'L') {
                        direction = 'R';
                    }
                    break;

                case KeyEvent.VK_UP:
                    if (direction != 'D') {
                        direction = 'U';
                    }
                    break;

                case KeyEvent.VK_DOWN:
                    if (direction != 'U') {
                        direction = 'D';
                    }
                    break;

            }
        }
    }
}
