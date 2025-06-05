package com.kathford.wms.repositoryimpl;

import com.kathford.wms.custom.PaymentCustom;
import com.kathford.wms.domain.Payment;
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
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public class PaymentRepositoryImpl implements PaymentCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Payment> findLoggedInCollectorPayment(Long loggedInAdminId) {
        boolean hasUserId = loggedInAdminId != null && loggedInAdminId > 0;
        Session session = (Session) entityManager.getDelegate();
        Criteria criteria = session.createCriteria(Payment.class)
                .addOrder(Order.desc("createdDate"));
        if (hasUserId)
            criteria.add(Restrictions.eq("createdBy.id", loggedInAdminId));

        return criteria.list();
    }

    @Override
    public Integer countAllPayment(Long collectorId) {
        boolean hasUserId = Helper.isNotNUllAndGreaterThanZero(collectorId);
        var queryString = """
                select count(distinct id) as total from tbl_payment
                """;
        if (hasUserId) queryString += "where created_by=:collectorId";

        Session session = (Session) entityManager.getDelegate();
        SQLQuery query = session.createSQLQuery(queryString)
                .addScalar("total", IntegerType.INSTANCE);
        if (hasUserId) query.setParameter("collectorId", collectorId);
        return (Integer) query.uniqueResult();
    }

    @Override
    public boolean setApproveOrRejectPayment(Long id, String status) {
        var queryString = "update tbl_payment set status=:status where id=:id";
        Query query = entityManager.createNativeQuery(queryString)
                .setParameter("id", id)
                .setParameter("status", status);
        try {
            query.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
