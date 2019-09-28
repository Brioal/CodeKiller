package com.atmo.atmo.util;

public interface OnExcelReadListener {

    /**
     * 文件读取回调
     *
     * @param rowIndex 当前行数
     * @param values   值列表
     */
    void read(int rowIndex, String[] values);

    /**
     * 文件不存在的回调
     */
    void fileNotExit();
}
