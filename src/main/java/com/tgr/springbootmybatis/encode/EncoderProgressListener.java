package com.tgr.springbootmybatis.encode;

public abstract interface EncoderProgressListener
{
  public abstract void sourceInfo(MultimediaInfo paramMultimediaInfo);
  
  public abstract void progress(int paramInt);
  
  public abstract void message(String paramString);
}
