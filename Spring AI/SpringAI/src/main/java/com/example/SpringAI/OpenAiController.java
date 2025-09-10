package com.example.SpringAI;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class OpenAiController {

    private ChatClient chatClient;

    private ChatMemory chatMemory;

    public OpenAiController(ChatClient.Builder chatClientBuilder){

        this.chatMemory = MessageWindowChatMemory
                .builder()
                .build();

        this.chatClient = chatClientBuilder
                .defaultAdvisors(MessageChatMemoryAdvisor
                        .builder(chatMemory)
                        .build())
                .build();
    }


        @GetMapping("/home/{msg}")
        public ResponseEntity<String> getAnswer(@PathVariable String msg){
            String response = chatClient
                    .prompt(msg)
                    .call()
                    .content();

            return new ResponseEntity<>(response, HttpStatus.OK);
        }

}
