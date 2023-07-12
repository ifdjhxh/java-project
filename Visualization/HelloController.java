package com.example.demo1;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HelloController {



    enum Type{
        FILE,
        UI,
        NOT_SELECTED
    }
    private Type type = Type.NOT_SELECTED;
    private String path = "";
    private List<Integer> arr = new ArrayList<>();

    private boolean isFirst = true;
    private boolean isSorting = false;
    private boolean changeElements = true;
    private List<Integer> resultArray = new ArrayList<>();
    private boolean canAdd = false;
    private int[] arrayFile;
    private int current;
    private Heap heap = new Heap();
    private int next;
    private FileChooser fileChooser;
    public static final int WIDTH = 1200;
    private static final List<List<Integer>> historyOfArrays = new ArrayList<>();



    public static String makeReverseArray(List<Integer> list){
        if (list.isEmpty()) return "";
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < list.size()-1; i++){
            stringBuilder.append(list.get(list.size()-i-1));
            stringBuilder.append(", ");
        }
        stringBuilder.append(list.get(0));
        return stringBuilder.toString();

    }
    private static void copyForHistory(List<Integer> array){
        List<Integer> tempArr = new ArrayList<>(array);
        historyOfArrays.add(tempArr);
    }
    public String arrayToString(int[] arr){
        if (arr.length == 0) return "";
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < arr.length-1; i++){
            stringBuilder.append(arr[i] + ", ");
        }
        stringBuilder.append(arr[arr.length-1]);
        return stringBuilder.toString();
    }
    public boolean isNumber(String str){
        boolean flag = false;
        if (str.equals("-0")) return false;
        for (int i = 0; i < str.length(); i++){
            if (i == 0){
                if (str.charAt(i) == '-')
                    continue;
            }
            if (str.charAt(i) >= '0' && str.charAt(i) <= '9'){
                flag = true;
                continue;
            }else{
                return false;
            }
        }
        return flag;
    }
    static void copyToAL(List<Integer> list, int[] arr, int len){
        list.clear();
        for (int i = 0; i < len; i++){
            list.add(arr[i]);
        }
    }

    public int calcWidth(int pos, int count){
        pos++;
        int oldPos = pos;
        int i = 0;
        for (; pos>1; i++){
            pos /=2;
        }
        int thisWidth = WIDTH;

        int oldCount = count;
        int j = 0;
        for (; count > 1; j++){
            count/=2;
        }
        if (j > 4){
            thisWidth *= Math.pow(2, j-4);
        }
//        System.out.println(thisWidth);
//        System.out.println(j);
        return (int) (thisWidth/2/Math.pow(2,i)+thisWidth/Math.pow(2,i)*(oldPos%Math.pow(2,i)) - 20);
    }

    public int calcHeight(int pos){
        pos++;
        int i = 0;
        for (; pos>1; i++){
            pos /=2;
        }
        return 10 + 50*i;
    }

    @FXML
    private Label labelForUser;
    @FXML
    private Label labelArray;
    @FXML
    private Label labelFromFile;
    @FXML
    private AnchorPane graph;
    @FXML
    private TextField textField;
    @FXML
    public void onButtonStepClick() {
        if (type == Type.NOT_SELECTED) return;
        if (arr.size() == 0){
            graph.getChildren().clear();
            createElements(arr, arr.size());
            if (resultArray.size() != 0){
                labelForUser.setText("Массив построен");
                labelForUser.setStyle("-fx-text-fill: black");
            }
            return;
        }
        if (isSorting){
            if (changeElements || arr.size() == 1){
                resultArray.add(heap.heap_sort_start());
                copyToAL(arr, heap.getHeap(), heap.getLast());
                graph.getChildren().clear(); // Удаление всех элементов
                createElements(arr, arr.size()); // Создание новых
                labelForUser.setText("Извлечен максимальный элемент. Сделайте шаг");  // Смена текста
                labelForUser.setStyle("-fx-text-fill: black");                      // Смена цвета текста
                labelArray.setText(makeReverseArray(resultArray));
                changeElements = false;
            }else{
                if (isFirst){
                    next = 0;
                    isFirst = false;
                }
                next = heap.sift_down_step(next);
                if (next == -1){
                    isFirst = true;
                    changeElements = true;
                    graph.getChildren().clear(); // Удаление всех элементов
                    createElements(arr, arr.size()); // Создание новых
                    labelForUser.setStyle("-fx-text-fill: black");
                    if (heap.getState() == Heap.States.COMPARE){
                        graph.getChildren().get(heap.getIndex1()).setStyle("-fx-text-fill: red");
                        ((Label) graph.getChildren().get(heap.getIndex1())).setBorder(getBorder(Color.RED));
                        if (heap.getIndex2() < heap.getLast()){
                            graph.getChildren().get(heap.getIndex2()).setStyle("-fx-text-fill: red");
                            ((Label) graph.getChildren().get(heap.getIndex2())).setBorder(getBorder(Color.RED));
                            labelForUser.setText("Сравниваются 2 элемента:" +heap.getHeap()[heap.getIndex1()] + ", " +heap.getHeap()[heap.getIndex2()] + " .Элементы не поменялись, т.к. "  + heap.getHeap()[heap.getIndex2()] +
                                    " не больше " + heap.getHeap()[heap.getIndex1()] + ". Сделайте шаг");
                        }
                        if (heap.getIndex3() < heap.getLast()){
                            graph.getChildren().get(heap.getIndex3()).setStyle("-fx-text-fill: red");
                            ((Label) graph.getChildren().get(heap.getIndex3())).setBorder(getBorder(Color.RED));
                        }
                    }else{
                        labelForUser.setText("Просеивание вниз завершено. Сделайте шаг.");
                    }
                    return;
                }
                copyToAL(arr, heap.getHeap(), heap.getLast());
                graph.getChildren().clear(); // Удаление всех элементов
                createElements(arr, arr.size()); // Создание новых
                labelForUser.setStyle("-fx-text-fill: black");
                if (heap.getState() == Heap.States.CHANGE){
                    graph.getChildren().get(heap.getIndex1()).setStyle("-fx-text-fill: blue");
                    ((Label) graph.getChildren().get(heap.getIndex1())).setBorder(getBorder(Color.BLUE));
                    if (heap.getIndex2() < heap.getLast()){
                        graph.getChildren().get(heap.getIndex2()).setStyle("-fx-text-fill: blue");
                        ((Label) graph.getChildren().get(heap.getIndex2())).setBorder(getBorder(Color.BLUE));
                        labelForUser.setText("Элементы "  + heap.getHeap()[heap.getIndex1()] + " и " + heap.getHeap()[heap.getIndex2()] + " поменялись местами, т.к. " +  heap.getHeap()[heap.getIndex1()] +
                                " больше " + heap.getHeap()[heap.getIndex2()] + ". Сделайте шаг");
                    }
                    if (heap.getIndex3() < heap.getLast()) {
                        graph.getChildren().get(heap.getIndex3()).setStyle("-fx-text-fill: red");
                        ((Label) graph.getChildren().get(heap.getIndex3())).setBorder(getBorder(Color.RED));
                        labelForUser.setText("Элемент " +heap.getHeap()[heap.getIndex1()] + " сравнивается с элементами " +heap.getHeap()[heap.getIndex2()]  + ", " + heap.getHeap()[heap.getIndex3()]+ ". Элементы "  + heap.getHeap()[heap.getIndex1()] + " и " + heap.getHeap()[heap.getIndex2()] + " поменялись местами, т.к. " +  heap.getHeap()[heap.getIndex1()] +
                                " не меньше " + heap.getHeap()[heap.getIndex3()] + " и " +  heap.getHeap()[heap.getIndex1()] +
                                " больше " + heap.getHeap()[heap.getIndex2()] + ". Сделайте шаг");
                    }
                }
            }
        }else{
            if (!canAdd){
                if (isFirst){
                    next = heap.getLast()-1;
                    isFirst = false;
                }
                next = heap.sift_up_step(next);
                if (next == -1){
                    canAdd = true;
                    isFirst = true;
                    graph.getChildren().clear(); // Удаление всех элементов
                    createElements(arr, arr.size()); // Создание новых
                    labelForUser.setStyle("-fx-text-fill: black");                      // Смена цвета текста
                    if (heap.getState() == Heap.States.DEFAULT){
                        labelForUser.setText("Просеивание вверх окончено. Добавьте новый элемент");
                    }else{
                        if (heap.getState() == Heap.States.COMPARE){
                            graph.getChildren().get(heap.getIndex1()).setStyle("-fx-text-fill: red");
                            ((Label) graph.getChildren().get(heap.getIndex1())).setBorder(getBorder(Color.RED));
                            graph.getChildren().get(heap.getIndex2()).setStyle("-fx-text-fill: red");
                            ((Label) graph.getChildren().get(heap.getIndex2())).setBorder(getBorder(Color.RED));
                        }
                        if (type == Type.FILE && current >= arrayFile.length){
                            labelForUser.setText("Элементы не поменялись, т.к. " + heap.getHeap()[heap.getIndex2()] +
                                    " не больше " + heap.getHeap()[heap.getIndex1()] + ". Просеивание вверх окончено. Файл считан. Добавьте новый элемент вручную или извлеките массив");
                            type = Type.UI;
                        }else{
                            labelForUser.setText("Элементы не поменялись, т.к. " + heap.getHeap()[heap.getIndex2()] +
                                    " не больше " + heap.getHeap()[heap.getIndex1()] + ". Просеивание вверх окончено. Добавьте новый элемент");
                        }
                    }
                    return;
                }
                copyToAL(arr, heap.getHeap(), heap.getLast());
                graph.getChildren().clear(); // Удаление всех элементов
                createElements(arr, arr.size()); // Создание новых
                if (heap.getState() == Heap.States.CHANGE){
                    graph.getChildren().get(heap.getIndex1()).setStyle("-fx-text-fill: blue");
                    ((Label) graph.getChildren().get(heap.getIndex1())).setBorder(getBorder(Color.BLUE));
                    graph.getChildren().get(heap.getIndex2()).setStyle("-fx-text-fill: blue");
                    ((Label) graph.getChildren().get(heap.getIndex2())).setBorder(getBorder(Color.BLUE));
                    labelForUser.setStyle("-fx-text-fill: black");                      // Смена цвета текста
                    labelForUser.setText("Элементы " + heap.getHeap()[heap.getIndex1()] + " и " + heap.getHeap()[heap.getIndex2()] + " поменялись местами, т.к. " + heap.getHeap()[heap.getIndex1()] +
                            " больше " + heap.getHeap()[heap.getIndex2()] +  ". Сделайте шаг");
                }
            }
        }
    }
    @FXML
    public void onButtonAddClick() {
        if (type == Type.NOT_SELECTED) return;
        if (isSorting) return;
        if (canAdd){
            try{
                int value;
                if (type == Type.UI){
                    value = Integer.parseInt(textField.getText());
                }else{
                    if (current < arrayFile.length){
                        value = arrayFile[current];
                        current++;
                    }else{
                        labelForUser.setText("Весь файл считан");
                        labelForUser.setStyle("-fx-text-fill: red");
                        return;
                    }
                }
                if (value >= -2147483647 && value <=2147483647){
                    copyForHistory(arr);
                    arr.add(value);
                    heap.add_no_sift(value);
                    graph.getChildren().clear();
                    createElements(arr, arr.size());
                    labelForUser.setText("Элемент добавлен. Сделайте шаг");
                    labelForUser.setStyle("-fx-text-fill: black");
                    canAdd = false;
                }else{
                    if (textField.getText().equals("")){
                        labelForUser.setStyle("-fx-text-fill: red");
                        labelForUser.setText("Введена пустая строка");
                    }else{
                        labelForUser.setStyle("-fx-text-fill: red");
                        labelForUser.setText("Число по модулю должно быть не больше 2'147'483'647");
                    }
                }
            }catch(Exception exception){
                if (isNumber(textField.getText())) {
                    labelForUser.setStyle("-fx-text-fill: red");
                    labelForUser.setText("Число по модулю должно быть не больше 2'147'483'647");
                } else {
                    labelForUser.setStyle("-fx-text-fill: red");
                    labelForUser.setText("Введено не число (или не целое)");
                }
            }
        }
    }
    public void createElements(List<Integer> list, int size){
        for (int i = 0; i < size; i++) {
            graph.getChildren().add(createElement(list.get(i), i, size));
        }
    }

    public Label createElement(int element, int number, int count){
        Label label = new Label();
        label.setText(String.valueOf(element));
        label.setLayoutX(calcWidth(number, count));
        label.setLayoutY(calcHeight(number));
        label.setPrefWidth(73);
        label.setBorder(getBorder(Color.BLACK));
        label.setAlignment(Pos.CENTER);
        return label;
    }
    public Border getBorder(Color color){
        return new Border(new BorderStroke(color, BorderStrokeStyle.SOLID, new CornerRadii(2), new BorderWidths(2)));
    }

    public void onDeleteLastClick() {
        if (type == Type.NOT_SELECTED) return;

        if (canAdd && !isSorting){
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
                graph.getChildren().clear(); // Удаление всех элементов
                createElements(arr, arr.size()); // Создание новых
            }
        }
    }

    public void onMakeArrayClick() {
        if (type == Type.NOT_SELECTED) return;
        if (canAdd && arr.size() > 0){
            if (!isSorting){
                isSorting = true;
                labelForUser.setStyle("-fx-text-fill: black");
                labelForUser.setText("Начинается построение массива. Сделайте шаг");
                graph.getChildren().clear(); // Удаление всех элементов
                createElements(arr, arr.size()); // Создание новых
            }
        }
    }

    public void onRestartClick() {
        if (type == Type.NOT_SELECTED) return;
        graph.getChildren().clear(); // Удаление всех элементов
        heap = new Heap();
        canAdd = true;
        next = -1;
        arr.clear();
        isFirst = true;
        isSorting = false;
        changeElements = true;
        resultArray = new ArrayList<>();
        labelForUser.setStyle("-fx-text-fill: black");
        labelForUser.setText("Выберите тип ввода");
        labelArray.setText("");
        labelFromFile.setText("");
        type = Type.NOT_SELECTED;
    }

    public void onFileReadClick() {
        if (type == Type.NOT_SELECTED){
            fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll( new FileChooser.ExtensionFilter("Text files", "*.txt"));
            List<File> selectedFiles = fileChooser.showOpenMultipleDialog(null);
            try{
                path = selectedFiles.get(0).getAbsolutePath();
//                System.out.println(selectedFiles.get(0).getAbsolutePath());
                ReadFile readFile = new ReadFile();
                readFile.setFileName(path);
                arrayFile = readFile.makeArray();
                current = 0;
                type = Type.FILE;
                labelForUser.setStyle("-fx-text-fill: black");
                labelForUser.setText("Добавьте новый элемент");
                labelFromFile.setText("Файл: " + arrayToString(arrayFile));
                canAdd = true;
            }catch (IOException|NumberFormatException exception){
                labelForUser.setStyle("-fx-text-fill: red");
                labelForUser.setText("Ошибка чтения файла");
            }catch(Exception exception){
                labelForUser.setStyle("-fx-text-fill: red");
                labelForUser.setText("Файл не выбран");
            }
        }
    }

    public void onInteractiveClick() {
        if (type == Type.NOT_SELECTED){
            type = Type.UI;
            labelForUser.setStyle("-fx-text-fill: black");
            labelForUser.setText("Добавьте новый элемент");
            canAdd = true;
        }
    }

}