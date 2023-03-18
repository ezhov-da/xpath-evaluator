@echo off
cd /d %~dp0 
start "run" "%JAVA_HOME%\bin\javaw" -cp xpath-evaluator-gui/target/xpath-evaluator-gui.jar;xpath-evaluator-core\target\xpath-evaluator-core.jar --module-path "{MODULE_PATH}\javafx-sdk-19.0.2.1\lib" --add-modules javafx.controls,javafx.fxml -Xmx768m  ru.ezhov.xpath.evaluator.gui.App
