package hu.alphabox.jgc.config.security;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

class CachedBodyServletInputStream extends ServletInputStream {

  private final InputStream inputStream;

  CachedBodyServletInputStream(byte[] content) {
    this.inputStream = new ByteArrayInputStream(content);
  }

  @Override
  public boolean isFinished() {
    try {
      return inputStream.available() == 0;
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }

  @Override
  public boolean isReady() {
    return true;
  }

  @Override
  public void setReadListener(ReadListener readListener) {
    throw new UnsupportedOperationException();
  }

  @Override
  public int read() throws IOException {
    return inputStream.read();
  }
}
