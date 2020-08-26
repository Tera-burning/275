package checklist;

import android.widget.CheckBox;
import android.widget.ListView;

public class ListViewItem {
    private String text;
    private String date;
    private String userid;
    //체크확인필요

    public ListViewItem() {}

    public ListViewItem(String text, String date, String userid){
        this.text=text;
        this.date=date;
        this.userid=userid;
    }

    public void setText(String text) {
        this.text = text;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getText() {
        return this.text;
    }
    public String getDate() {
        return this.date;
    }
    public String getUserid() {
        return this.userid;
    }

}
