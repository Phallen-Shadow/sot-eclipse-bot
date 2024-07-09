package bot.commands;

import bot.Bot;
import com.jagrosh.jdautilities.command.SlashCommand;
import com.jagrosh.jdautilities.command.SlashCommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import org.slf4j.Logger;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.concurrent.TimeUnit;

public abstract class GenericCommand extends SlashCommand {

    protected static Logger logger = Bot.getLogger("Command Listener");
    protected final Bot bot;
    protected boolean notYetImplemented = false;

    public GenericCommand(Bot bot){
        this.bot = bot;
        this.category = new Category("General", p -> {
//            if(p.getMember().getIdLong() == Config.getInstance().getOwner()) return true;
            if(p.getMember().hasPermission(Permission.ADMINISTRATOR)) return true;
            return true;
        });
    }

    @Override
    protected void execute(SlashCommandEvent event) {
        logger.info("{} executed command {}", event.getUser().getName(), this.getName());
        try {
            if(!notYetImplemented || (event.getUser().getIdLong() == 242374512644063233L)) doCommand(event);
            else event.reply(event.getFullCommandName() + " has not been implemented yet.").setEphemeral(true).queue();
        }catch (Exception ex) {
            StringWriter sw = new StringWriter();
            ex.printStackTrace(new PrintWriter(sw));
            String exceptionAsString = sw.toString();
            logger.error("Failed to dispatch command {} for user {}: {}", this.getName(), event.getUser().getName(), ex.getMessage());
            logger.debug("Suppressed Error: {}", exceptionAsString);
//            event.replyEmbeds(new EmbedBuilder(Messages.Embeds.error(
//                    event.getUser().getIdLong()==Config.getInstance().getOwner() ? (exceptionAsString.length() > 2000 ? exceptionAsString.substring(0, 2000) + "\n`[ +"+ (exceptionAsString.length() - 2000) +" characters ]`" : exceptionAsString)
//                            : exceptionAsString.substring(0, exceptionAsString.indexOf("\n")).replace("\n", "").substring(0, Math.min(exceptionAsString.indexOf("\n")-1, 2000)))).setFooter("This error has been logged").build())
//                    .setEphemeral(true).queue(msg -> {
//                try {
//                    msg.deleteOriginal().queueAfter(5, TimeUnit.MINUTES);
//                } catch (Exception ex2) {
//                    logger.warn("Message with ID " + msg.getInteraction().getIdLong() + " sent by " +
//                            msg.getInteraction().getUser().getName() + "#" + msg.getInteraction().getUser().getDiscriminator() + " in channel " +
//                            msg.getInteraction().getMessageChannel().getName() + " in guild " + msg.getInteraction().getGuild().getName() +
//                            " has already been deleted.");
//                }
//            });
        }
    }

    public abstract void doCommand(SlashCommandEvent event);
}
