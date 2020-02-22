package com.shd.bigfile.test.generators;

import java.util.Random;

public class RecordGenerator {


public static String generate(int tStrLen ) {
    int leftLimit = 48; // numeral '0'
    int rightLimit = 122; // letter 'z'
    //tStrLen = 220;
    Random random = new Random();
 
    String generatedString = random.ints(leftLimit, rightLimit + 1)
      .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
      .limit(tStrLen)
      //.map( i -> String.valueOf(Character.toChars(i) ))
      //.collect(Collectors.joining(String::new,"\n")) ;
      .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString() ;
      	  
   
    return generatedString ; //With the First CodePoint
   // return (GenUtil.getRandomUUID()+GenUtil.getRandomUUID()) ;
}
}