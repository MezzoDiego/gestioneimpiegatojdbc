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
import it.prova.gestioneimpiegatojdbc.model.Impiegato;

public class TestImpiegato {

	public static void main(String[] args) {
		ImpiegatoDAO impiegatoDAOInstance = null;
		CompagniaDAO compagniaDAOInstance = null;

		try (Connection connection = MyConnection.getConnection(Constants.DRIVER_NAME, Constants.CONNECTION_URL)) {
			// ecco chi 'inietta' la connection: il chiamante
			impiegatoDAOInstance = new ImpiegatoDAOImpl(connection);
			compagniaDAOInstance = new CompagniaDAOImpl(connection);
//********************************************************************
			//testInsert(impiegatoDAOInstance, compagniaDAOInstance);
			System.out.println("-----------------------------------------------------------------------------");
			testDelete(impiegatoDAOInstance, compagniaDAOInstance);
			System.out.println("-----------------------------------------------------------------------------");
			System.out.println("-----------------------------------------------------------------------------");
			System.out.println("-----------------------------------------------------------------------------");

//********************************************************************

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void testInsert(ImpiegatoDAO impiegatoDAOInstance, CompagniaDAO compagniaDAOInstance)
			throws Exception {
		// mi salvo in una variabile la lista dei records di compagnia presenti nella
		// tabella
		List<Impiegato> elencoImpiegatiPresenti = impiegatoDAOInstance.list();
		int quantiPresenti = elencoImpiegatiPresenti.size();
		// eseguo la insert
		Date dataDiNascitaPerTestInsert = new SimpleDateFormat("dd-MM-yyyy").parse("01-10-2002");
		Date dataAssunzionePerTestInsert = new SimpleDateFormat("dd-MM-yyyy").parse("01-08-2021");
		Compagnia compagniaPerLaQualeLavoraImpiegato = compagniaDAOInstance.list().get(0);

		Impiegato impiegatoDaInserire = new Impiegato("diego", "mezzo", "mzzdgi02r01i608p", dataDiNascitaPerTestInsert,
				dataAssunzionePerTestInsert, compagniaPerLaQualeLavoraImpiegato);
		impiegatoDAOInstance.insert(impiegatoDaInserire);

		// verifico che sia andato tutto bene
		if (quantiPresenti + 1 != impiegatoDAOInstance.list().size())
			throw new AssertionError("Test Insert: FAILED");

		// stampa records
		System.out.println(elencoImpiegatiPresenti);

		// reset tabella
		int rowsAffected = impiegatoDAOInstance.deleteAll();
		System.out.println("cancellati " + rowsAffected + " records");

	}

	private static void testDelete(ImpiegatoDAO impiegatoDAOInstance, CompagniaDAO compagniaDAOInstance)
			throws Exception {

		// mi salvo in una variabile la lista dei records di compagnia presenti nella
		// tabella
		List<Impiegato> elencoImpiegatiPresenti = impiegatoDAOInstance.list();
		int quantiPresenti = elencoImpiegatiPresenti.size();
		// eseguo la insert
		Date dataPerTestInsert = new SimpleDateFormat("dd-MM-yyyy").parse("03-01-2022");
		Compagnia compagniaDaInserireQualoraLaListaSiaVuota = new Compagnia("Annecchiarico s.r.l.", 1000000,
				dataPerTestInsert);

		compagniaDAOInstance.insert(compagniaDaInserireQualoraLaListaSiaVuota);

		// verifico che sia andato tutto bene
		if (compagniaDAOInstance.list().size() < 1)
			throw new AssertionError("Test Insert: FAILED");

		Date dataDiNascitaPerTestInsert = new SimpleDateFormat("dd-MM-yyyy").parse("01-10-2002");
		Date dataAssunzionePerTestInsert = new SimpleDateFormat("dd-MM-yyyy").parse("01-08-2021");
		Compagnia compagniaPerLaQualeLavoraImpiegato = compagniaDAOInstance.list().get(0);

		Impiegato impiegatoDaInserire = new Impiegato("diego", "mezzo", "mzzdgi02r01i608p", dataDiNascitaPerTestInsert,
				dataAssunzionePerTestInsert, compagniaPerLaQualeLavoraImpiegato);
		impiegatoDAOInstance.insert(impiegatoDaInserire);

		// verifico che sia andato tutto bene
		if (quantiPresenti + 1 != impiegatoDAOInstance.list().size())
			throw new AssertionError("Test Insert: FAILED");

		// stampa records
		System.out.println("before delete.." + impiegatoDAOInstance.list());

		// mi salvo l'id per provare a ricaricarlo dopo la delete
		Long idElementoPerRicaricataDaDB = elencoImpiegatiPresenti.get(0).getId();
		// eseguo la delete vera e propria
		impiegatoDAOInstance.delete(elencoImpiegatiPresenti.get(0));
		// verifica effettiva eliminazione
		Impiegato impiegatoRicaricataDaDB = impiegatoDAOInstance.get(idElementoPerRicaricataDaDB);
		if (impiegatoRicaricataDaDB != null)
			throw new RuntimeException("Test delete: FAILED");
		System.out.println("after delete..." + impiegatoDAOInstance.list());

	}

}
