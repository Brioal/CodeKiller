package com.atmo.atmo.service;


import com.atmo.atmo.bean.system.FileBean;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * email:brioal@foxmail.com
 * github:https://github.com/Brioal
 * Created by brioa on 2018/10/11.
 */

public interface FileService {

    /**
     * 读取文件夹htmls下的html文件内容
     *
     * @param name
     * @return
     */
    String readHtmlContents(String name);

    /**
     * 保存文件到模板文件文件夹
     *
     * @param dirName
     * @param fileName
     * @param file
     */
    void saveTemplate(String dirName, String fileName, MultipartFile file);

    // 保存文件
    FileBean saveFile(MultipartFile file, boolean temp);

    // 修改文件
    FileBean editFile(FileBean fileBean, MultipartFile file, boolean temp);

    /**
     * 保存文件
     *
     * @param file
     * @param temp
     * @return
     */
    FileBean saveFile(File file, boolean temp);


    /**
     * 修改文件
     *
     * @param fileBean
     * @param file
     * @param temp
     * @return
     */
    FileBean editFile(FileBean fileBean, File file, boolean temp);

    /**
     * 删除文件
     *
     * @param fileBean
     */
    void delFile(FileBean fileBean);

    /**
     * 清除临时文件
     */
    void delTemporaryFile();

    /**
     * 根据ID获取详情
     *
     * @param id
     * @return
     */
    FileBean getById(int id);

    /**
     * 保存一个excel文件,一般情况是临时文件
     *
     * @return
     */
    FileBean saveExcelFile(XSSFWorkbook workbook);


}
