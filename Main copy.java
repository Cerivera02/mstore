package com.mstore;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        String dbURL = "jdbc:sqlite:database.db";

        try (Connection connection = DriverManager.getConnection(dbURL)) {
            System.out.println("Conexión a la base de datos establecida.");

            // Crear las tablas solo si no existen
            createTablesIfNotExists(connection);

            System.out.println("Tablas creadas exitosamente.");         

            Statement statement = connection.createStatement();
            String query = "Select nombre from productos;";

            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String nombre = resultSet.getString("nombre");
                System.out.println(nombre);
            }
        } catch (SQLException e) {
            System.out.println("Error al conectarse a la base de datos: " + e.getMessage());
        }
    }

    private static void createTablesIfNotExists(Connection connection) throws SQLException {
        String createProductosTableSQL = "CREATE TABLE IF NOT EXISTS Productos (" +
                "IDProducto INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Nombre TEXT NOT NULL," +
                "Descripcion TEXT NOT NULL," +
                "Precio REAL NOT NULL," +
                "CantidadStock REAL NOT NULL," +
                "EsGranel BOOLEAN NOT NULL DEFAULT 0," +
                "CodigoBarras TEXT NOT NULL UNIQUE," +
                "Categoria TEXT NOT NULL" +
                ");";

        String createUsuariosTableSQL = "CREATE TABLE IF NOT EXISTS Usuarios (" +
                "IDUsuario INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Nombre TEXT NOT NULL," +
                "Direccion TEXT NOT NULL," +
                "Telefono TEXT NOT NULL," +
                "CorreoElectronico TEXT NOT NULL," +
                "Rol TEXT NOT NULL" +
                ");";

        String createVentasTableSQL = "CREATE TABLE IF NOT EXISTS Ventas (" +
                "IDVenta INTEGER PRIMARY KEY AUTOINCREMENT," +
                "NumeroFactura TEXT NOT NULL," +
                "FechaHora DATETIME NOT NULL," +
                "IDUsuario INTEGER NOT NULL," +
                "MontoTotal REAL NOT NULL," +
                "MetodoPago TEXT NOT NULL," +
                "FOREIGN KEY (IDUsuario) REFERENCES Usuarios(IDUsuario)" +
                ");";

        String createProveedoresTableSQL = "CREATE TABLE IF NOT EXISTS Proveedores (" +
                "IDProveedor INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Nombre TEXT NOT NULL," +
                "Direccion TEXT NOT NULL," +
                "Telefono TEXT NOT NULL," +
                "CorreoElectronico TEXT NOT NULL" +
                ");";

        String createEntradasSalidasDineroTableSQL = "CREATE TABLE IF NOT EXISTS EntradasSalidasDinero (" +
                "IDEntradaSalida INTEGER PRIMARY KEY AUTOINCREMENT," +
                "FechaHora DATETIME NOT NULL," +
                "Descripcion TEXT NOT NULL," +
                "Monto REAL NOT NULL" +
                ");";

        String createCreditoClientesTableSQL = "CREATE TABLE IF NOT EXISTS CreditoClientes (" +
                "IDCredito INTEGER PRIMARY KEY AUTOINCREMENT," +
                "IDCliente INTEGER NOT NULL," +
                "Monto REAL NOT NULL," +
                "FOREIGN KEY (IDCliente) REFERENCES Clientes(IDCliente)" +
                ");";

        String createHistorialCreditoTableSQL = "CREATE TABLE IF NOT EXISTS HistorialCredito (" +
                "IDMovimiento INTEGER PRIMARY KEY AUTOINCREMENT," +
                "IDCredito INTEGER NOT NULL," +
                "TipoMovimiento TEXT NOT NULL," +
                "Monto REAL NOT NULL," +
                "FechaMovimiento DATETIME NOT NULL," +
                "FOREIGN KEY (IDCredito) REFERENCES CreditoClientes(IDCredito)" +
                ");";

        String createDetallesVentasTableSQL = "CREATE TABLE IF NOT EXISTS DetallesVentas (" +
                "IDDetalle INTEGER PRIMARY KEY AUTOINCREMENT," +
                "IDVenta INTEGER NOT NULL," +
                "IDProducto INTEGER NOT NULL," +
                "Cantidad INTEGER NOT NULL," +
                "PrecioUnitario REAL NOT NULL," +
                "Descuentos REAL NOT NULL," +
                "FOREIGN KEY (IDVenta) REFERENCES Ventas(IDVenta)," +
                "FOREIGN KEY (IDProducto) REFERENCES Productos(IDProducto)" +
                ");";

        try (Statement statement = connection.createStatement()) {
            statement.execute(createProductosTableSQL);
            statement.execute(createUsuariosTableSQL);
            statement.execute(createVentasTableSQL);
            statement.execute(createProveedoresTableSQL);
            statement.execute(createEntradasSalidasDineroTableSQL);
            statement.execute(createCreditoClientesTableSQL);
            statement.execute(createHistorialCreditoTableSQL);
            statement.execute(createDetallesVentasTableSQL);
        }
    }
}