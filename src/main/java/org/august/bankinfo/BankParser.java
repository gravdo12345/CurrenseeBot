package org.august.bankinfo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.august.dtomodel.MonobankModel;
import org.august.dtomodel.NBUModel;
import org.august.dtomodel.PrivatBankModel;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class BankParser {
    private final CloseableHttpClient httpClient = HttpClients.createDefault();

    private final Gson gson = new GsonBuilder()
            .serializeNulls()
            .setPrettyPrinting()
            .create();


    private String getRequest(String url) {
        HttpGet httpGet = new HttpGet(url);

        try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
            HttpEntity responseEntity = response.getEntity();
            return EntityUtils.toString(responseEntity, "UTF-8");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public MonobankModel getMonoData(String currency) {
        String data = getRequest("https://api.monobank.ua/bank/currency");

        Type listType = new TypeToken<List<MonobankModel>>() {
        }.getType();
        List<MonobankModel> currencyList = gson.fromJson(data, listType);
        MonobankModel result = null;
        int serch;

        if (currency.equals("USD")) {
            serch = 840;
        } else {
            serch = 978;
        }

        for (MonobankModel item : currencyList) {
            if (item.getCurrencyCodeA() == serch) {
                result = item;
                break;
            }
        }

        return result;
    }

    public NBUModel getNBUModel(String currency) {
        String data = getRequest("https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?json&valcode=" + currency);

        Type listType = new TypeToken<List<NBUModel>>() {
        }.getType();
        List<NBUModel> currencyList = gson.fromJson(data, listType);
        return currencyList.get(0);
    }

    public PrivatBankModel getPrivatBankModel(String currency) {
        String data = getRequest("https://api.privatbank.ua/p24api/pubinfo?exchange&json&coursid=11");

        Type listType = new TypeToken<List<PrivatBankModel>>() {
        }.getType();
        List<PrivatBankModel> currencyList = gson.fromJson(data, listType);
        PrivatBankModel result = null;

        for (PrivatBankModel item : currencyList) {
            if (item.getCcy().equals(currency)) {
                result = item;
                break;
            }
        }
        return result;
    }

}
