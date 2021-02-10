package edu.ping.damian.enzinlum.develop;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

public class Address {
    //propiedades
    private PublicKey publicKey = null;
    private PrivateKey privateKey = null;
    private double balance = 0;
    private static final String SYMBOL = "EZI";
    //getters
    PublicKey getPK(){
        return this.publicKey;
    }
    Double getBalance(){
        return this.balance;
    }
    //setters
    void generateKeyPair(){
        KeyPair keyPair = GenSig.generateKeyPair();
        this.privateKey = keyPair.getPrivate();
        this.publicKey = keyPair.getPublic();
    }

    void transferEZI(Double balanceToTransfer){
        this.balance += balanceToTransfer;
    }

    void send(TokenContract contract, Double balanceToSend){
        //en construcción
        if (this.balance >= balanceToSend){
            contract.payable(contract.owner(), balanceToSend);
        } else {
            //no hagas nada, en plan, nada
        }
    }

    @Override
    public String toString(){
        return String.format("Esta es la publicKey: %s %n y este su balance: %d", this.publicKey.hashCode(), this.balance);
    }
}
