import com.fullcontact.nplay.service.NativeService;
import com.fullcontact.nplay.service.PrinterGrpc;
import com.google.protobuf.ByteString;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;

public class HelloGRPCClient {

    public static void main(String[] args) throws IOException {
        File file = new File(args[0]);
        byte[] photoBytes = Files.readAllBytes(file.toPath());
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext(true)
                .build();
        PrinterGrpc.PrinterBlockingStub blockingStub = PrinterGrpc.newBlockingStub(channel);
        NativeService.PrintRequest message = NativeService.PrintRequest.newBuilder()
                .setMsg("Message")
                .setPayload(ByteString.copyFrom(photoBytes))
                .build();
        NativeService.PrintReply printReply = blockingStub.printMsg(message);
        System.out.println(printReply.getMsg());
        System.out.println(printReply.getPayload().toString(Charset.defaultCharset()));
    }
}
