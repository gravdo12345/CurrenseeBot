package org.august.telegram;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class TelegramBot extends TelegramLongPollingBot {
    public TelegramBot() {
        NotificationService notification = new NotificationService();
        notification.start(this);
    }

    @Override
    public String getBotUsername() {
        return "";
    }

    @Override
    public String getBotToken() {
        return "";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {

            String messageText = update.getMessage().getText();
            SendMessage message = new SendMessage();
            message.setChatId(update.getMessage().getChatId().toString());

            // message processing
            MessageHandler handler = new MessageHandler();

            try {
                execute(handler.handleMessage(message, messageText));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
}
