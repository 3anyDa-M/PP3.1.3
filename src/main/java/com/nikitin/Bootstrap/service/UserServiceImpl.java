package com.nikitin.Bootstrap.service;


import com.nikitin.Bootstrap.dao.UserDao;
import com.nikitin.Bootstrap.models.Role;
import com.nikitin.Bootstrap.models.User;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final UserDao userDao;

    public UserServiceImpl(PasswordEncoder passwordEncoder, UserDao userDao) {
        this.passwordEncoder = passwordEncoder;
        this.userDao = userDao;
    }


    @Override
    @Transactional
    public List<User> findAllUsers() {
        try {
            return userDao.getAllUsers();
        } catch (Exception ex) {
            LOGGER.error("Ошибка при выводе пользователей : {}", ex.getMessage(), ex);
            throw new RuntimeException("Не удалось вывести всех пользователей , произведён откат  ", ex);
        }
    }

    @Override
    @Transactional
    public void save(String name, String password, Set<Role> roles) {
        try {
            String encodedPassword = passwordEncoder.encode(password);
            userDao.saveUser(name, encodedPassword, roles);
        } catch (Exception ex) {
            LOGGER.error("Ошибка при сохранении пользователя : {}", ex.getMessage(), ex);
            throw new RuntimeException("Не удалось сохранить пользователя , произведён откат  ", ex);
        }
    }





    @Override
    @Transactional
    public void update(User user) {
        try {
            User existingUser = userDao.findUserById(user.getId());
            if (existingUser == null) {
                throw new RuntimeException("Пользователь не найден");
            }


            if (!user.getPassword().equals(existingUser.getPassword())) {
                String encodedPassword = passwordEncoder.encode(user.getPassword());
                user.setPassword(encodedPassword);
            }
            userDao.updateUser(user);
        } catch (Exception ex) {
            LOGGER.error("Ошибка при обновлении пользователя: {}", ex.getMessage(), ex);
            throw new RuntimeException("Не удалось обновить пользователя, произведён откат", ex);
        }
    }

    @Override
    public void delete(int id) {
        try {
            userDao.deleteUserById(id);
        } catch (Exception ex) {
            LOGGER.error("Ошибка при удалении  пользователей : {}", ex.getMessage(), ex);
            throw new RuntimeException("Не удалось удалить пользователя, произведён откат", ex);
        }

    }

    @Override
    public User findById(Long id) {
        try{
            return userDao.findUserById(id);
        }catch(Exception ex){
            LOGGER.error("Ошибка при поиске  пользователя : {}", ex.getMessage(), ex);
            throw new RuntimeException("Не удалось найти пользователя, произведён откат", ex);
        }
    }
}
