package com.ziio.controller;

import com.ziio.annotation.AuthCheck;
import com.ziio.common.BaseResponse;
import com.ziio.common.ErrorCode;
import com.ziio.common.ResultUtils;
import com.ziio.constant.UserConstant;
import com.ziio.exception.BusinessException;
import com.ziio.manager.CosManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/file")
@Slf4j
public class FileController {

    @Resource
    CosManager cosManager;

    /**
     * 测试文件上传
     *
     * @param multipartFile
     * @return
     */
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    @PostMapping("/test/upload")
    public BaseResponse<String> testUploadFile(@RequestPart("file") MultipartFile multipartFile){
        // 文件目录
        String filename = multipartFile.getOriginalFilename();
        String filePath = String.format("/test/%s", filename);
        File file = null;
        try {
            // 上传文件
            file = File.createTempFile(filePath,null);
            multipartFile.transferTo(file);
            cosManager.putObject(filePath,file);
            // 返回可访问地址
            return ResultUtils.success(filePath);
        } catch (IOException e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "上传失败");
        }finally {
            if(file != null){
                // 删除临时文件
                boolean delete = file.delete();
                if(!delete){
                }
            }
        }

    }

}
