import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import util.PluginUtil;

import java.io.File;

/**
 * mysql配置文件
 */
public class MysqlSetting extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent event) {
        String driverIndex = "spring.datasource.driver-class-name=";
        String driverTemplate = "spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver";

        String passWordIndex = "spring.datasource.password=";
        String passWordTemplate = "spring.datasource.password=root";

        String userNameIndex = "spring.datasource.username=";
        String userNameTemplate = "spring.datasource.username=root";

        String urlIndex = "spring.datasource.url=";
        String urlTemplate = "spring.datasource.url=jdbc:mysql://localhost/mysql?characterEncoding=utf-8&useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";

        // 获取文档
        Document document = PluginUtil.getPropertyDocument(event.getProject());
        // 全部文本
        String allText = document.getText();
        System.out.println("全部配置内容:" + allText);
        // 判断是否存在驱动
        if (allText.contains(driverIndex)) {
            System.out.println("已经存在驱动");
        } else {
            // 添加url
            WriteCommandAction.runWriteCommandAction(event.getProject(), new Runnable() {
                @Override
                public void run() {
                    document.insertString(0, driverTemplate+"\n");
                }
            });
        }
        // 判断是否存在密码
        if (allText.contains(userNameIndex)) {
            System.out.println("已经存在密码");
        } else {
            // 添加url
            WriteCommandAction.runWriteCommandAction(event.getProject(), new Runnable() {
                @Override
                public void run() {
                    document.insertString(0, passWordTemplate+"\n");
                }
            });
        }
        // 判断是否存在用户名
        if (allText.contains(passWordIndex)) {
            System.out.println("已经存在用户名");
        } else {
            WriteCommandAction.runWriteCommandAction(event.getProject(), new Runnable() {
                @Override
                public void run() {
                    document.insertString(0, userNameTemplate+"\n");
                }
            });
        }
        // 判断是否存在url
        if (allText.contains(urlIndex)) {
            System.out.println("已经存在url");
        } else {
            // 添加url
            WriteCommandAction.runWriteCommandAction(event.getProject(), new Runnable() {
                @Override
                public void run() {
                    document.insertString(0, urlTemplate+"\n");
                }
            });
        }
    }


}
