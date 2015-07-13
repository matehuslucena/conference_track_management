package br.com.tw.problemtwo;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import br.com.tw.problemtwo.exceptions.ListaVaziaException;

/**
 * Classe que contem os metodos de montagem da conferencia
 * @author matheuslucena
 *
 */
public class Conferencia {

	/**
	 * Metodo que le o arquivo e faz o parser para um objeto talk
	 * adicionando ele em um ArrayList
	 * 
	 * @return
	 * @author matheuslucena
	 */
	public List<Talk> carregaListaTalk(String nomeArquivo) throws ListaVaziaException{
		Scanner scanner;
		List<Talk> lsTalk = new ArrayList<Talk>();
		try {
			scanner = new Scanner(new FileReader("problemTwo.txt")).useDelimiter("\\||\\n");
			while (scanner.hasNext()) {
				Talk talk = new Talk();
				talk = preencheTalk(scanner.next());
				lsTalk.add(talk);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		if(lsTalk.isEmpty()){
			throw new ListaVaziaException();
		}
		return lsTalk;
	}

	/**
	 * Metodo que preenche uma lista de talk a partir de um objeto scanner
	 * 
	 * @param scanner
	 * @return objeto talk
	 * @author matheuslucena
	 */
	public Talk preencheTalk(String scanner) {
		Talk talk = new Talk();
		if(scanner != null && scanner != ""){
			int splitIndex = scanner.lastIndexOf(" ");
			talk.setNome(scanner.substring(0, splitIndex));
			// retira o sinal de final de paragrado da string
			String tempo = scanner.substring(splitIndex + 1).replace("\r", "");
			// Se a string terminar com lightning e adicionado o tempo de 5min
			if (tempo.endsWith("min")) {
				talk.setTempo(tempo.substring(0, 2));
			} else if (tempo.equals("lightning")) {
				talk.setTempo("5");
			}
			talk.setLivre(true);
			return talk;
		}
		return talk;
	}

	/**
	 * Metodo que faz o balanceamento dos horario para montar o cronograma da
	 * conferencia
	 * 
	 * @return Lista de horarios
	 * @author matheuslucena
	 */
	public List<Talk> geraConferencia(List<Talk> talks) {

		List<Talk> conferencia = new ArrayList<Talk>();
		if(!talks.isEmpty()){
			conferencia = montaHorarioConferencia(talks, conferencia, 1);
		}
		return conferencia;
	}

	/**
	 * Metodo que seta os valores iniciais nas datas utilizadas na montagem do
	 * cronograma da conferencia
	 * 
	 * @param horarioAlmocoInicio
	 * @param horarioAlmocoFim
	 * @param horarioConferencia
	 * @param horarioConferenciaFim
	 * @author matheuslucena
	 */
	private void reiniciaDatas(	Calendar horarioAlmocoInicio,
			Calendar horarioAlmocoFim,
			Calendar horarioConferencia, Calendar horarioConferenciaFim) {
		
		horarioConferencia.set(Calendar.HOUR_OF_DAY, 9);
		horarioConferencia.set(Calendar.MINUTE, 0);
		horarioConferencia.set(Calendar.SECOND, 0);

		horarioConferenciaFim.set(Calendar.HOUR_OF_DAY, 17);
		horarioConferenciaFim.set(Calendar.MINUTE, 0);
		horarioConferenciaFim.set(Calendar.SECOND, 0);
		
		horarioAlmocoInicio.set(Calendar.HOUR_OF_DAY, 12);
		horarioAlmocoInicio.set(Calendar.MINUTE, 0);
		horarioAlmocoInicio.set(Calendar.SECOND, 0);

		horarioAlmocoFim.set(Calendar.HOUR_OF_DAY, 13);
		horarioAlmocoFim.set(Calendar.MINUTE, 0);
		horarioAlmocoFim.set(Calendar.SECOND, 0);

	}

	/**
	 * Metodo que monta o cronograma da conferencia a partir de uma lista de talk
	 * e retorna uma lista de conferencia
	 * 
	 * @param talks
	 * @param conferencia
	 * @param numeroTrack
	 * 
	 * @return Lista de talk
	 * @author matheuslucena
	 * @throws Exception 
	 */
	private List<Talk> montaHorarioConferencia(List<Talk> talks, List<Talk> conferencia, int numeroTrack){
		
		//Horarios para auxiliar a execucao do fluxo
		Calendar horarioAlmocoInicio = Calendar.getInstance();
		Calendar horarioAlmocoFim = Calendar.getInstance();
		Calendar horarioConferencia = Calendar.getInstance();
		Calendar horarioConferenciaFim = Calendar.getInstance();
		
		reiniciaDatas(horarioAlmocoInicio, horarioAlmocoFim,horarioConferencia, horarioConferenciaFim);
		
		Talk talkNumeroTrack = new Talk();
		talkNumeroTrack.setNome("Track" + numeroTrack);
		conferencia.add(talkNumeroTrack);
		
		// Seleciona os talks antes do almoco
		for (Talk talk : talks) {
			if (talk.isLivre()) {
				if (horarioConferencia.getTime().compareTo(horarioAlmocoInicio.getTime()) < 0) {
					//Calcula a quantidade de minutos ate o inicio da almoco
					long minutosParaAlmoco = TimeUnit.MILLISECONDS.toMinutes(horarioAlmocoInicio.getTimeInMillis() - horarioConferencia.getTimeInMillis());
					if (minutosParaAlmoco >= Long.parseLong(talk.getTempo())) {
						// Seta false no objeto talk para que ele nao possa ser  add em outro horario
						talk.setLivre(false);
						talk.setHorario(horarioConferencia.getTime());
						conferencia.add(talk);
						// Soma o tempo do objeto talk no horario da conferencia
						horarioConferencia.add(GregorianCalendar.MINUTE, new Integer(talk.getTempo()));
					}
					//Adiciona o talk do almoço
					if(horarioConferencia.compareTo(horarioAlmocoInicio) == 0 || minutosParaAlmoco < 30){
						horarioConferencia.set(Calendar.HOUR_OF_DAY, 12);
						horarioConferencia.set(Calendar.MINUTE, 0);
						horarioConferencia.set(Calendar.SECOND, 0);
						Talk talkAlmoco = new Talk("Lunch", "", horarioConferencia.getTime(), false);
						conferencia.add(talkAlmoco);
						break;
					}
				}

			}
		}

		// Seleciona os talks depois do almoco, utilizando Iterator para verificar o final da lista
		for (Iterator<Talk> i = talks.iterator(); i.hasNext();) {
			Talk talk = (Talk) i.next();
			if (talk.isLivre()) {
				// Primeira tarefa livre, o horario da conferencia vai estar 12:00
				if (horarioConferencia.getTime().compareTo(horarioAlmocoFim.getTime()) < 0) {
					horarioConferencia.set(Calendar.HOUR_OF_DAY, 13);
					talk.setLivre(false);
					talk.setHorario(horarioConferencia.getTime());
					conferencia.add(talk);
					horarioConferencia.add(GregorianCalendar.MINUTE,new Integer(talk.getTempo()));
				} else {
					//Calcula a quantidade de minutos ate o final da conferencia
					long minutosParaTermino = TimeUnit.MILLISECONDS.toMinutes(horarioConferenciaFim.getTimeInMillis() - horarioConferencia.getTimeInMillis());
					if (minutosParaTermino >= 30 && minutosParaTermino >= Long.parseLong(talk.getTempo())) {
						// Seta false no objeto talk para que ele nao possa ser add em outro horario
						talk.setLivre(false);
						talk.setHorario(horarioConferencia.getTime());
						conferencia.add(talk);
						// Soma o tempo do objeto talk no horario da conferencia
						horarioConferencia.add(GregorianCalendar.MINUTE, new Integer(talk.getTempo()));
					}
					//Adiciona o talk networking no final da conferencia
					if(!i.hasNext() || minutosParaTermino < 30){
						Talk talkNetworking = new Talk("Networking Event", "", horarioConferenciaFim.getTime(), false); 
						conferencia.add(talkNetworking);
						if(i.hasNext()){
							montaHorarioConferencia(talks, conferencia,  ++ numeroTrack);
						}
						break;
					}
				}

			}
		}
		return conferencia;
	}

}
