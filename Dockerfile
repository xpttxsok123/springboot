FROM fabric8/java-jboss-openjdk8-jdk

#设置时区与语言
ENV TZ=Asia/Shanghai

ENV jarName=springboot-1.0-SNAPSHOT.jar

ADD target/$jarName /

WORKDIR /

EXPOSE 8081

ENTRYPOINT java $JAVA_OPTS -jar $jarName
