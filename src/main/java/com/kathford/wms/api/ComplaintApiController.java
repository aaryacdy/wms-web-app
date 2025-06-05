package com.kathford.wms.api;

import com.kathford.wms.domain.BaseModel;
import com.kathford.wms.domain.Category;
import com.kathford.wms.domain.Complaint;
import com.kathford.wms.domain.User;
import com.kathford.wms.service.ComplaintService;
import com.kathford.wms.service.UserService;
import com.kathford.wms.util.Helper;
import com.kathford.wms.util.Response;
import com.kathford.wms.util.Strings;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "api/complaint")
@RequiredArgsConstructor
public class ComplaintApiController extends BaseModel {

    private UserService userService;
    private final ComplaintService complaintService;

    @PostMapping(value = "save-update")
    public ResponseEntity<Response> saveComplaint(
            @RequestParam(required = false) Long userId,
            @ModelAttribute Complaint complaint
    ) {

        var success = false;
        String message = null;
        try {

//            User user = userService.findById(userId);
//            complaint.setCreatedBy(new User(54l));
            complaint.setCreatedBy(new User(1l));
            complaint.setCreatedDate(new Date());
            if (complaintService.saveOrUpdate(complaint)) {
                success = true;
                message = Strings.SAVED_SUCCESSFULLY;
            } else message = Strings.FAILED_TO_SAVE;
        } catch (Exception e) {
            e.printStackTrace();
            message = Strings.SOMETHING_WENT_WRONG;
        }

        return Helper.getResponseEntity(null, message, success);
    }

   /* @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEntity(@PathVariable Long id) {
        MyEntity entity = entityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id));

        entityRepository.delete(entity);

        return ResponseEntity.ok().build();
    }*/

    @DeleteMapping(value = "{id}/delete")
    public ResponseEntity<Response> deleteComplaint(
            @PathVariable Long id
    ) {
        var success = false;
        String message = null;
        try {

            var complaint = complaintService.findById(id);
            if (complaintService.deleteComplaint(complaint)) {
                success = true;
                message = Strings.DELETED_SUCCESSFULLY;
            } else message = Strings.FAILED_TO_DELETE;
        } catch (Exception e) {
            e.printStackTrace();
            message = Strings.SOMETHING_WENT_WRONG;
        }

        return Helper.getResponseEntity(null, message, success);
    }

    @DeleteMapping(value = "delete")
    public ResponseEntity<Response> deleteComplaintV2(
            @RequestParam Long id
    ) {
        var success = false;
        String message = null;
        try {

            var complaint = complaintService.findById(id);
            if (complaintService.deleteComplaint(complaint)) {
                success = true;
                message = Strings.DELETED_SUCCESSFULLY;
            } else message = Strings.FAILED_TO_DELETE;
        } catch (Exception e) {
            e.printStackTrace();
            message = Strings.SOMETHING_WENT_WRONG;
        }

        return Helper.getResponseEntity(null, message, success);
    }


    @PostMapping(value = "{id}")
    public ResponseEntity<Response> viewComplaint(
            @PathVariable Long id
    ) {

        var success = false;
        String message = null;
        Complaint complaint = null;
        try {
            complaint = complaintService.findById(id);
            System.out.println(complaint);
            System.out.println(id + " id ");
            if (complaint != null)
                success = true;
        } catch (Exception e) {
            e.printStackTrace();
            message = Strings.SOMETHING_WENT_WRONG;
        }

        return Helper.getResponseEntity(complaint, message, success);
    }

    @PostMapping(value = "view")
    public ResponseEntity<Response> viewComplaintV2(
            @RequestParam Long id
    ) {

        var success = false;
        String message = null;
        Complaint complaint = null;
        try {
            complaint = complaintService.findById(id);
            if (complaint != null)
                success = true;
        } catch (Exception e) {
            e.printStackTrace();
            message = Strings.SOMETHING_WENT_WRONG;
        }

        return Helper.getResponseEntity(complaint, message, success);
    }

    @PostMapping(value = "findall")
    public ResponseEntity<Response> viewAllComplaint() {
        var success = false;
        String message = null;
        List<Complaint> complaints = null;
        try {

            complaints = complaintService.findAll();
            if (!Helper.isNullOrEmpty(complaints))
                success = true;
        } catch (Exception e) {
            e.printStackTrace();
            message = Strings.SOMETHING_WENT_WRONG;
        }

        return Helper.getResponseEntity(complaints, message, success);
    }

    @GetMapping(value = "findall-v2")
    public List<Complaint> viewAllComplaints() {
        return complaintService.findAll();
    }

}
