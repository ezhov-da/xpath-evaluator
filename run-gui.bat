@echo off
cd /d %~dp0 
start "run" "%JAVA_HOME%\bin\javaw" -cp xpath-evaluator-gui/target/xpath-evaluator-gui.jar;xpath-evaluator-core\target\xpath-evaluator-core.jar -Xmx768m  ru.ezhov.xpath.evaluator.gui.App
