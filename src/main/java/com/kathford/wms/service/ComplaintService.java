    package com.kathford.wms.service;

    import com.kathford.wms.domain.Complaint;
    import com.kathford.wms.domain.Waste;
    import com.kathford.wms.repository.ComplaintRepository;
    import org.springframework.stereotype.Service;

    import java.util.List;

    @Service
    public record ComplaintService(ComplaintRepository complaintRepository) {
        public boolean saveOrUpdate(Complaint complaint) {
            try {
                complaintRepository.save(complaint);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        public Complaint findById(Long id) {
            return complaintRepository.findById(id).orElse(null);
        }

        public boolean deleteComplaint(Complaint complaint) {
            try {
                complaintRepository.delete(complaint);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        public List<Complaint> findAll() {
            return complaintRepository.findAll();
        }

        public List<Complaint> findAllComplaintByUserId(Long loggedInUser) {
            return complaintRepository.findLoggedInUserComplaint(loggedInUser);
        }

        public Integer countAllComplaint(Long userId) {
            return complaintRepository.countAllComplaint(userId);
        }
    }
