package pt.tecnico.BFTB.bank.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.36.0)",
    comments = "Source: bank.proto")
public final class BFTServiceGrpc {

  private BFTServiceGrpc() {}

  public static final String SERVICE_NAME = "pt.tecnico.BFTB.bank.grpc.BFTService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<pt.tecnico.BFTB.bank.grpc.checkAccountWriteRequest,
      pt.tecnico.BFTB.bank.grpc.checkAccountWriteResponse> getCheckAccountWriteMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "checkAccountWrite",
      requestType = pt.tecnico.BFTB.bank.grpc.checkAccountWriteRequest.class,
      responseType = pt.tecnico.BFTB.bank.grpc.checkAccountWriteResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<pt.tecnico.BFTB.bank.grpc.checkAccountWriteRequest,
      pt.tecnico.BFTB.bank.grpc.checkAccountWriteResponse> getCheckAccountWriteMethod() {
    io.grpc.MethodDescriptor<pt.tecnico.BFTB.bank.grpc.checkAccountWriteRequest, pt.tecnico.BFTB.bank.grpc.checkAccountWriteResponse> getCheckAccountWriteMethod;
    if ((getCheckAccountWriteMethod = BFTServiceGrpc.getCheckAccountWriteMethod) == null) {
      synchronized (BFTServiceGrpc.class) {
        if ((getCheckAccountWriteMethod = BFTServiceGrpc.getCheckAccountWriteMethod) == null) {
          BFTServiceGrpc.getCheckAccountWriteMethod = getCheckAccountWriteMethod =
              io.grpc.MethodDescriptor.<pt.tecnico.BFTB.bank.grpc.checkAccountWriteRequest, pt.tecnico.BFTB.bank.grpc.checkAccountWriteResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "checkAccountWrite"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pt.tecnico.BFTB.bank.grpc.checkAccountWriteRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pt.tecnico.BFTB.bank.grpc.checkAccountWriteResponse.getDefaultInstance()))
              .setSchemaDescriptor(new BFTServiceMethodDescriptorSupplier("checkAccountWrite"))
              .build();
        }
      }
    }
    return getCheckAccountWriteMethod;
  }

  private static volatile io.grpc.MethodDescriptor<pt.tecnico.BFTB.bank.grpc.openAccountRequest,
      pt.tecnico.BFTB.bank.grpc.openAccountResponse> getOpenAccountMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "openAccount",
      requestType = pt.tecnico.BFTB.bank.grpc.openAccountRequest.class,
      responseType = pt.tecnico.BFTB.bank.grpc.openAccountResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<pt.tecnico.BFTB.bank.grpc.openAccountRequest,
      pt.tecnico.BFTB.bank.grpc.openAccountResponse> getOpenAccountMethod() {
    io.grpc.MethodDescriptor<pt.tecnico.BFTB.bank.grpc.openAccountRequest, pt.tecnico.BFTB.bank.grpc.openAccountResponse> getOpenAccountMethod;
    if ((getOpenAccountMethod = BFTServiceGrpc.getOpenAccountMethod) == null) {
      synchronized (BFTServiceGrpc.class) {
        if ((getOpenAccountMethod = BFTServiceGrpc.getOpenAccountMethod) == null) {
          BFTServiceGrpc.getOpenAccountMethod = getOpenAccountMethod =
              io.grpc.MethodDescriptor.<pt.tecnico.BFTB.bank.grpc.openAccountRequest, pt.tecnico.BFTB.bank.grpc.openAccountResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "openAccount"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pt.tecnico.BFTB.bank.grpc.openAccountRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pt.tecnico.BFTB.bank.grpc.openAccountResponse.getDefaultInstance()))
              .setSchemaDescriptor(new BFTServiceMethodDescriptorSupplier("openAccount"))
              .build();
        }
      }
    }
    return getOpenAccountMethod;
  }

  private static volatile io.grpc.MethodDescriptor<pt.tecnico.BFTB.bank.grpc.sendAmountRequest,
      pt.tecnico.BFTB.bank.grpc.sendAmountResponse> getSendAmountMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "sendAmount",
      requestType = pt.tecnico.BFTB.bank.grpc.sendAmountRequest.class,
      responseType = pt.tecnico.BFTB.bank.grpc.sendAmountResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<pt.tecnico.BFTB.bank.grpc.sendAmountRequest,
      pt.tecnico.BFTB.bank.grpc.sendAmountResponse> getSendAmountMethod() {
    io.grpc.MethodDescriptor<pt.tecnico.BFTB.bank.grpc.sendAmountRequest, pt.tecnico.BFTB.bank.grpc.sendAmountResponse> getSendAmountMethod;
    if ((getSendAmountMethod = BFTServiceGrpc.getSendAmountMethod) == null) {
      synchronized (BFTServiceGrpc.class) {
        if ((getSendAmountMethod = BFTServiceGrpc.getSendAmountMethod) == null) {
          BFTServiceGrpc.getSendAmountMethod = getSendAmountMethod =
              io.grpc.MethodDescriptor.<pt.tecnico.BFTB.bank.grpc.sendAmountRequest, pt.tecnico.BFTB.bank.grpc.sendAmountResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "sendAmount"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pt.tecnico.BFTB.bank.grpc.sendAmountRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pt.tecnico.BFTB.bank.grpc.sendAmountResponse.getDefaultInstance()))
              .setSchemaDescriptor(new BFTServiceMethodDescriptorSupplier("sendAmount"))
              .build();
        }
      }
    }
    return getSendAmountMethod;
  }

  private static volatile io.grpc.MethodDescriptor<pt.tecnico.BFTB.bank.grpc.checkAccountRequest,
      pt.tecnico.BFTB.bank.grpc.checkAccountResponse> getCheckAccountMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "checkAccount",
      requestType = pt.tecnico.BFTB.bank.grpc.checkAccountRequest.class,
      responseType = pt.tecnico.BFTB.bank.grpc.checkAccountResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<pt.tecnico.BFTB.bank.grpc.checkAccountRequest,
      pt.tecnico.BFTB.bank.grpc.checkAccountResponse> getCheckAccountMethod() {
    io.grpc.MethodDescriptor<pt.tecnico.BFTB.bank.grpc.checkAccountRequest, pt.tecnico.BFTB.bank.grpc.checkAccountResponse> getCheckAccountMethod;
    if ((getCheckAccountMethod = BFTServiceGrpc.getCheckAccountMethod) == null) {
      synchronized (BFTServiceGrpc.class) {
        if ((getCheckAccountMethod = BFTServiceGrpc.getCheckAccountMethod) == null) {
          BFTServiceGrpc.getCheckAccountMethod = getCheckAccountMethod =
              io.grpc.MethodDescriptor.<pt.tecnico.BFTB.bank.grpc.checkAccountRequest, pt.tecnico.BFTB.bank.grpc.checkAccountResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "checkAccount"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pt.tecnico.BFTB.bank.grpc.checkAccountRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pt.tecnico.BFTB.bank.grpc.checkAccountResponse.getDefaultInstance()))
              .setSchemaDescriptor(new BFTServiceMethodDescriptorSupplier("checkAccount"))
              .build();
        }
      }
    }
    return getCheckAccountMethod;
  }

  private static volatile io.grpc.MethodDescriptor<pt.tecnico.BFTB.bank.grpc.receiveAmountRequest,
      pt.tecnico.BFTB.bank.grpc.receiveAmountResponse> getReceiveAmountMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "receiveAmount",
      requestType = pt.tecnico.BFTB.bank.grpc.receiveAmountRequest.class,
      responseType = pt.tecnico.BFTB.bank.grpc.receiveAmountResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<pt.tecnico.BFTB.bank.grpc.receiveAmountRequest,
      pt.tecnico.BFTB.bank.grpc.receiveAmountResponse> getReceiveAmountMethod() {
    io.grpc.MethodDescriptor<pt.tecnico.BFTB.bank.grpc.receiveAmountRequest, pt.tecnico.BFTB.bank.grpc.receiveAmountResponse> getReceiveAmountMethod;
    if ((getReceiveAmountMethod = BFTServiceGrpc.getReceiveAmountMethod) == null) {
      synchronized (BFTServiceGrpc.class) {
        if ((getReceiveAmountMethod = BFTServiceGrpc.getReceiveAmountMethod) == null) {
          BFTServiceGrpc.getReceiveAmountMethod = getReceiveAmountMethod =
              io.grpc.MethodDescriptor.<pt.tecnico.BFTB.bank.grpc.receiveAmountRequest, pt.tecnico.BFTB.bank.grpc.receiveAmountResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "receiveAmount"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pt.tecnico.BFTB.bank.grpc.receiveAmountRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pt.tecnico.BFTB.bank.grpc.receiveAmountResponse.getDefaultInstance()))
              .setSchemaDescriptor(new BFTServiceMethodDescriptorSupplier("receiveAmount"))
              .build();
        }
      }
    }
    return getReceiveAmountMethod;
  }

  private static volatile io.grpc.MethodDescriptor<pt.tecnico.BFTB.bank.grpc.auditRequest,
      pt.tecnico.BFTB.bank.grpc.auditResponse> getAuditMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "audit",
      requestType = pt.tecnico.BFTB.bank.grpc.auditRequest.class,
      responseType = pt.tecnico.BFTB.bank.grpc.auditResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<pt.tecnico.BFTB.bank.grpc.auditRequest,
      pt.tecnico.BFTB.bank.grpc.auditResponse> getAuditMethod() {
    io.grpc.MethodDescriptor<pt.tecnico.BFTB.bank.grpc.auditRequest, pt.tecnico.BFTB.bank.grpc.auditResponse> getAuditMethod;
    if ((getAuditMethod = BFTServiceGrpc.getAuditMethod) == null) {
      synchronized (BFTServiceGrpc.class) {
        if ((getAuditMethod = BFTServiceGrpc.getAuditMethod) == null) {
          BFTServiceGrpc.getAuditMethod = getAuditMethod =
              io.grpc.MethodDescriptor.<pt.tecnico.BFTB.bank.grpc.auditRequest, pt.tecnico.BFTB.bank.grpc.auditResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "audit"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pt.tecnico.BFTB.bank.grpc.auditRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pt.tecnico.BFTB.bank.grpc.auditResponse.getDefaultInstance()))
              .setSchemaDescriptor(new BFTServiceMethodDescriptorSupplier("audit"))
              .build();
        }
      }
    }
    return getAuditMethod;
  }

  private static volatile io.grpc.MethodDescriptor<pt.tecnico.BFTB.bank.grpc.checkStatusRequest,
      pt.tecnico.BFTB.bank.grpc.checkStatusResponse> getCheckStatusMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "checkStatus",
      requestType = pt.tecnico.BFTB.bank.grpc.checkStatusRequest.class,
      responseType = pt.tecnico.BFTB.bank.grpc.checkStatusResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<pt.tecnico.BFTB.bank.grpc.checkStatusRequest,
      pt.tecnico.BFTB.bank.grpc.checkStatusResponse> getCheckStatusMethod() {
    io.grpc.MethodDescriptor<pt.tecnico.BFTB.bank.grpc.checkStatusRequest, pt.tecnico.BFTB.bank.grpc.checkStatusResponse> getCheckStatusMethod;
    if ((getCheckStatusMethod = BFTServiceGrpc.getCheckStatusMethod) == null) {
      synchronized (BFTServiceGrpc.class) {
        if ((getCheckStatusMethod = BFTServiceGrpc.getCheckStatusMethod) == null) {
          BFTServiceGrpc.getCheckStatusMethod = getCheckStatusMethod =
              io.grpc.MethodDescriptor.<pt.tecnico.BFTB.bank.grpc.checkStatusRequest, pt.tecnico.BFTB.bank.grpc.checkStatusResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "checkStatus"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pt.tecnico.BFTB.bank.grpc.checkStatusRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pt.tecnico.BFTB.bank.grpc.checkStatusResponse.getDefaultInstance()))
              .setSchemaDescriptor(new BFTServiceMethodDescriptorSupplier("checkStatus"))
              .build();
        }
      }
    }
    return getCheckStatusMethod;
  }

  private static volatile io.grpc.MethodDescriptor<pt.tecnico.BFTB.bank.grpc.nonceRequest,
      pt.tecnico.BFTB.bank.grpc.nonceResponse> getGetNonceMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getNonce",
      requestType = pt.tecnico.BFTB.bank.grpc.nonceRequest.class,
      responseType = pt.tecnico.BFTB.bank.grpc.nonceResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<pt.tecnico.BFTB.bank.grpc.nonceRequest,
      pt.tecnico.BFTB.bank.grpc.nonceResponse> getGetNonceMethod() {
    io.grpc.MethodDescriptor<pt.tecnico.BFTB.bank.grpc.nonceRequest, pt.tecnico.BFTB.bank.grpc.nonceResponse> getGetNonceMethod;
    if ((getGetNonceMethod = BFTServiceGrpc.getGetNonceMethod) == null) {
      synchronized (BFTServiceGrpc.class) {
        if ((getGetNonceMethod = BFTServiceGrpc.getGetNonceMethod) == null) {
          BFTServiceGrpc.getGetNonceMethod = getGetNonceMethod =
              io.grpc.MethodDescriptor.<pt.tecnico.BFTB.bank.grpc.nonceRequest, pt.tecnico.BFTB.bank.grpc.nonceResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getNonce"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pt.tecnico.BFTB.bank.grpc.nonceRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pt.tecnico.BFTB.bank.grpc.nonceResponse.getDefaultInstance()))
              .setSchemaDescriptor(new BFTServiceMethodDescriptorSupplier("getNonce"))
              .build();
        }
      }
    }
    return getGetNonceMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static BFTServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<BFTServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<BFTServiceStub>() {
        @java.lang.Override
        public BFTServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new BFTServiceStub(channel, callOptions);
        }
      };
    return BFTServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static BFTServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<BFTServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<BFTServiceBlockingStub>() {
        @java.lang.Override
        public BFTServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new BFTServiceBlockingStub(channel, callOptions);
        }
      };
    return BFTServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static BFTServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<BFTServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<BFTServiceFutureStub>() {
        @java.lang.Override
        public BFTServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new BFTServiceFutureStub(channel, callOptions);
        }
      };
    return BFTServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class BFTServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void checkAccountWrite(pt.tecnico.BFTB.bank.grpc.checkAccountWriteRequest request,
        io.grpc.stub.StreamObserver<pt.tecnico.BFTB.bank.grpc.checkAccountWriteResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCheckAccountWriteMethod(), responseObserver);
    }

    /**
     */
    public void openAccount(pt.tecnico.BFTB.bank.grpc.openAccountRequest request,
        io.grpc.stub.StreamObserver<pt.tecnico.BFTB.bank.grpc.openAccountResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getOpenAccountMethod(), responseObserver);
    }

    /**
     */
    public void sendAmount(pt.tecnico.BFTB.bank.grpc.sendAmountRequest request,
        io.grpc.stub.StreamObserver<pt.tecnico.BFTB.bank.grpc.sendAmountResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSendAmountMethod(), responseObserver);
    }

    /**
     */
    public void checkAccount(pt.tecnico.BFTB.bank.grpc.checkAccountRequest request,
        io.grpc.stub.StreamObserver<pt.tecnico.BFTB.bank.grpc.checkAccountResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCheckAccountMethod(), responseObserver);
    }

    /**
     */
    public void receiveAmount(pt.tecnico.BFTB.bank.grpc.receiveAmountRequest request,
        io.grpc.stub.StreamObserver<pt.tecnico.BFTB.bank.grpc.receiveAmountResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getReceiveAmountMethod(), responseObserver);
    }

    /**
     */
    public void audit(pt.tecnico.BFTB.bank.grpc.auditRequest request,
        io.grpc.stub.StreamObserver<pt.tecnico.BFTB.bank.grpc.auditResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getAuditMethod(), responseObserver);
    }

    /**
     */
    public void checkStatus(pt.tecnico.BFTB.bank.grpc.checkStatusRequest request,
        io.grpc.stub.StreamObserver<pt.tecnico.BFTB.bank.grpc.checkStatusResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCheckStatusMethod(), responseObserver);
    }

    /**
     */
    public void getNonce(pt.tecnico.BFTB.bank.grpc.nonceRequest request,
        io.grpc.stub.StreamObserver<pt.tecnico.BFTB.bank.grpc.nonceResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetNonceMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getCheckAccountWriteMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                pt.tecnico.BFTB.bank.grpc.checkAccountWriteRequest,
                pt.tecnico.BFTB.bank.grpc.checkAccountWriteResponse>(
                  this, METHODID_CHECK_ACCOUNT_WRITE)))
          .addMethod(
            getOpenAccountMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                pt.tecnico.BFTB.bank.grpc.openAccountRequest,
                pt.tecnico.BFTB.bank.grpc.openAccountResponse>(
                  this, METHODID_OPEN_ACCOUNT)))
          .addMethod(
            getSendAmountMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                pt.tecnico.BFTB.bank.grpc.sendAmountRequest,
                pt.tecnico.BFTB.bank.grpc.sendAmountResponse>(
                  this, METHODID_SEND_AMOUNT)))
          .addMethod(
            getCheckAccountMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                pt.tecnico.BFTB.bank.grpc.checkAccountRequest,
                pt.tecnico.BFTB.bank.grpc.checkAccountResponse>(
                  this, METHODID_CHECK_ACCOUNT)))
          .addMethod(
            getReceiveAmountMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                pt.tecnico.BFTB.bank.grpc.receiveAmountRequest,
                pt.tecnico.BFTB.bank.grpc.receiveAmountResponse>(
                  this, METHODID_RECEIVE_AMOUNT)))
          .addMethod(
            getAuditMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                pt.tecnico.BFTB.bank.grpc.auditRequest,
                pt.tecnico.BFTB.bank.grpc.auditResponse>(
                  this, METHODID_AUDIT)))
          .addMethod(
            getCheckStatusMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                pt.tecnico.BFTB.bank.grpc.checkStatusRequest,
                pt.tecnico.BFTB.bank.grpc.checkStatusResponse>(
                  this, METHODID_CHECK_STATUS)))
          .addMethod(
            getGetNonceMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                pt.tecnico.BFTB.bank.grpc.nonceRequest,
                pt.tecnico.BFTB.bank.grpc.nonceResponse>(
                  this, METHODID_GET_NONCE)))
          .build();
    }
  }

  /**
   */
  public static final class BFTServiceStub extends io.grpc.stub.AbstractAsyncStub<BFTServiceStub> {
    private BFTServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected BFTServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new BFTServiceStub(channel, callOptions);
    }

    /**
     */
    public void checkAccountWrite(pt.tecnico.BFTB.bank.grpc.checkAccountWriteRequest request,
        io.grpc.stub.StreamObserver<pt.tecnico.BFTB.bank.grpc.checkAccountWriteResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCheckAccountWriteMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void openAccount(pt.tecnico.BFTB.bank.grpc.openAccountRequest request,
        io.grpc.stub.StreamObserver<pt.tecnico.BFTB.bank.grpc.openAccountResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getOpenAccountMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void sendAmount(pt.tecnico.BFTB.bank.grpc.sendAmountRequest request,
        io.grpc.stub.StreamObserver<pt.tecnico.BFTB.bank.grpc.sendAmountResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSendAmountMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void checkAccount(pt.tecnico.BFTB.bank.grpc.checkAccountRequest request,
        io.grpc.stub.StreamObserver<pt.tecnico.BFTB.bank.grpc.checkAccountResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCheckAccountMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void receiveAmount(pt.tecnico.BFTB.bank.grpc.receiveAmountRequest request,
        io.grpc.stub.StreamObserver<pt.tecnico.BFTB.bank.grpc.receiveAmountResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getReceiveAmountMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void audit(pt.tecnico.BFTB.bank.grpc.auditRequest request,
        io.grpc.stub.StreamObserver<pt.tecnico.BFTB.bank.grpc.auditResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getAuditMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void checkStatus(pt.tecnico.BFTB.bank.grpc.checkStatusRequest request,
        io.grpc.stub.StreamObserver<pt.tecnico.BFTB.bank.grpc.checkStatusResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCheckStatusMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getNonce(pt.tecnico.BFTB.bank.grpc.nonceRequest request,
        io.grpc.stub.StreamObserver<pt.tecnico.BFTB.bank.grpc.nonceResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetNonceMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class BFTServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<BFTServiceBlockingStub> {
    private BFTServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected BFTServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new BFTServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public pt.tecnico.BFTB.bank.grpc.checkAccountWriteResponse checkAccountWrite(pt.tecnico.BFTB.bank.grpc.checkAccountWriteRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCheckAccountWriteMethod(), getCallOptions(), request);
    }

    /**
     */
    public pt.tecnico.BFTB.bank.grpc.openAccountResponse openAccount(pt.tecnico.BFTB.bank.grpc.openAccountRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getOpenAccountMethod(), getCallOptions(), request);
    }

    /**
     */
    public pt.tecnico.BFTB.bank.grpc.sendAmountResponse sendAmount(pt.tecnico.BFTB.bank.grpc.sendAmountRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSendAmountMethod(), getCallOptions(), request);
    }

    /**
     */
    public pt.tecnico.BFTB.bank.grpc.checkAccountResponse checkAccount(pt.tecnico.BFTB.bank.grpc.checkAccountRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCheckAccountMethod(), getCallOptions(), request);
    }

    /**
     */
    public pt.tecnico.BFTB.bank.grpc.receiveAmountResponse receiveAmount(pt.tecnico.BFTB.bank.grpc.receiveAmountRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getReceiveAmountMethod(), getCallOptions(), request);
    }

    /**
     */
    public pt.tecnico.BFTB.bank.grpc.auditResponse audit(pt.tecnico.BFTB.bank.grpc.auditRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getAuditMethod(), getCallOptions(), request);
    }

    /**
     */
    public pt.tecnico.BFTB.bank.grpc.checkStatusResponse checkStatus(pt.tecnico.BFTB.bank.grpc.checkStatusRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCheckStatusMethod(), getCallOptions(), request);
    }

    /**
     */
    public pt.tecnico.BFTB.bank.grpc.nonceResponse getNonce(pt.tecnico.BFTB.bank.grpc.nonceRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetNonceMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class BFTServiceFutureStub extends io.grpc.stub.AbstractFutureStub<BFTServiceFutureStub> {
    private BFTServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected BFTServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new BFTServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<pt.tecnico.BFTB.bank.grpc.checkAccountWriteResponse> checkAccountWrite(
        pt.tecnico.BFTB.bank.grpc.checkAccountWriteRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCheckAccountWriteMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<pt.tecnico.BFTB.bank.grpc.openAccountResponse> openAccount(
        pt.tecnico.BFTB.bank.grpc.openAccountRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getOpenAccountMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<pt.tecnico.BFTB.bank.grpc.sendAmountResponse> sendAmount(
        pt.tecnico.BFTB.bank.grpc.sendAmountRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSendAmountMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<pt.tecnico.BFTB.bank.grpc.checkAccountResponse> checkAccount(
        pt.tecnico.BFTB.bank.grpc.checkAccountRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCheckAccountMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<pt.tecnico.BFTB.bank.grpc.receiveAmountResponse> receiveAmount(
        pt.tecnico.BFTB.bank.grpc.receiveAmountRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getReceiveAmountMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<pt.tecnico.BFTB.bank.grpc.auditResponse> audit(
        pt.tecnico.BFTB.bank.grpc.auditRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getAuditMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<pt.tecnico.BFTB.bank.grpc.checkStatusResponse> checkStatus(
        pt.tecnico.BFTB.bank.grpc.checkStatusRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCheckStatusMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<pt.tecnico.BFTB.bank.grpc.nonceResponse> getNonce(
        pt.tecnico.BFTB.bank.grpc.nonceRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetNonceMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_CHECK_ACCOUNT_WRITE = 0;
  private static final int METHODID_OPEN_ACCOUNT = 1;
  private static final int METHODID_SEND_AMOUNT = 2;
  private static final int METHODID_CHECK_ACCOUNT = 3;
  private static final int METHODID_RECEIVE_AMOUNT = 4;
  private static final int METHODID_AUDIT = 5;
  private static final int METHODID_CHECK_STATUS = 6;
  private static final int METHODID_GET_NONCE = 7;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final BFTServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(BFTServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_CHECK_ACCOUNT_WRITE:
          serviceImpl.checkAccountWrite((pt.tecnico.BFTB.bank.grpc.checkAccountWriteRequest) request,
              (io.grpc.stub.StreamObserver<pt.tecnico.BFTB.bank.grpc.checkAccountWriteResponse>) responseObserver);
          break;
        case METHODID_OPEN_ACCOUNT:
          serviceImpl.openAccount((pt.tecnico.BFTB.bank.grpc.openAccountRequest) request,
              (io.grpc.stub.StreamObserver<pt.tecnico.BFTB.bank.grpc.openAccountResponse>) responseObserver);
          break;
        case METHODID_SEND_AMOUNT:
          serviceImpl.sendAmount((pt.tecnico.BFTB.bank.grpc.sendAmountRequest) request,
              (io.grpc.stub.StreamObserver<pt.tecnico.BFTB.bank.grpc.sendAmountResponse>) responseObserver);
          break;
        case METHODID_CHECK_ACCOUNT:
          serviceImpl.checkAccount((pt.tecnico.BFTB.bank.grpc.checkAccountRequest) request,
              (io.grpc.stub.StreamObserver<pt.tecnico.BFTB.bank.grpc.checkAccountResponse>) responseObserver);
          break;
        case METHODID_RECEIVE_AMOUNT:
          serviceImpl.receiveAmount((pt.tecnico.BFTB.bank.grpc.receiveAmountRequest) request,
              (io.grpc.stub.StreamObserver<pt.tecnico.BFTB.bank.grpc.receiveAmountResponse>) responseObserver);
          break;
        case METHODID_AUDIT:
          serviceImpl.audit((pt.tecnico.BFTB.bank.grpc.auditRequest) request,
              (io.grpc.stub.StreamObserver<pt.tecnico.BFTB.bank.grpc.auditResponse>) responseObserver);
          break;
        case METHODID_CHECK_STATUS:
          serviceImpl.checkStatus((pt.tecnico.BFTB.bank.grpc.checkStatusRequest) request,
              (io.grpc.stub.StreamObserver<pt.tecnico.BFTB.bank.grpc.checkStatusResponse>) responseObserver);
          break;
        case METHODID_GET_NONCE:
          serviceImpl.getNonce((pt.tecnico.BFTB.bank.grpc.nonceRequest) request,
              (io.grpc.stub.StreamObserver<pt.tecnico.BFTB.bank.grpc.nonceResponse>) responseObserver);
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

  private static abstract class BFTServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    BFTServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return pt.tecnico.BFTB.bank.grpc.Bank.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("BFTService");
    }
  }

  private static final class BFTServiceFileDescriptorSupplier
      extends BFTServiceBaseDescriptorSupplier {
    BFTServiceFileDescriptorSupplier() {}
  }

  private static final class BFTServiceMethodDescriptorSupplier
      extends BFTServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    BFTServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (BFTServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new BFTServiceFileDescriptorSupplier())
              .addMethod(getCheckAccountWriteMethod())
              .addMethod(getOpenAccountMethod())
              .addMethod(getSendAmountMethod())
              .addMethod(getCheckAccountMethod())
              .addMethod(getReceiveAmountMethod())
              .addMethod(getAuditMethod())
              .addMethod(getCheckStatusMethod())
              .addMethod(getGetNonceMethod())
              .build();
        }
      }
    }
    return result;
  }
}
