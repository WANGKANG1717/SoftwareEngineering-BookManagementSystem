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
import com.example.demo.entity.Order;
import com.example.demo.entity.User;
import com.example.demo.mapper.OrderMapper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Resource
    OrderMapper orderMapper;

    //随机生成16位订单号
    public static String getRandomNumber(int length){
        String str="0123456789";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
            int number=random.nextInt(10);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable("id") Integer id) {
        orderMapper.deleteById(id);
        return Result.success();
    }

    @PostMapping
    public Result<?> save(@RequestBody Order order) {
        order.setPayTime(null);
        order.setNumber(getRandomNumber(16));
        orderMapper.insert(order);
        return Result.success();
    }
    @PutMapping
    public Result<?> update(@RequestBody Order order) {
        //订单一旦提交支付，便不能再次改动
        //所以这里要进行判断
        if(order.getState()==1 && order.getPayTime()==null){
            order.setPayTime(new Date());
        }
        orderMapper.updateById(order);
        return Result.success();
    }

    @PostMapping("/deleteBatch")
    public Result<?> deleteBatch(@RequestBody List<Integer> ids) {
        orderMapper.deleteBatchIds(ids);
        return Result.success();
    }

    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search) {
        LambdaQueryWrapper<Order> wrapper = Wrappers.<Order>lambdaQuery();
        //加上这个条件可以查询出name为空的数据
//        System.out.println(orderMapper.selectById("1"));
        if(StrUtil.isNotBlank(search)) {
            wrapper.like(Order::getNumber, search);
        }
        return Result.success(orderMapper.selectPage(new Page<>(pageNum, pageSize), wrapper));
    }

    /**
     * Excel导出
     * @param response
     * @throws IOException
     */
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = CollUtil.newArrayList();
        List<Order> all = orderMapper.selectList(null);
        for (Order order : all) {
            Map<String, Object> row1 = new LinkedHashMap<>();
            row1.put("订单号", order.getNumber());
            row1.put("书籍id", order.getBookId());
            row1.put("书籍名", order.getBookName());
            row1.put("用户id", order.getUserId());
            row1.put("用户名", order.getUserName());
            row1.put("总价", order.getTotalPrice());
            row1.put("实付", order.getPayPrice());
            row1.put("折扣", order.getDiscount());
            row1.put("运费", order.getTransportPrice());
            row1.put("创建时间", order.getCreateTime());
            row1.put("支付时间", order.getPayTime());
            row1.put("支付状态", order.getState());
            list.add(row1);
        }

        // 2. 写excel
        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.write(list, true);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("订单信息", "UTF-8");
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
        List<List<Object>> lists= ExcelUtil.getReader(inputStream).read(1);
        List<Order> saveList=new ArrayList<>();
        for (List<Object> row : lists) {
            Order order = new Order();
            order.setNumber(row.get(0).toString());
            order.setBookId(Integer.valueOf(row.get(1).toString()));
            order.setBookName(row.get(2).toString());
            order.setUserId(Integer.valueOf(row.get(3).toString()));
            order.setUserName(row.get(4).toString());
            order.setTotalPrice(new BigDecimal(row.get(5).toString()));
            order.setPayPrice(new BigDecimal(row.get(6).toString()));
            order.setDiscount(new BigDecimal(row.get(7).toString()));
            order.setTransportPrice(new BigDecimal(row.get(8).toString()));
            //
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            order.setCreateTime(format.parse(row.get(9).toString()));
            //
            order.setState(Integer.valueOf(row.get(11).toString()));
            //因为可能订单未支付，所以需要判断一下，否则会出现错误
            if(order.getState().equals(1)) {
                order.setPayTime(format.parse(row.get(10).toString()));
            }
            saveList.add(order);
        }
        for (Order order : saveList) {
            orderMapper.insert(order);
        }
        return Result.success();
    }

}
