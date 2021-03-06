import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

/**
 * Created by diyuanwang on 2018/1/1.
 */
public class JumpViewPanel extends JPanel {

    Point startPoint;
    Point endPoint;

    double C = 1.56f;
    Image mImage = null;
    Dimension screenSize =  Toolkit.getDefaultToolkit().getScreenSize();
    Dimension devicesDimension = ShellExe.devicesDimension();

    public JumpViewPanel(Image i) {

        mImage = i;

        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

                int button = e.getButton();
                if (button == 1) {

                    startPoint = e.getPoint();

                    repaint();

                } else if (button == 2) {

                    mImage = ShellExe.refreshImage();
                    repaint();

                } else if (button == 3) {

                    endPoint = e.getPoint();


                    repaint();


                    if (startPoint == null) {
                        return;
                    }


                    int x = Math.abs(startPoint.x - endPoint.x);
                    int y = Math.abs(startPoint.y - endPoint.y);

                    //float scale = screenSize.height / devicesDimension.height;
                    double jumpDistance = Math.sqrt(x * x + y * y) * C * devicesDimension.height / screenSize.height;

                    System.out.println("x:" + x + " y:" + y + " dis:" + jumpDistance);

                    new Thread(new Runnable() {
                        @Override
                        public void run() {


                            ShellExe.runProcess("adb shell input swipe 100 200 100 100 " + (int) +jumpDistance);

                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e1) {
                                e1.printStackTrace();
                            }

                            mImage = ShellExe.refreshImage();

                            startPoint = null;
                            endPoint = null;

                            repaint();
                        }
                    }).start();


                }

            }

            @Override
            public void mousePressed(MouseEvent e) {

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
        });
    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);

        if (mImage != null) {
            g.drawImage(mImage, 0, 0, devicesDimension.width * screenSize.height / devicesDimension.height, screenSize.height, null);
        }

        if (startPoint != null) {
            g.fillOval((int) startPoint.getX() - 10, (int) startPoint.getY() - 10, 20, 20);
        }

        if (endPoint != null) {
            g.fillOval((int) endPoint.getX() - 10, (int) endPoint.getY() - 10, 20, 20);
        }
    }
}
