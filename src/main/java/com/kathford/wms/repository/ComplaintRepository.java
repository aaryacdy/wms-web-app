package com.kathford.wms.repository;

import com.kathford.wms.custom.ComplaintCustom;
import com.kathford.wms.domain.Complaint;
import com.kathford.wms.service.ComplaintService;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComplaintRepository extends JpaRepository<Complaint, Long>, ComplaintCustom {
}
