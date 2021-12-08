package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.List;

@TableName("category")
@Data
public class Category {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private Integer pid;

    @TableField(exist = false)
    private List<Category> children;
}
