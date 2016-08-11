import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class PrinterServer {
    private static final int PORT = 50051;
    private Server server;

    public void start() throws IOException {
        server = ServerBuilder.forPort(PORT)
                .addService(new PrinterService())
                .build()
                .start();
        System.out.println("Started, running on " + PORT);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                System.err.println("Shutting down gRPC server with JVM");
                shutdown();
            }
        });
    }

    public void shutdown() {
        if (server != null) {
            server.shutdown();
        }
    }

    public void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }
}
