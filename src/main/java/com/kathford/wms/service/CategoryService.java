package com.kathford.wms.service;

import com.kathford.wms.domain.Category;
import com.kathford.wms.payload.response.CategoryResponse;
import com.kathford.wms.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final
    CategoryRepository categoryRepository;

    public boolean saveOrUpdate(Category category) {
        try {
            categoryRepository.save(category);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Category findById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    public boolean delete(Category category) {
        try {
            categoryRepository.delete(category);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Category> findAllCategory() {
        return categoryRepository.findAll();
    }


    public List<CategoryResponse> filterAllCategory() {
        return categoryRepository.filterAllCategory();
    }


}
