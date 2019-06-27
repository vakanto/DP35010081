FROM openjdk:8-jre-alpine
COPY dp_ha1.jar /warehouse/warehouse.jar
WORKDIR /warehouse
EXPOSE 5002
RUN chmod +x warehouse.jar
CMD ["java","-jar","warehouse.jar","warehouse"]