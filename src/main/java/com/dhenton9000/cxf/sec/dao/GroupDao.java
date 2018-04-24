 

package com.dhenton9000.cxf.sec.dao;
import com.dhenton9000.cxf.sec.domain.Group;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
 
public class GroupDao {

    private JdbcTemplate jdbcTemplate;
    private static final Logger LOG = LoggerFactory.getLogger(GroupDao.class);
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
    
    public List<Group> getGroups() {
        
         List<Group> g = new ArrayList<Group>();
          String sql = "select id,group_name from groups";
        
     //   g = this.jdbcTemplate.query(sql, 
     //           new BeanPropertyRowMapper(Group.class));
     // https://www.mkyong.com/spring/spring-jdbctemplate-querying-examples/
      
          this.jdbcTemplate.query(sql, (r) -> {

                 while (r.next()) {
                   
                   int id =   r.getInt("id"); 
                   String groupName = r.getString("group_name");
                   g.add(new Group(id,groupName));
                    
                 }           
             
         });

        return g;
    }
    
}
