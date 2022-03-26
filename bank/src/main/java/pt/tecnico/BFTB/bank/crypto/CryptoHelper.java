package pt.tecnico.BFTB.bank.crypto;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class CryptoHelper {

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




}
