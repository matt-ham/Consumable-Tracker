package ca.cmpt213.a4.client;


import ca.cmpt213.a4.client.view.GUI;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(GUI::new);
    }
}
