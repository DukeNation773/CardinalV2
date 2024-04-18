/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.WildFinder.Alpha.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

/**
 *
 * @author csc340
 */
@Repository
public class UserRepository {

    @Autowired
    NamedParameterJdbcTemplate template;

    List<User> findAll() {

        String query = "select id, userName, email, password from user";
        return template.query(query,
                (result, rowNum)
                -> new User(result.getLong("id"),
                        result.getString("userName"), result.getString(
                        "email"), result.getString("password")));
    }

    public User getUserById(long id) {
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue(
                "id", id);
        String query = "select * from user where id=:id ";
        return template.queryForObject(query, namedParameters,
                BeanPropertyRowMapper.newInstance(User.class));
    }
    
    public User getUserName(String username) {
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue(
                "username", username);
        String query = "select * from user where id=:id ";
        return template.queryForObject(query, namedParameters,
                BeanPropertyRowMapper.newInstance(User.class));
    }

    public int saveUser(User user) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("user_name", user.getUserName());
        paramMap.put("email", user.getEmail());
        paramMap.put("password", user.getPassword());
        String query = "INSERT INTO user(user_name, email, password) VALUES(:user_name, :email, :password)";
        return template.update(query, paramMap);
    }

    void deleteUserById(long id) {

        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue(
                "id", id);
        String query = "delete from user where id=:id";
        template.update(query, namedParameters);
    }

    void updateUser(User user) {

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", user.getId());
        paramMap.put("userName", user.getUserName());
        paramMap.put("email", user.getEmail());
        paramMap.put("password", user.getPassword());
        String query = "update user set userName=:userName, email=:email, password=:password where id=:id ";
        template.update(query, paramMap);
    }

}
