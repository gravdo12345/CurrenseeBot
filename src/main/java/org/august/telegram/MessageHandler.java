package org.august.telegram;

import org.august.bankinfo.BankInterface;
import org.august.bankinfo.BankParser;
import org.august.dtomodel.*;
import org.august.userinfo.DatabaseUserInfo;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MessageHandler {
     BankParser bankParser = new BankParser();
     private final DatabaseUserInfo dbUserInfo = DatabaseUserInfo.getInstance();

    private UserModel getInfo(String userId){
        if (dbUserInfo.getDatabaseUser().containsKey(userId)){
            return dbUserInfo.getDatabaseUser().get(userId);
        } else {
            UserModel newUswer = new DefaultUserModel();
            dbUserInfo.getDatabaseUser().put(userId, newUswer);
            return newUswer;
        }
    }

    private String formatNumber(float number, int digits) {
        // Create sample for DecimalFormat

        DecimalFormat df = new DecimalFormat("#." + "#".repeat(Math.max(0, digits)));

        // Formatting
        return df.format(number);
    }

    private void writeBankData(StringBuilder builder, BankInterface bank, UserModel user, String userCurrency){

            String buyPrice = formatNumber(bank.getBuy(), user.getSumbols());
            String sellPrice = formatNumber(bank.getSell(), user.getSumbols());

            builder.append("\n");
            builder.append(userCurrency);
            builder.append("\n");

            builder.append("Купівля: ");
            builder.append(buyPrice);
            builder.append("\n");

            builder.append("Продаж: ");
            builder.append(sellPrice);
            builder.append("\n");

    }

    private String prepearingInfo(UserModel user){
        StringBuilder builder = new StringBuilder("Данні про валюту:");

        for (String bank: user.getBank()){
            builder.append("\n");
            builder.append(bank);
            builder.append(":");
            BankInterface bankModel = null;

            for (String userCurrency : user.getCurrency()) {
                switch (bank) {
                    case "monobank" -> bankModel = bankParser.getMonoData(userCurrency);
                    case "privatbank" -> bankModel = bankParser.getPrivatBankModel(userCurrency);
                    case "NBU" -> bankModel = bankParser.getNBUModel(userCurrency);
                }
                assert bankModel != null;
                writeBankData(builder, bankModel, user, userCurrency);
            }
        }

        return builder.toString();
    }

    public SendMessage handleMessage(SendMessage message, String command) {
        Functions func = new Functions(message);
        UserModel user = getInfo(message.getChatId());

        switch (command) {
            case "/start", "На головну" -> func.start();
            case "Отримати інфо" -> message.setText(prepearingInfo(user));
            case "Налаштування", "Повернутись" -> func.setings();
            case "1. Кількість знаків після коми" -> func.setSpliting(user);
            case "- 2" -> {
                int split = 2;
                func.spliting(user, split);
            }
            case "- 3" -> {
                int split = 3;
                func.spliting(user, split);
            }
            case "- 4" -> {
                int split = 4;
                func.spliting(user, split);
            }
            case "2. Банк" -> func.writeBank(user);
            case "1. НБУ", "1. НБУ ✔" -> {
                String bank = "NBU";
                func.choiceBank(user,bank);
            }
            case "2. ПриватБанк", "2. ПриватБанк ✔" -> {
                String bank = "privatbank";
                func.choiceBank(user,bank);
            }
            case "3. Монобанк", "3. Монобанк ✔" ->{
                String bank = "monobank";
                func.choiceBank(user,bank);
            }
            case "3. Валюти" -> func.writeCurrency(user);
            case "1. USD", "1. USD ✔" -> {
                String currency = "USD";
                func.choiceCurrency(user, currency);
            }
            case "2. EUR", "2. EUR ✔" -> {
                String currency = "EUR";
                func.choiceCurrency(user, currency);
            }
            case "4. Час оповіщень" -> {
                List<KeyboardRow> keyboard = getTimeKeyboard();
                // Add to keyboard
                ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
                replyKeyboardMarkup.setKeyboard(keyboard);
                message.setReplyMarkup(replyKeyboardMarkup);
                message.setText(".");
            }
            case "вимкнути" -> {
                if (user.getNotificationTime() == 0){
                    message.setText("Ваші повідомлення і так вимкнено.");
                } else {
                    user.setNotificationTime(0);
                    message.setText("Готово. Ваші повідомлення вимкнено.");
                }
            }
            case "9", "10", "11", "12", "13", "14", "15", "16", "17", "18" -> {
                int time = Integer.parseInt(command);
                if (user.getNotificationTime() == time) {
                    message.setText("Повідомлення і так приходять в цю годину.");
                } else {
                    user.setNotificationTime(time);
                    message.setText("Новий час встановлено: "+command);
                }
            }
        }
        return message;
    }

    private List<KeyboardRow> getTimeKeyboard(){
        String[] buttonList = {"9", "10", "11",
        "12", "13", "14",
        "15", "16", "17",
        "18", "вимкнути", "Повернутись"};
        // keyboard init
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();

        for (int i = 0; i < buttonList.length; i++){
            KeyboardButton button = new KeyboardButton(buttonList[i]);
            row.add(button);

            if ((i + 1) % 3 == 0 || i == buttonList.length - 1) {
                keyboard.add(row);

                row = new KeyboardRow();
            }
        }
        return keyboard;
    }
}
