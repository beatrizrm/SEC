// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: bank.proto

package pt.tecnico.BFTB.bank.grpc;

public final class Bank {
  private Bank() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_pt_tecnico_BFTB_bank_grpc_openAccountRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_pt_tecnico_BFTB_bank_grpc_openAccountRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_pt_tecnico_BFTB_bank_grpc_openAccountResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_pt_tecnico_BFTB_bank_grpc_openAccountResponse_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_pt_tecnico_BFTB_bank_grpc_auditRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_pt_tecnico_BFTB_bank_grpc_auditRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_pt_tecnico_BFTB_bank_grpc_auditResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_pt_tecnico_BFTB_bank_grpc_auditResponse_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_pt_tecnico_BFTB_bank_grpc_receiveAmountRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_pt_tecnico_BFTB_bank_grpc_receiveAmountRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_pt_tecnico_BFTB_bank_grpc_receiveAmountResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_pt_tecnico_BFTB_bank_grpc_receiveAmountResponse_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_pt_tecnico_BFTB_bank_grpc_checkAccountRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_pt_tecnico_BFTB_bank_grpc_checkAccountRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_pt_tecnico_BFTB_bank_grpc_checkAccountResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_pt_tecnico_BFTB_bank_grpc_checkAccountResponse_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_pt_tecnico_BFTB_bank_grpc_sendAmountRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_pt_tecnico_BFTB_bank_grpc_sendAmountRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_pt_tecnico_BFTB_bank_grpc_sendAmountResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_pt_tecnico_BFTB_bank_grpc_sendAmountResponse_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\nbank.proto\022\031pt.tecnico.BFTB.bank.grpc\"" +
      "!\n\022openAccountRequest\022\013\n\003key\030\001 \001(\t\"%\n\023op" +
      "enAccountResponse\022\016\n\006status\030\001 \001(\005\"\033\n\014aud" +
      "itRequest\022\013\n\003key\030\001 \001(\t\"+\n\rauditResponse\022" +
      "\032\n\022transactionHistory\030\001 \001(\t\":\n\024receiveAm" +
      "ountRequest\022\013\n\003key\030\001 \001(\t\022\025\n\rtransactionI" +
      "d\030\002 \001(\t\"\'\n\025receiveAmountResponse\022\016\n\006stat" +
      "us\030\001 \001(\005\"\"\n\023checkAccountRequest\022\013\n\003key\030\001" +
      " \001(\t\"D\n\024checkAccountResponse\022\017\n\007balance\030" +
      "\001 \001(\t\022\033\n\023pendingTransactions\030\002 \001(\t\"N\n\021se" +
      "ndAmountRequest\022\021\n\tsourceKey\030\001 \001(\t\022\026\n\016de" +
      "stinationKey\030\002 \001(\t\022\016\n\006amount\030\003 \001(\t\"$\n\022se" +
      "ndAmountResponse\022\016\n\006status\030\001 \001(\0052\247\004\n\013Ban" +
      "kService\022l\n\013openAccount\022-.pt.tecnico.BFT" +
      "B.bank.grpc.openAccountRequest\032..pt.tecn" +
      "ico.BFTB.bank.grpc.openAccountResponse\022i" +
      "\n\nsendAmount\022,.pt.tecnico.BFTB.bank.grpc" +
      ".sendAmountRequest\032-.pt.tecnico.BFTB.ban" +
      "k.grpc.sendAmountResponse\022o\n\014checkAccoun" +
      "t\022..pt.tecnico.BFTB.bank.grpc.checkAccou" +
      "ntRequest\032/.pt.tecnico.BFTB.bank.grpc.ch" +
      "eckAccountResponse\022r\n\rreceiveAmount\022/.pt" +
      ".tecnico.BFTB.bank.grpc.receiveAmountReq" +
      "uest\0320.pt.tecnico.BFTB.bank.grpc.receive" +
      "AmountResponse\022Z\n\005audit\022\'.pt.tecnico.BFT" +
      "B.bank.grpc.auditRequest\032(.pt.tecnico.BF" +
      "TB.bank.grpc.auditResponseB\002P\001b\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_pt_tecnico_BFTB_bank_grpc_openAccountRequest_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_pt_tecnico_BFTB_bank_grpc_openAccountRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_pt_tecnico_BFTB_bank_grpc_openAccountRequest_descriptor,
        new java.lang.String[] { "Key", });
    internal_static_pt_tecnico_BFTB_bank_grpc_openAccountResponse_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_pt_tecnico_BFTB_bank_grpc_openAccountResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_pt_tecnico_BFTB_bank_grpc_openAccountResponse_descriptor,
        new java.lang.String[] { "Status", });
    internal_static_pt_tecnico_BFTB_bank_grpc_auditRequest_descriptor =
      getDescriptor().getMessageTypes().get(2);
    internal_static_pt_tecnico_BFTB_bank_grpc_auditRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_pt_tecnico_BFTB_bank_grpc_auditRequest_descriptor,
        new java.lang.String[] { "Key", });
    internal_static_pt_tecnico_BFTB_bank_grpc_auditResponse_descriptor =
      getDescriptor().getMessageTypes().get(3);
    internal_static_pt_tecnico_BFTB_bank_grpc_auditResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_pt_tecnico_BFTB_bank_grpc_auditResponse_descriptor,
        new java.lang.String[] { "TransactionHistory", });
    internal_static_pt_tecnico_BFTB_bank_grpc_receiveAmountRequest_descriptor =
      getDescriptor().getMessageTypes().get(4);
    internal_static_pt_tecnico_BFTB_bank_grpc_receiveAmountRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_pt_tecnico_BFTB_bank_grpc_receiveAmountRequest_descriptor,
        new java.lang.String[] { "Key", "TransactionId", });
    internal_static_pt_tecnico_BFTB_bank_grpc_receiveAmountResponse_descriptor =
      getDescriptor().getMessageTypes().get(5);
    internal_static_pt_tecnico_BFTB_bank_grpc_receiveAmountResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_pt_tecnico_BFTB_bank_grpc_receiveAmountResponse_descriptor,
        new java.lang.String[] { "Status", });
    internal_static_pt_tecnico_BFTB_bank_grpc_checkAccountRequest_descriptor =
      getDescriptor().getMessageTypes().get(6);
    internal_static_pt_tecnico_BFTB_bank_grpc_checkAccountRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_pt_tecnico_BFTB_bank_grpc_checkAccountRequest_descriptor,
        new java.lang.String[] { "Key", });
    internal_static_pt_tecnico_BFTB_bank_grpc_checkAccountResponse_descriptor =
      getDescriptor().getMessageTypes().get(7);
    internal_static_pt_tecnico_BFTB_bank_grpc_checkAccountResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_pt_tecnico_BFTB_bank_grpc_checkAccountResponse_descriptor,
        new java.lang.String[] { "Balance", "PendingTransactions", });
    internal_static_pt_tecnico_BFTB_bank_grpc_sendAmountRequest_descriptor =
      getDescriptor().getMessageTypes().get(8);
    internal_static_pt_tecnico_BFTB_bank_grpc_sendAmountRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_pt_tecnico_BFTB_bank_grpc_sendAmountRequest_descriptor,
        new java.lang.String[] { "SourceKey", "DestinationKey", "Amount", });
    internal_static_pt_tecnico_BFTB_bank_grpc_sendAmountResponse_descriptor =
      getDescriptor().getMessageTypes().get(9);
    internal_static_pt_tecnico_BFTB_bank_grpc_sendAmountResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_pt_tecnico_BFTB_bank_grpc_sendAmountResponse_descriptor,
        new java.lang.String[] { "Status", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
