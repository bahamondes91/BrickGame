package com.company;

import javax.swing.*;


public class Main {

    public static void main(String[] args) {
        JFrame obj = new JFrame();

        Game game = new Game();
        obj.setBounds(10, 10, 700, 800);
        obj.setTitle("Ballbreaker");
        obj.setResizable(false);
        obj.setVisible(true);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        obj.add(game);

    }


}
