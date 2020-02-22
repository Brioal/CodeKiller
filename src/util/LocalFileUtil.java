package util;

import com.intellij.ide.util.DirectoryUtil;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.psi.PsiDirectory;

import java.io.*;

public class LocalFileUtil {
    /**
     * 读取模板文件内容
     *
     * @param fileName
     * @return
     */
    public static String readTemplatesFileContent(String fileName) {
        // 读取内容
        try {
            String result = FileUtil.loadTextAndClose(LocalFileUtil.class.getResourceAsStream("/templates/" + fileName));
//            System.out.println("读取文件内容为:" + result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

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

    /**
     * 新建一个文件并且写入内容
     *
     * @param currentDir
     * @param fileName
     * @param content
     */
    public static void writeFile(PsiDirectory currentDir, String subName, String fileName, String content) {
        // 新建tool文件
        PsiDirectory targetDir = null;
        if (subName != null) {
            PsiDirectory implDir = currentDir.findSubdirectory(subName);
            if (implDir == null) {
                // 创建文件夹
                implDir = DirectoryUtil.createSubdirectories(subName, currentDir, ".");
            }
            targetDir = implDir;
        } else {
            targetDir = currentDir;
        }
        File toolsFile = new File(targetDir.getVirtualFile().getCanonicalPath() + File.separator + fileName);
        if (toolsFile.exists()) {

        }
        LocalFileUtil.writeFile(toolsFile, content);
        // 刷新文件
        VirtualFileManager.getInstance().syncRefresh();
    }


}
