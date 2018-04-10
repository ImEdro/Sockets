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
    private JTextArea visor = new JTextArea();
    Icon bravo = new ImageIcon("bravo.png");
    Icon diablito = new ImageIcon("diablito.png");
    Icon enamorado = new ImageIcon("enamorado.png");
    Icon llorando = new ImageIcon("llorando.png");
    Icon mico = new ImageIcon("mico.png");
    Icon mirada = new ImageIcon("mirada.png");
    Icon muelas = new ImageIcon("muelas.png");
    Icon perro = new ImageIcon("perro.png");
    Icon risa = new ImageIcon("risa.png");
    Icon sisa = new ImageIcon("sisa.png");
    Icon sorpresa = new ImageIcon("sorpresa.png");
    Icon uñas = new ImageIcon("uñas.png");
    Icon fantasma = new ImageIcon("fantasma.png");
    private JButton adjuntar = new JButton("Adjuntar", new ImageIcon("adjuntar.png"));
    private DataOutputStream toServer;
    private DataInputStream fromServer;
    private JToolBar toolbar = new JToolBar("Emojis");
    private Socket socket;

    public WuatsapClient() {

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

            socket = new Socket("localhost", 8000);
            fromServer = new DataInputStream(socket.getInputStream());
            toServer = new DataOutputStream(socket.getOutputStream());

            while (true) {

                String texto = fromServer.readUTF();

                visor.append("Servidor: " + texto + "\n");

            }

        } catch (IOException ex) {
            visor.append(ex.toString() + '\n');
        }
    }

    private class Listener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {

                String texto = escritor.getText().trim();

                toServer.writeUTF(socket.getLocalAddress() + ": " + texto);
                toServer.flush();
                escritor.setText("");

                if (e.getActionCommand().equals("bravo")) {
                    JOptionPane.showMessageDialog(visor, "Enviado con Exito");
                    toServer.writeUTF("bravo");
                    visor.append("Enviaste el emoji Bravo" + "\n");
                    toServer.flush();
                }
                if (e.getActionCommand().equals("diablito")) {
                    JOptionPane.showMessageDialog(visor, "Enviado con Exito");
                    toServer.writeUTF("diablito");
                    visor.append("Enviaste el emoji Picaro" + "\n");
                    toServer.flush();
                }
                if (e.getActionCommand().equals("enamorado")) {
                    JOptionPane.showMessageDialog(visor, "Enviado con Exito");
                    toServer.writeUTF("enamorado");
                    visor.append("Enviaste el emoji Enamorado" + "\n");
                    toServer.flush();
                }
                if (e.getActionCommand().equals("llorando")) {
                    JOptionPane.showMessageDialog(visor, "Enviado con Exito");
                    toServer.writeUTF("llorando");
                    visor.append("Enviaste el emoji Llorando" + "\n");
                    toServer.flush();
                }
                if (e.getActionCommand().equals("mico")) {
                    JOptionPane.showMessageDialog(visor, "Enviado con Exito");
                    toServer.writeUTF("mico");
                    visor.append("Enviaste el emoji del Mico" + "\n");
                    toServer.flush();
                }
                if (e.getActionCommand().equals("mirada")) {
                    JOptionPane.showMessageDialog(visor, "Enviado con Exito");
                    toServer.writeUTF("mirada");
                    visor.append("Enviaste el emoji Mirada" + "\n");
                    toServer.flush();
                }
                if (e.getActionCommand().equals("muelas")) {
                    JOptionPane.showMessageDialog(visor, "Enviado con Exito");
                    toServer.writeUTF("muelas");
                    visor.append("Enviaste el emoji Muelas" + "\n");
                    toServer.flush();
                }
                if (e.getActionCommand().equals("perro")) {
                    JOptionPane.showMessageDialog(visor, "Enviado con Exito");
                    toServer.writeUTF("perro");
                    visor.append("Enviaste el emoji del Perro" + "\n");
                    toServer.flush();
                }
                if (e.getActionCommand().equals("risa")) {
                    JOptionPane.showMessageDialog(visor, "Enviado con Exito");
                    toServer.writeUTF("risa");
                    visor.append("Enviaste el emoji Risa" + "\n");
                    toServer.flush();
                }
                if (e.getActionCommand().equals("sisa")) {
                    JOptionPane.showMessageDialog(visor, "Enviado con Exito");
                    toServer.writeUTF("sisa");
                    visor.append("Enviaste el emoji Sisa" + "\n");
                    toServer.flush();
                }
                if (e.getActionCommand().equals("sorpresa")) {
                    JOptionPane.showMessageDialog(visor, "Enviado con Exito");
                    toServer.writeUTF("sorpresa");
                    visor.append("Enviaste el emoji Sorprendido" + "\n");
                    toServer.flush();
                }
                if (e.getActionCommand().equals("uñas")) {
                    JOptionPane.showMessageDialog(visor, "Enviado con Exito");
                    toServer.writeUTF("uñas");
                    visor.append("Enviaste el emoji Uñas" + "\n");
                    toServer.flush();
                }
                if (e.getActionCommand().equals("fantasma")) {
                    JOptionPane.showMessageDialog(visor, "Enviado con Exito");
                    toServer.writeUTF("fantasma");
                    visor.append("Enviaste el emoji Fantasma" + "\n");
                    toServer.flush();
                }
                if (e.getActionCommand().equals("Adjuntar")) {

                    Object[] ruta = {"Ruta: "};
                    String file = JOptionPane.showInputDialog(escritor, ruta, "Ingrese la ruta del archivo", WIDTH);

                    if (file != null) {
                        toServer.writeUTF("Adjuntar");
                        JOptionPane.showMessageDialog(visor, "Ruta: " + file + " ingresada correctamente");
                        JOptionPane.showMessageDialog(visor, "Archivo Enviado Con Exito");
                        ObjectOutputStream archivoenviado = new ObjectOutputStream(toServer);
                        FileInputStream CargaArchivo = new FileInputStream(file);
                        byte[] bites = new byte[4096];
                        while (true) {
                            int paso = CargaArchivo.read(bites);
                            if (paso == -1) {
                                break;
                            }
                            archivoenviado.write(bites, 0, paso);

                        }
                    }
                }

            } catch (IOException ex) {
                System.err.println(ex);
            }
        }
    }

    public static void main(String[] args) {
        new WuatsapClient();
    }
}
