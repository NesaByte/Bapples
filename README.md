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
- Added support for --i or --ignore to list urls except ignore url list 

# LICENSE
- [MIT](https://github.com/NesaByte/Bapples/blob/master/LICENSE)
- Icons made by mangsaabguru from www.flaticon.com
