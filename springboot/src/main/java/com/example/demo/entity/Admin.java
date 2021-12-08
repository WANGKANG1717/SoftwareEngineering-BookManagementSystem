package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("admin")
@Data
public class Admin {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private String nickname;
    private String passwd;
    private Integer role;
    private String headpic;
    //
    @TableField(exist = false)
    private String token;
}
