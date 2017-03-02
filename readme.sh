Key can be :
1. Label 
2. Image_Number
3. Match_Confidence

Values can be  : 

1. For Label - Passport , IDDocument
2. Image_Number - just a number
3. Match_Confidence - double value

Generate a highest ranking record, by the definition :

1. Passport with highest confidence will be given the first priority
2. If there is no label as passport , then I will get the label IDDocument with the highest score.

If we find 2 records with same label and same score, return the first one.

eg:

passport , 0 , 90.00
iddocument , 2 , 99
passport , 1 , 80.00
iddocument , 3 , 99.00
passport , 4 , 93.90

In this case the record output is  : passport , 4 , 93.90