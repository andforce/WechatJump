import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

/**
 * Created by diyuanwang on 2017/12/31.
 */
public class ShellExe {


    private static final String[] WIN_RUNTIME = { "cmd.exe", "/C" };
    private static final String[] OS_LINUX_RUNTIME = { "/bin/bash", "-l", "-c" };


    private static <T> T[] concat(T[] first, T[] second) {
        T[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }

    public static List<String> runProcess(String... command) {
        boolean isWin = System.getProperty("os.name").toLowerCase().startsWith("win");
        System.out.print("command to run: ");
        for (String s : command) {
            System.out.print(s);
        }
        System.out.print("\n");
        String[] allCommand = null;
        try {
            if (isWin) {
                allCommand = concat(WIN_RUNTIME, command);
            } else {
                allCommand = concat(OS_LINUX_RUNTIME, command);
            }
            ProcessBuilder pb = new ProcessBuilder(allCommand);
            pb.redirectErrorStream(true);
            Process p = pb.start();
            p.waitFor();
            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String _temp = null;
            List<String> line = new ArrayList<String>();
            while ((_temp = in.readLine()) != null) {
                System.out.println("temp line: " + _temp);
                line.add(_temp);
            }
            System.out.println("result after command: " + line);
            return line;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Image refreshImage(){
        ShellExe.runProcess("adb shell /system/bin/screencap -p /sdcard/screenshot.png && adb pull /sdcard/screenshot.png ./screenshot.png");
        try {
            return ImageIO.read(new File("./screenshot.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Dimension devicesDimension(){
        List<String> re = ShellExe.runProcess("adb shell dumpsys window displays");

        for (String s : re) {
            if (s.contains("cur=")){
                String[] curs = s.split(" ");
                for (String c: curs) {
                    if (c.contains("cur=")){
                        String wh = c.split("=")[1];
                        int w = Integer.valueOf(wh.split("x")[0]);
                        int h = Integer.valueOf(wh.split("x")[1]);
                        Dimension dimension = new Dimension(w, h);
                        return dimension;
                    }
                }
            }
        }
        return null;
    }
}
