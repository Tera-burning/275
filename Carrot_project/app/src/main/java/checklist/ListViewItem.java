package checklist;

import android.widget.CheckBox;

public class ListViewItem {
    private String CheckList;
    private CheckBox Check;

    public void setChecklist(String clist){
        CheckList=clist;
    }
    public void setCheck(CheckBox check){
        Check=check;
    }

    public String getCheckList(){
        return this.CheckList;
    }
    public CheckBox getCheck(){
        return this.Check;
    }
}
