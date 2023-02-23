import org.mariuszgromada.math.mxparser.License;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.util.ConcurrentModificationException;

public class Main implements Runnable, MouseWheelListener, MouseListener, MouseMotionListener, KeyListener, ActionListener {

    public final String fullName = "Bryan Sukidi";

    public JFrame frame;
    public JPanel panel;
    public Canvas canvas;
    public BufferStrategy bufferStrategy;

    public JPanel menuBarPanel;
    public MenuBar menuBar;

    public int screenWidth = 1200;
    public int screenHeight = 700;

    public int menuPanelWidth = 300;
    public int graphPanelWidth = screenWidth - menuPanelWidth;

    public Graph graph = new Graph(graphPanelWidth, screenHeight);


    public static void main(String[] args){
        Main ex = new Main();
        new Thread(ex).start();
    }

    public Main(){

        setUpGraphics();
        License.iConfirmNonCommercialUse(fullName);

    }


    @Override
    public void run() {

        while (true){
            try {
                Thread.sleep(1000 / 60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            render();
        }

    }

    private void setUpGraphics(){
        frame = new JFrame("Advanced Functions Graphing Calculator");

        panel = (JPanel) frame.getContentPane();
        panel.setPreferredSize(new Dimension(screenWidth, screenHeight));
        panel.setLayout(null);

        menuBarPanel = new JPanel();
        menuBar = new MenuBar(graph, menuBarPanel, menuPanelWidth, screenHeight);
        panel.add(menuBar.menuPanel);

        canvas = new Canvas();
        canvas.setBounds(menuPanelWidth, 0, graphPanelWidth, screenHeight);
        canvas.setIgnoreRepaint(true);
        canvas.addMouseWheelListener(this);
        canvas.addMouseMotionListener(this);
        canvas.addMouseListener(this);
        canvas.addKeyListener(this);

        panel.add(canvas);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setResizable(true);
        frame.setVisible(true);

        canvas.createBufferStrategy(2);
        canvas.requestFocus();
        bufferStrategy = canvas.getBufferStrategy();

        canvas.requestFocus();
        System.out.println("Graphics Setup Completed");

    }

    public void render(){
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        g.clearRect(0, 0, screenWidth, screenHeight);

        try {
            // Draws the Grid (don't change)
            graph.drawGrid(g, graphPanelWidth, screenHeight, new Color(0, 0, 0, 25));

            // Draws the Axes (don't change)
            graph.drawAxes(g, graphPanelWidth, screenHeight, Color.black);

            graph.drawLines(g);
            graph.drawPoints(g, Color.black);

        } catch (ConcurrentModificationException e){
            System.out.println("ConcurrentModificationException");
        }

        g.dispose();
        bufferStrategy.show();
    }

    public void pause(int time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
//        int notches = e.getWheelRotation();
//        if (notches < 0) {
//            System.out.println("Mouse wheel moved UP " + -notches + " notch(es)");
//            graph.graphXPixelsPerUnit -= 4;
//            graph.graphYPixelsPerUnit -= 2;
//
//        } else {
//            System.out.println("Mouse wheel moved DOWN " + notches + " notch(es)");
//            graph.graphXPixelsPerUnit += 4;
//            graph.graphYPixelsPerUnit += 2;
//        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

        if (e.isMetaDown()){
            int mouseX = e.getX();
            int mouseY = e.getY();
            double x = graph.convertXCoordinateToValue(mouseX);
            double y = graph.convertYCoordinateToValue(mouseY);

            x = Math.round(x * 1000.0) / 1000.0;
            y = Math.round(y * 1000.0) / 1000.0;

            graph.addPoint(new Point(x, y));

            System.out.println("x: " + x + " y: " + y);
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }


}
