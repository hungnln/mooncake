package com.hungnln.mooncake.daos;

import com.hungnln.mooncake.dtos.Log;
import com.hungnln.mooncake.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class UpdateLogDAO {
    protected Session session;
    SessionFactory factory = HibernateUtils.getSessionFactory();

    public UpdateLogDAO() {
    }

    private void closeConnection() {
        if (session != null) {
            session.close();
        }
    }

    public boolean createOrUpdateLog(Log log) {
        try {
            session = factory.getCurrentSession();
            if (session != null) {
                session.getTransaction().begin();
                session.saveOrUpdate(log);
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
