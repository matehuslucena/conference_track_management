package br.com.problemtwo.tests;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

import br.com.problemtwo.Talk;

public class ConferenciaTest {

	@Test
	public void testLoadTalkCorrectly(){
		String scanner = "Writing Fast Tests Against Enterprise Rails 60min";
		Talk expectedTalk = new Talk();
		expectedTalk.setName("Writing Fast Tests Against Enterprise Rails");
		expectedTalk.setTime("60");
		expectedTalk.setFree(true);
		Talk receivedTalk = new Talk().getTalk(scanner);
		
		assertThat(receivedTalk, is(equalTo(expectedTalk)));
	}
	

}
