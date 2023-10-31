package org.august.telegram;

import org.august.dtomodel.UserModel;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class Functions {
    public Functions(SendMessage message) {
        this.message = message;
    }

    private final SendMessage message;
    public void start() {
        // Button init
        List<KeyboardRow> keyboard = getSimpleKeyboard(new String[]{"Отримати інфо",
                "Налаштування",
        });

        // Add to keyboard
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(keyboard);

        message.setText("Привіт!\nЯ бот для відстеження курсу валют.\nВибири відповідну опцію, щоб почати.");
        message.setReplyMarkup(replyKeyboardMarkup);
    }

    public void setings() {
        List<KeyboardRow> keyboard = getSimpleKeyboard(new String[]{"1. Кількість знаків після коми",
                "2. Банк",
                "3. Валюти",
                "4. Час оповіщень",
                "На головну"
        });
        // Add to keyboard
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(keyboard);
        message.setReplyMarkup(replyKeyboardMarkup);
        message.setText(".");
    }

    public void setSpliting(UserModel user) {
        List<KeyboardRow> keyboard = getReviewerKeyboard(new String[]{"- 2",
                "- 3",
                "- 4",
        }, new String[]{"2",
                "3",
                "4",
        }, List.of(String.valueOf(user.getSumbols())));
        // Add to keyboard
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(keyboard);
        message.setReplyMarkup(replyKeyboardMarkup);
        message.setText(".");
    }
    public void choiceCurrency(UserModel user, String currency) {
        List<String> copy = new ArrayList<>(user.getCurrency());

        if (user.getCurrency().contains(currency)){
            copy.remove(currency);
        } else {
            copy.add(currency);
        }
        user.setCurrency(copy);

        List<KeyboardRow> keyboard = getReviewerKeyboard(new String[]{"1. USD",
                        "2. EUR"},
                new String[]{"USD",
                        "EUR",
                }, user.getCurrency());
        // Add to keyboard
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(keyboard);
        message.setReplyMarkup(replyKeyboardMarkup);
        message.setText(".");
    }
    public void writeCurrency(UserModel user) {
        List<KeyboardRow> keyboard = getReviewerKeyboard(new String[]{"1. USD",
                        "2. EUR"},
                new String[]{"USD",
                        "EUR",
                }, user.getCurrency());
        // Add to keyboard
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(keyboard);
        message.setReplyMarkup(replyKeyboardMarkup);
        message.setText(".");
    }
    public void choiceBank(UserModel user, String bank) {
        List<String> copy = new ArrayList<>(user.getBank());
        if (user.getBank().contains(bank)){
            copy.remove(bank);
        } else {
            copy.add(bank);
        }
        user.setBank(copy);

        List<KeyboardRow> keyboard = getReviewerKeyboard(new String[]{"1. НБУ",
                        "2. ПриватБанк",
                        "3. Монобанк"},
                new String[]{"NBU",
                        "privatbank",
                        "monobank",
                }, user.getBank());
        // Add to keyboard
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(keyboard);
        message.setReplyMarkup(replyKeyboardMarkup);
        message.setText(".");
    }
    public void writeBank(UserModel user) {
        List<KeyboardRow> keyboard = getReviewerKeyboard(new String[]{"1. НБУ",
                        "2. ПриватБанк",
                        "3. Монобанк"},
                new String[]{"NBU",
                        "privatbank",
                        "monobank",
                }, user.getBank());
        // Add to keyboard
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(keyboard);
        message.setReplyMarkup(replyKeyboardMarkup);
        message.setText(".");
    }
    public void spliting(UserModel user, int split) {
        if (user.getSumbols() == split){
            user.setSumbols(0);
        } else {
            user.setSumbols(split);
        }
        List<KeyboardRow> keyboard = getReviewerKeyboard(new String[]{"- 2",
                "- 3",
                "- 4",
        }, new String[]{"2",
                "3",
                "4",
        }, List.of(String.valueOf(user.getSumbols())));
        // Add to keyboard
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(keyboard);
        message.setReplyMarkup(replyKeyboardMarkup);
        message.setText(".");
    }

    private List<KeyboardRow> getReviewerKeyboard(String[] buttonList, String[] checkList, List<String> params){
        String[] writeKeyboard = new String[buttonList.length+1];

        for (int i = 0; i<buttonList.length; i++){
            String button = buttonList[i];

            if (params.contains(checkList[i])) { button+=" ✔"; }

            writeKeyboard[i] = button;
        }

        writeKeyboard[buttonList.length] = "Повернутись";

        return this.getSimpleKeyboard(writeKeyboard);
    }

    private List<KeyboardRow> getSimpleKeyboard(String[] buttonList){
        // keyboard init
        List<KeyboardRow> keyboard = new ArrayList<>();

        for (String buttonText: buttonList){
            KeyboardButton button = new KeyboardButton(buttonText);
            // Row init
            KeyboardRow row = new KeyboardRow();
            row.add(button);
            keyboard.add(row);
        }
        return keyboard;
    }
}
