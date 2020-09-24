# BAPPLES 
Command line tool that takes in a textfile or url. Find Good, Bad, Unknown links.

# FEATURES
- Reads from local html files and processes URLS https:// or https
- Reads from web link and processes URLS https:// or http://
- After processing URLS, displays URLS and its status code
- Accepts multiple commands (e.g. --v --200 <url>)
- Added --version or -v command to check the current version of downloaded jarfile
- Added --secure command to check whether http:// URLs actually work using https://
- Added --XXX command to display URL with specific status code the user wants(e.g. --404 <url or .html>)
- Add support for timeouts, DNS resolution issues, or other server errors when accessing a bad URL


# USAGE
After cloning the repository, unzip folder, open cmd prompt, cd into /assets. Finally, execute:

```java -jar bapples.jar <command>```

# HELP
| code| description | how to use|
|-----------------------|----------------------------------------------|--|
|--v or --version | to check the Bapple version                  | java -jar bapples.jar --v|
|--h or --help    | to check the Bapple help                     |java -jar bapples.jar --h |
|--200            | to list urls with status code: SUCCESS       |java -jar bapples.jar --200 <url or .html>|
|-400 or --404    | to list urls with status code: CLIENT ERRORS |java -jar bapples.jar --404 <url or .html>|
|--XXX            | to list urls with status code: UNKNOWNS      |java -jar bapples.jar --310 <url or .html>|



# LICENSE
