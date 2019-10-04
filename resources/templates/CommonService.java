package com.xbqm.green.service;

import com.xbqm.green.bean.system.FileBean;
import org.springframework.data.domain.Page;

public interface CommonService<T> {
    /**
     * 保存
     *
     * @param bean
     * @return
     */
    T save(T bean);


    /**
     * @param id
     * @return
     */
    T get(Integer id);

    T get(T bean);

    Page<T> find(T bean);

    /**
     * 删除
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * 从excel文件导入
     *
     * @param fileBean
     */
    void importFromExcel(FileBean fileBean);

}
