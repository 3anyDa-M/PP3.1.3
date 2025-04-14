package com.nikitin.Bootstrap.dao;


import com.nikitin.Bootstrap.models.Role;
import com.nikitin.Bootstrap.models.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
@Transactional
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager em;



    @Override
    public List<User> getAllUsers() {
        return em.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    @Override
    public void saveUser(String username, String password , Set<Role> roles) {
        em.persist(new User(username, password, roles));
    }

    @Override
    public void deleteUserById(int id) {
        User user = em.find(User.class, id);
        if (user != null) {
            em.remove(user);
        }
    }

    @Override
    public void updateUser(User user) {
        em.merge(user);

    }

    @Override
    public User findUserById(Long id) {
        return em.find(User.class,id);
    }
}
