package com.kathford.wms.controller;

import com.kathford.wms.domain.Waste;
import com.kathford.wms.service.AboutService;
import com.kathford.wms.service.AdminService;
import com.kathford.wms.service.WasteService;
import com.kathford.wms.util.Helper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class DashboardController {

    private final AdminService adminService;
    private final AboutService aboutService;
    private final WasteService wasteService;

    @GetMapping(value = "admin/dashboard")
    public String viewDashboard(HttpSession session, Model model,
                                HttpServletRequest request, RedirectAttributes redirectAttributes) {
        //validate logged in status
        if (!Helper.checkAdminLogin(session))
            return Helper.redirectToAdminLogin(redirectAttributes);

        Helper.setTitle(model, "Dashboard");
        model.addAttribute("baseUrl", request.getContextPath());
        model.addAttribute("totalWaste", wasteService.countAllWaste(null));

        return "layout/dashboard/admin-v2/dashboard";
    }

    @GetMapping(value = "collector/dashboard")
    public String viewCollectorDashboard(HttpSession session, Model model,
                                         HttpServletRequest request, RedirectAttributes redirectAttributes) {
        //validate logged in status
        if (!Helper.checkAdminLogin(session))
            return Helper.redirectToAdminLogin(redirectAttributes);

        Helper.setTitle(model, "Dashboard");
        model.addAttribute("baseUrl", request.getContextPath());
        model.addAttribute("totalWaste", wasteService.countAllWaste(null));

        //lets integrate recommendation algorithm for collector

        return "layout/dashboard/collector/dashboard";
    }

    @GetMapping(value = "dashboard")
    public String viewDashboard(Model model, HttpSession session, RedirectAttributes redirectAttributes,
                                HttpServletRequest request) {

        //validate user login status
        if (!Helper.checkUserLogin(session))
            return Helper.redirectToUserLogin(redirectAttributes);

        log.info(" User dashboard ");
        Helper.setTitle(model, "Dashboard");
        model.addAttribute("baseUrl", request.getContextPath());
        var total = wasteService.countAllWaste(Helper.getLoggedInUserId(session));
        System.out.println(total + " User Total Waste Created " + total);
        model.addAttribute("totalRequest", total);

        return "layout/dashboard/user/dashboard";
    }

    @GetMapping(value = "contact-us")
    public String viewContactPage(HttpServletRequest request,
                                  Model model) {

        Helper.setTitle(model, "Contact");
        model.addAttribute("baseUrl", request.getContextPath());
        model.addAttribute("aboutUs", aboutService.findById(10l));

        return "user/contact";
    }

    @GetMapping(value = "all-waste")
    public String viewListingWaste(HttpSession session, Model model, RedirectAttributes redirectAttributes,
                                   HttpServletRequest httpServletRequest) {
        //validate user login
        if (!Helper.checkAdminLogin(session))
            return Helper.redirectToAdminLogin(redirectAttributes);

        Helper.setTitle(model, "All Wastes");
        model.addAttribute("baseUrl", httpServletRequest.getContextPath());
        model.addAttribute("wastes", wasteService.findAllWasteByUserId(null));
        return "admin/all-waste";
    }

    @GetMapping(value = "collector-waste")
    public String viewListingCollectorWaste(HttpSession session, Model model, RedirectAttributes redirectAttributes,
                                            HttpServletRequest httpServletRequest) {
        //validate user login
        if (!Helper.checkAdminLogin(session))
            return Helper.redirectToCollectorLogin(redirectAttributes);

        Helper.setTitle(model, "All Wastes");
        model.addAttribute("baseUrl", httpServletRequest.getContextPath());
        model.addAttribute("wastes", wasteService.findAllWasteByUserId(null));
        return "collector/collector-waste";
    }

    @GetMapping(value = "terms-and-condition")
    public String termsAndCondition(
            Model model
    ) {

        model.addAttribute("titlt", "Terms and Condition");

        return "terms-condition/setting";
    }

    @GetMapping(value = "recommend-collector")
    public String recommendUserToCollectorToCollectWaste(
            Model model, HttpServletRequest request
    ) {

        System.out.println(" I RECOMMEND MOST REQUESTED USER BASED ON HIGHLY REQUESTED USER");

        var allCollectors = adminService.findAllByRole("Collector");

        if (!Helper.isNullOrEmpty(allCollectors))
            System.out.println(" All Collector " + allCollectors.size());

        var wasteMap = new HashMap<Long, List<Waste>>();
        var allWastes = wasteService.findAll();
        if (!Helper.isNullOrEmpty(allWastes)) {

            for (var waste : allWastes) {
                var status = waste.getStatus();

                if (Helper.isNullOrEmpty(status))
                    continue;

                if (status.equalsIgnoreCase("Approved")) {
                    var collectorId = waste.getApprovedBy() != null ? waste.getApprovedBy().getId() : null;
                    if (Helper.isNotNUllAndGreaterThanZero(collectorId)) {
                        var wastes = wasteMap.getOrDefault(collectorId, new ArrayList<>());
                        wastes.add(waste);
                        wasteMap.put(collectorId, wastes);
                    }
                }
            }
        }

        //all collectors to bound
        for (var collector : allCollectors) {
            var wastes = wasteMap.get(collector.getId());
            var userMap = new HashMap<Long, Integer>();
            if (!Helper.isNullOrEmpty(wastes)) {

                for (var user : wastes) {
                    var userId = user.getCreatedBy().getId();
                    if (userMap.containsKey(userId)) {
                        var total = userMap.get(userId);
                        if (total != null && total > 0)
                            userMap.put(userId, total + 1);
                    } else {
                        userMap.put(userId, 1);
                    }
                }

                var allCount = 0;
                var nameOfUser = "";
                for (var user : wastes) {
                    var userId = user.getCreatedBy().getId();

                    var count = userMap.get(userId);

                    if (count > allCount) {
                        nameOfUser = user.getCreatedBy().getName();
                        allCount = count;
                    }
                }

                collector.setNameOfUser(nameOfUser);
                collector.setCount(wastes.size());
            }
        }

        model.addAttribute("recommends", allCollectors);
        model.addAttribute("baseUrl", request.getContextPath());
        model.addAttribute("title", "Recommend Collector");

        return "recommend/collector";

    }
}