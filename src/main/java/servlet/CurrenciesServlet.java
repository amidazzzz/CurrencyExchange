package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Currency;
import repository.CurrencyRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/currencies")
public class CurrenciesServlet extends HttpServlet {
    private CurrencyRepository currencyRepository;

    @Override
    public void init(){
        this.currencyRepository = new CurrencyRepository();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("list".equals(action)){
            List<Currency> currencies = currencyRepository.findAll();
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonCurrencies = objectMapper.writeValueAsString(currencies);

            resp.getWriter().write(jsonCurrencies);
        }else{
            resp.getWriter().write("Invalid action parameter");
        }
    }
}
