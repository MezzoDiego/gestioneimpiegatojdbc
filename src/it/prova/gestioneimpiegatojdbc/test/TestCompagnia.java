package it.prova.gestioneimpiegatojdbc.test;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import it.prova.gestioneimpiegatojdbc.connection.MyConnection;
import it.prova.gestioneimpiegatojdbc.dao.Constants;
import it.prova.gestioneimpiegatojdbc.dao.compagnia.CompagniaDAO;
import it.prova.gestioneimpiegatojdbc.dao.compagnia.CompagniaDAOImpl;
import it.prova.gestioneimpiegatojdbc.dao.impiegato.ImpiegatoDAO;
import it.prova.gestioneimpiegatojdbc.dao.impiegato.ImpiegatoDAOImpl;
import it.prova.gestioneimpiegatojdbc.model.Compagnia;

public class TestCompagnia {

	public static void main(String[] args) {
		CompagniaDAO compagniaDAOInstance = null;
		ImpiegatoDAO impiegatoDAOInstance = null;


		try (Connection connection = MyConnection.getConnection(Constants.DRIVER_NAME, Constants.CONNECTION_URL)) {
			// ecco chi 'inietta' la connection: il chiamante
			compagniaDAOInstance = new CompagniaDAOImpl(connection);
			impiegatoDAOInstance = new ImpiegatoDAOImpl(connection);
//*************************************************************

			 //testInsert(compagniaDAOInstance);

			System.out.println("-----------------------------------------------------------------------------");

			//testDelete(compagniaDAOInstance, impiegatoDAOInstance);

			System.out.println("-----------------------------------------------------------------------------");
			
		    //testFindAllByDataAssunzioneMaggioreDi(compagniaDAOInstance);
			
			System.out.println("-----------------------------------------------------------------------------");
			
			//testFindAllByRagioneSocialeContiene(compagniaDAOInstance);
			
			System.out.println("-----------------------------------------------------------------------------");
			

//*************************************************************			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void testInsert(CompagniaDAO compagniaDAOInstance) throws Exception {
		// mi salvo in una variabile la lista dei records di compagnia presenti nella
		// tabella
		List<Compagnia> elencoCompagniePresenti = compagniaDAOInstance.list();
		int quantiPresenti = elencoCompagniePresenti.size();
		// eseguo la insert
		Date dataPerTestInsert = new SimpleDateFormat("dd-MM-yyyy").parse("03-01-2022");
		Compagnia compagniaDaInserire = new Compagnia("Annecchiarico s.r.l.", 1000000, dataPerTestInsert);
		compagniaDAOInstance.insert(compagniaDaInserire);

		// verifico che sia andato tutto bene
		if (quantiPresenti + 1 != compagniaDAOInstance.list().size())
			throw new AssertionError("Test Insert: FAILED");

		// stampa records
		System.out.println(compagniaDAOInstance.list());

		// reset tabella
		int rowsAffected = compagniaDAOInstance.deleteAll();
		System.out.println("cancellati " + rowsAffected + " records");

	}

	private static void testDelete(CompagniaDAO compagniaDAOInstance, ImpiegatoDAO impiegatoDAOInstance) throws Exception {
		// mi salvo in una variabile la lista dei records di compagnia presenti nella
		// tabella
		List<Compagnia> elencoCompagniePresenti = compagniaDAOInstance.list();
		int quantiPresenti = elencoCompagniePresenti.size();
		// eseguo la insert
		Date dataPerTestInsert = new SimpleDateFormat("dd-MM-yyyy").parse("03-01-2022");
		Compagnia compagniaDaInserire = new Compagnia("Annecchiarico s.r.l.", 1000000, dataPerTestInsert);
		compagniaDAOInstance.insert(compagniaDaInserire);

		// verifico che sia andato tutto bene
		if (quantiPresenti + 1 != compagniaDAOInstance.list().size())
			throw new AssertionError("Test Insert: FAILED");

		// stampa records
		System.out.println("before delete.." + compagniaDAOInstance.list());

		// mi salvo l'id per provare a ricaricarlo dopo la delete
		Long idElementoPerRicaricataDaDB = compagniaDAOInstance.list().get(0).getId();
		// eseguo la delete vera e propria
		impiegatoDAOInstance.deleteChildRows(compagniaDAOInstance.list().get(0));
		compagniaDAOInstance.delete(compagniaDAOInstance.list().get(0));
		// verifica effettiva eliminazione
		Compagnia compagniaRicaricataDaDB = compagniaDAOInstance.get(idElementoPerRicaricataDaDB);
		if (compagniaRicaricataDaDB != null)
			throw new RuntimeException("Test delete: FAILED");
		System.out.println("after delete..." + compagniaDAOInstance.list());

	}
	
	private static void testFindAllByDataAssunzioneMaggioreDi(CompagniaDAO compagniaDAOInstance) throws Exception{
		System.out.println(".......testFindAllByDataAssunzioneMaggioreDi inizio.............");

		List<Compagnia> elencoCompagniePresenti = compagniaDAOInstance.list();
		int quantiPresenti = elencoCompagniePresenti.size();
		// eseguo la insert
		Date dataPerTestInsert = new SimpleDateFormat("dd-MM-yyyy").parse("03-01-2022");
		Compagnia compagniaDaInserire = new Compagnia("Annecchiarico s.r.l.", 1000000, dataPerTestInsert);
		compagniaDAOInstance.insert(compagniaDaInserire);

		// verifico che sia andato tutto bene
		if (quantiPresenti + 1 != compagniaDAOInstance.list().size())
			throw new AssertionError("Test Insert: FAILED");
		
		List<Compagnia> listaDiCompagnieAventiImiegatiConDataAssunzioneMaggioreDi = compagniaDAOInstance.findAllByDataAssunzioneMaggioreDi(new SimpleDateFormat("dd-MM-yyyy").parse("03-01-2020"));

		System.out.println(listaDiCompagnieAventiImiegatiConDataAssunzioneMaggioreDi);
		
		// reset tabella
		int rowsAffected = compagniaDAOInstance.deleteAll();
		System.out.println("cancellati " + rowsAffected + " records");

		System.out.println(".......testFindAllByDataAssunzioneMaggioreDi fine: PASSED.............");
	}
	
	private static void testFindAllByRagioneSocialeContiene(CompagniaDAO compagniaDAOInstance) throws Exception {
		System.out.println(".......testFindAllByRagioneSocialeContiene inizio.............");

		List<Compagnia> elencoCompagniePresenti = compagniaDAOInstance.list();
		int quantiPresenti = elencoCompagniePresenti.size();
		// eseguo la insert
		Date dataPerTestInsert = new SimpleDateFormat("dd-MM-yyyy").parse("03-01-2022");
		Compagnia compagniaDaInserire = new Compagnia("Annecchiarico s.r.l.", 1000000, dataPerTestInsert);
		compagniaDAOInstance.insert(compagniaDaInserire);

		// verifico che sia andato tutto bene
		if (quantiPresenti + 1 != compagniaDAOInstance.list().size())
			throw new AssertionError("Test Insert: FAILED");
		
		List<Compagnia> listaDiCompagnieAventiRagioneSocialeContenente = compagniaDAOInstance.findAllByRagioneSocialeContiene("Anne");

		System.out.println(listaDiCompagnieAventiRagioneSocialeContenente);
		

		System.out.println(".......testFindAllByRagioneSocialeContiene fine: PASSED.............");
	}

}
