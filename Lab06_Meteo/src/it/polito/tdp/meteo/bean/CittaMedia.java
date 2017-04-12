package it.polito.tdp.meteo.bean;

public class CittaMedia {
	
	private String nome;
	private int mese;
	private float umiditaMedia;
	public CittaMedia(String nome, int mese, float umiditaMedia) {
		super();
		this.nome = nome;
		this.mese = mese;
		this.umiditaMedia = umiditaMedia;
	}
	
	public int getMese() {
		return mese;
	}
	public void setMese(int mese) {
		this.mese = mese;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public float getUmiditaMedia() {
		return umiditaMedia;
	}
	public void setUmiditaMedia(float umiditaMedia) {
		this.umiditaMedia = umiditaMedia;
	}
	
	
	
}
