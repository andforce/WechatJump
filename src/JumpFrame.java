import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

public class JumpFrame extends JFrame{


    JumpViewPanel jumpPanel = null;

    public JumpFrame() {
        jumpPanel = new JumpViewPanel(ShellExe.refreshImage());

        this.add(jumpPanel);
        this.setSize(1080, 1920);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);

    }

    public static void main(String[] args) {

        JumpFrame jumpFrame = new JumpFrame();


    }





}
