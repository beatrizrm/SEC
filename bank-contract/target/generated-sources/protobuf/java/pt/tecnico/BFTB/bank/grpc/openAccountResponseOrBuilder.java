// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: bank.proto

package pt.tecnico.BFTB.bank.grpc;

public interface openAccountResponseOrBuilder extends
    // @@protoc_insertion_point(interface_extends:pt.tecnico.BFTB.bank.grpc.openAccountResponse)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>.pt.tecnico.BFTB.bank.grpc.Status status = 1;</code>
   * @return Whether the status field is set.
   */
  boolean hasStatus();
  /**
   * <code>.pt.tecnico.BFTB.bank.grpc.Status status = 1;</code>
   * @return The status.
   */
  pt.tecnico.BFTB.bank.grpc.Status getStatus();
  /**
   * <code>.pt.tecnico.BFTB.bank.grpc.Status status = 1;</code>
   */
  pt.tecnico.BFTB.bank.grpc.StatusOrBuilder getStatusOrBuilder();

  /**
   * <code>string signature = 2;</code>
   * @return The signature.
   */
  java.lang.String getSignature();
  /**
   * <code>string signature = 2;</code>
   * @return The bytes for signature.
   */
  com.google.protobuf.ByteString
      getSignatureBytes();
}
