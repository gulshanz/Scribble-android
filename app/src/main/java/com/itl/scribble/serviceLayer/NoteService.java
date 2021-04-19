package com.itl.scribble.serviceLayer;

import android.util.Log;

import com.itl.scribble.helperClasses.Keys;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class NoteService {
    public static String getSearchTearm(HashMap<String, Double> dataMap, JSONObject emotion_te) {
        StringBuilder str = new StringBuilder();
        Log.d("fehwiofj", dataMap.toString());
        Log.d("fehwiofj", emotion_te.toString());

        str.append("How to ");
        try {
            double polarity = dataMap.get(Keys.polarity);
            double subjectivity = dataMap.get(Keys.subjectivity);
            double happy = emotion_te.getDouble(Keys.happy);
            double angry = emotion_te.getDouble(Keys.angry);
            double surprise = emotion_te.getDouble(Keys.surprise);
            double sad = emotion_te.getDouble(Keys.sad);
            double fear = emotion_te.getDouble(Keys.fear);
            //if texttoemotion data is empty
            //on the basis of polarity give search term
            if (polarity == 0 && subjectivity == 0) {
                return "";
            } else {
                if (subjectivity >= 0.1) {
                    if (polarity <= -0.3) {
                        str.append("stop negativity");
                        String string = getEmotionsFromObj(happy, angry, surprise, sad, fear);
                        str.append(string);
                    } else if (polarity >= -0.2 && polarity <= 0.3) {
                        str.append("overcome boredom");
                        String string = getEmotionsFromObj(happy, angry, surprise, sad, fear);
                        str.append(string);
                    } else if (polarity >= 0.3 && polarity <= 0.6) {
                        str.append("stay happy");
                        String string = getEmotionsFromObj(happy, angry, surprise, sad, fear);
                        str.append(string);
                    } else if (polarity >= 0.6) {
                        str.append("live balanced life");
                        String string = getEmotionsFromObj(happy, angry, surprise, sad, fear);
                        str.append(string);
                    }
                }
            }
            return str.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String getEmotionsFromObj(double happy, double angry, double surprise, double sad, double fear) {
        StringBuilder builder = new StringBuilder();
        if (happy == 0 && angry == 0 && surprise == 0 && sad == 0 && fear == 0)
            return "";
        if (sad >= 0.5)
            builder.append(" not to be sad");
        if (happy >= 0.6)
            builder.append(" spread happiness");
        if (angry >= 0.5)
            builder.append(" overcome anger");
        if (fear >= 0.35)
            builder.append(" fearless");
        if (surprise > 0.5)
            builder.append(" surprise");
        Log.d("hwuioehfio", builder.toString());
        return builder.toString();
    }
}
