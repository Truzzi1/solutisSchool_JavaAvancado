import java.net.URI;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;

import java.util.List;

public class SqsConsumer {

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

    // Recebe e processa mensagens
    receiveMessages(sqsClient);

    // Fecha o cliente
    sqsClient.close();
  }

  public static void receiveMessages(SqsClient sqsClient) {
    ReceiveMessageRequest receiveRequest = ReceiveMessageRequest.builder()
        .queueUrl(QUEUE_URL)
        .maxNumberOfMessages(5)
        .waitTimeSeconds(10) // Long polling
        .build();

    List<Message> messages = sqsClient.receiveMessage(receiveRequest).messages();

    for (Message message : messages) {
      System.out.println("Mensagem recebida: " + message.body());

      // Exclui a mensagem da fila após o processamento
      DeleteMessageRequest deleteRequest = DeleteMessageRequest.builder()
          .queueUrl(QUEUE_URL)
          .receiptHandle(message.receiptHandle())
          .build();
      sqsClient.deleteMessage(deleteRequest);
    }
  }
}
