package com.example.trainkiosk;

import javafx.scene.media.AudioClip;

public class AudioPlayer {
    AudioClip click = new AudioClip(getClass().getResource("/audio/btnClick.wav").toExternalForm());
    AudioClip error = new AudioClip(getClass().getResource("/audio/error.wav").toExternalForm());
    AudioClip success = new AudioClip(getClass().getResource("/audio/success.wav").toExternalForm());

    public void playClick(){click.play();}
    public void playError(){error.play();}
    public void playSuccess(){success.play();}
}
