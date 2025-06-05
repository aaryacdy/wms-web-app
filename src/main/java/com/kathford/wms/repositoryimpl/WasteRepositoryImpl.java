package com.kathford.wms.repositoryimpl;

import com.kathford.wms.custom.WasteRepositoryCustom;
import com.kathford.wms.domain.Waste;
import com.kathford.wms.util.Helper;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.IntegerType;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Repository
public class WasteRepositoryImpl implements WasteRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Waste> findLoggedInUserWaste(Long loggedInUserId) {
        boolean hasUserId = loggedInUserId != null && loggedInUserId > 0;
        Session session = (Session) entityManager.getDelegate();
        Criteria criteria = session.createCriteria(Waste.class)
                .addOrder(Order.desc("createdDate"));
        if (hasUserId)
            criteria.add(Restrictions.eq("createdBy.id", loggedInUserId));

        return criteria.list();
    }

    @Override
    public List<Waste> filterWasteAsRequested(String status, String fromDate, String toDate, Long loggedInUser) {

        boolean hasUserId = loggedInUser != null && loggedInUser > 0;
        boolean hasStatus = !Helper.isNullOrEmpty(status);
        boolean hasDateRange = !Helper.isNullOrEmpty(fromDate) && !Helper.isNullOrEmpty(toDate);
        Session session = (Session) entityManager.getDelegate();
        Criteria criteria = session.createCriteria(Waste.class);
//                .addOrder(Order.desc("createdDate"));

        if (hasStatus) criteria.add(Restrictions.eq("status", status));

        if (hasDateRange)
            criteria.add(Restrictions.between("createdDate", fromDate, toDate));

        if (hasUserId)
            criteria.add(Restrictions.eq("createdBy.id", loggedInUser));

        return criteria.list();

    }

    @Override
    public Integer countAllWaste(Long userId) {
        boolean hasUserId = Helper.isNotNUllAndGreaterThanZero(userId);
        var queryString = """
                select count(distinct id) as total from tbl_waste
                """;
        if (hasUserId) queryString += "where created_by=:userId";

        Session session = (Session) entityManager.getDelegate();
        SQLQuery query = session.createSQLQuery(queryString)
                .addScalar("total", IntegerType.INSTANCE);
        if (hasUserId) query.setParameter("userId", userId);
        return (Integer) query.uniqueResult();
    }

    @Override
    public List<Waste> filterWasteByStatusAndAddress(String status, String address, Long loggedInUserId, boolean showPaidWastes) {
        boolean hasStatus = !Helper.isNullOrEmpty(status);
        var hasAddress = !Helper.isNullOrEmpty(address);
        var hasUserId = Helper.isNotNUllAndGreaterThanZero(loggedInUserId);

        var queryString = """
                select * from tbl_waste w
                where w.status=:status
                """;

        if (hasUserId) {
            queryString += "and w.created_by = :userId ";
        }

        if (!showPaidWastes)
            queryString += "and w.id not in (select waste_id from tbl_payment where waste_id = w.id)";

        if (hasAddress) queryString += "and address=:address";

        Session session = (Session) entityManager.getDelegate();
        SQLQuery query = session.createSQLQuery(queryString)
                .addEntity(Waste.class);
        if (hasStatus) query.setParameter("status", status);
        if (hasAddress) query.setParameter("address", address);
        if (hasUserId) query.setParameter("userId", loggedInUserId);

        return query.list();
    }
}
