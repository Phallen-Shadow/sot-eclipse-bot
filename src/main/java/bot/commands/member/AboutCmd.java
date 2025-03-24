package bot.commands.member;

import bot.Bot;
import bot.commands.MemberCommand;
import bot.util.Messages;
import com.jagrosh.jdautilities.command.SlashCommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.util.Scanner;

public class AboutCmd extends MemberCommand {


    public AboutCmd(Bot bot) {
        super(bot);
        this.name = "about";
        this.help = "Sends information about the bot";
        this.category = new Category("General");
    }

    @Override
    public void doCommand(SlashCommandEvent event) {
        Runtime runtime = Runtime.getRuntime();
        OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
        User user = event.getJDA().getUserById(event.getClient().getOwnerIdLong()); //Owner should always be cached.
        EmbedBuilder eb = Messages.Embeds.getEmbedBase();
        eb.setTitle("About " + event.getJDA().getSelfUser().getName());
        eb.setDescription(event.getJDA().getSelfUser().getName() + " is a bot made for accessing Sea of Thieves timing utilities made by " +
                user.getAsMention() + "\n\n Based on [this](https://docs.google.com/spreadsheets/d/16_PB9alwNZt7CowvU5MowXe7l__RAYNgO0hDYzZ-HqY/edit?gid=2028923469#gid=2028923469) Google Sheet by Wazagoat, Swabbie John, JimboLoL, Owiiie, & Lukitch.");
        StringBuilder tech = new StringBuilder();
        tech.append("**Build Date:** ").append(new Scanner(getClass().getClassLoader().getResourceAsStream("build-date.txt")).nextLine() + "\n");
        tech.append("**JDA Version:** ").append("v5.0.0-beta.17\n");
        eb.addField("Technical Information", tech.toString(), false);

        StringBuilder cache = new StringBuilder();
        cache.append("**Cached Guilds:** ").append(event.getJDA().getGuilds().size()).append("\n");
        cache.append("**Cached Channels:** ").append(event.getJDA().getTextChannels().size()).append("\n");
        cache.append("**Cached Members:** ").append(event.getJDA().getUsers().size()).append("\n");
        cache.append("**Cached Commands:** ").append(event.getClient().getSlashCommands().size()).append("\n");
//        cache.append("**Cached Messages:** ").append(bot.getMessageCache().size()).append("\n");
        eb.addField("Technical Information", cache.toString(), false);
        StringBuilder sys = new StringBuilder();
        sys.append("**Total Memory:** " + formatBytes(runtime.totalMemory()) + " (" + runtime.totalMemory() + " B)\n");
        sys.append("**Free Memory:** " + formatBytes(runtime.freeMemory()) + " (" + runtime.freeMemory() + " B)\n");
        sys.append("**Used Memory:** " + formatBytes(runtime.totalMemory() - runtime.freeMemory()) + " (" + (runtime.totalMemory() - runtime.freeMemory()) + " B)\n");
        int availableProcessors = osBean.getAvailableProcessors();
        sys.append("**Processors:** " + availableProcessors + "\n");
//        sys.append("**CPU Usage:** " + (osBean.getSystemLoadAverage() / availableProcessors * 100.0) + "\n"); //Doesn't work
        eb.addField("System Information", sys.toString(), false);
        event.reply(MessageCreateData.fromEmbeds(eb.build())).queue();
    }

    private String formatBytes(long bytes){
        String formattedMemory;
        if (bytes >= 1L * 1024L * 1024L * 1024L) {
            formattedMemory = String.format("%.2f GB", (double) bytes / (1024L * 1024L * 1024L));
        } else if (bytes >= 1L * 1024L * 1024L) {
            formattedMemory = String.format("%.2f MB", (double) bytes / (1024L * 1024L));
        } else if (bytes >= 1L * 1024L) {
            formattedMemory = String.format("%.2f KB", (double) bytes / 1024L);
        } else {
            formattedMemory = bytes + " bytes";
        }
        return formattedMemory;
    }
}
