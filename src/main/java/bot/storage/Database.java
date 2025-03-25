package bot.storage;


import bot.storage.managers.EclipseAlertsChannelManager;
import database.DatabaseConnector;

public class Database extends DatabaseConnector
{
//    public final MegManager megs;
    public EclipseAlertsChannelManager eclipseAlerts;

    public Database(String host, String user, String pass) throws Exception
    {
        super(host, user, pass);
//        megs = new MegManager(this);
        eclipseAlerts = new EclipseAlertsChannelManager(this);
        init();
    }
}
