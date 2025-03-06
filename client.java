import java.net.*;
import java.io.*;
import javax.net.ssl.*;
import java.util.Scanner;
import java.util.jar.Attributes.Name;
import java.security.KeyStore;
import java.security.cert.*;

/*
 * This example shows how to set up a key manager to perform client
 * authentication.
 *
 * This program assumes that the client is not inside a firewall.
 * The application can be modified to connect to a server outside
 * the firewall by following SSLSocketClientWithTunneling.java.
 */

public class client {
  public static void main(String[] args) throws Exception {
    String host = null;
    int port = -1;
    String name = null;
    for (int i = 0; i < args.length; i++) {
      System.out.println("args[" + i + "] = " + args[i]);

    }
    if (args.length < 1) {
      System.out.println("USAGE: java client [host] port");
      System.exit(-1);
    }
    try { /* get input parameters */
      if (args.length == 1)
        port = Integer.parseInt(args[0]);
      else if (args.length == 2) {
        host = args[0];
        port = Integer.parseInt(args[1]);
      } else {
        host = args[0];
        port = Integer.parseInt(args[1]);
        name = args[2];
      }
    } catch (IllegalArgumentException e) {
      System.out.println("USAGE: java client [host] port");
      System.exit(-1);
    }

    try {
      SSLSocketFactory factory = null;
      try {
        char[] password = "passwd".toCharArray();
        KeyStore ks = KeyStore.getInstance("JKS");
        KeyStore ts = KeyStore.getInstance("JKS");
        KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
        TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
        SSLContext ctx = SSLContext.getInstance("TLSv1.2");
        // keystore password (storepass)
        ks.load(new FileInputStream("TLS_Users/" + name + "/clientkeystore"), password);
        // truststore password (storepass);
        ts.load(new FileInputStream("TLS_Users/" + name + "/clienttruststore"), password);
        kmf.init(ks, password); // user password (keypass)
        tmf.init(ts); // keystore can be used as truststore here
        ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
        factory = ctx.getSocketFactory();
      } catch (Exception e) {
        throw new IOException(e.getMessage());
      }
      SSLSocket socket = (SSLSocket) factory.createSocket(host, port);
      System.out.println("\nsocket before handshake:\n" + socket + "\n");

      /*
       * send http request
       *
       * See SSLSocketClient.java for more information about why
       * there is a forced handshake here when using PrintWriters.
       */

      socket.startHandshake();
      SSLSession session = socket.getSession();
      Certificate[] cert = session.getPeerCertificates();
      String subject = ((X509Certificate) cert[0]).getSubjectX500Principal().getName();
      String issuer = ((X509Certificate) cert[0]).getIssuerX500Principal().getName();
      String serial = ((X509Certificate) cert[0]).getSerialNumber().toString();
      System.out.println("certificate name (subject DN field) on certificate received from server:\n" + subject + "\n"
          + issuer + "\n" + serial + "\n");
      System.out.println("socket after handshake:\n" + socket + "\n");
      System.out.println("secure connection established\n\n");

      BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
      PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
      BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      
      // --- LOGIN PHASE (blocking calls) ---
      // Wait for and display the username prompt from the server.
      while(true){
        System.out.println(in.readLine()); // Expecting "Username" prompt
        System.out.print(">");
        String username = read.readLine();
        out.println(username);
        out.flush();
        
        // Wait for and display the password prompt from the server.
        System.out.println(in.readLine()); // Expecting "Password:" prompt
        System.out.print(">");
        String password = read.readLine();
        out.println(password);
        out.flush();
        
        // Read the login response from the server.
        String loginResponse = in.readLine();
        System.out.println("Server response: " + loginResponse);
        if (loginResponse.equalsIgnoreCase("logged in")) {
          break;
        }
      }
      
      // --- MAIN MESSAGE LOOP ---
      while (true) {
        // Blocking call: Wait for a message from the server.
        String serverMsg = in.readLine();
        if (serverMsg == null) { // Connection closed
            break;
        }
  
        // Blocking call: Wait for user input.
        
        if(!serverMsg.equals("$")){
          System.out.println(serverMsg);
          System.out.print(">");
          String msg = read.readLine();
          if (msg == null || msg.equalsIgnoreCase("quit")) {
              break;
          }
          System.out.print("sending '" + msg + "' to server...");
          out.println(msg);
          out.flush();
          System.out.println("done");
        }
        
        // Optionally, block for an immediate response from the server.
        String response = in.readLine();
        if (response != null) {
            System.out.println("received '" + response + "' from server\n");
        }
      }
      in.close();
      out.close();
      read.close();
      socket.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
