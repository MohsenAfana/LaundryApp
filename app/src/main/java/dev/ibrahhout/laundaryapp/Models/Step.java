package dev.ibrahhout.laundaryapp.Models;


public class Step {

    private String stepText;
    private int stepImage;
    private int stepNum;

    public Step() {
    }

    public Step(String stepText, int stepImage, int stepNum) {
        this.stepText = stepText;
        this.stepImage = stepImage;
        this.stepNum = stepNum;
    }

    public String getStepText() {
        return stepText;
    }

    public void setStepText(String stepText) {
        this.stepText = stepText;
    }

    public int getStepImage() {
        return stepImage;
    }

    public void setStepImage(int stepImage) {
        this.stepImage = stepImage;
    }

    public int getStepNum() {
        return stepNum;
    }

    public void setStepNum(int stepNum) {
        this.stepNum = stepNum;
    }
}

