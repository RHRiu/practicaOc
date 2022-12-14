FROM maven:3.8.2-jdk-8

WORKDIR /app
COPY . .

RUN \
    cd $JAVA_HOME/jre/lib/security \
    && keytool -keystore cacerts -storepass changeit -noprompt -trustcacerts -importcert -alias fortiriu -file /app/fortiriu.cer
	
RUN mvn -Duser.home=/var/maven clean install

CMD java -jar target/spring-boot-hibernate-dc-0.0.1-SNAPSHOT.jar