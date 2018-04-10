package wuatsap;

import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.awt.image.ImageObserver.WIDTH;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;

public class WuatsapClient extends JFrame {

    private JTextField escritor = new JTextField();
    private static JTextArea visor = new JTextArea();
    private JButton adjuntar = new JButton("Adjuntar", new ImageIcon("adjuntar.png"));
    private DataOutputStream toServer;
    private DataInputStream fromServer;
    private JToolBar toolbar = new JToolBar("Emojis");
    private Socket socket;

    public WuatsapClient() throws NoSuchAlgorithmException {

        JPanel p = new JPanel();
        p.setLayout(new BorderLayout());
        p.add(new JLabel("Ingrese su mensaje "), BorderLayout.WEST);
        p.add(escritor, BorderLayout.CENTER);
        adjuntar.setToolTipText("Adjuntar Archivo");
        adjuntar.addActionListener(new Listener());
        p.add(adjuntar, BorderLayout.EAST);

        setLayout(new BorderLayout());
        add(toolbar, BorderLayout.NORTH);
        add(new JScrollPane(visor), BorderLayout.CENTER);
        add(p, BorderLayout.SOUTH);

        escritor.addActionListener(new Listener());

        setTitle("Wuatsap Client");
        setSize(575, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        try {

            socket = new Socket("172.30.10.134", 8000);
            fromServer = new DataInputStream(socket.getInputStream());
            toServer = new DataOutputStream(socket.getOutputStream());

            while (true) {

                String texto = fromServer.readUTF();

                visor.append("Servidor: " + texto + "\n");
                separarFrase(texto);
            }

        } catch (IOException ex) {
            visor.append(ex.toString() + '\n');
        }
    }

    private class Listener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String password = "123456";

                MessageDigest md = MessageDigest.getInstance("SHA-256");
                md.update(password.getBytes());

                byte byteData[] = md.digest();

                StringBuffer hexString = new StringBuffer();
                for (int i = 0; i < byteData.length; i++) {
                    String hex = Integer.toHexString(0xff & byteData[i]);
                    if (hex.length() == 1) {
                        hexString.append('0');
                    }
                    hexString.append(hex);
                }
                String texto = password + ";" + hexString.toString();

                toServer.writeUTF(texto);
                toServer.flush();
                escritor.setText("");

            } catch (IOException ex) {
                System.err.println(ex);
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(WuatsapClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        new WuatsapClient();
    }

    public static void separarFrase(String s) throws NoSuchAlgorithmException {
        int cp = 0; // Cantidad de palabras

        // Recorremos en busca de espacios
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == ';') { // Si es un espacio
                cp++; // Aumentamos en uno la cantidad de palabras
            }
        }

        // "Este blog es genial" tiene 3 espacios y 3 + 1 palabras
        String[] partes = new String[cp + 1];
        for (int i = 0; i < partes.length; i++) {
            partes[i] = ""; // Se inicializa en "" en lugar de null (defecto)
        }

        int ind = 0; // Creamos un índice para las palabras
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == ';') { // Si hay un espacio
                ind++; // Pasamos a la siguiente palabra
                continue; // Próximo i
            }
            partes[ind] += s.charAt(i); // Sino, agregamos el carácter a la palabra actual
        }
        String resultado = igual(partes);
        visor.append(resultado);
        System.out.println(resultado);
    }

    public static String igual(String[] a) throws NoSuchAlgorithmException {
        String respuesta;
        String password = a[0];
        String hash = a[1];
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(password.getBytes());

        byte byteData[] = md.digest();

        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
            String hex = Integer.toHexString(0xff & byteData[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        if (hexString.toString().equals(hash)) {
            respuesta = "Mensaje recibido: "+password+"\n Hash recibido:   "+hash+"\n Hash calculado: "+hexString.toString()+"\n Por lo tanto son iguales";
        } else {
            respuesta = "No son iguales";
        }
        return respuesta;

    }
}
