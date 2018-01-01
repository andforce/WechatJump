import javax.swing.*;

public class Main extends JFrame{


    JumpViewPanel jumpPanel = null;

    public Main() {
        jumpPanel = new JumpViewPanel(ShellExe.refreshImage());

        this.add(jumpPanel);
        this.setSize(1080, 1920);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);

    }

    public static void main(String[] args) {

        Main main = new Main();


    }





}
