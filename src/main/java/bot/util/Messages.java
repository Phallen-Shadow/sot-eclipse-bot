package bot.util;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.Instant;

public class Messages {
    public static final String emoteInfo = ":information_source:";
    public static final String emoteSearching = ":mag:";
    public static final String emoteLoading = ":mag_right:";
    public static final String emoteSuccess = ":white_check_mark:";
    public static final String emoteWarn = ":warning:";
    public static final String emoteFail = ":x:";
    public static final Color colorInfo = new Color(64, 128, 255);
    public static final Color colorSearching = new Color(128, 128, 128);
    public static final Color colorLoading = new Color(128, 128, 128);
    public static final Color colorSuccess = new Color(67, 181, 129);
    public static final Color colorWarn = new Color(255, 200, 20);
    public static final Color colorFail = new Color(240, 73, 71);

    public static class Embeds {
        public static EmbedBuilder getEmbedBase() {
            EmbedBuilder eb = new EmbedBuilder();
            eb.setColor(new Color(217, 195, 27));
//            eb.setThumbnail(Constants.LOGO);
            return eb;
        }

        public static EmbedBuilder getEmbedBase(boolean thumbnail) {
            EmbedBuilder eb = new EmbedBuilder();
            eb.setColor(new Color(217, 195, 27));
//            if(thumbnail) eb.setThumbnail(Constants.LOGO);
            return eb;
        }

        public static MessageEmbed info(String description) {
            EmbedBuilder eb = getEmbedBase();
            eb.setColor(colorInfo);
            eb.setDescription(emoteInfo + " " + description);
            return eb.build();
        }
        public static MessageEmbed info(String description, boolean thumbnail) {
            EmbedBuilder eb = getEmbedBase(thumbnail);
            eb.setColor(colorInfo);
            eb.setDescription(emoteInfo + " " + description);
            return eb.build();
        }

        public static MessageEmbed info(String title, String description, boolean timestamp) {
            EmbedBuilder eb = getEmbedBase();
            if (!title.trim().equals("")) {
                eb.setTitle(title);
            }
            eb.setColor(colorInfo);
            eb.setDescription(emoteInfo + " " + description);
            if (timestamp) {
                eb.setTimestamp(Instant.now());
            }

            return eb.build();
        }

        public static MessageEmbed info(String title, String description, String footer, boolean timestamp) {
            EmbedBuilder eb = getEmbedBase();
            if (title != null && !title.trim().equals("")) {
                eb.setTitle(title);
            }
            eb.setColor(colorInfo);
            eb.setDescription(emoteInfo + " " + description);
            if (timestamp) {
                eb.setTimestamp(Instant.now());
            }
            eb.setFooter(footer);

            return eb.build();
        }

        public static MessageEmbed info(String title, String description, String footer, boolean timestamp, boolean thumbnail) {
            EmbedBuilder eb = getEmbedBase(thumbnail);
            if (title != null && !title.trim().equals("")) {
                eb.setTitle(title);
            }
            eb.setColor(colorInfo);
            eb.setDescription(emoteInfo + " " + description);
            if (timestamp) {
                eb.setTimestamp(Instant.now());
            }
            eb.setFooter(footer);

            return eb.build();
        }

        public static MessageEmbed searching(String description) {
            EmbedBuilder eb = getEmbedBase(false);
            eb.setColor(colorSearching);
            eb.setDescription(emoteSearching + " " + description);

            return eb.build();
        }

        public static MessageEmbed searching(String description, boolean thumbnail) {
            EmbedBuilder eb = getEmbedBase(thumbnail);
            eb.setColor(colorSearching);
            eb.setDescription(emoteSearching + " " + description);

            return eb.build();
        }

        public static MessageEmbed searching(String title, String description, boolean timestamp) {
            EmbedBuilder eb = getEmbedBase(false);
            if (!title.trim().equals("")) {
                eb.setTitle(title);
            }
            eb.setColor(colorSearching);
            eb.setDescription(emoteSearching + " " + description);
            if (timestamp) {
                eb.setTimestamp(Instant.now());
            }

            return eb.build();
        }

        public static MessageEmbed loading(String description) {
            EmbedBuilder eb = getEmbedBase(false);
            eb.setColor(colorLoading);
            eb.setDescription(emoteLoading + " " + description);

            return eb.build();
        }

        public static MessageEmbed loading(String description, boolean thumbnail) {
            EmbedBuilder eb = getEmbedBase(thumbnail);
            eb.setColor(colorLoading);
            eb.setDescription(emoteLoading + " " + description);

            return eb.build();
        }

        public static MessageEmbed loading(String title, String description, boolean timestamp) {
            EmbedBuilder eb = getEmbedBase(false);
            if (!title.trim().equals("")) {
                eb.setTitle(title);
            }
            eb.setColor(colorLoading);
            eb.setDescription(emoteLoading + " " + description);
            if (timestamp) {
                eb.setTimestamp(Instant.now());
            }

            return eb.build();
        }

        public static EmbedBuilder success() {
            EmbedBuilder eb = getEmbedBase(false);
            eb.setColor(colorSuccess);
            eb.setDescription(emoteSuccess);
            return eb;
        }
        public static MessageEmbed success(String description) {
            EmbedBuilder eb = getEmbedBase(false);
            eb.setColor(colorSuccess);
            eb.setDescription(emoteSuccess + " " + description);

            return eb.build();
        }
        public static MessageEmbed success(String description, boolean thumbnail) {
            EmbedBuilder eb = getEmbedBase(thumbnail);
            eb.setColor(colorSuccess);
            eb.setDescription(emoteSuccess + " " + description);

            return eb.build();
        }

        public static MessageEmbed success(String title, String description, boolean timestamp) {
            EmbedBuilder eb = getEmbedBase(false);
            if (!title.trim().equals("")) {
                eb.setTitle(title);
            }
            eb.setColor(colorSuccess);
            eb.setDescription(emoteSuccess + " " + description);
            if (timestamp) {
                eb.setTimestamp(Instant.now());
            }

            return eb.build();
        }

        public static MessageEmbed warning(String description) {
            EmbedBuilder eb = getEmbedBase(false);
            eb.setColor(colorWarn);
            eb.setDescription(emoteWarn + " " + description);

            return eb.build();
        }
        public static MessageEmbed warning(String description, boolean thumbnail) {
            EmbedBuilder eb = getEmbedBase(thumbnail);
            eb.setColor(colorWarn);
            eb.setDescription(emoteWarn + " " + description);

            return eb.build();
        }

        public static MessageEmbed warning(String title, String description, boolean timestamp) {
            EmbedBuilder eb = getEmbedBase(false);
            if (!title.trim().equals("")) {
                eb.setTitle(title);
            }
            eb.setColor(colorWarn);
            eb.setDescription(emoteWarn + " " + description);
            if (timestamp) {
                eb.setTimestamp(Instant.now());
            }

            return eb.build();
        }

        public static MessageEmbed error(String description) {
            EmbedBuilder eb = getEmbedBase(false);
            eb.setColor(colorFail);
            eb.setDescription(emoteFail + " " + description);

            return eb.build();
        }

        public static MessageEmbed error(Throwable throwable) {
            StringWriter sw = new StringWriter();
            throwable.printStackTrace(new PrintWriter(sw));
            String exceptionAsString = sw.toString();
            EmbedBuilder eb = getEmbedBase(false);
            eb.setColor(colorFail);
            eb.setDescription(emoteFail + " " + exceptionAsString.substring(0, exceptionAsString.indexOf("\n")).replace("\n", "").substring(0, Math.min(exceptionAsString.indexOf("\n")-1, 2000)));

            return eb.build();
        }

        public static MessageEmbed error(String description, boolean thumbnail) {
            EmbedBuilder eb = getEmbedBase(thumbnail);
            eb.setColor(colorFail);
            eb.setDescription(emoteFail + " " + description);

            return eb.build();
        }

        public static MessageEmbed error(String title, String description, boolean timestamp) {
            EmbedBuilder eb = getEmbedBase(false);
            if (!title.trim().equals("")) {
                eb.setTitle(title);
            }
            eb.setColor(colorFail);
            eb.setDescription(emoteFail + " " + description);
            if (timestamp) {
                eb.setTimestamp(Instant.now());
            }

            return eb.build();
        }

        public static MessageEmbed getNotYetImplementedMessage(String cmd) {
            EmbedBuilder eb = getEmbedBase(false);
            eb.setColor(colorFail);
            eb.setDescription(emoteFail + " " + cmd + " has not been implemented yet.");
            return eb.build();
        }


    }
}
