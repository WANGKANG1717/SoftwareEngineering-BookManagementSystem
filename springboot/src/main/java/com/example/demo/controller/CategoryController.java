package com.example.demo.controller;

import com.example.demo.common.Result;
import com.example.demo.entity.Category;
import com.example.demo.mapper.CategoryMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController  extends BaseController {

    @Resource
    CategoryMapper categoryMapper;

    @PostMapping
    public Result<?> save(@RequestBody Category category) {
        categoryMapper.insert(category);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody Category category) {
        try {
            categoryMapper.updateById(category);
        } catch (Exception e) {
            return Result.error("-1", "错误");
        }
        return Result.success(category);
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        categoryMapper.deleteById(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<?> getById(@PathVariable Long id) {
        return Result.success(categoryMapper.selectById(id));
    }

    @GetMapping
    public Result<?> getAll() {
        //先查询左右的数据
        List<Category> allcategories = categoryMapper.selectList(null);
        return Result.success(loopQuery(null, allcategories));
    }

    private List<Category> loopQuery(Integer pid, List<Category> allcategories) {
        List<Category> categoryList = new ArrayList<>();
        for (Category category : allcategories) {
            if (pid == null) {
                if (category.getPid() == null) {
                    categoryList.add(category);
                    category.setChildren(loopQuery(category.getId(), allcategories));
                }
            } else if (pid.equals(category.getPid())) {
                categoryList.add(category);
                category.setChildren(loopQuery(category.getId(), allcategories));
            }
        }
        return categoryList;
    }
}
