import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.impl.source.PsiClassImpl;
import com.intellij.psi.search.EverythingGlobalScope;
import com.intellij.psi.search.PsiShortNamesCache;

public class AutoWire extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent event) {
        //获取当前事件触发时，光标所在的元素
        PsiElement psiElement = event.getData(LangDataKeys.PSI_ELEMENT);
        PsiClassImpl psiType = (PsiClassImpl) psiElement;
        // 获取类名称
        String className = psiType.getName();
        System.out.println("类名:" + className);
        // 转换成变量
        String valiableClassName = (new StringBuilder()).append(Character.toLowerCase(className.charAt(0))).append(className.substring(1)).toString();
        System.out.println("成员变量名:" + valiableClassName);
        // 生成Setter方法
        StringBuffer buffer = new StringBuffer();
        buffer.append("\t@Autowired\n" +
                "    public void set"+className+"("+className+ " "+valiableClassName+") {\n" +
                "        this."+valiableClassName+" = "+valiableClassName+";\n" +
                "    }");
        // 循环实现父类方法
        final Editor editor = event.getRequiredData(CommonDataKeys.EDITOR);
        final Project project = event.getRequiredData(CommonDataKeys.PROJECT);
        final Document document = editor.getDocument();
        Caret primaryCaret = editor.getCaretModel().getPrimaryCaret();
        int start = primaryCaret.getVisualLineEnd();
        primaryCaret.removeSelection();
        WriteCommandAction.runWriteCommandAction(project, () ->
                document.insertString(start, buffer.toString())
        );

    }
}
