package com.kathford.wms.repository;

import com.kathford.wms.custom.WasteRepositoryCustom;
import com.kathford.wms.domain.Waste;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WasteRepository extends JpaRepository<Waste, Long>, WasteRepositoryCustom {
}
