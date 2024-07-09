package bot;

import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

public class Listener implements EventListener {

    public final static Logger logger = Bot.getLogger("Event Listener");

    private Bot bot;

    public Listener(Bot bot){
        this.bot = bot;
    }

    @Override
    public void onEvent(@NotNull GenericEvent event) {
        if(event instanceof ButtonInteractionEvent e){
            logger.info("Button pressed {}", e.getButton().getId());
        }
    }
}
