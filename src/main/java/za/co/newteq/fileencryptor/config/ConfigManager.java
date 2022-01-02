/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.co.newteq.fileencryptor.config;

import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import za.co.newteq.fileencryptor.exceptions.RuntimeIOException;

/**
 *
 * @author sanit_000
 */
public class ConfigManager {
    
    private Properties properties;
    private static final String CONFIG_FILE_FULL_PATH = "za/co/newteq/fileencryptor/config/config.properties";
    
    private ConfigManager(){
        try{
            InputStream in = this.getClass().getClassLoader().getResourceAsStream(CONFIG_FILE_FULL_PATH);
            properties = new Properties();
            properties.load(in);
            
            this.passwordFile = properties.getProperty("passwordFile");
        }
        catch (IOException e){
            throw new RuntimeIOException(e);
        }
    }
    
    private static ConfigManager _instance = null;
    
    public static ConfigManager getInstance(){
        if (_instance == null)
            _instance = new ConfigManager();
        return _instance;
    }
    
    private final String passwordFile;

    public String getPasswordFile() {
        return passwordFile;
    }
    
}
