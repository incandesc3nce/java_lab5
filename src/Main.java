import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.*;

public class Main {

    private static class pictureBox extends JPanel {
        public BufferedImage image1;
        public BufferedImage image2;
        private Rectangle selection;

        public pictureBox() {
            try {
                image1 = ImageIO.read(new File("/Users/coldmilka/Desktop/scp.jpg"));

            } catch (IOException e) {

            }
            try {
                image2 = ImageIO.read(new File("/Users/coldmilka/Desktop/testImage.jpg"));

            } catch (IOException e) {

            }

            addMouseListener(new MouseAdapter() {
                private Point startPoint;

                @Override
                public void mousePressed(MouseEvent e) {
                    startPoint = e.getPoint();
                    selection = null;
                    repaint();
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    if (startPoint != null) {
                        Point endPoint = e.getPoint();
                        int x = Math.min(startPoint.x, endPoint.x);
                        int y = Math.min(startPoint.y, endPoint.y);
                        int width = Math.abs(startPoint.x - endPoint.x);
                        int height = Math.abs(startPoint.y - endPoint.y);
                        selection = new Rectangle(x, y, width, height);
                        repaint();
                    }
                }
            });

            addMouseMotionListener(new MouseAdapter() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    Point startPoint = e.getPoint();
                    Point endPoint = e.getPoint();
                    int x = Math.min(startPoint.x, endPoint.x);
                    int y = Math.min(startPoint.y, endPoint.y);
                    int width = Math.abs(startPoint.x - endPoint.x);
                    int height = Math.abs(startPoint.y - endPoint.y);
                    selection = new Rectangle(x, y, width, height);
                    repaint();
                }
            });


        }
        @Override
        public void paintComponent(Graphics g) {

            Graphics2D g2d = (Graphics2D) g.create();
            int w,h;
            int wd,hd;
            double p;
            w = image1.getWidth();
            h = image1.getHeight();
            wd = 300;
            p = h;
            hd = (int)Math.round(p/w * wd);

            g.drawImage(image1, 10, 20, wd, hd, null);

            int dx1,dy1,dx2,dy2;
            dx1 = 200;
            dy1 = 200;
            dx2 = 250;
            p = h;
            hd = (int)Math.round(p/w * (dx2-dx1));
            dy2 = dy1 + hd;


            if (selection != null) {
                g2d.setColor(new Color(255, 255, 255, 100));
                g2d.fill(selection);
            }

            // Overlay image2 on the selected area
            if (selection != null && image2 != null) {
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
                g2d.drawImage(image2, selection.x, selection.y, selection.width, selection.height, this);
            }
            g2d.dispose();
        }
    }

    public static class UserInterface {
        public void createWindow() {
            JFrame frame = new JFrame("Lab 5");
            frame.setSize(500, 500);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(null);

            pictureBox pc;
            pc = new pictureBox();
            pc.setBounds(0, 0, 300, 300);
            frame.add(pc);

            frame.setVisible(true);
        }
    }


    public static void main(String[] args) {
        UserInterface ui = new UserInterface();
        ui.createWindow();
    }
}