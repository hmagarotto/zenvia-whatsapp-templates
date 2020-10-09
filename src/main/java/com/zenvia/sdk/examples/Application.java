package com.zenvia.sdk.examples;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.zenvia.api.sdk.client.Channel;
import com.zenvia.api.sdk.client.apache.Client;
import com.zenvia.api.sdk.client.errors.ErrorResponse;
import com.zenvia.api.sdk.client.exceptions.ApiException;
import com.zenvia.api.sdk.client.exceptions.UnsuccessfulRequestException;
import com.zenvia.api.sdk.contents.Content;
import com.zenvia.api.sdk.contents.TemplateContent;
import com.zenvia.api.sdk.messages.Message;

@SpringBootApplication
public class Application implements CommandLineRunner  {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
    @Value("${message.senderId}")
    private String senderId;

    @Value("${message.recipientId}")
    private String recipientId;

    @Value("${message.templateId}")
    private String templateId;

    @Value("${message.imageUrl}")
    private String imageUrl;

    @Autowired
    private Client client;
    
	@Override
	public void run(String... args) throws Exception {
		Channel whatsapp = client.getChannel("whatsapp");

		// Creating a template content
		Map<String,String> fields = new HashMap<>();
		fields.put("imageUrl", imageUrl);
		Content content = new TemplateContent(templateId, fields);
		
		try {
		  Message response = whatsapp.sendMessage(senderId, recipientId, content);
		  // do something here
		} catch(UnsuccessfulRequestException exception) {
		  ErrorResponse response = exception.body;
		  // handle error here
		} catch(ApiException exception) {
		  // handle error here
		}
	}

}
