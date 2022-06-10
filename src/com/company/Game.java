package com.company;

import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;

public class Game extends JPanel implements KeyListener, ActionListener {
    private boolean play = false;
    private int score = 0;

    private int totalBricks = 35;

    private Timer time;
    private int delay = 0;

    private int playerX = 310;

    private int ballposX = 200;
    private int ballposy = 350;
    private int secondballposX = 200;
    private int secondballposy = 350;

    //här ökar man speed!
    private int ballxdir = -1;
    private int ballydir = -2;
    private int secondballxdir = -2;
    private int secondballydir = -2;

    private MapGenerator map;


    public Game() {
        map = new MapGenerator(5, 7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        time = new Timer(delay, this);
        time.start();

    }

    public void paint(Graphics g) {
        //Background
        g.setColor(Color.black);
        g.fillRect(1, 1, 692, 792);

        //drawing map
        map.draw((Graphics2D) g);

        // Border
        g.setColor(Color.black);
        g.fillRect(0, 0, 3, 792);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(0, 0, 3, 792);

        //score
        g.setColor(Color.white);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString("" + score, 590, 30);


        //Paddle
        g.setColor(Color.green);
        g.fillRect(playerX, 750, 100, 8);

        //Ball
        g.setColor(Color.yellow);
        g.fillOval(ballposX, ballposy, 20, 20);
        if (totalBricks <= 0) {
            play = false;
            ballxdir = 0;
            ballydir = 0;
            secondballxdir = 0;
            secondballydir = 0;
            g.setColor(Color.red);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("YOU WON!", 260, 300);

            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString("Press Enter to restart", 230, 350);
        }
        if (ballposy > 770 || secondballposy > 770) {
            play = false;
            ballxdir = 0;
            ballydir = 0;
            secondballxdir = 0;
            secondballydir = 0;
            g.setColor(Color.red);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("game over, scores: " + score, 190, 300);

            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString("Press Enter to restart", 230, 350);


        }

        //second ball
        g.setColor(Color.cyan);
        if (score >= 20) {
            g.fillOval(secondballposX, secondballposy, 20, 20);
        }

        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        time.start();
        if (play) {
            if (new Rectangle(ballposX, ballposy, 20, 20).intersects(new Rectangle(playerX, 750, 100, 8))) {
                ballydir = -ballydir;
            }
            if (new Rectangle(secondballposX, secondballposy, 20, 20).intersects(new Rectangle(playerX, 750, 100, 8))) {
                secondballydir = -secondballydir;
            }
            A:
            for (int i = 0; i < map.map.length; i++) {
                for (int j = 0; j < map.map[0].length; j++) {
                    if (map.map[i][j] > 0) {
                        int brickX = j * map.brichWidth + 80;
                        int brickY = i * map.brickHeight + 50;
                        int brickWidth = map.brichWidth;
                        int brickHeight = map.brickHeight;

                        Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        Rectangle ballRect = new Rectangle(ballposX, ballposy, 20, 20);
                        Rectangle secondballRect = new Rectangle(secondballposX, secondballposy, 20, 20);

                        if (ballRect.intersects(rect)) {
                            map.setBrickValue(0, i, j);
                            totalBricks--;
                            score += 5;

                            if (ballposX + 19 <= rect.x || ballposX >= rect.x + rect.width) {
                                ballxdir = -ballxdir;
                            } else {
                                ballydir = -ballydir;
                            }
                            break A;
                        }
                        if (secondballRect.intersects(rect)) {
                            map.setBrickValue(0, i, j);
                            totalBricks--;
                            score += 5;

                            if (secondballposX + 19 <= rect.x || secondballposX >= rect.x + rect.width) {
                                secondballxdir = -secondballxdir;
                            } else {
                                secondballydir = -secondballydir;
                            }
                            break A;
                        }
                    }
                }
            }

            ballposX += ballxdir;
            ballposy += ballydir;

            if (ballposX < 0) {
                ballxdir = -ballxdir;
            }
            if (ballposy < 0) {
                ballydir = -ballydir;
            }
            if (ballposX > 670) {
                ballxdir = -ballxdir;
            } else if (score >= 20) {
                secondballposX += secondballxdir;
                secondballposy += secondballydir;

                if (secondballposX < 0) {
                    secondballxdir = -secondballxdir;
                }
                if (secondballposy < 0) {
                    secondballydir = -secondballydir;
                }
                if (secondballposX > 670) {
                    secondballxdir = -secondballxdir;
                }
            }

        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }


    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (playerX >= 600) {
                playerX = 600;
            } else {

                moveRight();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (playerX < 10) {
                playerX = 10;
            } else {
                moveLeft();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!play) {
                play = true;
                ballposX = 200;
                ballposy = 350;
                secondballposX = 250;
                secondballposy = 320;
                ballxdir = -1;
                ballydir = -2;
                secondballxdir = -2;
                secondballydir = -3;
                playerX = 310;
                score = 0;
                totalBricks = 35;
                map = new MapGenerator(5, 7);

                repaint();
            }
        }
    }

    public void moveRight() {
        play = true;
        playerX += 20;
    }

    public void moveLeft() {
        play = true;
        playerX -= 20;
    }

}


