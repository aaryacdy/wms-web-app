package com.kathford.wms.custom;

import com.kathford.wms.payload.response.CategoryResponse;

import java.util.List;

public interface CategoryRepositoryCustom {

    List<CategoryResponse> filterAllCategory();

}
