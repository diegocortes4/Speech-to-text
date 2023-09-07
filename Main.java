import com.google.cloud.speech.v1p1beta1.*;
import com.google.protobuf.ByteString;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class SpeechToTextExample {
    public static void main(String[] args) throws Exception {
        // Set your Google Cloud credentials here
        String credentialsPath = "path/to/your/credentials.json";

        // Create a SpeechClient using your credentials
        try (SpeechClient speechClient = SpeechClient.create(SpeechSettings.newBuilder().setCredentialsProvider(() -> Files.newInputStream(Paths.get(credentialsPath))).build())) {
            // The path to the audio file you want to transcribe
            String audioFilePath = "path/to/your/audiofile.wav";

            // Read the audio file into a ByteString
            ByteString audioData = ByteString.copyFrom(Files.readAllBytes(Paths.get(audioFilePath)));

            // Create a RecognitionConfig specifying the language and audio format
            RecognitionConfig config = RecognitionConfig.newBuilder()
                    .setEncoding(RecognitionConfig.AudioEncoding.LINEAR16)
                    .setSampleRateHertz(16000)
                    .setLanguageCode("en-US")
                    .build();

            // Create a RecognitionAudio from the audio data
            RecognitionAudio audio = RecognitionAudio.newBuilder()
                    .setContent(audioData)
                    .build();

            // Perform the speech recognition
            RecognizeResponse response = speechClient.recognize(config, audio);

            // Process the recognition results
            List<SpeechRecognitionResult> results = response.getResultsList();
            for (SpeechRecognitionResult result : results) {
                System.out.println("Transcript: " + result.getAlternatives(0).getTranscript());
            }
        }
    }
}
