package ferru97.dev.randomgooglebackgroundandroid;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.File;
import java.io.Serializable;

public class UserSettings implements Serializable {

    //Google Project API KEY
    private final String API_KEY = "AIzaSyCVomv1sG6XEJOVOt9yLWb6syL2aZgaauE" ;

    //Custm Search Engine ID
    private  String CSE_ID ;
    //search keywords
    private  String keyword;
    //Delay between background change
    private int delay;
    //Save exist
    private boolean saveExist = false;


    public  UserSettings (Context context){

        SharedPreferences mPrefs=context.getSharedPreferences(context.getApplicationInfo().name, Context.MODE_PRIVATE);
        if(mPrefs.getString("CSE_ID", null)!=null && mPrefs.getString("keyword", null)!=null && mPrefs.getString("delay", null)!=null){
            this.setCSE_ID(mPrefs.getString("CSE_ID", null));
            this.setkeyword(mPrefs.getString("keyword", null));
            this.setDelay(Integer.valueOf(mPrefs.getString("delay", null)));
            saveExist = true;
        }
    }

    public String getCSE_ID(){return this.CSE_ID;}

    public String getAPI_KEY(){return this.API_KEY;}

    public String getkeyword(){return this.keyword;}

    public int getDelay(){return this.delay;}

    public void setCSE_ID(String CSE_ID){ this.CSE_ID = CSE_ID;}

    public void setkeyword(String keyword){ this.keyword = keyword;}

    public void setDelay(int delay){this.delay = delay;}

    public void saveSettings(Context context){
        SharedPreferences mPrefs=context.getSharedPreferences(context.getApplicationInfo().name, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed=mPrefs.edit();
        ed.putString("CSE_ID", this.CSE_ID);
        ed.putString("keyword", this.keyword);
        ed.putString("delay", String.valueOf(this.delay));
        ed.commit();
    }


    public boolean checkFilExist(){
        if(saveExist)
            return true;
        else
            return false;
    }
}
