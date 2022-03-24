// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: bank.proto

package pt.tecnico.BFTB.bank.grpc;

/**
 * Protobuf type {@code pt.tecnico.BFTB.bank.grpc.BankWriteTransactionHistoryResponse}
 */
public final class BankWriteTransactionHistoryResponse extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:pt.tecnico.BFTB.bank.grpc.BankWriteTransactionHistoryResponse)
    BankWriteTransactionHistoryResponseOrBuilder {
private static final long serialVersionUID = 0L;
  // Use BankWriteTransactionHistoryResponse.newBuilder() to construct.
  private BankWriteTransactionHistoryResponse(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private BankWriteTransactionHistoryResponse() {
    status_ = "";
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new BankWriteTransactionHistoryResponse();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private BankWriteTransactionHistoryResponse(
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

            status_ = s;
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
    return pt.tecnico.BFTB.bank.grpc.Bank.internal_static_pt_tecnico_BFTB_bank_grpc_BankWriteTransactionHistoryResponse_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return pt.tecnico.BFTB.bank.grpc.Bank.internal_static_pt_tecnico_BFTB_bank_grpc_BankWriteTransactionHistoryResponse_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            pt.tecnico.BFTB.bank.grpc.BankWriteTransactionHistoryResponse.class, pt.tecnico.BFTB.bank.grpc.BankWriteTransactionHistoryResponse.Builder.class);
  }

  public static final int STATUS_FIELD_NUMBER = 1;
  private volatile java.lang.Object status_;
  /**
   * <code>string status = 1;</code>
   * @return The status.
   */
  @java.lang.Override
  public java.lang.String getStatus() {
    java.lang.Object ref = status_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      status_ = s;
      return s;
    }
  }
  /**
   * <code>string status = 1;</code>
   * @return The bytes for status.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString
      getStatusBytes() {
    java.lang.Object ref = status_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      status_ = b;
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
    if (!getStatusBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 1, status_);
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (!getStatusBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, status_);
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
    if (!(obj instanceof pt.tecnico.BFTB.bank.grpc.BankWriteTransactionHistoryResponse)) {
      return super.equals(obj);
    }
    pt.tecnico.BFTB.bank.grpc.BankWriteTransactionHistoryResponse other = (pt.tecnico.BFTB.bank.grpc.BankWriteTransactionHistoryResponse) obj;

    if (!getStatus()
        .equals(other.getStatus())) return false;
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
    hash = (37 * hash) + STATUS_FIELD_NUMBER;
    hash = (53 * hash) + getStatus().hashCode();
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static pt.tecnico.BFTB.bank.grpc.BankWriteTransactionHistoryResponse parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static pt.tecnico.BFTB.bank.grpc.BankWriteTransactionHistoryResponse parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static pt.tecnico.BFTB.bank.grpc.BankWriteTransactionHistoryResponse parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static pt.tecnico.BFTB.bank.grpc.BankWriteTransactionHistoryResponse parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static pt.tecnico.BFTB.bank.grpc.BankWriteTransactionHistoryResponse parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static pt.tecnico.BFTB.bank.grpc.BankWriteTransactionHistoryResponse parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static pt.tecnico.BFTB.bank.grpc.BankWriteTransactionHistoryResponse parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static pt.tecnico.BFTB.bank.grpc.BankWriteTransactionHistoryResponse parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static pt.tecnico.BFTB.bank.grpc.BankWriteTransactionHistoryResponse parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static pt.tecnico.BFTB.bank.grpc.BankWriteTransactionHistoryResponse parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static pt.tecnico.BFTB.bank.grpc.BankWriteTransactionHistoryResponse parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static pt.tecnico.BFTB.bank.grpc.BankWriteTransactionHistoryResponse parseFrom(
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
  public static Builder newBuilder(pt.tecnico.BFTB.bank.grpc.BankWriteTransactionHistoryResponse prototype) {
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
   * Protobuf type {@code pt.tecnico.BFTB.bank.grpc.BankWriteTransactionHistoryResponse}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:pt.tecnico.BFTB.bank.grpc.BankWriteTransactionHistoryResponse)
      pt.tecnico.BFTB.bank.grpc.BankWriteTransactionHistoryResponseOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return pt.tecnico.BFTB.bank.grpc.Bank.internal_static_pt_tecnico_BFTB_bank_grpc_BankWriteTransactionHistoryResponse_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return pt.tecnico.BFTB.bank.grpc.Bank.internal_static_pt_tecnico_BFTB_bank_grpc_BankWriteTransactionHistoryResponse_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              pt.tecnico.BFTB.bank.grpc.BankWriteTransactionHistoryResponse.class, pt.tecnico.BFTB.bank.grpc.BankWriteTransactionHistoryResponse.Builder.class);
    }

    // Construct using pt.tecnico.BFTB.bank.grpc.BankWriteTransactionHistoryResponse.newBuilder()
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
      status_ = "";

      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return pt.tecnico.BFTB.bank.grpc.Bank.internal_static_pt_tecnico_BFTB_bank_grpc_BankWriteTransactionHistoryResponse_descriptor;
    }

    @java.lang.Override
    public pt.tecnico.BFTB.bank.grpc.BankWriteTransactionHistoryResponse getDefaultInstanceForType() {
      return pt.tecnico.BFTB.bank.grpc.BankWriteTransactionHistoryResponse.getDefaultInstance();
    }

    @java.lang.Override
    public pt.tecnico.BFTB.bank.grpc.BankWriteTransactionHistoryResponse build() {
      pt.tecnico.BFTB.bank.grpc.BankWriteTransactionHistoryResponse result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public pt.tecnico.BFTB.bank.grpc.BankWriteTransactionHistoryResponse buildPartial() {
      pt.tecnico.BFTB.bank.grpc.BankWriteTransactionHistoryResponse result = new pt.tecnico.BFTB.bank.grpc.BankWriteTransactionHistoryResponse(this);
      result.status_ = status_;
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
      if (other instanceof pt.tecnico.BFTB.bank.grpc.BankWriteTransactionHistoryResponse) {
        return mergeFrom((pt.tecnico.BFTB.bank.grpc.BankWriteTransactionHistoryResponse)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(pt.tecnico.BFTB.bank.grpc.BankWriteTransactionHistoryResponse other) {
      if (other == pt.tecnico.BFTB.bank.grpc.BankWriteTransactionHistoryResponse.getDefaultInstance()) return this;
      if (!other.getStatus().isEmpty()) {
        status_ = other.status_;
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
      pt.tecnico.BFTB.bank.grpc.BankWriteTransactionHistoryResponse parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (pt.tecnico.BFTB.bank.grpc.BankWriteTransactionHistoryResponse) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private java.lang.Object status_ = "";
    /**
     * <code>string status = 1;</code>
     * @return The status.
     */
    public java.lang.String getStatus() {
      java.lang.Object ref = status_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        status_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string status = 1;</code>
     * @return The bytes for status.
     */
    public com.google.protobuf.ByteString
        getStatusBytes() {
      java.lang.Object ref = status_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        status_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string status = 1;</code>
     * @param value The status to set.
     * @return This builder for chaining.
     */
    public Builder setStatus(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      status_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string status = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearStatus() {
      
      status_ = getDefaultInstance().getStatus();
      onChanged();
      return this;
    }
    /**
     * <code>string status = 1;</code>
     * @param value The bytes for status to set.
     * @return This builder for chaining.
     */
    public Builder setStatusBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      status_ = value;
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


    // @@protoc_insertion_point(builder_scope:pt.tecnico.BFTB.bank.grpc.BankWriteTransactionHistoryResponse)
  }

  // @@protoc_insertion_point(class_scope:pt.tecnico.BFTB.bank.grpc.BankWriteTransactionHistoryResponse)
  private static final pt.tecnico.BFTB.bank.grpc.BankWriteTransactionHistoryResponse DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new pt.tecnico.BFTB.bank.grpc.BankWriteTransactionHistoryResponse();
  }

  public static pt.tecnico.BFTB.bank.grpc.BankWriteTransactionHistoryResponse getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<BankWriteTransactionHistoryResponse>
      PARSER = new com.google.protobuf.AbstractParser<BankWriteTransactionHistoryResponse>() {
    @java.lang.Override
    public BankWriteTransactionHistoryResponse parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new BankWriteTransactionHistoryResponse(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<BankWriteTransactionHistoryResponse> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<BankWriteTransactionHistoryResponse> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public pt.tecnico.BFTB.bank.grpc.BankWriteTransactionHistoryResponse getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

