package bot.commands;

import bot.Bot;
import net.dv8tion.jda.api.Permission;

public abstract class AdminCommand extends GenericCommand {
    public AdminCommand(Bot bot) {
        super(bot);
        this.userPermissions = new Permission[]{Permission.ADMINISTRATOR};
        this.category = new Category("Administration", p -> {
//            if(p.getMember().getIdLong() == Config.getInstance().getOwner()) return true;
            if(p.getMember().hasPermission(Permission.ADMINISTRATOR)) return true;
            return false;
        });
    }
}
