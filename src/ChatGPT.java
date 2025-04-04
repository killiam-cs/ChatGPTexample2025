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
        // If you push this to a public GitHub repository, the API key will STOP working
        // To push to GitHub, make sure you repository is *PRIVATE*
        String apiKey = ""; // add your API key here inside the quotes!
        String model = "gpt-3.5-turbo"; // feel free to upgrade this to a more recent model

        try {
            URL obj = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + apiKey);
            connection.setRequestProperty("Content-Type", "application/json");

            // request
            String body = "{\"model\": \"" + model + "\", \"messages\": [{\"role\": \"user\", \"content\": \"" + prompt + "\"}]}";
//            System.out.println("Body: \n" + body);
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
            return extractContentFromJSONResponse(response.toString());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    } // end of requestChatGPT method

    public String extractContentFromJSONResponse(String response) {
//        System.out.println(response);
        int start = response.indexOf("content")+ 11;

        int end = response.indexOf("\"", start);

        return response.substring(start, end);

    }


}
