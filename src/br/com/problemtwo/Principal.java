package br.com.problemtwo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import br.com.problemtwo.exceptions.EmptyListException;

public class Principal {

	public static void main(String[] args) {
		String file = "../problemTwo.txt";
		List<Talk> talks;
		try {
			talks = new Talk().loadTalks(file);
			Conference conference = new Conference(talks);
			List<Talk> conferenceTrack = conference.generatesConference();
			SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
			for (Talk talk : conferenceTrack) {
				System.out.println(talk.getSchedule() != null ? sdf.format(talk.getSchedule()) + " - " + talk.getName() : "" +  talk.getName());
			}
		} catch (EmptyListException e) {
			e.printStackTrace();
		}
	}
}
