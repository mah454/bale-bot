package ir.moke.bale;

import com.pengrad.telegrambot.TelegramBot;

public class MainClass {
    private static final String baseUrl = "https://tapi.bale.ai/";
    private static final String token = "808893338:ALpbGrbbAQRrqz2lAZ2qjk4ePSTgegf6hcFmqeDi";

    static void main() {
        TelegramBot bot = new TelegramBot.Builder(token)
                .apiUrl(baseUrl)
                .build();

        bot.setUpdatesListener(new MessageListener(bot));
    }
}
