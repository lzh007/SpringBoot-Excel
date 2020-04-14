package com.example.excel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.excel.model.User;
import org.springframework.stereotype.Repository;

/**
 * @Description: UserRepository
 * @Author: lzh
 * @Date: 2020/4/14
 */
@Repository
public interface  UserRepository extends JpaRepository<User, Long> {

}
