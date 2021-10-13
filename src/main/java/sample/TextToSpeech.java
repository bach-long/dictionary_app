package sample;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class TextToSpeech {
    private Voice voice;
    TextToSpeech() {
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
        voice = VoiceManager.getInstance().getVoice("kevin16");
    }

    public void toSpeech(String s) {
        if (voice != null) {
            voice.allocate();

            boolean status = voice.speak(s);

            voice.deallocate();
        } else {
            System.out.println("error!");
        }
    }
}
