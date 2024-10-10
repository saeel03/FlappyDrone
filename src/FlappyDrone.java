import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;


public class FlappyDrone extends JPanel implements ActionListener, KeyListener{

    int boarderwidth= 360;
    int boarderheight= 640;

    //Images
    Image backgroundImage;
    Image birdImage;
    Image topPipImage;
    Image bottomPipImage;

    //bird
    int birdX= boarderwidth/8;
    int birdY= boarderheight/2;
    int birdwidth= 64;
    int birdheight= 54;

    class Bird{
        int x= birdX;
        int y= birdY;
        int width= birdwidth;
        int height= birdheight;
        Image img;

        Bird(Image img){
            this.img= img;
        }


    }
    //pipes
    int pipeX= boarderwidth;
    int pipeY= 0;
    int pipeWidth= 64;
    int pipeHeight = 512;
    class Pipe{
        int x=pipeX;
        int y= pipeY;
        int width = pipeWidth;
        int height= pipeHeight;
        Image img;
        boolean passed= false;

        Pipe(Image img){
            this.img = img;
        }

    }

//game logic
    Bird bird;
    int velocityX=-4; //move pipes to the left
    int velocityY=0;
    int gravity=1;

    ArrayList<Pipe>pipes;
    Random random= new Random();


    Timer gameLoop;
    Timer placePipesTimer;

    boolean gameOver = false; 
    double score= 0;


    FlappyDrone(){

        setPreferredSize( new Dimension(boarderwidth,boarderheight));
        //setBackground(Color.BLUE);
        setFocusable(true);
        addKeyListener(this);

        //load images

        backgroundImage = new ImageIcon(getClass().getResource("./flappybirdbg.png")).getImage();
        birdImage = new ImageIcon(getClass().getResource("./drone.png")).getImage();
       topPipImage= new ImageIcon(getClass().getResource("./toppipe.png")).getImage();
        bottomPipImage = new ImageIcon(getClass().getResource("./bottompipe.png")).getImage();
        bird = new Bird(birdImage);
        pipes= new ArrayList<Pipe>();

        //place pipe timer
        placePipesTimer = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                    placePipes();
            }
            
        });
        placePipesTimer.start();

        gameLoop= new Timer(1000/60,this);
        gameLoop.start();

        
    }
    public void placePipes(){
        int randomPipeY= (int)(pipeY - pipeHeight/4 - Math.random()*(pipeHeight/2));
        int openingSpace = boarderheight/4;


        Pipe topPipe = new Pipe(topPipImage);
        topPipe.y= randomPipeY;
        pipes.add(topPipe);

        Pipe bottomPipe= new Pipe(bottomPipImage);
        bottomPipe.y = topPipe.y + pipeHeight + openingSpace;
        pipes.add(bottomPipe);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
        // System.out.println("draw");
        //background
        g.drawImage(backgroundImage,0,0,boarderwidth,boarderheight,null);

        //bird
        g.drawImage(bird.img,bird.x,bird.y,bird.width,bird.height,null);

        //pipes
        for(int i=0; i< pipes.size(); i++)  {
            Pipe pipe = pipes.get(i);
            g.drawImage(pipe.img, pipe.x, pipe.y, pipe.width, pipe.height, null);
        }

        //score
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.PLAIN,32));
        if(gameOver){
            g.drawString("Game Over: " + String.valueOf((int) score),10,35);
            
        }
        else{
            g.drawString(String.valueOf((int) score),10,35);
        }
    }

    public void move(){
        //bird moving
        velocityY += gravity;
        bird.y +=velocityY;
        bird.y = Math.max(bird.y,0);

        //pipes moving
        for(int i=0; i< pipes.size(); i++)  {
            Pipe pipe = pipes.get(i);
           pipe.x += velocityX;
           if(!pipe.passed && bird.x > pipe.x + pipe.width){
            pipe.passed=true;
            score += 0.5;
           }

           if(collision(bird, pipe)){
            gameOver= true;

           }
        }
        if(bird.y > boarderheight)
        {
            gameOver= true;
        }
    }

    public boolean collision(Bird a, Pipe b){
        return a.x< b.x + b.width && a.x + a.width> b.x && a.y < b.y + b.height && a.y + a.height > b.y; 
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        move();
      repaint();
      if(gameOver){
        placePipesTimer.stop();
        gameLoop.stop();
      }
    }

    //keylisteners
   
    @Override
    public void keyPressed(KeyEvent e) {
       if (e.getKeyCode()== KeyEvent.VK_SPACE){
        velocityY=-9;
       } 
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
       
    }

    
}
