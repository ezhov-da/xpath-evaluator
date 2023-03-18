package ru.ezhov.xpath.evaluator.gui.info;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.Objects;

public class InfoWindow {
    private Stage stage = new Stage();
    private TextArea textArea = new TextArea();

    public InfoWindow() {
        stage.setTitle("Справка по XPath");
        textArea.setEditable(false);
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(textArea);
        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/text-xml.png"))));
    }

    public void show(Scene parent) {
        Platform.runLater(() -> {
            InfoDao infoDao = new InfoDao();
            try {
                textArea.setText(infoDao.getInfo());
            } catch (InfoException e) {
                textArea.setText("Не удалось получить справочную информацию");
                e.printStackTrace();
            }
            stage.setWidth(parent.getWidth() - 100);
            stage.setHeight(parent.getHeight() - 100);
            stage.show();
        });

    }
}
