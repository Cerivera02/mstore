package com.mstore;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {
    private JTextField userTextField;
    private JPasswordField passwordField;

    public LoginFrame() {
        // Configurar el JFrame
        setTitle("Inicio de Sesión");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel para el centro
        JPanel centerPanel = new JPanel(new GridLayout(2, 2));
        JLabel userLabel = new JLabel("Usuario:");
        JLabel passwordLabel = new JLabel("Contraseña:");

        userTextField = new JTextField();
        passwordField = new JPasswordField();

        centerPanel.add(userLabel);
        centerPanel.add(userTextField);
        centerPanel.add(passwordLabel);
        centerPanel.add(passwordField);

        // Panel para los botones
        JPanel southPanel = new JPanel();
        JButton loginButton = new JButton("Iniciar sesión");
        loginButton.addActionListener(new LoginButtonListener());
        southPanel.add(loginButton);

        // Añadir los paneles al JFrame
        add(centerPanel, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);
    }

    private class LoginButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Obtener credenciales del formulario
            String username = userTextField.getText();
            String password = new String(passwordField.getPassword());

            // Verificar credenciales (NOTA: Este es un ejemplo simple, no almacenes contraseñas en texto plano en el código)
            if ("admin".equals(username) && "admin".equals(password)) {
                JOptionPane.showMessageDialog(null, "Inicio de sesión exitoso!");
                // Aquí podrías abrir la ventana principal de tu aplicación
            } else {
                JOptionPane.showMessageDialog(null, "Inicio de sesión fallido. Inténtalo de nuevo.");
            }
        }
    }
}
