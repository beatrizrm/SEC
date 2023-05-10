package pt.tecnico.BFTB.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class ClientMain {

    public static void main(String[] args) throws IOException, InterruptedException {

        System.out.println(ClientMain.class.getSimpleName());

        System.out.printf("Received %d arguments%n", args.length);
        for (int i = 0; i < args.length; i++) {
            System.out.printf("arg[%d] = %s%n", i, args[i]);
        }

        final ClientServiceImpl impl = new ClientServiceImpl(args);
        System.out.println("Client Started");
        String response = "";
        // Check if there is any shell redirect file to open and read commands
        if (args.length == 8) {

            // Read the commands on the commands.txt and execute them on service
            BufferedReader reader = impl.openResourcesFile(args[7]);
            String line;
            while (((line = reader.readLine()) != null) || (Objects.equals(response, "Client Stopped"))) {
                if (line.charAt(0) == '#') {
                    continue;
                }
                if (line.length() > 3 && line.substring(0, 3).equals("zzz")) {
                    int sleeptime = Integer.parseInt(line.substring(3, line.length()));
                    TimeUnit.MILLISECONDS.sleep(sleeptime);
                    continue;
                }
                response = impl.performCommand(line.split(" "));
                System.out.println(response);
            }
        } else {

            // Normal execution reading from the system in and executing the commands on the service
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            do {
                System.out.print("> ");
                response = impl.performCommand(reader.readLine().split(" "));
                System.out.println(response);
            } while (!response.equals("Client Stopped"));
        }
        impl.terminateService();
    }
}
