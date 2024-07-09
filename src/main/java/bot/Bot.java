package bot;

import SoTEclipse.Eclipse;
import bot.commands.member.AboutCmd;
import bot.commands.member.EclipseCmd;
import bot.commands.member.HelpCmd;
import bot.commands.member.NowCmd;
import ch.qos.logback.classic.Level;
import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

import java.io.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.internal.utils.JDALogger;
import org.slf4j.Logger;


public class Bot {
    private static Bot bot;
    private static JDA jda;
    private final EventWaiter waiter;
    private final ScheduledExecutorService threadpool;

    private static Logger log = getLogger();

    public static void main(String[] args) throws InterruptedException, IOException {
        if(Arrays.toString(args).contains("--debug")) {
            ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger) org.slf4j.LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
            root.setLevel(Level.DEBUG);
        }
        new Bot();
    }

    public Bot() throws InterruptedException, IOException {
        log.info("Initializing bot instance...");
        bot = this;
        log.info("Initializing thread executors...");
        waiter = new EventWaiter(Executors.newSingleThreadScheduledExecutor(r -> new Thread(r, "waiter")), false);
        threadpool = Executors.newScheduledThreadPool(100, r -> new Thread(r, "bot"));

        log.info("Building command client...");
        CommandClientBuilder builder = new CommandClientBuilder();
        builder.setOwnerId(242374512644063233L);
        builder.setActivity(Activity.playing("SoT Eclipse Bot is starting..."));
        builder.setLinkedCacheSize(0);
        builder.setScheduleExecutor(threadpool);
        builder.setShutdownAutomatically(false);

        log.info("Adding slash commands...");
        builder.addSlashCommand(new AboutCmd(this));
        builder.addSlashCommand(new HelpCmd(this));
        builder.addSlashCommand(new NowCmd(this));
        builder.addSlashCommand(new EclipseCmd(this));

        CommandClient commandClient = builder.build();

        log.info("Building JDA instance...");
        try {
            jda = JDABuilder.createDefault(readFile("token.txt"))
                    .enableIntents(GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MEMBERS)
                    .setStatus(OnlineStatus.IDLE)
                    .setMemberCachePolicy(MemberCachePolicy.ALL)
                    .addEventListeners(
                            new Listener(this),
                            commandClient,
                            waiter
                    ).build();
        }catch (FileNotFoundException ex){
            File file = new File("token.txt");
            if(file.createNewFile()){
                log.error("Please supply a token in " + file.getAbsolutePath());
                System.exit(10);
            }
        }

        jda.awaitReady();

        jda.getPresence().setActivity(Activity.watching("Eclipses"));

        System.out.println("Bot started successfully!"); //Should signal started to pterodactyl


        List<Long> ll = new ArrayList<>();
        threadpool.scheduleAtFixedRate(() -> {
            new Eclipse().getNextEclipse().forEach(e -> {
                if(e - TimeUnit.HOURS.toMillis(2) > new Date().getTime()){
                    if(!ll.contains(e)){

                    }
                }
//                System.out.println(DateTimeFormatter.ofPattern("yyyy/MMMM/dd HH:mm:ss z").withZone(ZoneId.systemDefault()).format(new Date(e).toInstant()));
            });
        }, (60 - LocalDateTime.now().getSecond()) % 60, 60, TimeUnit.SECONDS);
    }

    public static Bot getBot() {
        return bot;
    }

    public EventWaiter getWaiter() {
        return waiter;
    }

    public static JDA getJda() {
        return jda;
    }

    public static org.slf4j.Logger getLogger(){
        return JDALogger.getLog(getCallerClassName());
    }

    public static org.slf4j.Logger getLogger(String className){
        return JDALogger.getLog(className);
    }


    private static String getCallerClassName() {
        StackTraceElement[] stElements = Thread.currentThread().getStackTrace();
        for (int i=1; i<stElements.length; i++) {
            StackTraceElement ste = stElements[i];
            if (!ste.getClassName().equals(Logger.class.getName()) && ste.getClassName().indexOf("java.lang.Thread")!=0) {
                return ste.getClassName();
            }
        }
        return null;
    }

    private static String readFile(String fileName) throws FileNotFoundException {
        String output = "";
        try (FileInputStream fis = new FileInputStream(new File(fileName))) {
            int content;
            while ((content = fis.read()) != -1) {
                output += (char)content;
            }
        } catch (FileNotFoundException e){
            throw new FileNotFoundException(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return output;
    }
}