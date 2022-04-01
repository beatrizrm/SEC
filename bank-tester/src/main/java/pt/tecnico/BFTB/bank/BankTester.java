package pt.tecnico.BFTB.bank;

import pt.tecnico.BFTB.bank.crypto.CryptoHelper;
import pt.tecnico.BFTB.bank.grpc.openAccountRequest;
import pt.tecnico.BFTB.bank.grpc.openAccountResponse;

import java.security.KeyPair;

public class BankTester {

    public static void main(String[] args){

        System.out.println(BankTester.class.getSimpleName());

        System.out.printf("Received %d arguments%n", args.length);
        for (int i = 0; i < args.length; i++) {
            System.out.printf("arg[%d] = %s%n", i, args[i]);
        }

        BankFrontend frontend = new BankFrontend();

        //KeyPair keyPairDiogo = CryptoHelper.generate_RSA_keyPair();

        //openAccountRequest openAccRequest = openAccountRequest.newBuilder().setUser("Diogo").setKey(CryptoHelper.encodeToBase64(keyPairDiogo.getPublic().getEncoded())).build();
        //openAccountResponse openAccResponse = frontend.openAccount(openAccRequest);

        //KeyPair keyPairTomas = CryptoHelper.generate_RSA_keyPair();

        //openAccountRequest openAccRequest2 = openAccountRequest.newBuilder().setUser("Tomas").setKey(CryptoHelper.encodeToBase64(keyPairTomas.getPublic().getEncoded())).build();
        //openAccountResponse openAccResponse2 = frontend.openAccount(openAccRequest2);

        frontend.disconnect();
    }
}
