package com.kathford.wms.repositoryimpl;

import com.kathford.wms.custom.CategoryRepositoryCustom;
import com.kathford.wms.payload.response.CategoryResponse;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class CategoryRepositoryImpl implements CategoryRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<CategoryResponse> filterAllCategory() {
        var queryString = """
                select distinct id, name, price, description as remarks, icon, status from tbl_category
                """;
        Session session = (Session) entityManager.getDelegate();
        SQLQuery query = (SQLQuery) session.createSQLQuery(queryString)
                .setResultTransformer(Transformers.aliasToBean(CategoryResponse.class));

        return query.list();
    }
}
