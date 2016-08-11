import com.fullcontact.nplay.service.NativeService;
import com.fullcontact.nplay.service.PrinterGrpc;
import io.grpc.stub.StreamObserver;

public class PrinterService extends PrinterGrpc.PrinterImplBase {
    @Override
    public void printMsg(NativeService.PrintRequest request, StreamObserver<NativeService.PrintReply> responseObserver) {
        System.out.println("vvvvv");
        System.out.println(request.getMsg());
        System.out.println(request.getMsgBytes());
        System.out.println("^^^^^");
        NativeService.PrintReply reply = NativeService.PrintReply.newBuilder()
                .setMsg("Printed!")
                .build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
