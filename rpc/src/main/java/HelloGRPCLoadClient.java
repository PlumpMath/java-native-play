import com.fullcontact.nplay.service.NativeService;
import com.fullcontact.nplay.service.PrinterGrpc;
import com.google.protobuf.ByteString;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import io.grpc.stub.StreamObservers;
import org.threadly.concurrent.PriorityScheduler;
import org.threadly.concurrent.TaskPriority;
import org.threadly.concurrent.statistics.PrioritySchedulerStatisticTracker;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.LongAdder;

// Based on Threadly examples such as
// https://github.com/threadly/threadly_examples/blob/master/src/main/java/org/threadly/examples/fractals/ThreadlyFractal.java
public class HelloGRPCLoadClient {

    private static final PrioritySchedulerStatisticTracker SCHEDULER;
    static {
        int processors = Runtime.getRuntime().availableProcessors();
        SCHEDULER = new PrioritySchedulerStatisticTracker(processors * 2, TaskPriority.High, 500);
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        // Read file
        File file = new File(args[0]);
        byte[] photoBytes = Files.readAllBytes(file.toPath());
        // Set up communication
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext(true)
                .build();
        PrinterGrpc.PrinterStub stub = PrinterGrpc.newStub(channel);
        PrinterGrpc.PrinterBlockingStub blockingStub = PrinterGrpc.newBlockingStub(channel);
        // Set up tracking
        long tasks = 100;
        long rate = 16;
        CountDownLatch countDownLatch = new CountDownLatch((int) tasks);
        LongAdder completed = new LongAdder();
        long start = System.currentTimeMillis();
        StreamObserver<NativeService.PrintReply> observer = new StreamObserver<NativeService.PrintReply>() {

            @Override
            public void onNext(NativeService.PrintReply value) {
//                System.out.println("NEXT");
                completed.increment();
//                System.out.println(value.getMsg());
//                System.out.println(value.getPayload().toString(Charset.defaultCharset()));
//                countDownLatch.countDown();
            }

            @Override
            public void onError(Throwable t) {
                t.printStackTrace();
            }

            @Override
            public void onCompleted() {
                SCHEDULER.schedule(() -> {
//                System.out.println("COMPLETED TEST");
//                System.out.println("Finished " + completed.longValue() + " tasks");
                    long time = System.currentTimeMillis() - start;
//                System.out.println("Took " + time + "ms");
                    System.out.println(((double) time / completed.longValue()) + "ms per task");
                    countDownLatch.countDown();
                }, 0);
            }
        };

//        for (int i = 0; i < tasks; i++) {
//            Thread.sleep(1000 / rate);
//            NativeService.PrintRequest msg = NativeService.PrintRequest.newBuilder()
//                    .setMsg("Message")
//                    .setPayload(ByteString.copyFrom(photoBytes))
//                    .build();
//            stub.printMsg(msg, observer);
//        }

        System.out.println((System.currentTimeMillis() - start) + "ms to start");
        long s2 = System.currentTimeMillis();
        NativeService.PrintRequest msg = NativeService.PrintRequest.newBuilder()
                .setMsg("Message")
                .setPayload(ByteString.copyFrom(photoBytes))
                .build();
        for (int i = 0; i < tasks; i++) {
            SCHEDULER.schedule(() -> {
//                NativeService.PrintRequest msg = NativeService.PrintRequest.newBuilder()
//                        .setMsg("Message")
//                        .setPayload(ByteString.copyFrom(photoBytes))
//                        .build();
                stub.printMsg(msg, observer);
//                blockingStub.printMsg(msg);
//                countDownLatch.countDown();
//                completed.increment();
            }, 0);
        }

        // Repeatedly build, send, and process requests
//        while (rate <= 40) {
//            System.out.println("Testing rate of " + rate);
//            countDownLatch = new CountDownLatch((int) (rate * 10));
//            for (int i = 0; i < rate * 10; i++) {
//                Thread.sleep(1000 / rate);
//                NativeService.PrintRequest msg = NativeService.PrintRequest.newBuilder()
//                        .setMsg("Message")
//                        .setPayload(ByteString.copyFrom(photoBytes))
//                        .build();
//                stub.printMsg(msg, observer);
//            }
//            System.out.println("Finished testing rate of " + rate);
//            completed.reset();
//            rate += 10;
//        }

//        for (int i = 0; i < tasks; i++) {
//            NativeService.PrintRequest msg = NativeService.PrintRequest.newBuilder()
//                    .setMsg("Message")
//                    .setPayload(ByteString.copyFrom(photoBytes))
//                    .build();
//            stub.printMsg(msg, observer);
//        }

        countDownLatch.await();
        System.out.println((((double) System.currentTimeMillis() - s2) / completed.longValue()) + "ms per task");
//        NativeService.PrintReply printReply = stub.printMsg(message);
//        System.out.println(printReply.getMsg());
//        System.out.println(printReply.getPayload().toString(Charset.defaultCharset()));
    }
}
