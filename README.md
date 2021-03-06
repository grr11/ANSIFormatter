# ANSIFormatter
ANSIFormatter is a simple tool for formatting terminal output using ANSI escape codes in Java.

### How to Build
Clone the repository and run Ant in the root directory. 
```
git clone https://github.com/grr11/ANSIFormatter/
cd ANSIFormatter
ant
```
### Usage
Formats can be created easily and saved for later use.
```java
    Format titleFormat = new Format()
        .bright()
        .underlined()
        .foreground(Color.BLUE)
        .background(Color.WHITE);

    Format funFormat = new Format()
        .bright()
        .italicised()
        .alternating(Color.YELLOW, Color.CYAN, Color.GREEN);

    System.out.println(titleFormat.apply("This Is A Title"));
    System.out.println(funFormat.apply("This is fun!"));
```
![Alt Text](http://i.imgur.com/NpOisCB.png)
### Running tests
Most terminal emulators only support a subset of the ANSI escape sequences. To see which formats are supported by your system, navigate to the bin folder and run the jar file.
```
cd bin
java -jar ANSIFormatter.jar
```
![Alt Text](http://i.imgur.com/Uf3gNn4.png)

### License

This project is licensed under the MIT License - see the LICENSE.txt file for details.

