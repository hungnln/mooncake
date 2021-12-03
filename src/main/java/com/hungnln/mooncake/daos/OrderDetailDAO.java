package com.hungnln.mooncake.daos;

import com.hungnln.mooncake.dtos.Cake;
import com.hungnln.mooncake.dtos.Order;
import com.hungnln.mooncake.dtos.OrderDetail;
import com.hungnln.mooncake.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class OrderDetailDAO {
    protected Session session;
    SessionFactory factory = HibernateUtils.getSessionFactory();

    public OrderDetailDAO() {
    }

    private void closeConnection() {
        if (session != null) {
            session.close();
        }
    }

    public List<OrderDetail> getAllOrderDetail(Order order) {
        List<OrderDetail> listDetail = null;
        try {
            session = factory.getCurrentSession();
            if (session != null) {
                session.getTransaction().begin();
                String hql = "from OrderDetail o where  o.orderID =:orderID";
                Query query = session.createQuery(hql);
                query.setParameter("orderID",order.getId());
                listDetail =query.list();
                session.getTransaction().commit();

            }
        }catch (Exception e){
            session.getTransaction().rollback();
        }
        finally {
            closeConnection();
        }
        return listDetail;
    }

    public boolean createOrUpdateOrderDetail(OrderDetail order) {
        try {
            session =factory.getCurrentSession();
            if (session != null) {
                session.getTransaction().begin();
                session.saveOrUpdate(order);
                session.getTransaction().commit();
                return true;
            }
        }catch (Exception e){
            session.getTransaction().rollback();
        }
        finally {
            closeConnection();
        }
        return false;
    }
}
