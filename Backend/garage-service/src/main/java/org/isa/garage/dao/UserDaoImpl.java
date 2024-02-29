package org.isa.garage.dao;

import org.isa.garage.dto.UserDTO;
import org.isa.garage.dto.UserSignupDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public UserDTO loadUserFromEmail(String email) {
        String sql = "SELECT id,email,password,isActive,role,firstname, lastname FROM user WHERE email=?";
        try {
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
                UserDTO user = new UserDTO();
                user.setId(rs.getLong("id"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setActive(rs.getBoolean("isActive"));
                user.setRole(rs.getString("role"));
                user.setFirstname(rs.getString("firstname"));
                user.setLastname(rs.getString("lastname"));
                return user;
            }, email);

        }
        catch (EmptyResultDataAccessException e){
            return null;
        }
        
    }

    @Override
    public List<UserDTO> getAllUsers() {
        String sql = "SELECT id,email,password,isActive,role,firstname FROM user";
        try {
            return jdbcTemplate.query(sql, (rs, rowNum) -> {
                UserDTO user = new UserDTO();
                user.setId(rs.getLong("id"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setActive(rs.getBoolean("isActive"));
                user.setRole(rs.getString("role"));
                user.setFirstname(rs.getString("firstname"));
                return user;
            });
        }
        catch (EmptyResultDataAccessException e){
            return new ArrayList<>();
        }
    }

    @Override
    public void updateUserDetails(UserDTO userDTO){
        String sql = "UPDATE user SET email = ?, lastname = ?, firstname = ? WHERE id = ?";
        jdbcTemplate.update(sql, userDTO.getEmail(), userDTO.getLastname(), userDTO.getFirstname(), userDTO.getId());
    }

    @Override
    public UserDTO getUserById(Integer id){
        String sql = "SELECT id,email,password,isActive,role,firstname, lastname FROM user WHERE id=?";
        try {
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
                UserDTO user = new UserDTO();
                user.setId(rs.getLong("id"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setActive(rs.getBoolean("isActive"));
                user.setRole(rs.getString("role"));
                user.setFirstname(rs.getString("firstname"));
                user.setLastname(rs.getString("lastname"));
                return user;
            }, id);

        }
        catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    @Override
    public Boolean updatePassword(UserDTO userDTO){
        String sql = "UPDATE user SET password = ? WHERE id = ?";
        int rowAffected =   jdbcTemplate.update(sql,

                userDTO.getPassword(),
                userDTO.getId());

        return rowAffected == 1;
    }


}
