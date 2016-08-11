import java.io.IOException;

public class HelloGRPCServer {
    public static void main(String[] args) throws InterruptedException, IOException {
        PrinterServer printerServer = new PrinterServer();
        printerServer.start();
        printerServer.blockUntilShutdown();
    }
}
