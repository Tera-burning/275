package com.example.carrot_project;


import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class StudytimeData {
    //public String day;
    public String studytime;
    //public String day;

    public StudytimeData() {
        // Default constructor required for calls to DataSnapshot.getValue(FirebasePost.class)
    }

    public StudytimeData(String day, String studytime) {
        //this.day = day;
        this.studytime = studytime;
        //this.day = day;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        //result.put("day", day);
        result.put("studytime", studytime);
        //result.put("day", day);
        return result;
    }
}