javac -sourcepath ./ *.java
jar -cfm Risk.jar MANIFEST.MF gridworld.jar *.class resources\*.class resources\*.gif resources\*.png info\gridworld\actor\*.class info\gridworld\actor\*.gif info\gridworld\grid\*.class info\gridworld\grid\*.gif info\gridworld\gui\*.class info\gridworld\gui\*.gif info\gridworld\gui\*.properties info\gridworld\gui\*.html info\gridworld\world\*.class   