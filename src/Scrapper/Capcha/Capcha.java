package Scrapper.Capcha;

import org.openqa.selenium.WebDriver;

import org.openqa.selenium.chrome.ChromeDriver;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;



public class Capcha{
    String url;
    String titulo = "Currículo do Sistema de Currículos Lattes";

    public Capcha() {
    }

    public String getLattesCurriculo(String url) {
        this.url = url;
        WebDriver driver = new ChromeDriver();

        driver.get(url);

        // Espera até a página ser achada
        WebDriverWait wait = new WebDriverWait(driver, 5000);
        wait.until(ExpectedConditions.titleContains(titulo));

        // Retorna a página achada
        String retorno = driver.getPageSource();
        driver.close();

        return retorno;
    }
}
