package repository;

import sql.SqlQueries;
import util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import model.Currency;

public class CurrencyRepository implements CrudRepository<Currency> {


    private Currency mapNewCurrency(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        String code = resultSet.getString("code");
        String fullname = resultSet.getString("fullname");
        String sign = resultSet.getString("sign");

        return new Currency(id, code, fullname, sign);
    }
    @Override
    public Optional<Currency> findById(Long id) {
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQueries.SELECT_BY_ID_CURRENCY)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();


            if (resultSet.next()) {
                return Optional.of(mapNewCurrency(resultSet));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Currency> findAll(){
        List<Currency> currencies = new ArrayList<>();

        try(Connection connection = DatabaseUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(SqlQueries.SELECT_ALL_CURRENCIES)){
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                currencies.add(mapNewCurrency(resultSet));
            }

        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return currencies;
    }

    @Override
    public Optional<Currency> findByCode(String code) {
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQueries.SELECT_BY_CODE_CURRENCY)) {
            statement.setString(1, code);
            ResultSet resultSet = statement.executeQuery();


            if (resultSet.next()) {
                return Optional.of(mapNewCurrency(resultSet));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void save(Currency currency) {

    }

    @Override
    public List<Currency> update(Currency currency) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
