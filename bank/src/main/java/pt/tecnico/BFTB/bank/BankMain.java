package pt.tecnico.BFTB.bank;

import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.*;

public class BankMain {

    public static void main(String[] args) throws IOException, InterruptedException {

        System.out.println(BankMain.class.getSimpleName());

        int instance = Integer.parseInt(args[3]);
        int bankPort = 8080 + instance;

        try{
            // start gRPC server
            final BindableService impl = new BankServiceImpl(args[0], args[1], args[2], instance, 5432);
            // Create a new server to listen on port
            Server server = ServerBuilder.forPort(bankPort).addService(impl).build();

            server.start();

            System.out.println("Server " + args[3] + " Started on port " + bankPort);

            server.awaitTermination();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
