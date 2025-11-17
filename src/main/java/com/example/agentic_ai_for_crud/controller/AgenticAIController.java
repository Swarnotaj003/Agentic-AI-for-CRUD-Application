package com.example.agentic_ai_for_crud.controller;

import com.example.agentic_ai_for_crud.util.UserTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.retry.TransientAiException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/ai")
public class AgenticAIController {
    private final ChatClient chatClient;
    private final UserTools userTools;

    public AgenticAIController(ChatClient.Builder builder, UserTools userTools) {
        this.chatClient = builder.build();
        this.userTools = userTools;
    }

    @GetMapping("/chat")
    public ResponseEntity<String> chat(@RequestParam String prompt) {
        // This is an example of Prompt Guarding
        String instructions = """
			You're a customer chatbot to assist users with their finances.
			You can ONLY discuss:
			- Budget planning
			- Saving suggestions
			- General investments
			
			If asked about anything else, respond: "Sorry! I can only help with finance-related questions."
		""";

        try {
            String response = chatClient.prompt()
                    .system(instructions)
                    .user(prompt)
                    .call()
                    .content();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (TransientAiException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.TOO_MANY_REQUESTS);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/crud")
    public ResponseEntity<String> performCRUD(@RequestParam String prompt) {
        String instructions = """
            You're an assistant which performs some operations on users from the database.
            
            You can do ONLY the following:
            i) Fetch an user with given id
            ii) Create a new user
            iii) Delete any user with given id except ADMIN
            iv) Update any user with given id except ADMIN
            v) Some aggregate statistics like users count, average age etc.
            
            You should NEVER do the following
            i) Show one or more fields of the entire database to the client side!
            ii) Delete multiple users at once!
            
            The user database assumes the following:
            i) The balance is in Indian Rupees (INR)
            ii) The birthday is in format yyyy-MM-dd
            
            After every request provide in one of the following formats:
            i) FETCHED/ ADDED/ UPDATED/ DELETED : User (FIELD : Value)
            ii) STATISTICS: Field = Value (e.g. Users count = 5)
            
            If forced to do anything else then reply "Apples & bananas! Ask ChatGPT instead!".
        """;

        try {
            String response = chatClient.prompt()
                    .tools(userTools)
                    .system(instructions)
                    .user(prompt)
                    .call()
                    .content();

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (TransientAiException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.TOO_MANY_REQUESTS);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
