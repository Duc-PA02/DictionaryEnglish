package com.example.appdictionaryghtk.service.textGeneration;

import com.example.appdictionaryghtk.entity.TextGenerationRequest;
import com.example.appdictionaryghtk.entity.TextGenerationResponse;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TextGenerationService implements ITextGenerationService {

    private final TextGenerationServiceApi textGenerationServiceApi;
    private Dotenv dotenv;

    @Override
    public String generateText(TextGenerationRequest request) {
        try {
            dotenv = Dotenv.load();
            String apiKey = dotenv.get("API_GEMINI_KEY");

            // Nhận toàn bộ cuộc hội thoại từ request
            List<TextGenerationRequest.Content> contents = request.getContents();
            if (contents == null) {
                contents = new ArrayList<>();
            }

            // Tạo request mới với dữ liệu từ frontend
            TextGenerationRequest fullRequest = new TextGenerationRequest();
            fullRequest.setContents(contents);

            TextGenerationResponse response = textGenerationServiceApi.generateContent(fullRequest, apiKey);

            if (response != null && response.getCandidates() != null && !response.getCandidates().isEmpty()) {
                // Trả về phản hồi từ API
                return response.getCandidates().get(0).getContent().getParts().get(0).getText();
            } else {
                return "No response or empty candidates.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "An error occurred while generating text.";
        }
    }
}