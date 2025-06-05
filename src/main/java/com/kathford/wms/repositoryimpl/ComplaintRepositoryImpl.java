package com.kathford.wms.repositoryimpl;

import com.kathford.wms.custom.ComplaintCustom;
import com.kathford.wms.domain.Complaint;
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
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public class ComplaintRepositoryImpl implements ComplaintCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Complaint> findLoggedInUserComplaint(Long loggedInUserId) {
        boolean hasUserId = loggedInUserId != null && loggedInUserId > 0;
        Session session = (Session) entityManager.getDelegate();
        Criteria criteria = session.createCriteria(Complaint.class)
                .addOrder(Order.desc("createdDate"));
        if (hasUserId)
            criteria.add(Restrictions.eq("createdBy.id", loggedInUserId));

        return criteria.list();
    }

    @Override
    public Integer countAllComplaint(Long userId) {
        boolean hasUserId = Helper.isNotNUllAndGreaterThanZero(userId);
        var queryString = """
                select count(distinct id) as total from tbl_complaint
                """;
        if (hasUserId) queryString += "where created_by=:userId";

        Session session = (Session) entityManager.getDelegate();
        SQLQuery query = session.createSQLQuery(queryString)
                .addScalar("total", IntegerType.INSTANCE);
        if (hasUserId) query.setParameter("userId", userId);
        return (Integer) query.uniqueResult();
    }
}
