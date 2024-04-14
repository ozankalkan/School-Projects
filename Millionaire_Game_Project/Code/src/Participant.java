import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class Participant {
    private String name;
    private Date  birthDate;
    private Phone number;
	private Location adress;
	private int participantId;
	private int counter=0;
	private int prize=0;
	private boolean played=false;
	private int trueAnswers = 0;

	public Participant(String name, Date birthDate, Phone number, Location adress, int ParticipantId) {
		this.name = name;
		this.birthDate = birthDate;
		this.number = number;
		this.adress = adress;
		this.participantId = ParticipantId;
	}

	public boolean isPlayed() {
		return played;
	}

	public void setPlayed(boolean played) {
		this.played = played;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Phone getNumber() {
		return number;
	}

	public void setNumber(Phone number) {
		this.number = number;
	}

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

	public void setPrize(int prize) {
		this.prize=prize;
	}

	public int getPrize() {
		return prize;
	}
	

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public Location getAdress() {
		return adress;
	}

	public void setAdress(Location adress) {
		this.adress = adress;
	}
	
	public void trueAnswer(){
		counter++;
	}
	
	public void showAllInformation() {
		System.out.println(name+" "+"number"+" "+birthDate+" "+adress);
	}
	public int getParticipantId() {
		return participantId;
	}
	public void setParticipantId(int participantId) {
		this.participantId = participantId;
	}
	public int getTrueAnswers() {
        return trueAnswers;
    }
    public void setTrueAnswers() {
        trueAnswers++;
    }
	
}