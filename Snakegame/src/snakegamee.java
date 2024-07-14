import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class snakegamee extends JPanel implements ActionListener,KeyListener{
    private class Tile{
        int x;
        int y;
        
        Tile(int x,int y){
            this.x=x;
            this.y=y;
        }

    
    }
    int boardWidth;
    int boardHeight;
    int tileSize=25;//each box the snake traverse
    //each box is 25px..to move 4 boxes(4*25) this is how ot works
    //topleft is 0x0 and bottomright is 600x600

    //snake
    Tile snakeHead;
    ArrayList<Tile> snakeBody;
    
    //food
    Tile food;
    Random random;
    
    //Gmelogic
    Timer gameLoop;
    int velocityx,velocityy;
    boolean gameOver = false;

    snakegamee(int boardWidth, int boardHeight){
        this.boardWidth=boardWidth;
        this.boardHeight=boardHeight;
        setPreferredSize(new Dimension(this.boardWidth,this.boardHeight));
        setBackground(Color.black);
        addKeyListener(this);
        setFocusable(true);
        
        snakeHead = new Tile(5,5);
        snakeBody=new ArrayList<Tile>();
        food= new Tile(10,10);
        random=new Random();
        placeFood();

        velocityx=1;//snake goes tight-horizontal path if 1//left if -1
        velocityy=0;//it goes vertical-downwards if velocityy is 1//up if -1//if both 0 no movement!


        gameLoop= new Timer(100, this);
        gameLoop.start();

    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
         /*for(int i=0;i< (boardWidth/tileSize);i++){
            //gridlines
            g.drawLine(i*tileSize, 0, i*tileSize, boardHeight);//vertical line
            g.drawLine(0, i*tileSize, boardWidth, i*tileSize);//horizontalline
        } */

        //food
        g.setColor(Color.red);
        //if no 3d effect is needed take the comment symbol off from the nextline and comment the 3d effect line
        //g.fillRect(food.x*tileSize, food.y*tileSize, tileSize, tileSize );
        g.fill3DRect(food.x*tileSize, food.y*tileSize, tileSize, tileSize,true );
        //snake
        
        g.setColor(Color.green);
        //if no 3d effect is needed take the comment symbol off from the nextline and comment the 3d effect line
        //g.fillRect(snakeHead.x*tileSize, snakeHead.y*tileSize, tileSize, tileSize );//tilesize for width and height....multiplied to move 5 x and s y units thats each tile size multiplied by 5
        g.fill3DRect(snakeHead.x*tileSize, snakeHead.y*tileSize, tileSize, tileSize,true );
        

        //snakebody each body part (draw)//iterationg thru thr array list
        for(int i=0;i<snakeBody.size();i++){
            Tile snakePart = snakeBody.get(i);

            //g.fillRect(snakePart.x*tileSize,snakePart.y*tileSize,tileSize,tileSize);
            g.fill3DRect(snakePart.x*tileSize,snakePart.y*tileSize,tileSize,tileSize,true);
        }

        //Score
        g.setFont(new Font("Arial",Font.PLAIN,16));
        if(gameOver){
            g.setColor(Color.green);
            g.drawString("GAME OVER! & SCORE:" + String.valueOf(snakeBody.size()),tileSize - 16,tileSize); 
        }
        else{
            g.drawString("Score: " + String.valueOf(snakeBody.size()),tileSize - 16,tileSize); 
        }

    }
    public void placeFood(){
      food.x=random.nextInt(boardWidth/tileSize);//600/25=24
      food.y=random.nextInt(boardHeight/tileSize);  

    }

   
    public void move(){
        //eat food
        if(collision(snakeHead,food)){
            snakeBody.add(new Tile(food.x, food.y));
            placeFood();
        }

        //tile move along with the snakehead
        for(int i=snakeBody.size()-1;i>=0;i--){
            Tile snakePart=snakeBody.get(i);
            if(i==0){
                snakePart.x=snakeHead.x;
                snakePart.y=snakeHead.y;
            }
            else{
                Tile prevSnakePart =snakeBody.get(i-1);
                snakePart.x=prevSnakePart.x;
                snakePart.y=prevSnakePart.y;
            }
        }
         

        //snakehead
        snakeHead.x+=velocityx;
        snakeHead.y+=velocityy;

        //game over conditions
        for(int i=0;i<snakeBody.size();i++){
            Tile snakePart=snakeBody.get(i);
            ////collide with snake body with itself
            if(collision(snakeHead,snakePart)){
                gameOver=true;
            }

        }

        if(snakeHead.x * tileSize <  0 || snakeHead.x * tileSize>boardWidth || snakeHead.y * tileSize<0 || snakeHead.y * tileSize>boardHeight ){
        gameOver=true;
        }
    }

    public boolean collision(Tile tile1,Tile tile2){
        return tile1.x == tile2.x && tile1.y == tile2.y;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       move();
       repaint();
       if(gameOver){
        gameLoop.stop();
        }

        
    }


    @Override //created after implementing keylistener!
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode()== KeyEvent.VK_UP && velocityy!=1){
            velocityx=0;
            velocityy=-1;
        }
        else if(e.getKeyCode() == KeyEvent.VK_DOWN && velocityy!=-1){
            velocityx=0;
            velocityy=1;
        }
        else if(e.getKeyCode() == KeyEvent.VK_LEFT && velocityx!=1){
            velocityx=-1;
            velocityy=0;
        }
        else if(e.getKeyCode() == KeyEvent.VK_RIGHT && velocityx!=-1){
            velocityx=1;
            velocityy=0;
        }

    }

      //content below is not needed so leave the function truncated  
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}
