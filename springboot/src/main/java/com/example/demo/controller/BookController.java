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
import com.example.demo.entity.Book;
import com.example.demo.mapper.BookMapper;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;
import org.apache.poi.hpsf.Decimal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/book")
public class BookController {
    @Resource
    BookMapper bookMapper;

    @GetMapping("/count")
    public Result<?> count() {
        return Result.success(bookMapper.countCategory());
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable("id") Integer id) {
        bookMapper.deleteById(id);
        return Result.success();
    }

    @PostMapping("/deleteBatch")
    public Result<?> deleteBatch(@RequestBody List<Integer> ids) {
        bookMapper.deleteBatchIds(ids);
        return Result.success();
    }

    @PostMapping
    public Result<?> save(@RequestBody Book book) {
        bookMapper.insert(book);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody Book book) {
        bookMapper.updateById(book);
        return Result.success();
    }

    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search) {
        LambdaQueryWrapper<Book> wrapper = Wrappers.<Book>lambdaQuery();
        //加上这个条件可以查询出name为空的数据
        if(StrUtil.isNotBlank(search)) {
            wrapper.like(Book::getName, search);
        }
        return Result.success(bookMapper.selectPage(new Page<>(pageNum, pageSize), wrapper));
    }
    
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = CollUtil.newArrayList();
        List<Book> all = bookMapper.selectList(null);
        for (Book book : all) {
            Map<String, Object> row1 = new LinkedHashMap<>();
            row1.put("名称", book.getName());
            row1.put("单价", book.getPrice());
            row1.put("作者", book.getAuthor());
            row1.put("类别", book.getCategory());
            row1.put("出版时间", book.getCreatetime());
            row1.put("封面", book.getCover());
            row1.put("ISBN", book.getIsbn());
            row1.put("库存", book.getInventory());
            list.add(row1);
        }

        // 2. 写excel
        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.write(list, true);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("图书信息", "UTF-8");
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
        List<Book> saveList=new ArrayList<>();
        for (List<Object> row : lists) {
            Book book = new Book();
            book.setName(row.get(0).toString());
            book.setPrice(new BigDecimal(row.get(1).toString()));
            book.setAuthor(row.get(2).toString());
            book.setCategory(row.get(3).toString());
            /**
             * 闹了半天，是字符串转化为Date出错了，我晕
             * 终于解决掉这个bug了
             * 很棒
             * 预计消耗时间俩小时
             */
            String time=row.get(4).toString();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            book.setCreatetime(format.parse(time));
            //
            book.setCover(row.get(5).toString());
            book.setIsbn(row.get(6).toString());
            book.setInventory(Integer.valueOf(row.get(7).toString()));
            saveList.add(book);
        }
        for (Book book : saveList) {
            bookMapper.insert(book);
        }
        return Result.success();
    }
}
