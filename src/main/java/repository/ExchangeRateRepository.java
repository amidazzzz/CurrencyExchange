package repository;

import model.Currency;
import model.ExchangeRate;
import sql.SqlQueries;
import util.DatabaseUtil;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExchangeRateRepository implements CrudRepository<ExchangeRate> {
    private final CurrencyRepository currencyRepository;

    public ExchangeRateRepository() {
        this.currencyRepository = new CurrencyRepository();
    }

    @Override
    public Optional<ExchangeRate> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<ExchangeRate> findAll() throws SQLException, ClassNotFoundException {
        List<ExchangeRate> exchangeRates = new ArrayList<>();

        try(Connection connection = DatabaseUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(SqlQueries.SELECT_ALL_EXCHANGE_RATES)){
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                exchangeRates.add(mapNewExchangeRate(resultSet));
            }

        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return exchangeRates;
    }

    public Optional<ExchangeRate> findByCodes(String baseCurrencyCode, String targetCurrencyCode) {
        ExchangeRate exchangeRate = null;
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQueries.SELECT_BY_CURRENCY_CODES)) {

            statement.setLong(1,
                    currencyRepository.findByCode(baseCurrencyCode).get().getId());
            statement.setLong(2,
                    currencyRepository.findByCode(targetCurrencyCode).get().getId());

            statement.execute();

            ResultSet resultSet = statement.getResultSet();

            if (resultSet.next()) {
                exchangeRate = mapNewExchangeRate(resultSet);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return Optional.ofNullable(exchangeRate);
    }

    @Override
    public Optional<ExchangeRate> findByCode(String code) {
        return Optional.empty();
    }



    @Override
    public void save(ExchangeRate entity) {

    }

    @Override
    public List<ExchangeRate> update(ExchangeRate entity) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    private ExchangeRate mapNewExchangeRate(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        Long baseCurrencyId = resultSet.getLong("base_currency_id");
        Long targetCurrencyId = resultSet.getLong("target_currency_id");
        BigDecimal rate = resultSet.getBigDecimal("rate");

        return new ExchangeRate(id, baseCurrencyId, targetCurrencyId, rate);
    }
}
