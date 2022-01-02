package za.co.newteq.fileencryptor.application;

import java.io.IOException;
import java.util.Scanner;
import za.co.newteq.fileencryptor.config.ConfigManager;
import za.co.newteq.fileencryptor.logic.PasswordStorageManager;
import za.co.newteq.fileencryptor.password.UserPassword;

public class Console {

	public static void main(String[] args) throws IOException
	{
            java.io.Console currentConsole = System.console();
            Scanner inScanner = new Scanner(System.in);
            
            System.out.print("Please enter your password to proceed: ");
            String password = new String(currentConsole.readPassword());
            System.out.println("Password captured...");
            System.out.println("Checking match...");
            System.out.println();
            
            PasswordStorageManager psm = new PasswordStorageManager();
            boolean isMatch = psm.isMatchReadFromFile(ConfigManager.getInstance().getPasswordFile(), password);
            if (!isMatch){
                return;
            }
            
            System.out.println("Success");
//		System.out.println("hello world");
//		try {
//			FileEncryptor enc = new FileEncryptor();
//			
//			enc.makeKey();
//			
//			File testInFile = new File("D:\\SSD Folders\\My Files\\TestingEnc\\testIn.txt");
//			File testOutFile = new File("D:\\SSD Folders\\My Files\\TestingEnc\\testOut.txt");
//			File testOutDecFile = new File("D:\\SSD Folders\\My Files\\TestingEnc\\testOutDec.txt");
//			File publicKeyFile = new File("D:\\SSD Folders\\My Files\\TestingEnc\\keys\\public_key.der");
//			File privateKeyFile = new File("D:\\SSD Folders\\My Files\\TestingEnc\\keys\\pkcs8_key");
//			File savedAesFile = new File("D:\\SSD Folders\\My Files\\TestingEnc\\keys\\savedAes.ppk");
//			
//			enc.encrypt(testInFile, testOutFile);
//			
//			enc.saveKey(savedAesFile, publicKeyFile);
//			
//			enc.loadKey(savedAesFile, privateKeyFile);
//			enc.decrypt(testOutFile, testOutDecFile);
//		} catch (GeneralSecurityException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		
	}
	
}
