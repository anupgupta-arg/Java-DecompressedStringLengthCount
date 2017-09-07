/*

You are to implement a decompression algorithm given a compression algorithm.
The compression algorithm takes a sequence of characters (whitespace is ignored). To indicate that some sequence should be repeated, a marker is added to the file, like (5x3). To decompress this marker, take the subsequent 5 characters (which may include characters from another marker as well - see below) and repeat them 3 times. For example:

- HELLO becomes HELLO as there are no markers in this input.
- (3x3)ADF becomes ADFADFADF.
- S(7x2)(2x2)THCF becomes S(2x2)TH(2x2)THCF which then becomes STHTHTHTHCF.
- (25x15)(15x10)(12x11)(10x12)(1x10)B decompresses into a string with length 48.
- (20x3)(1x4)BHCG(3x5)TT(5x2)HGDTK(10x2)(2x1)HELLO(4x6)EIGHT becomes 101 characters long.

Note that in the case of two or more markers being next to each other like (20x3)(1x4), we evaluate the left most one first

What is the decompressed length of the file using this format? The file is input.


S(7x2)(2x2)THCF, you have two markers, (7x2) and (2x2). Since (7x2) is the first marker from the left, 
you start by expanding the string by that. So you take the next 7 characters and put it twice in the string.
 The next 7 characters after (7x2) are (2x2)TH. You take this and put it twice which gives you - S(2x2)TH(2x2)THCF.
  The new string has markers (2x2) and (2x2). You take the left most again and expand the sting accordingly. 
  This gives you STHTH(2x2)THCF. This new string has one marker (2x2) and following the same procedure,
   we get: STHTHTHTHCF. Now there are no more markers and this STHTHTHTHCF is the final answer.

My output string length is : 10943094568


*/

 
import java.io.IOException;
import java.util.Scanner;
 
 
public class DecompressedStringLengthCount {
   
    public static long decompressString(String inputString) {
        long lengthOfDecompressedinputString = 0;
        int lengthOfString = 0;
        int numberOfRepeatsString = 0;
        int startingIndex;
 
        for (int index = 0; index < inputString.length(); index++) {
            char processedChar = inputString.charAt(index);
            if (processedChar == '(') {
                startingIndex = index + 1;
                lengthOfString = countingNumber(inputString.substring(startingIndex), 'x');
               
                index += Integer.toString(lengthOfString).length();
            } else if (processedChar == 'x') {
                startingIndex = index + 1;
                
                numberOfRepeatsString = countingNumber(inputString.substring(startingIndex), ')');
               
                index += Integer.toString(numberOfRepeatsString).length();
            } else if (processedChar == ')') {
                startingIndex = index + 1;
                lengthOfDecompressedinputString = lengthOfDecompressedinputString + decompressString(inputString.substring(startingIndex, startingIndex + lengthOfString)) * numberOfRepeatsString;
              
                index += lengthOfString;
            } else {
                lengthOfDecompressedinputString++;
            }
        }
        return lengthOfDecompressedinputString;
    }
 
    public static int countingNumber(String stringCount, char bound) {
        String s = "";
        for (int i = 0; i < stringCount.length(); i++) {
            if (stringCount.charAt(i) == bound) {
                s = stringCount.substring(0, i);
                break;
            }
        }
        return Integer.parseInt(s);
    }
 
    public static void main(String[] args) throws IOException {
 
       
       // String input = "(27x12)(20x12)(13x14)(7x10)(1x12)A";
        String inputString;
        Scanner s = new Scanner(System.in);
        inputString = s.nextLine();
        
        long result = decompressString(inputString);
 
        System.out.println("Decompressed String length : " + result);
    }
}