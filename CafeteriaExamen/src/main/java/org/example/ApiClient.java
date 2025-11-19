package org.example;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class ApiClient {

    // Aquí hay que cambiar el puerto donde corro el node.js, en caso del monchoServer 6220
    private static final String BASE_URL = "http://localhost:3000/api";
    private static final HttpClient httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10)) // Aumenta a 30 segundos
            .build();

    // GET /menu - Obtener productos desde MySQL
    public static String obtenerMenu() {
        try {
            String url = BASE_URL + "/menu";
            System.out.println("🔗 Probando conexión simple a: " + url);

            // Conexión más básica
            java.net.URL urlObj = new java.net.URL(url);
            java.net.HttpURLConnection conn = (java.net.HttpURLConnection) urlObj.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);

            int responseCode = conn.getResponseCode();
            System.out.println("📡 Response Code: " + responseCode);

            if (responseCode == 200) {
                java.io.BufferedReader in = new java.io.BufferedReader(
                        new java.io.InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder content = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();
                conn.disconnect();
                return content.toString();
            } else {
                return "Error: " + responseCode;
            }
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
            e.printStackTrace();
            return "Error de conexión: " + e.getMessage();
        }
    }

    // POST /pedidos - Crear nuevo pedido (CAMARERO)
    public static String crearPedido(String jsonPedido) {
        try {
            String url = BASE_URL + "/pedidos";
            System.out.println("🔗 Crear pedido - URL: " + url);
            System.out.println("📦 JSON enviado: " + jsonPedido);

            java.net.URL urlObj = new java.net.URL(url);
            java.net.HttpURLConnection conn = (java.net.HttpURLConnection) urlObj.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);

            // Escribir el JSON en el body
            try (java.io.OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonPedido.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = conn.getResponseCode();
            System.out.println("📡 Crear pedido - Response Code: " + responseCode);

            if (responseCode == 201) {
                java.io.BufferedReader in = new java.io.BufferedReader(
                        new java.io.InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder content = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();
                conn.disconnect();

                String resultado = content.toString();
                System.out.println("✅ Pedido creado exitosamente: " + resultado.substring(0, Math.min(100, resultado.length())) + "...");
                return resultado;
            } else {
                // Leer mensaje de error
                java.io.BufferedReader in = new java.io.BufferedReader(
                        new java.io.InputStreamReader(conn.getErrorStream()));
                String inputLine;
                StringBuilder errorContent = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    errorContent.append(inputLine);
                }
                in.close();
                conn.disconnect();

                System.out.println("❌ Error HTTP: " + responseCode + " - " + errorContent.toString());
                return "Error: " + responseCode + " - " + errorContent.toString();
            }
        } catch (Exception e) {
            System.out.println("❌ Crear pedido - Error: " + e.getMessage());
            e.printStackTrace();
            return "Error de conexión: " + e.getMessage();
        }
    }

    // GET /pedidos - Obtener pedidos activos (BARISTA)
    public static String obtenerPedidosActivos() {
        try {
            String url = BASE_URL + "/pedidos";
            System.out.println("🔗 Barista - Conectando a: " + url);

            // Usar el mismo método que funciona para obtenerMenu
            java.net.URL urlObj = new java.net.URL(url);
            java.net.HttpURLConnection conn = (java.net.HttpURLConnection) urlObj.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);

            int responseCode = conn.getResponseCode();
            System.out.println("📡 Barista - Response Code: " + responseCode);

            if (responseCode == 200) {
                java.io.BufferedReader in = new java.io.BufferedReader(
                        new java.io.InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder content = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();
                conn.disconnect();

                String resultado = content.toString();
                System.out.println("✅ Barista - Datos recibidos: " + resultado.substring(0, Math.min(100, resultado.length())) + "...");
                return resultado;
            } else {
                System.out.println("❌ Barista - Error HTTP: " + responseCode);
                return "Error: " + responseCode;
            }
        } catch (Exception e) {
            System.out.println("❌ Barista - Error: " + e.getMessage());
            e.printStackTrace();
            return "Error de conexión: " + e.getMessage();
        }
    }

    // PUT /pedidos/:id/estado - Actualizar estado (BARISTA)
    public static String actualizarEstadoPedido(String pedidoId, String jsonEstado) {
        try {
            String url = BASE_URL + "/pedidos/" + pedidoId + "/estado";
            System.out.println("🔗 Actualizando estado - URL: " + url);
            System.out.println("📦 JSON enviado: " + jsonEstado);

            java.net.URL urlObj = new java.net.URL(url);
            java.net.HttpURLConnection conn = (java.net.HttpURLConnection) urlObj.openConnection();
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);

            // Escribir el JSON en el body
            try (java.io.OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonEstado.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = conn.getResponseCode();
            System.out.println("📡 Actualizar estado - Response Code: " + responseCode);

            if (responseCode == 200) {
                java.io.BufferedReader in = new java.io.BufferedReader(
                        new java.io.InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder content = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();
                conn.disconnect();
                return content.toString();
            } else {
                // Leer el mensaje de error
                java.io.BufferedReader in = new java.io.BufferedReader(
                        new java.io.InputStreamReader(conn.getErrorStream()));
                String inputLine;
                StringBuilder errorContent = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    errorContent.append(inputLine);
                }
                in.close();
                conn.disconnect();
                return "Error: " + responseCode + " - " + errorContent.toString();
            }
        } catch (Exception e) {
            System.out.println("❌ Actualizar estado - Error: " + e.getMessage());
            e.printStackTrace();
            return "Error de conexión: " + e.getMessage();
        }
    }
}