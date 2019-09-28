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
                    writeFile(currentDir, "util", "DoubleUtil.java");
                }
                // IntegerUtil.java
                if (result[1]) {
                    writeFile(currentDir, "util", "IntegerUtil.java");
                }
                // TextUtil.java
                if (result[2]) {
                    writeFile(currentDir, "util", "TextUtil.java");
                }
                // ListUtil.java
                if (result[3]) {
                    writeFile(currentDir, "util", "ListUtil.java");
                }
                // UpdateTool.java
                if (result[4]) {
                    writeFile(currentDir, "util", "UpdateTool.java");
                }
                // CodeUtil.java
                if (result[5]) {
                    writeFile(currentDir, "util", "CodeUtil.java");
                }
                // JpaExampleUtil.java
                if (result[6]) {
                    writeFile(currentDir, "util", "JpaExampleUtil.java");
                }
                // PasswordUtil.java
                if (result[7]) {
                    writeFile(currentDir, "util", "PasswordUtil.java");
                }
                // RandomUtil.java
                if (result[8]) {
                    writeFile(currentDir, "util", "RandomUtil.java");
                }
                // ReviewDateFormatUtl.java
                if (result[9]) {
                    writeFile(currentDir, "util", "ReviewDateFormatUtl.java");
                }
                // ReviewFileUtils.java
                if (result[10]) {
                    writeFile(currentDir, "util", "ReviewFileUtils.java");
                }
                // SizeConverter.java
                if (result[11]) {
                    writeFile(currentDir, "util", "SizeConverter.java");
                }
                // TokenUtils.java
                if (result[12]) {
                    writeFile(currentDir, "util", "TokenUtils.java");
                }
                // WebMvcConf.java
                if (result[13]) {
                    writeFile(currentDir, "config", "WebMvcConf.java");
                }
                // WebMvcConf.java
                if (result[14]) {
                    writeFile(currentDir, "config", "Swagger2Config.java");
                }
                // Config.java
                if (result[15]) {
                    writeFile(currentDir, "config", "Config.java");
                }
                // AuthenticationInterceptor.java
                if (result[16]) {
                    writeFile(currentDir, "config", "AuthenticationInterceptor.java");
                    writeFile(currentDir, "config", "CurrentUserMethodArgumentResolver.java");
                }
                // AdminRunner.java
                if (result[17]) {
                    writeFile(currentDir, "config", "AdminRunner.java");
                }
                // CurrentUser.java
                if (result[18]) {
                    writeFile(currentDir, "injections", "CurrentUser.java");
                }
                // PermissionCheck.java
                if (result[19]) {
                    writeFile(currentDir, "injections", "PermissionCheck.java");
                }
                // FileService.java
                if (result[20]) {
                    writeFile(currentDir, "service", "FileService.java");
                    writeFile(currentDir, "service", "FileServiceImpl.java");
                    writeFile(currentDir, "controller", "FileController.java");
                }

            }
        });
        dialog.setVisible(true);
    }

    public void writeFile(PsiDirectory currentDir, String dirName, String fileName) {
        // 读取文件
        String content = LocalFileUtil.readTemplatesFileContent(fileName);
        LocalFileUtil.writeFile(currentDir, dirName, fileName, content);
    }


}
