package com.vrplumber.pycraft.bukkitserver;

import java.math.BigInteger;

public interface IPycraftAPI {
  public void setWanted(boolean wanted);

  public PycraftServerPlugin getPlugin();

  public boolean send(String formatted);

  public String sendResponse(Integer request, String formatted);

  public void dispatch(String line, boolean async);

  public int holdReference( Object value );
  public Object getReference( int key);
  public void releaseReference( int key ); 
}