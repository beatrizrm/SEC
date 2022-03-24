// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: bank.proto

package pt.tecnico.BFTB.bank.grpc;

/**
 * Protobuf type {@code pt.tecnico.BFTB.bank.grpc.checkAccountResponse}
 */
public final class checkAccountResponse extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:pt.tecnico.BFTB.bank.grpc.checkAccountResponse)
    checkAccountResponseOrBuilder {
private static final long serialVersionUID = 0L;
  // Use checkAccountResponse.newBuilder() to construct.
  private checkAccountResponse(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private checkAccountResponse() {
    balance_ = "";
    pendingTransactions_ = "";
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new checkAccountResponse();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private checkAccountResponse(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    if (extensionRegistry == null) {
      throw new java.lang.NullPointerException();
    }
    com.google.protobuf.UnknownFieldSet.Builder unknownFields =
        com.google.protobuf.UnknownFieldSet.newBuilder();
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          case 10: {
            java.lang.String s = input.readStringRequireUtf8();

            balance_ = s;
            break;
          }
          case 18: {
            java.lang.String s = input.readStringRequireUtf8();

            pendingTransactions_ = s;
            break;
          }
          default: {
            if (!parseUnknownField(
                input, unknownFields, extensionRegistry, tag)) {
              done = true;
            }
            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
          e).setUnfinishedMessage(this);
    } finally {
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return pt.tecnico.BFTB.bank.grpc.Bank.internal_static_pt_tecnico_BFTB_bank_grpc_checkAccountResponse_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return pt.tecnico.BFTB.bank.grpc.Bank.internal_static_pt_tecnico_BFTB_bank_grpc_checkAccountResponse_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            pt.tecnico.BFTB.bank.grpc.checkAccountResponse.class, pt.tecnico.BFTB.bank.grpc.checkAccountResponse.Builder.class);
  }

  public static final int BALANCE_FIELD_NUMBER = 1;
  private volatile java.lang.Object balance_;
  /**
   * <code>string balance = 1;</code>
   * @return The balance.
   */
  @java.lang.Override
  public java.lang.String getBalance() {
    java.lang.Object ref = balance_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      balance_ = s;
      return s;
    }
  }
  /**
   * <code>string balance = 1;</code>
   * @return The bytes for balance.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString
      getBalanceBytes() {
    java.lang.Object ref = balance_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      balance_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int PENDINGTRANSACTIONS_FIELD_NUMBER = 2;
  private volatile java.lang.Object pendingTransactions_;
  /**
   * <code>string pendingTransactions = 2;</code>
   * @return The pendingTransactions.
   */
  @java.lang.Override
  public java.lang.String getPendingTransactions() {
    java.lang.Object ref = pendingTransactions_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      pendingTransactions_ = s;
      return s;
    }
  }
  /**
   * <code>string pendingTransactions = 2;</code>
   * @return The bytes for pendingTransactions.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString
      getPendingTransactionsBytes() {
    java.lang.Object ref = pendingTransactions_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      pendingTransactions_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  private byte memoizedIsInitialized = -1;
  @java.lang.Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @java.lang.Override
  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (!getBalanceBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 1, balance_);
    }
    if (!getPendingTransactionsBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 2, pendingTransactions_);
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (!getBalanceBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, balance_);
    }
    if (!getPendingTransactionsBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, pendingTransactions_);
    }
    size += unknownFields.getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof pt.tecnico.BFTB.bank.grpc.checkAccountResponse)) {
      return super.equals(obj);
    }
    pt.tecnico.BFTB.bank.grpc.checkAccountResponse other = (pt.tecnico.BFTB.bank.grpc.checkAccountResponse) obj;

    if (!getBalance()
        .equals(other.getBalance())) return false;
    if (!getPendingTransactions()
        .equals(other.getPendingTransactions())) return false;
    if (!unknownFields.equals(other.unknownFields)) return false;
    return true;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    hash = (37 * hash) + BALANCE_FIELD_NUMBER;
    hash = (53 * hash) + getBalance().hashCode();
    hash = (37 * hash) + PENDINGTRANSACTIONS_FIELD_NUMBER;
    hash = (53 * hash) + getPendingTransactions().hashCode();
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static pt.tecnico.BFTB.bank.grpc.checkAccountResponse parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static pt.tecnico.BFTB.bank.grpc.checkAccountResponse parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static pt.tecnico.BFTB.bank.grpc.checkAccountResponse parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static pt.tecnico.BFTB.bank.grpc.checkAccountResponse parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static pt.tecnico.BFTB.bank.grpc.checkAccountResponse parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static pt.tecnico.BFTB.bank.grpc.checkAccountResponse parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static pt.tecnico.BFTB.bank.grpc.checkAccountResponse parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static pt.tecnico.BFTB.bank.grpc.checkAccountResponse parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static pt.tecnico.BFTB.bank.grpc.checkAccountResponse parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static pt.tecnico.BFTB.bank.grpc.checkAccountResponse parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static pt.tecnico.BFTB.bank.grpc.checkAccountResponse parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static pt.tecnico.BFTB.bank.grpc.checkAccountResponse parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @java.lang.Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(pt.tecnico.BFTB.bank.grpc.checkAccountResponse prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  @java.lang.Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code pt.tecnico.BFTB.bank.grpc.checkAccountResponse}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:pt.tecnico.BFTB.bank.grpc.checkAccountResponse)
      pt.tecnico.BFTB.bank.grpc.checkAccountResponseOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return pt.tecnico.BFTB.bank.grpc.Bank.internal_static_pt_tecnico_BFTB_bank_grpc_checkAccountResponse_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return pt.tecnico.BFTB.bank.grpc.Bank.internal_static_pt_tecnico_BFTB_bank_grpc_checkAccountResponse_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              pt.tecnico.BFTB.bank.grpc.checkAccountResponse.class, pt.tecnico.BFTB.bank.grpc.checkAccountResponse.Builder.class);
    }

    // Construct using pt.tecnico.BFTB.bank.grpc.checkAccountResponse.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
      }
    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      balance_ = "";

      pendingTransactions_ = "";

      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return pt.tecnico.BFTB.bank.grpc.Bank.internal_static_pt_tecnico_BFTB_bank_grpc_checkAccountResponse_descriptor;
    }

    @java.lang.Override
    public pt.tecnico.BFTB.bank.grpc.checkAccountResponse getDefaultInstanceForType() {
      return pt.tecnico.BFTB.bank.grpc.checkAccountResponse.getDefaultInstance();
    }

    @java.lang.Override
    public pt.tecnico.BFTB.bank.grpc.checkAccountResponse build() {
      pt.tecnico.BFTB.bank.grpc.checkAccountResponse result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public pt.tecnico.BFTB.bank.grpc.checkAccountResponse buildPartial() {
      pt.tecnico.BFTB.bank.grpc.checkAccountResponse result = new pt.tecnico.BFTB.bank.grpc.checkAccountResponse(this);
      result.balance_ = balance_;
      result.pendingTransactions_ = pendingTransactions_;
      onBuilt();
      return result;
    }

    @java.lang.Override
    public Builder clone() {
      return super.clone();
    }
    @java.lang.Override
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.setField(field, value);
    }
    @java.lang.Override
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return super.clearField(field);
    }
    @java.lang.Override
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return super.clearOneof(oneof);
    }
    @java.lang.Override
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, java.lang.Object value) {
      return super.setRepeatedField(field, index, value);
    }
    @java.lang.Override
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.addRepeatedField(field, value);
    }
    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof pt.tecnico.BFTB.bank.grpc.checkAccountResponse) {
        return mergeFrom((pt.tecnico.BFTB.bank.grpc.checkAccountResponse)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(pt.tecnico.BFTB.bank.grpc.checkAccountResponse other) {
      if (other == pt.tecnico.BFTB.bank.grpc.checkAccountResponse.getDefaultInstance()) return this;
      if (!other.getBalance().isEmpty()) {
        balance_ = other.balance_;
        onChanged();
      }
      if (!other.getPendingTransactions().isEmpty()) {
        pendingTransactions_ = other.pendingTransactions_;
        onChanged();
      }
      this.mergeUnknownFields(other.unknownFields);
      onChanged();
      return this;
    }

    @java.lang.Override
    public final boolean isInitialized() {
      return true;
    }

    @java.lang.Override
    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      pt.tecnico.BFTB.bank.grpc.checkAccountResponse parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (pt.tecnico.BFTB.bank.grpc.checkAccountResponse) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private java.lang.Object balance_ = "";
    /**
     * <code>string balance = 1;</code>
     * @return The balance.
     */
    public java.lang.String getBalance() {
      java.lang.Object ref = balance_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        balance_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string balance = 1;</code>
     * @return The bytes for balance.
     */
    public com.google.protobuf.ByteString
        getBalanceBytes() {
      java.lang.Object ref = balance_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        balance_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string balance = 1;</code>
     * @param value The balance to set.
     * @return This builder for chaining.
     */
    public Builder setBalance(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      balance_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string balance = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearBalance() {
      
      balance_ = getDefaultInstance().getBalance();
      onChanged();
      return this;
    }
    /**
     * <code>string balance = 1;</code>
     * @param value The bytes for balance to set.
     * @return This builder for chaining.
     */
    public Builder setBalanceBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      balance_ = value;
      onChanged();
      return this;
    }

    private java.lang.Object pendingTransactions_ = "";
    /**
     * <code>string pendingTransactions = 2;</code>
     * @return The pendingTransactions.
     */
    public java.lang.String getPendingTransactions() {
      java.lang.Object ref = pendingTransactions_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        pendingTransactions_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string pendingTransactions = 2;</code>
     * @return The bytes for pendingTransactions.
     */
    public com.google.protobuf.ByteString
        getPendingTransactionsBytes() {
      java.lang.Object ref = pendingTransactions_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        pendingTransactions_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string pendingTransactions = 2;</code>
     * @param value The pendingTransactions to set.
     * @return This builder for chaining.
     */
    public Builder setPendingTransactions(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      pendingTransactions_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string pendingTransactions = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearPendingTransactions() {
      
      pendingTransactions_ = getDefaultInstance().getPendingTransactions();
      onChanged();
      return this;
    }
    /**
     * <code>string pendingTransactions = 2;</code>
     * @param value The bytes for pendingTransactions to set.
     * @return This builder for chaining.
     */
    public Builder setPendingTransactionsBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      pendingTransactions_ = value;
      onChanged();
      return this;
    }
    @java.lang.Override
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFields(unknownFields);
    }

    @java.lang.Override
    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:pt.tecnico.BFTB.bank.grpc.checkAccountResponse)
  }

  // @@protoc_insertion_point(class_scope:pt.tecnico.BFTB.bank.grpc.checkAccountResponse)
  private static final pt.tecnico.BFTB.bank.grpc.checkAccountResponse DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new pt.tecnico.BFTB.bank.grpc.checkAccountResponse();
  }

  public static pt.tecnico.BFTB.bank.grpc.checkAccountResponse getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<checkAccountResponse>
      PARSER = new com.google.protobuf.AbstractParser<checkAccountResponse>() {
    @java.lang.Override
    public checkAccountResponse parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new checkAccountResponse(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<checkAccountResponse> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<checkAccountResponse> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public pt.tecnico.BFTB.bank.grpc.checkAccountResponse getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}
