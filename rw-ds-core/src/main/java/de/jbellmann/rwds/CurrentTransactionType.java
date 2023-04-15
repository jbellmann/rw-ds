package de.jbellmann.rwds;

class CurrentTransactionType extends ThreadLocal<TransactionType> {
  @Override
  protected TransactionType initialValue() {
    return TransactionType.WRITE;
  }
}
