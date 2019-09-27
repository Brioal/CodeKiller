package util;

import java.io.*;

public class FileUtil {
    public static void writeFile(File file, String content) {
        try {
            FileOutputStream fos = new FileOutputStream(file);
            OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
            osw.write(content);
            osw.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
