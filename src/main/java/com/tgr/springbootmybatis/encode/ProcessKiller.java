package com.tgr.springbootmybatis.encode;

class ProcessKiller
  extends Thread
{
  private Process process;
  
  public ProcessKiller(Process process)
  {
    this.process = process;
  }
  
  public void run()
  {
    this.process.destroy();
  }
}
