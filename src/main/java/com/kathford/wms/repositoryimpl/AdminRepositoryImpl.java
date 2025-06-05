package com.kathford.wms.repositoryimpl;

import com.kathford.wms.custom.AdminRepositoryCustom;
import com.kathford.wms.domain.Admin;
import com.kathford.wms.domain.Waste;
import com.kathford.wms.util.Helper;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.LongType;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class AdminRepositoryImpl implements AdminRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Admin findAdminByUsername(String username) {
        try {
          /*  CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Admin> query = builder.createQuery(Admin.class);
            Root<Admin> adminRoot = query.from(Admin.class);
            query.select(adminRoot)
                    .where(builder.equal(adminRoot.get("username"), username));
            return entityManager.createQuery(query).getSingleResult();*/

            Session session = (Session) entityManager.getDelegate();
            Criteria criteria = session.createCriteria(Admin.class)
                    .add(Restrictions.eq("username", username));

            return (Admin) criteria.uniqueResult();

        } catch (NoResultException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Admin findAdminByUserRole(String role) {
        try {
           /* CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Admin> query = builder.createQuery(Admin.class);
            Root<Admin> root = query.from(Admin.class);
            query.select(root)
                    .where(builder.equal(root.get("role"), role));
            return entityManager.createQuery(query).getSingleResult();*/
            Session session = (Session) entityManager.getDelegate();
            Criteria criteria = session.createCriteria(Admin.class)
                    .add(Restrictions.eq("role", role));
            return (Admin) criteria.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean isUsernameAlreadyAvailable(String username) {
        if (Helper.isNullOrEmpty(username)) return true;

        var queryString = """
                select count(id) as total from tbl_admin where username = :username
                """;

        Session session = (Session) entityManager.getDelegate();
        SQLQuery query = session.createSQLQuery(queryString)
                .addScalar("total", LongType.INSTANCE)
                .setParameter("username", username);

        Long total = (Long) query.uniqueResult();
        return Helper.isNotNUllAndGreaterThanZero(total);
    }

    @Override
    public boolean isEmailAlreadyAvailable(String email) {
        if (Helper.isNullOrEmpty(email)) return true;

        // select cast(count(id) as integer) as total from tbl_admin where email = :email
        var queryString = """
                select count(id) as total from tbl_admin where email = :email
                """;
        Session session = (Session) entityManager.getDelegate();
        SQLQuery query = session.createSQLQuery(queryString)
                .addScalar("total", LongType.INSTANCE)
                .setParameter("email", email);

        Long total = (Long) query.uniqueResult();
        return Helper.isNotNUllAndGreaterThanZero(total);
    }

    @Override
    public boolean setAdminBlockedOrUnblocked(Long id, boolean isBlocked) {
        var queryString = "update tbl_admin set blocked=:isBlocked where id=:id";
        Query query = entityManager.createNativeQuery(queryString)
                .setParameter("id", id)
                .setParameter("isBlocked", isBlocked);
        try {
            query.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Admin> findAllByRole(String role) {
        Session session = (Session) entityManager.getDelegate();
        Criteria criteria = session.createCriteria(Admin.class)
                .addOrder(Order.desc("createdDate"))
                .add(Restrictions.eq("role", role));
        return criteria.list();
    }

    @Override
    public Admin findByUsernameAndRole(String username, String role) {
        try {

            Session session = (Session) entityManager.getDelegate();
            Criteria criteria = session.createCriteria(Admin.class)
                    .add(Restrictions.eq("username", username))
                    .add(Restrictions.eq("role", role));
            return (Admin) criteria.uniqueResult();

        } catch (NoResultException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Admin findByEmailAndRole(String email, String role) {
        try {

            Session session = (Session) entityManager.getDelegate();
            Criteria criteria = session.createCriteria(Admin.class)
                    .add(Restrictions.eq("email", email))
                    .add(Restrictions.eq("role", role));
            return (Admin) criteria.uniqueResult();

        } catch (NoResultException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean isUsernameAlreadyAvailable(String username, String role) {
        if (Helper.isNullOrEmpty(username)) return true;

        var queryString = """
                select count(id) as total from tbl_admin where username = :username and role = :role
                """;

        Session session = (Session) entityManager.getDelegate();
        SQLQuery query = session.createSQLQuery(queryString)
                .addScalar("total", LongType.INSTANCE)
                .setParameter("role", role)
                .setParameter("username", username);

        Long total = (Long) query.uniqueResult();
        return Helper.isNotNUllAndGreaterThanZero(total);
    }

    @Override
    public boolean isEmailAlreadyAvailable(String email, String role) {
        if (Helper.isNullOrEmpty(email)) return true;

        // select cast(count(id) as integer) as total from tbl_admin where email = :email
        var queryString = """
                select count(id) as total from tbl_admin where email = :email and role = :role
                """;
        Session session = (Session) entityManager.getDelegate();
        SQLQuery query = session.createSQLQuery(queryString)
                .addScalar("total", LongType.INSTANCE)
                .setParameter("role", role)
                .setParameter("email", email);

        Long total = (Long) query.uniqueResult();
        return Helper.isNotNUllAndGreaterThanZero(total);
    }
}