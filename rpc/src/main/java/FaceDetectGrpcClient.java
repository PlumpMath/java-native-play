import com.fullcontact.nplay.service.NativeService;
import com.fullcontact.nplay.service.NativeService.FaceDetectionRequest;
import com.google.protobuf.ByteString;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static com.fullcontact.nplay.service.FaceDetectionGrpc.FaceDetectionBlockingStub;
import static com.fullcontact.nplay.service.FaceDetectionGrpc.newBlockingStub;

public class FaceDetectGrpcClient {

    public static void main(String[] args) throws IOException {

        File file = new File(args[0]);
        byte[] photoBytes = Files.readAllBytes(file.toPath());

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext(true)
                .build();
        FaceDetectionBlockingStub faceDetectionStub = newBlockingStub(channel);

        FaceDetectionRequest request = FaceDetectionRequest.newBuilder()
                .setPayload(ByteString.copyFrom(photoBytes))
                .build();

        NativeService.FaceDetectionResponse reply = faceDetectionStub.countFaces(request);

        System.out.println("Saw " + reply.getCount() + " faces.");
    }

}
