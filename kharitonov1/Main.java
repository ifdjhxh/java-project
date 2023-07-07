import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    private static final int WIDTH = 1200;
    private static final int WIDTH_ELEM = 70;
    private static final int HEIGHT = 600;
    private static final int HEIGHT_ELEM = 35;
    private static JPanel panel1;
    private static JPanel panel2;
    private static JPanel panel3;

    private static List<JLabel> jLabels;
    private static List<Integer> arr;
    private static final List<List<Integer>> historyOfArrays = new ArrayList<>();
    private static void copyForHistory(List<Integer> array){
        List<Integer> tempArr = new ArrayList<>(array);
        historyOfArrays.add(tempArr);
    }
    static int calcWidth(int pos){
        pos++;
        int oldPos = pos;
        int i = 0;
        for (; pos>1; i++){
            pos /=2;
        }
        return (int) (WIDTH/2/Math.pow(2,i)+WIDTH/Math.pow(2,i)*(oldPos%Math.pow(2,i)) - WIDTH_ELEM/2);
    }

    static boolean isNumber(String str){
        for (int i = 0; i < str.length(); i++){
            if (str.charAt(i) > '0' && str.charAt(i) < '9'){
                continue;
            }else{
                return false;
            }
        }
        return true;
    }

    static int calcHeight(int pos){
        pos++;
        int i = 0;
        for (; pos>1; i++){
            pos /=2;
        }
        return 10 + 50*i;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("myApplication");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        Toolkit toolkit = Toolkit.getDefaultToolkit();
//        Dimension dimension = toolkit.getScreenSize();
        frame.setSize(WIDTH,HEIGHT);
        frame.setMinimumSize(new Dimension(WIDTH,HEIGHT));
        panel1 = new JPanel();
        panel1.setSize(WIDTH,HEIGHT-150);
//        panel1.setBackground(Color.GREEN);
        panel1.setLayout(null);
        panel2 = new JPanel();
        panel2.setSize(WIDTH,60);
//        panel2.setBackground(Color.YELLOW);
        panel2.setBounds(0,HEIGHT-90,WIDTH,100);
        panel3 = new JPanel();
        panel3.setSize(WIDTH, 40);
        panel3.setBounds(0, HEIGHT-120, WIDTH, 30);
        arr = new ArrayList<>();
        jLabels = createLabels(arr.size(), arr);
        JLabel label = new JLabel("");
        label.setForeground(Color.RED);
        label.setBackground(Color.GREEN);
        for (JLabel jlabel: jLabels){
            panel1.add(jlabel);
        }
        panel2.setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
        panel3.setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
        JTextField textField = new JTextField("", 10);
        textField.setSize(100, 25);
        JButton button = new JButton("Добавить элемент");
        JButton button2 = new JButton("Удалить последний элемент");
        JButton button0 = new JButton("Сделать шаг");
        JButton button3 = new JButton("Начать заново");

        button.addActionListener(e -> {
            label.setText("");
            try{
                int value = Integer.parseInt(textField.getText());
                if (value >= -999999 && value <= 999999) {
                    if (arr.size() > 29){
                        label.setText("Используется максимальное количество элементов");
                        panel3.repaint();
                        return;
                    }
                    copyForHistory(arr);
                    panel1.removeAll();
                    arr.add(value);
                    jLabels = createLabels(arr.size(), arr);
                    for (JLabel jlabel : jLabels) {
                        panel1.add(jlabel);
                    }
                    panel1.revalidate();
                    panel1.repaint();
                    panel3.repaint();
                    frame.revalidate();
                } else{
                    label.setText("Число по модулю должно быть меньше 1'000'000");
                    panel3.repaint();
                    return;
                }
            } catch (Exception exception) {
                if (isNumber(textField.getText())){
                    label.setText("Число по модулю должно быть меньше 1'000'000");
                }else{
                    label.setText("Введено не число");
                }
                panel3.repaint();
            }
        });
        button2.addActionListener(e -> {
            label.setText("");
            if (arr.size() < 1){
                label.setText("Дерево пустое");
                return;
            }
            panel1.removeAll();
            arr = historyOfArrays.get(historyOfArrays.size()-1);
            jLabels = createLabels(arr.size(), arr);
            historyOfArrays.remove(historyOfArrays.size()-1);
            for (JLabel jlabel : jLabels) {
                panel1.add(jlabel);
            }
            panel1.revalidate();
            panel1.repaint();
            frame.revalidate();
            frame.repaint();
            panel3.repaint();
        });
        panel2.add(button0);
        panel2.add(button);
        panel2.add(textField);
        panel2.add(button2);
        panel2.add(button3);
        panel3.add(label);
        frame.add(panel1);
        frame.add(panel2);
        frame.add(panel3);
        frame.setLayout(null);
        frame.setVisible(true);
    }

    static List<JLabel> createLabels(int count, List<Integer> ints){
        List<JLabel> jLabels = new LinkedList<>();
        if (count >=1){
            JLabel temp = new JLabel(ints.get(0).toString(), SwingConstants.CENTER);
            temp.setBounds(WIDTH/2-WIDTH_ELEM/2, 10, WIDTH_ELEM, HEIGHT_ELEM);
            temp.setBorder(BorderFactory.createLineBorder(Color.black, 2));
            jLabels.add(temp);
        }
        for (int i = 1; i < count; i++){
            JLabel temp = new JLabel(ints.get(i).toString(), SwingConstants.CENTER);
            temp.setBounds(calcWidth(i), calcHeight(i), WIDTH_ELEM, HEIGHT_ELEM);
            temp.setBorder(BorderFactory.createLineBorder(Color.black, 2));
            jLabels.add(temp);
        }
        return jLabels;
    }

    static List<Integer> createListInteger(){
        List<Integer> list = new ArrayList<>();
        list.add(5);
        list.add(2);
        list.add(65);
        list.add(-23);
        list.add(435);
        list.add(4);
        list.add(3);
        list.add(34);
        return list;
    }
}