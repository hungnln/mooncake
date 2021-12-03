package com.hungnln.mooncake.daos;

import com.hungnln.mooncake.dtos.Category;
import com.hungnln.mooncake.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class CategoryDAO {
    protected Session session;
    SessionFactory factory = HibernateUtils.getSessionFactory();

    public CategoryDAO() {
    }


    private void closeConnection() {
        if (session != null) {
            session.close();
        }
    }

    public List<Category> getAllCategory() {
        List<Category> listCategory = null;
        try {
            session = factory.getCurrentSession();
            if (session != null) {
                session.getTransaction().begin();
                String hql = "from Category";
                Query query = session.createQuery(hql);
                listCategory = query.list();
                session.getTransaction().commit();
            }
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            closeConnection();
        }
        return listCategory;
    }

    public Category getCategoryByID(String categoryID) {
        Category category = null;
        try {
            session = factory.getCurrentSession();
            if (session != null) {
                session.getTransaction().begin();
                String hql = "from Category c where c.id=:categoryID";
                Query query = session.createQuery(hql);
                query.setParameter("categoryID", categoryID);
                category = (Category) query.getSingleResult();
                session.getTransaction().commit();
            }
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            closeConnection();
        }
        return category;

    }
}
