// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: bank.proto

package pt.tecnico.BFTB.bank.grpc;

public interface checkAccountResponseOrBuilder extends
    // @@protoc_insertion_point(interface_extends:pt.tecnico.BFTB.bank.grpc.checkAccountResponse)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>string balance = 1;</code>
   * @return The balance.
   */
  java.lang.String getBalance();
  /**
   * <code>string balance = 1;</code>
   * @return The bytes for balance.
   */
  com.google.protobuf.ByteString
      getBalanceBytes();

  /**
   * <code>string pendingTransactions = 2;</code>
   * @return The pendingTransactions.
   */
  java.lang.String getPendingTransactions();
  /**
   * <code>string pendingTransactions = 2;</code>
   * @return The bytes for pendingTransactions.
   */
  com.google.protobuf.ByteString
      getPendingTransactionsBytes();
}
