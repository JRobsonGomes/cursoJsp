package br.com.robson.connection;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class SingleConnectionBanco {

//	private static String banco = "jdbc:postgresql://localhost:5432/curso-jsp?autoReconnect=true";
//	private static String user = "robson";
//	private static String senha = "010203mdf";
	private static Connection connection = null;

	public static Connection getConnection() {
		return connection;
	}

	static {
		conectar();
	}

	public SingleConnectionBanco() {/* quando tiver um instancia vai conectar */
		conectar();
	}

	private static void conectar() {
		try {
			if (connection == null) {
				Properties props = loadProperties(); /* Carrega as propriedades de conexão */
				String url = props.getProperty("jdbc.url");
				String drive = props.getProperty("jdbc.driver.classname");

				Class.forName(drive); /* Carrega o driver de conexão do banco */
				connection = DriverManager.getConnection(url, props);
				connection.setAutoCommit(false); /* para nao efetuar alteracoes no banco sem nosso comando */
			}

		} catch (SQLException | ClassNotFoundException e) {
			throw new DbException(e.getMessage());/* Mostrar qualquer erro no momento de conectar */
		}
	}

	private static Properties loadProperties() {
		try {
			URL resource = SingleConnectionBanco.class.getResource("/db.properties");
			FileInputStream fs = new FileInputStream(new File(resource.toURI()));
			Properties props = new Properties();
			props.load(fs);
			return props;
		} catch (IOException | URISyntaxException e) {
			throw new DbException(e.getMessage());
		}
	}
}
