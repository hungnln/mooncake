package com.hungnln.mooncake.daos;

import com.hungnln.mooncake.dtos.Cake;
import com.hungnln.mooncake.dtos.Category;
import com.hungnln.mooncake.utils.HibernateUtils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class CakeDAO {

    protected Session session;
    SessionFactory factory = HibernateUtils.getSessionFactory();

    public CakeDAO() {
    }

    private void closeConnection() {
        if (session != null) {
            session.close();
        }
    }

    public List<Cake> getAllCake(int size, int index) {
        List<Cake> listCakes = null;
        try {
            session = factory.getCurrentSession();
            if (session != null) {
                session.getTransaction().begin();
                String hql = "from Cake c order by c.expirationDate desc ";
                Query query = session.createQuery(hql);
                query.setFirstResult(index * size - size);
                query.setMaxResults(size);
                listCakes = query.list();
                session.getTransaction().commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            closeConnection();
        }
        return listCakes;
    }

    public List<Cake> getAllCakeByCategorySortByRange(Category category, int min, int max, int size, int index) {
        List<Cake> listCakes = null;
        try {
            session = factory.getCurrentSession();
            if (session != null) {
                session.getTransaction().begin();
                String hql = "from Cake o where o.category=:category and o.cakePrice between :min and :max order by o.expirationDate desc  ";
                Query query = session.createQuery(hql);
                query.setParameter("category", category);
                query.setParameter("min", min);
                query.setParameter("max", max);
                query.setFirstResult(index * size - size);
                query.setMaxResults(size);
                listCakes = query.getResultList();
                session.getTransaction().commit();

            }
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            closeConnection();
        }
        return listCakes;
    }

    public List<Cake> getAllCakeSortByPriceRange(int min, int max, int size, int index) {
        List<Cake> listCakes = null;
        try {
            session = factory.getCurrentSession();
            if (session != null) {
                session.getTransaction().begin();
                String hql = "from Cake o  where o.cakePrice between :min and :max order by o.expirationDate desc";
                Query query = session.createQuery(hql);
                query.setParameter("min", min);
                query.setParameter("max", max);
                query.setFirstResult(index * size - size);
                query.setMaxResults(size);
                listCakes = query.list();
                session.getTransaction().commit();

            }
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            closeConnection();
        }
        return listCakes;
    }

    public List<Cake> searchCakesByNameSortByPriceRange(String cakeName, int min, int max, int size, int index) {
        List<Cake> listCakes = null;
        try {
            session = factory.getCurrentSession();
            if (session != null) {
                session.getTransaction().begin();
                String hql = "from Cake o where o.cakeName like :cakeName and o.cakePrice between :min and :max order by o.expirationDate desc  ";
                Query query = session.createQuery(hql);
                query.setParameter("cakeName", "%"+cakeName+"%");
                query.setParameter("min", min);
                query.setParameter("max", max);
                query.setFirstResult(index * size - size);
                query.setMaxResults(size);
                listCakes = query.list();
                session.getTransaction().commit();

            }
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            closeConnection();
        }
        return listCakes;
    }

    public List<Cake> searchCakesByNameByCategorySortByPriceRange(Category category, String cakeName, int min, int max, int size, int index) {
        List<Cake> listCakes = null;
        try {
            session = factory.getCurrentSession();
            if (session != null) {
                session.getTransaction().begin();
                String hql = "from Cake o  where o.category=:category and o.cakeName like :cakeName and o.cakePrice between :min and :max  order by o.expirationDate desc ";
                Query query = session.createQuery(hql);
                query.setParameter("category", category);
                query.setParameter("cakeName", "%"+cakeName+"%");
                query.setParameter("min", min);
                query.setParameter("max", max);
                query.setFirstResult(index * size - size);
                query.setMaxResults(size);
                listCakes = query.getResultList();
                session.getTransaction().commit();

            }
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            closeConnection();
        }
        return listCakes;
    }

    public boolean createOrUpdateCake(Cake cake){
        try {
            session = factory.getCurrentSession();
            if (session != null) {
                session.getTransaction().begin();
                session.saveOrUpdate(cake);
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

    public boolean checkExistCakeID(String cakeID) {
        try {
            session = factory.getCurrentSession();
            if (session != null) {
                session.getTransaction().begin();
                String hql = "from Cake c where c.cakeID=:cakeID";
                Query query = session.createQuery(hql);
                query.setParameter("cakeID", cakeID);
                session.getTransaction().commit();
                return query.getSingleResult() != null;
            }
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            closeConnection();
        }
        return false;
    }

    public Cake getCakeByID(String cakeID) {
        Cake cake = null;
        try {
            session = factory.getCurrentSession();
            if (session != null) {
                session.getTransaction().begin();
                String hql = "from Cake c where c.cakeID=:cakeID";
                Query query = session.createQuery(hql);
                query.setParameter("cakeID", cakeID);
                cake = (Cake) query.getSingleResult();
                session.getTransaction().commit();
            }
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            closeConnection();
        }
        return cake;
    }

    public int countSearchByNameSortByRange(String cakeName, int min, int max) {
        try {
            session = factory.getCurrentSession();
            if (session != null) {
                session.getTransaction().begin();
                String hql = "Select count(*) from Cake c where c.cakeName like :cakeName  and c.cakePrice between :min and :max";
                Query query = session.createQuery(hql);
                query.setParameter("cakeName", "%" + cakeName + "%");
                query.setParameter("min", min);
                query.setParameter("max", max);
                Number count = (Number) query.list().get(0);
                session.getTransaction().commit();
                return count.intValue();
            }
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            closeConnection();
        }
        return 0;
    }

    public int countSearchByNameByCategorySortByRange(String cakeName, Category category, int min, int max) {
        try {
            session = factory.getCurrentSession();
            if (session != null) {
                session.getTransaction().begin();
                String hql = "Select count(*) from Cake c where c.cakeName like :cakeName and c.category=:category  and c.cakePrice between :min and :max";
                Query query = session.createQuery(hql);
                query.setParameter("cakeName", "%" + cakeName + "%");
                query.setParameter("category", category);
                query.setParameter("min", min);
                query.setParameter("max", max);
                Number count = (Number) query.list().get(0);
                session.getTransaction().commit();
                return count.intValue();
            }
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            closeConnection();
        }
        return 0;
    }

    public int countSearchByCategorySortByRange(String categoryID, int min, int max) {
        try {
            session = factory.getCurrentSession();
            if (session != null) {
                session.getTransaction().begin();
                String hql = "Select count(*) from Cake c where c.category.categoryID=:categoryID  and c.cakePrice between :min and :max";
                Query query = session.createQuery(hql);
                query.setParameter("categoryID", categoryID);
                query.setParameter("min", min);
                query.setParameter("max", max);
                Number count = (Number) query.list().get(0);
                session.getTransaction().commit();
                return count.intValue();
            }
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            closeConnection();
        }
        return 0;
    }

    public int countSearchSortByRange(int min, int max) {
        try {
            session = factory.getCurrentSession();
            if (session != null) {
                session.getTransaction().begin();
                String hql = "Select count(*) from Cake c where c.cakePrice between :min and :max";
                Query query = session.createQuery(hql);
                query.setParameter("min", min);
                query.setParameter("max", max);
                Number count = (Number) query.list().get(0);
                session.getTransaction().commit();
                return count.intValue();
            }
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            closeConnection();
        }
        return 0;
    }

    public int countAll() {
        try {
            session = factory.getCurrentSession();
            if (session != null) {
                session.getTransaction().begin();
                String hql = "Select count(*) from Cake";
                Query query = session.createQuery(hql);
                Number count = (Number) query.list().get(0);
                session.getTransaction().commit();
                return count.intValue();
            }
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            closeConnection();
        }
        return 0;
    }
}
