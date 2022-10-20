package it.prova.gestioneimpiegatojdbc.dao.compagnia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.prova.gestioneimpiegatojdbc.dao.AbstractMySQLDAO;
import it.prova.gestioneimpiegatojdbc.model.Compagnia;
import it.prova.gestioneimpiegatojdbc.model.Impiegato;

public class CompagniaDAOImpl extends AbstractMySQLDAO implements CompagniaDAO {

	// connessione al db iniettata dall'esterno
	public CompagniaDAOImpl(Connection connection) {
		super(connection);
	}

	@Override
	public List<Compagnia> list() throws Exception {
		// verifica connessione
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		ArrayList<Compagnia> result = new ArrayList<>();
		Compagnia temp = null;

		try (Statement ps = connection.createStatement(); ResultSet rs = ps.executeQuery("select * from compagnia")) {

			while (rs.next()) {
				temp = new Compagnia();
				temp.setId(rs.getLong("id"));
				temp.setRagioneSociale(rs.getString("ragionesociale"));
				temp.setFatturatoAnnuo(rs.getInt("fatturatoannuo"));
				temp.setDataFondazione(rs.getDate("datafondazione"));
				result.add(temp);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public Compagnia get(Long idInput) throws Exception {
		// prima di tutto cerchiamo di capire se possiamo effettuare le operazioni
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (idInput == null || idInput < 1)
			throw new Exception("Valore di input non ammesso.");

		Compagnia result = null;
		try (PreparedStatement ps = connection.prepareStatement("select * from compagnia where id=?")) {

			ps.setLong(1, idInput);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					result = new Compagnia();
					result.setId(rs.getLong("id"));
					result.setRagioneSociale(rs.getString("ragionesociale"));
					result.setFatturatoAnnuo(rs.getInt("fatturatoannuo"));
					result.setDataFondazione(rs.getDate("datafondazione"));
				} else {
					result = null;
				}
			} // niente catch qui

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public int update(Compagnia input) throws Exception {
		// prima di tutto cerchiamo di capire se possiamo effettuare le operazioni
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (input == null || input.getId() == null || input.getId() < 1)
			throw new Exception("Valore di input non ammesso.");

		int result = 0;
		try (PreparedStatement ps = connection.prepareStatement(
				"UPDATE compagnia SET ragionesociale=?, fatturatoannuo=?, datafondazione=? where id=?;")) {
			ps.setString(1, input.getRagioneSociale());
			ps.setInt(2, input.getFatturatoAnnuo());
			ps.setDate(3, new java.sql.Date(input.getDataFondazione().getTime()));
			ps.setLong(4, input.getId());
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public int insert(Compagnia input) throws Exception {
		// prima di tutto cerchiamo di capire se possiamo effettuare le operazioni
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (input == null)
			throw new Exception("Valore di input non ammesso.");

		int result = 0;
		try (PreparedStatement ps = connection.prepareStatement(
				"INSERT INTO compagnia (ragionesociale, fatturatoannuo, datafondazione) VALUES (?, ?, ?);")) {
			ps.setString(1, input.getRagioneSociale());
			ps.setInt(2, input.getFatturatoAnnuo());
			ps.setDate(3, new java.sql.Date(input.getDataFondazione().getTime()));
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public int delete(Compagnia input) throws Exception {
		// prima di tutto cerchiamo di capire se possiamo effettuare le operazioni
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (input == null || input.getId() == null || input.getId() < 1)
			throw new Exception("Valore di input non ammesso.");

		int result = 0;
		try (PreparedStatement ps = connection.prepareStatement("DELETE FROM compagnia c WHERE c.ID=?")) {
			ps.setLong(1, input.getId());
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public int deleteAll() throws Exception {
		// prima di tutto cerchiamo di capire se possiamo effettuare le operazioni
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		int result = 0;
		try (Statement s = connection.createStatement()) {
			result = s.executeUpdate("DELETE FROM compagnia;");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public List<Compagnia> findAllByDataAssunzioneMaggioreDi(java.util.Date data) throws Exception {
		// prima di tutto cerchiamo di capire se possiamo effettuare le operazioni
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (data == null)
			throw new Exception("Valore di input non ammesso.");

		Compagnia tempCompagnia = null;
		Impiegato tempImpiegato = null;
		List<Compagnia> result = new ArrayList<>();
		try (PreparedStatement ps = connection.prepareStatement(
				"select * from compagnia c join impiegato i on c.id=i.compagnia_id where i.dataassunzione > ?")) {

			ps.setDate(1, new java.sql.Date(data.getTime()));
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {

					tempCompagnia = new Compagnia();
					tempCompagnia.setId(rs.getLong("c.id"));
					tempCompagnia.setRagioneSociale(rs.getString("c.ragionesociale"));
					tempCompagnia.setFatturatoAnnuo(rs.getInt("c.fatturatoannuo"));
					tempCompagnia.setDataFondazione(rs.getDate("c.datafondazione"));

					tempImpiegato = new Impiegato();
					tempImpiegato.setId(rs.getLong("i.id"));
					tempImpiegato.setNome(rs.getString("i.nome"));
					tempImpiegato.setCognome(rs.getString("i.cognome"));
					tempImpiegato.setCodiceFiscale(rs.getString("i.codicefiscale"));
					tempImpiegato.setDataNascita(rs.getDate("i.datanascita"));
					tempImpiegato.setDataAssunzione(rs.getDate("i.dataassunzione"));
					tempImpiegato.setCompagnia(tempCompagnia);

					result.add(tempCompagnia);

				}
			} // niente catch qui

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public List<Compagnia> findAllByRagioneSocialeContiene(String input) throws Exception {
		// prima di tutto cerchiamo di capire se possiamo effettuare le operazioni
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (input == null)
			throw new Exception("Valore di input non ammesso.");

		Compagnia temp = null;
		List<Compagnia> result = new ArrayList<>();
		try (PreparedStatement ps = connection
				.prepareStatement("select * from compagnia where ragionesociale like ?")) {

			ps.setString(1, input + "%");
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					temp = new Compagnia();
					temp.setId(rs.getLong("id"));
					temp.setRagioneSociale(rs.getString("ragionesociale"));
					temp.setFatturatoAnnuo(rs.getInt("fatturatoannuo"));
					temp.setDataFondazione(rs.getDate("datafondazione"));
					result.add(temp);
				}
			} // niente catch qui

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

}
