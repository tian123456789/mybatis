package com.tgr.springbootmybatis.encode;

public abstract class FFMPEGLocator
{
  protected abstract String getFFMPEGExecutablePath();
  
  FFMPEGExecutor createExecutor()
  {
    return new FFMPEGExecutor(getFFMPEGExecutablePath());
  }
}
