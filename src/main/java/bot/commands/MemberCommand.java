package bot.commands;

import bot.Bot;

public abstract class MemberCommand extends GenericCommand {

    public MemberCommand(Bot bot){
        super(bot);
        this.category = new Category("General");
    }
}
