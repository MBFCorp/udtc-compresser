# Unicode [Binary] Dictionary Compressor
UDTC is a Lossless data compression algorithm that compresses data by turning
bytes of the text into variable-width bytes.
Each new variable-width byte will be stored in a "dictionary" structure inside
the file.

<pre>
dict| byte
----|---------
 00 | 01100101
 01 | 01110011
 10 | 01110100

Turns: 01110100 01100101 01110011 01110100
Into : dict + 10 00 01 10

(Works better for huge texts)
</pre>
