FROM openjdk

#设置时区与语言
ENV TZ=Asia/Shanghai

ENV jarName=springboot-1.0-SNAPSHOP.jar

RUN ln -snf /usr/share/zoneinfo/TZ /etc/localtime && echo TZ /etc/localtime && echo TZ > /etc/timezone


ADD target/$jarName /

WORKDIR /

EXPOSE 8081

ENTRYPOINT java $JAVA_OPTS -jar $jarName