
public class ListEntry {
	private int count; // number of occurrences of the word in the text file
	private String word; // the word added to the hash table
	private String fileName; // name of the text file
	private int totalWordCount; // the total number of words in the text file
	
	
	ListEntry(String word, String fileName, int totalWordCount){
		this.word = word;
		this.fileName = fileName;
		this.totalWordCount = totalWordCount;
		this.count = 1;
	}
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public int getTotalWordCount() {
		return totalWordCount;
	}
	public void setTotalWordCount(int totalWordCount) {
		this.totalWordCount = totalWordCount;
	}
	
}
