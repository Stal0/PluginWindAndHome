package com.windhome.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseManager {

    private static HikariDataSource dataSource;

    public DatabaseManager(String host, int port, String database, String username, String password) {
        HikariConfig config = new HikariConfig();
        // System.out.println("jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=false&user=" + "root");
config.setJdbcUrl("jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=false&user=" + "root");

     /*   config.setJdbcUrl(host);
        config.setUsername(username);
        config.setPassword(password);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
*/
        dataSource = new HikariDataSource(config);
    }

    public void connect() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            if (connection.isValid(2)) {
                System.out.println("Conexão com o banco de dados estabelecida com sucesso!");
            } else {
                throw new SQLException("Falha ao estabelecer conexão com o banco de dados.");
            }
        }
    }


    public void disconnect() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
            System.out.println("Conexão com o banco de dados encerrada.");
        }
    }

    public static Connection getConnection() throws SQLException {
        if (dataSource != null && !dataSource.isClosed()) {
            return dataSource.getConnection();
        } else {
            throw new SQLException("DataSource não iniciada");
        }
    }
}
