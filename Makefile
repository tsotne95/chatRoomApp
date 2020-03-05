all: build
build:
	javac src/interfaces/*.java src/server/*.java src/client/*.java
clean:
	rm -f src/interfaces/*.class src/server/*.class src/client/*.class
	rm -f backup.txt