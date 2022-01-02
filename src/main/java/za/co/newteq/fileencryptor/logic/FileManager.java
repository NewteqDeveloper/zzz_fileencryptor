/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.co.newteq.fileencryptor.logic;

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.apache.commons.io.FileUtils;
import za.co.newteq.fileencryptor.exceptions.RuntimeIOException;

/**
 *
 * @author newt.fourie
 */
public class FileManager {
    
    public void writeLines(String filePath, List<String> lines){
        try{
            File file = new File(filePath);
            FileUtils.writeLines(file, lines);
        }
        catch (IOException e){
            throw new RuntimeIOException(e);
        }
    }
    
    public List<String> readLines(String filePath) {
        try{
            File file = new File(filePath);
            return FileUtils.readLines(file);
        }
        catch (IOException e){
            throw new RuntimeIOException(e);
        }
    }
}
