package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Book;
import org.apache.ibatis.annotations.Select;
import com.example.demo.controller.dto.BookCategoryDto;
import org.springframework.stereotype.Repository;
import java.util.List;

public interface BookMapper extends BaseMapper<Book> {
    @Select("select count(id) count, category from book GROUP BY category")
    List<BookCategoryDto> countCategory();
}
