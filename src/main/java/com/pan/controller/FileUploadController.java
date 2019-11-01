package com.pan.controller;

import com.pan.util.FastdfsClientUtil;
import com.pan.util.FastdfsClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileUploadController {

    @Autowired
    private FastdfsClientUtil fastdfsClientUtil;


    /**
     * 跳转到文件上传的页面
     * */
    @GetMapping("/admin/file-upload")
    public String toFileUploadPage(Model model){
        model.addAttribute("FilePath","");
        return "admin/file-upload";
    }

    @PostMapping("/admin/fileupload")
    public String fileUpload(MultipartFile file,Model model){
        String addr = null;
        try {
            addr = fastdfsClientUtil.upload(file);
            if(StringUtils.isEmpty(addr)){
                addr = "上传失败,请重试";
            }else{
                addr = "http://106.15.204.187:8888/" + addr;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        model.addAttribute("FilePath",addr);
        return "admin/file-upload";
    }
}
