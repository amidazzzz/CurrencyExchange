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
import java.util.Optional;

@WebServlet("/currency")
public class CurrencyServlet extends HttpServlet {
    private CurrencyRepository currencyRepository;

    @Override
    public void init(){
        this.currencyRepository = new CurrencyRepository();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String currencyIdParam = req.getParameter("id");

        if (currencyIdParam != null && !currencyIdParam.isEmpty()) {
            try {
                Long currencyId = Long.parseLong(currencyIdParam);
                Optional<Currency> currencyOptional = currencyRepository.findById(currencyId);

                if (currencyOptional.isPresent()) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    String jsonCurrency = objectMapper.writeValueAsString(currencyOptional.get());

                    resp.setContentType("application/json");
                    resp.getWriter().write(jsonCurrency);
                } else {
                    resp.getWriter().write("Currency not found");
                }
            } catch (NumberFormatException e) {
                resp.getWriter().write("Invalid ID parameter");
            }
        } else {
            resp.getWriter().write("ID parameter is required");
        }
    }
}
