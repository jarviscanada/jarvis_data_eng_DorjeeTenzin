package ca.jrvs.apps.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JDBCExecutor {

  static final Logger logger = LoggerFactory.getLogger(JDBCExecutor.class);

  public static void main(String[] args) {
    BasicConfigurator.configure(); //set default config for logger
    DatabaseConnectionManager dcm = new DatabaseConnectionManager("localhost", "hplussport",
        "postgres", "postgres");
    try {
      Connection connection = dcm.getConnection();
      CustomerDAO customerDAO = new CustomerDAO(connection);
      OrderDAO orderDAO = new OrderDAO(connection);
    } catch (SQLException e) {
      logger.error("Error: Database connection Error", e);
    }
  }
}
