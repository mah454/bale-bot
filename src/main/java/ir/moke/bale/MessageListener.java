package ir.moke.bale;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import ir.moke.bale.model.State;
import ir.moke.bale.model.UserSession;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MessageListener implements UpdatesListener {
    private static final Keyboard replyKeyboardMarkup = new ReplyKeyboardMarkup(
            "Do something")
            .oneTimeKeyboard(true)   // optional
            .resizeKeyboard(true)    // optional
            .selective(true);

    private static final Map<Long, UserSession> MAP = new ConcurrentHashMap<>();
    private final TelegramBot bot;

    public MessageListener(TelegramBot bot) {
        this.bot = bot;
    }

    @Override
    public int process(List<Update> updates) {
        for (Update update : updates) {
            Message message = update.message();
            String text = message.text();
            Long chatId = message.chat().id();

            UserSession session = MAP.computeIfAbsent(chatId, aLong -> new UserSession());

            handleSession(bot, chatId, text, session);
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    private static void handleSession(TelegramBot bot, long chatId, String text, UserSession session) {
        switch (session.getState()) {
            case START -> {
                bot.execute(new SendMessage(chatId, "Enter number 1").replyMarkup(replyKeyboardMarkup));
                session.setState(State.P1);
            }
            case P1 -> {
                bot.execute(new SendMessage(chatId, "First number is " + text).replyMarkup(replyKeyboardMarkup));
                bot.execute(new SendMessage(chatId, "Enter second number").replyMarkup(replyKeyboardMarkup));
                session.setState(State.P2);
                session.setP1(Long.parseLong(text));
            }
            case P2 -> {
                bot.execute(new SendMessage(chatId, "Second number is : " + text).replyMarkup(replyKeyboardMarkup));
                session.setP2(Long.parseLong(text));
                bot.execute(new SendMessage(chatId, "Result : " + session.calc()));
                MAP.remove(chatId);
            }
        }
    }
}
