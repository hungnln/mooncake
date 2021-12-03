package com.hungnln.mooncake.daos;

import com.hungnln.mooncake.dtos.Role;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class RoleDAO {
    //    protected Session session;
//    SessionFactory factory = HibernateUtils.getSessionFactory();
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("mooncake");
    EntityManager em;
    private List<Role> roles;

    public RoleDAO() {
        try {
            this.roles = getAllRoles();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<Role> getAllRoles() {
        List list = null;
        try {
            em = emf.createEntityManager();
            if (em != null) {
                em.getTransaction().begin();
                String hql = "from Role";
                Query query = em.createQuery(hql);
                list = query.getResultList();
                em.getTransaction().commit();
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return list;
    }

    public Role getRoleByRoleId(int roleId) {
        RoleDAO roleDAO = new RoleDAO();
        for (Role role : roleDAO.roles) {
            if (role.getId() == roleId)
                return role;
        }
        return null;
    }
}
