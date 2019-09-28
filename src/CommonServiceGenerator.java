import com.intellij.ide.util.DirectoryUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.psi.*;
import interfaces.OnCheckBoxChooseListener;
import util.LocalFileUtil;


public class CommonServiceGenerator extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        // 获取主包文件
        PsiFile psiFile = e.getData(LangDataKeys.PSI_FILE);
        // 获取当前文件夹对象
        PsiDirectory currentDir = psiFile.getContainingDirectory();
        ToolsChooseDialog dialog = new ToolsChooseDialog();
        dialog.pack();
        dialog.setOnCheckBoxChooseListener(new OnCheckBoxChooseListener() {
            @Override
            public void done(boolean[] result) {
                // DoubleUtil.java
                if (result[0]) {
                    writeFile(currentDir, "DoubleUtil.java");
                }
                // IntegerUtil.java
                if (result[1]) {
                    writeFile(currentDir, "IntegerUtil.java");
                }
                // TextUtil.java
                if (result[2]) {
                    writeFile(currentDir, "TextUtil.java");
                }
                // ListUtil.java
                if (result[3]) {
                    writeFile(currentDir, "ListUtil.java");
                }
                // UpdateTool.java
                if (result[4]) {
                    writeFile(currentDir, "UpdateTool.java");
                }
                // CodeUtil.java
                if (result[5]) {
                    writeFile(currentDir, "CodeUtil.java");
                }
                // JpaExampleUtil.java
                if (result[6]) {
                    writeFile(currentDir, "JpaExampleUtil.java");
                }
                // PasswordUtil.java
                if (result[7]) {
                    writeFile(currentDir, "PasswordUtil.java");
                }
                // RandomUtil.java
                if (result[8]) {
                    writeFile(currentDir, "RandomUtil.java");
                }
                // ReviewDateFormatUtl.java
                if (result[9]) {
                    writeFile(currentDir, "ReviewDateFormatUtl.java");
                }
                // ReviewFileUtils.java
                if (result[10]) {
                    writeFile(currentDir, "ReviewFileUtils.java");
                }
                // SizeConverter.java
                if (result[11]) {
                    writeFile(currentDir, "SizeConverter.java");
                }
                // TokenUtils.java
                if (result[12]) {
                    writeFile(currentDir, "TokenUtils.java");
                }
            }
        });
        dialog.setVisible(true);
    }

    public void writeFile(PsiDirectory currentDir, String fileName) {
        // 读取文件
        String content = LocalFileUtil.readTemplatesFileContent(fileName);
        PsiDirectory utilDirectory = currentDir.findSubdirectory("util");
        if (utilDirectory == null) {
            // 创建文件夹
            utilDirectory = DirectoryUtil.createSubdirectories("util", currentDir, ".");
        }
        LocalFileUtil.writeFile(utilDirectory, fileName, content);
    }


}
