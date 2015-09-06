package br.com.conference.tests;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.com.conference.Conference;
import br.com.conference.Talk;

public class ConferenceTest {
	Talk expectedTalk;
	List<Talk> lsConference;
	List<Talk> talks;
	Conference conference;
	
	@Before
	public void createTestsArtifacts(){
		this.expectedTalk = new Talk("Test talk", "60", new Date(), true);
		lsConference = new ArrayList<Talk>();
		talks = new ArrayList<Talk>();
		conference = new Conference();
		conference.resetSchedules();
	}
	
	@Test
	public void testMountConferenceSchedule(){
		talks.add(expectedTalk);
		lsConference = conference.mountConferenceSchedule(talks, 1, lsConference);
		
		assertThat(talks.size(), is(equalTo(1)));
		assertThat(lsConference.get(1).getName(), is(equalTo(expectedTalk.getName())));
	}
}
