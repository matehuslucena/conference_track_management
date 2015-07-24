package br.com.problemtwo;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import br.com.problemtwo.exceptions.ListaVaziaException;

public class Talk {

	private String name;
	private String time;
	private Date schedule;
	private boolean free;

	public Talk(){}
	
	public Talk(String name, String time, Date schedule, boolean free){
		this.name = name;
		this.time = time;
		this.schedule = schedule;
		this.free = true;
	}
	
	public List<Talk> loadTalks(String file) throws ListaVaziaException{
		Scanner scanner;
		List<Talk> lsTalk = new ArrayList<Talk>();
		try {
			scanner = new Scanner(new FileReader("problemTwo.txt")).useDelimiter("\\||\\n");
			while (scanner.hasNext()) {
				Talk talk = new Talk();
				talk = getTalk(scanner.next());
				lsTalk.add(talk);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		if(lsTalk.isEmpty()){
			throw new ListaVaziaException();
		}
		return lsTalk;
	}

	public Talk getTalk(String scanner) {
		Talk talk = new Talk();
		if(scanner != null && scanner != ""){
			int splitIndex = scanner.lastIndexOf(" ");
			talk.setName(scanner.substring(0, splitIndex));
			String tempo = scanner.substring(splitIndex + 1).replace("\r", "");
			if (tempo.endsWith("min")) {
				talk.setTime(tempo.substring(0, 2));
			} else if (tempo.equals("lightning")) {
				talk.setTime("5");
			}
			talk.setFree(true);
			return talk;
		}
		return talk;
	}
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Date getSchedule() {
		return schedule;
	}

	public void setSchedule(Date schedule) {
		this.schedule = schedule;
	}

	public boolean isFree() {
		return free;
	}

	public void setFree(boolean free) {
		this.free = free;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((schedule == null) ? 0 : schedule.hashCode());
		result = prime * result + (free ? 1231 : 1237);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((time == null) ? 0 : time.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Talk other = (Talk) obj;
		if (schedule == null) {
			if (other.schedule != null)
				return false;
		} else if (!schedule.equals(other.schedule))
			return false;
		if (free != other.free)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (time == null) {
			if (other.time != null)
				return false;
		} else if (!time.equals(other.time))
			return false;
		return true;
	}
}
