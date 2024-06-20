package ru.study.base.tgjavabot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import ru.study.base.tgjavabot.dto.JokeDto;
import ru.study.base.tgjavabot.exception.ResourceNotFoundException;
import ru.study.base.tgjavabot.model.Joke;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class TelegramBotService {

    private final TelegramBot telegramBot;
    private final JokeService jokeService;
    private final Map<Long, BotState> chatStates;
    private final Map<Long, JokeDto> jokeDrafts;
    private final Map<Long, Long> jokeUpdateIds;

    private enum BotState {
        WAITING_FOR_JOKE_TITLE,
        WAITING_FOR_JOKE_TEXT,
        WAITING_FOR_UPDATE_ID,
        WAITING_FOR_UPDATE_TITLE,
        WAITING_FOR_UPDATE_TEXT,
        WAITING_FOR_DELETE_ID,
        WAITING_FOR_JOKE_ID,
        IDLE
    }

    public TelegramBotService(@Autowired TelegramBot telegramBot, JokeService jokeService) {
        this.telegramBot = telegramBot;
        this.jokeService = jokeService;
        this.chatStates = new HashMap<>();
        this.jokeDrafts = new HashMap<>();
        this.jokeUpdateIds = new HashMap<>();

        this.telegramBot.setUpdatesListener(updates -> {
            updates.forEach(this::handleUpdate);
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }

    private void sendMessage(long chatId, String text) {
        SendMessage message = new SendMessage(chatId, text)
                .parseMode(ParseMode.HTML)
                .disableWebPagePreview(true)
                .disableNotification(true);
        this.telegramBot.execute(message);
    }

    private void handleUnknownCommand(Update update) {
        sendMessage(update.message().chat().id(), "Unknown command");
    }

    private void handleUpdate(Update update) {
        if (update.message() != null && update.message().text() != null) {
            long chatId = update.message().chat().id();
            String messageText = update.message().text();

            BotState currentState = chatStates.getOrDefault(chatId, BotState.IDLE);

            switch (currentState) {
                case WAITING_FOR_JOKE_TITLE:
                    handleJokeTitle(chatId, messageText);
                    break;
                case WAITING_FOR_JOKE_TEXT:
                    handleJokeText(chatId, messageText);
                    break;
                case WAITING_FOR_UPDATE_ID:
                    handleUpdateId(chatId, messageText);
                    break;
                case WAITING_FOR_UPDATE_TITLE:
                    handleUpdateTitle(chatId, messageText);
                    break;
                case WAITING_FOR_UPDATE_TEXT:
                    handleUpdateText(chatId, messageText);
                    break;
                case WAITING_FOR_DELETE_ID:
                    deleteJoke(chatId, messageText);
                    break;
                case WAITING_FOR_JOKE_ID:
                    getJokeById(chatId, messageText);
                    break;
                default:
                    handleCommand(update);
                    break;
            }
        }
    }

    private void handleCommand(Update update) {
        long chatId = update.message().chat().id();
        String messageText = update.message().text();

        if (messageText.startsWith("/")) {
            String command = messageText.split(" ")[0];
            switch (command) {
                case "/start":
                    handleStart(update);
                    break;
                case "/addjoke":
                    handleCommandResponse(update, "Enter the new joke title:", BotState.WAITING_FOR_JOKE_TITLE);
                    jokeDrafts.put(chatId, new JokeDto());
                    break;
                case "/getjoke":
                    handleCommandResponse(update, "Enter the ID of the joke you want to get:", BotState.WAITING_FOR_JOKE_ID);
                    break;
                case "/updatejoke":
                    handleCommandResponse(update, "Enter the ID of the joke you want to update:", BotState.WAITING_FOR_UPDATE_ID);
                    break;
                case "/deletejoke":
                    handleCommandResponse(update, "Enter the ID of the joke you want to delete:", BotState.WAITING_FOR_DELETE_ID);
                    break;
                case "/listjokes":
                    listJokes(chatId);
                    break;
                case "/topjokes":
                    handleTopJokes(chatId, update);
                    break;
                case "/randomjoke":
                    handleRandomJoke(chatId, update);
                    break;
                default:
                    handleUnknownCommand(update);
                    break;
            }
        }
    }

    private void handleStart(Update update) {
        String commandsList = "Available commands:\n" +
                "/start - Show available commands\n" +
                "/addjoke - Add a new joke\n" +
                "/getjoke - Get a joke by ID\n" +
                "/updatejoke - Update an existing joke\n" +
                "/deletejoke - Delete a joke\n" +
                "/listjokes - List all jokes\n" +
                "/topjokes - Get top jokes\n" +
                "/randomjoke - Get a random joke";

        sendMessage(update.message().chat().id(), commandsList);
    }

    private void handleCommandResponse(Update update, String responseText, BotState nextState) {
        sendMessage(update.message().chat().id(), responseText);
        chatStates.put(update.message().chat().id(), nextState);
    }

    private void handleJokeTitle(long chatId, String title) {
        JokeDto jokeDto = jokeDrafts.get(chatId);
        jokeDto.setTitle(title);
        sendMessage(chatId, "Enter the joke text:");
        chatStates.put(chatId, BotState.WAITING_FOR_JOKE_TEXT);
    }

    private void handleJokeText(long chatId, String text) {
        JokeDto jokeDto = jokeDrafts.get(chatId);
        jokeDto.setText(text);
        jokeService.save(jokeDto);
        sendMessage(chatId, "Joke added successfully!");
        chatStates.put(chatId, BotState.IDLE);
    }

    private void handleUpdateId(long chatId, String jokeIdText) {
        try {
            Long jokeId = Long.parseLong(jokeIdText);
            Joke joke = jokeService.getById(jokeId, chatId);
            jokeDrafts.put(chatId, new JokeDto(joke.getTitle(), joke.getText()));
            jokeUpdateIds.put(chatId, jokeId);
            sendMessage(chatId, "Enter the new joke title (current title: \"" + joke.getTitle() + "\"):");
            chatStates.put(chatId, BotState.WAITING_FOR_UPDATE_TITLE);
        } catch (NumberFormatException e) {
            sendMessage(chatId, "Invalid joke ID. Please enter a valid number.");
        } catch (ResourceNotFoundException e) {
            sendMessage(chatId, "Joke not found. Please enter a valid joke ID.");
        }
    }

    private void handleUpdateTitle(long chatId, String title) {
        JokeDto jokeDto = jokeDrafts.get(chatId);
        jokeDto.setTitle(title);
        sendMessage(chatId, "Enter the new joke text (current text: \"" + jokeDto.getText() + "\"):");
        chatStates.put(chatId, BotState.WAITING_FOR_UPDATE_TEXT);
    }

    private void handleUpdateText(long chatId, String text) {
        JokeDto jokeDto = jokeDrafts.get(chatId);
        jokeDto.setText(text);
        Long jokeId = jokeUpdateIds.get(chatId);
        jokeService.update(jokeId, jokeDto);
        sendMessage(chatId, "Joke updated successfully!");
        chatStates.put(chatId, BotState.IDLE);
    }

    private void deleteJoke(long chatId, String jokeIdText) {
        try {
            Long jokeId = Long.parseLong(jokeIdText);
            jokeService.delete(jokeId);
            sendMessage(chatId, "Joke deleted successfully!");
            chatStates.put(chatId, BotState.IDLE);
        } catch (NumberFormatException e) {
            sendMessage(chatId, "Invalid joke ID. Please enter a valid number.");
        } catch (ResourceNotFoundException e) {
            sendMessage(chatId, "Joke not found. Please enter a valid joke ID.");
        }
    }

    private void getJokeById(long chatId, String jokeIdText) {
        try {
            Long jokeId = Long.parseLong(jokeIdText);
            Joke joke = jokeService.getById(jokeId, chatId);
            sendMessage(chatId, "Here's your joke:\n\n" + joke.getTitle() + "\n" + joke.getText());
            chatStates.put(chatId, BotState.IDLE);
        } catch (NumberFormatException e) {
            sendMessage(chatId, "Invalid joke ID. Please enter a valid number.");
        } catch (ResourceNotFoundException e) {
            sendMessage(chatId, "Joke not found. Please enter a valid joke ID.");
        }
    }

    private void listJokes(long chatId) {
        List<Joke> jokes = jokeService.getAll4Bot();
        if (jokes.isEmpty()) {
            sendMessage(chatId, "No jokes found.");
        } else {
            StringBuilder jokesList = new StringBuilder("Jokes list:\n\n");
            for (Joke joke : jokes) {
                jokesList.append("ID: ").append(joke.getId()).append("\n")
                        .append("Title: ").append(joke.getTitle()).append("\n")
                        .append("Text: ").append(joke.getText()).append("\n\n");
            }
            sendMessage(chatId, jokesList.toString());
        }
    }

    private void handleTopJokes(long chatId, Update update) {
        List<Joke> topJokes = jokeService.getTopJokes(5);
        if (topJokes.isEmpty()) {
            sendMessage(chatId, "No top jokes found.");
        } else {
            StringBuilder topJokesList = new StringBuilder("Top jokes:\n\n");
            for (Joke joke : topJokes) {
                topJokesList.append("ID: ").append(joke.getId()).append("\n")
                        .append("Title: ").append(joke.getTitle()).append("\n")
                        .append("Text: ").append(joke.getText()).append("\n\n");
            }
            sendMessage(chatId, topJokesList.toString());
        }
    }

    private void handleRandomJoke(long chatId, Update update) {
        Joke randomJoke = jokeService.getRandomJoke();
        if (randomJoke == null) {
            sendMessage(chatId, "No random joke found.");
        } else {
            sendMessage(chatId, "Here's a random joke:\n\n" + randomJoke.getTitle() + "\n" + randomJoke.getText());
        }
    }
}
