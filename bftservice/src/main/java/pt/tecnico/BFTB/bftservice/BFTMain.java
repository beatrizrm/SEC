package pt.tecnico.BFTB.bftservice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;

public class BFTMain {

    private static String host;
    private static int port;
    private static String bankHost;
    private static int bankPort;
    private static int f;

    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println(BFTMain.class.getSimpleName());

        try {
			host = args[0];
			port = Integer.valueOf(args[1]);
            bankHost = args[2];
            bankPort = Integer.valueOf(args[3]);
			f = Integer.valueOf(args[4]);
		} catch (NumberFormatException e) {
			System.out.println("Wrong argument type: " + e.getMessage());
			return;
		}

        final BindableService impl = new BFTServiceImpl(bankHost, bankPort, f);
		
        // Create a new server to listen on port.
        Server server = ServerBuilder.forPort(port).addService(impl).build();
        
        // Start the server.
        server.start();
        System.out.println("Server started on port " + port);

        server.awaitTermination();  // FIXME
    }
}
