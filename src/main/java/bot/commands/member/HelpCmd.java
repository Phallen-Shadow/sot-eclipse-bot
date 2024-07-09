package bot.commands.member;

import bot.Bot;
import bot.commands.MemberCommand;
import bot.util.Messages;
import com.jagrosh.jdautilities.command.SlashCommand;
import com.jagrosh.jdautilities.command.SlashCommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HelpCmd extends MemberCommand {
    public HelpCmd(Bot bot){
        super(bot);
        this.name = "help";
        this.help = "Displays the help embed which you are currently looking at.";
        this.category = new Category("General");
    }

    @Override
    public void doCommand(SlashCommandEvent event) {
        StringBuilder sb = new StringBuilder();
        EmbedBuilder eb = Messages.Embeds.getEmbedBase();
        eb.setTitle("**" + event.getJDA().getSelfUser().getName() + "** commands:\n");

        HashMap<Category, List<SlashCommand>> categoryCommands = new HashMap<>();
        for(SlashCommand cmd : event.getClient().getSlashCommands()) {
            var cat = cmd.getCategory() == null ? new Category("No Category") : cmd.getCategory();
            if (cmd.getUserPermissions().length == 0 || event.getMember().hasPermission(cmd.getUserPermissions())) {
                if (categoryCommands.get(cat) == null) {
                    categoryCommands.put(cat, new ArrayList<>(List.of(cmd)));
//                categoryCommands.entrySet().forEach(System.out::println);
                } else {
                    var catCmds = categoryCommands.get(cat);
                    catCmds.add(cmd);
                    categoryCommands.put(cat, catCmds);
//                categoryCommands.entrySet().forEach(System.out::println);
                }
            }
        }

        for(Map.Entry<Category, List<SlashCommand>> entry : categoryCommands.entrySet()) {
            sb.append("\n\n  __").append(entry.getKey().getName()).append("__:\n");
            for (SlashCommand cmd : entry.getValue()) {
                if (!cmd.isHidden() && !cmd.isOwnerCommand()) {
                    sb.append("\n`/").append(cmd.getName())
                            .append(cmd.getOptions() == null || cmd.getOptions().size() == 0 ? "" : cmd.getOptions().stream().map(OptionData::getName).collect(Collectors.joining("] [", " [", "]")))
                            .append("`")
                            .append(" - ")
                            .append(cmd.getHelp());
                }
            }
        }
//            if(!cmd.isHidden() && !cmd.isOwnerCommand()){
//
//                if(cmd.getCategory() == null){
//                    sb.append("\n\n  __").append("No Category").append("__:\n");
//                }else {
//                    sb.append("\n\n  __").append(cmd.getCategory().getName()).append("__:\n");
//                }
//                sb.append("\n`/").append(cmd.getName())
//                    .append(cmd.getOptions() == null || cmd.getOptions().size() == 0 ? "" : cmd.getOptions().stream().map(OptionData::getName).collect(Collectors.joining("], [", "[", "]")))
//                    .append("`")
//                    .append(" - ")
//                    .append(cmd.getHelp());
//            }
//        }

        eb.setDescription(sb.toString());
        eb.setFooter("Requested by: " + event.getUser().getName());
        event.reply(MessageCreateData.fromEmbeds(eb.build())).queue();
    }


}
