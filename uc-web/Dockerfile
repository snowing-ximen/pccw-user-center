FROM ascdc/jdk8:latest

ARG APP_NAME
ARG SUB_APP_NAME
ARG JAR_NAME
ARG JAVA_PARAM
ARG SPRING_PROFILE

RUN cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo "Asia/Shanghai" > /etc/timezone

RUN mkdir -p /application/$APP_NAME/logs

COPY $APP_NAME/$SUB_APP_NAME/target/$JAR_NAME  /application/$APP_NAME/

RUN chmod 755 /application/$APP_NAME/$JAR_NAME

WORKDIR  /application/$APP_NAME/

ENTRYPOINT exec java $JAVA_PARAM -XX:-HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=./logs -Dspring.profiles.active=$SPRING_PROFILE -jar  /application/$APP_NAME/$JAR_NAME