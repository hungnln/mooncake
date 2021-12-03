package com.hungnln.mooncake.daos;

import com.hungnln.mooncake.dtos.User;
import com.hungnln.mooncake.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

public class UserDAO {
    protected Session session;
    SessionFactory factory = HibernateUtils.getSessionFactory();

    public UserDAO() {
    }

    private void closeConnection() {
        if (session != null) {
            session.close();
        }
    }

    public User checkLogin(String userId, String userPassword) {
        User user = null;
        try {
            session = factory.getCurrentSession();
            if (session != null) {
                session.getTransaction().begin();
                String hql = "from User u where u.id=:userId and u.userPassword=:userPassword";
                Query query = session.createQuery(hql);
                query.setParameter("userId", userId);
                query.setParameter("userPassword", userPassword);
                user = (User) query.getSingleResult();
                session.getTransaction().commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            closeConnection();
        }
        return user;
    }

    public User findUser(String userId) {
        User user = null;
        try {
            session = factory.getCurrentSession();
            if (session != null) {
                session.getTransaction().begin();
                String hql = "from User u where u.id=:userId";
                Query query = session.createQuery(hql);
                query.setParameter("userId", userId);
                user = (User) query.getSingleResult();
                session.getTransaction().commit();
            }
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            closeConnection();
        }
        return user;
    }

    public boolean createOrUpdateUser(User user) {
        try {
            session = factory.getCurrentSession();
            if (session != null) {
                session.getTransaction().begin();
                session.saveOrUpdate(user);
                session.getTransaction().commit();
                return true;
            }
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            closeConnection();
        }
        return false;
    }
}
