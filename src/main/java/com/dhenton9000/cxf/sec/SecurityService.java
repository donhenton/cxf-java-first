package com.dhenton9000.cxf.sec;

import com.dhenton9000.cxf.sec.domain.Group;
import com.dhenton9000.cxf.sec.domain.User;
import java.util.List;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface SecurityService {
    String sayHi(String text);
    
    List<Group> getGroups();
    User saveUsers(User u);
    User deleteUsers(@WebParam(name = "userId") int userId);
    List<User> getUsers();
    User addUser(@WebParam(name = "login") String login, 
            @WebParam(name = "userName") String userName);
    
}

