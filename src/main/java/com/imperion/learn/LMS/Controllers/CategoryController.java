package com.imperion.learn.LMS.Controllers;

import com.imperion.learn.LMS.PayLoad.CategoryDto;
import com.imperion.learn.LMS.Services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/CreateCategory")
    ResponseEntity<CategoryDto> createNewCategory(@RequestBody CategoryDto categoryDto){

        return ResponseEntity.ok(categoryService.createNewCategory(categoryDto));
    }

    @GetMapping("/allCategory")
    ResponseEntity<List<CategoryDto>> getAllCategory(){
        return ResponseEntity.ok(categoryService.getAllCategory());
    }


}
