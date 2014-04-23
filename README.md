MAL Signature Designer
====================

Create MyAnimeList.net signatures dynamically with javascript

-------------------------------------------
This app simply follows a javascript script to load one's Anime list, and allow users to signatures based on what the last 
shows they watched were.

-------------------------------------------
Features include:<br>
-loading local image files, <br>
-loading the thumbnail of an anime in one's list,<br>
-loading system or custom font<br>
-placing custom text, that consisting of the anime name, episodes seen, and completetion status<br>
-rotatable text and images<br>
-and a large list of Filters to alter the look of text and images such as warp,skew,sharpen,opacitize,colorize, and many more

-------------------------------------------
Runnable out of the box with example script, images, and font included

-------------------------------------------
./images is used to hold image files to be placed on your signature<br>
./fonts supports .tff files to affect the look on your text<br>
./cache is required to be intact to load the animelist and cache anime thumbnails to be reused<br>
.sig.ini is the script that controls what is created overall, use your imagination!<br>

-------------------------------------------

#####Example Script:<br>
````javascript
sig.initSignature("Apocist", 600,110); //Grab My Anime List and make a 600 by 110 signature
sig.addBackground("FFFFFF"); //Place a black background(honestly not needed)
var animeImage = sig.loadAnimeThumbnail(1); //Gets the image of the first anime on my list
animeImage = filter.perspective(animeImage, 0, 11, 52, 0, 52, 49, 0, 61); //applys a perspective filter to appear 3d slanted
animeImage = filter.unsharp(animeImage,25); //then applys unsharp filter to unblur
sig.addImage(animeImage, 52, -1); //lastly places the altered thumbnail on the sig
sig.addImage("computergaze.png", 0, 0); //Places my main background image on the sig (it has a hole for the anime image)
sig.addImage("compgazeapocist1.png", 0, 0); //Places another image that has my username
sig.addTitle(1, 382, 107, sig.newFont("lightout.ttf","bold",22,"#3c5356"), "left", 0, false, 35); //Places the first anime's title
sig.addStatus(1, 306, 67, sig.newFont("lightout.ttf","bold",15,"#3c5356"), "center", 28); //Places the first anime's watching status
sig.addEpisodes(1, 295, 82, sig.newFont("lightout.ttf","bold",12,"#DCCBCB"), "center", 28, 2); //Places the first anime's episodes watched
sig.saveSignature("image.png"); //Saves the signature as image.png
sig.uploadToFTP("hikaritemple.com", "/sig/image_apocist.img","******","******"); //Uploads the image to hikaritemple.com with my username/password

````
