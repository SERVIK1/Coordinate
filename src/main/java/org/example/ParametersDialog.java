package src.main.java.org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

// Диалоговое окно настройки
public class ParametersDialog extends JDialog {
    private int panelWidth;
    private int panelHeight;
    private double xMin;
    private double xMax;
    private double yMin;
    private double yMax;
    private int maxPointsToDisplay;
    private Exception NumberFormatException;

    public ParametersDialog(JFrame parent) {
        super(parent, "Параметры", true);
        setSize(400, 400);
        setLayout(new GridLayout(8, 1));

        JTextField widthField = new JTextField();
        JTextField heightField = new JTextField();
        JTextField xMinField = new JTextField();
        JTextField xMaxField = new JTextField();
        JTextField yMinField = new JTextField();
        JTextField yMaxField = new JTextField();
        JTextField pointsField = new JTextField();

        add(new JLabel("Ширина окна: от 500 до 900"));
        add(widthField);
        add(new JLabel("Высота окна: от 500 до 900"));
        add(heightField);
        add(new JLabel("X мин: от -30"));
        add(xMinField);
        add(new JLabel("X макс: до 30"));
        add(xMaxField);
        add(new JLabel("Y мин: от -30"));
        add(yMinField);
        add(new JLabel("Y макс: до 30"));
        add(yMaxField);
        add(new JLabel("Кол-во точек:"));
        add(pointsField);

        JButton okButton = new JButton("OK");
        okButton.setPreferredSize(new Dimension(90,30));
        add(okButton);

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelWidth = Integer.parseInt(widthField.getText());
                panelHeight = Integer.parseInt(heightField.getText());
                xMin = Double.parseDouble(xMinField.getText());
                xMax = Double.parseDouble(xMaxField.getText());
                yMin = Double.parseDouble(yMinField.getText());
                yMax = Double.parseDouble(yMaxField.getText());
                maxPointsToDisplay = Integer.parseInt(pointsField.getText());

                setVisible(false);
                // Проверка ограничений
                try {
                    if (panelWidth < 500 | panelWidth > 900 | panelHeight < 500 | panelHeight > 900 | panelWidth != panelHeight |
                            xMax > 30 | xMin < -30 | yMax > 30 | yMin < -30 | xMin == xMax | yMin == yMax){
                        throw NumberFormatException;
                    }
                    showInputDialog(parent);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(parent, "Нарушены допустимые пределы!\nРавные стороны от 500 до 900\n" +
                                    "X от -30 до 30\nY от -30 до 30\n Границы X не равны друг другу\nГраницы Y не равны друг другу",
                            "ERROR",JOptionPane.ERROR_MESSAGE);
                    setVisible(true);
                }
            }
        });
    }

    public void showInputDialog(JFrame parent) throws Exception {
        List<Point> points = new ArrayList<>();
        // Для каждой точки задаётся координата
        for (int i = 0; i < maxPointsToDisplay; i++) {
            JTextField field1 = new JTextField();
            JTextField field2 = new JTextField();
            Object[] message = {
                    "Координата X", field1,
                    "Координата Y", field2,

            };
            JOptionPane.showConfirmDialog(parent, message, String.format("Введите координату точки №%d",i+1), JOptionPane.OK_CANCEL_OPTION);

            // Проверка ограничений
            try {
                double x = Double.parseDouble(field1.getText());
                double y = Double.parseDouble(field2.getText());
                if (x > xMax | x < xMin | y > yMax | y < yMin){
                    throw NumberFormatException;
                }
                points.add(new Point(x, y));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(parent, "Некорректный ввод", "ERROR",JOptionPane.ERROR_MESSAGE);
                i--;
            }
        }



        PointPanel panel = new PointPanel(panelWidth, panelHeight, xMin, xMax, yMin, yMax, maxPointsToDisplay, points);

        JFrame frame = new JFrame("ЛР №3 Распопин / Москалёв / АВТ-143");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.setSize(panelWidth, panelHeight);
        frame.setVisible(true);
    }
}
