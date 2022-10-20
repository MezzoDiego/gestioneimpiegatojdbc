package it.prova.gestioneimpiegatojdbc.test;

import java.sql.Connection;

import it.prova.gestioneimpiegatojdbc.connection.MyConnection;
import it.prova.gestioneimpiegatojdbc.dao.Constants;
import it.prova.gestioneimpiegatojdbc.dao.impiegato.ImpiegatoDAO;
import it.prova.gestioneimpiegatojdbc.dao.impiegato.ImpiegatoDAOImpl;

public class TestImpiegato {

	public static void main(String[] args) {
		ImpiegatoDAO impiegatoDAOInstance = null;

		try (Connection connection = MyConnection.getConnection(Constants.DRIVER_NAME, Constants.CONNECTION_URL)) {
			// ecco chi 'inietta' la connection: il chiamante
			impiegatoDAOInstance = new ImpiegatoDAOImpl(connection);

			// ....test...

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
