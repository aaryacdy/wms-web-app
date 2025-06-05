package com.kathford.wms.repository;

import com.kathford.wms.custom.CategoryRepositoryCustom;
import com.kathford.wms.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long>, CategoryRepositoryCustom {
}
