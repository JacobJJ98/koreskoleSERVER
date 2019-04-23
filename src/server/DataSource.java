package server;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DataSource {

    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;

    private static final String HOST     = "130.225.170.204";
    private static final int    PORT     = 4422;
    private static final String DATABASE = "koreskole";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "kodekode";


    static {

        String jdbcUrl ="jdbc:hsqldb:hsql://" + HOST + ":" + PORT + "/" + DATABASE;
//        String url = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE;
        config.setJdbcUrl(jdbcUrl);

        config.setUsername(USERNAME);
        config.setPassword(PASSWORD);

        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        ds = new HikariDataSource(config);

    }

    private DataSource() {
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

}
