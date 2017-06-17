package Pong;

import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Ball extends Thread {
    private final Pong game;
    
    protected final int DIAMETER = 20;

    protected int x = Pong.WIDTH / 2 - DIAMETER / 2;
    protected int y = Pong.HEIGHT / 2 - DIAMETER /2;

    protected boolean going_up = true;
    protected boolean going_right = false;
    
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
    
    private void move() throws InterruptedException {
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
            if(getBounds().intersects(Pong.panel.p2.getBounds())) { // Bounce when it hits the paddle 2 and update the score
                going_right = false;
                
                Pong.panel.p2.score++;
                Pong.panel.score2.setText(Integer.toString(Pong.panel.p2.score));
            }
            else {
                x++;
            }
            
            if(x + DIAMETER == Pong.WIDTH) { // Gameover if it hits the right border
                game.gameOver();
            }
        }
        else {
            if(getBounds().intersects(Pong.panel.p1.getBounds())) { // Bounce when it hits the paddle 1 and update the score
                going_right = true;
                
                Pong.panel.p1.score++;
                Pong.panel.score1.setText(Integer.toString(Pong.panel.p1.score));
            }
            else {
                x--;
            }
            
            if(x == 0) { // Gameover if it hits the left border
                game.gameOver();
            }
        }
    }
    
    protected Rectangle getBounds() {
        return new Rectangle(x, y, DIAMETER, DIAMETER);
    }
    
    protected void paint(Graphics2D g) {
        g.setColor(Color.DARK_GRAY);
        g.fillOval(x, y, DIAMETER, DIAMETER);
    }
}
