package coms.model.dtos;


public class FAQdto {
	
	    private Long FAQid;
	    private String question;
	    private String answer;
	    private String example;
	    private String others;
		private String category;

		public FAQdto() {
			super();
		}
		public FAQdto(String question, String answer, String example, String others, String category) {
			super();
			this.question = question;
			this.answer = answer;
			this.example = example;
			this.others = others;
			this.category = category;
		}
		public Long getFAQid() {
			return FAQid;
		}
		public void setFAQid(Long fAQid) {
			FAQid = fAQid;
		}
		public String getQuestion() {
			return question;
		}
		public void setQuestion(String question) {
			this.question = question;
		}
		public String getAnswer() {
			return answer;
		}
		public void setAnswer(String answer) {
			this.answer = answer;
		}
		public String getExample() {
			return example;
		}
		public void setExample(String example) {
			this.example = example;
		}
		public String getOthers() {
			return others;
		}
		public void setOthers(String others) {
			this.others = others;
		}
		public String getCategory() {
			return category;
		}
		public void setCategory(String category) {
			this.category = category;
		}
}
