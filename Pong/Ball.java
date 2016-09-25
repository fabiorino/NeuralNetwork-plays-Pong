package Pong;

import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Ball extends Thread {
    Pong game;
    
    final int DIAMETER = 20;

    int x = Pong.WIDTH / 2 - DIAMETER / 2;
    int y = Pong.HEIGHT / 2 - DIAMETER /2;

    boolean going_up = true;
    boolean going_right = false;
    
    public Ball(Pong game) {
        this.game = game;
    }
    
    @Override
    public void run() {
        while(true) {
            try {
                // Move (y, an input, will be updated), then learn
                move();
                game.learn();
            }
            catch(InterruptedException ex) {
                Logger.getLogger(Ball.class.getName()).log(Level.SEVERE, null, ex);
            }
            Pong.panel.repaint();
            try {
                sleep(3);
            }
            catch(InterruptedException ex) {}
        }
    }
    
    public void move() throws InterruptedException {
        if(going_up) {
            if(y == 0) { // Bounce when it hits the top
                going_up = false;
            }
            else {
                y--;
            }
        }
        else {
            if(y + DIAMETER == Pong.HEIGHT) { // Bounce when it hits the bottom
                going_up = true;
            }
            else {
                y++;
            }
        }
        
        if(going_right) {
            if(getBounds().intersects(game.panel.p2.getBounds())) { // Bounce when it hits the paddle 2 and update the score
                going_right = false;
                
                game.panel.p2.score++;
                game.panel.score2.setText(Integer.toString(game.panel.p2.score));
            }
            else {
                x++;
            }
            
            if(x + DIAMETER == Pong.WIDTH) { // Gameover if it hits the right border
                game.gameOver();
            }
        }
        else {
            if(getBounds().intersects(game.panel.p1.getBounds())) { // Bounce when it hits the paddle 1 and update the score
                going_right = true;
                
                game.panel.p1.score++;
                game.panel.score1.setText(Integer.toString(game.panel.p1.score));
            }
            else {
                x--;
            }
            
            if(x == 0) { // Gameover if it hits the left border
                game.gameOver();
            }
        }
    }
    
    public Rectangle getBounds() {
        return new Rectangle(x, y, DIAMETER, DIAMETER);
    }
    
    public void paint(Graphics2D g) {
        g.setColor(Color.DARK_GRAY);
        g.fillOval(x, y, DIAMETER, DIAMETER);
    }
}
