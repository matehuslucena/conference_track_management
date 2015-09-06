package br.com.problemtwo.tests;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import br.com.problemtwo.Talk;
import br.com.problemtwo.exceptions.EmptyListException;

public class TalkTest {
	Talk talk = new Talk();
	List<Talk> talks = new ArrayList<Talk>();
	
	@Test
	public void testLoadingTalkFile(){
		try {
			talks = talk.loadTalks("problemTwo.txt");
		} catch (EmptyListException e) {
			e.printStackTrace();
		}
		assertThat(talks.size(), is(not(0)));
	}
	
	@Test
	public void testGettingTalkCorrectly(){
		String scanner = "Writing Fast Tests Against Enterprise Rails 60min";
		Talk expectedTalk = new Talk();
		expectedTalk.setName("Writing Fast Tests Against Enterprise Rails");
		expectedTalk.setTime("60");
		expectedTalk.setFree(true);
		Talk receivedTalk = new Talk().getTalk(scanner);
		
		assertThat(receivedTalk, is(equalTo(expectedTalk)));
	}

}
