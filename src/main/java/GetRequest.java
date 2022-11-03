import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class GetRequest {
    public static final String URL = "https://raw.githubusercontent.com/netology-code/jd-homeworks/master/http/task1/cats";
    public static ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) { //создали http клиент

            HttpGet request = new HttpGet(URL); // чтобы сделать запрос http используем HttpGet и передаем урл

            try (CloseableHttpResponse response = httpClient.execute(request) //устанавливаем соединение:отправка запроса(request),получаем ответ(response)
            ) {
                List<FactsOfCats> responseList = mapper.readValue(response.getEntity().getContent(),
                        new TypeReference<>() {
                        });
                List<FactsOfCats> filteredOfNotNullUpvotesList = responseList.stream().filter(factsOfCats -> factsOfCats.getUpvotes() != null).collect(Collectors.toList());
                filteredOfNotNullUpvotesList.forEach(System.out::println);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
