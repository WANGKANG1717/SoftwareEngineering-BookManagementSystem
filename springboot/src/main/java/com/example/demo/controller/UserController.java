package com.example.demo.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.User;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController {
    @Resource
    UserMapper userMapper;

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable("id") Integer id) {
        userMapper.deleteById(id);
        return Result.success();
    }
    @PostMapping
    public Result<?> save(@RequestBody User user) {
        userMapper.insert(user);
        return Result.success();
    }
    @PutMapping
    public Result<?> update(@RequestBody User user) {
        userMapper.updateById(user);
        return Result.success();
    }
    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search) {
        LambdaQueryWrapper<User> wrapper = Wrappers.<User>lambdaQuery();
        //加上这个条件可以查询出name为空的数据
        if(StrUtil.isNotBlank(search)) {
            wrapper.like(User::getName, search);
        }
        return Result.success(userMapper.selectPage(new Page<>(pageNum, pageSize), wrapper));
    }
    /**
     * Excel导出
     * @param response
     * @throws IOException
     */
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws IOException {

        List<Map<String, Object>> list = CollUtil.newArrayList();

        List<User> all = userMapper.selectList(null);
        for (User user : all) {
            Map<String, Object> row1 = new LinkedHashMap<>();
            row1.put("用户名", user.getName());
            row1.put("昵称", user.getNickname());
            row1.put("密码", user.getPasswd());
            row1.put("年龄", user.getAge());
            row1.put("性别", user.getSex());
            row1.put("地址", user.getAddress());
            row1.put("角色", user.getRole());
            row1.put("头像", user.getHeadpic());
            list.add(row1);
        }

        // 2. 写excel
        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.write(list, true);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("用户信息", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        writer.close();
        IoUtil.close(System.out);
    }

    //导入接口
    @PostMapping("/import")
    public Result<?> import1(MultipartFile file) throws Exception {
        InputStream inputStream=file.getInputStream();
        List<List<Object>> lists=ExcelUtil.getReader(inputStream).read(1);
        List<User> saveList=new ArrayList<>();
        for (List<Object> row : lists) {
            User user = new User();
            user.setName(row.get(0).toString());
            user.setNickname(row.get(1).toString());
            user.setPasswd(row.get(2).toString());
            user.setAge(Integer.valueOf(row.get(3).toString()));
            user.setSex(row.get(4).toString());
            user.setAddress(row.get(5).toString());
            user.setRole(Integer.valueOf(row.get(6).toString()));
            user.setHeadpic(row.get(7).toString());
            saveList.add(user);
        }
        for (User user : saveList) {
            userMapper.insert(user);
        }
        return Result.success();
    }
}
