import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.impl.compiled.ClsClassImpl;
import com.intellij.psi.impl.source.PsiClassImpl;
import org.codehaus.groovy.control.messages.Message;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 默认值生成器
 */
public class ValueDefaultGenerator extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent event) {
        PsiElement psiElement = event.getData(LangDataKeys.PSI_ELEMENT);
        ClsClassImpl psiType = (ClsClassImpl) psiElement;
        if (psiType == null) {
            Messages.showDialog("选中类型不需要设置默认值,目前支持\nInteger \nBoolean \nString(会设置成TEXT类型,本质上不算是默认值)", "错误", new String[]{"好的"}, 0, null);
            return;
        }
        // 获取类名称
        String className = psiType.getName();
        System.out.println("类名称:" + className);
        final Editor editor = event.getRequiredData(CommonDataKeys.EDITOR);
        final Project project = event.getRequiredData(CommonDataKeys.PROJECT);
        final Document document = editor.getDocument();
        // 获取选中实体上一行的
        CaretModel caretModel = editor.getCaretModel();
        SelectionModel selectionModel = editor.getSelectionModel();
        int getSelectionStart = selectionModel.getSelectionStart();
        int lineNumber = document.getLineNumber(getSelectionStart);
        int lineStartOffset = document.getLineStartOffset(lineNumber);
        // 要插入的内容
        String content = "";
        switch (className) {
            case "Integer":
                content = "@Column(columnDefinition = \"int default 0\")\n";
                break;
            case "Boolean":
                content = "@Column(columnDefinition = \"tinyint default 0\")\n";
                break;
            case "Double":
                content = "@Column(columnDefinition = \"double default 0\")\n";
                break;
            case "String":
                content = "@Column(columnDefinition = \"TEXT\")\n";
                break;
            default:
                Messages.showDialog("选中类型不需要设置默认值,目前支持\nInteger \nBoolean \nString(会设置成TEXT类型,本质上不算是默认值)", "错误", new String[]{"好的"}, 0, null);
                break;
        }
        String finalContent = content;
        WriteCommandAction.runWriteCommandAction(project, () ->
                document.insertString(lineStartOffset, finalContent)
        );
    }

}
