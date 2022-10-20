package it.prova.gestioneimpiegatojdbc.test;

import java.sql.Connection;
import java.util.List;

import it.prova.gestioneimpiegatojdbc.connection.MyConnection;
import it.prova.gestioneimpiegatojdbc.dao.Constants;
import it.prova.gestioneimpiegatojdbc.dao.compagnia.CompagniaDAO;
import it.prova.gestioneimpiegatojdbc.dao.compagnia.CompagniaDAOImpl;
import it.prova.gestioneimpiegatojdbc.model.Compagnia;

public class TestCompagnia {

	public static void main(String[] args) {
		CompagniaDAO compagniaDAOInstance = null;

		try (Connection connection = MyConnection.getConnection(Constants.DRIVER_NAME, Constants.CONNECTION_URL)) {
			// ecco chi 'inietta' la connection: il chiamante
			compagniaDAOInstance = new CompagniaDAOImpl(connection);
//*************************************************************
			
			testInsert(compagniaDAOInstance);
			
//*************************************************************			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	private static void testInsert(CompagniaDAO compagniaDAOInstance) throws Exception{
		List<Compagnia> elencoCompagniePresenti = compagniaDAOInstance.list();
	}

}
