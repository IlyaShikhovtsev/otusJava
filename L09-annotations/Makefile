clear:
	mvn clean

delombok: clear
	mkdir -p ./target/generated-sources/delombok ./target/generated-test-sources/delombok
	ln -s ./java ./src/main/lombok
	ln -s ./java ./src/test/lombok
	java -cp `mvn dependency:build-classpath | grep -A1 'Dependencies classpath' | tail -1` -jar ~/.m2/repository/org/projectlombok/lombok/1.18.10/lombok-1.18.10.jar delombok ./src/main/lombok -d ./target/generated-sources/delomboked
	java -cp `mvn dependency:build-classpath | grep -A1 'Dependencies classpath' | tail -1` -jar ~/.m2/repository/org/projectlombok/lombok/1.18.10/lombok-1.18.10.jar delombok ./src/test/lombok -d ./target/generated-sources/test-delomboked
	rm ./src/main/lombok ./src/test/lombok

clearlombok:
	rm -rf ./target/generated-sources/delomboked
	rm -rf ./target/generated-sources/test-delomboked
