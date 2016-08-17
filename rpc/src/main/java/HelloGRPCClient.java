import com.fullcontact.nplay.service.NativeService;
import com.fullcontact.nplay.service.PrinterGrpc;
import com.google.protobuf.ByteString;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.nio.charset.Charset;
import java.util.Random;

public class HelloGRPCClient {

    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext(true)
                .build();
        Random random = new Random();
        byte[] bytes = new byte[20];
        random.nextBytes(bytes);
        PrinterGrpc.PrinterBlockingStub blockingStub = PrinterGrpc.newBlockingStub(channel);
        NativeService.PrintRequest message = NativeService.PrintRequest.newBuilder()
                .setMsg("Message")
                .setPayload(ByteString.copyFrom(bytes))
                .build();
        NativeService.PrintReply printReply = blockingStub.printMsg(message);
        System.out.println(printReply.getMsg());
        System.out.println(printReply.getPayload().toString(Charset.defaultCharset()));
    }
}
