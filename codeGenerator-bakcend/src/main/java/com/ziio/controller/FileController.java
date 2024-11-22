package com.ziio.controller;

import cn.hutool.core.io.FileUtil;
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
import com.ziio.model.dto.file.UploadFileRequest;
import com.ziio.model.entity.User;
import com.ziio.model.enums.FileUploadEnum;
import com.ziio.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

@RestController
@RequestMapping("/file")
@Slf4j
public class FileController {

    @Resource
    CosManager cosManager;

    @Resource
    UserService userService;

    /**
     * 测试文件上传
     *
     * @param multipartFile
     * @return
     */
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    @PostMapping("/upload")
    public BaseResponse<String> uploadFile(@RequestPart("file") MultipartFile multipartFile ,
                                           UploadFileRequest uploadFileRequest , HttpServletRequest request){
        String biz = uploadFileRequest.getBiz();
        FileUploadEnum fileUploadEnum = FileUploadEnum.getEnumByValue(biz);
        if(fileUploadEnum == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 文件大小 ，类型校验
        validFile(multipartFile,fileUploadEnum);
        User loginUser = userService.getLoginUser(request);
        // 文件目录 : 根据业务 ， 用户来划分
        String uuid = RandomStringUtils.randomAlphabetic(8);
        String filename = uuid + "-" + multipartFile.getOriginalFilename();
        String filePath = String.format("/%s/%s/%s", fileUploadEnum.getValue(), loginUser.getId(), filename);
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

    /**
     * 校验文件
     * @param multipartFile
     * @param fileUploadEnum
     */
    private void validFile(MultipartFile multipartFile , FileUploadEnum fileUploadEnum){
        // 文件大小
        long size = multipartFile.getSize();
        // 文件后缀
        String suffix = FileUtil.getSuffix(multipartFile.getOriginalFilename());
        final long ONE_M = 1024*1024L;
        if(FileUploadEnum.USER_AVATAR.equals(fileUploadEnum)){
            // 大小校验
            if(size>ONE_M){
                throw new BusinessException(ErrorCode.PARAMS_ERROR , "文件大小不能超过 1 M");
            }
            // 类型校验
            if (!Arrays.asList("jpeg", "jpg", "svg", "png", "webp").contains(suffix)) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "文件类型错误");
            }
        }
    }
}
