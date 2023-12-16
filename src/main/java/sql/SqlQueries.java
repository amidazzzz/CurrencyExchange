package sql;

public class SqlQueries {
    public static final String SELECT_BY_ID_CURRENCY = "SELECT * FROM currencies WHERE id = ?";
    public static final String SELECT_BY_CODE_CURRENCY = "SELECT * FROM currencies WHERE code = ?";
    public static final String SELECT_ALL_CURRENCIES = "SELECT * FROM currencies";
    public static final String INSERT_CURRENCY = "INSERT INTO currencies (code, fullname, sign) VALUES (?, ?, ?)";
    public static final String UPDATE_CURRENCY = "UPDATE currencies SET code = ?, fullname = ?, sign = ? WHERE id = ?";
    public static final String DELETE_CURRENCY = "DELETE FROM currencies WHERE id = ?";


    public static final String SELECT_ALL_EXCHANGE_RATES = "SELECT * FROM exchange_rates";
    public static final String SELECT_BY_CURRENCY_CODES = "SELECT * FROM currencyexchanger.exchangerates WHERE " +
            "basecurrencyid=? AND targetcurrencyid=?";

}
