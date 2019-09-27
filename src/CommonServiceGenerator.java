import com.intellij.ide.util.DirectoryUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.InputValidator;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.psi.*;
import com.intellij.psi.codeStyle.CodeStyleManager;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.PsiShortNamesCache;
import com.intellij.util.PairFunction;
import interfaces.OnCheckBoxChooseListener;
import util.FileUtil;

import javax.swing.*;
import java.awt.*;
import java.io.File;


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
                    writeFile(currentDir, "DoubleUtil.java", "");
                }
                // IntegerUtil.java
                if (result[1]) {
                    writeFile(currentDir, "IntegerUtil.java", "");
                }
                // TextUtil.java
                if (result[2]) {
                    writeFile(currentDir, "TextUtil.java", "");
                }
                // ListUtil.java
                if (result[3]) {
                    writeFile(currentDir, "ListUtil.java", "");
                }
                // UpdateTool.java
                if (result[4]) {
                    writeFile(currentDir, "UpdateTool.java", "");
                }
                // CodeUtil.java
                if (result[5]) {
                    writeFile(currentDir, "CodeUtil.java", "");
                }
                // JpaExampleUtil.java
                if (result[6]) {
                    writeFile(currentDir, "JpaExampleUtil.java", "");
                }
                // PasswordUtil.java
                if (result[7]) {
                    writeFile(currentDir, "PasswordUtil.java", "");
                }
                // RandomUtil.java
                if (result[8]) {
                    writeFile(currentDir, "RandomUtil.java", "");
                }
                // ReviewDateFormatUtl.java
                if (result[9]) {
                    writeFile(currentDir, "ReviewDateFormatUtl.java", "");
                }
                // ReviewFileUtils.java
                if (result[10]) {
                    writeFile(currentDir, "ReviewFileUtils.java", "");
                }
                // SizeConverter.java
                if (result[11]) {
                    writeFile(currentDir, "SizeConverter.java", "");
                }
                // TokenUtils.java
                if (result[12]) {
                    writeFile(currentDir, "TokenUtils.java", "");
                }
            }
        });
        dialog.setVisible(true);
    }

    public static void writeFile(PsiDirectory currentDir, String fileName, String content) {
        // 判断是否已经存在util
        PsiDirectory implDir = currentDir.findSubdirectory("util");
        if (implDir == null) {
            // 创建文件夹
            implDir = DirectoryUtil.createSubdirectories("util", currentDir, ".");
        }

        // 创建一个文件 TestServiceImpl.java
        String className = "UpdateTool.java";
        // 新建tool文件
        File toolsFile = new File(implDir.getVirtualFile().getCanonicalPath() + File.separator + className);
        if (toolsFile.exists()) {
            Messages.showDialog("UpdateTool文件已存在", "错误", new String[]{"好的"}, 0, null);
            return;
        } else {
            FileUtil.writeFile(toolsFile, "\n" +
                    "\n" +
                    "import org.springframework.beans.BeanUtils;\n" +
                    "import org.springframework.beans.BeanWrapper;\n" +
                    "import org.springframework.beans.BeanWrapperImpl;\n" +
                    "\n" +
                    "import java.beans.PropertyDescriptor;\n" +
                    "import java.util.HashSet;\n" +
                    "import java.util.Set;\n" +
                    "\n" +
                    "public class UpdateTool {\n" +
                    "    /**\n" +
                    "     * 将要设置的数据当中不为null的属性设置到从数据库读取的数据里面\n" +
                    "     *\n" +
                    "     * @param source 要设置的数据\n" +
                    "     * @param target 从数据库读取的\n" +
                    "     */\n" +
                    "    public static void copyNullProperties(Object source, Object target) {\n" +
                    "        BeanUtils.copyProperties(source, target, getNoNullProperties(source));\n" +
                    "    }\n" +
                    "\n" +
                    "    /**\n" +
                    "     * @param source 要修改的实体\n" +
                    "     * @return 将目标源中为空的字段取出\n" +
                    "     */\n" +
                    "    private static String[] getNoNullProperties(Object source) {\n" +
                    "        BeanWrapper srcBean = new BeanWrapperImpl(source);\n" +
                    "        PropertyDescriptor[] pds = srcBean.getPropertyDescriptors();\n" +
                    "        Set<String> emptyNames = new HashSet<>();\n" +
                    "        for (PropertyDescriptor p : pds) {\n" +
                    "            String name = p.getName();\n" +
                    "            Object value = srcBean.getPropertyValue(name);\n" +
                    "            if (value == null) emptyNames.add(name);\n" +
                    "        }\n" +
                    "        String[] result = new String[emptyNames.size()];\n" +
                    "        return emptyNames.toArray(result);\n" +
                    "    }\n" +
                    "}\n");
        }
        // 刷新文件
        VirtualFileManager.getInstance().syncRefresh();
    }


}
