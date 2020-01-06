import processing.core.*;
import processing.data.*;
import processing.event.*;
import processing.opengl.*;

import ddf.minim.*;
import ddf.minim.analysis.BeatDetect;
import processing.core.PApplet;

import java.util.HashMap;
import java.util.ArrayList;
import java.io.File;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;

public class MusicPlayerVisualizer extends PApplet {

    Minim minim;
    AudioPlayer player;
    BeatDetect beat;

    float bg = 0;

    public void setup() {

        minim = new Minim(this);
    }


    public void draw()
    {
        if (player != null){
            beat.detect(player.mix);
            if ( beat.isOnset() == true) { bg = 255; }
            background(bg);
            bg *= 0.95f;

            stroke(255);
            for(int i = 0; i < player.bufferSize() - 1; i++)
            {
                float left1 = 50 + player.left.get(i) * 50;
                float left2 = 50 + player.left.get(i+1) * 50;
                float right1 = 60 + player.right.get(i) * 50;
                float right2 = 60 + player.right.get(i+1) * 50;
                line(i, left1, i+1, left2);
                line(i, right1, i+1, right2);
            }
        }
    }

    public void stop()
    {
        player.close();
        minim.stop();
        super.stop();
    }

    public void visualizer(File selection){
        if (selection == null) {
            println("Window was closed or the user hit cancel.");
        } else {

            player = minim.loadFile(selection.getAbsolutePath());
            beat = new BeatDetect();
            player.play();
        }
    }

    public void mouseClicked()
    {
        player = null;

        selectInput("select an audiofile", "visualizer" );
    }



    public void settings() {  size(500, 120); }
    static public void main(String[] passedArgs) {
        String[] appletArgs = new String[] { "MusicPlayerVisualizer" };
        if (passedArgs != null) {
            PApplet.main(concat(appletArgs, passedArgs));
        } else {
            PApplet.main(appletArgs);
        }
    }
}