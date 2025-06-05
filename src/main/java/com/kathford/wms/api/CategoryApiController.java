package com.kathford.wms.api;

import com.kathford.wms.domain.Admin;
import com.kathford.wms.domain.Category;
import com.kathford.wms.payload.response.CategoryResponse;
import com.kathford.wms.service.CategoryService;
import com.kathford.wms.util.Helper;
import com.kathford.wms.util.Response;
import com.kathford.wms.util.Strings;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/category")
public class CategoryApiController {

    private final CategoryService categoryService;

    @GetMapping(value = "findall")
    public ResponseEntity<Response> getAllCategories() {

        var success = false;
        String messsage = null;
        List<CategoryResponse> categories = null;

        try {
            categories = categoryService.filterAllCategory();
            if (!Helper.isNullOrEmpty(categories)){
                categories.forEach(category -> {
                    category.setDescription(category.getRemarks());
                });
                success = true;

                for(var a : categories)  {
                    System.out.println(a.getRemarks() + " remarks ");
                    System.out.println(a.getDescription() + " description ");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            messsage = Strings.SOMETHING_WENT_WRONG;
        }

        return Helper.getResponseEntity(categories, messsage, success);
    }

    @PostMapping(value = "/save-update")
    public ResponseEntity<Response> saveCategory(
            @ModelAttribute Category category,
            @RequestParam(required = false) MultipartFile iconPic,
            @RequestParam(required = false) Long adminId
    ) {
        String message = null;
        var success = false;

        try {

            if (iconPic != null && iconPic.getSize() > 0) {
                String postImage = System.currentTimeMillis() + "_" + UUID.randomUUID() + "." + FilenameUtils.getExtension(iconPic.getOriginalFilename());
                iconPic.transferTo(new File(Strings.FILE_UPLOAD_BASE_PATH + "category/" + postImage));
                category.setIcon(postImage);
            }

            category.setCreatedDate(Helper.getCurrentDateTime());
            category.setStatus(true);
            category.setCreatedBy(adminId != null ? new Admin(adminId) : new Admin(1L));
            if (categoryService.saveOrUpdate(category)) {
                message = Strings.SAVED_SUCCESSFULLY;
                success = true;
            } else message = Strings.SOMETHING_WENT_WRONG;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return Helper.getResponseEntity(null, message, success);
    }

    @DeleteMapping(value = "delete")
    public ResponseEntity<Response> deleteCategory(
            @RequestParam Long id
    ) {
        var success = false;
        String message = null;
        try {

            var category = categoryService.findById(id);
            if (categoryService.delete(category)) {
                success = true;
                message = Strings.DELETED_SUCCESSFULLY;
            } else message = Strings.FAILED_TO_DELETE;
        } catch (Exception e) {
            e.printStackTrace();
            message = Strings.SOMETHING_WENT_WRONG;
        }

        return Helper.getResponseEntity(null, message, success);
    }


    @GetMapping(value = "view")
    public ResponseEntity<Response> viewCategory(
            @RequestParam Long id
    ) {

        var success = false;
        String message = null;
        Category category = null;
        try {
            category = categoryService.findById(id);
            if (category != null)
                success = true;
        } catch (Exception e) {
            e.printStackTrace();
            message = Strings.SOMETHING_WENT_WRONG;
        }

        return Helper.getResponseEntity(category, message, success);
    }
}
