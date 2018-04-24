package com.dhenton9000.cxf.sec;

import com.dhenton9000.cxf.sec.domain.Group;
import com.dhenton9000.cxf.sec.domain.User;
import java.util.List;
import javax.jws.WebService;

@WebService
public interface SecurityService {
    String sayHi(String text);
    
    List<Group> getGroups();
    User saveUsers(User u);
    User deleteUsers(int userId);
    List<User> getUsers();
    
}

