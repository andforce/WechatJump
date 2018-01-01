import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by diyuanwang on 2017/12/31.
 */
public class ShellExe {

    public void exeCmd(String command){
        InputStreamReader stdISR = null;
        InputStreamReader errISR = null;
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(command);
            int exitValue = process.waitFor();

            String line = null;

            stdISR = new InputStreamReader(process.getInputStream());
            BufferedReader stdBR = new BufferedReader(stdISR);
            while ((line = stdBR.readLine()) != null) {
                System.out.println("STD line:" + line);
            }

            errISR = new InputStreamReader(process.getErrorStream());
            BufferedReader errBR = new BufferedReader(errISR);
            while ((line = errBR.readLine()) != null) {
                System.out.println("ERR line:" + line);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stdISR != null) {
                    stdISR.close();
                }
                if (errISR != null) {
                    errISR.close();
                }
                if (process != null) {
                    process.destroy();
                }
            } catch (IOException e) {
                System.out.println("正式执行命令：" + command + "有IO异常");
            }
        }
    }

    public static Image refreshImage(){
        ShellExe exe = new ShellExe();
        exe.exeCmd("./pull_image.sh");
        try {
            return ImageIO.read(new File("./screenshot.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
