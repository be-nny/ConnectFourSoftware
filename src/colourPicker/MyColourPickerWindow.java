package colourPicker;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyColourPickerWindow implements ActionListener, ChangeListener {

    // window info
    private JFrame frame;
    private static final Dimension dimension = new Dimension(300, 500);

    // colour value info
    private Color rgbValue;
    private MyColourPicker cp;

    // buttons / widgets
    private JButton okBtn = new JButton("Ok");
    private JButton cancelBtn = new JButton("Cancel");
    private JSlider colourValSlider = new JSlider();
    private JLabel redLabel = new JLabel();
    private JLabel greenLabel = new JLabel();
    private JLabel blueLabel = new JLabel();

    // jpanels
    private JPanel buttonPane = new JPanel();
    private JPanel mainPane = new JPanel();
    private JPanel infoPane = new JPanel();
    private JPanel colourPane = new JPanel();

    public MyColourPickerWindow() {
        this.frame = new JFrame("Colour Wheel");
        this.frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.frame.setSize(dimension);
        this.frame.setResizable(false);
        // setting the colour picker pane
        this.cp = new MyColourPicker(this);
        setColourPickerPane();
        // setting the button pane
        setButtonPane();


        // adding contents to window
        this.mainPane.setLayout(new BorderLayout());
        this.mainPane.add(this.colourPane, BorderLayout.CENTER);
        this.mainPane.add(this.buttonPane, BorderLayout.PAGE_END);


        this.frame.add(mainPane);
        this.frame.setVisible(true);
    }

    private void setColourValSlider(){
        this.colourValSlider.setMinimum(0);
        this.colourValSlider.setMaximum(254);

        this.colourValSlider.setPaintTicks(true);
        this.colourValSlider.setPaintLabels(true);
        this.colourValSlider.addChangeListener(this);
        this.colourValSlider.setValue(0);
    }

    private void setColourPickerPane(){
        // setting the colour value slider
        setColourValSlider();
        // setting the info pane
        setInfoPane();

        this.colourPane.add(this.cp);
        this.colourPane.add(this.colourValSlider);
        this.colourPane.add(this.infoPane);
        this.colourPane.setBackground(new Color(220,220,220));
    }

    private void setButtonPane(){
        this.okBtn.addActionListener(this);
        this.cancelBtn.addActionListener(this);

        this.okBtn.setActionCommand("ok");
        this.cancelBtn.setActionCommand("cancel");

        this.buttonPane.setLayout(new FlowLayout());
        this.buttonPane.add(this.okBtn);
        this.buttonPane.add(this.cancelBtn);
    }

    public void updateInfoPane(int r, int g, int b){
        this.redLabel.setText(String.valueOf(r));
        this.greenLabel.setText(String.valueOf(g));
        this.blueLabel.setText(String.valueOf(b));

    }

    private void setInfoPane(){
        this.infoPane.setLayout(new FlowLayout());

        this.redLabel.setText("0");
        this.greenLabel.setText("0");
        this.blueLabel.setText("0");

        this.infoPane.add(this.redLabel);
        this.infoPane.add(new JSeparator());
        this.infoPane.add(this.greenLabel);
        this.infoPane.add(new JSeparator());
        this.infoPane.add(this.blueLabel);
        this.infoPane.add(new JSeparator());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("ok")){
            this.actionOnOk();
        } else if(e.getActionCommand().equals("cancel")){
            this.actionOnClose();
        }
    }

    public void showFrame(){
        this.frame.setVisible(true);
    }

    private void actionOnOk(){
        this.rgbValue = this.cp.getUsrColor();
        this.frame.setVisible(false);
    }

    private void actionOnClose(){
        this.frame.setVisible(false);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider)e.getSource();
        this.cp.adjustColourValue(source.getValue());
    }
}
