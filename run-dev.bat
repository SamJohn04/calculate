@echo off
setlocal enabledelayedexpansion

@REM root directory where you have the sub-projects
set "root_directory=%~dp0"
echo "%root_directory%"
@REM Specify the subfolders and their respective commands

set subprojects="user-auth;gradlew :bootrun" "store-result-app;gradlew :bootrun" "calculator-app;gradlew :bootrun" "calculate-web-app;ng serve"
echo "%subprojects%"
@REM Loop through the subfolders and execute the respective commands
(for %%a in (%subprojects%) do ( 
   for /f "tokens=1,2 delims=;" %%A in (%%a) Do (
    set "project=%%A"
    set "command=%%B"
    echo "%root_directory%!project!"
    echo Running !command! in folder !project!
    pushd "%root_directory%!project!"
    start "!project!" cmd /k !command!
    popd
   )
))
endlocal