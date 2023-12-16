package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.ExchangeRate;
import repository.ExchangeRateRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/exchangeRate/*")
public class ExchangeRateServlet extends HttpServlet {
    private ExchangeRateRepository exchangeRateRepository;

    @Override
    public void init() {
        this.exchangeRateRepository = new ExchangeRateRepository();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Указана не корректная валютная пара. Пример: .../exchangeRate/USD/EUR");
            return;
        }

        String[] currencies = pathInfo.replaceFirst("/", "").split("/");
        if (currencies.length == 2) {
            String baseCurrencyCode = currencies[0];
            String targetCurrencyCode = currencies[1];

            Optional<ExchangeRate> exchangeRateOptional = exchangeRateRepository.findByCodes(baseCurrencyCode, targetCurrencyCode);

            if (exchangeRateOptional.isPresent()) {
                sendJsonResponse(response, exchangeRateOptional.get());
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Обменный курс для пары не найден.");
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Коды валютной пары отсутствуют в адресе.");
        }
    }

    private void sendJsonResponse(HttpServletResponse response, Object data) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(data);
        response.setContentType("application/json");
        response.getWriter().write(jsonResponse);
    }
}