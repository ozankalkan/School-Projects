import java.util.Scanner;

public class Date {
    private int day;
    private int month;
	private int year;
	
	public Date(int day, int month, int year) {
		this.day = day;
		this.month = month;
		this.year = year;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		
		this.day = day;
			
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		
		this.month = month;		
	}
	public String toStr() {
		return day+ " " + month +" " + year;
	}
	public int getAge() {
		int age = 2022 - year;
		return age;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;	
	}
	
	
}