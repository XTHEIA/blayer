set PATH_BUILT="C:\library\project\blayer\build\libs\blayer-1.0-all.jar"
:: set DIR_JAVA="C:\src\java\17"
:: set FILE_BUKKIT="paper-1.19-61.jar"
set DIR_SERVER="C:\library\minecraft\server\THEIA"

xcopy %PATH_BUILT% %DIR_SERVER%\plugins /Y && exit