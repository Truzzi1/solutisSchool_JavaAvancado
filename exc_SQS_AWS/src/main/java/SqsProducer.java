import java.net.URI;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

public class SqsProducer {

  private static final String QUEUE_URL = "https://sqs.us-west-2.amazonaws"
      + ".com/000000000000/teste";

  public static void main(String[] args) {

    // Cria o cliente SQS com credenciais e endpoint do LocalStack
    SqsClient sqsClient = SqsClient.builder()
        .endpointOverride(URI.create("http://localhost:4566")) // Endpoint do LocalStack
        .credentialsProvider(StaticCredentialsProvider.create(
            AwsBasicCredentials.create("test", "test"))) // Credenciais fictícias
        .region(Region.US_WEST_2) // Use a região que você configurou
        .build();

    // Envia uma mensagem
    sendMessage(sqsClient, "Olá, esta é uma mensagem de teste!");

    // Fecha o cliente
    sqsClient.close();
  }

  public static void sendMessage(SqsClient sqsClient, String message) {
    SendMessageRequest sendMsgRequest = SendMessageRequest.builder()
        .queueUrl(QUEUE_URL)
        .messageBody(message)
        .delaySeconds(0)
        .build();

    sqsClient.sendMessage(sendMsgRequest);
    System.out.println("Mensagem enviada: " + message);
  }
}
