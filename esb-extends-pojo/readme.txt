打包方法：
一、开发阶段
1. 设置版本号 
versions:set -DnewVersion=0.0.1-DEV-SNAPSHOT
2. 部署到maven私服，并使用profile
clean deploy
3. 还原版本
versions:revert

二、sit阶段，
1、执行如下命令，版本设置
versions:set -DnewVersion=0.0.1-SNAPSHOT
2、部署私服
clean deploy
3、还原版本
versions:revert


示例:
versions:set -DnewVersion=0.0.1-DEV-SNAPSHOT
clean deploy
versions:revert