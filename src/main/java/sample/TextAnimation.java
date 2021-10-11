package sample;

import java.util.Random;

public class TextAnimation implements Runnable{
    private String text;
    private int animationTime;
    private TextOutput textOutput;
    private Random random = new Random();

    public TextAnimation(String text, int animationTime, TextOutput textField) {
        this.text = text;
        this.animationTime = animationTime;
        this.textOutput = textField;
    }

    @Override
    public void run() {

        try {
            for (int i = 0; i <= text.length(); i++) {
                String textAtThisPoint = text.substring(0,i);

                textOutput.writeText(textAtThisPoint);
                Thread.sleep(animationTime + random.nextInt(150));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
