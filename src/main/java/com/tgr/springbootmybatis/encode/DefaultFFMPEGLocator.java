package com.tgr.springbootmybatis.encode;

import java.io.File;
import java.io.IOException;

public class DefaultFFMPEGLocator
  extends FFMPEGLocator
{
  private static final int myEXEversion = 1;
  private String path;
  
  public DefaultFFMPEGLocator()
  {
    String os = System.getProperty("os.name").toLowerCase();
    boolean isWindows;
    if (os.indexOf("windows") != -1) {
      isWindows = true;
    } else {
      isWindows = false;
    }
    File temp = new File(System.getProperty("java.io.tmpdir"), "jave-1");
    if (!temp.exists())
    {
      temp.mkdirs();
      temp.deleteOnExit();
    }
    String suffix = isWindows ? ".exe" : "";
    File exe = new File(temp, "ffmpeg" + suffix);
    if (!exe.exists()) {
      copyFile("ffmpeg" + suffix, exe);
    }
    if (isWindows)
    {
      File dll = new File(temp, "pthreadGC2.dll");
      if (!dll.exists()) {
        copyFile("pthreadGC2.dll", dll);
      }
    }
    if (!isWindows)
    {
      Runtime runtime = Runtime.getRuntime();
      try
      {
        runtime.exec(new String[] { "/bin/chmod", "755", 
          exe.getAbsolutePath() });
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
    }
    this.path = exe.getAbsolutePath();
  }
  
  protected String getFFMPEGExecutablePath()
  {
    return this.path;
  }
  
  /* Error */
  private void copyFile(String path, File dest)
    throws java.lang.RuntimeException
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_3
    //   2: aconst_null
    //   3: astore 4
    //   5: aload_0
    //   6: invokevirtual 126	java/lang/Object:getClass	()Ljava/lang/Class;
    //   9: aload_1
    //   10: invokevirtual 132	java/lang/Class:getResourceAsStream	(Ljava/lang/String;)Ljava/io/InputStream;
    //   13: astore_3
    //   14: new 138	java/io/FileOutputStream
    //   17: dup
    //   18: aload_2
    //   19: invokespecial 140	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   22: astore 4
    //   24: sipush 1024
    //   27: newarray byte
    //   29: astore 5
    //   31: goto +13 -> 44
    //   34: aload 4
    //   36: aload 5
    //   38: iconst_0
    //   39: iload 6
    //   41: invokevirtual 143	java/io/OutputStream:write	([BII)V
    //   44: aload_3
    //   45: aload 5
    //   47: invokevirtual 149	java/io/InputStream:read	([B)I
    //   50: dup
    //   51: istore 6
    //   53: iconst_m1
    //   54: if_icmpne -20 -> 34
    //   57: goto +72 -> 129
    //   60: astore 5
    //   62: new 124	java/lang/RuntimeException
    //   65: dup
    //   66: new 59	java/lang/StringBuffer
    //   69: dup
    //   70: ldc 155
    //   72: invokespecial 63	java/lang/StringBuffer:<init>	(Ljava/lang/String;)V
    //   75: aload_2
    //   76: invokevirtual 92	java/io/File:getAbsolutePath	()Ljava/lang/String;
    //   79: invokevirtual 66	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   82: invokevirtual 70	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   85: invokespecial 157	java/lang/RuntimeException:<init>	(Ljava/lang/String;)V
    //   88: athrow
    //   89: astore 8
    //   91: jsr +6 -> 97
    //   94: aload 8
    //   96: athrow
    //   97: astore 7
    //   99: aload 4
    //   101: ifnull +13 -> 114
    //   104: aload 4
    //   106: invokevirtual 158	java/io/OutputStream:close	()V
    //   109: goto +5 -> 114
    //   112: astore 9
    //   114: aload_3
    //   115: ifnull +12 -> 127
    //   118: aload_3
    //   119: invokevirtual 161	java/io/InputStream:close	()V
    //   122: goto +5 -> 127
    //   125: astore 9
    //   127: ret 7
    //   129: jsr -32 -> 97
    //   132: return
    // Line number table:
    //   Java source line #110	-> byte code offset #0
    //   Java source line #111	-> byte code offset #2
    //   Java source line #113	-> byte code offset #5
    //   Java source line #114	-> byte code offset #14
    //   Java source line #115	-> byte code offset #24
    //   Java source line #117	-> byte code offset #31
    //   Java source line #118	-> byte code offset #34
    //   Java source line #117	-> byte code offset #44
    //   Java source line #120	-> byte code offset #60
    //   Java source line #121	-> byte code offset #62
    //   Java source line #122	-> byte code offset #75
    //   Java source line #121	-> byte code offset #85
    //   Java source line #123	-> byte code offset #89
    //   Java source line #138	-> byte code offset #94
    //   Java source line #123	-> byte code offset #97
    //   Java source line #124	-> byte code offset #99
    //   Java source line #126	-> byte code offset #104
    //   Java source line #127	-> byte code offset #112
    //   Java source line #131	-> byte code offset #114
    //   Java source line #133	-> byte code offset #118
    //   Java source line #134	-> byte code offset #125
    //   Java source line #138	-> byte code offset #127
    //   Java source line #139	-> byte code offset #132
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	133	0	this	DefaultFFMPEGLocator
    //   0	133	1	path	String
    //   0	133	2	dest	File
    //   1	118	3	input	java.io.InputStream
    //   3	102	4	output	java.io.OutputStream
    //   29	17	5	buffer	byte[]
    //   60	3	5	e	IOException
    //   34	6	6	l	int
    //   51	3	6	l	int
    //   97	1	7	localObject1	java.lang.Object
    //   89	6	8	localObject2	java.lang.Object
    //   112	1	9	localThrowable	java.lang.Throwable
    //   125	1	9	localThrowable1	java.lang.Throwable
    // Exception table:
    //   from	to	target	type
    //   5	57	60	java/io/IOException
    //   5	89	89	finally
    //   129	132	89	finally
    //   104	109	112	java/lang/Throwable
    //   118	122	125	java/lang/Throwable
  }
}
