package edu.ping.damian.enzinlum.develop;

import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;

public class TokenContract {
    //propiedades
    private final Address owner; 
    private String name = "";
    private String symbol = "";
    private int supplies = 0;
    private double price = 0.0d;
    private Map<PublicKey, Double> table = new HashMap<PublicKey,Double>();
    //constructor
    TokenContract(Address address){
        this.owner = address;
    }
    //getters
    Address owner(){
        return this.owner;
    }
    String name(){
        return this.name;
    }
    String symbol(){
        return this.symbol;
    }
    int totalSupply(){
        return this.supplies;
    }
    int numOwners(){
        return this.table.size();
    }
    Double balanceOf(PublicKey publicKey){
        if (this.table.get(publicKey) != null){
            return this.table.get(publicKey);
        } else {
            return 0.0d;
        }
    }
    boolean require(PublicKey publicKey, double supplies){
        boolean answer;
        if (this.table.get(publicKey) >= supplies){
            answer = true;
        } else {
            answer = false;
        }
        return answer;
    }
    void owners(){
        for (Map.Entry<PublicKey, Double> entry : this.table.entrySet()) {
            if (entry.getKey() == this.owner){
                continue;
            } else {
                System.out.println("Owner = " + entry.getKey().hashCode() + ", Tokens = " + entry.getValue()); 
            }
        } 
    }
    String totalTokensSold(){
        Double acumulator = 0.0d;
        for (Map.Entry<PublicKey, Double> entry : this.table.entrySet()){
            if (entry.getKey() == this.owner){
                continue;
            } else {
                acumulator += entry.getValue();
            }
        }
        return acumulator.toString();
    }
    //setters
    void setName(String name){
        this.name = name;
    }
    void setSymbol(String symbol){
        this.symbol = symbol;
    }
    void setTotalSupply(int numSupplies){
        this.supplies = numSupplies;
    }
    void setTokenPrice(double price){
        this.price = price;
    }
    void addOwner(PublicKey publicKey, double supplies){
        if (this.table.get(publicKey) == null){
            this.table.put(publicKey, supplies);
        } else {
            //no hagas nada, en plan, nada
        }
    }
    //método desde owner a cliente
    void transfer(PublicKey publicKey, double supplies){
        if (this.require(this.owner.getPK(), supplies)){
            double total =  this.table.get(this.owner.getPK()) - supplies;
            //actualizamos los tokens del owner
            this.table.put(this.owner.getPK(), total);
            //metemos y actualizamos los tokens del cliente
            if (this.table.get(publicKey) == null){
                this.addOwner(publicKey, supplies);
            } else {
                double totalCliente = this.table.get(publicKey) + supplies;
                this.table.put(publicKey, totalCliente);
            }
        } else {
            // no hagas nada, en plan, nada
        }
    }
    //método desde cliente a cliente
    void transfer(PublicKey publicKeyOrigen, PublicKey publicKeyDestino, double supplies){
        if (this.require(publicKeyOrigen, supplies)){
            double total =  this.table.get(publicKeyOrigen) - supplies;
            //actualizamos los tokens del cliente origen
            this.table.put(publicKeyOrigen, total);
            //actualizamos los tokens del cliente destino
            if (this.table.get(publicKeyDestino) == null){
                this.addOwner(publicKeyDestino, supplies);
            } else {
                double totalCliente = this.table.get(publicKeyDestino) + supplies;
                this.table.put(publicKeyDestino, totalCliente);
            }
        } else {
            // no hagas nada, en plan, nada
        }
    }
    //método desde cliente a cliente con tokens de por medio:
    void transfer(PublicKey publicKeyOrigen, PublicKey publicKeyDestino, double supplies, double tokens){
        //
    }
    //método desde cliente a owner con Enziniums
    double payable(Address ownerContract, Double balanceToSend){
        //hay que ver si tiene el suficiente token dependiendo de los enzinums
        //this.table.get(this.owner) nos da la cantidad de tokens que tiene
        //balanceToSend/this.price // nos indicaría cuantos entradas compraría morty
        double tokensToGet = balanceToSend/this.price;
        double answer = 0d;
        if (require(ownerContract.getPK(), tokensToGet)) {
            //this.transfer(ownerContract.getPK(), tokensToGet);
            //this.owner.transferEZI(+balanceToSend);
            answer = tokensToGet;
        }
        return answer;
    }
    @Override
    public String toString(){
        return String.format("%s, %s, %s",this.name, this.symbol, this.owner);
    }
}
