import java.util.Date;
public class getTime  extends Thread {
    private final Myframe myframe;
    public getTime(Myframe myframe){
        this.myframe = myframe;
    }
    @Override
    public void run() {
        super.run();
        while(true) {
            myframe.Date.setText(myframe.dateFormat.format(new Date()));
            myframe.Time.setText(myframe.timeFormat.format(new Date()));
            myframe.SettingDate.setText(myframe.settingdateFormat.format(new Date()));
            myframe.SettingTime.setText(myframe.settingtimeFormat.format(new Date()));
            if(myframe.stop){
                break;
            }
        }
    }
}
