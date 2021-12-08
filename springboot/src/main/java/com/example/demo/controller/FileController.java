package com.example.demo.controller;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.example.demo.common.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

@RestController
@RequestMapping("/files")
public class FileController {

    @Value("${server.port}")
    private String port;

    @Value("${file.ip}")
    private String ip;

    //上传接口
    @PostMapping("/upload")
    public Result<?> upload(MultipartFile file) throws Exception {
        String originalFilename = file.getOriginalFilename();   //获取文件名称
        //定义文件的唯一标识
        //解决相同文件名互相覆盖的问题
        String flag = IdUtil.fastSimpleUUID();
        String rootFilePath = System.getProperty("user.dir") + "/springboot/src/main/resources/files/" + flag + "_" + originalFilename;//获取上传路径
        FileUtil.writeBytes(file.getBytes(), rootFilePath);     //上传文件
        return Result.success("http://" + ip + ":" + port + "/files/" + flag);
    }

    //富文本文件上传接口
    @PostMapping("/editor/upload")
    public JSON editorUpload(MultipartFile file) throws Exception {
        String originalFilename = file.getOriginalFilename();   //获取文件名称
        //定义文件的唯一标识
        //解决相同文件名互相覆盖的问题
        String flag = IdUtil.fastSimpleUUID();
        String rootFilePath = System.getProperty("user.dir") + "/springboot/src/main/resources/files/" + flag + "_" + originalFilename;//获取上传路径
        FileUtil.writeBytes(file.getBytes(), rootFilePath);     //上传文件
        String url = "http://" + ip + ":" + port + "/files/" + flag;
        JSONObject json = new JSONObject();
        json.set("errno", 0);
        //
        JSONObject data = new JSONObject();
        data.set("url", url);
        //
        JSONArray arr = new JSONArray();
        arr.add(data);
        //
        json.set("data", arr);

        return json;
    }

    //下载接口
    @GetMapping("/{flag}")
    public void getFiles(@PathVariable String flag, HttpServletResponse response) {
        OutputStream os;
        String basePath = System.getProperty("user.dir") + "/springboot/src/main/resources/files/";
        List<String> fileNames = FileUtil.listFileNames(basePath);
        String fileName = fileNames.stream().filter(name -> name.contains(flag)).findAny().orElse("");
        try {
            if (StrUtil.isNotEmpty(fileName)) {
                response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
                response.setContentType("application/octet-stream");
                byte[] bytes = FileUtil.readBytes(basePath + fileName);
                os = response.getOutputStream();
                os.write(bytes);
                os.flush();
                ;
                os.close();
            }
        } catch (Exception e) {
            System.out.println("文件下载失败");
        }
    }


}
