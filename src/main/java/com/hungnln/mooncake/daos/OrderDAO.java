package com.hungnln.mooncake.daos;

import com.hungnln.mooncake.dtos.Order;
import com.hungnln.mooncake.dtos.User;
import com.hungnln.mooncake.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class OrderDAO {
    protected Session session;
    SessionFactory factory = HibernateUtils.getSessionFactory();

    public OrderDAO() {
    }

    private void closeConnection() {
        if (session != null) {
            session.close();
        }
    }

    public List<Order> getAllOrder() {
        List<Order> listOrders = null;
        try {
            session = factory.getCurrentSession();
            if (session != null) {
                session.getTransaction().begin();
                String hql = "from Order";
                Query query = session.createQuery(hql);
                listOrders = query.list();
                session.getTransaction().commit();
            }
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            closeConnection();
        }
        return listOrders;
    }

    public boolean createOrUpdateOrder(Order order) {
        try {
            session = factory.getCurrentSession();
            if (session != null) {
                session.getTransaction().begin();
                session.saveOrUpdate(order);
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

    public Order getOrderByOrderID(String orderID) {
        Order order = null;
        try {
            session = factory.getCurrentSession();
            if (session != null) {
                session.getTransaction().begin();
                String hql = "from Order o where  o.id=:orderID";
                Query query = session.createQuery(hql);
                query.setParameter("orderID", orderID);
                order = (Order) query.getSingleResult();
                session.getTransaction().commit();
            }
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            closeConnection();
        }
        return order;
    }

    public List<Order> getAllOrderByUserID(User user) {
        List<Order> listOrders = null;
        try {
            session = factory.getCurrentSession();
            if (session != null) {
                session.getTransaction().begin();
                String hql = "from Order o where  o.userID=:userID";
                Query query = session.createQuery(hql);
                query.setParameter("userID", user.getId());
                listOrders = query.list();
                session.getTransaction().commit();
            }
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            closeConnection();
        }
        return listOrders;
    }

    public boolean checkExistOrderID(String orderID) {
        try {
            session = factory.getCurrentSession();
            if (session != null) {
                session.getTransaction().begin();
                String hql = "from Order o where  o.id=:orderID";
                Query query = session.createQuery(hql);
                query.setParameter("orderID", orderID);
                session.getTransaction().commit();
                return query.getSingleResult() == null ? true : false;
            }
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            closeConnection();
        }
        return false;
    }

}
