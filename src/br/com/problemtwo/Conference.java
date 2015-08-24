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
 
    private final int INITIAL_TRACK_NUMBER = 1;
    private final int START_CONFERENCE = 9;
    private final int STAR_LUNCH = 12;
    private final int END_LUNCH = 13;
    private final int END_CONFERENCE = 17;

    public Conference() {

    }

    public Conference(List<Talk> talks) {
        this.talks = talks;
    }

    public List<Talk> generatesConference() {
        List<Talk> conference = new ArrayList<Talk>();
        if (!talks.isEmpty()) {
            conference = mountConferenceSchedule(talks, INITIAL_TRACK_NUMBER, conference);
        }
        return conference;
    }

    public List<Talk> mountConferenceSchedule(List<Talk> talks, int numberTrack, List<Talk> conference) {
        resetSchedules();

        Talk talkNumberTrack = new Talk();
        talkNumberTrack.setName("Tracks" + numberTrack);
        conference.add(talkNumberTrack);

        for (Talk talk : talks) {
            if (talk.isFree()) {
                if (isBeforeLunchTime(conferenceSchedule)) {
                    if (minutesToLunch() >= Long.parseLong(talk.getTime())) {
                        addTalkToConference(conference, talk);
                    }
                    if (isLunchTime()) {
                        setLunchTime(conference);
                        break;
                    }
                }
            }
        }

        for (Iterator<Talk> i = talks.iterator(); i.hasNext(); ) {
            Talk talk = i.next();
            if (talk.isFree()) {
                if (isEndLunchTime()) {
                    addFirstTrackAfterLunchTime(conference, talk);
                } else {
                    if (minutesToEnd() >= 30 && minutesToEnd() >= Long.parseLong(talk.getTime())) {
                        addTalkToConference(conference, talk);
                    }
                    if (!i.hasNext() || minutesToEnd() < 30) {
                        Talk talkNetworking = new Talk("Networking Event", "", endConferenceSchedule.getTime(), false);
                        conference.add(talkNetworking);
                        if (i.hasNext()) {
                            mountConferenceSchedule(talks, ++numberTrack, conference);
                        }
                        break;
                    }
                }
            }
        }
        return conference;
    }

    private long minutesToEnd() {
        return TimeUnit.MILLISECONDS.toMinutes(endConferenceSchedule.getTimeInMillis() - conferenceSchedule.getTimeInMillis());
    }

    public void addTalkToConference(List<Talk> conference, Talk talk) {
        talk.setFree(false);
        talk.setSchedule(conferenceSchedule.getTime());
        conference.add(talk);
        conferenceSchedule.add(GregorianCalendar.MINUTE, new Integer(talk.getTime()));
    }

    private void resetSchedules() {
        this.conferenceSchedule = Calendar.getInstance();
        this.conferenceSchedule.set(Calendar.HOUR_OF_DAY, START_CONFERENCE);
        this.conferenceSchedule.set(Calendar.MINUTE, 0);
        this.conferenceSchedule.set(Calendar.SECOND, 0);

        this.endConferenceSchedule = Calendar.getInstance();
        this.endConferenceSchedule.set(Calendar.HOUR_OF_DAY, END_CONFERENCE);
        this.endConferenceSchedule.set(Calendar.MINUTE, 0);
        this.endConferenceSchedule.set(Calendar.SECOND, 0);

        this.beginLunchTime = Calendar.getInstance();
        this.beginLunchTime.set(Calendar.HOUR_OF_DAY, STAR_LUNCH);
        this.beginLunchTime.set(Calendar.MINUTE, 0);
        this.beginLunchTime.set(Calendar.SECOND, 0);

        this.endLunchTime = Calendar.getInstance();
        this.endLunchTime.set(Calendar.HOUR_OF_DAY, END_LUNCH);
        this.endLunchTime.set(Calendar.MINUTE, 0);
        this.endLunchTime.set(Calendar.SECOND, 0);
    }

    private boolean isBeforeLunchTime(Calendar conferenceSchedule) {
        return conferenceSchedule.getTime().compareTo(beginLunchTime.getTime()) < 0;
    }
    
    private boolean isEndLunchTime() {
    	return conferenceSchedule.getTime().compareTo(endLunchTime.getTime()) < 0;
    }

    private boolean isLunchTime() {
        return conferenceSchedule.compareTo(beginLunchTime) == 0 || minutesToLunch() < 30;
    }

    private void setLunchTime(List<Talk> conference) {
        this.conferenceSchedule.set(Calendar.HOUR_OF_DAY, STAR_LUNCH);
        this.conferenceSchedule.set(Calendar.MINUTE, 0);
        this.conferenceSchedule.set(Calendar.SECOND, 0);
        Talk lunchTalk = new Talk("Lunch", "", conferenceSchedule.getTime(), false);
        conference.add(lunchTalk);
    }

    private long minutesToLunch() {
        return TimeUnit.MILLISECONDS.toMinutes(beginLunchTime.getTimeInMillis() - conferenceSchedule.getTimeInMillis());
    }

    private void addFirstTrackAfterLunchTime(List<Talk> conference, Talk talk) {
        this.conferenceSchedule.set(Calendar.HOUR_OF_DAY, END_LUNCH);
        addTalkToConference(conference, talk);
    }

}
