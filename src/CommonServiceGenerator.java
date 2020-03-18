import bean.GitFileBean;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.*;
import interfaces.OnListChooseListener;
import util.HttpUtils;
import util.LocalFileUtil;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;


public class CommonServiceGenerator extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        // 读取工具类列表
        String jsonList = HttpUtils.doGet("https://gitee.com/api/v5/repos/brioalishard/JavaCommon/git/trees/23a280832266018170e89eaf7c87f4e550d48870");
        if (jsonList == null) {
            Messages.showDialog("获取数据失败,请检查网络后重试", "错误", new String[]{"好的"}, 0, null);
            return;
        }
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = jsonParser.parse(jsonList).getAsJsonObject();
        JsonArray jsonArray = jsonObject.getAsJsonArray("tree");
        List<GitFileBean> list = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject object = jsonArray.get(i).getAsJsonObject();
            // 名称
            String name = object.get("path").getAsString();
            // 类型
            String type =object.get("type").getAsString();
            if (!type.equals("blob")) {
                continue;
            }
            // 下载地址
            String downloadFile = object.get("url").getAsString();
            GitFileBean fileBean = new GitFileBean();
            fileBean.setName(name);
            fileBean.setDownload_url(downloadFile);
            list.add(fileBean);
        }

        System.out.println(jsonList);
//        // 获取主包文件
        PsiFile psiFile = e.getData(LangDataKeys.PSI_FILE);
//        // 获取当前文件夹对象
        PsiDirectory currentDir = psiFile.getContainingDirectory();
        ToolsChooseDialog dialog = new ToolsChooseDialog(list);
        dialog.pack();
        dialog.setOnListChooseListener(new OnListChooseListener() {
            @Override
            public boolean choose(int index) {
                // 获取选中的文件
                GitFileBean fileBean = list.get(index);
                // 写入文件到当前目录,存在则更新,不存在则创建
                String fileName = fileBean.getName();
                // 读取文件
                String downLoadUrl = fileBean.getDownload_url();
                // 获取数据
                String content = HttpUtils.doGet(downLoadUrl);
                JsonParser jsonParser = new JsonParser();
                JsonObject jsonObject = jsonParser.parse(content).getAsJsonObject();
                content = jsonObject.get("content").getAsString();
                // 解密
                Base64.Decoder decoder = Base64.getDecoder();
                byte[] bytes = decoder.decode(content);
                try {
                    content = new String(bytes, "UTF-8");
                } catch (UnsupportedEncodingException ex) {
                    ex.printStackTrace();
                }
                if (content == null) {
                    Messages.showDialog("获取数据失败,请检查网络后重试", "错误", new String[]{"好的"}, 0, null);
                    return false;
                }
                LocalFileUtil.writeFile(currentDir, "", fileName, content);
                Messages.showDialog("下载"+fileName+"成功!", "成功", new String[]{"好的"}, 0, null);
                return true;
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
