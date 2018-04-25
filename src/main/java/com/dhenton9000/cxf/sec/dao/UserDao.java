package com.dhenton9000.cxf.sec.dao;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import com.dhenton9000.cxf.sec.domain.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;

public class UserDao {

    private JdbcTemplate jdbcTemplate;
    private static final Logger LOG = LoggerFactory.getLogger(UserDao.class);

    /**
     * @return the jdbcTemplate
     */
    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    /**
     * @param jdbcTemplate the jdbcTemplate to set
     */
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<User> getUsers() {

        List<User> uCollection = new ArrayList<User>();
        String sql = "select * from users";

        //   uCollection = this.jdbcTemplate.query(sql, 
        //           new BeanPropertyRowMapper(Group.class));
        // https://www.mkyong.com/spring/spring-jdbctemplate-querying-examples/
        this.jdbcTemplate.query(sql, (r) -> {

            while (r.next()) {

                int id = r.getInt("user_id");
                String userName = r.getString("username");
                String login = r.getString("login");
                uCollection.add(new User(userName, id, login));

            }

        });

        return uCollection;
    }

    /**
     * returns the original user before modification
     *
     * @param u
     * @return
     */
    public User saveUsers(final User u) {
        //  return new User();

        ResultSetExtractor<User> rse = (ResultSet rs) -> {
            if (rs.next()) {
                int uID = rs.getInt("user_id");
                String login = rs.getString("login");
                String userName = rs.getString("username");
                return new User(userName, uID, login);
            } else {
                return null;
            }
        };
        PreparedStatementSetter pss = (PreparedStatement ps) -> {
            ps.setInt(1, u.getId());
        };
        User user = this.jdbcTemplate.query("select * from users where user_id = ?", pss, rse);
        if (user == null) {
            throw new RuntimeException("could not find user id = " + u.getId());
        }
        String sql = "update users set username = ?, login=? where user_id = ?";
        PreparedStatementCreator psc = (Connection con) -> {
            PreparedStatement p = con.prepareStatement(sql);
            p.setInt(3, u.getId());
            p.setString(1, u.getUserName());
            p.setString(2, u.getLogin());
            return p;
        };
        PreparedStatementCallback<Boolean> action = (PreparedStatement ps) -> ps.execute();

        this.jdbcTemplate.execute(psc, action);

        return user;

    }

    public User deleteUsers(int id) {
        User u = null;
        ResultSetExtractor<User> rse = (ResultSet rs) -> {
            if (rs.next()) {
                int uID = rs.getInt("user_id");
                String login = rs.getString("login");
                String userName = rs.getString("username");
                return new User(userName, uID, login);
            } else {
                return null;
            }
        };
        PreparedStatementSetter pss = (PreparedStatement ps) -> {
            ps.setInt(1, id);
        };
        try {
            u = this.jdbcTemplate.query("select * from users where user_id = ?", pss, rse);

        } catch (Exception pe) {
            LOG.error("error " + pe.getMessage() + " " + pe.getClass().getName());
        }
        if (u == null) {
            throw new RuntimeException("could not delete user id = " + id);

        }

        PreparedStatementCreator psc = (Connection con) -> {
            PreparedStatement p = con.prepareStatement("delete from users where user_id = ?");
            p.setInt(1, id);
            return p;
        };
        PreparedStatementCallback<Boolean> action = (PreparedStatement ps) -> ps.execute();

        this.jdbcTemplate.execute(psc, action);

        return u;
    }

    public User addUser(String login, String userName) {

        PreparedStatementCreator psc = (Connection con) -> {
            PreparedStatement p
                    = con.prepareStatement(
                            "insert into users (login,username) values (?,?)",Statement.RETURN_GENERATED_KEYS);
            p.setString(1, login);
            p.setString(2, userName);
            return p;
        };
        
        // https://stackoverflow.com/questions/1915166/how-to-get-the-insert-id-in-jdbc
        // https://www.arundhaj.com/blog/getGeneratedKeys-with-postgresql.html
        
        PreparedStatementCallback<User> action = (PreparedStatement ps) -> {
            User newUser = new User();
            newUser.setLogin(login);
            newUser.setUserName(userName);
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("could not insert for user " + userName);
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    newUser.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }

            return newUser;
        };

        User newUser = this.jdbcTemplate.execute(psc, action);
        return newUser;
    }

}
