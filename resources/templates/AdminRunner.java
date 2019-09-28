package com.atmo.atmo.config.runner;

import com.atmo.atmo.bean.user.AdminUserBean;
import com.atmo.atmo.service.user.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 添加全权限用户
 * email:brioal@foxmail.com
 * github:https://github.com/Brioal
 * Created by brioa on 2018/4/24.
 */
@Component
@Order(value = 2)
public class AdminRunner implements CommandLineRunner {
    private AdminUserService adminUserService;

    @Autowired
    public void setAdminUserService(AdminUserService adminUserService) {
        this.adminUserService = adminUserService;
    }

    @Override
    public void run(String... args) throws Exception {
        // 添加全权限用户
        String code1 = "100000";
        String name1 = "超级管理员";
        String password1 = "1234qwerA.";
        // 更新/保存
        saveAdmin(code1, name1, password1);
    }


    /**
     * 保存超级管理员
     *
     * @param code
     * @param name
     * @param password
     */
    private void saveAdmin(String code, String name, String password) {
        // 寻找管理员
        AdminUserBean result = adminUserService.findByCode(code);
        if (result == null) {
            result = new AdminUserBean();
        } else {
        }
        result.setCode(code);
        result.setName(name);
        result.setPassWord(password);
        result.setSuperAdmin(true);
        result.setEnable(true);
        adminUserService.save(result);
    }
}
