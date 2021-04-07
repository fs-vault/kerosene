package com.firestartermc.kerosene.util.webhook;

import com.firestartermc.kerosene.Kerosene;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import javax.net.ssl.HttpsURLConnection;
import java.awt.*;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.*;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class DiscordWebhook {

    private static final Queue<DiscordWebhook> WEBHOOK_QUEUE = new ConcurrentLinkedQueue<>();
    private final String url;
    private final List<Embed> embeds;
    private String message;
    private String username;
    private String avatarUrl;
    private boolean tts;

    private DiscordWebhook(String url) {
        this.url = url;
        this.embeds = new ArrayList<>();
    }

    public static DiscordWebhook create(@NotNull String url) {
        return new DiscordWebhook(url);
    }

    @NotNull
    public DiscordWebhook message(@NotNull String message) {
        this.message = message;
        return this;
    }

    @NotNull
    public DiscordWebhook username(@NotNull String username) {
        this.username = username;
        return this;
    }

    @NotNull
    public DiscordWebhook avatar(@NotNull String url) {
        this.avatarUrl = url;
        return this;
    }

    @NotNull
    public DiscordWebhook tts(boolean tts) {
        this.tts = tts;
        return this;
    }

    @NotNull
    public DiscordWebhook embed(@NotNull Embed embed) {
        this.embeds.add(embed);
        return this;
    }

    public void queue() {
        WEBHOOK_QUEUE.offer(this);
    }

    public static class Embed {

        private final String title;
        private final String description;
        private final String url;
        private final Color color;
        private final Footer footer;
        private final String thumbnail;
        private final String image;
        private final Author author;
        private final List<Field> fields;

        private Embed(String title, String description, String url, Color color, Footer footer, String thumbnail, String image, Author author, List<Field> fields) {
            this.title = title;
            this.description = description;
            this.url = url;
            this.color = color;
            this.footer = footer;
            this.thumbnail = thumbnail;
            this.image = image;
            this.author = author;
            this.fields = fields;
        }

        public static Builder builder() {
            return new Builder();
        }

        @NotNull
        public JSONObject serialize() {
            var jsonEmbed = new JSONObject();
            jsonEmbed.put("title", title);
            jsonEmbed.put("description", description);
            jsonEmbed.put("url", url);

            if (color != null) {
                int rgb = color.getRed();
                rgb = (rgb << 8) + color.getGreen();
                rgb = (rgb << 8) + color.getBlue();
                jsonEmbed.put("color", rgb);
            }

            if (footer != null) {
                JSONObject jsonFooter = new JSONObject();
                jsonFooter.put("text", footer.getText());
                jsonFooter.put("icon_url", footer.getIconUrl());
                jsonEmbed.put("footer", jsonFooter);
            }

            if (image != null) {
                JSONObject jsonImage = new JSONObject();
                jsonImage.put("url", image);
                jsonEmbed.put("image", jsonImage);
            }

            if (thumbnail != null) {
                JSONObject jsonThumbnail = new JSONObject();
                jsonThumbnail.put("url", thumbnail);
                jsonEmbed.put("thumbnail", jsonThumbnail);
            }

            if (author != null) {
                JSONObject jsonAuthor = new JSONObject();
                jsonAuthor.put("name", author.getName());
                jsonAuthor.put("url", author.getUrl());
                jsonAuthor.put("icon_url", author.getIconUrl());
                jsonEmbed.put("author", jsonAuthor);
            }

            var jsonFields = new ArrayList<>();
            for (Embed.Field field : fields) {
                JSONObject jsonField = new JSONObject();
                jsonField.put("name", field.getName());
                jsonField.put("value", field.getValue());
                jsonField.put("inline", field.isInline());
                jsonFields.add(jsonField);
            }

            jsonEmbed.put("fields", jsonFields.toArray());
            return jsonEmbed;
        }

        public static class Builder {

            private String title;
            private String description;
            private String url;
            private Color color;
            private Footer footer;
            private String thumbnail;
            private String image;
            private Author author;
            private List<Field> fields;

            public Builder() {
                this.fields = new ArrayList<>();
            }

            @NotNull
            public Builder title(@NotNull String title) {
                this.title = title;
                return this;
            }

            @NotNull
            public Builder description(@NotNull String description) {
                this.description = description;
                return this;
            }

            @NotNull
            public Builder url(@NotNull String url) {
                this.url = url;
                return this;
            }

            @NotNull
            public Builder color(@NotNull Color color) {
                this.color = color;
                return this;
            }

            @NotNull
            public Builder footer(@NotNull String text, @NotNull String url) {
                this.footer = new Footer(text, url);
                return this;
            }

            @NotNull
            public Builder thumbnail(@NotNull String url) {
                this.thumbnail = url;
                return this;
            }

            @NotNull
            public Builder image(@NotNull String url) {
                this.image = url;
                return this;
            }

            @NotNull
            public Builder author(@NotNull String name, @NotNull String url, @NotNull String iconUrl) {
                this.author = new Author(name, url, iconUrl);
                return this;
            }

            @NotNull
            public Builder addField(@NotNull String name, @NotNull String value) {
                return addField(name, value, false);
            }

            @NotNull
            public Builder addField(@NotNull String name, @NotNull String value, boolean inline) {
                this.fields.add(new Field(name, value, inline));
                return this;
            }

            @NotNull
            public Embed build() {
                return new Embed(title, description, url, color, footer, thumbnail, image, author, fields);
            }
        }

        private static class Footer {
            private final String text;
            private final String iconUrl;

            private Footer(String text, String iconUrl) {
                this.text = text;
                this.iconUrl = iconUrl;
            }

            private String getText() {
                return text;
            }

            private String getIconUrl() {
                return iconUrl;
            }
        }

        private static class Author {

            private final String name;
            private final String url;
            private final String iconUrl;

            private Author(String name, String url, String iconUrl) {
                this.name = name;
                this.url = url;
                this.iconUrl = iconUrl;
            }

            private String getName() {
                return name;
            }

            private String getUrl() {
                return url;
            }

            private String getIconUrl() {
                return iconUrl;
            }
        }

        private static class Field {

            private final String name;
            private final String value;
            private final boolean inline;

            private Field(String name, String value, boolean inline) {
                this.name = name;
                this.value = value;
                this.inline = inline;
            }

            private String getName() {
                return name;
            }

            private String getValue() {
                return value;
            }

            private boolean isInline() {
                return inline;
            }
        }
    }

    private static class JSONObject {

        private final HashMap<String, Object> map = new HashMap<>();

        void put(String key, Object value) {
            if (value != null) {
                map.put(key, value);
            }
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            Set<Map.Entry<String, Object>> entrySet = map.entrySet();
            builder.append("{");

            int i = 0;
            for (Map.Entry<String, Object> entry : entrySet) {
                Object val = entry.getValue();
                builder.append(quote(entry.getKey())).append(":");

                if (val instanceof String) {
                    builder.append(quote(String.valueOf(val)));
                } else if (val instanceof Integer) {
                    builder.append(Integer.valueOf(String.valueOf(val)));
                } else if (val instanceof Boolean) {
                    builder.append(val);
                } else if (val instanceof JSONObject) {
                    builder.append(val.toString());
                } else if (val.getClass().isArray()) {
                    builder.append("[");
                    int len = Array.getLength(val);
                    for (int j = 0; j < len; j++) {
                        builder.append(Array.get(val, j).toString()).append(j != len - 1 ? "," : "");
                    }
                    builder.append("]");
                }
                builder.append(++i == entrySet.size() ? "}" : ",");
            }
            return builder.toString();
        }

        private String quote(String string) {
            return "\"" + string + "\"";
        }
    }

    static {
        Bukkit.getScheduler().runTaskTimerAsynchronously(Kerosene.getKerosene(), () -> {
            var webhook = WEBHOOK_QUEUE.poll();

            if (webhook == null) {
                return;
            }

            if (webhook.message == null && webhook.embeds.isEmpty()) {
                throw new IllegalArgumentException("Empty message contents.");
            }

            var json = new JSONObject();
            json.put("content", webhook.message);
            json.put("username", webhook.username);
            json.put("avatar_url", webhook.avatarUrl);
            json.put("tts", webhook.tts);

            if (!webhook.embeds.isEmpty()) {
                var serialized = webhook.embeds.stream().map(Embed::serialize).toArray();
                json.put("embeds", serialized);
            }

            try {
                var url = new URL(webhook.url);
                var connection = (HttpsURLConnection) url.openConnection();
                connection.addRequestProperty("Content-Type", "application/json");
                connection.addRequestProperty("User-Agent", "Kerosene");
                connection.setDoOutput(true);
                connection.setRequestMethod("POST");

                var stream = connection.getOutputStream();
                stream.write(json.toString().getBytes());
                stream.flush();
                stream.close();

                connection.getInputStream().close();
                connection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, 0L, 20 * 2L);
    }
}