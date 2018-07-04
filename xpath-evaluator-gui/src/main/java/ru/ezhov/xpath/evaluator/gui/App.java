package ru.ezhov.xpath.evaluator.gui;


import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.namespace.QName;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.logging.Logger;

public class App extends Application {
	private static final Logger LOG = Logger.getLogger(App.class.getName());

	@Override
	public void start(Stage primaryStage) throws Exception {
		BorderPane borderPane = new BorderPane();

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
		splitPaneVertical.setDividerPositions(0.8, 0.2);
		splitPaneVertical.getItems().addAll(splitPane, textAreaError);

		borderPane.setCenter(splitPaneVertical);


		Scene scene = new Scene(borderPane);

		Runnable runnable = () -> {
			Toggle toggle = toggleGroup.getSelectedToggle();
			RadioButton chk = (RadioButton) toggle.getToggleGroup().getSelectedToggle();
			QName qName = (QName) chk.getUserData();

			String textExpression = textFieldExpression.getText();
			String xml = textAreaSource.getText();

			XPathFactory xPathFactory = XPathFactory.newInstance();
			XPath xPath = xPathFactory.newXPath();
			String result = "Ooops error";
			switch (chk.getText()) {
				case "NODESET":
					try {
						XPathExpression compile = xPath.compile(textExpression);
						NodeList nodeList = (NodeList)
							compile.evaluate(new InputSource(new StringReader(xml)), qName);
						if (nodeList != null) {
							StringBuilder stringBuilder = new StringBuilder();
							for (int i = 0; i < nodeList.getLength(); i++) {
								result = nodeToString(nodeList.item(i));
								stringBuilder.append(result + "\n");
							}
							result = stringBuilder.toString();
							textAreaResult.setText("All good");
						}
					} catch (Exception e) {
						StringWriter stringWriter = new StringWriter();
						e.printStackTrace(new PrintWriter(stringWriter));
						textAreaError.setText(stringWriter.toString());
					}
					break;
				case "NODE":
					try {
						XPathExpression compile = xPath.compile(textExpression);
						Node node = (Node)
							compile.evaluate(new InputSource(new StringReader(xml)), qName);
						if (node != null) {
							result = nodeToString(node);
							textAreaResult.setText("All good");
						}
					} catch (Exception e) {
						StringWriter stringWriter = new StringWriter();
						e.printStackTrace(new PrintWriter(stringWriter));
						textAreaError.setText(stringWriter.toString());
					}
					break;
				case "STRING":
					try {
						XPathExpression compile = xPath.compile(textExpression);
						String s =
							compile.evaluate(new InputSource(new StringReader(xml)), qName).toString();
						result = s;
						textAreaResult.setText("All good");
					} catch (Exception e) {
						StringWriter stringWriter = new StringWriter();
						e.printStackTrace(new PrintWriter(stringWriter));
						textAreaError.setText(stringWriter.toString());
					}
					break;
				case "NUMBER":
					try {
						XPathExpression compile = xPath.compile(textExpression);
						String s =
							compile.evaluate(new InputSource(new StringReader(xml)), qName).toString();
						result = s;
						textAreaResult.setText("All good");
					} catch (Exception e) {
						StringWriter stringWriter = new StringWriter();
						e.printStackTrace(new PrintWriter(stringWriter));
						textAreaError.setText(stringWriter.toString());
					}
					break;
				case "BOOLEAN":
					try {
						XPathExpression compile = xPath.compile(textExpression);
						boolean b = Boolean.valueOf(
							compile.evaluate(new InputSource(new StringReader(xml)), qName).toString()
						);
						result = String.valueOf(b);
						textAreaResult.setText("All good");
					} catch (Exception e) {
						StringWriter stringWriter = new StringWriter();
						e.printStackTrace(new PrintWriter(stringWriter));
						textAreaError.setText(stringWriter.toString());
					}
					break;
			}

			textAreaResult.setText(result);
		};
		toggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> runnable.run());
		textFieldExpression.setOnKeyReleased(event -> runnable.run());
		primaryStage.setScene(scene);
		primaryStage.setWidth(800);
		primaryStage.setHeight(650);
		primaryStage.setTitle("XPath evaluator");
		primaryStage.show();
	}

	private String nodeToString(Node node) throws Exception {
		StringWriter sw = new StringWriter();

		Transformer t = TransformerFactory.newInstance().newTransformer();
		t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		t.setOutputProperty(OutputKeys.INDENT, "yes");
		t.transform(new DOMSource(node), new StreamResult(sw));

		return sw.toString();
	}
}
