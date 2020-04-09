FROM maven:3.6.1-jdk-11 as build
ADD . ~/source
WORKDIR ~/source
RUN mvn clean install

FROM openjdk:11
COPY --from=build ~/source/target/azure-properties*.jar /azure-properties.jar
ENV JAVA_OPTS=""
CMD java ${JAVA_OPTS} -jar /azure-properties.jar
EXPOSE 8887
