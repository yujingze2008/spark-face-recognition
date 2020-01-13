package com.gitee.code4fun.facerecognition.common.rpc.stub;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.12.0)",
    comments = "Source: service.opencv.proto")
public final class OpenCVServiceGrpc {

  private OpenCVServiceGrpc() {}

  public static final String SERVICE_NAME = "service.OpenCVService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @Deprecated // Use {@link #getGetFaceTagImageMethod()} instead.
  public static final io.grpc.MethodDescriptor<OpenCVServiceProto.OpenCVBasicRequest,
      OpenCVServiceProto.ImageResponse> METHOD_GET_FACE_TAG_IMAGE = getGetFaceTagImageMethodHelper();

  private static volatile io.grpc.MethodDescriptor<OpenCVServiceProto.OpenCVBasicRequest,
      OpenCVServiceProto.ImageResponse> getGetFaceTagImageMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<OpenCVServiceProto.OpenCVBasicRequest,
      OpenCVServiceProto.ImageResponse> getGetFaceTagImageMethod() {
    return getGetFaceTagImageMethodHelper();
  }

  private static io.grpc.MethodDescriptor<OpenCVServiceProto.OpenCVBasicRequest,
      OpenCVServiceProto.ImageResponse> getGetFaceTagImageMethodHelper() {
    io.grpc.MethodDescriptor<OpenCVServiceProto.OpenCVBasicRequest, OpenCVServiceProto.ImageResponse> getGetFaceTagImageMethod;
    if ((getGetFaceTagImageMethod = OpenCVServiceGrpc.getGetFaceTagImageMethod) == null) {
      synchronized (OpenCVServiceGrpc.class) {
        if ((getGetFaceTagImageMethod = OpenCVServiceGrpc.getGetFaceTagImageMethod) == null) {
          OpenCVServiceGrpc.getGetFaceTagImageMethod = getGetFaceTagImageMethod = 
              io.grpc.MethodDescriptor.<OpenCVServiceProto.OpenCVBasicRequest, OpenCVServiceProto.ImageResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "service.OpenCVService", "getFaceTagImage"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  OpenCVServiceProto.OpenCVBasicRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  OpenCVServiceProto.ImageResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new OpenCVServiceMethodDescriptorSupplier("getFaceTagImage"))
                  .build();
          }
        }
     }
     return getGetFaceTagImageMethod;
  }
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @Deprecated // Use {@link #getGetFaceImagesMethod()} instead.
  public static final io.grpc.MethodDescriptor<OpenCVServiceProto.OpenCVBasicRequest,
      OpenCVServiceProto.ImageResponse> METHOD_GET_FACE_IMAGES = getGetFaceImagesMethodHelper();

  private static volatile io.grpc.MethodDescriptor<OpenCVServiceProto.OpenCVBasicRequest,
      OpenCVServiceProto.ImageResponse> getGetFaceImagesMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<OpenCVServiceProto.OpenCVBasicRequest,
      OpenCVServiceProto.ImageResponse> getGetFaceImagesMethod() {
    return getGetFaceImagesMethodHelper();
  }

  private static io.grpc.MethodDescriptor<OpenCVServiceProto.OpenCVBasicRequest,
      OpenCVServiceProto.ImageResponse> getGetFaceImagesMethodHelper() {
    io.grpc.MethodDescriptor<OpenCVServiceProto.OpenCVBasicRequest, OpenCVServiceProto.ImageResponse> getGetFaceImagesMethod;
    if ((getGetFaceImagesMethod = OpenCVServiceGrpc.getGetFaceImagesMethod) == null) {
      synchronized (OpenCVServiceGrpc.class) {
        if ((getGetFaceImagesMethod = OpenCVServiceGrpc.getGetFaceImagesMethod) == null) {
          OpenCVServiceGrpc.getGetFaceImagesMethod = getGetFaceImagesMethod = 
              io.grpc.MethodDescriptor.<OpenCVServiceProto.OpenCVBasicRequest, OpenCVServiceProto.ImageResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "service.OpenCVService", "getFaceImages"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  OpenCVServiceProto.OpenCVBasicRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  OpenCVServiceProto.ImageResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new OpenCVServiceMethodDescriptorSupplier("getFaceImages"))
                  .build();
          }
        }
     }
     return getGetFaceImagesMethod;
  }
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @Deprecated // Use {@link #getGetFaceVectorMethod()} instead.
  public static final io.grpc.MethodDescriptor<OpenCVServiceProto.OpenCVBasicRequest,
      OpenCVServiceProto.VectorResponse> METHOD_GET_FACE_VECTOR = getGetFaceVectorMethodHelper();

  private static volatile io.grpc.MethodDescriptor<OpenCVServiceProto.OpenCVBasicRequest,
      OpenCVServiceProto.VectorResponse> getGetFaceVectorMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<OpenCVServiceProto.OpenCVBasicRequest,
      OpenCVServiceProto.VectorResponse> getGetFaceVectorMethod() {
    return getGetFaceVectorMethodHelper();
  }

  private static io.grpc.MethodDescriptor<OpenCVServiceProto.OpenCVBasicRequest,
      OpenCVServiceProto.VectorResponse> getGetFaceVectorMethodHelper() {
    io.grpc.MethodDescriptor<OpenCVServiceProto.OpenCVBasicRequest, OpenCVServiceProto.VectorResponse> getGetFaceVectorMethod;
    if ((getGetFaceVectorMethod = OpenCVServiceGrpc.getGetFaceVectorMethod) == null) {
      synchronized (OpenCVServiceGrpc.class) {
        if ((getGetFaceVectorMethod = OpenCVServiceGrpc.getGetFaceVectorMethod) == null) {
          OpenCVServiceGrpc.getGetFaceVectorMethod = getGetFaceVectorMethod = 
              io.grpc.MethodDescriptor.<OpenCVServiceProto.OpenCVBasicRequest, OpenCVServiceProto.VectorResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "service.OpenCVService", "getFaceVector"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  OpenCVServiceProto.OpenCVBasicRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  OpenCVServiceProto.VectorResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new OpenCVServiceMethodDescriptorSupplier("getFaceVector"))
                  .build();
          }
        }
     }
     return getGetFaceVectorMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static OpenCVServiceStub newStub(io.grpc.Channel channel) {
    return new OpenCVServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static OpenCVServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new OpenCVServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static OpenCVServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new OpenCVServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class OpenCVServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void getFaceTagImage(OpenCVServiceProto.OpenCVBasicRequest request,
        io.grpc.stub.StreamObserver<OpenCVServiceProto.ImageResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getGetFaceTagImageMethodHelper(), responseObserver);
    }

    /**
     */
    public void getFaceImages(OpenCVServiceProto.OpenCVBasicRequest request,
        io.grpc.stub.StreamObserver<OpenCVServiceProto.ImageResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getGetFaceImagesMethodHelper(), responseObserver);
    }

    /**
     */
    public void getFaceVector(OpenCVServiceProto.OpenCVBasicRequest request,
        io.grpc.stub.StreamObserver<OpenCVServiceProto.VectorResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getGetFaceVectorMethodHelper(), responseObserver);
    }

    @Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetFaceTagImageMethodHelper(),
            asyncUnaryCall(
              new MethodHandlers<
                OpenCVServiceProto.OpenCVBasicRequest,
                OpenCVServiceProto.ImageResponse>(
                  this, METHODID_GET_FACE_TAG_IMAGE)))
          .addMethod(
            getGetFaceImagesMethodHelper(),
            asyncUnaryCall(
              new MethodHandlers<
                OpenCVServiceProto.OpenCVBasicRequest,
                OpenCVServiceProto.ImageResponse>(
                  this, METHODID_GET_FACE_IMAGES)))
          .addMethod(
            getGetFaceVectorMethodHelper(),
            asyncUnaryCall(
              new MethodHandlers<
                OpenCVServiceProto.OpenCVBasicRequest,
                OpenCVServiceProto.VectorResponse>(
                  this, METHODID_GET_FACE_VECTOR)))
          .build();
    }
  }

  /**
   */
  public static final class OpenCVServiceStub extends io.grpc.stub.AbstractStub<OpenCVServiceStub> {
    private OpenCVServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private OpenCVServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected OpenCVServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new OpenCVServiceStub(channel, callOptions);
    }

    /**
     */
    public void getFaceTagImage(OpenCVServiceProto.OpenCVBasicRequest request,
        io.grpc.stub.StreamObserver<OpenCVServiceProto.ImageResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetFaceTagImageMethodHelper(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getFaceImages(OpenCVServiceProto.OpenCVBasicRequest request,
        io.grpc.stub.StreamObserver<OpenCVServiceProto.ImageResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetFaceImagesMethodHelper(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getFaceVector(OpenCVServiceProto.OpenCVBasicRequest request,
        io.grpc.stub.StreamObserver<OpenCVServiceProto.VectorResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetFaceVectorMethodHelper(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class OpenCVServiceBlockingStub extends io.grpc.stub.AbstractStub<OpenCVServiceBlockingStub> {
    private OpenCVServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private OpenCVServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected OpenCVServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new OpenCVServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public OpenCVServiceProto.ImageResponse getFaceTagImage(OpenCVServiceProto.OpenCVBasicRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetFaceTagImageMethodHelper(), getCallOptions(), request);
    }

    /**
     */
    public OpenCVServiceProto.ImageResponse getFaceImages(OpenCVServiceProto.OpenCVBasicRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetFaceImagesMethodHelper(), getCallOptions(), request);
    }

    /**
     */
    public OpenCVServiceProto.VectorResponse getFaceVector(OpenCVServiceProto.OpenCVBasicRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetFaceVectorMethodHelper(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class OpenCVServiceFutureStub extends io.grpc.stub.AbstractStub<OpenCVServiceFutureStub> {
    private OpenCVServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private OpenCVServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected OpenCVServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new OpenCVServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<OpenCVServiceProto.ImageResponse> getFaceTagImage(
        OpenCVServiceProto.OpenCVBasicRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetFaceTagImageMethodHelper(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<OpenCVServiceProto.ImageResponse> getFaceImages(
        OpenCVServiceProto.OpenCVBasicRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetFaceImagesMethodHelper(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<OpenCVServiceProto.VectorResponse> getFaceVector(
        OpenCVServiceProto.OpenCVBasicRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetFaceVectorMethodHelper(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_FACE_TAG_IMAGE = 0;
  private static final int METHODID_GET_FACE_IMAGES = 1;
  private static final int METHODID_GET_FACE_VECTOR = 2;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final OpenCVServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(OpenCVServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_FACE_TAG_IMAGE:
          serviceImpl.getFaceTagImage((OpenCVServiceProto.OpenCVBasicRequest) request,
              (io.grpc.stub.StreamObserver<OpenCVServiceProto.ImageResponse>) responseObserver);
          break;
        case METHODID_GET_FACE_IMAGES:
          serviceImpl.getFaceImages((OpenCVServiceProto.OpenCVBasicRequest) request,
              (io.grpc.stub.StreamObserver<OpenCVServiceProto.ImageResponse>) responseObserver);
          break;
        case METHODID_GET_FACE_VECTOR:
          serviceImpl.getFaceVector((OpenCVServiceProto.OpenCVBasicRequest) request,
              (io.grpc.stub.StreamObserver<OpenCVServiceProto.VectorResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @Override
    @SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class OpenCVServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    OpenCVServiceBaseDescriptorSupplier() {}

    @Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return OpenCVServiceProto.getDescriptor();
    }

    @Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("OpenCVService");
    }
  }

  private static final class OpenCVServiceFileDescriptorSupplier
      extends OpenCVServiceBaseDescriptorSupplier {
    OpenCVServiceFileDescriptorSupplier() {}
  }

  private static final class OpenCVServiceMethodDescriptorSupplier
      extends OpenCVServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    OpenCVServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (OpenCVServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new OpenCVServiceFileDescriptorSupplier())
              .addMethod(getGetFaceTagImageMethodHelper())
              .addMethod(getGetFaceImagesMethodHelper())
              .addMethod(getGetFaceVectorMethodHelper())
              .build();
        }
      }
    }
    return result;
  }
}
