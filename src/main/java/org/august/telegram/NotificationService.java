package org.august.telegram;

import org.august.dtomodel.UserModel;
import org.august.userinfo.DatabaseUserInfo;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.Map;

public class NotificationService {
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final DatabaseUserInfo dbUserInfo = DatabaseUserInfo.getInstance();

    public void start(TelegramLongPollingBot bot) {
        final Runnable checker = () -> {
            LocalTime time = LocalTime.now();
            int now = time.getHour();

            for (Map.Entry<String, UserModel> entry : dbUserInfo.getDatabaseUser().entrySet()) {
                String userId = entry.getKey();
                UserModel user = entry.getValue();

                if (now == user.getNotificationTime()) {
                    SendMessage message = new SendMessage();
                    message.setChatId(userId);
                    MessageHandler handler = new MessageHandler();

                    try {
                        bot.execute(handler.handleMessage(message, "Отримати інфо"));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }

                }
            }
        };

        scheduler.scheduleAtFixedRate(checker, 0, 1, TimeUnit.HOURS);
    }
}