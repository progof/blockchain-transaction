package com.blockchain;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;

class Transaction {
    private double amount;
    private String hash;
    private String lastTransaction;
    private int nonce;

    public Transaction(double amount, String lastTransaction) {
        this.amount = amount;
        this.lastTransaction = lastTransaction;
        this.nonce = 0;
        this.hash = mineBlock();
    }

    public String mineBlock() {
        String target = "00000";
        String calculatedHash;
        do {
            nonce++;
            calculatedHash = generateTransactionHash();
        } while (!calculatedHash.endsWith(target));
        return calculatedHash;
    }

    public String getLastTransaction() {
        return lastTransaction;
    }

    public String getHash() {
        return hash;
    }

    public String generateTransactionHash() {
        try {
            return DigestUtils.sha256Hex(this.amount + this.lastTransaction + this.nonce);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    @Override
    public String toString() {
        return String.format(
                "Transaction Details\n" +
                        "{\n" +
                        "\tLast transaction : %s\n" +
                        "\tAmount           : %s\n" +
                        "\tHash             : %s\n" +
                        "\tNonce            : %d\n" +
                        "}\n\n",

                this.lastTransaction, this.amount, this.hash, this.nonce);
    }

}

public class App {
    public static void main(String[] args) {

        List<Transaction> transaction = new ArrayList<>();

        Transaction transactionFirst = new Transaction(100, "0");
        transaction.add(transactionFirst);

        for (int i = 1; i < 6; i++) {
            transaction.add(new Transaction(Math.random() * 100, transaction.get(transaction.size() - 1).getHash()));
            System.out.println(transaction.get(i).toString());
        }
    }
}