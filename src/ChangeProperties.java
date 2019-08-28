import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.psi.PsiFile;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.PairFunction;
import org.jetbrains.annotations.NotNull;
import util.PluginUtil;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 修改当前profile配置
 */
public class ChangeProperties extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent event) {
        // 读取配置文件
        VirtualFile virtualFile = PluginUtil.getPropertyFile(event.getProject());
        // 获取文档
        Document document = FileDocumentManager.getInstance().getDocument(virtualFile);
        // 全部文本
        String allText = document.getText();
        System.out.println("全部配置内容:" + allText);
        int type = 0;
        String devProfile = "spring.profiles.active=dev";
        String testProfile = "spring.profiles.active=test";
        String onlineProfile = "spring.profiles.active=online";
        String oldText = "";
        if (allText.contains(devProfile)) {
            type = 0;
            oldText = devProfile;
        } else if (allText.contains(testProfile)) {
            type = 1;
            oldText = testProfile;
        }
        if (allText.contains(onlineProfile)) {
            type = 2;
            oldText = onlineProfile;
        }

        String finalOldText = oldText;
        int result = Messages.showDialog("选择要切换的环境", "选择开发环境", new String[]{"dev", "test", "online"}, type, type, null, null);
        System.out.println(result);
        // 根据当前选中类型替换总文本
        String targetText = "";
        switch (result) {
            case 0:
                targetText = devProfile;
                break;
            case 1:
                targetText = testProfile;
                break;
            case 2:
                targetText = onlineProfile;
                break;

        }
        allText = allText.replace(finalOldText, targetText);
        // 保存
        String finalAllText = allText;
        WriteCommandAction.runWriteCommandAction(event.getProject(), new Runnable() {
            @Override
            public void run() {
                document.setText(finalAllText);
            }
        });


    }


}
