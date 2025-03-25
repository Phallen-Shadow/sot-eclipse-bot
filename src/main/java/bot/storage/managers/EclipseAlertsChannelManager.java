package bot.storage.managers;

import database.DataManager;
import database.DatabaseConnector;
import database.SQLColumn;
import database.columns.LongColumn;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public class EclipseAlertsChannelManager extends DataManager {

    public final static SQLColumn<Long> GUILD_ID = new LongColumn("GUILD_ID", false, -1);
    public final static SQLColumn<Long> CHANNEL_ID = new LongColumn("CHANNEL_ID", false, -1);

    public EclipseAlertsChannelManager(DatabaseConnector connector) {
        super(connector, "ECLIPSE_ALERT_CHANNELS_SUBSCRIBED");
    }

    @Override
    protected String primaryKey() {
        return GUILD_ID+"";
    }

    public boolean addChannel(long guild, long channel){
        return readWrite(selectAll(GUILD_ID.is(guild) + " AND " + CHANNEL_ID.is(channel)), rs -> {
           if(!rs.next()){
               rs.moveToInsertRow();
               GUILD_ID.updateValue(rs, guild);
               CHANNEL_ID.updateValue(rs, channel);
               rs.insertRow();
               return true;
           }
           return false;
        });
    }

    public boolean addChannel(Guild guild, TextChannel channel){
        return addChannel(guild.getIdLong(), channel.getIdLong());
    }

    public boolean removeChannel(long guild, long channel){
        return readWrite(selectAll(GUILD_ID.is(guild) + " AND " + CHANNEL_ID.is(channel)), rs -> {
            if(rs.next()){
                rs.deleteRow();
                return true;
            }
            return false;
        });
    }

    public boolean removeChannel(Guild guild, TextChannel channel){
        return removeChannel(guild.getIdLong(), channel.getIdLong());
    }

    public void preformAll(BiConsumer<Long, Long> c){
        readWrite(selectAll(), rs -> {
            while(rs.next()){
                c.accept(GUILD_ID.getValue(rs), CHANNEL_ID.getValue(rs));
            }
        });
    }
}
