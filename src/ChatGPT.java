import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class ChatGPT {

    public static void main(String[] args) {
        ChatGPT c = new ChatGPT();
    }

    public ChatGPT() {
        System.out.println( requestChatGPT("Hello, how are you? Can you tell me what's a Fibonacci Number?") );
    } // end of constructor

    public String requestChatGPT(String prompt) {
        String url = "https://api.openai.com/v1/chat/completions";
        // Below is the department API key for 2025.
        // If you push this to a public GitHub repository, the API key will STOP working
        // To push to GitHub, make sure you repository is *PRIVATE*
        String apiKey = "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";
        String model = "gpt-3.5-turbo";

        try {
            URL obj = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + apiKey);
            connection.setRequestProperty("Content-Type", "application/json");

            // request
            String body = "{\"model\": \"" + model + "\", \"messages\": [{\"role\": \"user\", \"content\": \"" + prompt + "\"}]}";
            connection.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(body);
            writer.flush();
            writer.close();

            // response from ChatGPT
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;

            StringBuffer response = new StringBuffer();

            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            br.close();

            // calls the method to extract the message.
            return response.toString();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    } // end of requestChatGPT method

}
