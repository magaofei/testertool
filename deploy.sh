path=/data/testertool

cd ${path}
mvn clean package
mv target/testertool*.jar target/testertool.jar
service testertool restart
