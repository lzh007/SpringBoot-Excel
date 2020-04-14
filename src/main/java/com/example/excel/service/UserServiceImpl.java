package com.example.excel.service;

import com.example.excel.model.User;
import com.example.excel.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description: UserServiceImpl
 * @Author: lzh
 * @Date: 2020/4/14
 */
@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);


    @Autowired
    private UserRepository userRepository;

    @Override
    public Page<User> findByCondition(User user, Pageable pageable) {
        return null;
    }

    @Override
    public Page<User> findAllUser(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public void saveAll(List<User> users) {
        userRepository.saveAll(users);
        logger.info("用户信息导入成功!!!");
    }
}
