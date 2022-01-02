package za.co.newteq.fileencryptor.logic;

import java.io.*;
import java.security.*;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.asn1.pkcs.RSAPublicKey;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;

public class FileEncryptor {

	private Cipher rsaCipher;
	private Cipher aesCipher;
	
	private int AES_Key_Size = 256;
	
	private byte[] aesKey;
	private SecretKeySpec aeskeySpec;
	
	public FileEncryptor() throws GeneralSecurityException{
		rsaCipher = Cipher.getInstance("RSA");
		aesCipher = Cipher.getInstance("AES");
	}
	
	public void makeKey() throws NoSuchAlgorithmException{
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
	    kgen.init(AES_Key_Size);
	    SecretKey key = kgen.generateKey();
	    aesKey = key.getEncoded();
	    aeskeySpec = new SecretKeySpec(aesKey, "AES");
	}
	
	public void encrypt(File in, File out) throws IOException,
			InvalidKeyException {
		aesCipher.init(Cipher.ENCRYPT_MODE, aeskeySpec);

		FileInputStream is = new FileInputStream(in);
		CipherOutputStream os = new CipherOutputStream(
				new FileOutputStream(out), aesCipher);

		copy(is, os);

		os.close();
	}

	public void decrypt(File in, File out) throws IOException,
			InvalidKeyException {
		aesCipher.init(Cipher.DECRYPT_MODE, aeskeySpec);

		CipherInputStream is = new CipherInputStream(new FileInputStream(in),
				aesCipher);
		FileOutputStream os = new FileOutputStream(out);

		copy(is, os);

		is.close();
		os.close();
	}

	private void copy(InputStream is, OutputStream os) throws IOException {
		int i;
		byte[] b = new byte[1024];
		while ((i = is.read(b)) != -1) {
			os.write(b, 0, i);
		}
	}

	public void saveKey(File out, File publicKeyFile) throws IOException,
			GeneralSecurityException {
		// read public key to be used to encrypt the AES key
		/*byte[] encodedKey = new byte[(int) publicKeyFile.length()];
		new FileInputStream(publicKeyFile).read(encodedKey);*/

		// create public key
	    FileInputStream fis = new FileInputStream(publicKeyFile);
	    DataInputStream dis = new DataInputStream(fis);
	    byte[] keyBytes = new byte[(int)publicKeyFile.length()];
	    dis.readFully(keyBytes);
	    dis.close();

	    X509EncodedKeySpec spec =
	      new X509EncodedKeySpec(keyBytes);
	    KeyFactory kf = KeyFactory.getInstance("RSA");
		PublicKey pk = kf.generatePublic(spec);

		// write AES key
		rsaCipher.init(Cipher.ENCRYPT_MODE, pk);
		CipherOutputStream os = new CipherOutputStream(
				new FileOutputStream(out), rsaCipher);
		os.write(aesKey);
		os.close();
	}

	public void loadKey(File in, File privateKeyFile)
			throws GeneralSecurityException, IOException {
		// read private key to be used to decrypt the AES key
		byte[] encodedKey = new byte[(int) privateKeyFile.length()];
		new FileInputStream(privateKeyFile).read(encodedKey);

		// create private key
		PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(encodedKey);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		PrivateKey pk = kf.generatePrivate(privateKeySpec);

		// read AES key
		rsaCipher.init(Cipher.DECRYPT_MODE, pk);
		aesKey = new byte[AES_Key_Size / 8];
		CipherInputStream is = new CipherInputStream(new FileInputStream(in),
				rsaCipher);
		is.read(aesKey);
		aeskeySpec = new SecretKeySpec(aesKey, "AES");
	}
	
}
