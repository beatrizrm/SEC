package pt.tecnico.BFTB.bank.crypto;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
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

    public static void SaveKeyPair(String path, KeyPair keyPair) throws IOException {
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();

        // Store Public Key.
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(
                publicKey.getEncoded());
        FileOutputStream fos = new FileOutputStream(path + "/public.key");
        fos.write(x509EncodedKeySpec.getEncoded());
        fos.close();

        // Store Private Key.
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(
                privateKey.getEncoded());
        fos = new FileOutputStream(path + "/private.key");
        fos.write(pkcs8EncodedKeySpec.getEncoded());
        fos.close();
    }

    private static byte[] readFile(String path) throws IOException {
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





}
