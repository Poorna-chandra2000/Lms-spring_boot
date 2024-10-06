package com.imperion.learn.LMS.Services;

import com.imperion.learn.LMS.PayLoad.CategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto createNewCategory(CategoryDto categoryDto);

    List<CategoryDto> getAllCategory();
}
