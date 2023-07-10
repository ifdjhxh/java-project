package main;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.List;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    enum Type{
        FILE,
        UI,
        NOT_SELECTED
    }
    private static Type type = Type.NOT_SELECTED;
    private static final int WIDTH = 1200;
    private static final int WIDTH_ELEM = 70;
    private static final int HEIGHT = 600;
    private static final int HEIGHT_ELEM = 35;
    private static JPanel panel1;
    private static JPanel panel2;
    private static JPanel panel3;
    private static JPanel panel4;
    private static JLabel label;
    private static JLabel label2;
    private static JFileChooser jFileChooser;
    private static List<JLabel> jLabels;
    private static List<Integer> arr;
    private static String path = "";
    private static final List<List<Integer>> historyOfArrays = new ArrayList<>();
    private static int[] arrayFile;
    private static int current;
    private static JTextField textField;
    private static Heap heap;
    private static boolean canAdd;
    private static int next;
    private static boolean isFirst = true;
    private static boolean isSorting = false;
    private static boolean changeElements = true;
    private static List<Integer> resultArray = new ArrayList<>();
    private static void copyForHistory(List<Integer> array){
        List<Integer> tempArr = new ArrayList<>(array);
        historyOfArrays.add(tempArr);
    }
    public static int calcWidth(int pos){
        pos++;
        int oldPos = pos;
        int i = 0;
        for (; pos>1; i++){
            pos /=2;
        }
        return (int) (WIDTH/2/Math.pow(2,i)+WIDTH/Math.pow(2,i)*(oldPos%Math.pow(2,i)) - WIDTH_ELEM/2);
    }

    public static boolean isNumber(String str){
        boolean flag = false;
        for (int i = 0; i < str.length(); i++){
            if (str.charAt(i) > '0' && str.charAt(i) < '9'){
                flag = true;
                continue;
            }else{
                return false;
            }
        }
        return flag;
    }

    public static int calcHeight(int pos){
        pos++;
        int i = 0;
        for (; pos>1; i++){
            pos /=2;
        }
        return 10 + 50*i;
    }
    static void copyToAL(List<Integer> list, int[] arr, int len){
        list.clear();
        for (int i = 0; i < len; i++){
            list.add(arr[i]);
        }
    }
    public static String makeReverseArray(List<Integer> list){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < list.size()-1; i++){
            stringBuilder.append(list.get(list.size()-i-1));
            stringBuilder.append(", ");
        }
        stringBuilder.append(list.get(0));
        return stringBuilder.toString();

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("myApplication");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        Toolkit toolkit = Toolkit.getDefaultToolkit();
//        Dimension dimension = toolkit.getScreenSize();
        frame.setSize(WIDTH,HEIGHT);
        frame.setMinimumSize(new Dimension(WIDTH,HEIGHT));

        panel1 = createPanel1();
        panel2 = createPanel2();
        panel3 = createPanel3();
        panel4 = createPanel4();
        label2 = new JLabel("");
        arr = new ArrayList<>();
        jLabels = createLabels(arr.size(), arr);
        label = new JLabel("Выберите тип ввода");
        label.setForeground(Color.black);
        for (JLabel jlabel: jLabels){
            panel1.add(jlabel);
        }
        panel2.setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
        panel3.setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
        panel4.setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
        textField = new JTextField("", 10);
        textField.setSize(100, 25);
        JButton button = new JButton("Добавить элемент");
        JButton button2 = new JButton("Удалить последний элемент");
        JButton button0 = new JButton("Сделать шаг");
        JButton button3 = new JButton("Начать заново");
        JButton button4 = new JButton("Извлечь массив");
        JButton button5 = new JButton("Чтение из файла");
        JButton button6 = new JButton("Интерактивный ввод");
        heap = new Heap();
        canAdd = true;
        next = -1;
/*
    int next = heap.getLast() - 1;
    while(next > -1){
        next = sift_up_next()
    }
 */
        button0.addActionListener(e -> {
            if (type == Type.NOT_SELECTED) return;
            if (arr.size() == 0){
                panel1.removeAll();
                jLabels = createLabels(arr.size(), arr);
                for (JLabel jlabel : jLabels) {
                    panel1.add(jlabel);
                }
                label.setForeground(Color.black);
                label.setText("Массив построен");
                panel1.revalidate();
                panel1.repaint();
                panel3.repaint();
                frame.revalidate();
                return;
            }
            if (isSorting){
                if (changeElements || arr.size() == 1){
                    resultArray.add(heap.heap_sort_start());
                    copyToAL(arr, heap.getHeap(), heap.getLast());
                    panel1.removeAll();
                    jLabels = createLabels(arr.size(), arr);
                    for (JLabel jlabel : jLabels) {
                        panel1.add(jlabel);
                    }
                    label.setForeground(Color.black);
                    label.setText("Извлечен максимальный элемент. Сделайте шаг");
                    label2.setText(makeReverseArray(resultArray));
                    panel1.revalidate();
                    panel1.repaint();
                    panel3.repaint();
                    frame.revalidate();
                    changeElements = false;

                }else{
                    if (isFirst){
                        next = 0;
                        isFirst = false;
                    }
                    next = heap.sift_down_step(next);
                    if (next == -1) {
                        isFirst = true;
                        changeElements = true;
                        panel1.removeAll();
                        jLabels = createLabels(arr.size(), arr);
                        for (JLabel jlabel : jLabels) {
                            panel1.add(jlabel);
                        }
                        if (heap.getState() == Heap.States.COMPARE){
                            jLabels.get(heap.getIndex1()).setForeground(Color.red);
                            if (heap.getIndex2() < heap.getLast()){
                                jLabels.get(heap.getIndex2()).setForeground(Color.red);
                                jLabels.get(heap.getIndex2()).setBorder(BorderFactory.createLineBorder(Color.red, 2));
                            }
                            if (heap.getIndex3() < heap.getLast()){
                                jLabels.get(heap.getIndex3()).setForeground(Color.red);
                                jLabels.get(heap.getIndex3()).setBorder(BorderFactory.createLineBorder(Color.red, 2));
                            }
                            jLabels.get(heap.getIndex1()).setBorder(BorderFactory.createLineBorder(Color.red, 2));
                            label.setForeground(Color.black);
                            label.setText("Элементы не поменялись. Сделайте шаг");
                        }
                        panel1.revalidate();
                        panel1.repaint();
                        panel3.repaint();
                        frame.revalidate();
                        return;
                    }
                    copyToAL(arr, heap.getHeap(), heap.getLast());
                    panel1.removeAll();
                    jLabels = createLabels(arr.size(), arr);
                    for (JLabel jlabel : jLabels) {
                        panel1.add(jlabel);
                    }
                    if (heap.getState() == Heap.States.CHANGE){
                        jLabels.get(heap.getIndex1()).setForeground(Color.blue);
                        if (heap.getIndex2() < heap.getLast()){
                            jLabels.get(heap.getIndex2()).setForeground(Color.blue);
                            jLabels.get(heap.getIndex2()).setBorder(BorderFactory.createLineBorder(Color.blue, 2));
                        }
                        if (heap.getIndex3() < heap.getLast()) {
                            jLabels.get(heap.getIndex3()).setForeground(Color.red);
                            jLabels.get(heap.getIndex3()).setBorder(BorderFactory.createLineBorder(Color.red, 2));
                        }
                        jLabels.get(heap.getIndex1()).setBorder(BorderFactory.createLineBorder(Color.blue, 2));
                    }
                    label.setForeground(Color.black);
                    label.setText("Элементы " + heap.getHeap()[heap.getIndex1()] + " и " + heap.getHeap()[heap.getIndex2()] + " поменялись местами. Сделайте шаг");
                    panel1.revalidate();
                    panel1.repaint();
                    panel3.repaint();
                    frame.revalidate();
                }
            }else {
                if (!canAdd) {
                    if (isFirst) {
                        next = heap.getLast() - 1;
                        isFirst = false;
                    }
                    next = heap.sift_up_step(next);
                    if (next == -1) {
                        canAdd = true;
                        isFirst = true;
                        panel1.removeAll();
                        jLabels = createLabels(arr.size(), arr);
                        for (JLabel jlabel : jLabels) {
                            panel1.add(jlabel);
                        }
                        if (heap.getState() == Heap.States.COMPARE){
                            jLabels.get(heap.getIndex1()).setForeground(Color.red);
                            jLabels.get(heap.getIndex2()).setForeground(Color.red);
                            jLabels.get(heap.getIndex1()).setBorder(BorderFactory.createLineBorder(Color.red, 2));
                            jLabels.get(heap.getIndex2()).setBorder(BorderFactory.createLineBorder(Color.red, 2));
                        }
                        label.setForeground(Color.black);
                        label.setText("Элементы не поменялись. Добавьте новый элемент");
                        panel1.revalidate();
                        panel1.repaint();
                        panel3.repaint();
                        frame.revalidate();
                        return;
                    }
//                    label.setText("");
//                    label.setForeground(Color.black);

                    copyToAL(arr, heap.getHeap(), heap.getLast());
                    panel1.removeAll();
                    jLabels = createLabels(arr.size(), arr);
                    for (JLabel jlabel : jLabels) {
                        panel1.add(jlabel);
                    }

                    if (heap.getState() == Heap.States.CHANGE){
                        jLabels.get(heap.getIndex1()).setForeground(Color.blue);
                        jLabels.get(heap.getIndex2()).setForeground(Color.blue);
                        jLabels.get(heap.getIndex1()).setBorder(BorderFactory.createLineBorder(Color.blue, 2));
                        jLabels.get(heap.getIndex2()).setBorder(BorderFactory.createLineBorder(Color.blue, 2));
                        label.setForeground(Color.black);
                        label.setText("Элементы " + heap.getHeap()[heap.getIndex1()] + " и " + heap.getHeap()[heap.getIndex2()] + " поменялись местами.  Сделайте шаг");
                    }
                    panel1.revalidate();
                    panel1.repaint();
                    panel3.repaint();
                    frame.revalidate();
                }
            }
        });
        button.addActionListener(e -> {
            if (type == Type.NOT_SELECTED) return;

            if (isSorting) return;
            if (canAdd) {
                label.setText("");
                try {
                    int value;
                    if (type == Type.UI){
                        value = Integer.parseInt(textField.getText());
                    }else{
                        if (current < arrayFile.length){
                            value = arrayFile[current];
                            current++;
                        }else{
                            label.setText("Весь файл считан");
                            label.setForeground(Color.red);
                            panel3.repaint();
                            return;
                        }
                    }

                    if (value >= -999999 && value <= 999999) {
                        if (arr.size() > 29) {
                            label.setText("Используется максимальное количество элементов");
                            label.setForeground(Color.red);
                            panel3.repaint();
                            return;
                        }
                        copyForHistory(arr);
//                        copyForHistory(arr);
                        panel1.removeAll();
                        arr.add(value);
                        heap.add_no_sift(value);
                        jLabels = createLabels(arr.size(), arr);
                        for (JLabel jlabel : jLabels) {
                            panel1.add(jlabel);
                        }
                        label.setText("Элемент добавлен. Сделайте шаг.");
                        label.setForeground(Color.black);
                        panel1.revalidate();
                        panel1.repaint();
                        panel3.repaint();
                        frame.revalidate();
                        canAdd = false;
                    } else {
                        if (textField.getText().equals("")){
                            label.setForeground(Color.red);
                            label.setText("Введена пустая строка");
                        }else{
                            label.setForeground(Color.red);
                            label.setText("Число по модулю должно быть меньше 1'000'000");
                        }
                        panel3.repaint();
                        return;
                    }
                } catch (Exception exception) {
                    if (isNumber(textField.getText())) {
                        label.setForeground(Color.red);
                        label.setText("Число по модулю должно быть меньше 1'000'000");
                    } else {
                        label.setForeground(Color.red);
                        label.setText("Введено не число (или не целое)");
                    }
                    panel3.repaint();
                }
            }
        });
        button2.addActionListener(e -> {
            if (type == Type.NOT_SELECTED) return;

            if (canAdd && !isSorting) {
                if (arr.size() > 0){
                    if (type == Type.FILE){
                        current--;
                    }
                    arr = historyOfArrays.get(historyOfArrays.size()-1);
                    historyOfArrays.remove(historyOfArrays.size()-1);
                    heap = new Heap();
                    for (int i: arr){
                        heap.add(i);
                    }
                    panel1.removeAll();
                    jLabels = createLabels(arr.size(), arr);
                    for (JLabel jlabel : jLabels) {
                        panel1.add(jlabel);
                    }
                    panel1.revalidate();
                    panel1.repaint();
                    panel3.repaint();
                    frame.revalidate();
                }
            }
        });
        button3.addActionListener(e -> {
            if (type == Type.NOT_SELECTED) return;

            panel1.removeAll();
            heap = new Heap();
            canAdd = true;
            next = -1;
            arr.clear();
            isFirst = true;
            isSorting = false;
            changeElements = true;
            resultArray = new ArrayList<>();
            label.setForeground(Color.black);
            label.setText("Выберите тип ввода");
            label2.setText("");
            type = Type.NOT_SELECTED;
            panel1.revalidate();
            panel1.repaint();
            panel3.repaint();
            frame.revalidate();
        });
        button4.addActionListener(e -> {
            if (type == Type.NOT_SELECTED) return;
            if (canAdd && arr.size() > 0) {
                if (!isSorting) {
                    isSorting = true;
                    label.setForeground(Color.black);
                    label.setText("Начинается построение массива. Сделайте шаг");
                    panel1.removeAll();
                    jLabels.clear();
                    jLabels = createLabels(arr.size(), arr);
                    for (JLabel jlabel : jLabels) {
                        panel1.add(jlabel);
                    }
                    panel1.revalidate();
                    panel1.repaint();
                    panel3.repaint();
                    frame.revalidate();
                }
            }
        });
        button5.addActionListener(e -> {
            if (type == Type.NOT_SELECTED){
                JFrame frame2 = new JFrame("Выбрать файл");
                frame2.setSize(700, 500);
                jFileChooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
                jFileChooser.setFileFilter(filter);
                jFileChooser.showOpenDialog(frame2);
                try{
                    path = jFileChooser.getSelectedFile().toString();
                    System.out.println(path);
                    ReadFile readFile = new ReadFile();
                    readFile.setFileName(path);
                    arrayFile = readFile.makeArray();
                    current = 0;
                    type = Type.FILE;
                    label.setForeground(Color.black);
                    label.setText("Добавьте новый элемент");
                    panel1.revalidate();
                    panel1.repaint();
                    panel3.repaint();
                    frame.revalidate();
                } catch(IOException | NumberFormatException exception){
                    label.setForeground(Color.red);
                    label.setText("Ошибка чтения файла");
                    panel1.revalidate();
                    panel1.repaint();
                    panel3.repaint();
                    frame.revalidate();
                } catch(Exception exception){
                    label.setForeground(Color.red);
                    label.setText("Файл не выбран");
                    panel1.revalidate();
                    panel1.repaint();
                    panel3.repaint();
                    frame.revalidate();
                }
//                type = Type.FILE;
            }
        });
        button6.addActionListener(e -> {
            if (type == Type.NOT_SELECTED){
                type = Type.UI;
                label.setForeground(Color.black);
                label.setText("Добавьте новый элемент");
                panel1.revalidate();
                panel1.repaint();
                panel3.repaint();
                frame.revalidate();
            }
        });
        panel2.add(button0);
        panel2.add(button);
        panel2.add(textField);
        panel2.add(button2);
        panel2.add(button4);
        panel2.add(button3);
        panel2.add(button5);
        panel2.add(button6);
        panel3.add(label);
        panel4.add(label2);
        frame.add(panel1);
        frame.add(panel2);
        frame.add(panel3);
        frame.add(panel4);
        frame.setLayout(null);
        frame.setVisible(true);
    }

    private static JPanel createPanel4() {
        JPanel panel = new JPanel();
        panel.setSize(WIDTH,40);
        panel.setBounds(0,HEIGHT-160,WIDTH,40);
//        panel.setBackground(Color.RED);
        return panel;
    }

    static JPanel createPanel1(){
        JPanel panel = new JPanel();
        panel.setSize(WIDTH,HEIGHT-160);
        panel.setLayout(null);
        return panel;
    }

    static JPanel createPanel2(){
        JPanel panel = new JPanel();
        panel.setSize(WIDTH,60);
        panel.setBounds(0,HEIGHT-90,WIDTH,100);
        return panel;
    }

    static JPanel createPanel3(){
        JPanel panel = new JPanel();
        panel.setSize(WIDTH, 40);
        panel.setBounds(0, HEIGHT-120, WIDTH, 30);
        return panel;
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
}
