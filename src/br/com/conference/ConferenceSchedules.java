package br.com.conference;

public enum ConferenceSchedules {

	START_CONFERENCE(9), STAR_LUNCH(12), END_LUNCH(13), END_CONFERENCE(17);
	
	private int value;

	private ConferenceSchedules(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

}
