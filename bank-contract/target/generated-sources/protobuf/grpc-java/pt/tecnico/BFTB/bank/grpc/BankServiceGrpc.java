package pt.tecnico.BFTB.bank.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.36.0)",
    comments = "Source: bank.proto")
public final class BankServiceGrpc {

  private BankServiceGrpc() {}

  public static final String SERVICE_NAME = "pt.tecnico.BFTB.bank.grpc.BankService";

  // Static method descriptors that strictly reflect the proto.
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
    if ((getOpenAccountMethod = BankServiceGrpc.getOpenAccountMethod) == null) {
      synchronized (BankServiceGrpc.class) {
        if ((getOpenAccountMethod = BankServiceGrpc.getOpenAccountMethod) == null) {
          BankServiceGrpc.getOpenAccountMethod = getOpenAccountMethod =
              io.grpc.MethodDescriptor.<pt.tecnico.BFTB.bank.grpc.openAccountRequest, pt.tecnico.BFTB.bank.grpc.openAccountResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "openAccount"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pt.tecnico.BFTB.bank.grpc.openAccountRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pt.tecnico.BFTB.bank.grpc.openAccountResponse.getDefaultInstance()))
              .setSchemaDescriptor(new BankServiceMethodDescriptorSupplier("openAccount"))
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
    if ((getSendAmountMethod = BankServiceGrpc.getSendAmountMethod) == null) {
      synchronized (BankServiceGrpc.class) {
        if ((getSendAmountMethod = BankServiceGrpc.getSendAmountMethod) == null) {
          BankServiceGrpc.getSendAmountMethod = getSendAmountMethod =
              io.grpc.MethodDescriptor.<pt.tecnico.BFTB.bank.grpc.sendAmountRequest, pt.tecnico.BFTB.bank.grpc.sendAmountResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "sendAmount"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pt.tecnico.BFTB.bank.grpc.sendAmountRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pt.tecnico.BFTB.bank.grpc.sendAmountResponse.getDefaultInstance()))
              .setSchemaDescriptor(new BankServiceMethodDescriptorSupplier("sendAmount"))
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
    if ((getCheckAccountMethod = BankServiceGrpc.getCheckAccountMethod) == null) {
      synchronized (BankServiceGrpc.class) {
        if ((getCheckAccountMethod = BankServiceGrpc.getCheckAccountMethod) == null) {
          BankServiceGrpc.getCheckAccountMethod = getCheckAccountMethod =
              io.grpc.MethodDescriptor.<pt.tecnico.BFTB.bank.grpc.checkAccountRequest, pt.tecnico.BFTB.bank.grpc.checkAccountResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "checkAccount"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pt.tecnico.BFTB.bank.grpc.checkAccountRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pt.tecnico.BFTB.bank.grpc.checkAccountResponse.getDefaultInstance()))
              .setSchemaDescriptor(new BankServiceMethodDescriptorSupplier("checkAccount"))
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
    if ((getReceiveAmountMethod = BankServiceGrpc.getReceiveAmountMethod) == null) {
      synchronized (BankServiceGrpc.class) {
        if ((getReceiveAmountMethod = BankServiceGrpc.getReceiveAmountMethod) == null) {
          BankServiceGrpc.getReceiveAmountMethod = getReceiveAmountMethod =
              io.grpc.MethodDescriptor.<pt.tecnico.BFTB.bank.grpc.receiveAmountRequest, pt.tecnico.BFTB.bank.grpc.receiveAmountResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "receiveAmount"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pt.tecnico.BFTB.bank.grpc.receiveAmountRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pt.tecnico.BFTB.bank.grpc.receiveAmountResponse.getDefaultInstance()))
              .setSchemaDescriptor(new BankServiceMethodDescriptorSupplier("receiveAmount"))
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
    if ((getAuditMethod = BankServiceGrpc.getAuditMethod) == null) {
      synchronized (BankServiceGrpc.class) {
        if ((getAuditMethod = BankServiceGrpc.getAuditMethod) == null) {
          BankServiceGrpc.getAuditMethod = getAuditMethod =
              io.grpc.MethodDescriptor.<pt.tecnico.BFTB.bank.grpc.auditRequest, pt.tecnico.BFTB.bank.grpc.auditResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "audit"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pt.tecnico.BFTB.bank.grpc.auditRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pt.tecnico.BFTB.bank.grpc.auditResponse.getDefaultInstance()))
              .setSchemaDescriptor(new BankServiceMethodDescriptorSupplier("audit"))
              .build();
        }
      }
    }
    return getAuditMethod;
  }

  private static volatile io.grpc.MethodDescriptor<pt.tecnico.BFTB.bank.grpc.BankReadBalanceRequest,
      pt.tecnico.BFTB.bank.grpc.BankReadBalanceResponse> getReadBalanceMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "readBalance",
      requestType = pt.tecnico.BFTB.bank.grpc.BankReadBalanceRequest.class,
      responseType = pt.tecnico.BFTB.bank.grpc.BankReadBalanceResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<pt.tecnico.BFTB.bank.grpc.BankReadBalanceRequest,
      pt.tecnico.BFTB.bank.grpc.BankReadBalanceResponse> getReadBalanceMethod() {
    io.grpc.MethodDescriptor<pt.tecnico.BFTB.bank.grpc.BankReadBalanceRequest, pt.tecnico.BFTB.bank.grpc.BankReadBalanceResponse> getReadBalanceMethod;
    if ((getReadBalanceMethod = BankServiceGrpc.getReadBalanceMethod) == null) {
      synchronized (BankServiceGrpc.class) {
        if ((getReadBalanceMethod = BankServiceGrpc.getReadBalanceMethod) == null) {
          BankServiceGrpc.getReadBalanceMethod = getReadBalanceMethod =
              io.grpc.MethodDescriptor.<pt.tecnico.BFTB.bank.grpc.BankReadBalanceRequest, pt.tecnico.BFTB.bank.grpc.BankReadBalanceResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "readBalance"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pt.tecnico.BFTB.bank.grpc.BankReadBalanceRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pt.tecnico.BFTB.bank.grpc.BankReadBalanceResponse.getDefaultInstance()))
              .setSchemaDescriptor(new BankServiceMethodDescriptorSupplier("readBalance"))
              .build();
        }
      }
    }
    return getReadBalanceMethod;
  }

  private static volatile io.grpc.MethodDescriptor<pt.tecnico.BFTB.bank.grpc.BankReadTransactionHistoryRequest,
      pt.tecnico.BFTB.bank.grpc.BankReadTransactionHistoryResponse> getReadTransactionHistoryMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "readTransactionHistory",
      requestType = pt.tecnico.BFTB.bank.grpc.BankReadTransactionHistoryRequest.class,
      responseType = pt.tecnico.BFTB.bank.grpc.BankReadTransactionHistoryResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<pt.tecnico.BFTB.bank.grpc.BankReadTransactionHistoryRequest,
      pt.tecnico.BFTB.bank.grpc.BankReadTransactionHistoryResponse> getReadTransactionHistoryMethod() {
    io.grpc.MethodDescriptor<pt.tecnico.BFTB.bank.grpc.BankReadTransactionHistoryRequest, pt.tecnico.BFTB.bank.grpc.BankReadTransactionHistoryResponse> getReadTransactionHistoryMethod;
    if ((getReadTransactionHistoryMethod = BankServiceGrpc.getReadTransactionHistoryMethod) == null) {
      synchronized (BankServiceGrpc.class) {
        if ((getReadTransactionHistoryMethod = BankServiceGrpc.getReadTransactionHistoryMethod) == null) {
          BankServiceGrpc.getReadTransactionHistoryMethod = getReadTransactionHistoryMethod =
              io.grpc.MethodDescriptor.<pt.tecnico.BFTB.bank.grpc.BankReadTransactionHistoryRequest, pt.tecnico.BFTB.bank.grpc.BankReadTransactionHistoryResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "readTransactionHistory"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pt.tecnico.BFTB.bank.grpc.BankReadTransactionHistoryRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pt.tecnico.BFTB.bank.grpc.BankReadTransactionHistoryResponse.getDefaultInstance()))
              .setSchemaDescriptor(new BankServiceMethodDescriptorSupplier("readTransactionHistory"))
              .build();
        }
      }
    }
    return getReadTransactionHistoryMethod;
  }

  private static volatile io.grpc.MethodDescriptor<pt.tecnico.BFTB.bank.grpc.BankWriteBalanceRequest,
      pt.tecnico.BFTB.bank.grpc.BankWriteBalanceResponse> getWriteBalanceMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "writeBalance",
      requestType = pt.tecnico.BFTB.bank.grpc.BankWriteBalanceRequest.class,
      responseType = pt.tecnico.BFTB.bank.grpc.BankWriteBalanceResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<pt.tecnico.BFTB.bank.grpc.BankWriteBalanceRequest,
      pt.tecnico.BFTB.bank.grpc.BankWriteBalanceResponse> getWriteBalanceMethod() {
    io.grpc.MethodDescriptor<pt.tecnico.BFTB.bank.grpc.BankWriteBalanceRequest, pt.tecnico.BFTB.bank.grpc.BankWriteBalanceResponse> getWriteBalanceMethod;
    if ((getWriteBalanceMethod = BankServiceGrpc.getWriteBalanceMethod) == null) {
      synchronized (BankServiceGrpc.class) {
        if ((getWriteBalanceMethod = BankServiceGrpc.getWriteBalanceMethod) == null) {
          BankServiceGrpc.getWriteBalanceMethod = getWriteBalanceMethod =
              io.grpc.MethodDescriptor.<pt.tecnico.BFTB.bank.grpc.BankWriteBalanceRequest, pt.tecnico.BFTB.bank.grpc.BankWriteBalanceResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "writeBalance"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pt.tecnico.BFTB.bank.grpc.BankWriteBalanceRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pt.tecnico.BFTB.bank.grpc.BankWriteBalanceResponse.getDefaultInstance()))
              .setSchemaDescriptor(new BankServiceMethodDescriptorSupplier("writeBalance"))
              .build();
        }
      }
    }
    return getWriteBalanceMethod;
  }

  private static volatile io.grpc.MethodDescriptor<pt.tecnico.BFTB.bank.grpc.BankWriteTransactionHistoryRequest,
      pt.tecnico.BFTB.bank.grpc.BankWriteTransactionHistoryResponse> getWriteTransactionHistoryMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "writeTransactionHistory",
      requestType = pt.tecnico.BFTB.bank.grpc.BankWriteTransactionHistoryRequest.class,
      responseType = pt.tecnico.BFTB.bank.grpc.BankWriteTransactionHistoryResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<pt.tecnico.BFTB.bank.grpc.BankWriteTransactionHistoryRequest,
      pt.tecnico.BFTB.bank.grpc.BankWriteTransactionHistoryResponse> getWriteTransactionHistoryMethod() {
    io.grpc.MethodDescriptor<pt.tecnico.BFTB.bank.grpc.BankWriteTransactionHistoryRequest, pt.tecnico.BFTB.bank.grpc.BankWriteTransactionHistoryResponse> getWriteTransactionHistoryMethod;
    if ((getWriteTransactionHistoryMethod = BankServiceGrpc.getWriteTransactionHistoryMethod) == null) {
      synchronized (BankServiceGrpc.class) {
        if ((getWriteTransactionHistoryMethod = BankServiceGrpc.getWriteTransactionHistoryMethod) == null) {
          BankServiceGrpc.getWriteTransactionHistoryMethod = getWriteTransactionHistoryMethod =
              io.grpc.MethodDescriptor.<pt.tecnico.BFTB.bank.grpc.BankWriteTransactionHistoryRequest, pt.tecnico.BFTB.bank.grpc.BankWriteTransactionHistoryResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "writeTransactionHistory"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pt.tecnico.BFTB.bank.grpc.BankWriteTransactionHistoryRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pt.tecnico.BFTB.bank.grpc.BankWriteTransactionHistoryResponse.getDefaultInstance()))
              .setSchemaDescriptor(new BankServiceMethodDescriptorSupplier("writeTransactionHistory"))
              .build();
        }
      }
    }
    return getWriteTransactionHistoryMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static BankServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<BankServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<BankServiceStub>() {
        @java.lang.Override
        public BankServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new BankServiceStub(channel, callOptions);
        }
      };
    return BankServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static BankServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<BankServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<BankServiceBlockingStub>() {
        @java.lang.Override
        public BankServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new BankServiceBlockingStub(channel, callOptions);
        }
      };
    return BankServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static BankServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<BankServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<BankServiceFutureStub>() {
        @java.lang.Override
        public BankServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new BankServiceFutureStub(channel, callOptions);
        }
      };
    return BankServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class BankServiceImplBase implements io.grpc.BindableService {

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
    public void readBalance(pt.tecnico.BFTB.bank.grpc.BankReadBalanceRequest request,
        io.grpc.stub.StreamObserver<pt.tecnico.BFTB.bank.grpc.BankReadBalanceResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getReadBalanceMethod(), responseObserver);
    }

    /**
     */
    public void readTransactionHistory(pt.tecnico.BFTB.bank.grpc.BankReadTransactionHistoryRequest request,
        io.grpc.stub.StreamObserver<pt.tecnico.BFTB.bank.grpc.BankReadTransactionHistoryResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getReadTransactionHistoryMethod(), responseObserver);
    }

    /**
     */
    public void writeBalance(pt.tecnico.BFTB.bank.grpc.BankWriteBalanceRequest request,
        io.grpc.stub.StreamObserver<pt.tecnico.BFTB.bank.grpc.BankWriteBalanceResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getWriteBalanceMethod(), responseObserver);
    }

    /**
     */
    public void writeTransactionHistory(pt.tecnico.BFTB.bank.grpc.BankWriteTransactionHistoryRequest request,
        io.grpc.stub.StreamObserver<pt.tecnico.BFTB.bank.grpc.BankWriteTransactionHistoryResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getWriteTransactionHistoryMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
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
            getReadBalanceMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                pt.tecnico.BFTB.bank.grpc.BankReadBalanceRequest,
                pt.tecnico.BFTB.bank.grpc.BankReadBalanceResponse>(
                  this, METHODID_READ_BALANCE)))
          .addMethod(
            getReadTransactionHistoryMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                pt.tecnico.BFTB.bank.grpc.BankReadTransactionHistoryRequest,
                pt.tecnico.BFTB.bank.grpc.BankReadTransactionHistoryResponse>(
                  this, METHODID_READ_TRANSACTION_HISTORY)))
          .addMethod(
            getWriteBalanceMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                pt.tecnico.BFTB.bank.grpc.BankWriteBalanceRequest,
                pt.tecnico.BFTB.bank.grpc.BankWriteBalanceResponse>(
                  this, METHODID_WRITE_BALANCE)))
          .addMethod(
            getWriteTransactionHistoryMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                pt.tecnico.BFTB.bank.grpc.BankWriteTransactionHistoryRequest,
                pt.tecnico.BFTB.bank.grpc.BankWriteTransactionHistoryResponse>(
                  this, METHODID_WRITE_TRANSACTION_HISTORY)))
          .build();
    }
  }

  /**
   */
  public static final class BankServiceStub extends io.grpc.stub.AbstractAsyncStub<BankServiceStub> {
    private BankServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected BankServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new BankServiceStub(channel, callOptions);
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
    public void readBalance(pt.tecnico.BFTB.bank.grpc.BankReadBalanceRequest request,
        io.grpc.stub.StreamObserver<pt.tecnico.BFTB.bank.grpc.BankReadBalanceResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getReadBalanceMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void readTransactionHistory(pt.tecnico.BFTB.bank.grpc.BankReadTransactionHistoryRequest request,
        io.grpc.stub.StreamObserver<pt.tecnico.BFTB.bank.grpc.BankReadTransactionHistoryResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getReadTransactionHistoryMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void writeBalance(pt.tecnico.BFTB.bank.grpc.BankWriteBalanceRequest request,
        io.grpc.stub.StreamObserver<pt.tecnico.BFTB.bank.grpc.BankWriteBalanceResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getWriteBalanceMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void writeTransactionHistory(pt.tecnico.BFTB.bank.grpc.BankWriteTransactionHistoryRequest request,
        io.grpc.stub.StreamObserver<pt.tecnico.BFTB.bank.grpc.BankWriteTransactionHistoryResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getWriteTransactionHistoryMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class BankServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<BankServiceBlockingStub> {
    private BankServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected BankServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new BankServiceBlockingStub(channel, callOptions);
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
    public pt.tecnico.BFTB.bank.grpc.BankReadBalanceResponse readBalance(pt.tecnico.BFTB.bank.grpc.BankReadBalanceRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getReadBalanceMethod(), getCallOptions(), request);
    }

    /**
     */
    public pt.tecnico.BFTB.bank.grpc.BankReadTransactionHistoryResponse readTransactionHistory(pt.tecnico.BFTB.bank.grpc.BankReadTransactionHistoryRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getReadTransactionHistoryMethod(), getCallOptions(), request);
    }

    /**
     */
    public pt.tecnico.BFTB.bank.grpc.BankWriteBalanceResponse writeBalance(pt.tecnico.BFTB.bank.grpc.BankWriteBalanceRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getWriteBalanceMethod(), getCallOptions(), request);
    }

    /**
     */
    public pt.tecnico.BFTB.bank.grpc.BankWriteTransactionHistoryResponse writeTransactionHistory(pt.tecnico.BFTB.bank.grpc.BankWriteTransactionHistoryRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getWriteTransactionHistoryMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class BankServiceFutureStub extends io.grpc.stub.AbstractFutureStub<BankServiceFutureStub> {
    private BankServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected BankServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new BankServiceFutureStub(channel, callOptions);
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
    public com.google.common.util.concurrent.ListenableFuture<pt.tecnico.BFTB.bank.grpc.BankReadBalanceResponse> readBalance(
        pt.tecnico.BFTB.bank.grpc.BankReadBalanceRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getReadBalanceMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<pt.tecnico.BFTB.bank.grpc.BankReadTransactionHistoryResponse> readTransactionHistory(
        pt.tecnico.BFTB.bank.grpc.BankReadTransactionHistoryRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getReadTransactionHistoryMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<pt.tecnico.BFTB.bank.grpc.BankWriteBalanceResponse> writeBalance(
        pt.tecnico.BFTB.bank.grpc.BankWriteBalanceRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getWriteBalanceMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<pt.tecnico.BFTB.bank.grpc.BankWriteTransactionHistoryResponse> writeTransactionHistory(
        pt.tecnico.BFTB.bank.grpc.BankWriteTransactionHistoryRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getWriteTransactionHistoryMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_OPEN_ACCOUNT = 0;
  private static final int METHODID_SEND_AMOUNT = 1;
  private static final int METHODID_CHECK_ACCOUNT = 2;
  private static final int METHODID_RECEIVE_AMOUNT = 3;
  private static final int METHODID_AUDIT = 4;
  private static final int METHODID_READ_BALANCE = 5;
  private static final int METHODID_READ_TRANSACTION_HISTORY = 6;
  private static final int METHODID_WRITE_BALANCE = 7;
  private static final int METHODID_WRITE_TRANSACTION_HISTORY = 8;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final BankServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(BankServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
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
        case METHODID_READ_BALANCE:
          serviceImpl.readBalance((pt.tecnico.BFTB.bank.grpc.BankReadBalanceRequest) request,
              (io.grpc.stub.StreamObserver<pt.tecnico.BFTB.bank.grpc.BankReadBalanceResponse>) responseObserver);
          break;
        case METHODID_READ_TRANSACTION_HISTORY:
          serviceImpl.readTransactionHistory((pt.tecnico.BFTB.bank.grpc.BankReadTransactionHistoryRequest) request,
              (io.grpc.stub.StreamObserver<pt.tecnico.BFTB.bank.grpc.BankReadTransactionHistoryResponse>) responseObserver);
          break;
        case METHODID_WRITE_BALANCE:
          serviceImpl.writeBalance((pt.tecnico.BFTB.bank.grpc.BankWriteBalanceRequest) request,
              (io.grpc.stub.StreamObserver<pt.tecnico.BFTB.bank.grpc.BankWriteBalanceResponse>) responseObserver);
          break;
        case METHODID_WRITE_TRANSACTION_HISTORY:
          serviceImpl.writeTransactionHistory((pt.tecnico.BFTB.bank.grpc.BankWriteTransactionHistoryRequest) request,
              (io.grpc.stub.StreamObserver<pt.tecnico.BFTB.bank.grpc.BankWriteTransactionHistoryResponse>) responseObserver);
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

  private static abstract class BankServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    BankServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return pt.tecnico.BFTB.bank.grpc.Bank.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("BankService");
    }
  }

  private static final class BankServiceFileDescriptorSupplier
      extends BankServiceBaseDescriptorSupplier {
    BankServiceFileDescriptorSupplier() {}
  }

  private static final class BankServiceMethodDescriptorSupplier
      extends BankServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    BankServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (BankServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new BankServiceFileDescriptorSupplier())
              .addMethod(getOpenAccountMethod())
              .addMethod(getSendAmountMethod())
              .addMethod(getCheckAccountMethod())
              .addMethod(getReceiveAmountMethod())
              .addMethod(getAuditMethod())
              .addMethod(getReadBalanceMethod())
              .addMethod(getReadTransactionHistoryMethod())
              .addMethod(getWriteBalanceMethod())
              .addMethod(getWriteTransactionHistoryMethod())
              .build();
        }
      }
    }
    return result;
  }
}
