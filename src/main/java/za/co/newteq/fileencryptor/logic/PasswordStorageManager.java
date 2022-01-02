/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.co.newteq.fileencryptor.logic;

import java.util.ArrayList;
import java.util.List;
import za.co.newteq.fileencryptor.password.UserPassword;

/**
 *
 * @author newt.fourie
 */
public class PasswordStorageManager extends FileManager {
    
    public void savePasswordToFile(String filePath, UserPassword password) {
        List<String> l = new ArrayList<>();
        l.add(password.getHashedPassword());
        l.add(password.getSalt());
        this.writeLines(filePath, l);
    }
    
    public boolean isMatchReadFromFile(String filePath, String password){
        List<String> lines = this.readLines(filePath);
        UserPassword oldPassword = new UserPassword(lines.get(0), lines.get(1), true);
        UserPassword currentPassword = new UserPassword(password, lines.get(1), false);
        return oldPassword.equals(currentPassword);
    }
}
