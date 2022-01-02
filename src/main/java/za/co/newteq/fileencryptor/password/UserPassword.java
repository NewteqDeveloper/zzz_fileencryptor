/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.co.newteq.fileencryptor.password;

import java.util.Objects;
import za.co.newteq.fileencryptor.logic.PasswordManager;

/**
 *
 * @author sanit_000
 */
public class UserPassword {
    
    private final String salt;
    private final String hashedPassword;

    public String getSalt() {
        return salt;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }
    
    private PasswordManager passwordMan;
    
    public UserPassword(String password){
        this.salt = PasswordManager.getNextSalt();
        passwordMan = new PasswordManager();
        this.hashedPassword = passwordMan.hash(password, this.salt);
    }
    
    public UserPassword(String password, String salt, boolean isHashed){
        if (isHashed){
            this.hashedPassword = password;
            this.salt = salt;
        }
        else{
            this.salt = salt;
            passwordMan = new PasswordManager();
            this.hashedPassword = passwordMan.hash(password, this.salt);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof UserPassword))
            return false;
        
        UserPassword other = (UserPassword) obj;
        return (other.getSalt() == null ? this.getSalt() == null : other.getSalt().equals(this.getSalt()))
                && (other.getHashedPassword() == null ? this.getHashedPassword() == null : other.getHashedPassword().equals(this.getHashedPassword()));
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 61 * hash + Objects.hashCode(this.salt);
        hash = 61 * hash + Objects.hashCode(this.hashedPassword);
        return hash;
    }
    
    
}
