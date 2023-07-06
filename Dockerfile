FROM eclipse-temurin:17-jre-alpine

EXPOSE 9090
ENV db_hostname dcsa_db
COPY run-in-container.sh /run.sh
RUN chmod +x /run.sh
COPY surrender-application/src/main/resources/application.yml .
COPY surrender-application/target/dcsa-surrender-application.jar .
CMD ["/run.sh"]
