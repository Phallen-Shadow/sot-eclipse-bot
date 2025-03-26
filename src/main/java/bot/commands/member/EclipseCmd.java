package bot.commands.member;

import SoTEclipse.Eclipse;
import bot.Bot;
import bot.commands.MemberCommand;
import com.jagrosh.jdautilities.command.SlashCommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EclipseCmd extends MemberCommand {
    public EclipseCmd(Bot bot) {
        super(bot);
        this.name = "eclipse";
        this.help = "Prints the times of the next eclipse cycle";
        this.category = new Category("SoT Utils");

        List<OptionData> options = new ArrayList<>();
        options.add(new OptionData(OptionType.INTEGER, "cycles", "Cycles to go forwards (between 1 and 10)", false).setMinValue(1).setMaxValue(10));
        this.options = options;
    }

    @Override
    public void doCommand(SlashCommandEvent event) {
        String content = "";
        int cycles = 1;
        if(event.hasOption("cycles")) {
            cycles = event.getOption("cycles").getAsInt();
        }
        int i = 0;
        for(long e : Eclipse.getNextEclipse(cycles)) {
            if(i % 4 == 0) content += "\n";
            e = e/1000;
            content += "``"+(++i)+".`` <t:" + e + ":F> (<t:"+e+":R>)\n";
        }
        event.replyEmbeds(new EmbedBuilder().setDescription(content).build()).queue();
    }
}
