package pt.tecnico.BFTB.bftservice;

public class BFTTester {

    public static void main(String[] args){

        System.out.println(BFTTester.class.getSimpleName());

        System.out.printf("Received %d arguments%n", args.length);
        for (int i = 0; i < args.length; i++) {
            System.out.printf("arg[%d] = %s%n", i, args[i]);
        }

        BFTFrontend frontend = new BFTFrontend();

        //KeyPair keyPairDiogo = CryptoHelper.generate_RSA_keyPair();

        //openAccountRequest openAccRequest = openAccountRequest.newBuilder().setUser("Diogo").setKey(CryptoHelper.encodeToBase64(keyPairDiogo.getPublic().getEncoded())).build();
        //openAccountResponse openAccResponse = frontend.openAccount(openAccRequest);

        //KeyPair keyPairTomas = CryptoHelper.generate_RSA_keyPair();

        //openAccountRequest openAccRequest2 = openAccountRequest.newBuilder().setUser("Tomas").setKey(CryptoHelper.encodeToBase64(keyPairTomas.getPublic().getEncoded())).build();
        //openAccountResponse openAccResponse2 = frontend.openAccount(openAccRequest2);

        frontend.disconnect();
    }
}
