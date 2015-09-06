package br.com.conference;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import br.com.conference.exceptions.EmptyListException;

public class Main {

	public static void main(String[] args) {
		FileReader file;
		List<Talk> talks = new ArrayList<Talk>();
		try {
			file = new FileReader("conference.txt");
			talks = Talk.loadTalks(file);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (EmptyListException e) {
			e.printStackTrace();
		}
		Conference conference = new Conference(talks);
		List<Talk> conferenceTrack = conference.generatesConference();
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
		for (Talk talk : conferenceTrack) {
			System.out.println(talk.getSchedule() != null ? sdf.format(talk
					.getSchedule()) + " - " + talk.getName() : ""
					+ talk.getName());
		}
	}
}
