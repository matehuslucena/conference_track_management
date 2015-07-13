package br.com.tw.problemtwo;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;


import org.junit.Test;

public class ConferenciaTest {

	@Test
	public void testaPreencheTalkCorretamente(){
		String scanner = "Writing Fast Tests Against Enterprise Rails 60min";
		Talk talkEsperada = new Talk();
		talkEsperada.setNome("Writing Fast Tests Against Enterprise Rails");
		talkEsperada.setTempo("60");
		talkEsperada.setLivre(true);
		
		Talk talkRecebida;
		Conferencia conferencia = new Conferencia();
		talkRecebida = conferencia.preencheTalk(scanner);
		
		assertThat(talkRecebida, is(equalTo(talkEsperada)));
//		assertThat(talkRecebida, is(equalTo(talkEsperada)));
		
	}
	

}
