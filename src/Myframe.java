import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.lang.*;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class Myframe extends JFrame implements MouseListener,ActionListener,ItemListener, WindowListener {

    private static final String PS_FONT_NAME = null;
    private static final int PS_BTN_FONT_SIZE = 18;
    private static final int PS_BTN_WIDTH = 121;
    private static final int PS_BTN_HEIGHT = 60;
    final JFrame frame = new JFrame();
    private JLabel TimePage = null;
    private JLabel TimeSettingPage = null;
    private JLabel DateTime = null;
    public JLabel Date = null;
    public JLabel Time = null;
    public JLabel SettingDateTime = null;
    public JLabel SettingDate = null;
    public JLabel SettingTime = null;
    private JButton btnSave = null;
    private JButton btnCancel = null;
    public String zone;
    private JComboBox cb = null;
    public boolean stop=false;
    public SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
    public SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    public SimpleDateFormat settingdateFormat = new SimpleDateFormat("dd/MM/yy");
    public SimpleDateFormat settingtimeFormat = new SimpleDateFormat("HH:mm:ss");

    public JLabel System_lb=null;
    public JLabel System_zone=null;
    public  Myframe() {

        super("Time");

        try {
            File file = new File(System.getProperty("user.dir")+"/Timezones.txt");
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                zone = myReader.nextLine();
                System.out.println(zone);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }


        Font buttonFont = new Font(PS_FONT_NAME, Font.BOLD, PS_BTN_FONT_SIZE);
        frame.setBounds(0, 0, 1024, 600);
        frame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        frame.setResizable(false);

        //TimePage
        TimePage = new JLabel();
        TimePage.setBounds(0,0,1024,600);
        frame.getContentPane().add(TimePage);

        //label:DateTime
        DateTime = new JLabel();
        DateTime.setBorder(BorderFactory.createLineBorder(Color.blue, 1));
        DateTime.setBounds(362, 225, 300, 150);
        TimePage.add(DateTime);

        //label:Time
        Date = new JLabel("Date",SwingConstants.CENTER);
        Date.setFont(new Font(null,Font.BOLD,20));
        Date.setBounds(0, 35, 300, 50);
        DateTime.add(Date);

        //label:Time
        Time = new JLabel("Time",SwingConstants.CENTER);
        Time.setFont(new Font(null,Font.BOLD,20));
        Time.setBounds(0, 65, 300, 50);
        DateTime.add(Time);

        //TimeSettingPage
        TimeSettingPage = new JLabel();
        TimeSettingPage.setVisible(false);
        TimeSettingPage.setBackground(Color.red);
        TimeSettingPage.setBounds(0,0,1024,600);
        frame.getContentPane().add(TimeSettingPage);

        //Button:Cancel
        btnCancel = new JButton("CANCEL");
        btnCancel.setFont(buttonFont);
        btnCancel.setBounds(750, 450, PS_BTN_WIDTH, PS_BTN_HEIGHT);
        TimeSettingPage.add(btnCancel);

        //Button:Save
        btnSave = new JButton("SAVE");
        btnSave.setFont(buttonFont);
        btnSave.setBounds(880, 450, PS_BTN_WIDTH, PS_BTN_HEIGHT);
        TimeSettingPage.add(btnSave);

        //box:cb
        cb = new JComboBox();
        cb.setBounds(362, 200, 300, 30);
        TimeSettingPage.add(cb);

        //label:DateTime
        SettingDateTime = new JLabel();
        SettingDateTime.setBorder(BorderFactory.createLineBorder(Color.blue, 1));
        SettingDateTime.setBounds(800, 10, 200, 100);
        TimeSettingPage.add(SettingDateTime);

        //label:Date
        SettingDate = new JLabel("Date",SwingConstants.CENTER);
        SettingDate.setFont(new Font(null,Font.BOLD,14));
        SettingDate.setBounds(0, 20, 200, 30);
        SettingDateTime.add(SettingDate);

        //label:Time
        SettingTime = new JLabel("Time",SwingConstants.CENTER);
        SettingTime.setFont(new Font(null,Font.BOLD,14));
        SettingTime.setBounds(0, 40, 200, 30);
        SettingDateTime.add(SettingTime);

        //label: system_zone_lb
        System_lb = new JLabel("System Time Zone:");
        System_lb.setFont(new Font(null,Font.BOLD,14));
        System_lb.setBounds(362, 230, 200, 40);
        TimeSettingPage.add( System_lb);

        //label: system_zone
        System_zone = new JLabel(zone);
        System_zone.setFont(new Font(null,Font.BOLD,14));
        System_zone.setBounds(510, 230, 200, 40);
        TimeSettingPage.add( System_zone);





        try {
            File file = new File(System.getProperty("user.dir")+"/ListTimeZone.txt");
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                String s = myReader.nextLine();
                cb.addItem(s);

            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }



        frame.setVisible(true);


    }

    public void Init() {
        Controls();
    }
    private void Controls() {

        stop=false;
        final getTime _time= new getTime(this);
        _time.start();
        dateFormat.setTimeZone(TimeZone.getTimeZone(zone));
        timeFormat.setTimeZone(TimeZone.getTimeZone(zone));
        settingdateFormat.setTimeZone(TimeZone.getTimeZone(zone));
        settingtimeFormat.setTimeZone(TimeZone.getTimeZone(zone));

        cb.addItemListener(this);
        btnSave.addActionListener(this);
        btnSave.setEnabled(false);
        btnCancel.addActionListener(this);
        DateTime.addMouseListener(this);
        frame.addWindowListener(this);
    }

    public void itemStateChanged(ItemEvent e){

        if (e.getStateChange() == ItemEvent.SELECTED) {
            Object Item = (Object) e.getItem();
            zone = Item.toString();
            if(zone.equals("")){
                btnSave.setEnabled(false);
            }else{
                btnSave.setEnabled(true);
                zone = Item.toString();
                settingdateFormat.setTimeZone(TimeZone.getTimeZone(zone));
                settingtimeFormat.setTimeZone(TimeZone.getTimeZone(zone));
            }
        }

    }

    public void actionPerformed(ActionEvent e) {

        Object obj = e.getSource();

        if (obj.equals(btnSave)) {
            TimeSettingPage.setVisible(false);
            TimePage.setVisible(true);
            dateFormat.setTimeZone(TimeZone.getTimeZone(zone));
            timeFormat.setTimeZone(TimeZone.getTimeZone(zone));
            System_zone.setText(zone);
            try {
                FileWriter myWriter = new FileWriter(System.getProperty("user.dir")+"/Timezones.txt");
                   myWriter.write(zone);
                   myWriter.close();
                //System.out.println("Successfully wrote to the file.");
            } catch (IOException e2) {
                System.out.println("An error occurred.");
                e2.printStackTrace();
            }

        }

        if (obj.equals(btnCancel)) {
            TimeSettingPage.setVisible(false);
            TimePage.setVisible(true);
        }
    }

    public void mouseClicked(MouseEvent e) {
        Object obj = e.getSource();
        if (obj.equals(DateTime)) {
            TimePage.setVisible(false);
            TimeSettingPage.setVisible(true);
        }
    }
    public void mousePressed(MouseEvent e) {
    }
    public void mouseReleased(MouseEvent e) {
    }
    public void mouseEntered(MouseEvent e) {
    }
    public void mouseExited(MouseEvent e) {
    }
    public void windowActivated(WindowEvent e) {
    }
    public void windowClosed(WindowEvent e) {
    }
    public void windowClosing(WindowEvent e) {
        System.out.println("close");
        stop=true;
        frame.dispose();
    }

    public void windowDeactivated(WindowEvent e) {
    }
    public void windowDeiconified(WindowEvent e) {
    }
    public void windowIconified(WindowEvent e) {
    }
    public void windowOpened(WindowEvent e) {
    }


}