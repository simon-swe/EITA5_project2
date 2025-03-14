import java.io.*;
import java.net.*;
import javax.net.*;
import javax.net.ssl.*;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;

public class server implements Runnable {
  private ServerSocket serverSocket = null;
  private static int numConnectedClients = 0;

  public server(ServerSocket ss) throws IOException {
    serverSocket = ss;
    newListener();
  }

  public void run() {
    try {
      SSLSocket socket = (SSLSocket) serverSocket.accept();
      newListener();
      SSLSession session = socket.getSession();
      Certificate[] cert = session.getPeerCertificates();
      String subject = ((X509Certificate) cert[0]).getSubjectX500Principal().getName();
      String issuer = ((X509Certificate) cert[0]).getIssuerX500Principal().getName();
      String serial = ((X509Certificate) cert[0]).getSerialNumber().toString();
      numConnectedClients++;
      System.out.println("client connected");
      System.out.println("client name (cert subject DN field): " + subject + "\n" + issuer + "\n" + serial + "\n");
      System.out.println(numConnectedClients + " concurrent connection(s)\n");

      PrintWriter out = null;
      BufferedReader in = null;
      out = new PrintWriter(socket.getOutputStream(), true);
      in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

      loginServer loginServer = new loginServer();
      boolean loggedIn = loginServer.login(out, in);
      if (loggedIn) {
        main_to_connect mtc = new main_to_connect(out, in, loginServer.getAccess());
        mtc.program();

      }
      in.close();
      out.close();
      socket.close();
      numConnectedClients--;
      System.out.println("client disconnected");
      System.out.println(numConnectedClients + " concurrent connection(s)\n");
    } catch (IOException e) {
      System.out.println("Client died: " + e.getMessage());
      e.printStackTrace();
      return;
    }
  }

  private void newListener() {
    (new Thread(this)).start();
  } // calls run()

  public static void main(String args[]) {
    System.out.println("\nServer Started\n");
    int port = -1;
    if (args.length >= 1) {
      port = Integer.parseInt(args[0]);
    }
    String type = "TLSv1.2";
    try {
      ServerSocketFactory ssf = getServerSocketFactory(type);
      ServerSocket ss = ssf.createServerSocket(port, 0, InetAddress.getByName(null));
      ((SSLServerSocket) ss).setNeedClientAuth(true); // enables client authentication
      new server(ss);
    } catch (IOException e) {
      System.out.println("Unable to start Server: " + e.getMessage());
      e.printStackTrace();
    }
  }

  private static ServerSocketFactory getServerSocketFactory(String type) {
    if (type.equals("TLSv1.2")) {
      SSLServerSocketFactory ssf = null;
      try { // set up key manager to perform server authentication
        SSLContext ctx = SSLContext.getInstance("TLSv1.2");
        KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
        TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
        KeyStore ks = KeyStore.getInstance("JKS");
        KeyStore ts = KeyStore.getInstance("JKS");
        char[] password = "password".toCharArray();
        // keystore password (storepass)
        ks.load(new FileInputStream("TLS_server/serverkeystore"), password);
        // truststore password (storepass)
        ts.load(new FileInputStream("TLS_server/servertruststore"), password);
        kmf.init(ks, password); // certificate password (keypass)
        tmf.init(ts); // possible to use keystore as truststore here
        ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
        ssf = ctx.getServerSocketFactory();
        return ssf;
      } catch (Exception e) {
        e.printStackTrace();
      }
    } else {
      return ServerSocketFactory.getDefault();
    }
    return null;
  }
}
