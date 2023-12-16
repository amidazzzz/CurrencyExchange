package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import model.ExchangeRate;
import repository.ExchangeRateRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/exchangeRates")
public class ExchangeRatesServlet extends HttpServlet {
    private ExchangeRateRepository exchangeRateRepository;

    @Override
    public void init() {
        this.exchangeRateRepository = new ExchangeRateRepository();
    }

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response){
        List<ExchangeRate> exchangeRates = exchangeRateRepository.findAll();
        sendJsonResponse(response, exchangeRates);
    }

    private void sendJsonResponse(HttpServletResponse response, Object data) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(data);
        response.setContentType("application/json");
        response.getWriter().write(jsonResponse);
    }
}
