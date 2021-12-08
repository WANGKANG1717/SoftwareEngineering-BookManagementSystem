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
import com.example.demo.entity.Admin;
import com.example.demo.mapper.AdminMapper;
import com.example.demo.utils.TokenUtils;
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
@RequestMapping("/admin")
public class AdminController extends BaseController {

    @Resource
    AdminMapper adminMapper;

    //通过token来获取用户的信息
    @GetMapping("/getAdmin")
    public Result<?> getAdmin2() {
        Admin admin = getAdmin();
        if(admin==null) {
            return Result.error("-1", "token错误");
        }
        admin.setToken(getToken());
        return Result.success(admin);
    }

    @PostMapping("/login")
    public Result<?> login(@RequestBody Admin admin) {
        Admin res = adminMapper.selectOne(Wrappers.<Admin>lambdaQuery().eq(Admin::getName, admin.getName()).eq(Admin::getPasswd, admin.getPasswd()));
        if (res == null) {
            return Result.error("-1", "用户名或密码错误");
        }
        //生成token
        //genToken这个方法主要是根据用户的信息生成token的,这个token里面存储了adminId,使用用户密码进行加密，默认的过期时间是1天。
        String token= TokenUtils.genToken(res);
        res.setToken(token);
        return Result.success(res);
    }

    @PostMapping("/register")
    public Result<?> register(@RequestBody Admin admin) {
        Admin res = adminMapper.selectOne(Wrappers.<Admin>lambdaQuery().eq(Admin::getName, admin.getName()));
        if (res != null) {
            return Result.error("-1", "用户名已存在");
        }
        //密码校验
        if (admin.getPasswd() == null) {
            admin.setPasswd("123456");
        }
        if(admin.getRole()==null) {
            admin.setRole(2);                //默认用户权限
        }
        adminMapper.insert(admin);
        return Result.success();
    }

    @PostMapping
    public Result<?> save(@RequestBody Admin admin) {
        if (admin.getPasswd() == null) {
            admin.setPasswd("123456");
        }
        adminMapper.insert(admin);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody Admin admin) {
        try {
            adminMapper.updateById(admin);
        } catch (Exception e) {
            return Result.error("-1", "错误");
        }
        return Result.success(admin);
    }

    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search) {
        LambdaQueryWrapper<Admin> wrapper = Wrappers.<Admin>lambdaQuery();
        //加上这个条件可以查询出name为空的数据
        if(StrUtil.isNotBlank(search)) {
            wrapper.like(Admin::getName, search);
        }
        return Result.success(adminMapper.selectPage(new Page<>(pageNum, pageSize), wrapper));
    }


    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        adminMapper.deleteById(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<?> getById(@PathVariable Long id) {
        Admin admin=adminMapper.selectById(id);
        admin.setToken(getToken());
        return Result.success(admin);
    }

    /**
     * Excel导出
     * @param response
     * @throws IOException
     */
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws IOException {

        List<Map<String, Object>> list = CollUtil.newArrayList();

        List<Admin> all = adminMapper.selectList(null);
        for (Admin admin : all) {
            Map<String, Object> row1 = new LinkedHashMap<>();
            row1.put("用户名", admin.getName());
            row1.put("昵称", admin.getNickname());
            row1.put("密码", admin.getPasswd());
            row1.put("角色", admin.getRole());
            list.add(row1);
        }

        // 2. 写excel
        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.write(list, true);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("管理员信息", "UTF-8");
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
        List<Admin> saveList=new ArrayList<>();
        for (List<Object> row : lists) {
            Admin admin = new Admin();
            admin.setName(row.get(0).toString());
            admin.setNickname(row.get(1).toString());
            admin.setPasswd(row.get(2).toString());
            admin.setRole(Integer.valueOf(row.get(3).toString()));
            saveList.add(admin);
        }
        for (Admin admin : saveList) {
            adminMapper.insert(admin);
        }
        return Result.success();
    }
}
