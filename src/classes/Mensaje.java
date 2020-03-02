package classes;

import java.io.Serializable;

public class Mensaje implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String texto;

	public Mensaje(String texto) {
		super();
		this.texto = texto;
	}

	public String getTexto() {
		return texto;
	}

	@Override
	public String toString() {
		return "Mensaje [texto=" + texto + "]";
	}
	
}
