# INSTALLATION
Make sure you have java properly installed in your pc

```java -version``` and ```javac -version``` should give you "java version 1.8.0_261"

If not, install java in your system from [jdk8 downloads](https://www.oracle.com/ca-en/java/technologies/javase/javase-jdk8-downloads.html)

After cloning the repository, unzip folder, open cmd prompt, cd into /assets. 

Finally, execution:

```java -jar Bapples.jar <command>```

# COMMANDS to test
| code| description | how to use|
|-----------------------|----------------------------------------------|--|
|--v or --version | to check the Bapple version                  | ```java -jar Bapples.jar --v```|
|--h or --help    | to check the Bapple help                     | ```java -jar Bapples.jar --h``` |
|--200            | to list urls with status code: SUCCESS       | ```java -jar Bapples.jar --200 <url or .html>``` |
|--400 or --404   | to list urls with status code: CLIENT ERRORS | ```java -jar Bapples.jar --404 <url or .html>``` |
|--XXX            | to list urls with status code: UNKNOWNS      | ```java -jar Bapples.jar --310 <url or .html>``` |
|--secure         | to check URLS with http:// if they work with https://| ```java -jar bapples.jar --secure <url or .html>``` |
|--all            | to list urls with all status                   | ```java -jar Bapples.jar --all <url or .html>``` |
|--good           | to list urls with good status code: 200        | ```java -jar Bapples.jar --good <url or .html>``` |
|--bad            | to list urls with bad status code: 404 and 400 | ```java -jar Bapples.jar --bad <url or .html>``` |
|--j or --json    | to output JSON                                 | ```java -jar Bapples.jar --j *.html``` |
|--i or --ignore  | to list urls except ignore url list            | ```java -jar Bapples.jar --ignore ignore-urls.txt testing.html``` | 

# Setting the environment 

## Inline set up
  - [ ] Use command ```Run google-java-formatter.bat``` to auto format the code.
  - [ ] Use command ```Run spotbugs.bat``` to check for bugs.
  
## Integrate into IntelliJ
### Adding plugin: Google Java Format
 - Go to File -> Settings -> Plugins 
 - Type in "google-java-format"
 - Click install and restart IntelliJ
 
 - Highlight and use ```ctrl + alt + l``` to use the new formatter plugin

### Adding plugin: Spot Bugs
 - Go to File -> Settings -> Plugins 
 - Type in "SpotBugs"
 - Click install and restart IntelliJ  
 
 - Right click the project -> SpotBugs