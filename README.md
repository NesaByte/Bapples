# BAPPLES version 3 <img src="https://github.com/NesaByte/Bapples/blob/master/assets/apple.png" width="48">
Command line tool that takes in a textfile or url. Find Good, Bad, Unknown links.

# FEATURES
- Reads from local html files and processes URLS https:// or https
- Reads from web link and processes URLS https:// or http://
- After processing URLS, displays URLS and its status code
- Accepts multiple commands (e.g. --v --200 <url>)
- Added --version or --v command to check the current version of downloaded jarfile
- Added --secure command to check whether http:// URLs actually work using https://
- Added --XXX command to display URL with specific status code the user wants(e.g. --404 <url or .html>)
- Add support for timeouts, DNS resolution issues, or other server errors when accessing a bad URL
- Added support for coloured terminal text
- Added support for --all, --good, --bad flags
- Added support for --j or --json  to output JSON


# INSTALLATION
Make sure you have java properly installed in your pc

```java -version``` and ```javac -version``` should give you "java version 1.8.0_261"

If not, install java in your system from [jdk8 downloads](https://www.oracle.com/ca-en/java/technologies/javase/javase-jdk8-downloads.html)

After cloning the repository, unzip folder, open cmd prompt, cd into /assets. 
Finally, execution:

```java -jar Bapples.jar <command>```

# COMMANDS
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



# LICENSE
- [MIT](https://github.com/NesaByte/Bapples/blob/master/LICENSE)
- Icons made by mangsaabguru from www.flaticon.com
