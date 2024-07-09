package bot.storage;


import bot.storage.managers.EclipseHistoryManager;
import database.DatabaseConnector;

public class Database extends DatabaseConnector
{
//    public final MegManager megs;
    public EclipseHistoryManager eHistory;

    public Database(String host, String user, String pass) throws Exception
    {
        super(host, user, pass);
//        megs = new MegManager(this);
        eHistory = new EclipseHistoryManager(this);
        init();
    }
}
