package Pong;

import java.awt.*;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Panel extends JPanel {    
    protected Paddle p1;
    protected Paddle p2;
    protected Ball ball;
    
    protected JLabel score1 = new JLabel("0"), score2 = new JLabel("0");
    
    public Panel(final Pong game) {        
        setFocusable(true);
        
        p1 = new Paddle(10, Pong.HEIGHT / 2 - Paddle.HEIGHT / 2);
        p2 = new Paddle(Pong.WIDTH - 10 - Paddle.WIDTH, Pong.HEIGHT / 2 - Paddle.HEIGHT / 2);
        ball = new Ball(game);
        
        add(score1);
        add(new JLabel("     "));
        add(score2);
        
        // Paddle listener
        addKeyListener(new KeyListener() {            
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_UP) {
                    p2.direction = 'u';
                }
                else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
                    p2.direction = 'd';
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                p2.direction = 's';
            }
        });
        
        // AutoPlay listener
        addKeyListener(new KeyListener() {            
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_SPACE) {
                    game.autoplay = !game.autoplay;
                }
            }
            
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        p1.start();
        p2.start();
        ball.start();
    }
    
    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;

        super.paint(g);
        
        g.setColor(new Color(231, 76, 60));
        p1.paint(g2d);
        
        g.setColor(Color.LIGHT_GRAY);
        g.drawLine(Pong.WIDTH / 2, 0, Pong.WIDTH / 2, Pong.HEIGHT);
        
        g.setColor(new Color(52, 152, 219));
        p2.paint(g2d);
        
        g.setColor(Color.BLACK);
        ball.paint(g2d);
    }
}
