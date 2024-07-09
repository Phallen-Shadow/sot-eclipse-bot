package bot.commands.member;

import SoTEclipse.Eclipse;
import bot.Bot;
import bot.commands.MemberCommand;
import com.jagrosh.jdautilities.command.SlashCommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class EclipseCmd extends MemberCommand {
    public EclipseCmd(Bot bot) {
        super(bot);
        this.name = "eclipse";
        this.help = "Prints the times of the next eclipse cycle";
        this.category = new Category("SoT Utils");
    }

    @Override
    public void doCommand(SlashCommandEvent event) {
        String content = "";
        int i = 0;
        for(long e : new Eclipse().getNextEclipse()) {
            e = e/1000;
            content += "``"+(++i)+".``<t:" + e + ":F> (<t:"+e+":R>)\n";
        }
        event.replyEmbeds(new EmbedBuilder().setDescription(content).build()).queue();
    }
}
