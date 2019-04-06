FROM fabric8/java-jboss-openjdk8-jdk

#设置时区与语言
ENV TZ=Asia/Shanghai

ENV jarName=springboot.jar

ADD target/$jarName /

WORKDIR /

EXPOSE 8081

ENTRYPOINT java $JAVA_OPTS -jar $jarName