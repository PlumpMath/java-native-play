package com.fullcontact.nplay.service;

import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.0.0-pre2)",
    comments = "Source: native-service.proto")
public class FaceDetectionGrpc {

  private FaceDetectionGrpc() {}

  public static final String SERVICE_NAME = "service.FaceDetection";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.fullcontact.nplay.service.NativeService.FaceDetectionRequest,
      com.fullcontact.nplay.service.NativeService.FaceDetectionResponse> METHOD_COUNT_FACES =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "service.FaceDetection", "CountFaces"),
          io.grpc.protobuf.ProtoUtils.marshaller(com.fullcontact.nplay.service.NativeService.FaceDetectionRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(com.fullcontact.nplay.service.NativeService.FaceDetectionResponse.getDefaultInstance()));

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static FaceDetectionStub newStub(io.grpc.Channel channel) {
    return new FaceDetectionStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static FaceDetectionBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new FaceDetectionBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary and streaming output calls on the service
   */
  public static FaceDetectionFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new FaceDetectionFutureStub(channel);
  }

  /**
   */
  public static abstract class FaceDetectionImplBase implements io.grpc.BindableService {

    /**
     */
    public void countFaces(com.fullcontact.nplay.service.NativeService.FaceDetectionRequest request,
        io.grpc.stub.StreamObserver<com.fullcontact.nplay.service.NativeService.FaceDetectionResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_COUNT_FACES, responseObserver);
    }

    @java.lang.Override public io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_COUNT_FACES,
            asyncUnaryCall(
              new MethodHandlers<
                com.fullcontact.nplay.service.NativeService.FaceDetectionRequest,
                com.fullcontact.nplay.service.NativeService.FaceDetectionResponse>(
                  this, METHODID_COUNT_FACES)))
          .build();
    }
  }

  /**
   */
  public static final class FaceDetectionStub extends io.grpc.stub.AbstractStub<FaceDetectionStub> {
    private FaceDetectionStub(io.grpc.Channel channel) {
      super(channel);
    }

    private FaceDetectionStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FaceDetectionStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new FaceDetectionStub(channel, callOptions);
    }

    /**
     */
    public void countFaces(com.fullcontact.nplay.service.NativeService.FaceDetectionRequest request,
        io.grpc.stub.StreamObserver<com.fullcontact.nplay.service.NativeService.FaceDetectionResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_COUNT_FACES, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class FaceDetectionBlockingStub extends io.grpc.stub.AbstractStub<FaceDetectionBlockingStub> {
    private FaceDetectionBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private FaceDetectionBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FaceDetectionBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new FaceDetectionBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.fullcontact.nplay.service.NativeService.FaceDetectionResponse countFaces(com.fullcontact.nplay.service.NativeService.FaceDetectionRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_COUNT_FACES, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class FaceDetectionFutureStub extends io.grpc.stub.AbstractStub<FaceDetectionFutureStub> {
    private FaceDetectionFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private FaceDetectionFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FaceDetectionFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new FaceDetectionFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.fullcontact.nplay.service.NativeService.FaceDetectionResponse> countFaces(
        com.fullcontact.nplay.service.NativeService.FaceDetectionRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_COUNT_FACES, getCallOptions()), request);
    }
  }

  private static final int METHODID_COUNT_FACES = 0;

  private static class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final FaceDetectionImplBase serviceImpl;
    private final int methodId;

    public MethodHandlers(FaceDetectionImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_COUNT_FACES:
          serviceImpl.countFaces((com.fullcontact.nplay.service.NativeService.FaceDetectionRequest) request,
              (io.grpc.stub.StreamObserver<com.fullcontact.nplay.service.NativeService.FaceDetectionResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    return new io.grpc.ServiceDescriptor(SERVICE_NAME,
        METHOD_COUNT_FACES);
  }

}
