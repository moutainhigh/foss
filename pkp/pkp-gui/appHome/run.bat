cd C:\depponApp\appHome
@echo off
rem echo %CLASSPATH%
rem 启用变量延迟
SetLocal EnableDelayedExpansion
set Path=C:\depponApp\appHome;%Path%
set CLASSPATH=C:\depponApp\appHome;%CLASSPATH%
rem 
FOR %%i IN ("C:\depponApp\appHome\lib\*.jar") DO SET CLASSPATH=!CLASSPATH!;%%~fsi
rem 显示设置后的CLASSPATH值
rem echo %CLASSPATH%

java.exe -Duser.dir=/C:/depponApp/appHome -Djava.library.path=/C:/depponApp/appHome/lib/win32 org.java.plugin.boot.Boot

rem 还原系统环境设置
EndLocal
pause



