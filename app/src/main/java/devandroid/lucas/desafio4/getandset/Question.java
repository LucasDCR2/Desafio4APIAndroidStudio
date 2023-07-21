package devandroid.lucas.desafio4.getandset;

import java.util.List;

public class Question {
    private String question;
    private List<String> options;
    private int right_option;
    private int selectedOptionIndex = -1;

    private String selectedOption;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public int getRightOption() {
        return right_option;
    }

    public void setRightOption(int right_option) {
        this.right_option = right_option;
    }

    public int getSelectedOptionIndex() {
        return selectedOptionIndex;
    }

    public void setSelectedOptionIndex(int selectedOptionIndex) {
        this.selectedOptionIndex = selectedOptionIndex;
    }

    public String getSelectedOption() {
        return selectedOption;
    }

    public void setSelectedOption(String selectedOption) {
        this.selectedOption = selectedOption;
    }
}
