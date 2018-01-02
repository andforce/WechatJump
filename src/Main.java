import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;

public class Main extends JFrame{


    public Main() {



        Dimension screenSize =  Toolkit.getDefaultToolkit().getScreenSize();

        Dimension devicesDimension = ShellExe.devicesDimension();

        if (devicesDimension == null){
            System.out.println("faile get devices dimension");
            return;
        }

        float scale = screenSize.height / devicesDimension.height;

        System.out.println("显示器：" + screenSize.width + " x " + screenSize.height);

        JumpViewPanel jumpPanel = new JumpViewPanel(ShellExe.refreshImage());

        this.add(jumpPanel);
        this.setSize(devicesDimension.width * screenSize.height / devicesDimension.height, screenSize.height);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);

    }

    public static void main(String[] args) {

        Main main = new Main();

//        Dimension dimension = ShellExe.devicesDimension();
//
//        System.out.println(" >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + dimension.width + " " + dimension.height);

        //ShellExe.runProcess("adb shell /system/bin/screencap -p /sdcard/screenshot.png && adb pull /sdcard/screenshot.png ./screenshot.png");

    }
}
