package bot.commands.admin;

import bot.Bot;
import bot.commands.AdminCommand;
import bot.util.Messages;
import com.jagrosh.jdautilities.command.SlashCommand;
import com.jagrosh.jdautilities.command.SlashCommandEvent;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.ArrayList;
import java.util.List;


public class EclipseAlertsCmd extends AdminCommand {

    public EclipseAlertsCmd(Bot bot) {
        super(bot);
        this.name = "eclipse-alerts";
        this.help = "Eclipse alert control command";
        this.category = new Category("Administration");
        this.children = new SlashCommand[]{new EclipseAlertsAddCmd(bot), new EclipseAlertsRemoveCmd(bot)};
    }

    @Override
    public void doCommand(SlashCommandEvent event) {

    }

    private static class EclipseAlertsAddCmd extends AdminCommand {

        public EclipseAlertsAddCmd(Bot bot) {
            super(bot);
            this.name = "add";
            List<OptionData> options = new ArrayList<>();
            options.add(new OptionData(OptionType.CHANNEL, "channel", "Channel to send a message to before and during the eclipse.", true));
            this.options = options;
        }

        @Override
        public void doCommand(SlashCommandEvent event) {
            if(event.hasOption("channel")){
                try {
                    TextChannel tc = event.getOption("channel").getAsChannel().asTextChannel();
                    if(Bot.getBot().getDatabase().eclipseAlerts.addChannel(tc.getGuild().getIdLong(), tc.getIdLong())) {
                        event.replyEmbeds(Messages.Embeds.success("Added " + tc.getAsMention() + " to eclipse alerts.")).setEphemeral(true).queue();
                    }else{
                        event.replyEmbeds(Messages.Embeds.error("Unable to add duplicate channel.")).setEphemeral(true).queue();
                    }
                }catch (NullPointerException npe){
                    event.replyEmbeds(Messages.Embeds.error("Unable to add channel to eclipse alerts. Do I have access to see the channel?")).setEphemeral(true).queue();
                }
            }
        }
    }

    private static class EclipseAlertsRemoveCmd extends AdminCommand {

        public EclipseAlertsRemoveCmd(Bot bot) {
            super(bot);
            this.name = "remove";
            options.add(new OptionData(OptionType.CHANNEL, "channel", "Channel to send a message to before and during the eclipse.", true));
            this.options = options;
        }

        @Override
        public void doCommand(SlashCommandEvent event) {
            if (event.hasOption("channel")) {
                try {
                    TextChannel tc = event.getOption("channel").getAsChannel().asTextChannel();
                    if (Bot.getBot().getDatabase().eclipseAlerts.removeChannel(tc.getGuild().getIdLong(), tc.getIdLong())) {
                        event.replyEmbeds(Messages.Embeds.success("Removed " + tc.getAsMention() + " to eclipse alerts.")).setEphemeral(true).queue();
                    }else{
                        event.replyEmbeds(Messages.Embeds.error("Channel is not currently subscribed to eclipse alerts.")).setEphemeral(true).queue();
                    }
                } catch (NullPointerException npe) {
                    event.replyEmbeds(Messages.Embeds.error("Unable to remove channel to eclipse alerts. Is the channel receiving alerts?")).setEphemeral(true).queue();
                }
            }
        }
    }
}
