import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

/*
 * Hilfsklasse HistogramPanel zur Darstellung eines Histogramms
 */    
public class HistogramPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    int[] values;
    int max;

    int getMax(int[] values) {
        if (values == null) return 0;
        
        int max = values[0];
        for (int i=1; i < values.length; i++) {
            if (values[i] > max) max = values[i];
        }
        return max;
    }

    HistogramPanel(int[] v) {
        setMinimumSize(new Dimension(276, 20));
        setPreferredSize(new Dimension(560, 100));
        setBorder(new LineBorder(Color.WHITE, 10));
    }

    public void setValues(int[] v) {
        values = v;
        if (values != null) {
            max = getMax(values);
            repaint();
        }
    }

    public void paint(Graphics g) {
        super.paint(g);
        Color col = g.getColor();

        int left, top, bottom, right, availH, availW;
        left = getInsets().left;
        top = getInsets().top;
        bottom = getInsets().bottom;
        right = getInsets().right;
        availH = getHeight() - (top + bottom);
        availW = getWidth() - (left + right);

        g.clearRect(left, top, getWidth() - (left + right), getHeight() - (top + bottom));

        if (values != null) {
            int COLUMNWIDTH = availW / values.length;
            int usedW = values.length * COLUMNWIDTH;

            //Achsen            
            g.setColor(Color.RED);
            g.drawLine(left, top + availH - 1, left + 1 + (values.length - 1) * COLUMNWIDTH - 1, top + availH -1); //X-Achse
            g.drawLine(left, top + availH - 1, left, top); //Y-Achse
            g.drawLine(left + usedW, top + availH - 1, left + usedW, top); //Y-Achse rechts

            g.setColor(Color.GRAY);
            for (int i=0; i<values.length; i++) {
                int h = (int)(values[i]/(double)max * availH);
                g.fillRect(left + 1 + i * COLUMNWIDTH, top + availH -1 - h, COLUMNWIDTH, h);
            }

            g.setColor(col);
        }
    }
}

