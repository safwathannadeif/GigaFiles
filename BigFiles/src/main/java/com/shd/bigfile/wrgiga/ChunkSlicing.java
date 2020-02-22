package com.shd.bigfile.wrgiga;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

//RS  Unicode Character 'SYMBOL FOR RECORD SEPARATOR' (U+241E) == "\u241E"
////  Or Int=30 As char  30	001e 		036	0x1E	0x1E		RS
public class ChunkSlicing {
//char crsu =	0x1E;
//private	static final char RsAsInt = (char)30 ;  //Record Separator
private int size ;  							//Array Total Size as Chunk 
private	int istart= 0 ; 						//Start index for Each Slice
private	int length ;							//Length offset from start index for Each Slice
private int unit  ;								//Size/Length for Each Slice
private Boolean more = false ;
public ChunkSlicing(int sizei , int uniti )
{
	this.size=sizei ;
	this.unit = uniti ;
}
public int getSize() {
	return size;
}
public int getIstart() {
	return istart;
}
public int getLength() {
	return length;
}
public int getUnit() {
	return unit;
}
public void oneSclice()
{
	more = true ;
	if (length == size ) {
		more = false ;
		return ;
	}
	istart= length ;
	length = length + unit ;
	if (istart +length >= size) length=size ;
	return ;
}
public Boolean isMore() {
	return more;
}
public static ByteBuffer strToByteBufferUTF8(String msg)  {
    return ByteBuffer.wrap(msg.getBytes(StandardCharsets.UTF_8));
}
public static ByteBuffer lisOfStrToByteBufferUTF8(List<String> lisIn)  {
	String allMgs= lisIn.stream().map(Object::toString).collect(Collectors.joining("")) ;
    return ByteBuffer.wrap(allMgs.getBytes(StandardCharsets.UTF_8));
}
public static String byteBufferToStrUFT8(ByteBuffer buffer) {
    byte[] bytes;
    if(buffer.hasArray()) {
        bytes = buffer.array();
    } else {
        bytes = new byte[buffer.remaining()];
        buffer.get(bytes);
    }
    return new String(bytes, StandardCharsets.UTF_8);
}

public static ByteBuffer str_to_bb(String msg, Charset charset){
    return ByteBuffer.wrap(msg.getBytes(charset));
}

public static String bb_to_str(ByteBuffer buffer, Charset charset){
    byte[] bytes;
    if(buffer.hasArray()) {
        bytes = buffer.array();
    } else {
        bytes = new byte[buffer.remaining()];
        buffer.get(bytes);
    }
    return new String(bytes, charset);
}

}
