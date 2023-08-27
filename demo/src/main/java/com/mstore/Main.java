package com.mstore;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        Connection c = null;
        
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:database.db");

            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='Roles';");

            if (!rs.next()) {
                System.out.println("Creando tablas...");

                String createRoles = "CREATE TABLE Roles (RolID INTEGER PRIMARY KEY AUTOINCREMENT, Nombre TEXT);";
                stmt.executeUpdate(createRoles);
                stmt.executeUpdate("INSERT INTO Roles (Nombre) VALUES ('Administrador'), ('Cajero');");

                String createUsuarios = "CREATE TABLE Usuarios (UsuarioID INTEGER PRIMARY KEY AUTOINCREMENT, Nombre TEXT, Apellido TEXT, Usuario TEXT, Contraseña TEXT, RolID INTEGER, FOREIGN KEY(RolID) REFERENCES Roles(RolID));";
                stmt.executeUpdate(createUsuarios);
                stmt.executeUpdate("INSERT INTO Usuarios (usuario,contraseña,RolID) VALUES ('admin','admin','1');");

                String createProveedores = "CREATE TABLE Proveedores (ProveedorID INTEGER PRIMARY KEY AUTOINCREMENT, Nombre TEXT, Contacto TEXT, Telefono TEXT);";
                stmt.executeUpdate(createProveedores);

                String createInventario = "CREATE TABLE Inventario (ArticuloID INTEGER PRIMARY KEY AUTOINCREMENT, Nombre TEXT, Descripcion TEXT, Cantidad INTEGER, Precio REAL, EsAGranel BOOLEAN, ProveedorID INTEGER, FOREIGN KEY(ProveedorID) REFERENCES Proveedores(ProveedorID));";
                stmt.executeUpdate(createInventario);

                String createVentas = "CREATE TABLE Ventas (VentaID INTEGER PRIMARY KEY AUTOINCREMENT, Fecha TEXT DEFAULT CURRENT_TIMESTAMP, UsuarioID INTEGER, ClienteID INTEGER, Total REAL, FOREIGN KEY(UsuarioID) REFERENCES Usuarios(UsuarioID), FOREIGN KEY(ClienteID) REFERENCES Clientes(ClienteID));";
                stmt.executeUpdate(createVentas);

                String createDetalleVentas = "CREATE TABLE DetalleVentas (DetalleID INTEGER PRIMARY KEY AUTOINCREMENT, VentaID INTEGER, ArticuloID INTEGER, Cantidad REAL, Subtotal REAL, FOREIGN KEY(VentaID) REFERENCES Ventas(VentaID), FOREIGN KEY(ArticuloID) REFERENCES Inventario(ArticuloID));";
                stmt.executeUpdate(createDetalleVentas);

                String createClientes = "CREATE TABLE Clientes (ClienteID INTEGER PRIMARY KEY AUTOINCREMENT, Nombre TEXT, Apellido TEXT, LimiteCredito REAL);";
                stmt.executeUpdate(createClientes);

                String createCreditos = "CREATE TABLE Creditos (CreditoID INTEGER PRIMARY KEY AUTOINCREMENT, ClienteID INTEGER, VentaID INTEGER, Monto REAL, Fecha TEXT DEFAULT CURRENT_TIMESTAMP, Pagado BOOLEAN DEFAULT 0, FOREIGN KEY(ClienteID) REFERENCES Clientes(ClienteID), FOREIGN KEY(VentaID) REFERENCES Ventas(VentaID));";
                stmt.executeUpdate(createCreditos);

                String createEntradasSalidas = "CREATE TABLE EntradasSalidas (EntradaSalidaID INTEGER PRIMARY KEY AUTOINCREMENT, ArticuloID INTEGER, Cantidad REAL, Tipo TEXT, Fecha TEXT DEFAULT CURRENT_TIMESTAMP, UsuarioID INTEGER, FOREIGN KEY(ArticuloID) REFERENCES Inventario(ArticuloID), FOREIGN KEY(UsuarioID) REFERENCES Usuarios(UsuarioID));";
                stmt.executeUpdate(createEntradasSalidas);

                System.out.println("Tablas creadas exitosamente.");
            }
            
            stmt.close();
            c.close();

            
            LoginFrame login = new LoginFrame();
            login.setVisible(true);
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }
}