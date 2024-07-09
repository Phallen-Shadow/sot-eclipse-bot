package bot.storage.managers;

import database.DataManager;
import database.DatabaseConnector;
import database.SQLColumn;
import database.columns.LongColumn;

public class EclipseHistoryManager extends DataManager {

    public final static SQLColumn<Long> MILLI = new LongColumn("MILLI", false, -1);

    public EclipseHistoryManager(DatabaseConnector connector) {
        super(connector, "EclipseHistory");
    }

    @Override
    protected String primaryKey() {
        return MILLI+"";
    }

    public boolean addMilli(long l){
        return readWrite(selectAll(MILLI.is(l)), rs -> {
           if(!rs.next()){
               rs.moveToInsertRow();
               MILLI.updateValue(rs, l);
               rs.insertRow();
               return true;
           }
           return false;
        });
    }
}
