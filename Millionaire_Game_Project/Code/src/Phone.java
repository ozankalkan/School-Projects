public class Phone 
{
    private String areacode;
	private String prefix;
	private String number;
	
	public Phone(String phonenumber) {
		String[] pNumber = phonenumber.split(" ");
		areacode = pNumber[0];
		prefix = pNumber[1];
		number = pNumber[2];
	}

	public String getAreacode() {
		return areacode;
	}

	public void setAreacode(String areacode) {
		this.areacode = areacode;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getPhoneNumber() {
		return number;
	}

	public void setPhoneNumber(String number) {
		this.number = number;
	}
	public String toStr() {
		return areacode+ " " + prefix +" " + number;
	}
}
