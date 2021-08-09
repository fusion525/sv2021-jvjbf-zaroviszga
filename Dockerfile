FROM adoptopenjdk:16-jre-hotspot
RUN mkdir /opt/app
COPY target/sv2021-jvjbf-zaroviszga-0.0.1-SNAPSHOT.jar /opt/app
CMD ["java", "-jar", "/opt/app/sv2021-jvjbf-zaroviszga.jar"]