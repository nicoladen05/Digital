/*
 * Copyright (c) 2016 Helmut Neemann
 * Use of this source code is governed by the GPL v3 license
 * that can be found in the LICENSE file.
 */
package de.neemann.digital;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.util.SystemInfo;
import de.neemann.digital.draw.graphics.ColorScheme;
import de.neemann.digital.gui.MainGui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

/**
 * Launch Wrapper to initialize system properties for proper macOS integration
 * macOS requires the apple.awt.application.name to be set prior to any swing component initialization
 */
public final class Main {
    private Main() {

    }
    /**
     * Main Method
     * @param args the arguments
     * @throws IOException IOException
     * @throws UnsupportedLookAndFeelException UnsupportedLookAndFeelException
     * @throws ClassNotFoundException ClassNotFoundException
     * @throws InstantiationException InstantiationException
     * @throws IllegalAccessException IllegalAccessException
     */
    public static void main(String[] args) throws IOException, UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        ColorScheme scheme = ColorScheme.getSelected();
        if (SystemInfo.isMacOS) {
            System.setProperty("apple.awt.application.name", "Digital");
            System.setProperty("apple.awt.application.appearance", scheme.getAquaTheme());
            System.setProperty("apple.laf.useScreenMenuBar", "true");
        }
        JFrame frame = new JFrame();
        BufferedImage image;
        if (Objects.requireNonNull(scheme.getType()) == ColorScheme.Type.DARK) {
            image = ImageIO.read(ClassLoader.getSystemResource("icons/splash_dark.png"));
        } else {
            image = ImageIO.read(ClassLoader.getSystemResource("icons/splash.png"));
        }
        JPanel pane = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(image, 0, 0, null);
            }
        };
        UIManager.setLookAndFeel(scheme.getTheme());
        FlatLaf.updateUI();
        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        frame.add(pane);
        frame.add(progressBar, BorderLayout.SOUTH);
        frame.setUndecorated(true);
        frame.setSize(image.getWidth(), image.getHeight() + 4);
        frame.setLocationRelativeTo(null);
        frame.setUndecorated(true);
        frame.setVisible(true);
        MainGui.main(frame, args);
    }
}
