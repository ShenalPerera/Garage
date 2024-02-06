package org.isa.garage.dao;

import org.isa.garage.dto.UserSignupDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao{

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDaoImpl(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean existByEmail(String email) {
        String sql = "SELECT COUNT(id) FROM user WHERE email=?";

        Integer count = jdbcTemplate.queryForObject(sql, Integer.class,email);

        return count != null && count > 0;
    }

    @Override
    public boolean saveUser(UserSignupDTO userSignupDTO) {
        String sql = "INSERT INTO user (email, password, firstname, lastname, isActive) VALUES (?, ?, ?, ?, ?)";
        int rowAffected =   jdbcTemplate.update(sql,
                userSignupDTO.getEmail(),
                userSignupDTO.getPassword(),
                userSignupDTO.getFirstname(),
                userSignupDTO.getLastname(),
                true);

        return rowAffected == 1;
    }
}
