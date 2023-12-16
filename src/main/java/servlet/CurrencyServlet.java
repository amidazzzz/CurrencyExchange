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

@WebServlet("/currency/*")
public class CurrencyServlet extends HttpServlet {
    private CurrencyRepository currencyRepository;

    @Override
    public void init(){
        this.currencyRepository = new CurrencyRepository();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Указана не корректная валюта. Пример: .../currency/USD или /currency/id");
            return;
        }

        String currencyReq = pathInfo.substring(1).toUpperCase();

        try {
            Long id = Long.parseLong(currencyReq);

            processById(id, resp);
        } catch (NumberFormatException e) {
            processByCode(currencyReq, resp);
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code = req.getParameter("code");
        String fullname = req.getParameter("fullname");
        String sign = req.getParameter("sign");

        Currency currency = new Currency();
        currency.setCode(code);
        currency.setFullname(fullname);
        currency.setSign(sign);

        currencyRepository.save(currency);
        resp.getWriter().write("Валюта успешно создана");
    }

    private void processById(Long id, HttpServletResponse response) throws IOException {
        Optional<Currency> currencyIdOptional = currencyRepository.findById(id);

        if (currencyIdOptional.isEmpty()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Валюта с ID " + id + " не найдена.");
            return;
        }

        response.getWriter().write(new ObjectMapper().writeValueAsString(currencyIdOptional.get()));
    }

    private void processByCode(String code, HttpServletResponse response) throws IOException {
        Optional<Currency> currencyCodeOptional = currencyRepository.findByCode(code);

        if (currencyCodeOptional.isEmpty()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Валюта с кодом " + code + " не найдена.");
            return;
        }

        response.getWriter().write(new ObjectMapper().writeValueAsString(currencyCodeOptional.get()));
    }

}
