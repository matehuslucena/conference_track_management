package br.com.problemtwo.exceptions;


public class ListaVaziaException extends Exception {

	private static final long serialVersionUID = -3889737608971056845L;
	 
	 public ListaVaziaException(){
		 super("Lista não pode ser vazia");
	 }

}
