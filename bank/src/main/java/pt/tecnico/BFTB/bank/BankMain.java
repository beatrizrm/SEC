package pt.tecnico.BFTB.bank;

import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.*;

public class BankMain {

    public static void main(String[] args) throws IOException, InterruptedException {

        System.out.println(BankMain.class.getSimpleName());

        System.out.printf("Received %d arguments%n", args.length);
        for (int i = 0; i < args.length; i++) {
            System.out.printf("arg[%d] = %s%n", i, args[i]);
        }

        try{
            // start gRPC server
            final BindableService impl = new BankServiceImpl();

            // Create a new server to listen on port
            Server server = ServerBuilder.forPort(8080).addService(impl).build();

            server.start();

            System.out.println("Server Started");

            server.awaitTermination();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
