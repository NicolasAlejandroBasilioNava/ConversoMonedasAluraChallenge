import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.util.Scanner;
import org.json.JSONObject;


public class CurrencyConverter {
    private static final String API_KEY = "f193bd854d2d1b1ed186e280";
    private static final String BASE_URL = "https://v6.exchangerate-api.com/v6/";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        HttpClient client = HttpClient.newHttpClient();

        while (true) {
            System.out.println("Seleccione una opción de conversión:");
            System.out.println("1) Dolar -> Peso Mexicano");
            System.out.println("2) Peso Mexicano -> Dolar");
            System.out.println("3) Dolar -> Peso argentino");
            System.out.println("4) Peso Argentino -> Dolar");
            System.out.println("5) Dolar -> Real Brasileño");
            System.out.println("6) Real Brasileño -> Dolar");
            System.out.println("7) Salir");
            System.out.print("Elija una opción válida: ");

            int option = scanner.nextInt();
            if (option == 7) {
                System.out.println("Gracias por utilizar el conversor de moneda.");
                break;
            }

            System.out.print("Ingrese el monto que desea convertir: ");
            double amount = scanner.nextDouble();

            String baseCurrency = "";
            String targetCurrency = "";

            switch (option) {
                case 1:
                    baseCurrency = "USD";
                    targetCurrency = "MXN";
                    break;
                case 2:
                    baseCurrency = "MXN";
                    targetCurrency = "USD";
                    break;
                case 3:
                    baseCurrency = "USD";
                    targetCurrency = "ARS";
                    break;
                case 4:
                    baseCurrency = "ARS";
                    targetCurrency = "USD";
                    break;
                case 5:
                    baseCurrency = "USD";
                    targetCurrency = "BRL";
                    break;
                case 6:
                    baseCurrency = "BRL";
                    targetCurrency = "USD";
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, elija una opción correcta.");
                    continue;
            }

            convertCurrency(client, baseCurrency, targetCurrency, amount);
        }
        scanner.close();
    }

    private static void convertCurrency(HttpClient client, String baseCurrency, String targetCurrency, double amount) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + API_KEY + "/latest/" + baseCurrency ))
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                JSONObject json = new JSONObject(response.body());
                double rate = json.getJSONObject("conversion_rates").getDouble(targetCurrency);
                double convertedAmount = rate * amount;
                System.out.printf("\n\nEl valor convertido es: %.2f %s\n\n\n", convertedAmount, targetCurrency);
            } else {
                System.out.println("Error en la solicitud HTTP: " + response.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
