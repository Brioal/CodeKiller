import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;

import java.io.*;
import java.util.Scanner;

/**
 * 生成多个 application.properties 文件
 */
public class SetProperties extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent event) {
        VirtualFile baseVirtualFile = event.getData(PlatformDataKeys.VIRTUAL_FILE);
        if (baseVirtualFile == null) {
            System.out.println("文件选择错误");
            return;
        }
        // 文件名字
        String name = baseVirtualFile.getName();
        System.out.println(name);
        if (!name.equals("application.properties")) {
            System.out.println("文件选择错误");
            return;
        }
        String basePath = baseVirtualFile.getCanonicalPath();
        File baseFile = new File(basePath);
        if (!baseFile.exists()) {
            System.out.println("文件选择错误");
            return;
        }
        // 增加环境配置切换的代码
        final Editor editor = event.getRequiredData(CommonDataKeys.EDITOR);
        final Project project = event.getRequiredData(CommonDataKeys.PROJECT);
        final Document document = editor.getDocument();
        String allText = document.getText();
        String lineIndex = "# 环境切换配置 dev=开发环境 test=测试环境 online=正式环境";
        System.out.println("全部配置:" + allText);
        if (allText.contains(lineIndex)) {
            System.out.println("已经存在环境切换代码");
        } else {
            // 添加环境配置代码
            String profiles = lineIndex + "\nspring.profiles.active=test\n";
            // 插入到第一行
            WriteCommandAction.runWriteCommandAction(project, new Runnable() {
                @Override
                public void run() {
                    document.insertString(0, profiles);
                }
            });
        }
        // 获取目录
        String dir = baseFile.getParent();
        System.out.println("父级目录:" + dir);
        // 新建dev文件
        File devFile = new File(dir + File.separator + "application-dev.properties");
        if (devFile.exists()) {
            System.out.println("dev文件已存在");
        } else {
            writeFile(devFile, "# 开发环境配置");
        }
        // 新建test文件
        File testFile = new File(dir + File.separator + "application-test.properties");
        if (testFile.exists()) {
            System.out.println("test文件已存在");
        } else {
            writeFile(testFile, "# 测试环境配置");
        }
        // 新建online文件
        File onlineFile = new File(dir + File.separator + "application-online.properties");
        if (onlineFile.exists()) {
            System.out.println("online文件已存在");
        } else {
            writeFile(onlineFile, "# 正式环境配置");
        }
        // 刷新文件
        VirtualFileManager.getInstance().syncRefresh();

    }

    public static void writeFile(File file, String content) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write(content);
            bufferedWriter.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
