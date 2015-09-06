package br.com.conference.tests;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.com.conference.Conference;
import br.com.conference.Talk;
import br.com.conference.exceptions.EmptyListException;

public class TalkTest {
	List<Talk> talks = new ArrayList<Talk>();
	
	@Before
	public void createTalk(){
		Talk talk = new Talk("Test Talk", "60", new Date(), true);
	}
	
	@Test
	public void testLoadingTalkFile(){
		try {
			FileReader file = new FileReader("problemTwo.txt");
			talks = Talk.loadTalks(file);
		} catch (EmptyListException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
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
