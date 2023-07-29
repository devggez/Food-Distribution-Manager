package com.example.fooddistributionmanager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.net.URL;
import java.util.*;

public class WorkerShowArea implements Initializable{
    List<Food> list ;
    Food food;
    @FXML
    private VBox addCards;
    @FXML
    private BorderPane borderPane;
    public void show(){
        getFoodData();
        Collections.sort(list, new Comparator<Food>() {
            @Override
            public int compare(Food o1, Food o2) {
                return o1.getDivision().compareTo(o2.getDivision());
            }
        });
        Collections.sort(list, new Comparator<Food>() {
            @Override
            public int compare(Food o1, Food o2) {
                if (o1.getYear()<o2.getYear()){
                    if (o1.getMonth()<o2.getMonth()) return 1;
                }
                if (o1.getYear()>o2.getYear()){
                    if (o1.getMonth()>o2.getMonth()) return -1;
                }
                return 0;
            }
        });
        showCards();
    }


    private void showCards() {
        for (Food d: list){
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("WorkerShowCard.fxml"));
                Pane root = fxmlLoader.load();
                addCards.getChildren().add(root);

                WorkerShowCard controller =fxmlLoader.getController();
                controller.setData(d);

            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private void getFoodData() {
        list = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/Data/foods.txt"));
            String line;
            while ((line = reader.readLine()) != null){
                String[] parts = line.split("&%&");
                food = new Food();
                food.setYear(Integer.parseInt(parts[0]));
                food.setMonth(Integer.parseInt(parts[1]));
                food.setDivision(parts[2]);
                food.setRice(Double.parseDouble(parts[3]));
                food.setSoybeanOil(Double.parseDouble(parts[4]));
                food.setOnion(Double.parseDouble(parts[5]));
                food.setPotato(Double.parseDouble(parts[6]));
                list.add(food);
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        show();
    }
}
