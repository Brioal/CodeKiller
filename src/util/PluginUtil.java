package util;

import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;


public class PluginUtil {

    /**
     * 获取配置文件的Document
     *
     * @param project
     * @return
     */
    public static Document getPropertyDocument(Project project) {
        VirtualFile virtualFile = getPropertyFile(project);
        return getDocumentFromVirtualFile(virtualFile);
    }

    /**
     * 根据文件获取Document
     *
     * @param file
     * @return
     */
    public static Document getDocumentFromVirtualFile(VirtualFile file) {
        Document document = FileDocumentManager.getInstance().getDocument(file);
        return document;
    }

    /**
     * 获取默认的配置文件
     *
     * @return
     */
    public static VirtualFile getPropertyFile(Project project) {
        PsiFile[] files = FilenameIndex.getFilesByName(project, "application.properties", GlobalSearchScope.allScope(project));
        if (files == null || files.length <= 0) {
            Messages.showDialog("找不到配置文件", "错误", new String[]{"确认"}, 0, null);
            return null;
        }
        PsiFile psiFile = files[0];
        VirtualFile virtualFile = psiFile.getVirtualFile();
        return virtualFile;
    }
}
