import com.fullcontact.nplay.service.NativeService;
import com.fullcontact.nplay.service.PrinterGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class HelloGRPCClient {

    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext(true)
                .build();
        PrinterGrpc.PrinterBlockingStub blockingStub = PrinterGrpc.newBlockingStub(channel);
        NativeService.PrintRequest message = NativeService.PrintRequest.newBuilder()
                .setMsg("Message")
                .build();
        NativeService.PrintReply printReply = blockingStub.printMsg(message);
        System.out.println(printReply.getMsg());
    }
}
