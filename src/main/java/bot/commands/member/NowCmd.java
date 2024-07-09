package bot.commands.member;

import bot.Bot;
import bot.commands.MemberCommand;
import bot.util.Messages;
import com.jagrosh.jdautilities.command.SlashCommandEvent;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class NowCmd extends MemberCommand {
    public NowCmd(Bot bot) {
        super(bot);
        this.name = "now";
        this.help = "Gets the current epoch second for formatting.";
        this.category = new Category("General");
    }

    @Override
    public void doCommand(SlashCommandEvent event) {
        long now = new Date().getTime() / 1000;
        event.replyEmbeds(Messages.Embeds.info("It is currently <t:"+now+":F>\nepoch second: " + now + ".\n\nFormats:\n" +
                "> `<t:" + now + ":F>`" + "<t:" + now + ":F>\n" +
                "> `<t:" + now + ":f>`" + "<t:" + now + ":f>\n" +
                "> `<t:" + now + ":D>`" + "<t:" + now + ":D>\n" +
                "> `<t:" + now + ":d>`" + "<t:" + now + ":d>\n" +
                "> `<t:" + now + ":T>`" + "<t:" + now + ":T>\n" +
                "> `<t:" + now + ":t>`" + "<t:" + now + ":t>\n" +
                "> `<t:" + now + ":R>`" + "<t:" + now + ":R>\n" +
                "")).queue(msg -> {
                    try {
                        msg.deleteOriginal().queueAfter(10, TimeUnit.MINUTES);
                    }catch (Exception ignored){}
        });
    }
}
