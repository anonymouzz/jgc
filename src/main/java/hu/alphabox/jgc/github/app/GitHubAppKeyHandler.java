package hu.alphabox.jgc.github.app;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMWriter;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemWriter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class GitHubAppKeyHandler {

  /**
   * Generates an RSA 2048 keypair with PKCS#8 format.
   */
  public static KeyPair generate() {
    try {
      KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA", "BC");
      gen.initialize(2048, new SecureRandom());

      return gen.generateKeyPair();
    } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
      throw new GitHubAppKeyHandlingException(e);
    }
  }

  public static RSAPrivateKey fromPemFormattedPKCS8(String privateKey) {
    try {
      KeyFactory kf = KeyFactory.getInstance("RSA", "BC");

      PEMParser parser = new PEMParser(new StringReader(privateKey));
      byte[] content = parser.readPemObject().getContent();

      PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(content);
      return (RSAPrivateKey) kf.generatePrivate(keySpec);
    } catch (InvalidKeySpecException | NoSuchAlgorithmException | NoSuchProviderException | IOException e) {
      throw new GitHubAppKeyHandlingException(e);
    }
  }

  /**
   *
   * @return PEM formatted PKCS#1 private key
   */
  public static String asPEMFormattedPKCS1(RSAPrivateKey privateKey) {
    try {
      PemObject pemObject = new PemObject("RSA PRIVATE KEY", asPKCS1(privateKey));

      StringWriter stringWriter = new StringWriter();
      PemWriter pemWriter = new PemWriter(stringWriter);
      pemWriter.writeObject(pemObject);
      pemWriter.close();

      return stringWriter.toString();
    } catch (IOException e) {
      throw new GitHubAppKeyHandlingException(e);
    }
  }

  public static String asX509SubjectPublicKeyInfo(RSAPublicKey publicKey) {
    StringWriter stringWriter = new StringWriter();
    JcaPEMWriter pemWriter = new JcaPEMWriter(stringWriter);

    try {
      pemWriter.writeObject(publicKey);
      pemWriter.close();

      return stringWriter.toString();
    } catch (IOException e) {
      throw new GitHubAppKeyHandlingException(e);
    }
  }

  /**
   *
   * @return PEM formatted PKCS#8 public key
   */
  public static String asPEMFormattedPKCS8(RSAPrivateKey privateKey) {
    try {
      PemObject pemObject = new PemObject("PRIVATE KEY", privateKey.getEncoded());

      StringWriter stringWriter = new StringWriter();
      PemWriter pemWriter = new PemWriter(stringWriter);
      pemWriter.writeObject(pemObject);
      pemWriter.close();

      return stringWriter.toString();
    } catch (IOException e) {
      throw new GitHubAppKeyHandlingException(e);
    }
  }

  public static byte[] asPKCS1(PrivateKey privateKey) {
    try {
      PrivateKeyInfo pkInfo = PrivateKeyInfo.getInstance(privateKey.getEncoded());
      ASN1Encodable encodable = pkInfo.parsePrivateKey();
      ASN1Primitive primitive = encodable.toASN1Primitive();
      return primitive.getEncoded();
    } catch (IOException e) {
      throw new GitHubAppKeyHandlingException(e);
    }
  }
}
