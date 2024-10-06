package com.imperion.learn.LMS.Services;

import com.imperion.learn.LMS.Entities.Category;
import com.imperion.learn.LMS.PayLoad.CategoryDto;
import com.imperion.learn.LMS.Repositories.CategoryRepositoy;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl  implements CategoryService{
    private final ModelMapper modelMapper;
    private final CategoryRepositoy categoryRepositoy;

    @Override
    public CategoryDto createNewCategory(CategoryDto categoryDto) {
        Category categoryEntity=modelMapper.map(categoryDto,Category.class);

        Category saveCategory=categoryRepositoy.save(categoryEntity);

        return modelMapper.map(saveCategory,CategoryDto.class);
    }


    @Override
    public List<CategoryDto> getAllCategory(){
        List<Category> categoryList=categoryRepositoy.findAll();

        return categoryList
                .stream()
                .map(categ->modelMapper.map(categ,CategoryDto.class))
                .collect(Collectors.toList());
    }
}
