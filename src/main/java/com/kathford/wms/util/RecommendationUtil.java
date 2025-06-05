package com.kathford.wms.util;

import com.kathford.wms.service.AdminService;
import com.kathford.wms.service.UserService;
import com.kathford.wms.service.WasteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecommendationUtil {

    private final WasteService wasteService;
    private final UserService userService;
    private final AdminService adminService;

}
