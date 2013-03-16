Asteroids repository for Team4
===================================

"How do I get this to work on ma komputer ?"

=====

1) First make sure you have git installed on your computer. For our fellow Windows users, grab the github tool here (http://windows.github.com/). Do not use the actual GUI app itself, for it's shitty as hell and you'll likely just fuck the repo up, use instead Git Shell, which is basically the git command line utility for Windows:

    - Go to wherever you want to put the project files: "cd /wherever/You/Want/To/Put/The/Files/", you can use "mkdir folderName" to create a folder where you are, and then "cd folderName" to get into it.

    - Then type "git clone https://github.com/winter2013-ecse321-mcgill/team4", that's it you got the latest revision, in the team4 folder. team4 folder is the ROOT of the repository, where this very readme.txt file actually is. Try to find me!


2) Open Eclipse, and switch workspace to the folder "/wherever/You/Put/The/Files/team4/". Now you should have nothing, YEAH!.


2) First we setup JOGL, which is the set of library for drawing things (the openGL bindings), we will use this set of library as an external,separate folder than Asteroids. 

    - So go to File->New->Project select "Java Project" and type "JOGL" as project name, then Finish (say yes if some warning shows up). You should now have a bunch of .zip and .jars in JOGL.

    - Right-click on JOGL project, go to Properties->Java Build Path->Libraries and remove all the "natives" files, you should only have 2 jars left.

    - The for each jar, extends the properties, double click "sources attachment" and select the corresponding src.zip.

    - That's it, JOGL's setup for good in your workspace.


3) Now last step, we Setup the Asteroids project, 

    - So same as JOGL, create a new java project named "Asteroids" (NO TYPO!) 

    - You should now have all the sources hierarchie, with some red cross indicating it won't compile ! We need to specify where JOGL is.

    - Right Click on Asteroids Project go to Properties->Java Build Path->Projects and select JOGL !


4) Launch the main method, and enjoy the little demo written in 10 lines of code ! :).
