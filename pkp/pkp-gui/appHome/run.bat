cd C:\depponApp\appHome
@echo off
rem echo %CLASSPATH%
rem ���ñ����ӳ�
SetLocal EnableDelayedExpansion
set Path=C:\depponApp\appHome;%Path%
set CLASSPATH=C:\depponApp\appHome;%CLASSPATH%
rem 
FOR %%i IN ("C:\depponApp\appHome\lib\*.jar") DO SET CLASSPATH=!CLASSPATH!;%%~fsi
rem ��ʾ���ú��CLASSPATHֵ
rem echo %CLASSPATH%

java.exe -Duser.dir=/C:/depponApp/appHome -Djava.library.path=/C:/depponApp/appHome/lib/win32 org.java.plugin.boot.Boot

rem ��ԭϵͳ��������
EndLocal
pause



