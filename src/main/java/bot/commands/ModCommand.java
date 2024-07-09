package bot.commands;

import bot.Bot;
import net.dv8tion.jda.api.Permission;

public abstract class ModCommand extends GenericCommand {
    public ModCommand(Bot bot) {
        super(bot);
        this.userPermissions = new Permission[]{Permission.MESSAGE_MANAGE};
        this.category = new Category("Moderation", p -> {
//           if(p.getMember().getIdLong() == Config.getInstance().getOwner()) return true;
           if(p.getMember().hasPermission(Permission.ADMINISTRATOR)) return true;
           if(p.getMember().hasPermission(Permission.MESSAGE_MANAGE)) return true;
           return false;
        });
    }
}
