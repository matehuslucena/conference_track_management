package br.com.problemtwo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Conference {
	
	private List<Talk> talks;
	private Calendar beginLunchTime;
	private Calendar endLunchTime;
	private Calendar conferenceSchedule;
	private Calendar endConferenceSchedule;
	
	public Conference(List<Talk> talks){
		this.talks = talks;
	}

	public List<Talk> generatesConference() {
		
		List<Talk >conference = new ArrayList<Talk>();
		if(!talks.isEmpty()){
			conference = mountConferenceSchedule(talks, 1, conference);
		}
		return conference;
	}

	private List<Talk> mountConferenceSchedule(List<Talk> talks, int numberTrack, List<Talk> conference){
		
		this.beginLunchTime = Calendar.getInstance();
		this.endLunchTime = Calendar.getInstance();
		this.conferenceSchedule = Calendar.getInstance();
		this.endConferenceSchedule = Calendar.getInstance();
		
		resetSchedules();
		
		Talk talkNumeroTrack = new Talk();
		talkNumeroTrack.setName("Track" + numberTrack);
		conference.add(talkNumeroTrack);
		
		for (Talk talk : talks) {
			if (talk.isFree()) {
				if (isBeforeLunchTime(conferenceSchedule)) {
					if (minutesToLunch() >= Long.parseLong(talk.getTime())) {
						talk.setFree(false);
						talk.setSchedule(conferenceSchedule.getTime());
						conference.add(talk);
						conferenceSchedule.add(GregorianCalendar.MINUTE, new Integer(talk.getTime()));
					}
					if(isLunchTime()){
						setLunchTime(conference);
						break;
					}
				}

			}
		}

		for (Iterator<Talk> i = talks.iterator(); i.hasNext();) {
			Talk talk = (Talk) i.next();
			if (talk.isFree()) {
				if (conferenceSchedule.getTime().compareTo(endLunchTime.getTime()) < 0) {
					addTrackAfterLunchTime(conference, talk);
				} else {
					long minutesToEnd = TimeUnit.MILLISECONDS.toMinutes(endConferenceSchedule.getTimeInMillis() - conferenceSchedule.getTimeInMillis());
					if (minutesToEnd >= 30 && minutesToEnd >= Long.parseLong(talk.getTime())) {
						talk.setFree(false);
						talk.setSchedule(conferenceSchedule.getTime());
						conference.add(talk);
						conferenceSchedule.add(GregorianCalendar.MINUTE, new Integer(talk.getTime()));
					}
					if(!i.hasNext() || minutesToEnd < 30){
						Talk talkNetworking = new Talk("Networking Event", "", endConferenceSchedule.getTime(), false); 
						conference.add(talkNetworking);
						if(i.hasNext()){
							mountConferenceSchedule(talks, ++ numberTrack, conference);
						}
						break;
					}
				}

			}
		}
		return conference;
	}
	
	private void resetSchedules(){
		
		this.conferenceSchedule.set(Calendar.HOUR_OF_DAY, 9);
		conferenceSchedule.set(Calendar.MINUTE, 0);
		conferenceSchedule.set(Calendar.SECOND, 0);

		endConferenceSchedule.set(Calendar.HOUR_OF_DAY, 17);
		endConferenceSchedule.set(Calendar.MINUTE, 0);
		endConferenceSchedule.set(Calendar.SECOND, 0);
		
		beginLunchTime.set(Calendar.HOUR_OF_DAY, 12);
		beginLunchTime.set(Calendar.MINUTE, 0);
		beginLunchTime.set(Calendar.SECOND, 0);

		endLunchTime.set(Calendar.HOUR_OF_DAY, 13);
		endLunchTime.set(Calendar.MINUTE, 0);
		endLunchTime.set(Calendar.SECOND, 0);

	}
	
	private boolean isBeforeLunchTime(Calendar conferenceSchedule){
		return conferenceSchedule.getTime().compareTo(beginLunchTime.getTime()) < 0;
	}
	
	private boolean isLunchTime(){
		return conferenceSchedule.compareTo(beginLunchTime) == 0 || minutesToLunch() < 30;
	}
	
	private void setLunchTime(List<Talk> conference){
		conferenceSchedule.set(Calendar.HOUR_OF_DAY, 12);
		conferenceSchedule.set(Calendar.MINUTE, 0);
		conferenceSchedule.set(Calendar.SECOND, 0);
		Talk talkAlmoco = new Talk("Lunch", "", conferenceSchedule.getTime(), false);
		conference.add(talkAlmoco);
	}

	private long minutesToLunch() {
		return TimeUnit.MILLISECONDS.toMinutes(beginLunchTime.getTimeInMillis() - conferenceSchedule.getTimeInMillis());
	}

	private void addTrackAfterLunchTime(List<Talk> conference, Talk talk) {
		conferenceSchedule.set(Calendar.HOUR_OF_DAY, 13);
		talk.setFree(false);
		talk.setSchedule(conferenceSchedule.getTime());
		conference.add(talk);
		conferenceSchedule.add(GregorianCalendar.MINUTE,new Integer(talk.getTime()));
	}

}
