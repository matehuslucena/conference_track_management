package br.com.tw.problemtwo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import br.com.tw.problemtwo.exceptions.ListaVaziaException;
/**
 * Classe utilizada para executar a montagem da conferencia
 * @author matheuslucena
 *
 */
public class Principal {

	public static void main(String[] args) {
		Conferencia conferencia = new Conferencia();
		//Endereco do arquivo a ser carregado
		String enderecoArquivo = "problemTwo.txt";
		// Le o arquivo de entrada e carrega um List de talk
		List<Talk> talks = new ArrayList<Talk>();
		try {
			talks = conferencia.carregaListaTalk(enderecoArquivo);
			//Monta o cronograma da conferencia
			List<Talk> cronogramaConferencia = conferencia.geraConferencia(talks);
			SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
			for (Talk talk : cronogramaConferencia) {
				System.out.println(talk.getHorario() != null ? sdf.format(talk.getHorario()) + " - " + talk.getNome() : "" +  talk.getNome());
			}
		} catch (ListaVaziaException e) {
			e.printStackTrace();
		}
	}
}
