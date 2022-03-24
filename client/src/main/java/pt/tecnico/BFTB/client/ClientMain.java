package pt.tecnico.BFTB.client;

public class ClientMain {

    public static void main(String[] args){

        System.out.printf("Received %d arguments%n", args.length);
        for (int i = 0; i < args.length; i++) {
            System.out.printf("arg[%d] = %s%n", i, args[i]);
        }
    }
}
