# Unicode [Binary] Dictionary Compressor
UDTC is a Lossless data compression algorithm that compresses data by turning
bytes of the text into variable-width bytes.
Each new variable-width byte will be stored in a "dictionary" structure inside
the file.

## How it works
<pre>
dict| byte
----|---------
 00 | 01100101
 01 | 01110011
 10 | 01110100

Turns: 01110100 01100101 01110011 01110100
Into : dict + 10 00 01 10
</pre>

The more distinct bytes, the greater the result. <br>
The more bytes overall, the smaller the result. <br>

## Example
```java
String str = "huuuuuuuuge texts is better at times";
String cstr = Compressor.toUDTC(str); // compresses str
str = Compressor.bin(str); // returns str in binary

System.out.println("DEFAULT: " + str); // 36bytes
System.out.println("UDTC   : " + cstr); // 32bytes
```

## Todo
- [X] Make a ```toUDTC()``` method
- [ ] Make a ```fromUDTC()``` method
- [ ] Make an ```isCompressed()``` method
- [ ] Add file supporting
- [ ] Mix UDTC with redundant information removal (nnnn -> 4n)
- [ ] Phasing support (try to compress twice)
- [ ] Fix data overflow
