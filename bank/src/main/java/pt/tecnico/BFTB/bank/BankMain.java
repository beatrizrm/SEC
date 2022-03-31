package pt.tecnico.BFTB.bank;

import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.*;

public class BankMain {

    public static void main(String[] args) throws IOException, InterruptedException {

        System.out.println(BankMain.class.getSimpleName());

        try{
            // start gRPC server
            final BindableService impl = new BankServiceImpl(args[0], args[1], args[2]);

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
