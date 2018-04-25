
package com.dhenton9000.cxf.sec;

import com.dhenton9000.cxf.sec.dao.GroupDao;
import com.dhenton9000.cxf.sec.dao.UserDao;
import com.dhenton9000.cxf.sec.domain.Group;
import com.dhenton9000.cxf.sec.domain.User;
import java.util.List;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebService( endpointInterface = "com.dhenton9000.cxf.sec.SecurityService")
 
public class SecurityServiceImpl implements SecurityService {

    public final static Logger LOG = LoggerFactory.getLogger(SecurityServiceImpl.class);
    private GroupDao groupDao;
    private UserDao userDao;

    public SecurityServiceImpl() {
        
        LOG.debug("service starting") ;
    }
    
    
    @Override
    public String sayHi(String text) {
         LOG.debug("hi bozo") ;
        return "Hello " + text;
    }

    @Override
    public List<Group> getGroups() {
                
        return this.getGroupDao().getGroups();
    }

    /**
     * @return the groupDao
     */
    public GroupDao getGroupDao() {
        return groupDao;
    }

    /**
     * @param groupDao the groupDao to set
     */
    public void setGroupDao(GroupDao groupDao) {
        this.groupDao = groupDao;
    }

    @Override
    public User saveUsers(@WebParam( name = "newUser" ) @XmlElement( required = true ) User u) {
         return this.userDao.saveUsers(u);
    }

    @Override
    public User deleteUsers(int id) {
        return this.userDao.deleteUsers(id);
    }

    @Override
    public List<User> getUsers() {
        return this.getUserDao().getUsers();
    }

    @Override
    public User addUser(@WebParam(name = "login") String login,   
            @WebParam(name = "userName") String userName) {
        
        return this.getUserDao().addUser(login,userName);
    }
    
    /**
     * @return the userDao
     */
    public UserDao getUserDao() {
        return userDao;
    }

    /**
     * @param userDao the userDao to set
     */
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * Web service operation
     */
    @Override
    public User findUser(final int userId) {
        //TODO write your implementation code here:
       return this.userDao.findUser(userId);
    }
}

