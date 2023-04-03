package colourPicker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class MyColourPicker extends JPanel implements MouseMotionListener {

    private static final int width = 280;
    private static final int height = 280;
    private static final int step_size = 255;
    private int colour_value = 0;
    private Color usrColor;
    private ArrayList<Color> colours = new ArrayList<>();
    private LinearGradientPaint gradientPaint;

    private MyColourPickerWindow cwFrame;

    private int mouse_x = 0;
    private int mouse_y = 0;

    public MyColourPicker(MyColourPickerWindow parent){
        this.cwFrame = parent;
        this.addMouseMotionListener(this);
        this.setPreferredSize(new Dimension(width , height));
        this.generateColours();
        this.createPicker();
    }

    protected Color getUsrColor(){
        return this.usrColor;
    }

    public ArrayList<Color> getColours(){
        return this.colours;
    }

    public Dimension getSize(){
        return new Dimension(width, height);
    }

    private void createPicker(){
        Point2D start = new Point2D.Float(0,0);
        Point2D end = new Point2D.Float(width, height);

        // setting the distance between each colour
        float[] dist = new float[this.colours.size()];
        Color[] colours = new Color[this.colours.size()];
        for(int i = 0; i < this.colours.size(); i ++){
            dist[i] = (float)(i) / (float)(this.colours.size());
            colours[i] = this.colours.get(i);
        }

        this.gradientPaint = new LinearGradientPaint(start, end, dist, colours, MultipleGradientPaint.CycleMethod.NO_CYCLE);
    }

    private void generateColours(){
        this.colours = new ArrayList<>();

        for(int r = this.colour_value; r<step_size; r ++){
            this.colours.add(new Color((r*255/step_size) - this.colour_value, 255 - this.colour_value, 0));
        }
        for(int g = step_size; g > this.colour_value; g --){
            this.colours.add(new Color(255 - this.colour_value, (g*255/step_size) - this.colour_value, 0));
        }
        for(int b = this.colour_value; b<step_size; b ++){
            this.colours.add(new Color(255 - this.colour_value, 0, (b*255/step_size) - this.colour_value));
        }
        for(int r = step_size; r > this.colour_value; r --){
            this.colours.add(new Color((r*255/step_size) - this.colour_value, 0, 255 - this.colour_value));
        }
        for(int g = this.colour_value; g<step_size; g ++){
            this.colours.add(new Color(0, (g*255/step_size) - this.colour_value, 255 - this.colour_value));
        }
        for(int b = step_size; b > this.colour_value; b --){
            this.colours.add(new Color(0, 255 - this.colour_value, (b*255/step_size) - this.colour_value));
        }

        this.colours.add(new Color(0, 255 - this.colour_value, 0));
    }

    public void adjustColourValue(int new_val){
        this.colour_value = new_val;
        this.generateColours();
        this.createPicker();
        this.repaint();
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        this.removeAll();
        revalidate();

        Graphics2D g2d = (Graphics2D) g;
        g2d.setPaint(this.gradientPaint);
        g2d.fillRect(0,0,width,height);

        g2d.setPaint(new Color(10,10,10));
        revalidate();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        try {
            Robot robot = new Robot();
            this.usrColor = robot.getPixelColor(e.getX(), e.getY());

            this.cwFrame.updateInfoPane(this.usrColor.getRed(), this.usrColor.getGreen(), this.usrColor.getBlue());

        } catch (AWTException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
