package src.main.java.org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.List;
import java.util.Random;

class PointPanel extends JPanel {
    private int panelWidth;
    private int panelHeight;
    private double xMin;
    private double xMax;
    private double yMin;
    private double yMax;
    private int maxPointsToDisplay;
    private List<Point> points;


    public PointPanel(int panelWidth, int panelHeight, double xMin, double xMax, double yMin, double yMax, int maxPointsToDisplay, List<Point> points) {
        this.panelWidth = panelWidth;
        this.panelHeight = panelHeight;
        this.xMin = xMin;
        this.xMax = xMax;
        this.yMin = yMin;
        this.yMax = yMax;
        this.maxPointsToDisplay = maxPointsToDisplay;
        this.points = points;

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Рисуем координатную сетку
        g2d.setColor(Color.LIGHT_GRAY);
        for (double x = xMin; x <= xMax; x += (xMax - xMin) / 10) {
            int screenX = (int) ((x - xMin) / (xMax - xMin) * panelWidth);
            g2d.drawLine(screenX, 0, screenX, panelHeight);
        }
        for (double y = yMin; y <= yMax; y += (yMax - yMin) / 10) {
            int screenY = (int) ((yMax - y) / (yMax - yMin) * panelHeight);
            g2d.drawLine(0, screenY, panelWidth, screenY);
        }

        // Ось X + разметка
        g2d.setColor(Color.BLACK);
        g2d.drawLine(50, panelHeight / 2, panelWidth - 50, panelHeight / 2);
        for (double i = xMin; i <= xMax; i++) {
            int x = (int) ((i - xMin) / (xMax - xMin) * (panelWidth - 100) + 50);
            g2d.drawString(Integer.toString((int) i), x, panelHeight / 2 + 20);
        }
        g2d.drawString("X", panelWidth - 20, panelHeight / 2 + 20);

        // Ось Y + разметка
        g2d.drawLine(panelWidth / 2, 50, panelWidth / 2, panelHeight - 50);
        for (double p = yMin; p <= yMax; p++) {
            int y = panelHeight - (int) ((p - yMin) / (yMax - yMin) * (panelHeight - 100) + 50);
            g2d.drawString(Integer.toString((int) p), panelWidth / 2 - 40, y);
        }
        g2d.drawString("Y", panelWidth / 2 - 40, 20);



        // Рисуем точки
        Random rand = new Random();
        for (Point point : points) {
            // Создание случайного цвета для каждой точки
            g2d.setColor(new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)));

            int screenX = (int) ((point.getX() - xMin) / (xMax - xMin) * panelWidth);
            int screenY = (int) ((yMax - point.getY()) / (yMax - yMin) * panelHeight);
            Ellipse2D.Double circle = new Ellipse2D.Double(screenX - 3, screenY - 3, 6, 6);
            g2d.fill(circle);
        }
    }
}