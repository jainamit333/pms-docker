FROM tomcat:8.0.20-jre8
COPY target/pms-0.0.1.war /usr/local/tomcat/webapps/pms-0.0.1.war
