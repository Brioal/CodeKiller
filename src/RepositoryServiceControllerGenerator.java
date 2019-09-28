import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.*;
import util.LocalFileUtil;
import util.TextUtil;


public class RepositoryServiceControllerGenerator extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        // 获取当前编辑的文件, 通过PsiFile可获得PsiClass, PsiField等对象
        PsiFile psiFile = e.getData(LangDataKeys.PSI_FILE);
        PsiClass psiClass = ((PsiJavaFile) psiFile).getClasses()[0];
        // 获取当前的project对象
        Project project = e.getProject();
        // 获取当前文件夹对象
        PsiDirectory currentDir = psiFile.getContainingDirectory();
        // 获取当前文件所在的包名
        String packageName = TextUtil.getMatchContent(psiClass.getScope().getText(), "package .+;");
        // 当前Bean的名称
        String className = psiFile.getName();
        className = className.replaceAll(".java", "");
        // 变量名称
        String classNameLow = TextUtil.changeFirstToLowCase(className);
        // 去除Bean的名称
        String classNameWithOutBean = className.replaceAll("Bean", "");
        // 地址名称
        String urlName = TextUtil.changeUpCaseToLowCaseAndLine(classNameWithOutBean);
        //开头小写
        String classNameWithOutBeanWithLowCase = TextUtil.changeFirstToLowCase(classNameWithOutBean);
        // 读取Repository模板
        String repositoryName = classNameWithOutBean + "Repository.java";
        String repositoryContent = LocalFileUtil.readTemplatesFileContent("CommonRepository.txt");
        if (repositoryContent == null) {
            Messages.showDialog("读取模板文件失败", "错误", new String[]{"好的"}, 0, null);
            return;
        }
        // 替换内容
        repositoryContent = repositoryContent.replaceAll("\\$bean\\$", className);
        repositoryContent = repositoryContent.replaceAll("\\$bean_low\\$", classNameLow);
        repositoryContent = repositoryContent.replaceAll("\\$bean_with_out_bean\\$", classNameWithOutBean);
        repositoryContent = repositoryContent.replaceAll("\\$bean_with_out_bean_low_case\\$", classNameWithOutBeanWithLowCase);
        repositoryContent = repositoryContent.replaceAll("\\$package_name\\$", packageName);

        String serviceName = classNameWithOutBean + "Service.java";
        String serviceContent = LocalFileUtil.readTemplatesFileContent("CommonService.txt");
        serviceContent = serviceContent.replaceAll("\\$bean\\$", className);
        serviceContent = serviceContent.replaceAll("\\$bean_low\\$", classNameLow);
        serviceContent = serviceContent.replaceAll("\\$bean_with_out_bean\\$", classNameWithOutBean);
        serviceContent = serviceContent.replaceAll("\\$bean_with_out_bean_low_case\\$", classNameWithOutBeanWithLowCase);
        serviceContent = serviceContent.replaceAll("\\$package_name\\$", packageName);

        String serviceImplName = classNameWithOutBean + "ServiceImpl.java";
        String serviceImplContent = LocalFileUtil.readTemplatesFileContent("CommonServiceImpl.txt");
        serviceImplContent = serviceImplContent.replaceAll("\\$bean\\$", className);
        serviceImplContent = serviceImplContent.replaceAll("\\$bean_low\\$", classNameLow);
        serviceImplContent = serviceImplContent.replaceAll("\\$bean_with_out_bean\\$", classNameWithOutBean);
        serviceImplContent = serviceImplContent.replaceAll("\\$bean_with_out_bean_low_case\\$", classNameWithOutBeanWithLowCase);
        serviceImplContent = serviceImplContent.replaceAll("\\$package_name\\$", packageName);

        String controllerName = classNameWithOutBean + "Controller.java";
        String controllerContent = LocalFileUtil.readTemplatesFileContent("CommonController.txt");
        controllerContent = controllerContent.replaceAll("\\$bean\\$", className);
        controllerContent = controllerContent.replaceAll("\\$bean_low\\$", classNameLow);
        controllerContent = controllerContent.replaceAll("\\$bean_with_out_bean\\$", classNameWithOutBean);
        controllerContent = controllerContent.replaceAll("\\$bean_with_out_bean_low_case\\$", classNameWithOutBeanWithLowCase);
        controllerContent = controllerContent.replaceAll("\\$package_name\\$", packageName);
        controllerContent = controllerContent.replaceAll("\\$url\\$", urlName);

        LocalFileUtil.writeFile(currentDir, repositoryName, repositoryContent);
        LocalFileUtil.writeFile(currentDir, serviceName, serviceContent);
        LocalFileUtil.writeFile(currentDir, serviceImplName, serviceImplContent);
        LocalFileUtil.writeFile(currentDir, controllerName, controllerContent);

    }


}
