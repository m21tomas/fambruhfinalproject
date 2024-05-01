package coms.model.extra;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class FAQ {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long FAQid;

    private String question;

    private String answer;

    private String example;

    private String others;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id" , referencedColumnName = "category_id")
	private FAQcategory category;

	public FAQ() {
		super();
	}

	public FAQ(String question, String answer, String example, String others, FAQcategory category) {
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

	public FAQcategory getCategory() {
		return category;
	}

	public void setCategory(FAQcategory category) {
		this.category = category;
	}
}
