# FBF-algorithm

DetectMain.java is the main program of FBF algorithm. The implementation classes are all in the utils package.
Gogo.java is used to calculate the result. It compared the result image which calculated by detectmain.java with that in groundtruth, and calculated the metrics of CDNet2014.
Main.java is used to integrate all the txt results into the CSV, which is more convenient for comparison.
Other files are not important, they are mostly used to generate a single TXT file or CSV file.
