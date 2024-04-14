public class Question {
    private String category;
	private String questionText;
	private String firstOption;
	private String secondOption;
	private String thirdOption;
	private String fourthOption;
	private String answer;
	private String difficulty;
	private int questionId;
	private boolean ýsShowed=false; 
	
	
	
	public Question(String category, String questionText, String firstOption, String secondOption, String thirdOption,
			String fourthOption, String answer, String difficulty, int questionId) 
	{
		
		this.category = category;
		this.questionText = questionText;
		this.firstOption = firstOption;
		this.secondOption = secondOption;
		this.thirdOption = thirdOption;
		this.fourthOption = fourthOption;
		this.answer = answer;
		this.difficulty = difficulty;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getQuestionText() {
		return questionText;
	}

	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}

	public String getFirstOption() {
		return firstOption;
	}

	public void setFirstOption(String firstOption) {
		this.firstOption = firstOption;
	}

	public String getSecondOption() {
		return secondOption;
	}

	public void setSecondOption(String secondOption) {
		this.secondOption = secondOption;
	}

	public String getThirdOption() {
		return thirdOption;
	}

	public void setThirdOption(String thirdOption) {
		this.thirdOption = thirdOption;
	}

	public String getFourthOption() {
		return fourthOption;
	}

	public void setFourthOption(String fourthOption) {
		this.fourthOption = fourthOption;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}
	
	public void display(Question question, int questionNumber) {
		System.out.println("\nQ" + questionNumber + " " + question.getQuestionText());  //question is printing
		System.out.println("A) " + question.getFirstOption());
		System.out.println("B) " + question.getSecondOption());
		System.out.println("C) " + question.getThirdOption());
		System.out.println("D) " + question.getFourthOption() + "\n");
	}
	public boolean getIsShowed() { 
		return ýsShowed;     
		}    
	
	public void setIsShowed() {  
		ýsShowed = true;    
		}
	public int getQuestionId() {
		return questionId;
	}
	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}
	
}
