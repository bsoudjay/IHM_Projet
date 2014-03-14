package vue;

import java.util.ArrayList;
import javax.swing.JFrame;
import model.Musique;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

public class statsGraphe extends JFrame {

    private static final long serialVersionUID = 1L;

    public statsGraphe(String applicationTitle, String chartTitle, ArrayList<Musique> liste) {
        super(applicationTitle);
        PieDataset dataset = createDataset(liste);
        JFreeChart chart = createChart(dataset, chartTitle);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(900, 970));
        setContentPane(chartPanel);

    }

    /**
     * Creates a sample dataset
     */
    private PieDataset createDataset(ArrayList<Musique> liste) {
        DefaultPieDataset result = new DefaultPieDataset();
        for (int j = 0; j < liste.size(); j++) {
            result.setValue(liste.get(j).getTitre()+" de "+liste.get(j).getAuteur(),liste.get(j).getNbEcoute());

        }
        return result;

    }

    /**
     * Creates a chart
     */
    private JFreeChart createChart(PieDataset dataset, String title) {

        JFreeChart chart = ChartFactory.createPieChart3D(title, // chart title
                dataset, // data
                true, // include legend
                true,
                false);

        PiePlot3D plot = (PiePlot3D) chart.getPlot();
        plot.setStartAngle(290);
        plot.setDirection(Rotation.CLOCKWISE);
        plot.setForegroundAlpha(0.5f);
        return chart;

    }
}
