package br.com.problemtwo.tests;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.com.problemtwo.Conference;
import br.com.problemtwo.Talk;

public class ConferenceTest {
	Talk expectedTalk;
	
	@Before
	public void loadNewTalk(){
		this.expectedTalk = new Talk("Writing Fast Tests Against Enterprise Rails", "60",new Date(), true);
	}

	@Test
	public void testMountConferenceSchedule(){
		List<Talk> talks = new ArrayList<Talk>();
		List<Talk> conference = new ArrayList<Talk>();
		
		talks.add(expectedTalk);
		conference = new Conference().mountConferenceSchedule(talks, 1, conference);
		
		assertThat(talks.size(), is(equalTo(1)));
		assertThat(conference.get(1).getName(), is(equalTo(expectedTalk.getName())));
	}
	
	

}
