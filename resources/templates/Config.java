package com.atmo.atmo.config;

import java.io.File;

/**
 * 参数配置i文件
 * email:brioal@foxmail.com
 * github:https://github.com/Brioal
 * Created by Brioal on 2018/4/20.
 */

public class Config {

    /**
     * ip解析地址
     */
    public static String IP_CONVERT_URL = "http://ip.taobao.com/service/getIpInfo.php?ip=";
    /**
     * 备份服务器地址
     */
    public static String BACK_FILE_SERVER_URL = "http://192.168.6.133:8014/upload";

    /**
     * 备份文件的存放位置
     */
    public static String BACK_FILE_DIR_NAME = "backups";
    /**
     * 项目的主目录
     */
    public static String PROJECT_DIR = "/Atmo";
    /**
     * 普通文件的目录名称
     */
    public static String REGULAR_FILE_DIR_NAME = "saveFiles";
    /**
     * 模板文件的存放位置
     */
    public static String TEMPLATE_FILE_DIR_NAME = "templates";
    /**
     * 生产厂商logo存放位置
     */
    public static String TEMPLATE_PRODUCER_DIR_NAME = "producers";
    /**
     * 车辆品牌logo存放位置
     */
    public static String TEMPLATE_CAR_BRAND_DIR_NAME = "carBrands";

    /**
     * 首页url
     */
    public static String URL_HOME = "http://61.178.110.2:8012";


    /**
     * 打分系统url
     */
    public static String URL_MARK = "http://61.178.110.2:8012/mark_login/";
    /**
     * 邮件用的版权信息
     */
    public static String VERSION_FOR_EMAIl = "@2019 甘肃省计算中心 联系电话:0931-123456";

    /**
     * 返回文件全路径
     *
     * @param path
     * @return
     */
    public static String getFullLocalPath(String path) {
        return PROJECT_DIR + File.separator + path;
    }

    /**
     * 返回保存文件的目录地址
     *
     * @return
     */
    public static String getSaveFileDirPath() {
        return PROJECT_DIR + File.separator + REGULAR_FILE_DIR_NAME;
    }

    /**
     * 邮箱发送地址，
     * 以后需要改变
     */
    public static String SEND_EMAIL_ADDRESS = "gspcc2018@163.com";

}
