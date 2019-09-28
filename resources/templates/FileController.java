package com.atmo.atmo.controller.system;


import com.atmo.atmo.bean.tool.ResultBean;
import com.atmo.atmo.config.Config;
import com.atmo.atmo.injections.PermissionCheck;
import com.atmo.atmo.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api(description = "文件管理类")
@RestController
@RequestMapping("/file_manager")
public class FileController {

    private FileService fileService;

    @Autowired
    public void setFileService(FileService fileService) {
        this.fileService = fileService;
    }




}
