package pt.tecnico.BFTB.bank.crypto;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class CryptoHelper {

    public static final String pki_path = "../keys/pki";
    public static final String private_path = "../keys/private";

    public static KeyPair generate_RSA_keyPair(){
        try  {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048); //TODO escolher 1024 ou 2048
            return keyPairGenerator.generateKeyPair();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static KeyPair get_keyPair(String _user) {

        PublicKey pub_key = readRSAPublicKey(pki_path + "/" +  _user + ".pub");
        PrivateKey priv_key = readRSAPrivateKey(private_path + "/" + _user + ".priv");

        KeyPair ret_keys = new KeyPair(pub_key,priv_key);

        return ret_keys;
    }

    public static boolean checkIfAccountExists(String user) {
        File key_file = new File(CryptoHelper.pki_path + "/" + user + ".pub");

        if (!key_file.exists()) {
            key_file.getParentFile().mkdirs();
            return false;
        }

        return true;

    }

    public static PublicKey readRSAPublicKey(String publicKeyPath) {
        try {
            byte[] encoded = readFile(publicKeyPath);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encoded);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePublic(keySpec);
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static PrivateKey readRSAPrivateKey(String privateKeyPath) {
        try {
            byte[] encoded = readFile(privateKeyPath);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePrivate(keySpec);
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static void SaveKeyPair(String user, KeyPair keyPair) throws IOException {
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();

        // Store Public Key.
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(
                publicKey.getEncoded());
        String pubPath = pki_path + "/" +  user + ".pub";
        new File(pubPath).getParentFile().mkdirs();    // create directories if they dont exist
        FileOutputStream fos = new FileOutputStream(pubPath);
        fos.write(x509EncodedKeySpec.getEncoded());
        fos.close();

        // Store Private Key.
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(
                privateKey.getEncoded());
        String privPath = private_path + "/" + user + ".priv";
        new File(privPath).getParentFile().mkdirs();
        fos = new FileOutputStream(privPath);
        fos.write(pkcs8EncodedKeySpec.getEncoded());
        fos.close();

    }

    private static byte[] readFile(String path) throws IOException {
        new File(path).getParentFile().mkdirs();    // create directories if they dont exist
        FileInputStream fis = new FileInputStream(path);
        byte[] content = new byte[fis.available()];
        fis.read(content);
        fis.close();
        return content;
    }

    public static String encodeToBase64(byte[] data){
        return Base64.getEncoder().encodeToString(data);
    }

    public static byte[] decodeFromBase64(String data){
        return Base64.getDecoder().decode(data);
    }

    public static PublicKey publicKeyFromBase64(String publicKey_Base64){
        try {
            return KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decodeFromBase64(publicKey_Base64)));
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] signMessage(PrivateKey privKey,byte[] messageToSign) {
        try {
            Signature sig = Signature.getInstance("SHA256withRSA");
            sig.initSign(privKey);
            sig.update(messageToSign);
            byte[] signatureBytes = sig.sign();

            return signatureBytes;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static boolean verifySignature(byte[] msg, String signature_base64, PublicKey publicKey){
        try {
            Signature publicSignature = Signature.getInstance("SHA256withRSA");
            publicSignature.initVerify(publicKey);
            publicSignature.update(msg);

            byte[] signatureBytes = Base64.getDecoder().decode(signature_base64);

            return publicSignature.verify(signatureBytes);
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public static String generateNonce() {
        try {
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            byte bytes[] = new byte[20];
            random.nextBytes(bytes);
            return encodeToBase64(bytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static long getPoW(byte[] msg, int numZeroes) {
        long solution = 0;
        StringBuilder sb;
        try {
            do {
                MessageDigest sha = MessageDigest.getInstance("SHA-256");
                byte[] solution_arr = ByteBuffer.allocate(Long.BYTES).putLong(solution).array();
                sha.update(msg);
                sha.update(solution_arr);
                byte[] hash = sha.digest();
                sb = new StringBuilder();
                for (byte b : hash) {
                    sb.append(String.format("%02X", b));
                }
                solution++;
            } while (!sb.substring(0,numZeroes).equals("0".repeat(numZeroes)));
            return solution-1;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static boolean isPoWValid(byte[] msg, int numZeroes, long solution) {
        MessageDigest sha;
        try {
            sha = MessageDigest.getInstance("SHA-256");
            byte[] solution_arr = ByteBuffer.allocate(Long.BYTES).putLong(solution).array();
            sha.update(msg);
            sha.update(solution_arr);
            byte[] hash = sha.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02X", b));
            }
            return sb.substring(0,numZeroes).equals("0".repeat(numZeroes));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return false;
        }
    }

}
