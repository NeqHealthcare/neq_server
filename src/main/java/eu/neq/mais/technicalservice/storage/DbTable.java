package eu.neq.mais.technicalservice.storage;

import org.tmatesoft.sqljet.core.table.ISqlJetCursor;
import org.tmatesoft.sqljet.core.table.SqlJetDb;

public interface DbTable {

	static String TABLE_NAME = "CHANGE_TO_TABLE_NAME";
	public void initialize(SqlJetDb db);
	public void read(ISqlJetCursor cursor);
}
