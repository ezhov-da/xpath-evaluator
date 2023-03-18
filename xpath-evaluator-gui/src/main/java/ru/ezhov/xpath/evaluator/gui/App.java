package ru.ezhov.xpath.evaluator.gui;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.*;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import ru.ezhov.xpath.XPathEvaluator;
import ru.ezhov.xpath.XPathEvaluatorException;
import ru.ezhov.xpath.XPathEvaluatorFactory;
import ru.ezhov.xpath.evaluator.gui.info.InfoWindow;

import javax.xml.namespace.QName;
import javax.xml.xpath.XPathConstants;
import java.awt.*;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Logger;

public class App extends Application {
    private static final Logger LOG = Logger.getLogger(App.class.getName());

    @Override
    public void start(Stage primaryStage) {
        BorderPane borderPane = new BorderPane();
        Scene scene = new Scene(borderPane);

        BorderPane borderPaneTop = new BorderPane();
        TextField textFieldExpression = new TextField();
        textFieldExpression.setPromptText("Input XPath...");


        FlowPane stackPaneBottom = new FlowPane();
        stackPaneBottom.setAlignment(Pos.CENTER);

        ToggleGroup toggleGroup = new ToggleGroup();

        RadioButton radioButton1 = new RadioButton("NODESET");
        radioButton1.setSelected(true);
        radioButton1.setToggleGroup(toggleGroup);
        radioButton1.setUserData(XPathConstants.NODESET);
        RadioButton radioButton2 = new RadioButton("NODE");
        radioButton2.setToggleGroup(toggleGroup);
        radioButton2.setUserData(XPathConstants.NODE);
        RadioButton radioButton3 = new RadioButton("STRING");
        radioButton3.setToggleGroup(toggleGroup);
        radioButton3.setUserData(XPathConstants.STRING);
        RadioButton radioButton4 = new RadioButton("NUMBER");
        radioButton4.setToggleGroup(toggleGroup);
        radioButton4.setUserData(XPathConstants.NUMBER);
        RadioButton radioButton5 = new RadioButton("BOOLEAN");
        radioButton5.setToggleGroup(toggleGroup);
        radioButton5.setUserData(XPathConstants.BOOLEAN);

        stackPaneBottom.getChildren().addAll(
                radioButton1,
                radioButton2,
                radioButton3,
                radioButton4,
                radioButton5
        );

        borderPaneTop.setCenter(textFieldExpression);
        Button button = new Button("Показать справку по XPath");
        button.setOnAction(new EventHandler<ActionEvent>() {
            private InfoWindow infoWindow = new InfoWindow();

            @Override
            public void handle(ActionEvent event) {
                infoWindow.show(scene);
            }
        });
        borderPaneTop.setRight(button);
        borderPaneTop.setBottom(stackPaneBottom);

        borderPane.setTop(borderPaneTop);

        SplitPane splitPane = new SplitPane();
        TextArea textAreaSource = new TextArea();
        textAreaSource.setPromptText("Source XML");
        textAreaSource.setText(
                "<root>\n" +
                        "    <a val=\"1\" id=\"45\">\n" +
                        "        <b val=\"10\">\n" +
                        "            <c>Вот такие пироги</c>\n" +
                        "            <c>Или не такие</c>\n" +
                        "        </b>\n" +
                        "        <b val=\"100\">\n" +
                        "            <c>Тестовый текст</c>\n" +
                        "            <c>Или просто так</c>\n" +
                        "\t</b>\n" +
                        "    </a>\n" +
                        "    <a val=\"2\" id=\"20\">\n" +
                        "        <b val=\"20\">text</b>\n" +
                        "        <b val=\"200\">\n" +
                        "            <c>Вот такие огурцы</c>\n" +
                        "            <c>Или не такие огурцы</c>\n" +
                        "\t</b>\n" +
                        "    </a>\n" +
                        "</root>");


        TextArea textAreaResult = new TextArea();
        textAreaResult.setPromptText("Result");
        splitPane.getItems().addAll(textAreaSource, textAreaResult);

        TextArea textAreaError = new TextArea();
        textAreaError.setPromptText("Errors");

        SplitPane splitPaneVertical = new SplitPane();
        splitPaneVertical.setOrientation(Orientation.VERTICAL);
        splitPaneVertical.setDividerPositions(0.9, 0.1);
        splitPaneVertical.getItems().addAll(splitPane, textAreaError);

        borderPane.setCenter(splitPaneVertical);


        Runnable runnable = () -> {
            Toggle toggle = toggleGroup.getSelectedToggle();
            RadioButton chk = (RadioButton) toggle.getToggleGroup().getSelectedToggle();
            QName qName = (QName) chk.getUserData();

            String xpathExpression = textFieldExpression.getText();
            String xml = textAreaSource.getText();
            if ("".equals(xpathExpression) && "".equals(xml)) {
                textAreaError.setText("Необходимо указать XPath и XML");
                return;
            } else if ("".equals(xpathExpression)) {
                textAreaError.setText("Необходимо указать XPath");
                return;
            } else if ("".equals(xml)) {
                textAreaError.setText("Необходимо указать XML");
                return;
            }

            XPathEvaluator xPathEvaluator = XPathEvaluatorFactory.newInstance(qName);

            try {
                textAreaResult.setText(xPathEvaluator.evaluate(xpathExpression, xml));
            } catch (XPathEvaluatorException e) {
                StringWriter stringWriter = new StringWriter();
                e.printStackTrace(new PrintWriter(stringWriter));
                textAreaError.setText(stringWriter.toString());
            }
        };
        toggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> runnable.run());
        textFieldExpression.setOnKeyReleased(event -> runnable.run());
        primaryStage.setScene(scene);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        primaryStage.setWidth(dimension.getWidth() - 200);
        primaryStage.setHeight(dimension.getHeight() - 100);
        primaryStage.setTitle("XPath evaluator");
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/text-xml.png")));
        primaryStage.show();
    }
}
