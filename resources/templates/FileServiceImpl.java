package com.atmo.atmo.service.impl;

import com.atmo.atmo.bean.system.FileBean;
import com.atmo.atmo.config.Config;
import com.atmo.atmo.repository.FileRepository;
import com.atmo.atmo.service.FileService;
import com.atmo.atmo.util.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * email:brioal@foxmail.com
 * github:https://github.com/Brioal
 * Created by brioa on 2018/10/11.
 */
@Service
public class FileServiceImpl implements FileService {

    // 记录器
    public static Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);


    FileRepository mFileRepository;

    @Autowired
    public void setFileRepository(FileRepository fileRepository) {
        mFileRepository = fileRepository;
    }


    /**
     * 读取文件夹htmls下面的指定html 的内容
     *
     * @param name
     * @return
     */
    @Override
    public String readHtmlContents(String name) {
        String path = "htmls/" + name;
        File file = new File(path);
        if (!file.exists()) {
            return null;
        }
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
            String str = "";
            StringBuffer stringBuffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                stringBuffer.append(str);
            }
            return stringBuffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 保存临时文件
     *
     * @param dirName
     * @param fileName
     * @param file
     */
    @Override
    public void saveTemplate(String dirName, String fileName, MultipartFile file) {
        if (file == null) {
            return;
        }
        logger.info("将上传的文件保存模板文件:");
        // 文件类型
        logger.info("文件夹名称:" + dirName);
        logger.info("文件名称:" + fileName);
        // 获取要存储的目标文件
        File targetFile = new File(Config.PROJECT_DIR + "/" + Config.TEMPLATE_FILE_DIR_NAME + "/" + dirName + "/" + fileName);
        logger.info("目标文件路径:" + targetFile.getAbsolutePath());
        // 保存文件
        try {
            FileCopyUtils.copy(file.getBytes(), targetFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 保存文件
     *
     * @param file
     * @return
     */
    @Override
    public FileBean saveFile(MultipartFile file, boolean temp) {
        FileBean fileBean = new FileBean();
        // 保存文件
        File targetFile = ReviewFileUtils.saveRegularFileAndReturnPath(file);
        return saveFile(targetFile, temp);
    }

    /**
     * 编辑文件
     *
     * @param fileBean
     * @param file
     * @param temp
     * @return
     */
    @Override
    public FileBean editFile(FileBean fileBean, MultipartFile file, boolean temp) {
        String oldPath = fileBean.getPath();
        String oldPathThumb = fileBean.getThumbPath();
        // 先保存文件
        File targetFile = ReviewFileUtils.saveRegularFileAndReturnPath(file);
        fileBean.setPath(ReviewFileUtils.getRelativePath(targetFile));
        // 保存修改时间
        fileBean.setEditTime(ReviewDateFormatUtl.getCurrentTime());
        // 保存文件大小
        String fileSize = SizeConverter.convertBytes(file.getSize(), true);
        fileBean.setSize(fileSize);
        // 是否是临时文件
        fileBean.setTemp(temp);
        // 保存文件
        FileBean result = mFileRepository.save(fileBean);
        // 删除原有文件
        File oldFile = new File(Config.getFullLocalPath(oldPath));
        File oldFileThumb = new File(Config.getFullLocalPath(oldPathThumb));
        if (oldFile.exists()) {
            oldFile.delete();
        }
        if (oldFileThumb.exists()) {
            oldFileThumb.delete();
        }
        return result;
    }

    /**
     * 新建一个FileBean
     *
     * @param file
     * @param temp
     * @return
     */
    @Override
    public FileBean saveFile(File file, boolean temp) {
        FileBean fileBean = new FileBean();
        // 保存文件名
        fileBean.setName(file.getName());
        // 获取相对的路径
        fileBean.setPath(ReviewFileUtils.getRelativePath(file));
        // 保存创建时间
        fileBean.setCreateTime(ReviewDateFormatUtl.getCurrentTime());
        // 保存修改时间
        fileBean.setEditTime(ReviewDateFormatUtl.getCurrentTime());
        // 保存文件大小
        String fileSize = SizeConverter.convertBytes(file.length(), true);
        fileBean.setSize(fileSize);
        // 是否是临时文件
        fileBean.setTemp(temp);
        // 保存文件
        FileBean result = mFileRepository.save(fileBean);
        return result;
    }

    @Override
    public FileBean editFile(FileBean fileBean, File file, boolean temp) {
        // 原有文件路径
        String oldPath = fileBean.getPath();
        // 保存文件
        fileBean.setName(file.getName());
        fileBean.setPath(ReviewFileUtils.getRelativePath(file));
        // 保存修改时间
        fileBean.setEditTime(ReviewDateFormatUtl.getCurrentTime());
        // 保存文件大小
        String fileSize = SizeConverter.convertBytes(file.length(), true);
        fileBean.setSize(fileSize);
        // 是否是临时文件
        fileBean.setTemp(temp);
        // 保存文件
        FileBean result = mFileRepository.save(fileBean);
        // 删除原有文件
        System.out.println("源文件:" + oldPath);
        File oldFile = new File(Config.getFullLocalPath(oldPath));
        if (oldFile.exists()) {
            System.out.println("原有文件存在,删除");
            oldFile.delete();
        }
        return result;
    }

    /**
     * 删除文件
     *
     * @param fileBean
     */
    @Override
    public void delFile(FileBean fileBean) {
        if (fileBean == null) {
            return;
        }
        try {
            // 删除物理文件
            File file = new File(Config.getFullLocalPath(fileBean.getPath()));
            File fileThumb = new File(Config.getFullLocalPath(fileBean.getThumbPath()));
            if (file.exists()) {
                file.delete();
            }
            if (fileThumb.exists()) {
                fileThumb.delete();
            }
            // 删除数据库数据
            mFileRepository.deleteById(fileBean.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 清空临时文件
     */
    @Async
    @Override
    public void delTemporaryFile() {
        System.out.println("开始清空临时文件");
        // 获取所有的临时文件
        Page<FileBean> page = mFileRepository.findAllByTempEqualsOrderByCreateTimeDesc(true, ConvertUtil.getFullPageRequest());
        try {
            List<FileBean> list = page.getContent();
            System.out.println("临时文件数量:" + list.size());
            if (!ListUtil.isAvaliable(list)) {
                return;
            }
            for (int i = 0; i < list.size(); i++) {
                FileBean fileBean = list.get(i);
                delFile(fileBean);
            }
            System.out.println("清空临时文件完成");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public FileBean getById(int id) {
        if (id <= 0) {
            return null;
        }
        return mFileRepository.findById(id).get();
    }

    @Override
    public FileBean saveExcelFile(XSSFWorkbook workbook) {
        if (workbook == null) {
            return null;
        }
        try {
            File file = ReviewFileUtils.getRegularFormatFile("xlsx");
            FileOutputStream fout = new FileOutputStream(file);
            workbook.write(fout);
            fout.close();
            FileBean fileBean = saveFile(file, true);
            return fileBean;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
