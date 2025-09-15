package config;

import io.github.cdimascio.dotenv.Dotenv;

public class Settings {
    private static final Dotenv dotenv = Dotenv.load();

    public static final String DB_USER = dotenv.get("DB_USER");
    public static final String DB_PASSWORD = dotenv.get("DB_PASSWORD");
    public static final String DB_NAME = dotenv.get("DB_NAME");
    public static final String DB_HOST = dotenv.get("DB_HOST");
    public static final String DB_PORT = dotenv.get("DB_PORT");
    public static final String PDF_DIRECTORY = dotenv.get("PDF_DIRECTORY");
    public static final String PROCESSED_PDF_DIRECTORY = dotenv.get("PROCESSED_PDF_DIRECTORY");
    public static final String TXT_DIRECTORY = dotenv.get("TXT_DIRECTORY");


    public static String getJdbcUrl(){
        return "jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME;
    }

}
