 

package com.dhenton9000.cxf.sec.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "User")
public class User {
   
    private String userName; //username
    private int id; //user_id
    private String login; //login

    public User(String userName, int id, String login) {
        this.userName = userName;
        this.id = id;
        this.login = login;
    }
    
    public User() {
        
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
     @XmlElement( required = true )
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * @param login the login to set
     */
     @XmlElement( required = true )
    public void setLogin(String login) {
        this.login = login;
    }

}
