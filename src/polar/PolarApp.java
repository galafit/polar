package polar;
import graph.GraphViewer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class PolarApp extends JFrame {
    private final Color BG_COLOR = Color.BLACK;
    private final String title = "Polar";
    private GraphViewer graphViewer;

    public PolarApp() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // super.windowClosing(e);
                close();
            }
        });
        setTitle(title);
        getContentPane().setBackground(BG_COLOR);
        graphViewer = new GraphViewer();
        PolarPresenter.createGraphs(graphViewer);
        add(graphViewer, BorderLayout.CENTER);
        graphViewer.requestFocusInWindow();
        validate();
        setPreferredSize(getWorkspaceDimension());
        pack();
        setVisible(true);
    }

    private Dimension getWorkspaceDimension() {
        // To get the effective screen size (the size of the screen without the taskbar and etc)
        // GraphicsEnvironment has a method which returns the maximum available size,
        // accounting all taskbars etc. no matter where they are aligned
        Rectangle dimension = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        int width = dimension.width;
        int height = dimension.height;
        return new Dimension(width, height);
    }

    private void close() {
        System.exit(0);
    }

    public static void main(String[] args) {
        try {
            String lookAndFeelClassName = UIManager.getCrossPlatformLookAndFeelClassName();
            // устанавливаем LookAndFeel
            UIManager.setLookAndFeel(lookAndFeelClassName);
        } catch (UnsupportedLookAndFeelException e) {
            System.out.println("Can't use the specified look and feel on this platform.");
        } catch (Exception e) {
            System.out.println("Couldn't get specified look and feel, for some reason.");
        }

        new PolarApp();
    }
}
