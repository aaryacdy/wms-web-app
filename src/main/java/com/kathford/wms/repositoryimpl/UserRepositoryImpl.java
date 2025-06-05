package com.kathford.wms.repositoryimpl;

import com.kathford.wms.custom.UserRepositoryCustom;
import com.kathford.wms.domain.User;
import com.kathford.wms.util.Helper;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.type.LongType;
import org.springframework.jdbc.object.SqlQuery;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

@Repository
@Transactional
public class UserRepositoryImpl implements UserRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public User findByUsername(String username) {
        if (username == null || username.isEmpty())
            return null; // Handle invalid input

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> root = query.from(User.class);
        query.select(root)
                .where(builder.equal(root.get("username"), username));
        try {
            return entityManager.createQuery(query).getSingleResult();
        } catch (NoResultException ex) {
            return null; // Handle the case when no result is found
        }
    }

    @Override
    public User findByEmail(String email) {
        try {
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<User> query = builder.createQuery(User.class);
            Root<User> root = query.from(User.class);
            query.select(root)
                    .where(builder.equal(root.get("email"), email));
            return entityManager.createQuery(query).getSingleResult();
        } catch (NoResultException ex) {
            // Handle the case when no result is found.
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean setUserBlockedOrUnblocked(Long id, boolean isBlocked) {
        var queryString = "update tbl_user set blocked=:isBlocked where id=:id";
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
    public boolean isUsernameAlreadyAvailable(String username) {
        if (Helper.isNullOrEmpty(username)) return true;

        //  select cast(count(id) as integer) as total from tbl_user where username = :username
        var queryString = """
                select count(id) as total from tbl_user where username = :username
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
        var queryString = """
                select count(id) as total from tbl_user where email = :email
                """;

        Session session = (Session) entityManager.getDelegate();
        SQLQuery query = session.createSQLQuery(queryString)
                .addScalar("total", LongType.INSTANCE)
                .setParameter("email", email);

        Long total = (Long) query.uniqueResult();
        return Helper.isNotNUllAndGreaterThanZero(total);
    }
}
