package com.zephyrs.android.onefriend;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

/**
 * Created by Barry on 12/9/17.
 */
@IgnoreExtraProperties
public class Report implements Serializable {
    public String Exercise;
    public Long Finally;
    public String Hobby;
    public String Meditation;
    public String Sleep;
    public String Social;
    public Long Today;
    public String Water;



    public Report(String Exercise,Long Finally,String Hobby,String Meditation,String Sleep,String Social,Long Today,String Water){
        this.Exercise = Exercise;
        this.Finally = Finally;
        this.Hobby = Hobby;
        this.Meditation = Meditation;
        this.Sleep = Sleep;
        this.Social = Social;
        this.Today = Today;
        this.Water = Water;
    }
    public Report(){
    }
//    public Long getFinally(){
//        return Finally;
//    }
//    public void setFinally(Long Finally){
//        this.Finally = Finally;
//    }
//    public Long getToday(){
//        return Today;
//    }
//    public String getExercise(){
//        return Exercise;
//    }
//    public String getHobby(){
//        return Hobby;
//    }
//    public String getMeditation(){
//        return Meditation;
//    }
//    public String getSleep(){
//        return Sleep;
//    }
//    public String getSocial(){
//        return Social;
//    }
//    public String getWater(){
//        return Water;
//    }
}
