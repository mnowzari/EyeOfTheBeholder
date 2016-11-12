EYE OF THE BEHOLDER
--------------------
Pixel Sorting Application

What is EYE OF THE BEHOLDER?
============================
EYE OF THE BEHOLDER (hereby referred to as EOTB) is a GUI desktop application that allows the user to sort the pixels of a given image for creative purposes.

Pixel sorted images look distorted or corrupt - that's the point! It's a relatively niche form of visual media, but can be very unique in its presentation and can oftentimes be used a tool to supplement larger creative works.

Features
============================
+ User friendly GUI interface
+ Three different pixel sorting modes
+ Selective-area sorting
+ Histogram generation
+ Top three dominant colors of an image
+ Local dominant colors XML database
+ Multithreaded analysis
+ Ability to save processing settings into text file

Technical Bits
============================
EOTB is written entirely in Java and utilizes Java standard libraries for everything.

The program by default maintains a locally-stored XML database. Every time you load a photo into the program, it logs the photo name and saves the top three dominant colors of the photo into the database. This is in preperation of a future feature regarding colors. You can disable this feature in the main menu.

