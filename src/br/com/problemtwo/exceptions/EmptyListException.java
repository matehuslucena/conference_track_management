package br.com.problemtwo.exceptions;


public class EmptyListException extends Exception {

	private static final long serialVersionUID = -3889737608971056845L;
	 
	 public EmptyListException(){
		 super("List can´t be empty");
	 }

}
