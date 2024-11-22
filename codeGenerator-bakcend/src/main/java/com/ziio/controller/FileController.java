package com.ziio.controller;

import cn.hutool.core.io.IoUtil;
import com.qcloud.cos.model.COSObject;
import com.qcloud.cos.model.COSObjectInputStream;
import com.qcloud.cos.utils.IOUtils;
import com.ziio.annotation.AuthCheck;
import com.ziio.common.BaseResponse;
import com.ziio.common.ErrorCode;
import com.ziio.common.ResultUtils;
import com.ziio.constant.UserConstant;
import com.ziio.exception.BusinessException;
import com.ziio.manager.CosManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
            log.error("file upload error, filepath = " + filePath, e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "上传失败");
        }finally {
            if(file != null){
                // 删除临时文件
                boolean delete = file.delete();
                if(!delete){
                    log.error("file delete error , filePath = {}" , filePath);
                }
            }
        }

    }

    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    @GetMapping("/test/download")
    public void testDownloadFile(String filePath , HttpServletResponse response) throws IOException {
        COSObjectInputStream cosObjectInputStream = null;
        try {
            // 在 cos 上找到 cosObject
            COSObject cosObject = cosManager.getObject(filePath);
            cosObjectInputStream = cosObject.getObjectContent();
            // 处理下载流
            byte[] byteArray = IOUtils.toByteArray(cosObjectInputStream);
            // 设置响应头
            response.setContentType("application/octet-stream;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=" + filePath);
            // 写入响应
            response.getOutputStream().write(byteArray);
            response.getOutputStream().flush();
        } catch (IOException e) {
            log.error("file download error, filepath = " + filePath, e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "下载失败");
        }finally {
            if (cosObjectInputStream != null) {
                cosObjectInputStream.close();
            }
        }
    }
}
