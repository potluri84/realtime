FROM openjdk
ADD /target/kafkaproducer-0.0.1-SNAPSHOT.jar kp.jar
ENTRYPOINT ["java","-jar","/kp.jar"]