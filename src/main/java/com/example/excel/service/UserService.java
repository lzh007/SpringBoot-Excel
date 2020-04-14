package com.example.excel.service;

import com.example.excel.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @Description: UserService
 * @Author: lzh
 * @Date: 2020/4/14
 */
public interface UserService {
    Page<User> findByCondition(User user, Pageable pageable);

    Page<User> findAllUser(Pageable pageable);

    void saveAll(List<User> users);
}
