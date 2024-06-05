package model;

import java.io.Serializable;

public class Prodotto implements Serializable{
	private String nome;
	private String descrizione;
	private double prezzo;
	private int id;
	private byte[] immagine;
	
public String getNome() {
	return this.nome;
}
public String getDescrizione() {
	return this.descrizione;
}
public double getPrezzo() {
	return this.prezzo;
}
public int getId() {
	return this.id;
}
public void setNome(String nome) {
	this.nome=nome;
}
public void setDescrizione(String descrizione) {
	this.descrizione=descrizione;
}
public void setPrezzo(double prezzo2) {
	this.prezzo=prezzo2;
}
public void setId(int id) {
	this.id=id;
}
public byte[] getImmagine() {
    return this.immagine;
}

public void setImmagine(byte[] immagine) {
    this.immagine = immagine;
}
}
